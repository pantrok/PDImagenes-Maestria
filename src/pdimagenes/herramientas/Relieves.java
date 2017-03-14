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
 * @author USUARIO
 */
public class Relieves {

    public static final int MATRIZ_CONVU_1ER_ORDEN_0[][] = new int[][]{{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}};
    public static final int MATRIZ_CONVU_1ER_ORDEN_45[][] = new int[][]{{0, -1, -1}, {1, 0, -1}, {1, 1, 0}};
    public static final int MATRIZ_CONVU_1ER_ORDEN_90[][] = new int[][]{{1, 0, -1}, {1, 0, -1}, {1, 0, -1}};
    public static final int MATRIZ_CONVU_1ER_ORDEN_135[][] = new int[][]{{1, 1, 0}, {1, 0, -1}, {0, -1, -1}};
    public static final int MATRIZ_CONVU_1ER_ORDEN_180[][] = new int[][]{{1, 1, 1}, {0, 0, 0}, {-1, -1, -1}};
    public static final int MATRIZ_CONVU_1ER_ORDEN_225[][] = new int[][]{{0, 1, 1}, {-1, 0, 1}, {-1, -1, 0}};
    public static final int MATRIZ_CONVU_1ER_ORDEN_270[][] = new int[][]{{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}};
    public static final int MATRIZ_CONVU_1ER_ORDEN_315[][] = new int[][]{{-1, -1, 0}, {-1, 0, 1}, {0, 1, 1}};
    public static final int MATRIZ_CONVU_2DO_ORDEN_0[][] = new int[][]{{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    public static final int MATRIZ_CONVU_2DO_ORDEN_45[][] = new int[][]{{0, -1, -2}, {1, 0, -1}, {2, 1, 0}};
    public static final int MATRIZ_CONVU_2DO_ORDEN_90[][] = new int[][]{{1, 0, -1}, {2, 0, -2}, {1, 0, -1}};
    public static final int MATRIZ_CONVU_2DO_ORDEN_135[][] = new int[][]{{2, 1, 0}, {1, 0, -1}, {0, -1, -2}};
    public static final int MATRIZ_CONVU_2DO_ORDEN_180[][] = new int[][]{{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
    public static final int MATRIZ_CONVU_2DO_ORDEN_225[][] = new int[][]{{0, 1, 2}, {-1, 0, 1}, {-2, -1, 0}};
    public static final int MATRIZ_CONVU_2DO_ORDEN_270[][] = new int[][]{{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    public static final int MATRIZ_CONVU_2DO_ORDEN_315[][] = new int[][]{{-2, -1, 0}, {-1, 0, 1}, {0, 1, 2}};

    public BufferedImage aplicarRelieve(BufferedImage imagen, int matrizConvolucion[][], int valorCuantizacion) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        for (int x = 1; x < (imagen.getWidth() - 1); x++) {
            for (int y = 1; y < (imagen.getHeight() - 1); y++) {
                Color c00 = new Color(imagen.getRGB(x - 1, y - 1));
                Color c01 = new Color(imagen.getRGB(x, y - 1));
                Color c02 = new Color(imagen.getRGB(x + 1, y - 1));
                Color c10 = new Color(imagen.getRGB(x - 1, y));
                Color c11 = new Color(imagen.getRGB(x, y));
                Color c12 = new Color(imagen.getRGB(x + 1, y));
                Color c20 = new Color(imagen.getRGB(x - 1, y + 1));
                Color c21 = new Color(imagen.getRGB(x, y + 1));
                Color c22 = new Color(imagen.getRGB(x + 1, y + 1));
                int rgb = (c00.getRed() * matrizConvolucion[0][0]) + (c01.getRed() * matrizConvolucion[0][1]) + (c02.getRed() * matrizConvolucion[0][2])
                        + (c10.getRed() * matrizConvolucion[1][0]) + (c11.getRed() * matrizConvolucion[1][1]) + (c12.getRed() * matrizConvolucion[1][2])
                        + (c20.getRed() * matrizConvolucion[2][0]) + (c21.getRed() * matrizConvolucion[2][1]) + (c22.getRed() * matrizConvolucion[2][2]);
                rgb = ((valorCuantizacion - 1) / 2) + rgb;
                rgb = Math.min(255, Math.max(0, rgb));
                imagenModificada.setRGB(x, y, new Color(rgb, rgb, rgb).getRGB());
            }
        }
        return imagenModificada;
    }

}
