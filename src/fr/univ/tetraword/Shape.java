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
        /*for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                bricks[i][j]=new Brick(); 
            }
        }*/
        if(type.getTaille()==3)
            x=4;
        else if(type.getTaille()==4)
            x=3;
        y=0;
       
        char random = (char) ((Math.random() * (26)) + 65);
        Brick case1=new Brick(random,getRarityFromLetter(random));
        random = (char) ((Math.random() * (26)) + 65);
        Brick case2=new Brick(random,getRarityFromLetter(random));
        random = (char) ((Math.random() * (26)) + 65);
        Brick case3=new Brick(random,getRarityFromLetter(random));
        random = (char) ((Math.random() * (26)) + 65);
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
        couleur = (int) ((Math.random() * (3)));

    }
    
    public void rotateShape(){
        
        
        if(type.getTaille()==3){
        Brick tmp, tmp2;
            
        //+
        tmp=bricks[0][1];
        
        tmp2=bricks[1][0];
        bricks[1][0]=tmp;
        
        tmp=bricks[2][1];
        bricks[2][1]=tmp2;
        
        tmp2=bricks[1][2];
        bricks[1][2]=tmp;
        
        bricks[0][1]=tmp2;
        
        //x
        tmp=bricks[0][0];
        
        tmp2=bricks[2][0];
        bricks[2][0]=tmp;
        
        tmp=bricks[2][2];
        bricks[2][2]=tmp2;
        
        tmp2=bricks[0][2];
        bricks[0][2]=tmp;
        
        bricks[0][0]=tmp2;
        }
        else if(type.getTaille()==4){
            Brick tmp;
            for(int i=1;i<4;++i){
                tmp=bricks[i][0];
                bricks[i][0]=bricks[0][i];
                bricks[0][i]=tmp;
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
    
    private static int getRarityFromLetter(char lettre){
        switch(lettre){
            case 'A':
                return 1;
            case 'B':
                return 3;
            case 'C':
                return 3;
            case 'D':
                return 2;
            case 'E':
                return 1;
            case 'F':
                return 4;
            case 'G':
                return 2;
            case 'H':
                return 4;
            case 'I':
                return 1;
            case 'J':
                return 8;
            case 'K':
                return 5;
            case 'L':
                return 1;
            case 'M':
                return 3;
            case 'N':
                return 1;
            case 'O':
                return 1;
            case 'P':
                return 3;
            case 'Q':
                return 10;
            case 'R':
                return 1;
            case 'S':
                return 1;
            case 'T':
                return 1;
            case 'U':
                return 1;
            case 'V':
                return 4;
            case 'W':
                return 4;
            case 'X':
                return 8;
            case 'Y':
                return 4;
            case 'Z':
                return 10;
            default:
                return -1;
        }
        
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
