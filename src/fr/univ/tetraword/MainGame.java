package fr.univ.tetraword;

import fr.univ.graphicinterface.JWelcomeButton;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class MainGame {
    static Vector<Game> Games;
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
    
    public static void jouerActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        mainPage();
    }
    
    public static void chargerActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        loadGamePage();
    }
    
    /* Page d'accueil */
    public static void welcomePage() throws IOException{
            // Création de la fenêtre
            final JFrame frame = new JFrame();
            frame.setTitle("Bienvenue sur Tetra Word");
            frame.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(frame, new File("src/fr/univ/graphicinterface/accueil.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Création des boutons
            Font century = new Font("Century Gothic",0,26);
                       
            JWelcomeButton jouer = new JWelcomeButton("Nouveau jeu");
            jouer.setPreferredSize(new Dimension(220,60));
            jouer.setForeground(Color.white);
            jouer.setFont(century);
            jouer.setFocusPainted(false);
            jouer.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        jouerActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            JWelcomeButton charger = new JWelcomeButton("Charger partie");
            charger.setPreferredSize(new Dimension(220,60));
            charger.setForeground(Color.white);
            charger.setFont(century);
            charger.setFocusPainted(false);
            charger.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        chargerActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
                        
            JWelcomeButton options = new JWelcomeButton("Options");
            options.setPreferredSize(new Dimension(220,60));
            options.setForeground(Color.white);
            options.setFont(century);
            options.setFocusPainted(false);
                     
            JWelcomeButton quitter = new JWelcomeButton("Quitter");
            quitter.setPreferredSize(new Dimension(220,60));
            quitter.setForeground(Color.white);
            quitter.setFont(century);
            quitter.setFocusPainted(false);
            quitter.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                   frame.dispose();
                }
            });
       
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
    
    /* Page Principale */
    public static void mainPage() throws IOException{
            // Création de la fenêtre
            JFrame frame = new JFrame();
            frame.setTitle("Vous jouez à Tetra Word");
            frame.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(frame, new File("src/fr/univ/graphicinterface/game.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Fonts
            Font copperplate = new Font("Copperplate Gothic Bold",0,26);
            Font century = new Font("Century Gothic",0,26);
            
            // Création du jeu
            Games=new Vector<Game>();
            Game game=new Game();
            game.start();
            Games.add(game);
            // Grille de Jeu        
            JPanel grille = new JPanel (new GridLayout (20,10));
            Border whiteline = BorderFactory.createLineBorder(Color.WHITE,1);
           
            Box grid[][]=game.getGrid();
            
            for (int i=0; i<20;++i){
                for (int j=0; j<10; ++j){
                    JPanel pCase = new JPanel();
                    if (grid[i][j].getShape()!=null){
                        if (grid[i][j].getShape().couleur==0){
                            System.out.println("couleur"+grid[i][j].getShape().couleur);
                            pCase.setBackground(Color.blue);
                        }
                        else if (grid[i][j].getShape().couleur==1){
                            pCase.setBackground(Color.green);
                        }
                        else if (grid[i][j].getShape().couleur==2){
                            pCase.setBackground(Color.red);
                        }
                        else if (grid[i][j].getShape().couleur==3){
                            pCase.setBackground(Color.orange);
                        }
                    }else {
                        pCase.setBackground(Color.gray);
                    }
                    pCase.setBorder(whiteline);
                    grille.add(pCase);
                } 
            }
                       
            // Labels
            JLabel labelNiveau = new JLabel();
            labelNiveau.setFont(copperplate);
            labelNiveau.setForeground(new Color(33,91,201));
            labelNiveau.setText("Niveau");

            JLabel labelScore = new JLabel();
            labelScore.setFont(copperplate);
            labelScore.setForeground(new Color(33,91,201));
            labelScore.setText("Score");

            JLabel labelPiece = new JLabel();
            labelPiece.setFont(copperplate);
            labelPiece.setForeground(new Color(33,91,201));
            labelPiece.setText("Prochaine pièce");
            
            // Boutons
            JWelcomeButton buttonNiveau = new JWelcomeButton(String.valueOf(game.getLevel()));
            buttonNiveau.setFont(century);
            buttonNiveau.setForeground(Color.WHITE);
            buttonNiveau.setFocusPainted(false);

            JWelcomeButton buttonScore = new JWelcomeButton(String.valueOf(game.getScore()));
            buttonScore.setFont(century);
            buttonScore.setForeground(Color.WHITE);
            buttonScore.setFocusPainted(false);

            // Prochaine piece        
            JPanel buttonPiece = new JPanel (new GridLayout (4,4));
            for(int i=0; i<16; ++i){
               JPanel pCase = new JPanel();
               pCase.setBackground(new Color(67,71,79));
               pCase.setBorder(whiteline);
               buttonPiece.add(pCase);
            }

            GroupLayout jPanel1Layout = new GroupLayout(panel);
            panel.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(71, 71, 71)
                    .addComponent(grille, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
                    .addGap(148, 148, 148)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(labelNiveau, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelScore, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                            .addGap(48, 48, 48)
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(buttonNiveau, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonScore, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)))
                        .addComponent(labelPiece, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonPiece, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(166, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(66, 66, 66)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelNiveau, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonNiveau, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                            .addGap(53, 53, 53)
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelScore, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonScore, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                            .addGap(56, 56, 56)
                            .addComponent(labelPiece, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addComponent(buttonPiece, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                        .addComponent(grille, GroupLayout.PREFERRED_SIZE, 631, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(71, Short.MAX_VALUE))
            );
       
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /* Page Principale */
    public static void loadGamePage() throws IOException{
            // Création de la fenêtre
            JFrame frame = new JFrame();
            frame.setTitle("Charger une partie");
            frame.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(frame, new File("src/fr/univ/graphicinterface/game.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Fonts
            Font copperplate = new Font("Copperplate Gothic Bold",0,26);
            Font century = new Font("Century Gothic",0,26);
            
            
       
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void misAJour(){
        Box grid[][]=Games.get(0).getGrid();
        
        for (int i=0; i<20;++i){
                for (int j=0; j<10; ++j){
                    JPanel pCase = new JPanel();
                    if (grid[i][j].getShape()!=null){
                        if (grid[i][j].getShape().couleur==0){
                            pCase.setBackground(Color.blue);
                        }
                        else if (grid[i][j].getShape().couleur==1){
                            pCase.setBackground(Color.green);
                        }
                        else if (grid[i][j].getShape().couleur==2){
                            pCase.setBackground(Color.red);
                        }
                        else if (grid[i][j].getShape().couleur==3){
                            pCase.setBackground(Color.orange);
                        }
                    }else {
                        pCase.setBackground(Color.gray);
                    }
                    //pCase.setBorder(whiteline);
                    //grille.add(pCase);
                } 
            }
    }
    /* Programme Principal */
    public static void main(String[] args) throws IOException{
        welcomePage();
    }
}
