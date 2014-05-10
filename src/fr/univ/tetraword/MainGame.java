package fr.univ.tetraword;

import fr.univ.graphicinterface.JWelcomeButton;
import static fr.univ.tetraword.shapeType.shapeTypes;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

/**
    * MainGame est la classe représentant le jeu principal 
 **/

public class MainGame extends JFrame {
    Vector<Game> Games;
    HashMap<String,JComponent> Composants;
    HashMap<String,JComponent> Composants2;
 
/**
    * Constructeur du jeu principal
 **/
    public MainGame(){
        addKeyListener(new ClavierListener());
    }
    
/**
    * Permet d'appliquer une image de fond
    * @param frame
    * la fenêtre sur laquelle on souhaite appliquer l'image
    * @param img
    * l'image que l'on souhaite mettre en fond
 **/
    public JPanel setBackgroundImage(JFrame frame, final File img) throws IOException{
	JPanel panel = new JPanel(){
            private static final long serialVersionUID = 1;
            private BufferedImage buf = ImageIO.read(img);
            
            @Override
            protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(buf, 0,0, null);
            }
	};
	
	this.setContentPane(panel);
	return panel;
    }
 
/**
    * Lorsque l'on clique sur "Nouveau jeu"
 **/
    public void jouerActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        gameMode();
    }

/**
    * Lorsque l'on clique sur "Jouer en solo"
 **/
    public void soloActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        gameSolo();
    }
 
/**
    * Lorsque l'on clique sur "Jouer contre l'ordinateur"
 **/
    public void ordiActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        gameMulti(true);
    }

/**
    * Lorsque l'on clique sur "Options"
 **/
    public void optionsActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        shapeEditor();
    }
 
/**
    * Lorsque l'on clique sur "Jouer contre un ami"
 **/
    public void amiActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        gameMulti(false);
    }

 /**
    * Lorsque l'on clique sur "Retour"
 **/
    public void retourActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        welcomePage();
    }
 
/**
    * Lorsque l'on clique sur "Sauvegarder"
 **/
    public void sauvegardeActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        JOptionPane.showMessageDialog(this,"Partie sauvegardée !");
        Game.saveGame(Games);
    }

