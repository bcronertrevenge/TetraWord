/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author bruno
 */
public class IA {
    Game game;
    private boolean find;
    Moves move;
    Shape currentShapeIA;
    
    public IA(Game game){
        this.game=game;
        find=true;
        move=new Moves();
        currentShapeIA=null;
    }
    
    public void play(){
        
        switch(game.getMode()){
            case 0:
                tetris();
                find=true;
                if(System.currentTimeMillis()-game.worddleLast>=game.worddleReload){
                    game.worddle();
                }
                break;
            case 1:
                anagramme();
                break;
            case 2:
                worddle();
                break;
            default:
                break;
        }
    }
    
    ////////////////////////////// TETRIS ////////////////////////////////////
    public void tetris(){
        if(currentShapeIA!=game.currentShape){
            //Recherche chemin et rempli le vecteur de mouvements à faire
            move=null;
            lookPath();
            currentShapeIA=game.currentShape;
        }
        else if(move.getRotation()>0){
            //Tourne la pièce
            game.rotateUp();
            move.setRotation(move.getRotation()-1);
        }
        else if(move.getDeplacement()>0){
            //Bouge à droite
            game.moveShapeAside(1,true);
            move.setDeplacement(move.getDeplacement()-1);
        }
        else if(move.getDeplacement()<0){
            //Bouge à gauche
            game.moveShapeAside(-1,true);
            move.setDeplacement(move.getDeplacement()+1);
        }
        else {
            //Descend la pièce
            game.shapeFall(game.currentShape,true);
        }
    }
    
    public void lookPath(){
        Vector<Moves> moves=new Vector<Moves>();
        int Ox=game.currentShape.x;
        
        for(int i=0;i<=Ox;++i){
            game.moveShapeAside(-1, false);
        }
        
        //Deplacement gauche droite
        for(int i=0;i<10-game.currentShape.width;++i){
            //Rotation
            for(int j=0;j<4;++j){
                
                //Descente de la pièce
                while(game.shapeFall(game.currentShape,false)==0){}
                
                //Verif ligne
                int nblignes=0;
                boolean good;
                for(int k=game.currentShape.y;k<=game.currentShape.y+game.currentShape.height;++k){
                    good=true;
                    for(int l=0;l<10;++l){
                        if(game.getGrid()[k][l].isEmpty()){
                            good=false;
                            break;
                        }
                    }
                    if(good) nblignes++;
                }
                
                //Verif Trous
                int trous=0;
                
                for(int l=game.currentShape.x;l<=game.currentShape.x+game.currentShape.width;++l){
                    good=false;
                    for(int k=game.currentShape.y;k<=game.currentShape.y+game.currentShape.height+1;++k){
                        if(k>19) continue;
                        if(!game.getGrid()[k][l].isEmpty()){
                            good=true;
                        }
                        else if(game.getGrid()[k][l].isEmpty() && good){
                            trous++;
                        }
                    }
                }                
                moves.add(new Moves((i-Ox),j,nblignes,game.currentShape.y,trous));
                
                clearLastMove();
                game.rotateUp();
            }
            game.moveShapeAside(1, false);
        }
        
        //Replacement de la pièce
        while(Ox<game.currentShape.x){
            game.moveShapeAside(-1, false);
        }
        chooseBestMove(moves);
    }
    
    public void chooseBestMove(Vector<Moves> moves){
        
        if(moves.isEmpty()) return;
        
        int maxLigne=0;
        int maxHauteur=0;
        int minTrous=200;
        move=null;
        Moves moveRes=null;
        
        for(int i=0;i<moves.size();++i){
            // Choix du mouvement qui enlève le plus de lignes
            if(moves.get(i).nblignes>=maxLigne){
                if(moves.get(i).nblignes>maxLigne){
                    maxHauteur=moves.get(i).hauteurMax;
                    minTrous=moves.get(i).trous;
                    maxLigne=moves.get(i).nblignes;
                    moveRes=moves.get(i);
                }
                // Choix du mouvement qui fait le moins de trous possible
                else if(moves.get(i).trous<=minTrous){
                    if(moves.get(i).trous<minTrous){
                        maxHauteur=moves.get(i).hauteurMax;
                        minTrous=moves.get(i).trous;
                        moveRes=moves.get(i);
                    }
                    // Choix du mouvement qui place une pièce le moins haut possible
                    else if(moves.get(i).hauteurMax>maxHauteur){
                        maxHauteur=moves.get(i).hauteurMax;
                        moveRes=moves.get(i);
                    }     
                }
            }
        }
        
        
        if(moveRes==null){
             int rand = (int)((Math.random() * (moves.size())));
             moveRes=moves.get(rand);
        }
        
        move=moveRes;
    }
    public void clearLastMove(){
        
        for(int i=game.currentShape.y;i<=game.currentShape.y+game.currentShape.height;++i){
            for(int j=game.currentShape.x;j<=game.currentShape.x+game.currentShape.width;++j){
                if(!game.getGrid()[i][j].isEmpty()){
                    if(game.getGrid()[i][j].getShape()==game.currentShape){
                        game.getGrid()[i-game.currentShape.y][j].setShapeBrick(game.currentShape, game.getGrid()[i][j].getBrick());
                        game.getGrid()[i][j].setShapeBrick(null,null);
                    }
                }
            }
        }
        
        game.currentShape.y=0;
    }
    
    ////////////////////////////// ANAGRAMME ////////////////////////////////////
    public void anagramme(){
        if(game.anagLine==-1 || !find) return;
        
        find=findWordAnagramme("");
        System.out.println(game.mot);
        if(find){   
            game.validate();
        }

       
    }
    
