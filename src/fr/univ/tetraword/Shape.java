/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import static fr.univ.tetraword.shapeType.T;
import static fr.univ.tetraword.shapeType.leftL;
import static fr.univ.tetraword.shapeType.leftZ;
import static fr.univ.tetraword.shapeType.line;
import static fr.univ.tetraword.shapeType.rightL;
import static fr.univ.tetraword.shapeType.rightZ;
import static fr.univ.tetraword.shapeType.square;

/**
 *
 * @author bruno
 */

public class Shape {
    
    
    private shapeType type;
    private Brick bricks[][];
    public int x,y;
    public int couleur;
    public int width,height;
    
    public Shape(shapeType type){
        this.type=type;
        bricks=new Brick[4][4];
        
        x=7-type.getTaille();
        y=0;
       
        double totalProb=0;
        for(int i=65;i<65+26;++i)
            totalProb+=getRarityFromLetter((char) i);
        
        char random = getLetterFromProb((int)(Math.random() * totalProb));
        Brick case1=new Brick(random,getRarityFromLetter(random));
        random = getLetterFromProb((int)(Math.random() * totalProb));
        Brick case2=new Brick(random,getRarityFromLetter(random));
        random = getLetterFromProb((int)(Math.random() * totalProb));
        Brick case3=new Brick(random,getRarityFromLetter(random));
        random = getLetterFromProb((int)(Math.random() * totalProb));
        Brick case4=new Brick(random,getRarityFromLetter(random));
        
        switch(type){
            case T:
                 bricks[1][0]=case1;
                 bricks[0][1]=case2;
                 bricks[1][1]=case3;
                 bricks[1][2]=case4;
                 width=2;
                 height=1;
                break;
            case square:
                 bricks[0][0]=case1;
                 bricks[0][1]=case2;
                 bricks[1][0]=case3;
                 bricks[1][1]=case4;
                 width=1;
                 height=1;
                break;
            case rightZ:
                 bricks[1][0]=case1;
                 bricks[1][1]=case2;
                 bricks[0][1]=case3;
                 bricks[0][2]=case4;
                 width=2;
                 height=1;
                break;
            case leftZ:
                 bricks[0][0]=case1;
                 bricks[0][1]=case2;
                 bricks[1][1]=case3;
                 bricks[1][2]=case4;
                 width=2;
                 height=1;
                break;
            case rightL:
                 bricks[0][1]=case1;
                 bricks[0][0]=case2;
                 bricks[1][0]=case3;
                 bricks[2][0]=case4;
                 width=1;
                 height=2;
                break;
            case leftL:
                 bricks[0][0]=case1;
                 bricks[0][1]=case2;
                 bricks[1][1]=case3;
                 bricks[2][1]=case4;
                 width=1;
                 height=2;
                break;
            case line:
                 bricks[0][0]=case1;
                 bricks[1][0]=case2;
                 bricks[2][0]=case3;
                 bricks[3][0]=case4;
                 width=0;
                 height=3;
                break;
            default:
                System.out.println("Not a Shape");
                break;
        }
        
        //couleurs
        couleur = (int)((Math.random() * (6)));

    }
    
    public void rotateShape(){
        
        Brick res[][] = new Brick[type.getTaille()][type.getTaille()];
        for (int i = 0; i < type.getTaille(); ++i) {
            for (int j = 0; j < type.getTaille(); ++j) {
                res[i][j] = bricks[type.getTaille() - j - 1][i];
            }
        }
        
        for (int i = 0; i < type.getTaille(); ++i) {
            for (int j = 0; j < type.getTaille(); ++j) {
                bricks[i][j]=res[i][j];
            }
        }
        
        int tmp=width;
        width=height;
        height=tmp;
    }
    
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
    
    public static char getLetterFromProb(double prob){
        double total=0;
        char a;
        for(a='A';a<='Z';++a){
            total+=getRarityFromLetter((char) a);
            if(total>prob) break;
        }
        return a;
    }
    
    public void refreshShape(){
        rePosition();
        width=getRightSide();
        height=getLowestLine();
    }
    
    public void rePosition(){
        boolean done=false;
        
        //La 1ere ligne est-elle vide ?
        for(int i=0;i<type.getTaille();++i){
            if(bricks[0][i]!=null)
                done=true;
        }
        
        //Si oui, on remonte tout
        if(!done){
            
            for(int i=0;i<type.getTaille();++i){
                for(int j=0;j<type.getTaille()-1;++j){
                    bricks[j][i]=bricks[j+1][i];
                }
                bricks[type.getTaille()-1][i]=null;
            }
        }
            
        
        done = false;
        
        //La 1ere colonne est-elle vide ?
        for(int i=0;i<type.getTaille();++i){
            if(bricks[i][0]!=null)
                done=true;
        }
        
        //Si oui, on decale tout
        if(!done){
            for(int i=0;i<type.getTaille();++i){
                for(int j=0;j<type.getTaille()-1;++j){
                    bricks[i][j]=bricks[i][j+1];
                }
                bricks[i][type.getTaille()-1]=null;
            }
        }
    }
    
    public int getLowestLine(){
        for(int i=3;i>=0;--i){
            for(int j=0;j<4;++j){
                if(bricks[i][j]!=null)
                    return i;
            }
        }
        return -1;
    }
    
    //Retourne le côté de la pièce concerné
    public int getRightSide(){
         
        for(int i=3;i>=0;--i){
            for(int j=0;j<4;++j){
                if(bricks[j][i]!=null)
                    return i;
            }
        }
        return -1;     
    }
    
    public static Shape getRandomShape(){
        char random = (char) (Math.random() * 7);
        switch(random){
            case 0:
                return (new Shape(T));
            case 1:
                return (new Shape(square));
            case 2:
                return (new Shape(rightZ));
            case 3:
                return (new Shape(leftZ));
            case 4:
                return (new Shape(rightL));
            case 5:
                return (new Shape(leftL));
            case 6:
                return (new Shape(line));
            default:
                break;
        }
        return null;
    }
    
    public Brick[][] getBricks(){
        return bricks;    
    }
    
    public shapeType getType(){
        return type;
    }    
    
    public static void main (String[] args){
        Shape s=new Shape(line);
        s.printShape();
        System.out.println("couleur"+s.couleur);
    }
}
