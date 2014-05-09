package fr.univ.tetraword;

import static fr.univ.tetraword.Shape.getLetterFromProb;
import static fr.univ.tetraword.Shape.getRarityFromLetter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

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
            //T
            shapeTypes=new HashMap<Integer,Integer[][]>();
            Integer [][]T=new Integer[4][4];
            for(int i=0;i<4;++i){
                for(int j=0;j<4;++j){
                    T[i][j]=0;
                }
            }
            T[1][0]=1;
            T[0][1]=1;
            T[1][1]=1;
            T[1][2]=1;
            shapeTypes.put(0,T);
            
            //square
            Integer [][]square=new Integer[4][4];
            for(int i=0;i<4;++i){
                for(int j=0;j<4;++j){
                    square[i][j]=0;
                }
            }
            square[0][0]=1;
            square[0][1]=1;
            square[1][0]=1;
            square[1][1]=1;
            shapeTypes.put(1,square);
            
            //rightZ
            Integer [][]rightZ=new Integer[4][4];
            for(int i=0;i<4;++i){
                for(int j=0;j<4;++j){
                    rightZ[i][j]=0;
                }
            }
            rightZ[1][0]=1;
            rightZ[1][1]=1;
            rightZ[0][1]=1;
            rightZ[0][2]=1;
            shapeTypes.put(2,rightZ);
            
            //leftZ
            Integer [][]leftZ=new Integer[4][4];
            for(int i=0;i<4;++i){
                for(int j=0;j<4;++j){
                    leftZ[i][j]=0;
                }
            }
            leftZ[0][0]=1;
            leftZ[0][1]=1;
            leftZ[1][1]=1;
            leftZ[1][2]=1;
            shapeTypes.put(3,leftZ);
            
            //rightL
            Integer [][]rightL=new Integer[4][4];
            for(int i=0;i<4;++i){
                for(int j=0;j<4;++j){
                    rightL[i][j]=0;
                }
            }
            rightL[0][1]=1;
            rightL[0][0]=1;
            rightL[1][0]=1;
            rightL[2][0]=1;
            shapeTypes.put(4,rightL);
            
            //leftL
            Integer [][]leftL=new Integer[4][4];
            for(int i=0;i<4;++i){
                for(int j=0;j<4;++j){
                    leftL[i][j]=0;
                }
            }
            leftL[0][0]=1;
            leftL[0][1]=1;
            leftL[1][1]=1;
            leftL[2][1]=1;
            shapeTypes.put(5,leftL);
            
            //line
            Integer [][]line=new Integer[4][4];
            for(int i=0;i<4;++i){
                for(int j=0;j<4;++j){
                    line[i][j]=0;
                }
            }
            line[0][0]=1;
            line[1][0]=1;
            line[2][0]=1;
            line[3][0]=1;
            shapeTypes.put(6,line);

        }
        
        public static void saveShapes() throws IOException
    {
	try{
            FileWriter fw = new FileWriter("shapes.txt", false);
            BufferedWriter output = new BufferedWriter(fw);
            for(int i=0;i<shapeTypes.size();++i){
                output.write(String.valueOf(i)+"\n");
                for(int j=0;j<4;++j){
                    for(int l=0;l<4;++l){
                        output.write(shapeTypes.get(i)[j][l] + " ");
                    }
                    output.write("\n");
                }
                output.write("\n");
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