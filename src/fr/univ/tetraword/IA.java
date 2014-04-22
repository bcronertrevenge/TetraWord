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
    
    public void tetris(){
        //System.out.println("Tetris");
    }
    
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
        
    public void worddle(){
        System.out.println("Worddle");
    }
    

}
