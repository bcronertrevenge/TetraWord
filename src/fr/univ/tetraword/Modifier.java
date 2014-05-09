package fr.univ.tetraword;

import static fr.univ.tetraword.modifierType.allowWorddle;
import static fr.univ.tetraword.modifierType.bonus;
import static fr.univ.tetraword.modifierType.directFall;
import static fr.univ.tetraword.modifierType.explode;
import static fr.univ.tetraword.modifierType.malus;
import static fr.univ.tetraword.modifierType.reverse;
import static fr.univ.tetraword.modifierType.reverseOther;
import static fr.univ.tetraword.modifierType.slowFall;
import static fr.univ.tetraword.modifierType.speedFall;
import static fr.univ.tetraword.modifierType.switchGrid;
import java.awt.Color;
import java.io.Serializable;

/**
    * Modifier est la classe représentant un modificateur du jeu
 **/
public class Modifier {
    modifierType type;
    Game game;
    Game other;
 
/**
    * Constructeur avec paramètres
    * @param other
    * deuxième jeu si on est en mode multijoueur, il sera nul sinon
    * @param game
    * le jeu principal
 **/
    public Modifier(Game other, Game game){
        this.game=game;
        this.other=other;
        int a;
        
        if(other!=null)
            a=(int)(Math.random()*10);
        else
            a=(int)(Math.random()*8);
        switch(a){
            case 0:
                type=speedFall;
                break;
            case 1:
                type=slowFall;
                break;
            case 2:
                type=directFall;
                break;
            case 3:
                type=reverse;
                break;
            case 4:
                type=allowWorddle;
                break;
            case 5:
                type=bonus;
                break;
            case 6:
                type=malus;
                break;
            case 7:
                type=explode;
                break;
            case 8:
                type=reverseOther;
                break;
            case 9:
                type=switchGrid;
                break;
            default:
                break;
        }
    }

/**
    *Permet d'activer un modificateur existant
    * @param from
    * indique comment la pièce a attrapé le modificateur
    * 0 si elle vient du haut, 1 si elle vient de la droite, 2 si elle vient de la gauche
 **/
    public boolean activate(int from){ //0 : shapeFall, 1 : MoveAside : right, 2 : MoveAside : left
        boolean terminate=false;
        
        switch(type){
            case speedFall:
                game.fallTime-=50;
                break;
            case slowFall:
                game.fallTime+=50;
                break;
            case directFall:
                int end=0;
                boolean in=false;
                int x,y;
                             
                x=game.currentShape.x;
                y=game.currentShape.y;
                
                
                while(end==0){
                    for(int i=game.currentShape.x;i<=game.currentShape.x+game.currentShape.width;++i)
                        game.getGrid()[game.currentShape.y][i].temporaryEffect=Color.green;
                    end=game.shapeFall(game.currentShape,false);
                    if(end==0) in=true;
                }
                if(!in) break;

                switch(from){
                    case 0:
                        for(int i=x;i<=x+game.currentShape.width;++i){
                            game.getGrid()[y-1][i].setShapeBrick(null, null);
                        }
                        break;
                    case 1:
                        for(int i=y;i<=y+game.currentShape.height;++i)
                            game.getGrid()[i][x+game.currentShape.width+1].setShapeBrick(null, null);
                        break;
                    case 2:
                        for(int i=y;i<=y+game.currentShape.height;++i)
                            game.getGrid()[i][x-1].setShapeBrick(null, null);
                        break;
                    default :
                        break;
                }
                terminate=true;
                break;
            case reverse:
                game.getGridInterface().reverse=!game.getGridInterface().reverse;
                break;
            case bonus:
                game.score+=50;
                if(game.composants.containsKey("Score")){
                    game.composants.get("Score").setForeground(Color.green);
                    game.composants.get("Score").setText(String.valueOf((int)game.score));
                    game.composants.get("Score").repaint();
                }
                break;
            case malus:
                game.score-=50;
                if(game.composants.containsKey("Score")){
                    game.composants.get("Score").setForeground(Color.red);
                    game.composants.get("Score").setText(String.valueOf((int)game.score));
                    game.composants.get("Score").repaint();
                }
                break;
            case explode:
                //Dans la shape
                int explosionwidth=2;
                for(int i=-explosionwidth;i<game.currentShape.height+explosionwidth;++i){
                    for(int j=-explosionwidth;j<game.currentShape.width+explosionwidth;++j){
                        if(game.currentShape.y + i >= 0 && game.currentShape.y + i < 20 && game.currentShape.x + j >= 0 && game.currentShape.x + j < 10){
                            if(game.getGrid()[game.currentShape.y + i][game.currentShape.x + j].getShape()!=null)
                                game.getGrid()[game.currentShape.y + i][game.currentShape.x + j].setShapeBrick(null, null);
                            else if(game.getGrid()[game.currentShape.y + i][game.currentShape.x + j].getModifier()!=null)
                                game.getGrid()[game.currentShape.y + i][game.currentShape.x + j].setModifier(null);
                            game.getGrid()[game.currentShape.y + i][game.currentShape.x + j].temporaryEffect=Color.red;
                        }
                    }
                }
                game.currentShape=null;
                game.newShapeInGame();
                terminate=true;
                break;
            case allowWorddle:
                game.worddleLast=0;
                break;
            case switchGrid:
                other.clearBox();
                other.whiteOut();
                for(int i=0;i<20;++i){
                    for(int j=0;j<10;++j){
                        if(game.getGrid()[i][j].getShape()==game.currentShape && game.getGrid()[i][j].getBrick()!=null)
                            game.getGrid()[i][j].setShapeBrick(null, null);
                        if(other.getGrid()[i][j].getShape()==other.currentShape && other.getGrid()[i][j].getBrick()!=null)
                            other.getGrid()[i][j].setShapeBrick(null, null);
                        
                        game.getGrid()[i][j].boxChange(other.getGrid()[i][j]);
                        
                    }
                }
                game.rafraichir();
                other.rafraichir();
                
                game.currentShape=null;
                other.currentShape=null;
                game.newShapeInGame();   
                other.newShapeInGame();  
                if(other.getMode()==2){
                    other.setMode(0);
                    other.suppressionWorddle();
                }
                else
                    other.setMode(0);
                
                
                other.mot="";
                other.anagLine=-1;
                        
                terminate=true;
                break;
            case reverseOther:
                other.getGridInterface().reverse=!other.getGridInterface().reverse;
                break;
            default:
                break;
        }
        return terminate;
    }
    
}
