/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

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
}
