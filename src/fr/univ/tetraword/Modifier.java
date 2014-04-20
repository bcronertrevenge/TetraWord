/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import static fr.univ.tetraword.modifierType.allowWorddle;
import static fr.univ.tetraword.modifierType.bonus;
import static fr.univ.tetraword.modifierType.directFall;
import static fr.univ.tetraword.modifierType.explode;
import static fr.univ.tetraword.modifierType.malus;
import static fr.univ.tetraword.modifierType.reverse;
import static fr.univ.tetraword.modifierType.slowFall;
import static fr.univ.tetraword.modifierType.speedFall;
import static fr.univ.tetraword.modifierType.switchGrid;



/**
 *
 * @author bruno
 */
public class Modifier {
    modifierType type;
    Game game;
    
    public Modifier(boolean multi, Game game){
        this.game=game;
        int a;
        
        if(multi)
            a=(int)(Math.random()*9);
        else
            a=(int)(Math.random()*8);
        
        a=6;
        
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
                type=bonus;
                break;
            case 5:
                type=malus;
                break;
            case 6:
                type=explode;
                break;
            case 7:
                type=allowWorddle;
                break;
            case 8:
                type=switchGrid;
                break;
            default:
                break;
        }
    }
    
    public boolean activate(){
        System.out.println("Activate");
        boolean terminate=false;
        switch(type){
            case speedFall:
                game.fallTime-=50;
                break;
            case slowFall:
                game.fallTime+=50;
                break;
            case directFall:
                int quit=0;
                while(quit==0){
                    quit=game.shapeFall(game.currentShape);
                }
                break;
            case reverse:
                break;
            case bonus:
                game.score+=50;
                break;
            case malus:
                game.score-=50;
                break;
            case explode:
                //Dans la shape
                int explosionwidth=2;
                for(int i=-explosionwidth;i<game.currentShape.height+explosionwidth;++i){
                    for(int j=-explosionwidth;j<game.currentShape.width+explosionwidth;++j){
                        if(game.currentShape.y + i >= 0 && game.currentShape.y + i < 20 && game.currentShape.x + j >= 0 && game.currentShape.x + j < 10)
                            if(game.getGrid()[game.currentShape.y + i][game.currentShape.x + j].getShape()!=null)
                                game.getGrid()[game.currentShape.y + i][game.currentShape.x + j].setShapeBrick(null, null);
                            else if(game.getGrid()[game.currentShape.y + i][game.currentShape.x + j].getModifier()!=null)
                                game.getGrid()[game.currentShape.y + i][game.currentShape.x + j].setModifier(null);
                    }
                }
                game.currentShape=null;
                game.newShapeInGame();
                terminate=true;
                game.rafraichir();
                break;
            case allowWorddle:
                game.worddleLast=0;
                break;
            case switchGrid:
                break;
            default:
                break;
        }
        return terminate;
    }
    
}
