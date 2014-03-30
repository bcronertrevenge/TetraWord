package fr.univ.tetraword;

import fr.univ.graphicinterface.JCoolButton;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class MainGame {
    
    /* Fonction pour mettre une image en arrière plan */
    public static JPanel setBackgroundImage(JFrame frame, final File img) throws IOException{
	JPanel panel = new JPanel(){
            private static final long serialVersionUID = 1;
            private BufferedImage buf = ImageIO.read(img);
            
            @Override
            protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(buf, 0,0, null);
            }
	};
	
	frame.setContentPane(panel);
	return panel;
    }
    
    /* Page d'accueil */
    public static void welcomePage() throws IOException{
            // Création de la fenêtre
            JFrame frame = new JFrame();
            frame.setTitle("Welcome on Tetra Word");
            frame.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(frame, new File("src/fr/univ/graphicinterface/accueil.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Création des boutons
            Font century = new Font("Century Gothic",0,26);
            
            JCoolButton jouer = new JCoolButton("Nouveau jeu");
            jouer.setPreferredSize(new Dimension(220,60));
            jouer.setForeground(Color.white);
            jouer.setFont(century);
            jouer.setFocusPainted(false);
            
            JCoolButton charger = new JCoolButton("Charger partie");
            charger.setPreferredSize(new Dimension(220,60));
            charger.setForeground(Color.white);
            charger.setFont(century);
            charger.setFocusPainted(false);
                        
            JCoolButton options = new JCoolButton("Options");
            options.setPreferredSize(new Dimension(220,60));
            options.setForeground(Color.white);
            options.setFont(century);
            options.setFocusPainted(false);
                     
            JCoolButton quitter = new JCoolButton("Quitter");
            quitter.setPreferredSize(new Dimension(220,60));
            quitter.setForeground(Color.white);
            quitter.setFont(century);
            quitter.setFocusPainted(false);
       
            // Placement à gauche
            GroupLayout jPanel1Layout = new GroupLayout(panel);
            panel.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(104, 104, 104)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(jouer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(charger, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(options, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(quitter, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(640, Short.MAX_VALUE))
                );
            
            jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(370, Short.MAX_VALUE)
                .addComponent(jouer, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(charger, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(options, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(quitter, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );
           
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    }
    
    /* Page d'accueil */
    public static void mainPage() throws IOException{
            // Création de la fenêtre
            JFrame frame = new JFrame();
            frame.setTitle("Tetra Word");
            frame.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(frame, new File("src/fr/univ/graphicinterface/game.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Fonts
            Font copperplate = new Font("Copperplate Gothic Light",1,26);
            Font century = new Font("Century Gothic",0,26);
            
            // Création du jeu
            Game mainGame=new Game();
            
            // Affichage score, niveau
            JLabel niveau = new JLabel("Niveau     "+mainGame.getLevel());
            niveau.setPreferredSize(new Dimension(180,60));
            niveau.setForeground(new Color(33,91,201));
            niveau.setFont(copperplate);
                        
            JLabel score = new JLabel("Score     "+mainGame.getScore());
            score.setPreferredSize(new Dimension(180,60));
            score.setForeground(new Color(33,91,201));
            score.setFont(copperplate);
            
            JLabel prochainepiece = new JLabel("Prochaine pièce");
            prochainepiece.setPreferredSize(new Dimension(180,60));
            prochainepiece.setForeground(new Color(33,91,201));
            prochainepiece.setFont(copperplate);
       
            // Placement à gauche
            GroupLayout jPanel1Layout = new GroupLayout(panel);
            panel.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(640, 640, 640)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(niveau, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(score, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(prochainepiece, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(640, Short.MAX_VALUE))
                );
            
            jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(370, Short.MAX_VALUE)
                .addComponent(niveau, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(score, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(prochainepiece, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addGap(260, 260, 260))
        );
                      
           
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    }
    
    /* Programme Principal */
    public static void main(String[] args) throws IOException{
        mainPage();
    }
}
