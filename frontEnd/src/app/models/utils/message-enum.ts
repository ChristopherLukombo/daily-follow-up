export enum TypeMessage {
  /** Diet */
  DIET_DOES_NOT_EXIST = "Le régime alimentaire recherché n'existe pas, ou a été supprimé. Veuillez réessayer.",

  /** Menu **/
  MENU_FORM_INVALID = "Le menu est incomplet. Veuillez vérifier la carte de remplacement ainsi que vos insertions pour chaque jour.",
  MENU_HAS_TO_BEGIN_AT_LEAST_NEXT_WEEK = "Le menu doit débuter au moins partir de la semaine prochaine.",
  MENU_DOES_NOT_EXIST = "Ce menu n'existe pas. Veuillez réessayer.",
  OLD_MENU_ARE_NOT_EDITABLE = "Il n'est pas possible de modifier un ancien menu, ni un menu en cours. Vous pouvez néanmoins recréer un menu à partir de celui ci.",

  /** Erreurs applicatives */
  NOT_AUTHENTICATED = "Vous n'êtes plus connecté, veuillez rafraichir votre navigateur.",
  AN_ERROR_OCCURED = "Une erreur s'est produite. Veuillez réessayer plus tard.",
}
