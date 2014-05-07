package fr.univ.tetraword;

import java.io.Serializable;

/**
    * modifierType est une énumération qui contient les différents types de modificateurs
 **/
public enum modifierType{
       speedFall, //Acceleration de la chute
       slowFall, // Ralentissement de la chute
       directFall, // La piece tombe directement en bas de la grille
       reverse, // Le plateau est a l'envers
       switchGrid, // Echange le plateau avec l'autre joueur
       bonus, //Ajoute des points
       malus, //Enleve des points
       explode, // La pièce en cours explose et detruit des briques
       allowWorddle, // Enleve le temps de rechargement du mode Worddle
       reverseOther;
      }