/**
    * Lorsque l'on clique sur "Charger un partie"
 **/
    public void chargerPartieActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        // Changement du titre
            this.setTitle("Vous jouez à Tetra Word en Solo");
            this.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/game.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Fonts
            Font copperplate = new Font("Copperplate Gothic Bold",0,22);
            Font bigCentury = new Font("Century Gothic",0,26);
            Font smallCentury = new Font("Century Gothic",0,18);
            
            Composants=new HashMap<>(); 
            
            // Boutons
            JWelcomeButton buttonRetour = new JWelcomeButton("Retour");
            buttonRetour.setFont(bigCentury);
            buttonRetour.setForeground(Color.WHITE);
            buttonRetour.setFocusPainted(false);
            buttonRetour.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        retourActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            
            JWelcomeButton buttonRegles = new JWelcomeButton("?");
            buttonRegles.setFont(smallCentury);
            buttonRegles.setForeground(Color.WHITE);
            buttonRegles.setFocusPainted(false);
            buttonRegles.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        firstReglesActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            
            JButton buttonWorddle = new JButton("");
            if(buttonWorddle==null)exit(1);
            buttonWorddle.setFocusPainted(false);
            Composants.put("Worddle",buttonWorddle);
            
            JWelcomeButton buttonNiveau = new JWelcomeButton("");
            if(buttonNiveau==null)exit(1);
            buttonNiveau.setFont(bigCentury);
            buttonNiveau.setForeground(Color.WHITE);
            buttonNiveau.setFocusPainted(false);
            Composants.put("Niveau",buttonNiveau);
            
            JWelcomeButton buttonScore = new JWelcomeButton("");
            if(buttonScore==null)exit(1);
            buttonScore.setFont(bigCentury);
            buttonScore.setForeground(Color.WHITE);
            buttonScore.setFocusPainted(false);
            Composants.put("Score",buttonScore);
            
            JWelcomeButton buttonSaisie = new JWelcomeButton("");
            buttonSaisie.setFont(bigCentury);
            buttonSaisie.setForeground(Color.WHITE);
            buttonSaisie.setFocusPainted(false);
            Composants.put("Saisie",buttonSaisie);
            
            JWelcomeButton buttonSauvegarde = new JWelcomeButton("Sauvegarder la partie");
            buttonSauvegarde.setFont(smallCentury);
            buttonSauvegarde.setForeground(Color.WHITE);
            buttonSauvegarde.setFocusPainted(false);
            buttonSauvegarde.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        sauvegardeActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            
            // Création du jeu
          Dictionary dictionary=new Dictionary();
           Games=new Vector<Game>();
             
         /*   Game game=new Game(this,dictionary,Composants,false);
           game.start();
          Games.add(game);*/
         
          
            Games=Game.readGame();
            
            // Grille de Jeu        
            JPanel grille=Games.get(0).getGridInterface();
            
            // Prochaine piece        
            JPanel buttonPiece = Games.get(0).getNextInterface();
                       
            // Labels
            JLabel labelTime = new JLabel();
            labelTime.setFont(copperplate);
            labelTime.setForeground(new Color(33,91,201));
            labelTime.setText("Temps");
            
            JLabel labelWorddle = new JLabel();
            labelWorddle.setFont(copperplate);
            labelWorddle.setForeground(new Color(33,91,201));
            labelWorddle.setText("Worddle");
            
            JLabel labelNiveau = new JLabel();
            labelNiveau.setFont(copperplate);
            labelNiveau.setForeground(new Color(33,91,201));
            labelNiveau.setText("Niveau");

            JLabel labelScore = new JLabel();
            labelScore.setFont(copperplate);
            labelScore.setForeground(new Color(33,91,201));
            labelScore.setText("Score");
            
            
            JLabel labelSaisie = new JLabel();
            labelSaisie.setFont(copperplate);
            labelSaisie.setForeground(new Color(33,91,201));
            labelSaisie.setText("Saisie");
  
               javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panel);
                panel.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(grille, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(labelSaisie, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(labelScore, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(labelNiveau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(buttonNiveau, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                                    .addComponent(buttonScore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(buttonSaisie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(19, 19, 19)
                                                .addComponent(labelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(162, 162, 162))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(140, 140, 140)
                                                .addComponent(buttonSauvegarde, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(160, 160, 160)
                                                .addComponent(buttonPiece, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(152, 152, 152)
                                                .addComponent(labelWorddle, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(buttonWorddle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(buttonRetour, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonRegles, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63))))
                );
                jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(buttonRetour, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(labelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(buttonRegles, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(buttonPiece, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(labelWorddle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buttonWorddle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelNiveau, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonNiveau, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelScore, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonScore, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelSaisie, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonSaisie, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addComponent(buttonSauvegarde, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(grille, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(67, Short.MAX_VALUE))
                );
       
            
       
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.requestFocusInWindow();
    }

/**
    * Lorsque l'on clique sur "Règles"
 **/
    public void firstReglesActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        reglesPage(1);
    }
    
    public void secondReglesActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        reglesPage(2);
    }
    
     public void thirdReglesActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        reglesPage(3);
    }
    
/**
    * Lorsque l'on clique sur "Quitter"
 **/       
    public void quitter(java.awt.event.ActionEvent evt) throws IOException{                                         
        this.dispose();
    }
  
/**
    * Permet de créer la page d'accueil
 **/  
    public void welcomePage() throws IOException{
        
            //Lecture des shapes
            shapeType.readShapes();
            
            // Création de la fenêtre
            this.setTitle("Bienvenue sur Tetra Word");
            this.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/accueil.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Création des boutons
            Font century = new Font("Century Gothic",0,26);
            Font smallCentury = new Font("Century Gothic",0,18);
                       
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
                        chargerPartieActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
                        
            JWelcomeButton options = new JWelcomeButton("Editeur de pièce");
            options.setPreferredSize(new Dimension(220,60));
            options.setForeground(Color.white);
            options.setFont(century);
            options.setFocusPainted(false);
            options.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        optionsActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
                     
            JWelcomeButton quitter = new JWelcomeButton("Quitter");
            quitter.setPreferredSize(new Dimension(220,60));
            quitter.setForeground(Color.white);
            quitter.setFont(century);
            quitter.setFocusPainted(false);
            quitter.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        quitter(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            JWelcomeButton regles = new JWelcomeButton("Règles");
            regles.setPreferredSize(new Dimension(220,60));
            regles.setForeground(Color.white);
            regles.setFont(smallCentury);
            regles.setFocusPainted(false);
            regles.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        firstReglesActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
       
            // Placement
           javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
            panel.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(874, Short.MAX_VALUE)
                    .addComponent(regles, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(60, 60, 60))
                .addGroup(layout.createSequentialGroup()
                    .addGap(111, 111, 111)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jouer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(charger, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(options, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(quitter, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(44, 44, 44)
                    .addComponent(regles, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 285, Short.MAX_VALUE)
                    .addComponent(jouer, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(34, 34, 34)
                    .addComponent(charger, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(34, 34, 34)
                    .addComponent(options, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(32, 32, 32)
                    .addComponent(quitter, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(124, 124, 124))
            );
           
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
/**
    * Affiche une fenêtre lorsque l'on met le jeu en pause
 **/ 
    public void pausePage() throws IOException{
            if (Games.get(0).pause)
                JOptionPane.showMessageDialog(this,"Le jeu est en pause");
    }

/**
    * Permet de créer la page pour choisir son mode de jeu
 **/ 
    public void gameMode() throws IOException{
            // Création de la fenêtre
            this.setTitle("Jouer à Tetra Word");
            this.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/mode.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Fonts
            Font copperplate  = null;
            try{
                File fis = new File("data/COPRGTB.TTF");
                copperplate = Font.createFont(Font.TRUETYPE_FONT, fis);
                copperplate = copperplate.deriveFont((float)26.0);
            }
            catch (Exception e) {
                System.out.println("Police non chargée");
            } 
            
            Font century = new Font("Century Gothic",0,26);
                       
            // Boutons
            JWelcomeButton buttonRetour = new JWelcomeButton("Retour");
            buttonRetour.setFont(century);
            buttonRetour.setForeground(Color.WHITE);
            buttonRetour.setFocusPainted(false);
            buttonRetour.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        retourActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            
            JWelcomeButton buttonSolo = new JWelcomeButton("Jouer en Solo");
            buttonSolo.setFont(century);
            buttonSolo.setForeground(Color.WHITE);
            buttonSolo.setFocusPainted(false);
            buttonSolo.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        soloActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            
            JWelcomeButton buttonOrdi = new JWelcomeButton("Contre l'ordinateur");
            buttonOrdi.setFont(century);
            buttonOrdi.setForeground(Color.WHITE);
            buttonOrdi.setFocusPainted(false);
            buttonOrdi.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        ordiActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            
            JWelcomeButton buttonAmi = new JWelcomeButton("Contre un ami");
            buttonAmi.setFont(century);
            buttonAmi.setForeground(Color.WHITE);
            buttonAmi.setFocusPainted(false);
            buttonAmi.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        amiActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            
            // Labels
            JLabel labelTitre = new JLabel();
            labelTitre.setFont(copperplate);
            labelTitre.setForeground(new Color(33,91,201));
            labelTitre.setText("Modes de Jeu");
            labelTitre.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel labelSolo = new JLabel();
            labelSolo.setFont(copperplate);
            labelSolo.setForeground(new Color(33,91,201));
            labelSolo.setText("En Solitaire");
            labelSolo.setHorizontalAlignment(SwingConstants.CENTER);
            
            JLabel labelMulti = new JLabel();
            labelMulti.setFont(copperplate);
            labelMulti.setForeground(new Color(33,91,201));
            labelMulti.setText("En Multijoueur");
            labelMulti.setHorizontalAlignment(SwingConstants.CENTER);
            
            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panel);
            panel.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(38, 38, 38)
                            .addComponent(buttonRetour))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(81, 81, 81)
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(buttonSolo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonOrdi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonAmi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelMulti,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelSolo, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(652, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(buttonRetour)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 188, Short.MAX_VALUE)
                    .addComponent(labelSolo, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                    .addGap(31, 31, 31)
                    .addComponent(buttonSolo, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                    .addGap(73, 73, 73)
                    .addComponent(labelMulti, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                    .addGap(27, 27, 27)
                    .addComponent(buttonOrdi, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                    .addGap(38, 38, 38)
                    .addComponent(buttonAmi, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                    .addGap(101, 101, 101))
            );
            
            
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
 
/**
    * Permet de créer les pages avec les règles du jeu
 **/ 
    public void reglesPage(int a) throws IOException{
            // Création de la fenêtre
            JPanel panel;
            this.setPreferredSize(new Dimension(1024,768));
            
            // en fonction de la page
            switch(a){
                case 1 :
                    this.setTitle("Règles du Tetra Word");
                    panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/regles1.jpg"));
                    break;
                case 2 :
                    this.setTitle("Règles du Tetra Word (2)");
                    panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/regles2.jpg"));
                    break;
                case 3 :
                    this.setTitle("Règles du Tetra Word (3)");
                    panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/regles3.jpg"));
                    break;
                default :
                    this.setTitle("Règles du Tetra Word");
                    panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/regles1.jpg"));
            }
            
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            
            // Fonts
            Font century = new Font("Century Gothic",0,16);
                       
            // Boutons
            JWelcomeButton buttonRetour = new JWelcomeButton("Retour");
            buttonRetour.setFont(century);
            buttonRetour.setForeground(Color.WHITE);
            buttonRetour.setFocusPainted(false);
            switch (a){
                case 1:
                    buttonRetour.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            try {
                                retourActionPerformed(evt);
                            } catch (IOException ex) {
                                Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                });
                break;
                case 2:
                    buttonRetour.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            try {
                                firstReglesActionPerformed(evt);
                            } catch (IOException ex) {
                                Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                });
                break;
                case 3:
                    buttonRetour.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            try {
                                secondReglesActionPerformed(evt);
                            } catch (IOException ex) {
                                Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                });
            }
            
            JWelcomeButton buttonSuivant = new JWelcomeButton("Suivant");
            buttonSuivant.setFont(century);
            buttonSuivant.setForeground(Color.WHITE);
            buttonSuivant.setFocusPainted(false);
            switch (a){
                case 1:
                    buttonSuivant.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            try {
                                secondReglesActionPerformed(evt);
                            } catch (IOException ex) {
                                Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                });
                break;
                case 2:
                    buttonSuivant.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            try {
                                thirdReglesActionPerformed(evt);
                            } catch (IOException ex) {
                                Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                });
                break;
            }
                    
            
            
            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panel);
                panel.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(buttonRetour, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 751, Short.MAX_VALUE)
                        .addComponent(buttonSuivant, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                );
                jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonRetour, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonSuivant, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(683, Short.MAX_VALUE))
                );

            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
 /**
    * Permet de créer la page de jeu en mode solitaire
 **/ 
    public void gameSolo() throws IOException{
            // Changement du titre
            this.setTitle("Vous jouez à Tetra Word en Solo");
            this.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/game.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Fonts
            Font copperplate  = new Font("Copperplate Gothic Bold",0,22);
            try{
                File fis = new File("data/COPRGTB.TTF");
                copperplate = Font.createFont(Font.TRUETYPE_FONT, fis);
                copperplate = copperplate.deriveFont((float)22.0);
            }
            catch (Exception e) {
                System.out.println("Police non chargée");
            }
            
            Font bigCentury = new Font("Century Gothic",0,26);
            Font smallCentury = new Font("Century Gothic",0,18);
            
            Composants=new HashMap<String,JComponent>(); 
            
            // Boutons
            JWelcomeButton buttonRetour = new JWelcomeButton("Retour");
            buttonRetour.setFont(bigCentury);
            buttonRetour.setForeground(Color.WHITE);
            buttonRetour.setFocusPainted(false);
            buttonRetour.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        retourActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            
            JWelcomeButton buttonRegles = new JWelcomeButton("?");
            buttonRegles.setFont(smallCentury);
            buttonRegles.setForeground(Color.WHITE);
            buttonRegles.setFocusPainted(false);
            buttonRegles.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        firstReglesActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            
            JButton buttonWorddle = new JButton("");
            if(buttonWorddle==null)exit(1);
            buttonWorddle.setFocusPainted(false);
            Composants.put("Worddle",buttonWorddle);
            
            JWelcomeButton buttonNiveau = new JWelcomeButton("");
            if(buttonNiveau==null)exit(1);
            buttonNiveau.setFont(bigCentury);
            buttonNiveau.setForeground(Color.WHITE);
            buttonNiveau.setFocusPainted(false);
            Composants.put("Niveau",buttonNiveau);
            
            JWelcomeButton buttonScore = new JWelcomeButton("");
            if(buttonScore==null)exit(1);
            buttonScore.setFont(bigCentury);
            buttonScore.setForeground(Color.WHITE);
            buttonScore.setFocusPainted(false);
            Composants.put("Score",buttonScore);
            
            JWelcomeButton buttonSaisie = new JWelcomeButton("");
            buttonSaisie.setFont(bigCentury);
            buttonSaisie.setForeground(Color.WHITE);
            buttonSaisie.setFocusPainted(false);
            Composants.put("Saisie",buttonSaisie);
            
            JLabel labelTime = new JLabel();
            labelTime.setFont(copperplate);
            labelTime.setForeground(new Color(33,91,201));
            labelTime.setText("Temps");
            Composants.put("Temps",labelTime);
            
            JWelcomeButton buttonSauvegarde = new JWelcomeButton("Sauvegarder la partie");
            buttonSauvegarde.setFont(smallCentury);
            buttonSauvegarde.setForeground(Color.WHITE);
            buttonSauvegarde.setFocusPainted(false);
            buttonSauvegarde.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        sauvegardeActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            
            // Création du jeu
            Dictionary dictionary=new Dictionary();
            Games=new Vector<Game>();
            
            Game game=new Game(this,dictionary,Composants,false);
            game.start();
            Games.add(game);
            
            // Grille de Jeu        
            JPanel grille=Games.get(0).getGridInterface();
            
            // Prochaine piece        
            JPanel buttonPiece = Games.get(0).getNextInterface();
            
            
                       
            // Labels

            
            JLabel labelWorddle = new JLabel();
            labelWorddle.setFont(copperplate);
            labelWorddle.setForeground(new Color(33,91,201));
            labelWorddle.setText("Worddle");
            
            JLabel labelNiveau = new JLabel();
            labelNiveau.setFont(copperplate);
            labelNiveau.setForeground(new Color(33,91,201));
            labelNiveau.setText("Niveau");

            JLabel labelScore = new JLabel();
            labelScore.setFont(copperplate);
            labelScore.setForeground(new Color(33,91,201));
            labelScore.setText("Score");
            
            
            JLabel labelSaisie = new JLabel();
            labelSaisie.setFont(copperplate);
            labelSaisie.setForeground(new Color(33,91,201));
            labelSaisie.setText("Saisie");
  
               javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panel);
                panel.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(grille, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(labelSaisie, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(labelScore, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(labelNiveau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(buttonNiveau, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                                    .addComponent(buttonScore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(buttonSaisie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(19, 19, 19)
                                                .addComponent(labelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(162, 162, 162))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(140, 140, 140)
                                                .addComponent(buttonSauvegarde, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(160, 160, 160)
                                                .addComponent(buttonPiece, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(152, 152, 152)
                                                .addComponent(labelWorddle, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(buttonWorddle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(buttonRetour, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonRegles, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63))))
                );
                jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(buttonRetour, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(labelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(buttonRegles, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(buttonPiece, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(labelWorddle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buttonWorddle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelNiveau, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonNiveau, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelScore, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonScore, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelSaisie, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonSaisie, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addComponent(buttonSauvegarde, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(grille, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(67, Short.MAX_VALUE))
                );
       
            
       
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.requestFocusInWindow();
    }
    
 /**
    * Permet de créer la page de jeu en mode multijoueur (ordinateur ou contre un ami)
 **/ 
    public void gameMulti(boolean ia) throws IOException{
            // Changement du titre
            if(ia)
                this.setTitle("Tetra Word contre l'ordinateur");
            else
                this.setTitle("Tetra Word contre un ami");
            
            this.setPreferredSize(new Dimension(1280,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/multi.jpg"));
            panel.setMaximumSize(new Dimension(1280, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1280, 768));
            
            // Fonts
            Font bigCopperplate  = new Font("Copperplate Gothic Bold",0,24);
            Font smallCopperplate  = new Font("Copperplate Gothic Bold",0,18);
            try{
                File fis = new File("data/COPRGTB.TTF");
                bigCopperplate = Font.createFont(Font.TRUETYPE_FONT, fis);
                smallCopperplate = Font.createFont(Font.TRUETYPE_FONT, fis);
                bigCopperplate = bigCopperplate.deriveFont((float)24.0);
                smallCopperplate = bigCopperplate.deriveFont((float)18.0);
            }
            catch (Exception e) {
                System.out.println("Police non chargée");
            }
            Font century = new Font("Century Gothic",0,20);
            
            
            /***** JEU 1 *****/
            Composants=new HashMap<String,JComponent>(); 
            
            // Boutons
            JWelcomeButton buttonRetour = new JWelcomeButton("Retour");
            buttonRetour.setFont(century);
            buttonRetour.setForeground(Color.WHITE);
            buttonRetour.setFocusPainted(false);
            buttonRetour.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        retourActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            
            JButton buttonWorddle1 = new JButton("");
            if(buttonWorddle1==null)exit(1);
            buttonWorddle1.setFont(century);
            buttonWorddle1.setForeground(Color.green);
            buttonWorddle1.setFocusPainted(false);
            Composants.put("Worddle",buttonWorddle1);
            
            JWelcomeButton buttonNiveau1 = new JWelcomeButton("");
            if(buttonNiveau1==null)exit(1);
            buttonNiveau1.setFont(century);
            buttonNiveau1.setForeground(Color.WHITE);
            buttonNiveau1.setFocusPainted(false);
            Composants.put("Niveau",buttonNiveau1);
            
            JWelcomeButton buttonScore1 = new JWelcomeButton("");
            if(buttonScore1==null)exit(1);
            buttonScore1.setFont(century);
            buttonScore1.setForeground(Color.WHITE);
            buttonScore1.setFocusPainted(false);
            Composants.put("Score",buttonScore1);
            
            JWelcomeButton buttonSaisie1 = new JWelcomeButton("");
            buttonSaisie1.setFont(century);
            buttonSaisie1.setForeground(Color.WHITE);
            buttonSaisie1.setFocusPainted(false);
            Composants.put("Saisie",buttonSaisie1);
            
            // Création du jeu
            Dictionary dictionary=new Dictionary();
            Games=new Vector<Game>();
            
            Game game1=new Game(this,dictionary,Composants,false);
            game1.start();
            Games.add(game1);
            
            // Grille de Jeu        
            JPanel grille1=Games.get(0).getGridInterface();
    
            // Labels
            JLabel labelTitle1 = new JLabel();
            labelTitle1.setFont(bigCopperplate);
            labelTitle1.setForeground(new Color(33,91,201));
            labelTitle1.setText("Vous");
            labelTitle1.setHorizontalAlignment(SwingConstants.CENTER);
            
            JLabel labelTime1 = new JLabel();
            labelTime1.setFont(smallCopperplate);
            labelTime1.setForeground(new Color(33,91,201));
            labelTime1.setText("Temps");
            Composants.put("Temps",labelTime1);
            
            JLabel labelWorddle1 = new JLabel();
            labelWorddle1.setFont(smallCopperplate);
            labelWorddle1.setForeground(new Color(33,91,201));
            labelWorddle1.setText("Worddle");
            
            JLabel labelNiveau1 = new JLabel();
            labelNiveau1.setFont(smallCopperplate);
            labelNiveau1.setForeground(new Color(33,91,201));
            labelNiveau1.setText("Niveau");

            JLabel labelScore1 = new JLabel();
            labelScore1.setFont(smallCopperplate);
            labelScore1.setForeground(new Color(33,91,201));
            labelScore1.setText("Score");

            JLabel labelSaisie1 = new JLabel();
            labelSaisie1.setFont(smallCopperplate);
            labelSaisie1.setForeground(new Color(33,91,201));
            labelSaisie1.setText("Saisie");
       
      
            // Prochaine piece        
            JPanel buttonPiece1 = Games.get(0).getNextInterface();
            
            
            /***** JEU 2 *****/
            Composants2=new HashMap<String,JComponent>(); 
            
            // Boutons
            
            JButton buttonWorddle2 = new JButton("");
            if(buttonWorddle2==null)exit(1);
            buttonWorddle2.setFont(century);
            buttonWorddle2.setBackground(Color.green);
            buttonWorddle2.setFocusPainted(false);
            Composants2.put("Worddle",buttonWorddle2);
            
            JWelcomeButton buttonNiveau2 = new JWelcomeButton("");
            if(buttonNiveau2==null)exit(1);
            buttonNiveau2.setFont(century);
            buttonNiveau2.setForeground(Color.WHITE);
            buttonNiveau2.setFocusPainted(false);
            Composants2.put("Niveau",buttonNiveau2);
            
            JWelcomeButton buttonScore2 = new JWelcomeButton("");
            if(buttonScore2==null)exit(1);
            buttonScore2.setFont(century);
            buttonScore2.setForeground(Color.WHITE);
            buttonScore2.setFocusPainted(false);
            Composants2.put("Score",buttonScore2);
            
            JWelcomeButton buttonSaisie2 = new JWelcomeButton("");
            buttonSaisie2.setFont(century);
            buttonSaisie2.setForeground(Color.WHITE);
            buttonSaisie2.setFocusPainted(false);
            Composants2.put("Saisie",buttonSaisie2);
            
            // Création du jeu
            Game game2=new Game(this,dictionary,Composants2,ia);
            game2.start();
            Games.add(game2);
            
            // Grille de Jeu        
            JPanel grille2=Games.get(1).getGridInterface();
    
            // Labels
            JLabel labelTitle2 = new JLabel();
            labelTitle2.setFont(bigCopperplate);
            labelTitle2.setForeground(new Color(33,91,201));
            if(ia)
                labelTitle2.setText("Ordinateur");
            else
                labelTitle2.setText("Votre Ami");
            
            labelTitle2.setHorizontalAlignment(SwingConstants.CENTER);
            
            JLabel labelTime2 = new JLabel();
            labelTime2.setFont(smallCopperplate);
            labelTime2.setForeground(new Color(33,91,201));
            labelTime2.setText("Temps");
            Composants2.put("Temps",labelTime2);
            
            JLabel labelWorddle2 = new JLabel();
            labelWorddle2.setFont(smallCopperplate);
            labelWorddle2.setForeground(new Color(33,91,201));
            labelWorddle2.setText("Worddle");
            
            JLabel labelNiveau2 = new JLabel();
            labelNiveau2.setFont(smallCopperplate);
            labelNiveau2.setForeground(new Color(33,91,201));
            labelNiveau2.setText("Niveau");

            JLabel labelScore2 = new JLabel();
            labelScore2.setFont(smallCopperplate);
            labelScore2.setForeground(new Color(33,91,201));
            labelScore2.setText("Score");

            JLabel labelSaisie2 = new JLabel();
            labelSaisie2.setFont(smallCopperplate);
            labelSaisie2.setForeground(new Color(33,91,201));
            labelSaisie2.setText("Saisie");
       
            // Prochaine piece        
            JPanel buttonPiece2 = Games.get(1).getNextInterface();


            GroupLayout jPanel1Layout = new GroupLayout(panel);
            panel.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addComponent(buttonRetour)
                    .addGap(1002, 1115, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(72, 72, 72)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(labelTitle1, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
                        .addComponent(grille1, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE))
                    .addGap(46, 46, 46)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(labelTime1, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(buttonPiece1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelNiveau1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonNiveau1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelScore1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonScore1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelSaisie1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonSaisie1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(labelWorddle2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(buttonWorddle1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(labelTitle2, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
                        .addComponent(grille2, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE))
                    .addGap(46, 46, 46)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(labelTime2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonPiece2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelNiveau2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonNiveau2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelScore2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonScore2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelSaisie2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonSaisie2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(labelWorddle2, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(buttonWorddle2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)))
                    .addGap(69, 69, 69))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(buttonRetour)
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(labelTitle2)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(labelTime2)))
                            .addGap(39, 39, 39)
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(buttonPiece2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                    .addGap(37, 37, 37)
                                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelWorddle2)
                                        .addComponent(buttonWorddle2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelNiveau2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonNiveau2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                    .addGap(30, 30, 30)
                                    .addComponent(labelScore2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonScore2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                    .addGap(35, 35, 35)
                                    .addComponent(labelSaisie2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonSaisie2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                .addComponent(grille2, GroupLayout.PREFERRED_SIZE, 536, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(labelTitle1)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(labelTime1)))
                            .addGap(39, 39, 39)
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(buttonPiece1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                    .addGap(37, 37, 37)
                                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelWorddle2)
                                        .addComponent(buttonWorddle1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelNiveau1)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonNiveau1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                    .addGap(30, 30, 30)
                                    .addComponent(labelScore1)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonScore1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                    .addGap(35, 35, 35)
                                    .addComponent(labelSaisie1)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonSaisie1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                .addComponent(grille1, GroupLayout.PREFERRED_SIZE, 536, GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(53, Short.MAX_VALUE))
            );
       
            Games.get(0).setOther(Games.get(1));
            Games.get(1).setOther(Games.get(0));
       
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.requestFocusInWindow();
    }
    
 /**
    * Fenêtre d'édition de pièce
 **/ 
    public void shapeEditor() throws IOException{
            // Création de la fenêtre
            this.setTitle("Editeur de piece");
            this.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/editeurPiece.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Fonts            
            Font smallCentury = new Font("Century Gothic",0,18);
            Font bigCentury = new Font("Century Gothic",0,24);
                       
            // Boutons
            JWelcomeButton buttonRetour = new JWelcomeButton("Retour");
            buttonRetour.setFont(smallCentury);
            buttonRetour.setForeground(Color.WHITE);
            buttonRetour.setFocusPainted(false);
            buttonRetour.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        retourActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            
            JWelcomeButton buttonNewPiece = new JWelcomeButton("Nouvelle pièce");
            buttonNewPiece.setFont(bigCentury);
            buttonNewPiece.setForeground(Color.WHITE);
            buttonNewPiece.setFocusPainted(false);
            
            JWelcomeButton chargerPiece = new JWelcomeButton("Charger pièce");
            chargerPiece.setFont(bigCentury);
            chargerPiece.setForeground(Color.WHITE);
            chargerPiece.setFocusPainted(false);
            
            JWelcomeButton savePiece = new JWelcomeButton("Sauvegarder");
            savePiece.setFont(bigCentury);
            savePiece.setForeground(Color.WHITE);
            savePiece.setFocusPainted(false);
            
            JPanel grillePiece = new JPanel(new GridLayout(4,4));
            Border whiteline = BorderFactory.createLineBorder(Color.WHITE,1);
            final Integer [][]tab=new Integer[4][4];
            final JButton [][]tabButton=new JButton[4][4];
            for(int i=0;i<4;++i){
                for(int j=0;j<4;++j){
                    JButton but=new JButton("");
                    but.setBackground(Color.GRAY);
                    but.setBorder(whiteline);
                    tabButton[i][j]=but;
                    tab[i][j]=0;
                    grillePiece.add(but);
                    but.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            if(evt.getSource() instanceof JButton){
                                JButton b=(JButton)evt.getSource();
                                
                                boolean quit=false;
                                for(int i=0;i<4;++i){
                                    for(int j=0;j<4;++j){
                                        if(tabButton[i][j]==b){
                                            if(tab[i][j]==0){
                                                b.setBackground(Color.red);
                                                tab[i][j]=1;
                                            }
                                            else{
                                                b.setBackground(Color.gray);
                                                tab[i][j]=0;
                                            }
                                        }
                                    }
                                    if(quit) break;
                                }
                            }

                        }
                    });
                }
            }
            
            savePiece.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        boolean quit=false;
                        for(int i=0;i<4;++i){
                            for(int j=0;j<4;++j){
                                if(tab[i][j]!=0){
                                    quit=true;
                                    break;
                                }
                            }
                            if(quit) break;
                        }
                        //Pièce vide
                        if(!quit) return;
                        
                        shapeType.shapeTypes.put(shapeType.shapeTypes.size(),tab);
                        shapeType.saveShapes();
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            buttonNewPiece.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    for(int i=0;i<4;++i){
                        for(int j=0;j<4;++j){
                            tab[i][j]=0;
                            tabButton[i][j].setBackground(Color.gray);
                        }
                    }
                }
            });
                    
            chargerPiece.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    int r = (int) (Math.random() * shapeTypes.size());
                    
                    for(int i=0;i<4;++i){
                        for(int j=0;j<4;++j){
                            tab[i][j]=shapeTypes.get(r)[i][j];
                            if(tab[i][j]==0)
                                tabButton[i][j].setBackground(Color.gray);
                            else
                                tabButton[i][j].setBackground(Color.red);
                        }
                    }
                }
            });
                    
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
            panel.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(55, 55, 55)
                            .addComponent(buttonRetour, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(119, 119, 119)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(buttonNewPiece, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chargerPiece, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(savePiece, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(156, 156, 156)
                            .addComponent(grillePiece, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(219, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addComponent(buttonRetour, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(207, 207, 207)
                            .addComponent(buttonNewPiece, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50)
                            .addComponent(chargerPiece, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(52, 52, 52)
                            .addComponent(savePiece, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(185, 185, 185)
                            .addComponent(grillePiece, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(228, Short.MAX_VALUE))
            );
            
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
   
 /**
    * Contient tous les évènements clavier
 **/ 
   class ClavierListener implements KeyListener{

        public void keyPressed(KeyEvent event) {
            
                int keyCode = event.getKeyCode();
                switch( keyCode ) {
                    //Joueur1
                    case KeyEvent.VK_Z:
                        Games.get(0).rotateUp();
                        Games.get(0).rafraichir();
                        break;
                    case KeyEvent.VK_S:
                        // handle down
                        Games.get(0).shapeFall(Games.get(0).currentShape,true);
                        Games.get(0).rafraichir();
                        break;
                    case KeyEvent.VK_Q:
                        // handle left
                        Games.get(0).moveShapeAside(-1,true);
                        Games.get(0).rafraichir();
                        break;
                    case KeyEvent.VK_D :
                        // handle right
                        Games.get(0).moveShapeAside(1,true);
                        Games.get(0).rafraichir();
                        break;
                    case KeyEvent.VK_SPACE :
                        Games.get(0).validate();
                        break;
                    case KeyEvent.VK_W :
                        Games.get(0).worddle();
                        break;
                        //Joueur 2
                    case KeyEvent.VK_UP:
                        if(Games.size()>1 && Games.get(1).intelligence==null){
                            Games.get(1).rotateUp();                        
                            Games.get(1).rafraichir();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        // handle down 
                        if(Games.size()>1 && Games.get(1).intelligence==null){
                            Games.get(1).shapeFall(Games.get(1).currentShape,true);
                            Games.get(1).rafraichir();
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        // handle left
                        if(Games.size()>1 && Games.get(1).intelligence==null){
                            Games.get(1).moveShapeAside(-1,true);
                            Games.get(1).rafraichir();
                        }
                        break;
                    case KeyEvent.VK_RIGHT :
                        // handle right
                        if(Games.size()>1 && Games.get(1).intelligence==null){
                            Games.get(1).moveShapeAside(1,true);
                            Games.get(1).rafraichir();
                        }
                        break;
                    case KeyEvent.VK_ENTER :                        
                        if(Games.size()>1 && Games.get(1).intelligence==null){
                            Games.get(1).validate();
                        }
                        break;
                    case KeyEvent.VK_M :
                        if(Games.size()>1 && Games.get(1).intelligence==null){
                            Games.get(1).worddle();
                        }
                        break;
					
                    case KeyEvent.VK_P :
                        pauseGames();
                try {
                    pausePage();
                } catch (IOException ex) {
                    Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                        break;
                    case KeyEvent.VK_J:
                        try {
                            
                            Game.saveGame(Games);
                        } catch (IOException ex) {
                            Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                }
            
          
        }
        
        public void keyReleased(KeyEvent event) {
         
        }

        public void keyTyped(KeyEvent event) {
         
        }   	
    }  
   
 /**
    * Permet de mettre le jeu en pause
 **/ 
   public void pauseGames(){
       for(Game g:Games){
                            if(g.pause)
                                g.pause=false;
                            else
                                g.pause=true;
                        }
   }

 /**
    * Programme principal
 **/ 
    public static void main(String[] args) throws IOException{
        MainGame game=new MainGame();
        game.welcomePage();
    }
}
