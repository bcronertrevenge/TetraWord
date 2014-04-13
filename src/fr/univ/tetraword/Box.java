/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import java.awt.Color;
import javax.swing.JButton;

public class Box extends JButton {

    public Box(){
        
    }

    public void setShape(Shape shape){}
    
    public void setBrick(Brick brick){}
    
    public Shape getShape(){return null;}
    
    public Brick getBrick(){return null;}

    public boolean isEmpty(){
        return true;
    }
    
    public void repaint(){
        setBackground(Color.gray);
    }
}
