package fr.univ.tetraword;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
    * shapeType est une énumération qui contient les différents types de forme d'une pièce
 **/
public enum shapeType{
        T(3),
        square(2),
        rightZ(3),
        leftZ(3),
        rightL(3),
        leftL(3),
        line(4);
        
        private final int taille;
        
/**
    * Constructeur par défaut
    * @param taille
    * taille maximale de la pièce
 **/
        private shapeType(int taille){
            this.taille=taille;
        }

/**
    * Retourne la taille de la pièce
 **/
        public int getTaille(){
            return taille;
        }
        
        public static void saveShapes(Shape shape) throws IOException
    {
	try{
            FileWriter fw = new FileWriter("game.txt", true);
            BufferedWriter output = new BufferedWriter(fw);
            output.write("CurrentShape afaire ");
            output.write("NextShape afaire ");
            output.flush();	
            output.close();
            
	}
            catch(IOException ioe){
		System.out.print("Erreur : ");
		ioe.printStackTrace();
            }
        /*ObjectOutputStream output = null;
          try {
            output = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File("game.txt"))));
            output.writeObject(savedGame.get(0));
            output.close();
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }*/

        }
      }