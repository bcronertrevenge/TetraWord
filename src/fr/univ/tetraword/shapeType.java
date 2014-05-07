package fr.univ.tetraword;

import java.io.Serializable;

public enum shapeType{
        T(3),
        square(2),
        rightZ(3),
        leftZ(3),
        rightL(3),
        leftL(3),
        line(4);
        
        private final int taille;
        
        private shapeType(int taille){
            this.taille=taille;
        }
        
        public int getTaille(){
            return taille;
        }
      }