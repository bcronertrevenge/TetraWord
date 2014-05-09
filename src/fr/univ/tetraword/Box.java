package fr.univ.tetraword;

import java.awt.Color;
import javax.swing.JButton;

/**
    * Box est la classe représentant une case de la grille
 **/
public class Box extends JButton {
    
    private Shape shape;
    private Brick brick;
    private Modifier modifier;
    boolean isSelected;
    boolean isSuppressed;
    boolean isStart;
    boolean noWord;
    Color temporaryEffect;
    
/**
    * Constructeur par défaut d'une Box
 **/
    public Box(){
        shape=null;
        brick=null;
        modifier=null;
        isSelected=false;
        isSuppressed=false;
        isStart=false;
        noWord=false;
        temporaryEffect=null;
    }

 /**
    * Constructeur de Box
    * @param shape
    * la forme que contiendra la box
    * @param brick
    * la brique que contiendra la box
    * 
 **/
    public Box(Shape shape, Brick brick){
        this.shape=shape;
        this.brick=brick;
        modifier=null;
        isSelected=false;
        isSuppressed=false;
        isStart=false;
        noWord=false;
        temporaryEffect=null;
    }

 /**
    * Constructeur de Box
    * @param modifier
    * le modificateur que pourra contenir la Box
    * 
 **/
    public Box(Modifier modifier){
        shape=null;
        brick=null;
        this.modifier=modifier;
        isSelected=false;
        isSuppressed=false;
        isStart=false;
        noWord=false;
        temporaryEffect=null;
    }

 /**
    * Permet d'associer une forme et une brique à une Box existante
    * @param shape
    * la forme qui sera contenue dans la Box
    * @param brick
    * la brique que contiendra la Box
 **/
    public void setShapeBrick(Shape shape, Brick brick){
        this.shape=shape;
        this.brick=brick;
    }

 /**
    * Permet d'associer un modificateur à une Box existante
    * @param modifier
    * le modificateur que contiendra la Box
 **/
    public void setModifier(Modifier modifier){
        this.modifier=modifier;
    }

 /**
    * Permet d'obtenir la forme associée à la Box courante
     * @return 
 **/
    public Shape getShape(){
            return shape;
    }

 /**
    * Permet d'obtenir la brique associée à la Box courante
     * @return 
 **/
    public Brick getBrick(){
            return brick;
    }

/**
    * Permet d'obtenir le modificateur associé à la Box courante
     * @return 
 **/
    public Modifier getModifier(){
            return modifier;
    }

/**
    * Permet de savoir si une Box est vide ou pas
     * @return 
 **/
    public boolean isEmpty(){
        if(this==null)
            return true;
        else if(brick==null || shape==null)
            return true;
        return false;
    }

/**
    * Permet d'échanger la Box courante avec une autre Box existante
    * @param bo
    * La box avec laquelle on souhaite échanger
 **/
    public void boxChange(Box bo){
        Shape s=shape;
        Brick b=brick;
        Modifier m=modifier;
        boolean selec=isSelected;
        boolean suppr=isSuppressed;
        boolean start=isStart;
        boolean word=noWord;
        Color temp=temporaryEffect;
        
        setShapeBrick(bo.shape, bo.brick);
        modifier=bo.modifier;
        isSelected=bo.isSelected;
        isSuppressed=bo.isSuppressed;
        isStart=bo.isStart;
        noWord=bo.noWord;
        temporaryEffect=bo.temporaryEffect;
        
        bo.setShapeBrick(s, b);
        bo.brick=b;
        bo.modifier=m;
        bo.isSelected=selec;
        bo.isSuppressed=suppr;
        bo.isStart=start;
        bo.noWord=word;
        bo.temporaryEffect=temp;
    }

/**
    * Permet de mettre à jour la Box courante (Composant Swing)
 **/
    public void rafraichir(){

        if(this == null)
            return;
        
        Color bleuC=new Color(23,109,181);
        Color bleuF=new Color(21,23,98);
        Color rougeF=new Color(196,6,6);
        Color vert=new Color(25,137,49);
        Color orange=new Color(248,111,0);
        Color violet=new Color(116,28,191);
        Color jaune=new Color(248,203,0);
        Color modifierColor=new Color(147,93,212);
        
        
        if(brick!=null && shape!=null){            
            if(isSelected){
                setBackground(jaune);
                if(brick!=null)
                setText(String.valueOf(brick.lettre));
                setForeground(Color.BLACK);
                return;
            }
            else if(isStart || isSuppressed){
                setBackground(Color.WHITE);
                setText(String.valueOf(brick.lettre));
                setForeground(Color.BLACK);
                return;
            }
            switch (shape.couleur){
            case 0 :
                setBackground(bleuC);
                setText(String.valueOf(brick.lettre));
                setForeground(Color.WHITE);
                break;
            case 1 :
                setBackground(rougeF);
                setText(String.valueOf(brick.lettre));
                setForeground(Color.WHITE);
                break;
            case 2 :
                setBackground(vert);
                setText(String.valueOf(brick.lettre));
                setForeground(Color.WHITE);
                break;
            case 3 :
                setBackground(orange);
                setText(String.valueOf(brick.lettre));
                setForeground(Color.WHITE);
                break;
            case 4 :
                setBackground(violet);
                setText(String.valueOf(brick.lettre));
                setForeground(Color.WHITE);
                break;
            case 5 :
                setBackground(bleuF);
                setText(String.valueOf(brick.lettre));
                setForeground(Color.WHITE);
                break;
            default :
                setBackground(Color.gray);
                setText(String.valueOf(""));
                break;
        }
        }
        else if(temporaryEffect!=null){
            setBackground(temporaryEffect);
            setText("");
            temporaryEffect=null;
        }
        else if(modifier!=null){
            setBackground(modifierColor);
            setText("");
            setForeground(Color.BLACK);
        }
        else if(brick==null){
            setBackground(Color.gray);
            setText(String.valueOf(""));
        }
    }  
}
