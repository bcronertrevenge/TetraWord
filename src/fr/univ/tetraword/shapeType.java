package fr.univ.tetraword;

import java.io.Serializable;

/**
    * shapeType est une énumération qui contient les différents types de forme d'une pièce
 **/
public enum shapeType{
        T(3),
        square(2),
        rightZ(3),
        leftZ(3),
        rightL(3),
        leftL(3),
        line(4);
        
        private final int taille;
        
/**
    * Constructeur par défaut
    * @param taille
    * taille maximale de la pièce
 **/
        private shapeType(int taille){
            this.taille=taille;
        }

/**
    * Retourne la taille de la pièce
 **/
        public int getTaille(){
            return taille;
        }
      }