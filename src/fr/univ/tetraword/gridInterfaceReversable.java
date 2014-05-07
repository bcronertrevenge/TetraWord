/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author bruno
 */
public class gridInterfaceReversable extends JPanel {
    
    boolean reverse;
    
    public gridInterfaceReversable(GridLayout g){
        super(g);
        reverse=false;
    }
    
     @Override
    public void paintComponent(Graphics g) {
        
        if(reverse){
            Graphics2D g2d = (Graphics2D) g;
            int w2 = getWidth() / 2;
            int h2 = getHeight() / 2;
            g2d.rotate(-Math.PI , w2, h2);
        }
        super.paintComponent(g);
    }

}
