export enum TypeMessage {
  /** Login **/
  PSEUDO_OR_PASSWORD_INCORRECT = "Le nom d'utilisateur et le mot de passe ne correspondent pas.",

  /** Diet **/
  DIET_DOES_NOT_EXIST = "Le régime alimentaire recherché n'existe pas, ou a été supprimé. Veuillez réessayer.",

  /** Patient **/
  PATIENT_DOES_NOT_EXIST = "Ce patient n'existe pas. Veuillez réessayer.",
  PATIENT_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED = "Ce patient n'existe pas, ou ne fait plus partie de la clinique. Veuillez réessayer.",
  PATIENT_OR_PATIENTS_ALREADY_EXIST = "Un ou plusieurs patients existent déjà",
  NO_HISTORY_FOR_PATIENT = "Il n'y a aucun historique pour le patient recherché.",

  /** Chambres **/
  INFORMATIONS_OF_ROOMS_NOT_AVAILABLE = "Informations sur la chambre indisponibles.",
  IMPOSSIBLE_TO_GET_FREE_ROOMS = "Impossible de récupérer les chambres disponibles pour le moment.",

  CONTENT_DOES_NOT_EXIST = "Ce plat n'éxiste pas, ou a été supprimé du stock de nourriture de la clinique. Veuillez réessayer.",
  NO_INFOS_FROM_API_FOR_THIS_CONTENT = "Il n'existe aucune information connue sur ce plat. Veuillez renseigner manuellement les ingédients du plat pour le créer.",

  /** Menu **/
  MENU_FORM_INVALID = "Le menu est incomplet. Veuillez vérifier la carte de remplacement ainsi que vos insertions pour chaque jour.",
  MENU_HAS_TO_BEGIN_AT_LEAST_NEXT_WEEK = "Le menu doit débuter au moins partir de la semaine prochaine.",
  MENU_DOES_NOT_EXIST = "Ce menu n'existe pas. Veuillez réessayer.",
  OLD_MENU_ARE_NOT_EDITABLE = "Il n'est pas possible de modifier un ancien menu, ni un menu en cours. Vous pouvez néanmoins recréer un menu à partir de celui ci.",
  MENU_CANNOT_BE_DECLINE_AUTOMATICALLY = "Le menu ne peut pas être automatiquement adapté au(s) régime(s) séléctionné(s), veuillez le faire manuellement.",
  IMPOSSIBLE_TO_GET_LOCAL_MENU = "Impossible de charger le menu à modifier.",
  NO_CONTENTS_AVAILABLE_FOR_CREATING_MENU = "Il n'y a aucun plats disponibles actuellement dans la clinique. Veuillez d'abord en ajouter afin de pouvoir composer un menu.",

  /** Commandes **/
  ORDER_OF_PATIENT_NOT_FOUND = "Cette commande n'est pas, ou n'est plus disponible. Veuillez réessayer.",
  ORDER_INFOS_DOES_NOT_EXIST = "Les informations concernant la date de livraison et le moment de la commande sont maquantes. Veuillez réessayer.",
  OLD_ORDERS_ARE_NOT_EDITABLE = "Il n'est plus possible de modifier une commande du jour ni une commande passée.",
  DELIVERY_ORDER_DATE_CANNOT_BE_IN_PAST = "La date de livraison choisit ne doit pas être déjà passée. Veuillez réessayer.",
  ORDERS_OF_TODAY_ARE_NOT_AVAILABLE = "Impossible de récupérer les commandes d'aujourd'hui pour le moment. Réessayez plus tard.",
  NO_ORDERS_OF_TODAY_YET = "Aucune commande à préparer pour le moment.",

  /** Erreurs applicatives **/
  NOT_AUTHENTICATED = "Vous n'êtes plus connecté, veuillez rafraichir votre navigateur.",
  NOT_AUTHORIZED = "Vous n'êtes plus habilité à cette requête.",
  AN_ERROR_OCCURED = "Une erreur s'est produite. Veuillez réessayer plus tard.",
}
