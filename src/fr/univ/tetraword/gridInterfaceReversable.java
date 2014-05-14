package fr.univ.tetraword;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
    * gridInterfaceReversable est la classe qui permet d'inverser (ou pas) le sens de la grille
 **/
public class gridInterfaceReversable extends JPanel {
    
    boolean reverse;
    HashMap<String,JComponent> Componant;
/**
    * Constructeur par défaut
    * @param g
    * la grille initiale que l'on souhaite renverser (ou pas)
 **/
    
    public gridInterfaceReversable(GridLayout g){
        super(g);
        reverse=false;
        this.Componant=null;
    }
    
    public gridInterfaceReversable(GridLayout g, HashMap<String,JComponent> Componant){
        super(g);
        reverse=false;
        this.Componant=Componant;
    }

/**
    * Permet d'afficher la grille (renversé ou pas)
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
