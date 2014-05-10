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
public class Options {
        long worddleTime; //Le temps en mode Worddle
        long worddleReload; //Le temps de rechargement de Worddle
        long anagTime; //Le temps en mode anagramme
        long fallTime; //Le temps de chute des pièces
        int anagLettresMax; // lettres minimales pour l'anagramme
        int frequenceModif; // frequence des modificateurs
        
        public Options(){
            worddleTime=40000;
            worddleReload=20000;
            anagTime=30000;
            fallTime=1000;
            anagLettresMax=2;
            frequenceModif=3;
        }
        
    public Options(long worddleTime, long worddleReload, long anagTime, long fallTime, int anagLettresMax, int frequenceModif){
        this.worddleTime=worddleTime;
        this.worddleReload=worddleReload;
        this.anagTime=anagTime;
        this.fallTime=fallTime;
        this.anagLettresMax=anagLettresMax;
        this.frequenceModif=frequenceModif;
    }
}
