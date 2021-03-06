package fr.univ.tetraword;

import com.sun.corba.se.impl.orbutil.ObjectWriter;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import static java.lang.System.exit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
    * Game est la classe représentant un jeu de Tetra Word
 **/
public class Game extends Thread implements ActionListener, MouseListener, Serializable {
    Shape currentShape;
    Shape nextShape;
    public double score;
    private int level;
    private final Box grid[][];
    private final Box nextgrid[][];
    gridInterfaceReversable gridInterface;
    gridInterfaceReversable nextInterface;
    private int mode; //0 : mode Tetris, 1 : mode Anagramme, 2 : Worddle
    String mot;
    int anagLine;
    Vector<Integer> worddleStartX;
    Vector<Integer> worddleStartY; // Case Worddle sur lesquels on peut demarrer
    int worddleBoxPosX,worddleBoxPosY;
    long worddleTime, worddleReload, worddleLast, anagTime, fallTime;
    int anagLettres;
    IA intelligence;
    boolean pause;
    long beginTime;
    Game other;
    boolean gameOver;
    Options options;
    private static final long serialVersionUID=1;
    boolean firstGame;
    private long decalage;
    int nbShape;
    boolean stop;
/**
    * Constructeur d'un Game
    * @param composants
    * les JButton à mettre à jour (saisie, score, niveau)
    * @param ia
    * Si le jeu courant est contrôlé par une intelligence artificielle
    * @param options
    * Les options gérées dans le menu
 **/
    public Game(HashMap<String,JComponent> composants, boolean ia, Options options){  
        stop=false;
        this.other=null;
        this.options=options;
        
        if(composants==null)
            exit(1);
        if(ia)
            intelligence=new IA(this);
        pause=false;
        //Difficulte
        worddleTime=options.worddleTime; //Le temps en mode Worddle
        worddleReload=options.worddleReload; //Le temps de rechargement de Worddle
        anagTime=options.anagTime; //Le temps en mode anagramme
        fallTime=options.fallTime; //Le temps de chute des pièces
        anagLettres=2; //Le nombre de lettres minimum en anagramme
        worddleLast=0; //Le temps du dernier worddle
        worddleBoxPosX=-1;
        worddleBoxPosY=-1;
        worddleStartX=new Vector<Integer>();
        worddleStartY=new Vector<Integer>();
        beginTime=0;

        anagLine=-1;
        score=0;
        level=1;
        currentShape=null;
        mode = 0;
        grid=new Box[20][10];
        nextgrid=new Box[4][4];
        mot="";
        gameOver=false;
        
        // Initialisation de grid
        gridInterface = new gridInterfaceReversable(new GridLayout(20,10),composants);
        nextInterface = new gridInterfaceReversable(new GridLayout(4,4));
        
        firstGame=true; //Le jeu a été crée et non chargé
        
        decalage=0;
        nbShape=1;
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
                grid[i][j].addMouseListener(this); 
            } 
        }
        
    }

