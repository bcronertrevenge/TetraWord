/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import java.awt.Color;
import javax.swing.JButton;

public class Box extends JButton{
    
    private Shape shape;
    private Brick brick;
    private Modifier modifier;
    boolean isSelected;
    boolean isSuppressed;
    boolean isStart;
    boolean noWord;
    Color temporaryEffect;
    
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
    
    public void setShapeBrick(Shape shape, Brick brick){
        this.shape=shape;
        this.brick=brick;
    }
    
    public void setModifier(Modifier modifier){
        this.modifier=modifier;
    }
    
    public Shape getShape(){
            return shape;
    }
    
    public Brick getBrick(){
            return brick;
    }

    public Modifier getModifier(){
            return modifier;
    }
    
    public boolean isEmpty(){
        if(this==null)
            return true;
        else if(brick==null || shape==null)
            return true;
        return false;
    }
    
    public static Box getItself(Box itself, Box dummy)
    {
        return itself;
    }
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
    
    public void changeColor(Color color){
        setBackground(color);
    }
    
}
