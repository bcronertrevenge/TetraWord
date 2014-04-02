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
        boolean end=false;
        System.out.println("Debut boucle principale");
        //Boucle principale
        try {
        currentShape=Shape.getRandomShape();
                            
        while(!end){
                if(shapeFall(currentShape)==1)
                    currentShape=Shape.getRandomShape();
                
                Thread.sleep(1000);
            
        }
        } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public int shapeFall(Shape shape){
         
        System.out.println("Descente");
        //La pièce ne peut plus chuter
        if(!canFall(shape)){
            return 1;
        }
        shape.y--;
        
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                grid[shape.y-i][shape.x-j].setBrick(shape.getBricks()[shape.y-i][shape.x-j]);
                grid[shape.y-i][shape.x-j].setShape(shape);
            }
        }
        
        return 0;
    }
    
    //Teste si la piece peut tomber
    public boolean canFall(Shape shape){
        int ligne=getLowestLine(shape);
        for(int i=0;i<4;++i){
            if(shape.y-ligne-1<0)
                return false;
            else if(grid[shape.y-ligne-1][shape.x-i]!=null && shape.getBricks()[ligne][i]!=null)
                return false;
        }
        return true;
    }
    
    //retourne la ligne d'une shape la plus basse
    public int getLowestLine(Shape shape){
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                if(shape.getBricks()[i][j]!=null)
                    return i;
            }
        }
        return -1;
    }
}
