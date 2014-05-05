/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author bruno
 */
public class Moves {
    HashMap<String,Integer> Moves;
    int nblignes; //nombre de lignes enlevées
    int hauteurMax;
    int trous;
    
    public Moves(){
        Moves=new HashMap<String,Integer>();
        Moves.put("Deplacement",0);
        Moves.put("Rotate",0);
        nblignes=0;
        hauteurMax=0;
        trous=0;
    }
    
    public Moves(int deplacement, int rotate,int nblignes,int hauteurMax, int trous){
        Moves=new HashMap<String,Integer>();
        Moves.put("Deplacement",deplacement);
        Moves.put("Rotate",rotate);
        this.nblignes=nblignes;
        this.hauteurMax=hauteurMax;
        this.trous=trous;
    }
    
    public void clear(){
        Moves.put("Deplacement",0);
        Moves.put("Right",0);
        Moves.put("Rotate",0);
    }
    
    public int getRotation(){
        return Moves.get("Rotate");
    }
    
    public int getDeplacement(){
        return Moves.get("Deplacement");
    }
    
    public void setRotation(int rotate){
        Moves.put("Rotate",rotate);
    }
    
    public void setDeplacement(int deplacement){
        Moves.put("Deplacement",deplacement);
    }
}
