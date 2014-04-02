/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

/**
 *
 * @author bruno
 */
public class Game extends Thread {
    Shape currentShape;
    private int score;
    private int level;
    Dictionary dictionary;
    private Box grid[][];
    
    public Game(){
        score=0;
        level=1;
        currentShape=null;
        grid=new Box[20][10];
        
    }
    
    public int getScore(){
        return score;
    }
    
    public int getLevel(){
        return level;
    }
    
    public Box[][] getGrid(){
        return grid;
    }
    
    public void run(){
        boolean win=false;
        
        while(!win){
            
        }
    }
    
}
