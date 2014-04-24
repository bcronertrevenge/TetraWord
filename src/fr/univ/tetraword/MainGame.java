package fr.univ.tetraword;

import fr.univ.graphicinterface.JWelcomeButton;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class MainGame extends JFrame {
    Vector<Game> Games;
    HashMap<String,JWelcomeButton> Composants;
    HashMap<String,JWelcomeButton> Composants2;
    
    public MainGame(){
        addKeyListener(new ClavierListener());
    }
    
    /* Fonction pour mettre une image en arrière plan */
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
    
    public void jouerActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        gameMode();
    }
    
    public void soloActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        gameSolo();
    }
    
    public void ordiActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        gameOrdi();
    }
    
    public void retourActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        welcomePage();
    }
    
    public void chargerActionPerformed(java.awt.event.ActionEvent evt) throws IOException{                                         
        loadGamePage();
    }
    
    public void quitter(java.awt.event.ActionEvent evt) throws IOException{                                         
        this.dispose();
    }
    
    /* Page d'accueil */
    public void welcomePage() throws IOException{
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
                    try {
                        quitter(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
           
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void gameMode() throws IOException{
            // Création de la fenêtre
            this.setTitle("Jouer à Tretraword");
            this.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/mode.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Fonts
            Font copperplate = new Font("Copperplate Gothic Bold",0,26);
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
                        soloActionPerformed(evt);
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
    
    /* Jeu en solo */
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
            Font copperplate = new Font("Copperplate Gothic Bold",0,26);
            Font century = new Font("Century Gothic",0,26);
            
            Composants=new HashMap<String,JWelcomeButton>();      
            
            JWelcomeButton buttonNiveau = new JWelcomeButton("");
            if(buttonNiveau==null)exit(1);
            buttonNiveau.setFont(century);
            buttonNiveau.setForeground(Color.WHITE);
            buttonNiveau.setFocusPainted(false);
            Composants.put("Niveau",buttonNiveau);
            
            JWelcomeButton buttonScore = new JWelcomeButton("");
            if(buttonScore==null)exit(1);
            buttonScore.setFont(century);
            buttonScore.setForeground(Color.WHITE);
            buttonScore.setFocusPainted(false);
            Composants.put("Score",buttonScore);
            
            JWelcomeButton buttonSaisie = new JWelcomeButton("");
            buttonSaisie.setFont(century);
            buttonSaisie.setForeground(Color.WHITE);
            buttonSaisie.setFocusPainted(false);
            Composants.put("Saisie",buttonSaisie);
            
            // Création du jeu
            Dictionary dictionary=new Dictionary();
            Games=new Vector<Game>();
            
            boolean intelligence=false;
            Game game=new Game(this,dictionary,false,Composants,intelligence);
            game.start();
            Games.add(game);
            
            // Grille de Jeu        
            JPanel grille=Games.get(0).getGridInterface();

                       
            // Labels
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
           

            JLabel labelPiece = new JLabel();
            labelPiece.setFont(copperplate);
            labelPiece.setForeground(new Color(33,91,201));
            labelPiece.setText("Prochaine pièce");
            
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
            
            // Prochaine piece        
            JPanel buttonPiece = Games.get(0).getNextInterface();


        GroupLayout jPanel1Layout = new GroupLayout(panel);
        panel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonRetour, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(grille, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(buttonPiece, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                                        .addGap(173, 173, 173))
                                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(labelNiveau, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(labelScore, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                            .addComponent(labelSaisie, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                        .addGap(48, 48, 48)
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(buttonNiveau, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                            .addComponent(buttonScore, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                            .addComponent(buttonSaisie, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                                        .addGap(136, 136, 136))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(157, 157, 157)
                                .addComponent(labelPiece,GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 162, Short.MAX_VALUE))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonRetour, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(grille, GroupLayout.PREFERRED_SIZE, 631, GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelPiece, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonPiece, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNiveau, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonNiveau, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(labelScore, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonScore, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(labelSaisie, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonSaisie, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
       
            
       
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.requestFocusInWindow();
    }
    
  /* Jeu contre l'ordinateur */
    public void gameOrdi() throws IOException{
            // Changement du titre
            this.setTitle("Tetra Word contre l'ordinateur");
            this.setPreferredSize(new Dimension(1280,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/multi.jpg"));
            panel.setMaximumSize(new Dimension(1280, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1280, 768));
            
            // Fonts
            Font bigCopperplate = new Font("Copperplate Gothic Bold",0,24);
            Font smallCopperplate = new Font("Copperplate Gothic Bold",0,18);
            Font century = new Font("Century Gothic",0,20);
            
            
            /***** JEU 1 *****/
            Composants=new HashMap<String,JWelcomeButton>(); 
            
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
            
            boolean intelligence=false;
            Game game1=new Game(this,dictionary,false,Composants,intelligence);
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
            Composants2=new HashMap<String,JWelcomeButton>(); 
            
            // Boutons
            
            JButton buttonWorddle2 = new JButton("");
            if(buttonWorddle2==null)exit(1);
            buttonWorddle2.setFont(century);
            buttonWorddle2.setForeground(Color.green);
            buttonWorddle2.setFocusPainted(false);
            
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
            intelligence=false;
            Game game2=new Game(this,dictionary,false,Composants2,intelligence);
            game2.start();
            Games.add(game2);
            
            // Grille de Jeu        
            JPanel grille2=Games.get(1).getGridInterface();
    
            // Labels
            JLabel labelTitle2 = new JLabel();
            labelTitle2.setFont(bigCopperplate);
            labelTitle2.setForeground(new Color(33,91,201));
            labelTitle2.setText("Ordinateur");
            labelTitle2.setHorizontalAlignment(SwingConstants.CENTER);
            
            JLabel labelTime2 = new JLabel();
            labelTime2.setFont(smallCopperplate);
            labelTime2.setForeground(new Color(33,91,201));
            labelTime2.setText("Temps");
            
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
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addComponent(buttonRetour)
                    .addGap(1002, 1115, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(72, 72, 72)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(grille1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(46, 46, 46)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(labelTime1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(buttonPiece1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelNiveau1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonNiveau1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelScore1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonScore1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelSaisie1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonSaisie1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(labelWorddle2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(buttonWorddle1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(grille2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(46, 46, 46)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(labelTime2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonPiece2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelNiveau2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonNiveau2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelScore2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonScore2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelSaisie2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonSaisie2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(labelWorddle2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(buttonWorddle2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(69, 69, 69))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(buttonRetour)
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelTitle2)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(labelTime2)))
                            .addGap(39, 39, 39)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(buttonPiece2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(37, 37, 37)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelWorddle2)
                                        .addComponent(buttonWorddle2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelNiveau2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonNiveau2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(30, 30, 30)
                                    .addComponent(labelScore2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonScore2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(35, 35, 35)
                                    .addComponent(labelSaisie2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonSaisie2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(grille2, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelTitle1)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(labelTime1)))
                            .addGap(39, 39, 39)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(buttonPiece1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(37, 37, 37)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelWorddle2)
                                        .addComponent(buttonWorddle1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelNiveau1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonNiveau1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(30, 30, 30)
                                    .addComponent(labelScore1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonScore1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(35, 35, 35)
                                    .addComponent(labelSaisie1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonSaisie1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(grille1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(53, Short.MAX_VALUE))
            );
       
            
       
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.requestFocusInWindow();
    }
    
    /* Page Principale */
    public void loadGamePage() throws IOException{
            // On change le titre
            this.setTitle("Charger une partie");
            this.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/game.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Fonts
            Font copperplate = new Font("Copperplate Gothic Bold",0,26);
            Font century = new Font("Century Gothic",0,26);
                   
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
   
    //Lecture clavier
   class ClavierListener implements KeyListener{

        public void keyPressed(KeyEvent event) {
            
              int keyCode = event.getKeyCode();
                switch( keyCode ) { 
                    //Joueur 1
                    case KeyEvent.VK_UP:
                        
                        Games.get(0).rotateUp();                        
                        Games.get(0).rafraichir();
                        break;
                    case KeyEvent.VK_DOWN:
                        // handle down 
                        Games.get(0).shapeFall(Games.get(0).currentShape);
                        Games.get(0).rafraichir();
                        break;
                    case KeyEvent.VK_LEFT:
                        // handle left
                        Games.get(0).moveShapeAside(-1);
                        Games.get(0).rafraichir();
                        break;
                    case KeyEvent.VK_RIGHT :
                        // handle right
                        Games.get(0).moveShapeAside(1);
                        Games.get(0).rafraichir();
                        break;
                    case KeyEvent.VK_ENTER :                        
                        Games.get(0).validate();
                        break;
                    case KeyEvent.VK_M :
                        Games.get(0).worddle();
                        break;
                 }
          
        }

        public void keyReleased(KeyEvent event) {
         
        }

        public void keyTyped(KeyEvent event) {
         
        }   	
    }  
   
   //Petite Pause
    private void pause(){
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
    }
    
    /* Programme Principal */
    public static void main(String[] args) throws IOException{
        MainGame game=new MainGame();
        game.welcomePage();
    }
}
