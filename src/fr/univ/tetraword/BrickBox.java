/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import java.awt.Color;

/**
 *
 * @author bruno
 */
public class BrickBox extends Box {
    private Shape shape;
    private Brick brick;
    
    public BrickBox(){
        super();
        shape=null;
        brick=null;
    }
    
    public BrickBox(Shape shape, Brick brick){
        this.shape=shape;
        this.brick=brick;
    }
    
    public void setShape(Shape shape){
        this.shape=shape;
    }
    
    public void setBrick(Brick brick){
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
    
    public void repaint(){
        switch (shape.couleur){
            case 0 : setBackground(Color.blue);
                break;
            case 1 : setBackground(Color.green);
                break;
            case 2 : setBackground(Color.red);
                break;
            case 3 : setBackground(Color.orange);
                break;
            default : setBackground(Color.gray);
        }
    }
}
