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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.HashSet;
public class Dictionary {
	HashSet line;
	public Dictionary() {
 		line = new HashSet();
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


	public static void main(String[] args){
		Dictionary dictionary = new Dictionary();
		String mot = "pute";
		if( dictionary.line.contains(mot) ){
		      System.out.println("Mot correct");
		}
		else{
		      System.out.println("Mot incorrect");
		}
}
}
