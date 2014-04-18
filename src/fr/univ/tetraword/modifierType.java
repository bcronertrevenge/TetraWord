/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

/**
 *
 * @author bruno
 */
public enum modifierType {
       speedFall(false), //Acceleration de la chute
       slowFall(false), // Ralentissement de la chute
       directFall(false), // La piece tombe directement en bas de la grille
       reverse(false), // Le plateau est a l'enver
       switchGrid(true), // Echange le plateau avec l'autre joueur
       bonus(false), //Ajoute des points
       malus(false), //Enleve des points
       explode(false), // La pièce en cours explose et detruit des briques
       allowWorddle(false); // Enleve le temps de rechargement du mode Worddle

       private final boolean multi;
       
       modifierType(boolean multi){
            this.multi=multi;
        }
       
       boolean isMulti(){
           return multi;
       }
      }