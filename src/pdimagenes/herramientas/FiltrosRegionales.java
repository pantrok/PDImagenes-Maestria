/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.herramientas;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pdimagenes.utils.ImageUtils;
import boofcv.alg.feature.detect.edge.CannyEdge;
import boofcv.alg.feature.detect.edge.EdgeContour;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.factory.feature.detect.edge.FactoryEdgeDetectors;
import boofcv.struct.image.GrayS16;
import boofcv.struct.image.GrayU8;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.struct.ConnectRule;
import pdimagenes.utils.MathUtils;

/**
 *
 * @author USUARIO
 */
public class FiltrosRegionales {

    public static final int MATRIZ_CONVU_LAPLACIANO[][] = new int[][]{{0, 1, 0}, {1, -4, 1}, {0, 1, 0}};
    public static final int MATRIZ_CONVU_LAPLACIANO_ROT[][] = new int[][]{{1, 0, 1}, {0, -4, 0}, {1, 0, 1}};
    public static final int MATRIZ_CONVU_LAPLACIANO_MIXTA[][] = new int[][]{{1, 1, 1}, {1, -8, 1}, {1, 1, 1}};
    public static final int MATRIZ_CONVU_SOBEL_X[][] = new int[][]{{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    public static final int MATRIZ_CONVU_SOBEL_Y[][] = new int[][]{{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    public static final int MATRIZ_CONVU_GAUSS_FUERTE[][] = new int[][]{{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
    public static final int MATRIZ_CONVU_GAUSS_DEBIL[][] = new int[][]{{1, 4, 1}, {4, 12, 4}, {1, 4, 1}};

    public BufferedImage mediaExcluyendo(BufferedImage imagen, int rgbExcluir) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        for (int x = 1; x < (imagen.getWidth() - 1); x++) {
            for (int y = 1; y < (imagen.getHeight() - 1); y++) {
                int media = 0;
                boolean mediaValida = true;
                mediaFor:
                for (int kx = 0; kx < 3; kx++) {
                    for (int ky = 0; ky < 3; ky++) {
                        if (imagen.getRGB(x + kx - 1, y + ky - 1) == rgbExcluir) {
                            mediaValida = false;
                            break mediaFor;
                        }
                        media += new Color(imagen.getRGB(x + kx - 1, y + ky - 1)).getRed();
                    }
                }
                if (mediaValida) {
                    media = (int) (media / 9.0);
                    imagenModificada.setRGB(x, y, new Color(media, media, media).getRGB());
                }

            }
        }
        return imagenModificada;
    }

    public BufferedImage medianaExcluyendo(BufferedImage imagen, int rgbExcluir) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        for (int x = 1; x < (imagen.getWidth() - 1); x++) {
            for (int y = 1; y < (imagen.getHeight() - 1); y++) {
                List<Integer> values = new ArrayList<>();
                boolean medianaValida = true;
                medianaFor:
                for (int kx = 0; kx < 3; kx++) {
                    for (int ky = 0; ky < 3; ky++) {
                        if (imagen.getRGB(x + kx - 1, y + ky - 1) == rgbExcluir) {
                            medianaValida = false;
                            break medianaFor;
                        }
                        values.add(new Color(imagen.getRGB(x + kx - 1, y + ky - 1)).getRed());
                    }
                }
                if (medianaValida) {
                    Collections.sort(values);
                    imagenModificada.setRGB(x, y, new Color(values.get(4), values.get(4), values.get(4)).getRGB());
                }

            }
        }
        return imagenModificada;
    }

    public BufferedImage media(BufferedImage imagen) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        for (int x = 1; x < (imagen.getWidth() - 1); x++) {
            for (int y = 1; y < (imagen.getHeight() - 1); y++) {
                int media = 0;
                for (int kx = 0; kx < 3; kx++) {
                    for (int ky = 0; ky < 3; ky++) {
                        media += new Color(imagen.getRGB(x + kx - 1, y + ky - 1)).getRed();
                    }
                }
                media = (int) (media / 9.0);
                imagenModificada.setRGB(x, y, new Color(media, media, media).getRGB());
            }
        }
        return imagenModificada;
    }

    public BufferedImage mediaGaussiana(BufferedImage imagen, int matrizConvolucion[][], boolean fuerte) {
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
                if (fuerte) {
                    rgb = rgb / 16;
                } else {
                    rgb = rgb / 32;
                }
                rgb = Math.min(255, Math.max(0, rgb));
                //System.out.println("RGB " + rgb);
                imagenModificada.setRGB(x, y, new Color(rgb, rgb, rgb).getRGB());
            }
        }
        return imagenModificada;
    }

    public BufferedImage mediaDireccional(BufferedImage imagen) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        for (int x = 1; x < (imagen.getWidth() - 1); x++) {
            for (int y = 1; y < (imagen.getHeight() - 1); y++) {

                int p1 = 0, p2 = 0, p3 = 0, p4 = 0;
                for (int i = -1; i <= 1; i++) {
                    p1 += new Color(imagen.getRGB(x + i, y + i)).getRed();
                    p2 += new Color(imagen.getRGB(x + i, y - i)).getRed();
                    p3 += new Color(imagen.getRGB(x + i, y)).getRed();
                    p4 += new Color(imagen.getRGB(x, y + i)).getRed();
                }
                p1 /= 3.0;
                p2 /= 3.0;
                p3 /= 3.0;
                p4 /= 3.0;
                int rgb = Math.max(Math.max(Math.max(p1, p2), p3), p4);
                rgb = Math.min(255, Math.max(0, rgb));
                imagenModificada.setRGB(x, y, new Color(rgb, rgb, rgb).getRGB());
            }
        }
        return imagenModificada;
    }

