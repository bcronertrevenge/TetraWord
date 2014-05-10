/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.graphicinterface;

import fr.univ.tetraword.Options;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderListener implements ChangeListener {
    public JLabel label;
    public Options options;

    public SliderListener(JLabel label, Options options){
        this.label=label;
        this.options=options;
    }
    
    @Override   
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int value = (int)source.getValue();
            if (label!=null){
                label.setText(String.valueOf(value));
                label.repaint();
            }
        }    
    }
}