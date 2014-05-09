package fr.univ.tetraword;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
    * Dictionary est la classe qui contient la liste des mots autorisés
 **/
public class Dictionary {
	HashSet<String> line;
        
/**
    * Constructeur par défaut
 **/
	public Dictionary() {
 		line = new HashSet<String>();
		String fichier ="data/dictionnaire.txt";
		
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String str= null;		
			while ((str = br.readLine()) != null){
			line.add(str);
			//else System.out.printf(" pas coucou");
			}
			br.close();
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		
	}

/**
    * Permet de tester l'expression regulière
    * @param regex
    * l'expression regulière que l'on souhaite tester
     * @return 
 **/
        public boolean containsRegEx( String regex ) {
            for( String string : line ) {
                if( string.matches( regex ) ) {
                    return true;
                }
            }
            return false;
        }

}
