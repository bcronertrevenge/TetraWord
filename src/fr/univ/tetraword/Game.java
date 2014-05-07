/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import com.sun.corba.se.impl.orbutil.ObjectWriter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import static java.lang.System.exit;
import static java.lang.System.out;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
    * Game est la classe représentant un jeu de Tetra Word
 **/
public class Game extends Thread implements ActionListener{
    Shape currentShape;
    Shape nextShape;
    public double score;
    private int level;
    Dictionary dictionary;
    private final Box grid[][];
    private final Box nextgrid[][];
    gridInterfaceReversable gridInterface;
    JPanel nextInterface;
    private int mode; //0 : mode Tetris, 1 : mode Anagramme, 2 : Worddle
    JFrame window;
    String mot;
    int anagLine;
    Vector<Integer> worddleStartX;
    Vector<Integer> worddleStartY; // Case Worddle sur lesquels on peut demarrer
    int worddleBoxPosX,worddleBoxPosY;
    long worddleTime, worddleReload, worddleLast, anagTime, fallTime;
    int anagLettres;
    HashMap<String,JButton> composants;
    IA intelligence;
    boolean pause;
 
    Game other;
    boolean gameOver;

/**
    * Constructeur d'un Game
    * @param window
    * la fenêtre qui contiendra le jeu
    * @param dictionary
    * le dictionnaire qui vérifie l'existence des mots
    * @param composants
    * les JButton à mettre à jour (saisie, score, niveau)
    * @param ia
    * Si on a besoin de l'intelligence artificielle
 **/
    public Game(JFrame window, Dictionary dictionary,  HashMap<String,JButton> composants, boolean ia){  
        this.other=null;
        
        if(composants==null)
            exit(1);
        if(ia)
            intelligence=new IA(this);
        pause=false;
        //Difficulte
        worddleTime=40000; //Le temps en mode Worddle
        worddleReload=20000; //Le temps de rechargement de Worddle
        anagTime=30000; //Le temps en mode anagramme
        fallTime=1000; //Le temps de chute des pièces
        anagLettres=2; //Le nombre de lettres minimum en anagramme
        worddleLast=0; //Le temps du dernier worddle
        worddleBoxPosX=-1;
        worddleBoxPosY=-1;
        worddleStartX=new Vector<Integer>();
        worddleStartY=new Vector<Integer>();
        
        this.composants=composants;
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
        gameOver=false;
        
        // Initialisation de grid
        gridInterface = new gridInterfaceReversable(new GridLayout(20,10));
        nextInterface = new JPanel(new GridLayout(4,4));
    }

/**
    * Permet de connaître l'autre jeu s'il y en a un
    * @param other
    * Le deuxième jeu qu'on veut connaître
 **/
    public void setOther(Game other){
        this.other=other;
    }

/**
    * Permet d'initialiser le jeu
 **/
    public void init(){
                // Initialisation de gridInterface
        Border whiteline = BorderFactory.createLineBorder(Color.WHITE,1);
        
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                nextgrid[i][j]=new Box();
                if(nextgrid[i][j]==null)
                    exit(1);
                nextgrid[i][j].setBorder(whiteline);
                nextInterface.add(nextgrid[i][j]);
            }
        }
        for (int i=0;i<20;++i){
            for (int j=0;j<10;++j){
                grid[i][j]=new Box();
                if(grid[i][j]==null)                
                    exit(1);
                grid[i][j].setBorder(whiteline);
                gridInterface.add(grid[i][j]);
                grid[i][j].addActionListener(this); 
            } 
        }
        
    }

/**
    * Permet de mettre à jour les données du jeu
 **/
    public void rafraichir(){
        
            for (int i=0; i<20;++i){
               for (int j=0; j<10; ++j){
                               if(grid[i][j]!=null){   
                                        grid[i][j].rafraichir();
                               }      
                 }
            }
        gridInterface.repaint();   
        
        if(composants.containsKey("Niveau")){
            composants.get("Niveau").setForeground(Color.white);
            composants.get("Niveau").setText(String.valueOf(level));
            composants.get("Niveau").repaint();
        }
        if(composants.containsKey("Score")){
            composants.get("Score").setForeground(Color.white);
            composants.get("Score").setText(String.valueOf((int)score));
            composants.get("Score").repaint();
        }
        if(composants.containsKey("Saisie")){
            composants.get("Saisie").setForeground(Color.white);
            composants.get("Saisie").setText(String.valueOf(mot));
            composants.get("Saisie").repaint();
        }
        if(composants.containsKey("Worddle")){
            if(System.currentTimeMillis()-worddleLast>=worddleReload){
                composants.get("Worddle").setBackground(new Color(49,177,19));
                composants.get("Worddle").setFocusPainted(false);
                composants.get("Worddle").setText("");
                composants.get("Worddle").repaint();
            }
            else if(mode==2){
                composants.get("Worddle").setBackground(new Color(221,128,17));
                composants.get("Worddle").setFocusPainted(false);
                composants.get("Worddle").setText("");
                composants.get("Worddle").repaint();
            }
            else{
                composants.get("Worddle").setBackground(new Color(209,7,7));
                composants.get("Worddle").setFocusPainted(false);
                composants.get("Worddle").setText("");
                composants.get("Worddle").repaint();
            }
        }
    }

