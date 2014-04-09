/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

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
    JPanel grille[][];
    
    public Game(){
        score=0;
        level=1;
        currentShape=null;
        grid=new Box[20][10];
                
        grille=new JPanel[20][10];
        // Initialisation de Grid
        for(int i=0;i<20;++i){
            for(int j=0;j<10;++j)
                grid[i][j]=new Box();
        }
        //Initialisation de la grille
        Border whiteline = BorderFactory.createLineBorder(Color.WHITE,1);
        for (int i=0; i<20;++i){
            for (int j=0; j<10; ++j){
                JPanel pCase = new JPanel();
                pCase.setBackground(Color.gray);
                pCase.setBorder(whiteline);
                grille[i][j]=pCase;    
                }
            }
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
    
    public JPanel[][] getGrille(){
        return grille;
    }
    
    public void run(){
        boolean end=false;
        
        //Boucle principale
        try {
        currentShape=Shape.getRandomShape();
                            
        while(!end){
            if(shapeFall()==1)                     
                    currentShape=Shape.getRandomShape();
                
                Thread.sleep(1000);
                moveShapeAside(1);
                //Mis a jour de l'affichage
        }
        } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    //Bouge la piece a gauche(-1) ou a droite(1)
    public void moveShapeAside(int sens){
        Shape shape=currentShape;

        if(!canMoveAside(shape,sens))
            return;
        
        //Gauche
        if(sens < 0){
            for(int i=0;i<4;++i){
                for(int j=0;j<4;++j){
                    grid[shape.y+i][shape.x+j-1].setBrick(grid[shape.y+i][shape.y+j].getBrick());
                    grid[shape.y+i][shape.x+j-1].setShape(grid[shape.y+i][shape.y+j].getShape());
                    grid[shape.y+i][shape.x+j].setBrick(null);
                    grid[shape.y+i][shape.x+j].setShape(null);
                }
            }
            shape.x--;
        }
        //Droite
        else {
            int col=getRightSide(shape);
            for(int i=0;i<4;++i){
                for(int j=col;j>=0;--j){                    
                    grid[shape.y+i][shape.x+j+1].setBrick(grid[shape.y+i][shape.y+j].getBrick());
                    grid[shape.y+i][shape.x+j+1].setShape(grid[shape.y+i][shape.y+j].getShape());
                    grid[shape.y+i][shape.x+j].setBrick(null);
                    grid[shape.y+i][shape.x+j].setShape(null);
                }
            }
            shape.x++;
        }
    }
    
    //Teste si le mouvement est possible
    public boolean canMoveAside(Shape shape, int sens){
                    
          //Mouvement a gauche
          if(sens < 0){
              if(shape.x-1<0){                 
                  return false;
              }
              else {
                  for(int i=0;i<4;++i){
                      if(!grid[shape.x][shape.y].isEmpty() && !grid[shape.x-1][shape.y].isEmpty()){
                            return false;
                      }
                  }
              }
          }
          //Mouvement à droite
          else {
              int col=getRightSide(shape);
             if(col==-1) return false;
             System.out.println(shape.x+col+1);
              if(shape.x+col+1>=10){
                  return false;
              }
              else {
                  for(int i=0;i<4;++i){
                      if(!grid[shape.x+col][shape.y].isEmpty() && !grid[shape.x+col+1][shape.y].isEmpty())
                          return false;
                  }
              }
          }
          return true;
    }
    
    public void rotate(){
        currentShape.rotateShape();
    }
    
    //Retourne le côté de la pièce concerné
    public int getRightSide(Shape shape){
         
        for(int i=3;i>=0;--i){
            for(int j=0;j<4;++j){
                if(shape.getBricks()[j][i]!=null)
                    return i;
            }
        }
        return -1;     
    }
    
    public int shapeFall(){
        Shape shape=currentShape;
         if(shape==null)
             return -1;
        
        //La pièce ne peut plus chuter
        if(!canFall(shape)){
            return 1;
        }
        
        shape.y++;
        
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){                                      
                grid[shape.y+i][shape.x+j].setBrick(shape.getBricks()[i][j]);
                grid[shape.y+i][shape.x+j].setShape(shape);
            }
        }
        
        return 0;
    }
    
    //Teste si la piece peut tomber
    public boolean canFall(Shape shape){
        int ligne=getLowestLine(shape);
        if(ligne==-1) return false;
        
        shape.printShape();
        for(int i=0;i<4;++i){
            if(shape.y+ligne+1>=20){
                System.out.println("Hors tableau");
                return false;
            }
            else if(!grid[shape.y+ligne+1][shape.x+i].isEmpty() && shape.getBricks()[ligne][i]!=null){
                System.out.println("Piece en dessous");
                return false;
            }
                
        }
        return true;
    }
    
    //retourne la ligne d'une shape la plus basse
    public int getLowestLine(Shape shape){
        for(int i=3;i>=0;--i){
            for(int j=0;j<4;++j){
                if(shape.getBricks()[i][j]!=null)
                    return i;
            }
        }
        return -1;
    }
}
