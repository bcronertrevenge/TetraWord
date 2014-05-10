package fr.univ.tetraword;

import static fr.univ.tetraword.Shape.getLetterFromProb;
import static fr.univ.tetraword.Shape.getRarityFromLetter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
    * shapeType est une énumération qui contient les différents types de forme d'une pièce
 **/
public class shapeType {
        static HashMap<Integer,Integer[][]> shapeTypes;
        
        public static Brick[][] getBricksWithType(){
            
            Brick[][] bricks=new Brick[4][4];
            
           double totalProb=0;
            for(int i=65;i<65+26;++i)
                totalProb+=getRarityFromLetter((char) i);

        char random;
        
        int r = (int) (Math.random() * shapeTypes.size());
        Integer[][] typeTab=shapeTypes.get(r);
        
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                if(typeTab[i][j]==1){
                    random = getLetterFromProb((int)(Math.random() * totalProb));
                    Brick case1=new Brick(random,getRarityFromLetter(random));
                    bricks[i][j]=case1;
                }
            }
        }
           
           return bricks;
        }
        
        public static void readShapes(){
            shapeTypes=new HashMap<Integer,Integer[][]>();
            
            	try 
		{
                        InputStream ips=new FileInputStream("data/Shapes.txt"); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
                        String ligne;
                        String str[];
                        int i=0,j=0;
                        int numPiece=-1;
                        Integer [][]bricks=null;
                        int numLignes=0;
			while ((ligne=br.readLine())!=null){
                            numLignes++;
                            if(ligne.length()==0){
                                numPiece++;
                                bricks=new Integer[4][4];
                                j=0;
                            }
                            if(ligne.length()==8 || ligne.length()==7){
                                str=ligne.split(" ");
                                for(i=0;i<4;++i)
                                    bricks[j][i]=Integer.valueOf(str[i]);
                                j++;
                                
                                if(j==4){
                                    shapeTypes.put(numPiece,bricks);
                                }
                                else if(j>4 || str.length>4){
                                    throw new Exception("Mauvaise pièce");
                                }
                            }
                            else if(ligne.length()!=0){
				throw new Exception("Mauvaise ligne " +numLignes);
                            }
                                
			}
			br.close(); 
		} catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.exit(1);
                } 
                catch (Exception ex) { 
                Logger.getLogger(shapeType.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            } 
            
        }
        
        public static void saveShapes() throws IOException
    {
	try{
            FileWriter fw = new FileWriter("data/Shapes.txt", false);
            BufferedWriter output = new BufferedWriter(fw);
            for(int i=0;i<shapeTypes.size();++i){
                output.write("\n");
                for(int j=0;j<4;++j){
                    for(int l=0;l<4;++l){
                        output.write(shapeTypes.get(i)[j][l] + " ");
                    }
                    output.write("\n");
                }
            }
            
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