/**
    * Permet de mettre à jour la shape suivante
 **/
    public void rafraichirNextShape(){
        nextInterface.repaint();
        for (int i=0; i<4;++i){
                for (int j=0; j<4; ++j){
                       nextgrid[i][j].setShapeBrick(nextShape, nextShape.getBricks()[i][j]);
                       nextgrid[i][j].rafraichir();
               }
            }
    }

/**
    * Permet de récupérer le niveau du jeu
 **/
    public int getLevel(){
        return level;
    }

/**
    * Permet de récupérer la grille du jeu
 **/
    public Box[][] getGrid(){
        return grid;
    }

/**
    * Permet de récupérer le mode du jeu (Tetris, Anagramme, Worddle)
 **/
    public int getMode(){
        return mode;
    }
    
/**
    * Permet de récupérer le JPanel de la grille
 **/   
    public gridInterfaceReversable getGridInterface(){
        return gridInterface;
    }

/**
    * Permet de récupérer le JPanel de la prochaine pièce
 **/
    public JPanel getNextInterface(){
        return nextInterface;
    }

/**
    * Permet de lancer le jeu
 **/
    @Override
    public void run(){
        
        
        //Boucle principale
        try {
        init();
        nextShape=Shape.getRandomShape();
        newShapeInGame();
        long beginTime=0;
        int nbShape=1;
        boolean modif=false;
                
        while(!gameOver){
     
                if(pause){
                    
                }
                else if(mode == 0){
                    if(shapeFall(currentShape,true)==1){
                        nbShape++;
                        modif=false;
                        beginTime=verifLigne();
                        if(mode == 0){
                            gameOver=newShapeInGame();                    
                            
                            score+=2;
                            if(nbShape%10==0){
                                levelUp();
                            }
                            if(gameOver) break;
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
                        gameOver=newShapeInGame();
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
                        System.out.println("Worddle Over");
                    }
                }
                
              if(nbShape%3==0 && !modif){
                  modif=true;
                  addModifier();
              }
                  
        }
        System.out.println("GAME OVER");
        JOptionPane.showMessageDialog(window,"GAME OVER !");

        } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

/**
    * Permet de supprimer les cases que l'on utilisé en mode Worddle
 **/
    public void suppressionWorddle(){
        int fall;
        boolean quit=true;
        for(int i=19;i>=0;--i){
            for(int j=0;j<10;++j){
                if(!grid[i][j].isEmpty() && grid[i][j].isSuppressed){
                    score+=50/grid[i][j].getBrick().rarity;
                    grid[i][j].setShapeBrick(null, null);
                    quit=false;
                }
            }
        }
        if(quit) return;
        
        for(int i=19;i>=0;--i){
            quit=true;
            for(int j=0;j<10;++j){
                if(!grid[i][j].isEmpty()){
                    grid[i][j].getShape().refreshShape();
                    quit=false;           
                    fall=0;
                    while(fall!=1 && fall!=-1){
                        fall=shapeFall(grid[i][j].getShape(),true);
                    }
                }
            }
            if(quit) break;
        }
    }

/**
    * Permet d'activer le mode Worddle
 **/
    public void worddle(){
        if(mode != 0 || System.currentTimeMillis()-worddleLast<worddleReload || pause){
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
        ligne++;
        
        Box firstBox = null;
        int y=0;
        int x=0;
        do{
            x=(int)(Math.random() * 10);
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

/**
    * Permet d'enlever les cases surlignées et de vider le Worddle
 **/
    public void clean(){
           whiteOut();
           worddleStartX.clear();
           worddleStartY.clear();
    }

/**
    * Permet d'enlever les propriétés des cases
 **/
    public void clearBox(){
            for(int i=0;i<20;++i){
                for(int j=0;j<10;++j){
                    grid[i][j].isSelected=false;
                    grid[i][j].isStart=false;
                    grid[i][j].isSuppressed=false;
                    grid[i][j].noWord=false;
                }
            }
    }

/**
    * Permet de désélectionner une ligne
    *@param ligne
    * numéro de la ligne que l'on souhaite déselectionner
 **/
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

/**
    * Permet de remettre les bordures en blanc une fois un mot validé
 **/
    public void whiteOut(){
        Border whiteline = BorderFactory.createLineBorder(Color.WHITE,1);
        for(int i=0;i<20;++i){
            for(int j=0;j<10;++j){
                grid[i][j].setBorder(whiteline);
            }
        }
    }

/**
    * Permet d'activer le mode anagramme
    * @param ligne
    * numéro de la ligne sur laquelle on active le mode anagramme
 **/
    public long anagramme(int ligne){
        
        Border yellowline = BorderFactory.createLineBorder(Color.YELLOW,1);
            for(int j=0;j<10;++j){
                    grid[ligne][j].setBorder(yellowline);
            }
        anagLine=ligne;
        mode = 1;
        return System.currentTimeMillis(); 
        
    }

/**
    * Permet de valider un mot
 **/
    public void validate(){
        if(mode == 1){
            mot=mot.toLowerCase();
            
            if(mot.equals("")){
                unSelected(-1);
                clean();
                mode=0;
                anagLine=-1;
                newShapeInGame();
            }
            else if(mot.length() > anagLettres && dictionary.line.contains(mot)){
                
                eraseLine(anagLine);
                unSelected(-1);
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
            if(mot.length()<=1){
                mode=0;
                suppressionWorddle();
                clean();
                clearBox();
                window.requestFocusInWindow();
                mot="";
                worddleLast=System.currentTimeMillis();
            }
            else if(dictionary.line.contains(mot)){                
                boolean quit;
                for(int i=19;i>=0;--i){
                    quit=true;
                    for(int j=0;j<10;++j){
                        if(grid[i][j].isSelected){
                            grid[i][j].isSelected=false;
                            grid[i][j].isSuppressed=true;
                            grid[i][j].isStart=true;
                            if(!worddleStartX.contains(j))
                                worddleStartX.add(j);
                            if(!worddleStartX.contains(i))
                                worddleStartY.add(i);
                        }
                        if(!grid[i][j].isEmpty())
                            quit=false;
                    }
                    if(quit) break;
                }
                
                worddleBoxPosX=worddleStartX.get(0);
                worddleBoxPosY=worddleStartY.get(0);
                mot="";
                mot+=grid[worddleBoxPosY][worddleBoxPosX].getBrick().lettre;
                grid[worddleBoxPosY][worddleBoxPosX].isSelected=true;
            
            }
            else {
                for(int i=0;i<20;++i){
                    for(int j=0;j<10;++j){
                        if(grid[i][j].isSelected)
                            grid[i][j].isSelected=false;
                    }
                }
                
                worddleBoxPosX=worddleStartX.get(0);
                worddleBoxPosY=worddleStartY.get(0);
                mot="";
                mot+=grid[worddleBoxPosY][worddleBoxPosX].getBrick().lettre;
                grid[worddleBoxPosY][worddleBoxPosX].isSelected=true;
            }
            

        }
    }
    
/**
    * Permet de supprimer une ligne
    * @param ligne
    * numéro de la ligne à supprimer
 **/    
    public void eraseLine(int ligne){
        for(int i=ligne;i>0;--i){
            for(int j=0;j<10;++j){
                grid[i][j].setShapeBrick(grid[i-1][j].getShape(),grid[i-1][j].getBrick());
                grid[i-1][j].setShapeBrick(null,null);
            }    
        }
    }
    
/**
    * Permet de vérifier si il y a une ligne complète dans la grille
 **/  
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

/**
    * Permet de créer une nouvelle forme dans le jeu
 **/ 
    public boolean newShapeInGame(){
        
        currentShape=nextShape;
        nextShape=Shape.getRandomShape();
        rafraichirNextShape();
        
        for(int i=0;i<=currentShape.height;++i){
                for(int j=0;j<=currentShape.width;++j){
                    if(!grid[currentShape.y+i][currentShape.x+j].isEmpty()){
                        return true;
                    }
                    grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
                }
        }
        
        return false;
    }
    
/**
    * Permet de bouger la piece a gauche(-1) ou a droite(1)
    * @param sens
    * le sens du mouvement
    * @param takeModifier
    * Si il doit prendre en compte les modificateurs ou pas
 **/
    public void moveShapeAside(int sens, boolean takeModifier){
        
        if(mode == 1 || pause)
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
        
        boolean terminate;
        //Gauche
        if(sens < 0){
            currentShape.x--;
                
            for(int i=0;i<=currentShape.height;++i){
                for(int j=0;j<=currentShape.width;++j){
                    if(grid[currentShape.y+i][currentShape.x+j].getShape()==currentShape || grid[currentShape.y+i][currentShape.x+j].isEmpty()){
                        if(grid[currentShape.y+i][currentShape.x+j].getModifier()!=null && currentShape.getBricks()[i][j]!=null && takeModifier){
                            Modifier m=grid[currentShape.y+i][currentShape.x+j].getModifier();
                            grid[currentShape.y+i][currentShape.x+j].setModifier(null);
                            terminate=m.activate(1);
                            if(terminate) return;
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
                        if(grid[currentShape.y+i][currentShape.x+j].getModifier()!=null && currentShape.getBricks()[i][j]!=null && takeModifier){
                            Modifier m=grid[currentShape.y+i][currentShape.x+j].getModifier();
                            grid[currentShape.y+i][currentShape.x+j].setModifier(null);
                            terminate=m.activate(2);
                            if(terminate) return;
                        }
                        grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
                        if(grid[currentShape.y+i][currentShape.x+j-1].getShape()==currentShape)
                            grid[currentShape.y+i][currentShape.x+j-1].setShapeBrick(null,null);                    
                    }
                }
            }
        }
    }
    
/**
    * Permet de tester si le mouvement est possible ou pas
 **/
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

/**
    * Permet de tester si la pièce courante peut rotater ou pas
 **/
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

/**
    * Permet de rotater la pièce
 **/
    public void rotateUp(){
        
        if(mode == 1 || pause)
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
      
    public int shapeFall(Shape shape, boolean modifierTake){
        
        if(mode == 1 || pause)
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
                    if(grid[shape.y+i][shape.x+j].getModifier()!=null && shape.getBricks()[i][j]!=null && modifierTake){
                        Modifier m=grid[shape.y+i][shape.x+j].getModifier();
                        grid[shape.y+i][shape.x+j].setModifier(null);
                        terminate=m.activate(0);
                        
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
                if(!grid[shape.y+1+i][shape.x+j].isEmpty() && shape.getBricks()[i][j]!=null && shape.getBricks()[i+1][j]==null && grid[shape.y+1+i][shape.x+j].getShape()!=shape){
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
    public static void saveGame(Vector<Game> savedGame) throws IOException
    {
	try{
            FileWriter fw = new FileWriter("game.txt", true);
            BufferedWriter output = new BufferedWriter(fw);
            output.write("CurrentShape afaire ");
            output.write("NextShape afaire ");
            output.write("Score "+savedGame.get(0).score);
            output.write("Level "+savedGame.get(0).level);
            output.flush();	
            output.close();
            
	}
            catch(IOException ioe){
		System.out.print("Erreur : ");
		ioe.printStackTrace();
            }
        /*ObjectOutputStream output = null;
          try {
            output = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File("game.txt"))));
            output.writeObject(savedGame.get(0));
            output.close();
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }*/

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
    
    public Box getInverse(Box b){
        for(int i=0;i<20;++i){
            for(int j=0;j<10;++j){
                if(grid[i][j]==b)
                    return grid[19-i][9-j];
            }
        }
        return null;
    }
    
    public void actionPerformed(java.awt.event.ActionEvent evt) {
             if(evt.getSource() instanceof Box){
                if(mode==1){
                   Box box;
                   if(gridInterface.reverse)
                       box=getInverse((Box)evt.getSource());
                   else
                       box=(Box)evt.getSource();
                   
                   if(box.isEmpty()) return;
                    
                    if(!box.isSelected){
                        for(int i=0;i<10;++i){
                            if(grid[anagLine][i]==box){
                                mot+=box.getBrick().lettre;
                                box.isSelected=true;
                                break;
                            }
                        }
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
        if(anagLettres<4 && level%3==0){
            anagLettres++; //Le nombre de lettres minimum en anagramme
        }
        if(composants.containsKey("Niveau")){
            composants.get("Niveau").setForeground(Color.white);
            composants.get("Niveau").setText(String.valueOf(level));
            composants.get("Niveau").repaint();
        }
    }
    
    public void addModifier(){
        int x,y;
        do {
            x=(int)(Math.random() * 10);
            y=(int)(Math.random() * 15);
        }while(!grid[y+5][x].isEmpty());
        
        Modifier modifier=new Modifier(other,this);
        grid[y+5][x].setModifier(modifier);
    }

    void setMode(int i) {
        mode=i;
    }
}
