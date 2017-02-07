/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.herramientas;

import java.awt.Color;
import java.awt.image.BufferedImage;
import pdimagenes.utils.ImageUtils;

/**
 *
 * @author daniel
 */
public class FiltroGris {
    
    public BufferedImage grisPromedio(BufferedImage imagen) {
        BufferedImage copiaImagen = ImageUtils.deepCopy(imagen);
        //Generamos valores para esta funcion
        for (int x = 0; x < copiaImagen.getWidth(); x++) {
            for (int y = 0; y < copiaImagen.getHeight(); y++) {
                Color color = new Color(copiaImagen.getRGB(x, y));
                double promedioGris = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                Color colorGris = new Color((int)promedioGris, (int)promedioGris, (int)promedioGris);
                copiaImagen.setRGB(x, y, colorGris.getRGB());
            }
        }
        return copiaImagen;
    }
    
}