/**
    * Permet de mettre à jour les données du jeu (Composants Swing)
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
        JButton b;
        if(gridInterface.Componant.containsKey("Niveau")){
            b=(JButton) gridInterface.Componant.get("Niveau");
            b.setForeground(Color.white);
            b.setText(String.valueOf(level));
            b.repaint();
        }
        if(gridInterface.Componant.containsKey("Score")){
            b=(JButton) gridInterface.Componant.get("Score");
            b.setForeground(Color.white);
            b.setText(String.valueOf((int)score));
            b.repaint();
        }
        if(gridInterface.Componant.containsKey("Saisie")){
            b=(JButton) gridInterface.Componant.get("Saisie");
            b.setForeground(Color.white);
            b.setText(String.valueOf(mot));
            b.repaint();
        }
        if(gridInterface.Componant.containsKey("Worddle")){
            b=(JButton) gridInterface.Componant.get("Worddle");
            if(System.currentTimeMillis()-decalage-worddleLast>=worddleReload){
                b.setBackground(new Color(49,177,19));
                b.setFocusPainted(false);
                b.setText("");
                b.repaint();
            }
            else if(mode==2){
                b.setBackground(new Color(221,128,17));
                b.setFocusPainted(false);
                b.setText("");
                b.repaint();
            }
            else{
                b.setBackground(new Color(209,7,7));
                b.setFocusPainted(false);
                b.setText("");
                b.repaint();
            }
        }
        if(gridInterface.Componant.containsKey("Temps")){
            JLabel l=(JLabel) gridInterface.Componant.get("Temps");
            switch(mode){
                case 0:
                        l.setForeground(new Color(33,91,201));
                        l.setText("Temps");
                    break;
                case 1:
                        long timeAnag=anagTime-(System.currentTimeMillis()-decalage-beginTime);
                        if(timeAnag < 6000)
                            l.setForeground(Color.red);
                        else
                            l.setForeground(new Color(33,91,201));
                        l.setText(String.valueOf(timeAnag/1000)+" s restantes");
                    break;
                case 2:
                        long timeWorddle=worddleTime-(System.currentTimeMillis()-decalage-worddleLast);
                        if(timeWorddle < 6000)
                            l.setForeground(Color.red);
                        else
                            l.setForeground(new Color(33,91,201));
                        l.setText(String.valueOf(timeWorddle/1000)+" s restantes");
                    
                    break;
            }
        }
    }

/**
    * Permet de mettre à jour la shape suivante (Composants Swing)
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
     * @return le niveau
 **/
    public int getLevel(){
        return level;
    }

/**
    * Permet de récupérer la grille du jeu
     * @return la grille de Box
 **/
    public Box[][] getGrid(){
        return grid;
    }

/**
    * Permet de récupérer le mode actuel du jeu (Tetris, Anagramme, Worddle)
     * @return le mode actuel
 **/
    public int getMode(){
        return mode;
    }
    
/**
    * Permet de récupérer le JPanel de la grille
     * @return le composant Swing de la grille
 **/   
    public gridInterfaceReversable getGridInterface(){
        return gridInterface;
    }

/**
    * Permet de récupérer le JPanel de la prochaine pièce
     * @return le composant Swing de la prochaine pièce
 **/
    public JPanel getNextInterface(){
        return nextInterface;
    }

/**
    * La boucle principale du jeu
 **/
    @Override
    public void run(){
        
        //Boucle principale
        try {
            if(firstGame){
                init();
                nextShape=Shape.getRandomShape();
                newShapeInGame();
            }
            else{
                decalage=System.currentTimeMillis()-decalage;
                pause=false;
            }
            
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
                    if(System.currentTimeMillis()-decalage-beginTime>anagTime){
                        mode = 0;
                        unSelected(-1);
                        clean();
                        MainGame.getFrames()[0].requestFocusInWindow();
                        mot="";
                        anagLine=-1;
                        gameOver=newShapeInGame();
                    }
                }else if(mode == 2){
                        if(intelligence!=null)
                            intelligence.play();
                        rafraichir();
                    if(System.currentTimeMillis()-decalage-worddleLast>worddleTime){
                        mode=0;
                        suppressionWorddle();
                        clean();
                        clearBox();
                        MainGame.getFrames()[0].requestFocusInWindow();
                        mot="";
                        worddleLast=System.currentTimeMillis()-decalage;
                    }
                }
                
              if(nbShape%options.frequenceModif==0 && !modif){
                  modif=true;
                  addModifier();
              }
                  
        }
        if(!stop){
            System.out.println("GAME OVER");
            JOptionPane.showMessageDialog(JFrame.getFrames()[0],"GAME OVER !");
        }
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
        if(mode != 0 || System.currentTimeMillis()-decalage-worddleLast<worddleReload || pause){
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
        worddleLast=System.currentTimeMillis()-decalage;
        mot+=firstBox.getBrick().lettre;
    }

