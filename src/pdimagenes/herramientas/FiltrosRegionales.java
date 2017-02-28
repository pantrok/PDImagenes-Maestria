/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.herramientas;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pdimagenes.utils.ImageUtils;

/**
 *
 * @author USUARIO
 */
public class FiltrosRegionales {

    public BufferedImage mediaExcluyendo(BufferedImage imagen, int rgbExcluir) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        for (int x = 1; x < (imagenModificada.getWidth() - 1); x++) {
            for (int y = 1; y < (imagenModificada.getHeight() - 1); y++) {
                double media = 0.0; boolean mediaValida = true;
                mediaFor: 
                for (int kx = 0; kx < 3; kx++) {
                    for (int ky = 0; ky < 3; ky++) {
                        if (imagenModificada.getRGB(x+kx-1, y+ky-1) == rgbExcluir) {
                            mediaValida = false;
                            break mediaFor;
                        }
                        media += imagenModificada.getRGB(x+kx-1, y+ky-1);
                    }
                }
                if (mediaValida) {
                    imagenModificada.setRGB(x, y, (int)(media/9.0));
                }
                
            }
        }
        return imagenModificada;
    }
    
    public BufferedImage medianaExcluyendo(BufferedImage imagen, int rgbExcluir) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        for (int x = 1; x < (imagenModificada.getWidth() - 1); x++) {
            for (int y = 1; y < (imagenModificada.getHeight() - 1); y++) {
                List<Integer> values = new ArrayList<>(); boolean medianaValida = true;
                medianaFor: 
                for (int kx = 0; kx < 3; kx++) {
                    for (int ky = 0; ky < 3; ky++) {
                        if (imagenModificada.getRGB(x+kx-1, y+ky-1) == rgbExcluir) {
                            medianaValida = false;
                            break medianaFor;
                        }
                        values.add(imagenModificada.getRGB(x+kx-1, y+ky-1));
                    }
                }
                if (medianaValida) {
                    Collections.sort(values);
                    imagenModificada.setRGB(x, y, values.get(4));
                }
                
            }
        }
        return imagenModificada;
    }

}
