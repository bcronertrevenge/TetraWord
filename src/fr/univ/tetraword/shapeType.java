/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import java.io.Serializable;

/**
 *
 * @author bruno
 */
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