/**
    * Permet d'enlever les cases surlignées et de vider les cases de départ Worddle
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
    * @return l'heure à laquelle la ligne à été faite
 **/
    public long anagramme(int ligne){
        
        //Si l'autre joueur est en anagramme, il ne peut pas jouer l'anagramme tout de suite
        if(other!=null){
            if(other.getMode()==1) return System.currentTimeMillis()-decalage;
        }
        
        Border yellowline = BorderFactory.createLineBorder(Color.YELLOW,1);
            for(int j=0;j<10;++j){
                    grid[ligne][j].setBorder(yellowline);
            }
        anagLine=ligne;
        mode = 1;
        return System.currentTimeMillis()-decalage; 
        
    }

/**
    * Permet de valider un mot (ou de finir le mode actuel si le mot est vide)
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
            else if(mot.length() > anagLettres && Dictionary.getDictionary().contains(mot)){
                
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
                MainGame.getFrames()[0].requestFocusInWindow();
                mot="";
                worddleLast=System.currentTimeMillis()-decalage;
            }
            else if(Dictionary.getDictionary().contains(mot)){                
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
     * @return l'heure à laquelle une ligne a été realisé
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
     * @return true si il y a GameOver, false sinon
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
     * @param sens, le sens du deplacement
     * @return true si la pièce peut bouger, false sinon
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
    * Permet de tester si la pièce courante peut tourner ou pas
     * @return true si la pièce peut tourner, false sinon
 **/
    public boolean canRotate(){
        while(currentShape.x+currentShape.height>=10 || currentShape.y+currentShape.width>=20){
            if(!canMoveAside(-1)) break;
            moveShapeAside(-1, true);
        }
        if(currentShape.x+currentShape.height>=10 || currentShape.y+currentShape.width>=20)
            return false;
        
        int taille=currentShape.width;
        if(taille < currentShape.height) taille=currentShape.height;
        
        for(int i=0;i<=taille;++i){
            for(int j=0;j<=taille;++j){
                if(!grid[currentShape.y+i][currentShape.x+j].isEmpty() && grid[currentShape.y+i][currentShape.x+j].getShape()!=currentShape)
                    return false;
            }
        }
        return true;
    }

/**
    * Permet de tourner la pièce courante ou de monter en mode Worddle
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
        
        int taille=currentShape.width+1;
        if(taille <= currentShape.height) taille=currentShape.height+1;
        
        for(int i=0;i<taille;++i){
            for(int j=0;j<taille;++j){                
                
                grid[currentShape.y+i][currentShape.x+j].setShapeBrick(currentShape,currentShape.getBricks()[i][j]);
            }
        }
    }

/**
    * Permet de faire tomber la pièce courante
    * @param shape
    * La forme que l'on veut faire tomber
    * @param modifierTake
    * Si la forme doit prendre le modificateur ou pas
     * @return si la pièce peut descendre
 **/
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
    
/**
    * Permet de tester si une pièce peut tomber
    * @param shape
    * la pièce que l'on souhaite tester
     * @return si la pièce peut descendre
 **/
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

