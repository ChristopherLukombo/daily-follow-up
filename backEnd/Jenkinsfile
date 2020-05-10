def projectName = "backEnd"
def mails = "christopher.lukombo@outlook.fr;neal.k@hotmail.fr;Angelo.deliessche@gmail.com"

pipeline{
    triggers { 
        pollSCM('H * * * *') 
        }
    options { timeout(time: 1, unit: 'HOURS') }
    agent any
    stages {
        stage("Build & Test") {
            options { timeout(time: 1, unit: 'HOURS') }
            steps {
                script {
                    dir(projectName) {
                        sh("mvn clean package -X -e -Pprod")
                    }
                }
            } 
            post {
                always{
                    junit '**/target/surefire-reports/*.xml'
                }
                
                failure {
                    emailext(
                        body: "Plus d'information sur : ${env.BUILD_URL}", mimeType: 'text/html',
                        replyTo: '', subject: "[${env.JOB_NAME}] Failure - Rapport de build",
                        to: "${mails}", recipientProviders: []
                    )
                }
                fixed {
                    emailext(
                        body: "Plus d'information sur : ${env.BUILD_URL}", mimeType: 'text/html',
                        replyTo: '', subject: "[${env.JOB_NAME}] Fixed - Rapport de build",
                        to: "${mails}", recipientProviders: []
                    )
                }
            }
        }
    }
}