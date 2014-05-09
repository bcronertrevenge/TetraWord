package fr.univ.tetraword;

import java.io.Serializable;

/**
    * Shape est la classe représentant une pièce du jeu
 **/
public class Shape implements Serializable {
    
    private Brick bricks[][];
    public int x,y;
    public int couleur;
    public int width,height;

/**
    * Constructeur par défaut
 **/
    public Shape(){
       bricks=shapeType.getBricksWithType();
       width=0;
       height=0;
       for(int i=0;i<4;++i){
           for(int j=0;j<4;++j){
               if(bricks[i][j]!=null){
                   if(height < i) height=i;
                   if(width < j) width=j;
               }
           }
       }
       
       x=3;
        y=0;
        
        
        //couleurs
        couleur = (int)((Math.random() * (6)));

    }
    
/**
    * Permet une rotation de la pièce courante
 **/   
    public void rotateShape(){
        
        int taille=width+1;
        if(taille <= height) taille=height+1;
                
        Brick res[][] = new Brick[taille][taille];
        for (int i = 0; i < taille; ++i) {
            for (int j = 0; j < taille; ++j) {
                res[i][j] = bricks[taille - j - 1][i];
            }
        }
        
        for (int i = 0; i < taille; ++i) {
            for (int j = 0; j < taille; ++j) {
                bricks[i][j]=res[i][j];
            }
        }
        
        refreshShape();
        
    }
 
/**
    * Permet l'affichage de la pièce avec ses lettres (dans la console)
 **/ 
    public void printShape(){
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                if(bricks[i][j]!=null)
                    System.out.print(bricks[i][j].lettre+" ");
                else
                    System.out.print("  ");
            }
            System.out.print("\n");
        }
        
    }
 
/**
    * Permet d'obtenir la rareté d'une lettre
    * @param lettre
    * lettre dont on veut connaître la rareté
 **/ 
    public static double getRarityFromLetter(char lettre){
        switch(lettre){
            case 'A':
                return 8.11;
            case 'B':
                return 0.81;
            case 'C':
                return 3.38;
            case 'D':
                return 4.28;
            case 'E':
                return 17.69;
            case 'F':
                return 1.13;
            case 'G':
                return 1.19;
            case 'H':
                return 0.74;
            case 'I':
                return 7.24;
            case 'J':
                return 0.18;
            case 'K':
                return 0.02;
            case 'L':
                return 5.99;
            case 'M':
                return 2.29;
            case 'N':
                return 7.68;
            case 'O':
                return 5.20;
            case 'P':
                return 2.92;
            case 'Q':
                return 0.83;
            case 'R':
                return 6.43;
            case 'S':
                return 8.87;
            case 'T':
                return 7.44;
            case 'U':
                return 5.23;
            case 'V':
                return 1.28;
            case 'W':
                return 0.06;
            case 'X':
                return 0.53;
            case 'Y':
                return 0.26;
            case 'Z':
                return 0.12;
            default:
                return -1;
        }
        
    }

/**
    * Permet de donner à l'utilisateur une lettre en fonction de probabilités
    * @param prob
    * probabilité
 **/ 
    public static char getLetterFromProb(double prob){
        double total=0;
        char a;
        for(a='A';a<='Z';++a){
            total+=getRarityFromLetter((char) a);
            if(total>prob) break;
        }
        return a;
    }

/**
    * Permet de mettre à jour la position, la largeur, la hauteur de la pièce courante
 **/ 
    public void refreshShape(){
        rePosition();
        width=getRightSide();
        height=getLowestLine();
    }
 
/**
    * Permet de repositionner une pièce si la place au dessus et/ou à gauche est vide
 **/ 
    public void rePosition(){
        boolean done=false;
        int taille=width+1;
        if(taille <= height) taille=height+1;
        
        while(!done){
            //La 1ere ligne est-elle vide ?
            for(int i=0;i<taille;++i){
                if(bricks[0][i]!=null)
                    done=true;
            }

            //Si oui, on remonte tout
            if(!done){

                for(int i=0;i<taille;++i){
                    for(int j=0;j<taille-1;++j){
                        bricks[j][i]=bricks[j+1][i];
                    }
                    bricks[taille-1][i]=null;
                }
            }
        }
        
        done = false;
        
        while(!done){
            //La 1ere colonne est-elle vide ?
            for(int i=0;i<taille;++i){
                if(bricks[i][0]!=null)
                    done=true;
            }

            //Si oui, on decale tout
            if(!done){
                for(int i=0;i<taille;++i){
                    for(int j=0;j<taille-1;++j){
                        bricks[i][j]=bricks[i][j+1];
                    }
                    bricks[i][taille-1]=null;
                }
            }
        }
    }

/**
    * Retourne l'indice de la ligne la plus basse qu'occupe la pièce
 **/ 
    public int getLowestLine(){
        for(int i=3;i>=0;--i){
            for(int j=0;j<4;++j){
                if(bricks[i][j]!=null)
                    return i;
            }
        }
        return -1;
    }
    
/**
    * Retourne le côté droit de la pièce concernée
 **/ 
    public int getRightSide(){
         
        for(int i=3;i>=0;--i){
            for(int j=0;j<4;++j){
                if(bricks[j][i]!=null)
                    return i;
            }
        }
        return -1;     
    }

/**
    * Permet de créer une pièce aléatoire
 **/ 
    public static Shape getRandomShape(){
        return (new Shape());
    }
 
/**
    * Retourne le tableau de briques contenues dans la pièce courante
 **/ 
    public Brick[][] getBricks(){
        return bricks;    
    }

/**
    * Retourne le type (la forme) de la pièce courante
 **/ 
    
}
