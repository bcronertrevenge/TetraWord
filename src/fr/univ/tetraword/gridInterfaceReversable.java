package fr.univ.tetraword;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import javax.swing.JPanel;

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
