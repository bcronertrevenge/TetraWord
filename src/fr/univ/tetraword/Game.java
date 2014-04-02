/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        //Boucle principale
        while(!win){
            try {
                currentShape=Shape.getRandomShape();
                
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void placeShape(Shape shape){
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                grid[shape.y-i][shape.x-j].setBrick(shape.getBricks()[shape.y-i][shape.x-j]);
            }
        }
        //Box[shape.y][shape.x]
    }
}
