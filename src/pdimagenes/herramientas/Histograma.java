/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.herramientas;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author daniel
 */
public class Histograma {

    private final BufferedImage imagen;
    private double red[];
    private double green[];
    private double blue[];
    private double cumulative[];
    //TODO: Declarar arreglos nuevos para visualizar 
    private final int tamanoImagen;

    public Histograma(BufferedImage imagen) {
        this.imagen = imagen;
        tamanoImagen = this.imagen.getHeight() * this.imagen.getWidth();
        obtenerHistograma();
        cumulative = new double[256];
    }

    //Tener los arreglos de valores para cada canal
    private void obtenerHistograma() {
        
        red = new double[256];
        green = new double[256];
        blue = new double[256];
        
        for (int i = 0; i < 256; i++) {
            red[i] = 0;
            green[i] = 0;
            blue[i] = 0;
        }
        
        for (int x = 0; x < imagen.getWidth(); x++) {
            for (int y = 0; y < imagen.getHeight(); y++) {
                Color color = new Color(imagen.getRGB(x, y));
                red[color.getRed()]++;
                green[color.getGreen()]++;
                blue[color.getBlue()]++;
            }
        }
        
        //Se normaliza el histograma
        for (int i = 0; i < 256; i++) {
            red[i] = red[i] / tamanoImagen;
            green[i] = green[i] / tamanoImagen;
            blue[i] = blue[i] / tamanoImagen;
        }

    }
    
    public double[] obtenerHistogramaAcumulativoGrises(int i) {
        
        //Se calcula acumulativo
        if (i == 0) {
            cumulative[0] = red[0];
            return cumulative;
        } else {
            cumulative[i] = obtenerHistogramaAcumulativoGrises(i-1)[i-1] + red[i];
            return cumulative;
        }

    }

    public double[] getRed() {
        return red;
    }

    public double[] getGreen() {
        return green;
    }

    public double[] getBlue() {
        return blue;
    }
    
}
