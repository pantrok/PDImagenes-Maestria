/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.herramientas;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author daniel
 */
public class Roi {

    private BufferedImage imagen;
    private Histograma histograma;

    public Roi() {
    }

    public Roi(BufferedImage imagen) {
        this.imagen = imagen;
        histograma = new Histograma(imagen);
        for (int i = 0; i < 256; i++) {
            System.out.println("R ["+ i +"] = " + histograma.getRed()[i]);
        }
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }

    public Histograma getHistograma() {
        return histograma;
    }

    public void setHistograma(Histograma histograma) {
        this.histograma = histograma;
    }
    
}
