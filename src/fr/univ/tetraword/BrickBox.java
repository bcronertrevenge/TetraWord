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
    Shape shape;
    Brick brick;
    
    public BrickBox(Shape shape, Brick brick){
        this.shape=shape;
        this.brick=brick;
    }
}
