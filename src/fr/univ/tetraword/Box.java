/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import java.awt.Color;
import javax.swing.JButton;

public class Box extends JButton {
    private int type; //0 : case vide, 1 : case brick, 2 : case modificateur
    private Shape shape;
    private Brick brick;
    
    public Box(){
        type=0;
    }
    
    public Box(Shape shape, Brick brick){
        type=1;
        this.shape=shape;
        this.brick=brick;
        if(brick==null)
            type=0;
    }
    public void setShapeBrick(Shape shape, Brick brick){
        if(brick==null)
            type=0;
        else
            type=1;
        
        this.shape=shape;
        this.brick=brick;
    }
    
    public Shape getShape(){
        if(type==1)
            return shape;
        return null;
    }
    
    public Brick getBrick(){
    if(type==1)
            return brick;
        return null;
    }

    public boolean isEmpty(){
        if(this==null)
            return true;
        else if(brick==null || shape==null)
            return true;
        return false;
    }
    
    
    public void rafraichir(){
        Color bleuC=new Color(23,109,181);
        Color bleuF=new Color(21,23,98);
        Color rougeF=new Color(196,6,6);
        Color vert=new Color(25,137,49);
        Color orange=new Color(248,111,0);
        Color violet=new Color(116,28,191);

        if(type==0)
            setBackground(Color.gray);
        else if(type==1){            
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
                break;
        }
        
        }
    }
    
    public void changeColor(Color color){
        setBackground(color);
    }
}
