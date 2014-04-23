/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

/**
 *
 * @author bruno
 */
public class IA {
    Game game;
    private boolean find;
    
    public IA(Game game){
        this.game=game;
        find=true;
    }
    
    public void play(){
        
        switch(game.getMode()){
            case 0:
                tetris();
                find=true;
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
        //System.out.println("Tetris");
    }
    
    public void getLowestLine(){
        
    }
    
    ////////////////////////////// ANAGRAMME ////////////////////////////////////
    public void anagramme(){
        if(game.anagLine==-1 || !find) return;
        
        find=findWordAnagramme("");
        System.out.println(game.mot);
        if(find){   
            game.validate();
            game.rafraichir();
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
