/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.herramientas;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import pdimagenes.utils.ImageUtils;

/**
 *
 * @author daniel
 */
public class OperacionPunto {

    private int valoresGenerados[];

    //TODO: Crear tabla donde se calculen los valores ocupando programacion dinamica, tabla de x con su f(x)
    public OperacionPunto() {
        valoresGenerados = new int[256];
    }

    public int[] getValoresGenerados() {
        return valoresGenerados;
    }
    
    public BufferedImage negativo(BufferedImage imagen) {
        BufferedImage copiaImagen = ImageUtils.deepCopy(imagen);
        //Generamos valores para esta funcion
        for (int i = 0; i < 256; i++) {
            valoresGenerados[i] = 255 - i;
        }
        for (int x = 0; x < copiaImagen.getWidth(); x++) {
            for (int y = 0; y < copiaImagen.getHeight(); y++) {
                Color color = new Color(copiaImagen.getRGB(x, y));
                Color colorNegativo = new Color(valoresGenerados[color.getRed()], valoresGenerados[color.getGreen()], valoresGenerados[color.getBlue()]);
                copiaImagen.setRGB(x, y, colorNegativo.getRGB());
            }
        }
        return copiaImagen;
    }

    public BufferedImage correccionGama(BufferedImage imagen, double correccionGama) {
        BufferedImage copiaImagen = ImageUtils.deepCopy(imagen);
        //Generamos valores para esta funcion
        for (int i = 0; i < 256; i++) {
            valoresGenerados[i] = (int) (255 * (Math.pow((double) i / (double) 255, correccionGama)));
        }
        for (int x = 0; x < copiaImagen.getWidth(); x++) {
            for (int y = 0; y < copiaImagen.getHeight(); y++) {
                Color color = new Color(copiaImagen.getRGB(x, y));
                Color colorGama = new Color(valoresGenerados[color.getRed()], valoresGenerados[color.getGreen()], valoresGenerados[color.getBlue()]);
                copiaImagen.setRGB(x, y, colorGama.getRGB());
            }
        }
        return copiaImagen;
    }

    public BufferedImage contraste(BufferedImage imagen, double factorContraste) {
        BufferedImage copiaImagen = ImageUtils.deepCopy(imagen);
        //Generamos valores para esta funcion
        for (int i = 0; i < 256; i++) {
            int contraste = (i * factorContraste) > 255 ? 255 : (int) (i * factorContraste);
            valoresGenerados[i] = contraste;
        }
        for (int x = 0; x < copiaImagen.getWidth(); x++) {
            for (int y = 0; y < copiaImagen.getHeight(); y++) {
                Color color = new Color(copiaImagen.getRGB(x, y));
                Color colorContraste = new Color(valoresGenerados[color.getRed()], valoresGenerados[color.getGreen()], valoresGenerados[color.getBlue()]);
                copiaImagen.setRGB(x, y, colorContraste.getRGB());
            }
        }
        return copiaImagen;
    }

    public BufferedImage brillo(BufferedImage imagen, double factorBrillo) {
        BufferedImage copiaImagen = ImageUtils.deepCopy(imagen);
        //Generamos valores para esta funcion
        for (int i = 0; i < 256; i++) {
            int brillo = (i + factorBrillo) > 255 ? 255 : (int) (i + factorBrillo);
            valoresGenerados[i] = brillo;
        }
        for (int x = 0; x < copiaImagen.getWidth(); x++) {
            for (int y = 0; y < copiaImagen.getHeight(); y++) {
                Color color = new Color(copiaImagen.getRGB(x, y));
                Color colorBrillo = new Color(valoresGenerados[color.getRed()], valoresGenerados[color.getGreen()], valoresGenerados[color.getBlue()]);
                copiaImagen.setRGB(x, y, colorBrillo.getRGB());
            }
        }
        return copiaImagen;
    }

    //La imagen ya deberia de venir en grises
    public BufferedImage umbral(BufferedImage imagen, double umbral, int a0, int a1) {
        BufferedImage copiaImagen = ImageUtils.deepCopy(imagen);
        //Generamos valores para esta funcion
        for (int i = 0; i < 256; i++) {
            int valorPixel = i < umbral ? a0 : a1;
            valoresGenerados[i] = valorPixel;
        }
        for (int x = 0; x < copiaImagen.getWidth(); x++) {
            for (int y = 0; y < copiaImagen.getHeight(); y++) {
                Color color = new Color(copiaImagen.getRGB(x, y));
                Color colorBrillo = new Color(valoresGenerados[color.getRed()], valoresGenerados[color.getGreen()], valoresGenerados[color.getBlue()]);
                copiaImagen.setRGB(x, y, colorBrillo.getRGB());
            }
        }
        return copiaImagen;
    }

