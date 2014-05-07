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
public class Brick{
    char lettre;
    double rarity;
    
    public Brick(){
        lettre='a';
        rarity=0;
    }
    
    public Brick(char lettre, double rarity){
        this.lettre=lettre;
        this.rarity=rarity;
    }
    
    public void setBrick(Brick b){
        lettre=b.lettre;
        rarity=b.rarity;
    }
    
}