/**
    * Permet la sauvegarde d'un ou plusieurs jeux
     * @throws java.io.IOException
 **/
  public void saveGame() throws IOException
    {
        pause=true;
       long tmp=decalage;
       decalage=System.currentTimeMillis()-decalage;
    try {
        
        firstGame=false;
        Date date = new Date( System.currentTimeMillis() );
    SimpleDateFormat sdf = new SimpleDateFormat( "dd.MM.yyyy" );
        FileOutputStream file = new FileOutputStream("save/"+sdf.format( date )+".txt");
        ObjectOutputStream object= new ObjectOutputStream(file);
        try {
            object.writeObject(this);
            System.out.println("Fichier sauvegardé");
            object.flush();
        }
        finally{
            try{
                object.close();
            }
            finally { 
                file.close();
            }
        }
    } 
    catch(IOException ioe) {
        ioe.printStackTrace();
    }
        decalage=tmp;
        pause=false;
        
    }
  
 
 /**
    * Permet de charger un jeu déjà existant
     * @return le jeu crée
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
 **/
	 /**
    * Permet de charger un jeu déjà existant
 **/
	public static Game readGame() throws IOException, ClassNotFoundException
	{ 
		 Game loadGame=null;
                 File studentFile = null;
            try {
                JFileChooser dialogue = new JFileChooser(new File("save/."));
	PrintWriter sortie;
	
	int choix = dialogue.showOpenDialog(null);
	if (choix== 
	    JFileChooser.APPROVE_OPTION) {
	   studentFile = dialogue.getSelectedFile();
	    sortie = new PrintWriter
		(new FileWriter(studentFile.getPath(), true));
	    sortie.close();
            
            
	}else 
            return null;
                FileInputStream fileInput = new FileInputStream(studentFile);
                ObjectInputStream object2 = new ObjectInputStream(fileInput);
                try {
                    System.out.println("Chargement en cours...");
                    loadGame = (Game) object2.readObject();
                }
                finally{
                    try{
                        object2.close();
                    }
                    finally {
                        fileInput.close();
                    }
                }
            }
            catch (FileNotFoundException e) {
            System.out.println("Erreur, pas de fichiers");
                    System.exit(1);
                } 
            catch(IOException ioe) {
                ioe.printStackTrace();
            }
            catch(ClassNotFoundException cnfe) {
            System.out.println("Erreur, problème classe");
            System.exit(1);
            }
            
         return loadGame;
        }


/**
    * Permet d'avoir la Box inverse d'une box passée en paramètre
    * @param b
    * La Box dont on veut l'inverse
    * @return La box inverse
 **/ 
    public Box getInverse(Box b){
        for(int i=0;i<20;++i){
            for(int j=0;j<10;++j){
                if(grid[i][j]==b)
                    return grid[19-i][9-j];
            }
        }
        return null;
    }
    
/**
    * La méthode actionPerformed de l'interface ActionListener, appelé quand l'on clique sur une Box
    * @param evt
    * La Box sur laquelle l'utilisateur a cliqué
 **/
    
    @Override
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
            MainGame.getFrames()[0].requestFocusInWindow();
    }

/**
    * Permet d'augmenter le niveau du jeu
 **/ 
    public void levelUp(){
        
        level++;
        worddleTime=options.worddleTime-level*50; //Le temps en mode Worddle
        worddleReload=options.worddleReload+level*50; //Le temps de rechargement de Worddle
        anagTime=options.anagTime-level*50; //Le temps en mode anagramme
        fallTime=options.fallTime-level*5; //Le temps de chute des pièces
        if(anagLettres<4 && level%3==0){
            anagLettres++; //Le nombre de lettres minimum en anagramme
        }
        if(gridInterface.Componant.containsKey("Niveau")){
            JButton b=(JButton) gridInterface.Componant.get("Niveau");
            b.setForeground(Color.white);
            b.setText(String.valueOf(level));
            b.repaint();
        }
    }
 
/**
    * Ajoute un modificateur au hasard dans une case vide de la grille
 **/
    public void addModifier(){
        int x,y;
        do {
            x=(int)(Math.random() * 10);
            y=(int)(Math.random() * 15);
        }while(!grid[y+5][x].isEmpty());
        
        Modifier modifier=new Modifier(other,this);
        grid[y+5][x].setModifier(modifier);
    }

/**
    * Permet de définir le mode du jeu
    * @param i
    * Si i=0 mode Tetris, i=1 mode Anagramme, i=2 mode Worddle
 **/
    void setMode(int i) {
        mode=i;
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {}

    /**
    * Permet de recharger la grille
    * @param me
    * L'event MouseEvent de l'interface MouseListener
 **/        
    @Override
    public void mouseEntered(MouseEvent me) {
        gridInterface.repaint();   
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }
}
