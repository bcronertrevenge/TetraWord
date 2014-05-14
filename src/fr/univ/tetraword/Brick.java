package fr.univ.tetraword;

import java.io.Serializable;

/**
    * Brick est la classe représentant le contenu d'une Box
 **/
public class Brick implements Serializable{
    char lettre;
    double rarity;
 
/**
    * Constructeur par défaut
 **/
    public Brick(){
        lettre='a';
        rarity=0;
    }

/**
    * Constructeur en fonctions de paramètres
    * @param lettre
    * la lettre qui est contenue dans la brique
    * @param rarity
    * la rareté de cette lettre
 **/
    public Brick(char lettre, double rarity){
        this.lettre=lettre;
        this.rarity=rarity;
    }
 
/**
    * Permet de donner à la brique courante les paramètres d'une autre brique
    * @param b
    * la brique dont on souhaite récupérer les données
 **/
    public void setBrick(Brick b){
        lettre=b.lettre;
        rarity=b.rarity;
    }
    
}
