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
        
        a=2;
        
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
                    end=game.shapeFall(game.currentShape);
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
