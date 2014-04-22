/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import fr.univ.graphicinterface.JWelcomeButton;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author bruno
 */
public class Game extends Thread implements ActionListener{
    Shape currentShape;
    Shape nextShape;
    public double score;
    private int level;
    Dictionary dictionary;
    private final Box grid[][];
    private final Box nextgrid[][];
    JPanel gridInterface;
    JPanel nextInterface;
    private int mode; //0 : mode Tetris, 1 : mode Anagramme, 2 : Worddle
    JFrame window;
    String mot;
    int anagLine;
    Vector<Integer> worddleStartX;
    Vector<Integer> worddleStartY; // Case Worddle sur lesquels on peut demarrer
    int worddleBoxPosX,worddleBoxPosY;
    boolean multi;
    long worddleTime, worddleReload, worddleLast, anagTime, fallTime;
    int anagLettres;
    Vector<JWelcomeButton> Buttons;
    IA intelligence;
    
    public Game(JFrame window, Dictionary dictionary, boolean multi, Vector<JWelcomeButton> Buttons, boolean ia){            
        if(ia)
            intelligence=new IA(this);
        
        //Difficulte
        worddleTime=40000; //Le temps en mode Worddle
        worddleReload=20000; //Le temps de rechargement de Worddle
        anagTime=30000; //Le temps en mode anagramme
        fallTime=1000; //Le temps de chute des pièces
        anagLettres=3; //Le nombre de lettres minimum en anagramme
        worddleLast=0; //Le temps du dernier worddle
        worddleBoxPosX=-1;
        worddleBoxPosY=-1;
        worddleStartX=new Vector<Integer>();
        worddleStartY=new Vector<Integer>();
        
        this.Buttons=Buttons;
        this.multi=multi;
        anagLine=-1;
        score=0;
        level=1;
        currentShape=null;
        mode = 0;
        grid=new Box[20][10];
        nextgrid=new Box[4][4];
        this.window=window;
        mot="";
        this.dictionary=dictionary;
        
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
                grid[i][j].addActionListener(this); 
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
        
        Buttons.get(0).setText(String.valueOf(level));
        Buttons.get(1).setText(String.valueOf((int)score));
        
        for(JWelcomeButton b:Buttons)
            b.repaint();
        
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
    
    public int getLevel(){
        return level;
    }
    
    public Box[][] getGrid(){
        return grid;
    }
    
    public int getMode(){
        return mode;
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
        long beginTime=0;
        int nbShape=1;
        boolean modif=false;
        while(!end){

                   
                if(mode == 0){
                    if(shapeFall(currentShape)==1){
                        nbShape++;
                        modif=false;
                        beginTime=verifLigne();
                        if(mode == 0){
                            end=newShapeInGame();                    
                            
                            score+=2;
                            if(nbShape%10==0){
                                levelUp();
                            }
                            if(end) break;
                        }
                    }
                    else{
                        if(intelligence!=null)
                            intelligence.play();
                        rafraichir();
                        Thread.sleep(fallTime);
                    }
                }else if (mode == 1){
                        if(intelligence!=null)
                             intelligence.play();
                        rafraichir();
                    if(System.currentTimeMillis()-beginTime>anagTime){
                        mode = 0;
                        unSelected(-1);
                        clean();
                        window.requestFocusInWindow();
                        mot="";
                        anagLine=-1;
                        end=newShapeInGame();
                    }
                }else if(mode == 2){
                        if(intelligence!=null)
                            intelligence.play();
                        rafraichir();
                    if(System.currentTimeMillis()-worddleLast>worddleTime){
                        mode=0;
                        suppressionWorddle();
                        clean();
                        clearBox();
                        window.requestFocusInWindow();
                        mot="";
                        worddleLast=System.currentTimeMillis();
                        //beginTime=verifLigne();
                        System.out.println("Worddle Over");
                    }
                }
                
              if(nbShape%3==0 && !modif){
                  modif=true;
                  System.out.println("Apparition Modifier");
                  addModifier();
              }
                  
        }
        System.out.println("GAME OVER");
        } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    public void suppressionWorddle(){
        int fall;
        for(int i=19;i>=0;--i){
            for(int j=0;j<10;++j){
                if(!grid[i][j].isEmpty() && grid[i][j].isSuppressed){
                    score+=50/grid[i][j].getBrick().rarity;
                    grid[i][j].setShapeBrick(null, null);
                    
                }
            }
        }
        
        boolean quit;
        for(int i=19;i>=0;--i){
            quit=true;
            for(int j=0;j<10;++j){
                if(!grid[i][j].isEmpty()){
                    grid[i][j].getShape().refreshShape();
                    quit=false;           
                    fall=0;
                    while(fall!=1 && fall!=-1){
                        fall=shapeFall(grid[i][j].getShape());
                    }
                }
            }
            if(quit) break;
        }
    }
    
    public void worddle(){
        if(mode == 1 || mode == 2 || System.currentTimeMillis()-worddleLast<worddleReload){
            return;
        }    
        
        boolean end;
        int ligne;
        for(ligne=19;ligne>=0;--ligne){
            end=true;
            for(int j=0;j<10;++j){
                if(!grid[ligne][j].isEmpty() && grid[ligne][j].getShape()!=currentShape)
                    end=false;
            }
            if(end) break;
        }
        
        if(ligne==19)
            return;
        
        Box firstBox = null;
        int y=0;
        int x=(int)(Math.random() * 10);
        do{
            y= (int)((Math.random() * (20-ligne)) + ligne);
            firstBox=grid[y][x];
        }while(firstBox.isEmpty());
        
        firstBox.isStart=true;
        firstBox.isSelected=true;
        worddleStartX.add(x);
        worddleStartY.add(y);
        worddleBoxPosX=x;
        worddleBoxPosY=y;
        mode=2;
        worddleLast=System.currentTimeMillis();
        mot+=firstBox.getBrick().lettre;
    }
    
    public void clean(){
           whiteOut();
           worddleStartX.clear();
           worddleStartY.clear();
    }
    
    public void clearBox(){
            for(int i=0;i<20;++i){
                for(int j=0;j<10;++j){
                    grid[i][j].isSelected=false;
                    grid[i][j].isStart=false;
                    grid[i][j].isSuppressed=false;
                }
            }
    }
    
    public void unSelected(int ligne){
        if(ligne < 0){
            boolean quit;
            for(int i=19;i>=0;--i){
                quit=true;
                for(int j=0;j<10;++j){
                    grid[i][j].isSelected=false;
                    if(!grid[i][j].isEmpty())
                        quit=false;
                }
                if(quit) break;
            }
        }
        else {
            for(int i=0;i<10;++i)
                grid[ligne][i].isSelected=false;
        }
    }
    
    public void whiteOut(){
        Border whiteline = BorderFactory.createLineBorder(Color.WHITE,1);
        for(int i=0;i<20;++i){
            for(int j=0;j<10;++j){
                grid[i][j].setBorder(whiteline);
            }
        }
    }
    
    public long anagramme(int ligne){
        
        Border yellowline = BorderFactory.createLineBorder(Color.YELLOW,1);
            for(int j=0;j<10;++j){
                grid[ligne][j].setBorder(yellowline);
            }
        anagLine=ligne;
        mode = 1;
        return System.currentTimeMillis(); 
        
    }
    
    public void validate(){
        if(mode == 1){
            mot=mot.toLowerCase();
            
            if(mot.length() > anagLettres && dictionary.line.contains(mot)){
                eraseLine(anagLine);
                unSelected(anagLine);
                for(int i=0;i<mot.length();++i){
                    score+=50/Shape.getRarityFromLetter(mot.toUpperCase().charAt(i));
                }
                clean();
                mode = 0;
                anagLine=-1;
            }
            else{
                unSelected(anagLine);
            }
            mot="";
        }
        else if(mode == 2){
            mot=mot.toLowerCase();
            if(dictionary.line.contains(mot)){                
                boolean quit;
                for(int i=19;i>=0;--i){
                    quit=true;
                    for(int j=0;j<10;++j){
                        if(grid[i][j].isSelected){
                            grid[i][j].isSelected=false;
                            grid[i][j].isSuppressed=true;
                            grid[i][j].isStart=true;
                        }
                        if(!grid[i][j].isEmpty())
                            quit=false;
                    }
                    if(quit) break;
                }
            }
            else {
                for(int i=0;i<20;++i){
                    for(int j=0;j<10;++j){
                        if(grid[i][j].isSelected)
                            grid[i][j].isSelected=false;
                    }
                }
            }
            mot="";
            worddleBoxPosX=worddleStartX.get(0);
            worddleBoxPosY=worddleStartY.get(0);
            grid[worddleBoxPosY][worddleBoxPosX].isSelected=true;
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
    public long verifLigne(){
        int ligne=-1;
        boolean end;        
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
            if(ligne!=-1){
                return anagramme(ligne);
            }

        return -1;
    }
    
    public boolean newShapeInGame(){
        
        currentShape=nextShape;
        nextShape=Shape.getRandomShape();
        rafraichirNextShape();
        
        for(int i=0;i<=currentShape.height;++i){
                for(int j=0;j<=currentShape.width;++j){
                    if(!grid[currentShape.y+i][currentShape.x+j].isEmpty()){
                        System.out.println();
                        return true;
                    }
                    grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
                }
        }
        
        return false;
    }
    
    //Bouge la piece a gauche(-1) ou a droite(1)
    public void moveShapeAside(int sens){
        
        if(mode == 1)
            return;
        else if(mode == 2){ //Worddle Mod
            if(worddleBoxPosX < 0 || worddleBoxPosX >= 10 || (sens < 0 && worddleBoxPosX == 0) || (sens > 0 && worddleBoxPosX == 9))
                return;
            //Droite
            else if(sens > 0 && !grid[worddleBoxPosY][worddleBoxPosX+1].isEmpty()){
                if(grid[worddleBoxPosY][worddleBoxPosX+1].isStart){
                    worddleBoxPosX++;
                    unSelected(-1);
                    grid[worddleBoxPosY][worddleBoxPosX].isSelected=true;
                    mot="";
                    mot+=grid[worddleBoxPosY][worddleBoxPosX].getBrick().lettre;
                }
                else if(!grid[worddleBoxPosY][worddleBoxPosX+1].isSelected){
                    worddleBoxPosX++;
                    grid[worddleBoxPosY][worddleBoxPosX].isSelected=true;
                    mot+=grid[worddleBoxPosY][worddleBoxPosX].getBrick().lettre;
                }
            }
            //Gauche
            else if(sens < 0 && !grid[worddleBoxPosY][worddleBoxPosX-1].isEmpty()){
                if(grid[worddleBoxPosY][worddleBoxPosX-1].isStart){
                    worddleBoxPosX--;
                    unSelected(-1);
                    grid[worddleBoxPosY][worddleBoxPosX].isSelected=true;
                    mot="";
                    mot+=grid[worddleBoxPosY][worddleBoxPosX].getBrick().lettre;
                }
                else if(!grid[worddleBoxPosY][worddleBoxPosX-1].isSelected){
                    worddleBoxPosX--;
                    grid[worddleBoxPosY][worddleBoxPosX].isSelected=true;
                    mot+=grid[worddleBoxPosY][worddleBoxPosX].getBrick().lettre;
                }
            }
            return;
        }
        
        else if(!canMoveAside(sens))
            return;
        
        //Gauche
        if(sens < 0){
            currentShape.x--;
                
            for(int i=0;i<=currentShape.height;++i){
                for(int j=0;j<=currentShape.width;++j){
                    if(grid[currentShape.y+i][currentShape.x+j].getShape()==currentShape || grid[currentShape.y+i][currentShape.x+j].isEmpty()){
                        if(grid[currentShape.y+i][currentShape.x+j].getModifier()!=null && currentShape.getBricks()[i][j]!=null){
                            grid[currentShape.y+i][currentShape.x+j].getModifier().activate();
                            grid[currentShape.y+i][currentShape.x+j].setModifier(null);
                        }
                        grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
                        if(grid[currentShape.y+i][currentShape.x+j+1].getShape()==currentShape)
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
                        if(grid[currentShape.y+i][currentShape.x+j].getModifier()!=null && currentShape.getBricks()[i][j]!=null){
                            grid[currentShape.y+i][currentShape.x+j].getModifier().activate();
                            grid[currentShape.y+i][currentShape.x+j].setModifier(null);
                        }
                        grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
                        if(grid[currentShape.y+i][currentShape.x+j-1].getShape()==currentShape)
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
    
    public void rotateUp(){
        
        if(mode == 1)
            return;
        else if(mode == 2){
            if(worddleBoxPosY < 1 || worddleBoxPosY >= 20)
                return;
            else if(!grid[worddleBoxPosY-1][worddleBoxPosX].isEmpty()){
                if(grid[worddleBoxPosY-1][worddleBoxPosX].isStart){
                    worddleBoxPosY--;
                    unSelected(-1);
                    grid[worddleBoxPosY][worddleBoxPosX].isSelected=true;
                    mot="";
                    mot+=grid[worddleBoxPosY][worddleBoxPosX].getBrick().lettre;
                }
                else if(!grid[worddleBoxPosY-1][worddleBoxPosX].isSelected){
                    worddleBoxPosY--;
                    grid[worddleBoxPosY][worddleBoxPosX].isSelected=true;
                    mot+=grid[worddleBoxPosY][worddleBoxPosX].getBrick().lettre;
                }
            }
            return;
        }
                   
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
        
        if(mode == 1)
            return -1;
        else if(mode == 2){
            if(worddleBoxPosY < 0 || worddleBoxPosY >= 19)
                return -1;
            
            else if(!grid[worddleBoxPosY+1][worddleBoxPosX].isEmpty()){
                 if(grid[worddleBoxPosY+1][worddleBoxPosX].isStart){
                    worddleBoxPosY++;
                    unSelected(-1);
                    grid[worddleBoxPosY][worddleBoxPosX].isSelected=true;
                    mot="";
                    mot+=grid[worddleBoxPosY][worddleBoxPosX].getBrick().lettre;
                }
                 else if(!grid[worddleBoxPosY+1][worddleBoxPosX].isSelected){
                    worddleBoxPosY++;
                    grid[worddleBoxPosY][worddleBoxPosX].isSelected=true;
                    mot+=grid[worddleBoxPosY][worddleBoxPosX].getBrick().lettre;
                }
            }
            return -1;
        }
        
         if(shape==null)
             return -1;
        
        //La pièce ne peut plus chuter
        if(!canFall(shape)){
            return 1;
        }
        
        shape.y++;        
     
      boolean terminate=false;
        for(int i=0;i<=shape.height;++i){
            for(int j=0;j<=shape.width;++j){           
                if(grid[shape.y+i][shape.x+j].getShape()==shape || grid[shape.y+i][shape.x+j].isEmpty()){
                    if(grid[shape.y+i][shape.x+j].getModifier()!=null && shape.getBricks()[i][j]!=null){
                        terminate=grid[shape.y+i][shape.x+j].getModifier().activate();
                        grid[shape.y+i][shape.x+j].setModifier(null);
                        
                    }
                    if(!terminate) grid[shape.y+i][shape.x+j].setShapeBrick(shape,shape.getBricks()[i][j]);             
                }
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
        
        for(int i=0;i<=shape.width;++i){
                if(!grid[shape.y+shape.height+1][shape.x+i].isEmpty() && shape.getBricks()[shape.height][i]!=null){
                    //System.out.println("Piece dessous");
                    return false;
                }
        }
        return true;
    }
    public static void saveGame(Game[] savedGame) throws IOException
	{
		try 
		{
			ObjectOutputStream save=new ObjectOutputStream(new FileOutputStream("SavedGame.dat"));
			save.writeObject(savedGame);
			save.close();
		} catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
		catch (IOException e) {
                    e.printStackTrace();
                }
	}

	public static Game[] readGame()
	{ 
		Game[] game=new Game[0];
		try 
		{
			ObjectInputStream save=new ObjectInputStream(new FileInputStream("SavedGame.dat"));
			game=(Game[]) save.readObject();
		} catch (FileNotFoundException e) {} 
		catch (IOException e) {e.printStackTrace();} 
		catch (ClassNotFoundException e) {e.printStackTrace();}
		return game;
	}
    
    public void actionPerformed(java.awt.event.ActionEvent evt) {
             if(evt.getSource() instanceof Box){
                 if(mode==1){
                    Box box=(Box)evt.getSource();
                    if(!box.isSelected){
                        for(int i=0;i<10;++i){
                            if(grid[anagLine][i]==box){
                                mot+=box.getBrick().lettre;
                                box.isSelected=true;
                                break;
                            }
                        }
                        
                    }
                    else {
                        unSelected(anagLine);
                    }
                 }
             }
             window.requestFocusInWindow();
    }
    
    public void levelUp(){
        
        level++;
        worddleTime=40000-level*50; //Le temps en mode Worddle
        worddleReload=20000+level*50; //Le temps de rechargement de Worddle
        anagTime=30000-level*50; //Le temps en mode anagramme
        fallTime=1000-level*5; //Le temps de chute des pièces
        if(anagLettres<5 && level%2==0){
            anagLettres=level; //Le nombre de lettres minimum en anagramme
        }
    }
    
    public void addModifier(){
        int x,y;
        do {
            x=(int)(Math.random() * 10);
            y=(int)(Math.random() * 15);
        }while(!grid[y+5][x].isEmpty());
        
        Modifier modifier=new Modifier(multi,this);
        grid[y+5][x].setModifier(modifier);
    }
}
