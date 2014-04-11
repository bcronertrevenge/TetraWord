
import fr.univ.tetraword.OldMainGame;
import static fr.univ.tetraword.OldMainGame.welcomePage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bruno
 */
public class tetraMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Main Project");
        OldMainGame main=new OldMainGame();
        try {
            welcomePage();
        } catch (IOException ex) {
            Logger.getLogger(tetraMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
