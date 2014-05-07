package fr.univ.tetraword;

import java.io.Serializable;
import java.util.HashMap;

/**
    * Moves est la classe représentant le mouvement d'une pièce
 **/
public class Moves {
    HashMap<String,Integer> Moves;
    int nblignes; //nombre de lignes enlevées
    int hauteurMax;
    int trous;
 
/**
    * Constructeur par défaut
 **/
    public Moves(){
        Moves=new HashMap<String,Integer>();
        Moves.put("Deplacement",0);
        Moves.put("Rotate",0);
        nblignes=0;
        hauteurMax=0;
        trous=0;
    }

 /**
        * Constructeur avec paramètres
        * @param deplacement
        * indique le nombre de déplacements latéraux (gauche-droite) du mouvement
        * @param rotate
        * indique le nombre de rotations du mouvement
        * @param nblignes
        * indique le nombre de lignes que le mouvement peut supprimer
        * @param hauteurMax
        * indique la hauteur maximale qu'un mouvement peut atteindre
        * @param trous
        * nombre de trous que l'on peut faire sous la pièce concernée par le mouvement
 **/
    public Moves(int deplacement, int rotate,int nblignes,int hauteurMax, int trous){
        Moves=new HashMap<String,Integer>();
        Moves.put("Deplacement",deplacement);
        Moves.put("Rotate",rotate);
        this.nblignes=nblignes;
        this.hauteurMax=hauteurMax;
        this.trous=trous;
    }

 /**
        Permet de réinitialiser tous les mouvements
 **/
    public void clear(){
        Moves.put("Deplacement",0);
        Moves.put("Right",0);
        Moves.put("Rotate",0);
    }
    
/**
        Permet de savoir si on peut rotater une pièce
 **/   
    public int getRotation(){
        return Moves.get("Rotate");
    }
    
/**
        Permet de savoir si on peut déplacer une pièce
 **/  
    public int getDeplacement(){
        return Moves.get("Deplacement");
    }

/**
        Permet d'autoriser la rotation d'une pièce
 **/  
    public void setRotation(int rotate){
        Moves.put("Rotate",rotate);
    }

/**
        Permet d'autoriser le déplacement latéral d'une pièce
 **/
    public void setDeplacement(int deplacement){
        Moves.put("Deplacement",deplacement);
    }
}