    public BufferedImage mediaPonderada(BufferedImage imagen) {
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
                int promedioVecinos = 0;
                if (c11.getRed() == 0) {
                    promedioVecinos = c00.getRed() + c01.getRed() + c02.getRed()
                            + c10.getRed() + c12.getRed()
                            + c20.getRed() + c21.getRed() + c22.getRed();
                    promedioVecinos /= 8.0;
                }
                int rgb = c00.getRed() + c01.getRed() + c02.getRed()
                        + c10.getRed() + (c11.getRed() == 0 ? promedioVecinos : (c11.getRed() * c11.getRed())) + c12.getRed()
                        + c20.getRed() + c21.getRed() + c22.getRed();
                if (c11.getRed() == 0) {
                    rgb /= (8.0 + promedioVecinos);
                } else {
                    rgb /= (8.0 + c11.getRed());
                }
                rgb = Math.min(255, Math.max(0, rgb));
                imagenModificada.setRGB(x, y, new Color(rgb, rgb, rgb).getRGB());
            }
        }
        return imagenModificada;
    }

    public BufferedImage mediaArmonica(BufferedImage imagen) {
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
                int rgb;
                if (c00.getRed() == 0 || c01.getRed() == 0 || c02.getRed() == 0
                        || c10.getRed() == 0 || c11.getRed() == 0 || c12.getRed() == 0
                        || c20.getRed() == 0 || c21.getRed() == 0 || c22.getRed() == 0) {
                    rgb = 0;
                } else {
                    double reciprocos = (1.0 / c00.getRed()) + (1.0 / c01.getRed()) + (1.0 / c02.getRed())
                            + (1.0 / c10.getRed()) + (1.0 / c11.getRed()) + (1.0 / c12.getRed())
                            + (1.0 / c20.getRed()) + (1.0 / c21.getRed()) + (1.0 / c22.getRed());
                    rgb = (int) (9 / reciprocos);
                }
                rgb = Math.min(255, Math.max(0, rgb));
                imagenModificada.setRGB(x, y, new Color(rgb, rgb, rgb).getRGB());
            }
        }
        return imagenModificada;
    }

    public BufferedImage mediana(BufferedImage imagen) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        for (int x = 1; x < (imagen.getWidth() - 1); x++) {
            for (int y = 1; y < (imagen.getHeight() - 1); y++) {
                List<Integer> values = new ArrayList<>();
                for (int kx = 0; kx < 3; kx++) {
                    for (int ky = 0; ky < 3; ky++) {
                        values.add(new Color(imagen.getRGB(x + kx - 1, y + ky - 1)).getRed());
                    }
                }
                Collections.sort(values);
                imagenModificada.setRGB(x, y, new Color(values.get(4), values.get(4), values.get(4)).getRGB());
            }
        }
        return imagenModificada;
    }
    
    public BufferedImage medianaRecortada(BufferedImage imagen, int K, int m) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        for (int x = 1; x < (imagen.getWidth() - 1); x++) {
            for (int y = 1; y < (imagen.getHeight() - 1); y++) {
                List<Integer> values = new ArrayList<>();
                for (int kx = 0; kx < K; kx++) {
                    for (int ky = 0; ky < K; ky++) {
                        values.add(new Color(imagen.getRGB(x + kx - 1, y + ky - 1)).getRed());
                    }
                }
                Collections.sort(values);
                //Se calcula la media eliminando los m puntos en ambos extremos
                List<Double> valoresRecortados = new ArrayList<>();
                for (int i = m; i < ((K*K)-m); i++) {
                    valoresRecortados.add(new Double(values.get(i)));
                }
                int mediaRecortada = (int) MathUtils.mediana(valoresRecortados);
                imagenModificada.setRGB(x, y, new Color(mediaRecortada, mediaRecortada, mediaRecortada).getRGB());
            }
        }
        return imagenModificada;
    }

    public BufferedImage laplacianoGrises(BufferedImage imagen, int matrizConvolucion[][]) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        //imagenModificada = new FiltroGris().grisPromedio(imagenModificada);
        //System.out.println(matrizConvolucion[0][0] + " " + matrizConvolucion[0][1] + " " + matrizConvolucion[0][2]);
        //System.out.println(matrizConvolucion[0][1] + " " + matrizConvolucion[1][1] + " " + matrizConvolucion[1][2]);
        //System.out.println(matrizConvolucion[0][2] + " " + matrizConvolucion[2][1] + " " + matrizConvolucion[2][2]);
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
                rgb = Math.min(255, Math.max(0, rgb));
                //System.out.println("RGB " + rgb);
                imagenModificada.setRGB(x, y, new Color(rgb, rgb, rgb).getRGB());
            }
        }
        return imagenModificada;
    }

    public BufferedImage sobelGrises(BufferedImage imagen) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        //imagenModificada = new FiltroGris().grisPromedio(imagenModificada);
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
                int rgbX = (c00.getRed() * MATRIZ_CONVU_SOBEL_X[0][0]) + (c01.getRed() * MATRIZ_CONVU_SOBEL_X[0][1]) + (c02.getRed() * MATRIZ_CONVU_SOBEL_X[0][2])
                        + (c10.getRed() * MATRIZ_CONVU_SOBEL_X[1][0]) + (c11.getRed() * MATRIZ_CONVU_SOBEL_X[1][1]) + (c12.getRed() * MATRIZ_CONVU_SOBEL_X[1][2])
                        + (c20.getRed() * MATRIZ_CONVU_SOBEL_X[2][0]) + (c21.getRed() * MATRIZ_CONVU_SOBEL_X[2][1]) + (c22.getRed() * MATRIZ_CONVU_SOBEL_X[2][2]);
                int rgbY = (c00.getRed() * MATRIZ_CONVU_SOBEL_Y[0][0]) + (c01.getRed() * MATRIZ_CONVU_SOBEL_Y[0][1]) + (c02.getRed() * MATRIZ_CONVU_SOBEL_Y[0][2])
                        + (c10.getRed() * MATRIZ_CONVU_SOBEL_Y[1][0]) + (c11.getRed() * MATRIZ_CONVU_SOBEL_Y[1][1]) + (c12.getRed() * MATRIZ_CONVU_SOBEL_Y[1][2])
                        + (c20.getRed() * MATRIZ_CONVU_SOBEL_Y[2][0]) + (c21.getRed() * MATRIZ_CONVU_SOBEL_Y[2][1]) + (c22.getRed() * MATRIZ_CONVU_SOBEL_Y[2][2]);

                int valorPixel = (int) Math.sqrt((rgbX * rgbX) + (rgbY * rgbY));
                //System.out.println("RGB " + rgb);
                valorPixel = Math.min(255, Math.max(0, valorPixel));
                imagenModificada.setRGB(x, y, new Color(valorPixel, valorPixel, valorPixel).getRGB());
            }
        }
        return imagenModificada;
    }

    public BufferedImage cannyGrises(BufferedImage imagen) {

        GrayU8 gray = ConvertBufferedImage.convertFrom(imagen, (GrayU8) null);
        GrayU8 edgeImage = gray.createSameShape();

        // Create a canny edge detector which will dynamically compute the threshold based on maximum edge intensity
        // It has also been configured to save the trace as a graph.  This is the graph created while performing
        // hysteresis thresholding.
        CannyEdge<GrayU8, GrayS16> canny = FactoryEdgeDetectors.canny(2, true, true, GrayU8.class, GrayS16.class);

        // The edge image is actually an optional parameter.  If you don't need it just pass in null
        canny.process(gray, 0.03f, 0.2f, edgeImage);

        // First get the contour created by canny
        List<EdgeContour> edgeContours = canny.getContours();
        // The 'edgeContours' is a tree graph that can be difficult to process.  An alternative is to extract
        // the contours from the binary image, which will produce a single loop for each connected cluster of pixels.
        // Note that you are only interested in external contours.
        List<Contour> contours = BinaryImageOps.contour(edgeImage, ConnectRule.EIGHT, null);

        // display the results
        BufferedImage visualBinary = VisualizeBinaryData.renderBinary(edgeImage, false, null);
        BufferedImage visualCannyContour = VisualizeBinaryData.renderContours(edgeContours, null, gray.width, gray.height, null);
        BufferedImage visualEdgeContour = new BufferedImage(gray.width, gray.height, BufferedImage.TYPE_INT_RGB);
        VisualizeBinaryData.renderContours(contours, Color.white.getRGB(), Color.RED.getRGB(), gray.width, gray.height, visualEdgeContour);

        return visualBinary;
    }

}