    public BufferedImage contrasteAutomatico(BufferedImage imagen, Histograma histograma, double amin, double amax) {
        BufferedImage copiaImagen = ImageUtils.deepCopy(imagen);
        int alow = 0, ahigh = 0;
        for (int i = 0; i < histograma.getRed().length; i++) {
            if (histograma.getRed()[i] != 0.0) {
                System.out.println("i "+ i + " valor " + histograma.getRed()[i] + " " + histograma.getGreen()[i] + " " + histograma.getBlue()[i]);
                alow = i;
                break;
            }
        }
        for (int i = (histograma.getRed().length - 1); i >= 0; i--) {
            if (histograma.getRed()[i] != 0.0) {
                System.out.println("i "+ i + " valor " + histograma.getRed()[i] + " " + histograma.getGreen()[i] + " " + histograma.getBlue()[i]);
                ahigh = i;
                break;
            }
        }
        int range = ahigh - alow;
        alow =  (int) (alow + (range * 0.05));
        ahigh =  (int) (ahigh - (range * 0.05));
        System.out.println("alow " + alow + " ahigh " + ahigh);
        //Generamos valores para esta funcion
        for (int i = 0; i < 256; i++) {
            int valorPixel = (int) (amin + (i - alow) * ((amax - amin) / (ahigh - alow)));
            System.out.println(valorPixel);
            if (valorPixel < amin) {
                valorPixel = (int) amin;
            }
            if (valorPixel > amax) {
                valorPixel = (int) amax;
            }
            valoresGenerados[i] = valorPixel;
        }
        for (int x = 0; x < copiaImagen.getWidth(); x++) {
            for (int y = 0; y < copiaImagen.getHeight(); y++) {
                Color color = new Color(copiaImagen.getRGB(x, y));
                Color colorContrasteAutomatico = new Color(valoresGenerados[color.getRed()], valoresGenerados[color.getGreen()], valoresGenerados[color.getBlue()]);
                copiaImagen.setRGB(x, y, colorContrasteAutomatico.getRGB());
            }
        }
        return copiaImagen;
    }
    
    public BufferedImage contrasteAutomaticoMejorado(BufferedImage imagen, Histograma histograma, double amin, double amax) {
        BufferedImage copiaImagen = ImageUtils.deepCopy(imagen);
        int alow = 0, ahigh = 0;
        List<Double> deviations = new ArrayList<>();
        //Obtenemos media 
        //TODO: Probar con la mediana
        double media = 0.0;
        List<Double> frecuenciasHisto = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            frecuenciasHisto.add(histograma.getRed()[i]);
        }
        Collections.sort(frecuenciasHisto);
        System.out.println(frecuenciasHisto);
        //Mediana
        int size = frecuenciasHisto.size();
        double mediana = 0.0;
        if (size % 2 == 0) {
            int posicion1 = size / 2;
            int posicion2 = posicion1 + 1;
            mediana = (frecuenciasHisto.get(posicion1) + frecuenciasHisto.get(posicion2)) / 2;
        } else {
            int posicion = (int)Math.ceil(size / 2);
            mediana = frecuenciasHisto.get(posicion);
        }
        
        System.out.println("Mediana " + mediana);
        for (int i = 0; i < 256; i++) {
            System.out.println(i + " " + histograma.getRed()[i]);
            media += histograma.getRed()[i];
        }
        media /= 256;
        System.out.println("Media " + media);
        //Calculando desviacion estandar
        for (int i = 0; i < 256; i++) {
            deviations.add(Math.pow((histograma.getRed()[i] - mediana), 2));
        }
        double standardDeviation = 0.0;
        for (Double deviation : deviations) {
            standardDeviation += deviation;
        }
        standardDeviation /= 256;
        standardDeviation = Math.sqrt(standardDeviation);
        System.out.println("Desviacion estandar " + standardDeviation);
        //La desviacion siempre es mas grande que la media por los picos altos, buscamos del primer valor igual a la media
        //hasta el primer valor igual mayor a la desviacion sin que haya ceros en medio
        int i = 0;
        boolean valido = false;
        while (histograma.getRed()[i] <= standardDeviation) {
            if (valido && histograma.getRed()[i] == 0) {
                valido = false;
            } else if (!valido && histograma.getRed()[i] >= mediana) {
                alow = i;
                valido = true;
            }
            i++;
        }
        
