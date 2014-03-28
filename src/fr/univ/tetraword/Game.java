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
public class Game {
    Shape currentShape;
    int score;
    int level;
    Dictionary dictionary;
    
    public Game(){
        score=0;
        level=0;
        currentShape=null;
    }
}
