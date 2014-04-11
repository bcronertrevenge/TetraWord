/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.tetraword;

import javax.swing.JPanel;

/**
 *
 * @author bruno
 */
public class Grid extends JPanel {
    private Box grid[][];
    
    public Grid(){
        grid=new Box[20][10];
                
        // Initialisation de Grid
        for(int i=0;i<20;++i){
            for(int j=0;j<10;++j)
                grid[i][j]=new Box();
        }
    }
    
    public Box[][] getGrid(){
        return grid;
    }
}