        i = 255;
        valido = false;
        while (histograma.getRed()[i] <= standardDeviation) {
            if (valido && histograma.getRed()[i] == 0) {
                valido = false;
            } else if (!valido && histograma.getRed()[i] >= mediana) {
                ahigh = i;
                valido = true;
            }
            i--;
        }
        System.out.println("alow " + alow + " ahigh " + ahigh);
        //Generamos valores para esta funcion
        for (i = 0; i < 256; i++) {
            int valorPixel = (int) (amin + (i - alow) * ((amax - amin) / (ahigh - alow)));
            System.out.println(valorPixel);
            if (valorPixel < amin) {
                valorPixel = (int) amin;
            }
            if (valorPixel > amax) {
                valorPixel = (int) amax;
            }
            valoresGenerados[i] = valorPixel;
        }
        for (int x = 0; x < copiaImagen.getWidth(); x++) {
            for (int y = 0; y < copiaImagen.getHeight(); y++) {
                Color color = new Color(copiaImagen.getRGB(x, y));
                Color colorContrasteAutomatico = new Color(valoresGenerados[color.getRed()], valoresGenerados[color.getGreen()], valoresGenerados[color.getBlue()]);
                copiaImagen.setRGB(x, y, colorContrasteAutomatico.getRGB());
            }
        }
        return copiaImagen;
    }
    
    public BufferedImage contrasteAutomaticoAcumulado(BufferedImage imagen, Histograma histograma, double q, double amin, double amax) {
        BufferedImage copiaImagen = ImageUtils.deepCopy(imagen);
        int alow = 0, ahigh = 0;
        double histoAcumulado[] = histograma.obtenerHistogramaAcumulativoGrises(255);
        for (int j = 0; j < 256; j++) {
            System.out.println(histoAcumulado[j]);
        }
        int size = imagen.getHeight() * imagen.getWidth();
        int i = 0;
        double lowCutValue = (size*q)/size;
        System.out.println("valorCorteLow " + lowCutValue);
        while (histoAcumulado[i] < lowCutValue) {
            i++;
        }
        alow = i;
        i = 255;
        double highCutValue = histoAcumulado[histoAcumulado.length - 1] - ((size*q)/size);
        System.out.println("valorCorteHigh " + highCutValue);
        while (histoAcumulado[i] > highCutValue) {
            i--;
        }
        ahigh = i;
        System.out.println("alow " + alow + " ahigh " + ahigh);
        //Generamos valores para esta funcion
        for (i = 0; i < 256; i++) {
            int valorPixel;
            if (i <= alow) {
                valorPixel = (int) amin;
            } else if (i >= ahigh) {
                valorPixel = (int) amax;
            } else {
                valorPixel = (int)(amin + (i - alow) * ((amax-amin)/(ahigh-alow)));
            }
            valoresGenerados[i] = valorPixel;
        }
        for (int x = 0; x < copiaImagen.getWidth(); x++) {
            for (int y = 0; y < copiaImagen.getHeight(); y++) {
                Color color = new Color(copiaImagen.getRGB(x, y));
                Color colorContrasteAutomaticoAcumu = new Color(valoresGenerados[color.getRed()], valoresGenerados[color.getGreen()], valoresGenerados[color.getBlue()]);
                copiaImagen.setRGB(x, y, colorContrasteAutomaticoAcumu.getRGB());
            }
        }
        return copiaImagen;
    }
    
    public BufferedImage especHistogramaInterpolacion(BufferedImage imagen, int puntos) {
        BufferedImage copiaImagen = ImageUtils.deepCopy(imagen);
        Histograma histograma = new Histograma(imagen);
        //Calcular puntos
        double histogramaAcumulado[] = histograma.obtenerHistogramaAcumulativoGrises(255);
        double qs[] = new double[puntos+1];
        int as[] = new int[puntos+1];
        double mediaSegmento = histogramaAcumulado.length / puntos;
        //punto a0,q0
        as[0] = 0;
        qs[0] = histogramaAcumulado[as[0]];
        System.out.println("a0 " + as[0] + " q0 " + qs[0]);
        
        for (int i = 1; i <= puntos; i++) {
            as[i] = i == puntos ? histogramaAcumulado.length - 1 : (int)(as[i-1] + mediaSegmento);
            qs[i] = histogramaAcumulado[as[i]]; 
            System.out.println("a"+ i +" " + as[i] + " q"+ i +" " + qs[i]);
        }
        
        //Generamos valores para esta funcion
        for (int i = 0; i < histogramaAcumulado.length; i++) {
            double b = histogramaAcumulado[i];
            int valorPixel;
            if (b <= qs[0]) {
                valorPixel = 0;
            } else if (b >= 1) {
                valorPixel = histogramaAcumulado.length - 1;
            } else {
                int n = puntos - 1;
                while (n >= 0 && qs[n] > b) {
                    n--;
                }
                valorPixel = (int)(as[n] + (b - qs[n]) * ((as[n+1] - as[n])/(qs[n+1]-qs[n])));
            }
            valoresGenerados[i] = valorPixel;
        }
        for (int x = 0; x < copiaImagen.getWidth(); x++) {
            for (int y = 0; y < copiaImagen.getHeight(); y++) {
                Color color = new Color(copiaImagen.getRGB(x, y));
                Color colorBrillo = new Color(valoresGenerados[color.getRed()], valoresGenerados[color.getGreen()], valoresGenerados[color.getBlue()]);
                copiaImagen.setRGB(x, y, colorBrillo.getRGB());
            }
        }
        return copiaImagen;
    }

}
