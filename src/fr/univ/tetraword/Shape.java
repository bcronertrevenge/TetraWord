/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import static fr.univ.tetraword.shapeType.T;

/**
 *
 * @author bruno
 */

public class Shape {
    
    
    shapeType type;
    Brick bricks[][];
    
    public Shape(shapeType type){
        this.type=type;
        bricks=new Brick[4][4];
        
       
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
                break;
            case Square:
                 bricks[0][0]=case1;
                 bricks[0][1]=case2;
                 bricks[1][0]=case3;
                 bricks[1][1]=case4;
                break;
            case rightZ:
                 bricks[1][0]=case1;
                 bricks[1][1]=case2;
                 bricks[0][1]=case3;
                 bricks[0][2]=case4;
                break;
            case leftZ:
                 bricks[0][0]=case1;
                 bricks[0][1]=case2;
                 bricks[1][1]=case3;
                 bricks[1][2]=case4;
                break;
            case rightL:
                 bricks[0][1]=case1;
                 bricks[0][0]=case2;
                 bricks[1][0]=case3;
                 bricks[2][0]=case4;
                break;
            case leftL:
                 bricks[0][0]=case1;
                 bricks[0][1]=case2;
                 bricks[1][1]=case3;
                 bricks[2][1]=case4;
                break;
            case line:
                 bricks[0][0]=case1;
                 bricks[1][0]=case2;
                 bricks[2][0]=case3;
                 bricks[3][0]=case4;
                break;
            default:
                System.out.println("Not a Shape");
                break;
        }
    }
    
    private int getRarityFromLetter(char lettre){
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
    
    public static void main (String[] args){
        Shape s=new Shape(T);
        System.out.println("Hello");
    }
}
