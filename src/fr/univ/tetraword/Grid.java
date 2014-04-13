package fr.univ.tetraword;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Grid extends JPanel {
    private Box grid[][];
    public JPanel gridInterface;
    
    public Grid(GridLayout gridlay){
        grid=new Box[20][10];
        gridInterface = new JPanel(gridlay);
        
        // Initialisation de grid
        for(int i=0;i<20;++i){
            for(int j=0;j<10;++j)
                grid[i][j]=new Box();
        }

        // Initialisation de gridInterface
        Border whiteline = BorderFactory.createLineBorder(Color.WHITE,1);
        for (int i=0;i<20;++i){
            for (int j=0;j<10;++j){
                JPanel jcase =new JPanel();
                jcase.setBackground(Color.gray);
                System.out.println("Je suis passe");
                jcase.setBorder(whiteline);
                gridInterface.add(jcase);
            } 
        }
    }
    
    public Box[][] getGrid(){
        return grid;
    }
    
    public JPanel getGridInterface(){
        return gridInterface;
    }
    
}
