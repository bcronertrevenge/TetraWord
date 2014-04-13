package fr.univ.tetraword;

import javax.swing.JPanel;

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
