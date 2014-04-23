package fr.univ.tetraword;

import fr.univ.graphicinterface.JWelcomeButton;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class MainGame extends JFrame {
    Vector<Game> Games;    
    Vector<JWelcomeButton> Buttons;
    
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
            JPanel panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/game.jpg"));
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
                        soloActionPerformed(evt);
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
                            .addGap(405, 405, 405)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(labelTitre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonSolo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonOrdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonAmi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelMulti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelSolo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addContainerGap(328, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(buttonRetour)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(labelTitre, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(103, 103, 103)
                    .addComponent(labelSolo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(31, 31, 31)
                    .addComponent(buttonSolo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(73, 73, 73)
                    .addComponent(labelMulti, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(27, 27, 27)
                    .addComponent(buttonOrdi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(38, 38, 38)
                    .addComponent(buttonAmi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(135, Short.MAX_VALUE))
            );
            
            
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /* Page Principale */
    public void gameSolo() throws IOException{
            // Changement du titre
            this.setTitle("Vous jouez à Tetra Word");
            this.setPreferredSize(new Dimension(1024,768));
                        
            // Arrière plan
            JPanel panel = setBackgroundImage(this, new File("src/fr/univ/graphicinterface/game.jpg"));
            panel.setMaximumSize(new Dimension(1024, 768));
            panel.setMinimumSize(new Dimension(600, 400));
            panel.setPreferredSize(new Dimension(1024, 768));
            
            // Fonts
            Font copperplate = new Font("Copperplate Gothic Bold",0,26);
            Font century = new Font("Century Gothic",0,26);
                        
            // Création du jeu
            Dictionary dictionary=new Dictionary();
            Games=new Vector<Game>();
            Buttons=new Vector<JWelcomeButton>();
            boolean intelligence=false;
            Game game=new Game(this,dictionary,false,Buttons,intelligence);
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
            
            JWelcomeButton buttonNiveau = new JWelcomeButton(String.valueOf(Games.get(0).getLevel()));
            buttonNiveau.setFont(century);
            buttonNiveau.setForeground(Color.WHITE);
            buttonNiveau.setFocusPainted(false);
            Buttons.add(buttonNiveau);
            
            JWelcomeButton buttonScore = new JWelcomeButton(String.valueOf(Games.get(0).score));
            buttonScore.setFont(century);
            buttonScore.setForeground(Color.WHITE);
            buttonScore.setFocusPainted(false);
            Buttons.add(buttonScore);
            // Prochaine piece        
            JPanel buttonPiece = Games.get(0).getNextInterface();
            
            JWelcomeButton buttonSaisie = new JWelcomeButton(Games.get(0).mot);
            buttonSaisie.setFont(century);
            buttonSaisie.setForeground(Color.WHITE);
            buttonSaisie.setFocusPainted(false);
            Buttons.add(buttonSaisie);


        javax.swing.GroupLayout jPanel1Layout = new GroupLayout(panel);
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
                            .addComponent(labelSaisie, GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonSaisie, GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(49, Short.MAX_VALUE))
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