    private boolean findWordAnagramme(String mot){
        boolean res;
        char lettre;
        for(int i=0;i<10;++i){
             if(game.getGrid()[game.anagLine][i].isSelected){
                continue; 
             }
             
             lettre=Character.toLowerCase(game.getGrid()[game.anagLine][i].getBrick().lettre);
             
             if(game.dictionary.line.contains(mot+lettre) && mot.length()>=game.anagLettres){
                 game.getGrid()[game.anagLine][i].isSelected=true;
                 game.mot=mot+lettre;
                 return true;
             }
             else if(game.dictionary.containsRegEx(mot+lettre+".")){
                 game.getGrid()[game.anagLine][i].isSelected=true;
                 res=findWordAnagramme(mot+lettre);
                 if(res) return true;
                 game.getGrid()[game.anagLine][i].isSelected=false;
             }
        }
        return false;
    }
        ////////////////////////////// WORDDLE ////////////////////////////////////
    public void worddle(){
               
        boolean word;
        while(find){
            find=false;
            for(int i=0;i<game.worddleStartX.size();++i){
                if(!game.getGrid()[game.worddleStartY.get(i)][game.worddleStartX.get(i)].noWord){
                    word=findWordWorddle(String.valueOf(Character.toLowerCase(game.getGrid()[game.worddleStartY.get(i)][game.worddleStartX.get(i)].getBrick().lettre)),game.worddleStartX.get(i),game.worddleStartY.get(i));
                    if(!word) game.getGrid()[game.worddleStartY.get(i)][game.worddleStartX.get(i)].noWord=true;
                    else {System.out.println(game.mot); game.validate(); find=true;}
                }
            }
            System.out.println(find);
        }
        System.out.println("Je trouve plus de mot :/ ");
        game.validate();
    }
    
    public boolean findWordWorddle(String mot,int x, int y){
        char lettre;
        boolean res;
        //Gauche
        if(x!=0){
            if(!game.getGrid()[y][x-1].isEmpty()){
                if(!game.getGrid()[y][x-1].isSelected && !game.getGrid()[y][x-1].isStart){
                    lettre=Character.toLowerCase(game.getGrid()[y][x-1].getBrick().lettre);
                    
                    //Mot Correct
                    if(game.dictionary.line.contains(mot+lettre)){
                        game.getGrid()[y][x-1].isSelected=true;
                        game.mot=mot+lettre;
                        return true;
                    }
                    //Partie du mot
                    else if(game.dictionary.containsRegEx(mot+lettre+".")){
                        game.getGrid()[y][x-1].isSelected=true;
                        res=findWordWorddle(mot+lettre,x-1,y);
                        if(res) return true;
                        game.getGrid()[y][x-1].isSelected=false;
                    }
                }
            }
        }
        
        //Haut
        if(y!=0){
            if(!game.getGrid()[y-1][x].isEmpty()){
                if(!game.getGrid()[y-1][x].isSelected && !game.getGrid()[y-1][x].isStart){
                    lettre=Character.toLowerCase(game.getGrid()[y-1][x].getBrick().lettre);
                    
                    //Mot Correct
                    if(game.dictionary.line.contains(mot+lettre)){
                        game.getGrid()[y-1][x].isSelected=true;
                        game.mot=mot+lettre;
                        return true;
                    }
                    //Partie du mot
                    else if(game.dictionary.containsRegEx(mot+lettre+".")){
                        game.getGrid()[y-1][x].isSelected=true;
                        res=findWordWorddle(mot+lettre,x,y-1);
                        if(res) return true;
                        game.getGrid()[y-1][x].isSelected=false;
                    }
                }
            }
        }
        
        //Droite
        if(x!=9){
            if(!game.getGrid()[y][x+1].isEmpty()){
                if(!game.getGrid()[y][x+1].isSelected && !game.getGrid()[y][x+1].isStart){
                    lettre=Character.toLowerCase(game.getGrid()[y][x+1].getBrick().lettre);
                    
                    //Mot Correct
                    if(game.dictionary.line.contains(mot+lettre)){
                        game.getGrid()[y][x+1].isSelected=true;
                        game.mot=mot+lettre;
                        return true;
                    }
                    //Partie du mot
                    else if(game.dictionary.containsRegEx(mot+lettre+".")){
                        game.getGrid()[y][x+1].isSelected=true;
                        res=findWordWorddle(mot+lettre,x+1,y);
                        if(res) return true;
                        game.getGrid()[y][x+1].isSelected=false;
                    }
                }
            }
        }
        
        //Bas
        if(y!=19){
            if(!game.getGrid()[y+1][x].isEmpty()){
                if(!game.getGrid()[y+1][x].isSelected && !game.getGrid()[y+1][x].isStart){
                    lettre=Character.toLowerCase(game.getGrid()[y+1][x].getBrick().lettre);
                    
                    //Mot Correct
                    if(game.dictionary.line.contains(mot+lettre)){
                        game.getGrid()[y+1][x].isSelected=true;
                        game.mot=mot+lettre;
                        return true;
                    }
                    //Partie du mot
                    else if(game.dictionary.containsRegEx(mot+lettre+".")){
                        game.getGrid()[y+1][x].isSelected=true;
                        res=findWordWorddle(mot+lettre,x,y+1);
                        if(res) return true;
                        game.getGrid()[y+1][x].isSelected=false;
                    }
                }
            }
        }
        
        return false;
    }

}
