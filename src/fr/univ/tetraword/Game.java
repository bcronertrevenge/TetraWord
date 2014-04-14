/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import java.awt.Color;
import java.awt.GridLayout;
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
    JPanel gridInterface;
    
    public Game(){
        score=0;
        level=1;
        currentShape=null;

        grid=new Box[20][10];
        gridInterface = new JPanel(new GridLayout(20,10));
        
       // Initialisation de grid
        

        // Initialisation de gridInterface
        Border whiteline = BorderFactory.createLineBorder(Color.WHITE,1);
        for (int i=0;i<20;++i){
            for (int j=0;j<10;++j){
                grid[i][j]=new Box();
                                
                grid[i][j].setBorder(whiteline);
                gridInterface.add(grid[i][j]);
            } 
        }
    }
    
    public void rafraichir(){
        gridInterface.repaint();
        for (int i=0; i<20;++i){
                for (int j=0; j<10; ++j){
                       if(grid[i][j]!=null){   
                                grid[i][j].rafraichir();
                       }      
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
    
    public JPanel getGridInterface(){
        return gridInterface;
    }
    
    @Override
    public void run(){
        boolean end=false;
        
        //Boucle principale
        try {
        newShapeInGame();
                            
        while(!end){
            if(shapeFall()==1){                                         
                    newShapeInGame();
            }
                rafraichir();
                           
                Thread.sleep(1000);
                currentShape.printShape();
                
            
                //Mis a jour de l'affichage
        }
        } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void newShapeInGame(){
        
        currentShape=Shape.getRandomShape();
        for(int i=0;i<=currentShape.height;++i){
                for(int j=0;j<=currentShape.width;++j){
                    if(currentShape.getBricks()[i][j]!=null)
                        grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
                }
        }
    }
    
    //Bouge la piece a gauche(-1) ou a droite(1)
    public void moveShapeAside(int sens){
        
        if(!canMoveAside(sens))
            return;
        
        //Gauche
        if(sens < 0){
            currentShape.x--;
            
            for(int i=0;i<=currentShape.height;++i){
                for(int j=0;j<=currentShape.width;++j){
                    grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
                    grid[currentShape.y+i][currentShape.x+j+1].setShapeBrick(null,null);                    
                }
            }
            
        }
        //Droite
        else {
           
            currentShape.x++;
            
            for(int i=0;i<=currentShape.height;++i){
                for(int j=currentShape.width;j>=0;--j){                    
                    grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
                    grid[currentShape.y+i][currentShape.x+j-1].setShapeBrick(null,null);                    
                }
            }
            
        }
    }
    
    //Teste si le mouvement est possible
    public boolean canMoveAside(int sens){          

          //Mouvement a gauche
          if(sens < 0){
              if(currentShape.x-1<0){                 
                  return false;
              }
              else {
                  for(int i=0;i<=currentShape.height;++i){
                      if(!grid[currentShape.y+i][currentShape.x].isEmpty() && !grid[currentShape.y+i][currentShape.x-1].isEmpty())
                          return false;
                  }
              }
          }
          //Mouvement à droite
          else {
                         
              if(currentShape.x+currentShape.width+1>=10){
                  return false;
              }
              else {
                  for(int i=0;i<=currentShape.height;++i){
                      if(!grid[currentShape.y+i][currentShape.x+currentShape.width].isEmpty() && !grid[currentShape.y+i][currentShape.x+currentShape.width+1].isEmpty())
                          return false;
                  }
              }
          }
          return true;
    }
    
    public void rotate(){
        currentShape.rotateShape();
    }
    
    
    public int shapeFall(){
        
         if(currentShape==null)
             return -1;
        
        //La pièce ne peut plus chuter
        if(!canFall(currentShape)){
            return 1;
        }
        
        currentShape.y++;        
     
      
        for(int i=0;i<=currentShape.height;++i){
            for(int j=0;j<=currentShape.width;++j){                                      
                grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
            }
        }
        
        for(int i=0;i<=currentShape.width;++i)
            grid[currentShape.y-1][currentShape.x+i].setShapeBrick(null,null);
        return 0;
    }
    
    //Teste si la piece peut tomber
    public boolean canFall(Shape shape){
       
        for(int i=0;i<=shape.width;++i){
            if(shape.y+shape.height+1>=20){
                System.out.println("Hors tableau");
                return false;
            }
            else if(!grid[shape.y+shape.height+1][shape.x+i].isEmpty() && shape.getBricks()[shape.height][i]!=null){
                System.out.println("Piece en dessous");
                return false;
            }
                
        }
        return true;
    }
    
    
}
