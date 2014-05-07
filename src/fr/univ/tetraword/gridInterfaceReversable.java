package fr.univ.tetraword;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
    * gridInterfaceReversable est la classe qui permet d'inverser le sens de la grille
 **/
public class gridInterfaceReversable extends JPanel {
    
    boolean reverse;
 
/**
    * Constructeur par défaut
    * @param g
    * la grille initiale que l'on souhaite renverser
 **/
    public gridInterfaceReversable(GridLayout g){
        super(g);
        reverse=false;
    }

/**
    * Permet d'afficher le résultat
 **/
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
