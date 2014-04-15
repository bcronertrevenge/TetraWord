/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import java.awt.Color;
import javax.swing.JButton;

public class Box extends JButton {
    //private int type; //0 : case vide, 1 : case brick, 2 : case modificateur
    private Shape shape;
    private Brick brick;
    
    public Box(){
        shape=null;
        brick=null;
    }
    
    public Box(Shape shape, Brick brick){
        this.shape=shape;
        this.brick=brick;
    }
    public void setShapeBrick(Shape shape, Brick brick){
        
        this.shape=shape;
        this.brick=brick;
    }
    
    public Shape getShape(){
            return shape;
    }
    
    public Brick getBrick(){
            return brick;
    }

    public boolean isEmpty(){
        if(this==null)
            return true;
        else if(brick==null || shape==null)
            return true;
        return false;
    }
    
    
    public void rafraichir(){
        if(brick==null){
            setBackground(Color.gray);
            setText(String.valueOf(""));
        }
        else if(brick!=null && shape!=null){            
            switch (shape.couleur){
            case 0 :
                setBackground(Color.blue);
                setText(String.valueOf(brick.lettre));
                break;
            case 1 :
                setBackground(Color.green);
                setText(String.valueOf(brick.lettre));
                break;
            case 2 :
                setBackground(Color.red);
                setText(String.valueOf(brick.lettre));
                break;
            case 3 :
                setBackground(Color.orange);
                setText(String.valueOf(brick.lettre));
                break;
            default :
                setBackground(Color.gray);
                setText(String.valueOf(""));
                break;
        }
        
        }
    }
    
    public void changeColor(Color color){
        setBackground(color);
    }
}
