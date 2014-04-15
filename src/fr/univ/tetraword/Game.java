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
    Shape nextShape;
    private int score;
    private int level;
    Dictionary dictionary;
    private Box grid[][];
    private Box nextgrid[][];
    JPanel gridInterface;
    JPanel nextInterface;
    boolean ready;
    
    public Game(){
        ready=true;
        score=0;
        level=1;
        currentShape=null;

        grid=new Box[20][10];
        nextgrid=new Box[4][4];
        
        // Initialisation de grid
        gridInterface = new JPanel(new GridLayout(20,10));
        nextInterface = new JPanel(new GridLayout(4,4));
       
        Border whiteline = BorderFactory.createLineBorder(Color.WHITE,1);
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                nextgrid[i][j]=new Box();
                nextgrid[i][j].setBorder(whiteline);
                nextInterface.add(nextgrid[i][j]);
            }
        }

        // Initialisation de gridInterface
        
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
    
    public void rafraichirNextShape(){
        nextInterface.repaint();
        for (int i=0; i<4;++i){
                for (int j=0; j<4; ++j){
                       nextgrid[i][j].setShapeBrick(nextShape, nextShape.getBricks()[i][j]);
                       nextgrid[i][j].rafraichir();
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
    
    public JPanel getNextInterface(){
        return nextInterface;
    }
    
    @Override
    public void run(){
        boolean end=false;
        
        //Boucle principale
        try {
        nextShape=Shape.getRandomShape();
        newShapeInGame();
                            
        while(!end){

                   rafraichir();
  
                if(shapeFall(currentShape)==1){
                    verifLigne();
                    newShapeInGame();
                    score+=2;
                }
                Thread.sleep(1000);
                //currentShape.printShape();
                
            
                //Mis a jour de l'affichage
        }
        } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void eraseLine(int ligne){
        for(int i=ligne;i>0;--i){
            for(int j=0;j<10;++j){
                grid[i][j].setShapeBrick(grid[i-1][j].getShape(),grid[i-1][j].getBrick());
                grid[i-1][j].setShapeBrick(null,null);
            }    
        }
    }
    
    //Verif ligne
    public int verifLigne(){
        int ligne=-1;
        boolean end;
        do {
            int tmp;
            for(int i=19;i>=0;--i){
                end=true;
                tmp=i;
                for(int j=0;j<10;++j){
                    
                    if(grid[i][j].isEmpty()){
                        tmp=-1;                    
                    }
                    else{                      
                        end=false;
                    }
                }
                ligne=tmp;
                
                if(end || ligne!=-1)
                    break;
            }
            if(ligne!=-1)
                score+=10;
            
            eraseLine(ligne);
        } while(ligne!=-1);
        System.out.println();
        return ligne;
    }
    
    public void newShapeInGame(){
        
        currentShape=nextShape;
        nextShape=Shape.getRandomShape();
        rafraichirNextShape();
        
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
                    if(grid[currentShape.y+i][currentShape.x+j].getShape()==currentShape || grid[currentShape.y+i][currentShape.x+j].isEmpty()){
                        grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
                        grid[currentShape.y+i][currentShape.x+j+1].setShapeBrick(null,null);                    
                    }
                }
            }
            
        }
        //Droite
        else {
           
            currentShape.x++;
            
            for(int i=0;i<=currentShape.height;++i){
                for(int j=currentShape.width;j>=0;--j){                    
                    if(grid[currentShape.y+i][currentShape.x+j].getShape()==currentShape || grid[currentShape.y+i][currentShape.x+j].isEmpty()){
                        grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
                        grid[currentShape.y+i][currentShape.x+j-1].setShapeBrick(null,null);                    
                    }
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
    
    public boolean canRotate(){
        if(currentShape.x+currentShape.height>=10 || currentShape.y+currentShape.width>=20)
            return false;
        for(int i=0;i<=currentShape.width;++i){
            for(int j=0;j<=currentShape.height;++j){
                if(!grid[currentShape.y+i][currentShape.x+j].isEmpty() && grid[currentShape.y+i][currentShape.x+j].getShape()!=currentShape)
                    return false;
            }
        }
        return true;
    }
    
    public void rotate(){
        if(!canRotate()) return;
        
        currentShape.rotateShape();
        currentShape.refreshShape();
        
        for(int i=0;i<currentShape.getType().getTaille();++i){
            for(int j=0;j<currentShape.getType().getTaille();++j){                
                
                grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
            }
        }
        
    }
    
    
    public int shapeFall(Shape shape){
        
         if(shape==null)
             return -1;
        
        //La pièce ne peut plus chuter
        if(!canFall(shape)){
            return 1;
        }
        
        shape.y++;        
     
      
        for(int i=0;i<=shape.height;++i){
            for(int j=0;j<=shape.width;++j){           
                if(grid[shape.y+i][shape.x+j].getShape()==shape || grid[shape.y+i][shape.x+j].isEmpty())                     
                    grid[shape.y+i][shape.x+j].setShapeBrick(shape,shape.getBricks()[i][j]);                
            }
        }
        
        for(int i=0;i<=shape.width;++i){
            if(grid[shape.y-1][shape.x+i].getShape()==shape)
                 grid[shape.y-1][shape.x+i].setShapeBrick(null,null);
        }
        return 0;
    }
    
    //Teste si la piece peut tomber
    public boolean canFall(Shape shape){
       
        if(shape.y+shape.height+1>=20){
                //System.out.println("Hors tableau");
                return false;
            }
        
        for(int i=0;i<shape.height;++i){
            for(int j=0;j<=shape.width;++j)
                if(!grid[shape.y+1+i][shape.x+j].isEmpty() && shape.getBricks()[i][j]!=null && shape.getBricks()[i+1][j]==null){
                  //  System.out.println("Piece dedans");
                    return false;
                }
                
        }
        
        for(int i=0;i<=shape.width;++i)
                if(!grid[shape.y+shape.height+1][shape.x+i].isEmpty() && shape.getBricks()[shape.height][i]!=null){
                    //System.out.println("Piece dessous");
                    return false;
                }
        
        return true;
    }
    public static void saveGame(Game[] savedGame)
	{
		try 
		{
			ObjectOutputStream save=new ObjectOutputStream(new FileOutputStream("SavedGame.dat"));
			save.writeObject(savedGame);
			save.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}

	public static Game[] readGame()
	{ 
		Game[] game=new Game[0];
		try 
		{
			ObjectInputStream save=new ObjectInputStream(new FileInputStream("SavedGame.dat"));
			game=(Game[]) save.readObject();
		} catch (FileNotFoundException e) {} 
		catch (EOFException e){}
		catch (IOException e) {e.printStackTrace();} 
		catch (ClassNotFoundException e) {e.printStackTrace();}
		return game;
	}
    
}
