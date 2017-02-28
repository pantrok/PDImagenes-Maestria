/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.parciales;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pdimagenes.herramientas.FiltrosRegionales;
import pdimagenes.herramientas.Histograma;
import pdimagenes.herramientas.OperacionPunto;
import pdimagenes.utils.ImageUtils;
import pdimagenes.utils.MathUtils;

/**
 *
 * @author USUARIO
 */
public class PrimerParcial {

    private Histograma ultimoHistograma;
    private Point centroPupila;
    private double radioPupila;
    private double radioIris;
    private double umbralIris;

    //TODO: Identificar zona de pupila
    // Identificar el centro de la circunferencia y sacar el radio hacia algún borde
    private BufferedImage pintarPupila(BufferedImage imagen) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        for (int y = 0; y < imagenModificada.getHeight(); y++) {
            for (int x = 0; x < imagenModificada.getWidth(); x++) {
                //Definimos valor del umbral como 25
                Color color = new Color(imagenModificada.getRGB(x, y));
                double promedio = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                if (promedio <= 25 && vecinosSimilares(imagen, x, y, 20, 25)) {
                    //Checar si este pixel tiene vecinos con las mismas caracteristicas
                    imagenModificada.setRGB(x, y, Color.RED.getRGB());
                }
            }
        }
        return imagenModificada;
    }

    //Identifcar densidades de pixeles
    private boolean vecinosSimilares(BufferedImage imagen, int x, int y, int tamanoMuestra, int colorRGB) {
        //Muestra de 10 pixeles, checar hacia la derecha, abajo, izquierda y arriba
        int muestraDerecha = imagen.getWidth() - x < tamanoMuestra ? imagen.getWidth() - x : tamanoMuestra;
        int muestraIzquierda = x <= (tamanoMuestra - 1) ? x : tamanoMuestra;
        int muestraAbajo = imagen.getHeight() - y < tamanoMuestra ? imagen.getHeight() - y : tamanoMuestra;
        int muestraArriba = y <= (tamanoMuestra - 1) ? y : tamanoMuestra;

        //Checamos que cumpla regla de muestras hacia la derecha y abajo o hacia la izquierda y arriba
        //o dos muestras hacia la derecha o izquierda
        //Primer caso
        boolean cumpleCriterio = true;
        for (int i = x; i < (x + muestraDerecha); i++) {
            Color color = new Color(imagen.getRGB(i, y));
            double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
            //System.out.println(i + " " + y + " " + promVecino);
            if (promVecino > colorRGB) {
                cumpleCriterio = false;
                break;
            }
        }

        for (int i = y; i < (y + muestraAbajo); i++) {
            Color color = new Color(imagen.getRGB(x, i));
            double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
            //System.out.println(x + " " + i + " " + promVecino);
            if (promVecino > colorRGB) {
                cumpleCriterio = false;
                break;
            }
        }

        if (cumpleCriterio) {
            //System.out.println("Primer caso");
            return true;
        }

        //Segundo caso
        cumpleCriterio = true;
        for (int i = x; i >= (x - muestraIzquierda); i--) {
            Color color = new Color(imagen.getRGB(i, y));
            double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
            if (promVecino > colorRGB) {
                cumpleCriterio = false;
                break;
            }
        }

        for (int i = y; i >= (y - muestraArriba); i--) {
            Color color = new Color(imagen.getRGB(x, i));
            double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
            if (promVecino > colorRGB) {
                cumpleCriterio = false;
                break;
            }
        }

        if (cumpleCriterio) {
            //System.out.println("Segundo caso");
            return true;
        }

        //Tercer caso
        if ((imagen.getWidth() - x) >= (tamanoMuestra * 2)) {
            cumpleCriterio = true;
            muestraDerecha = tamanoMuestra * 2;
            for (int i = x; i < (x + muestraDerecha); i++) {
                Color color = new Color(imagen.getRGB(i, y));
                double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                if (promVecino > colorRGB) {
                    cumpleCriterio = false;
                    break;
                }
            }
            if (cumpleCriterio) {
                //System.out.println("Tercer caso");
                return true;
            }

        }

        //Cuarto caso
        if (x >= (tamanoMuestra * 2)) {
            cumpleCriterio = true;
            muestraIzquierda = tamanoMuestra * 2;
            for (int i = x; i >= (x - muestraIzquierda); i--) {
                Color color = new Color(imagen.getRGB(i, y));
                double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                if (promVecino > colorRGB) {
                    cumpleCriterio = false;
                    break;
                }
            }

            if (cumpleCriterio) {
                //System.out.println("Cuarto caso");
                return true;
            }

        }

        //Quinto caso
        if ((imagen.getHeight() - y) >= (tamanoMuestra * 2)) {
            cumpleCriterio = true;
            muestraAbajo = tamanoMuestra * 2;
            for (int i = y; i < (y + muestraAbajo); i++) {
                Color color = new Color(imagen.getRGB(x, i));
                double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                if (promVecino > colorRGB) {
                    cumpleCriterio = false;
                    break;
                }
            }
            if (cumpleCriterio) {
                //System.out.println("Quinto caso");
                return true;
            }

        }

        //Sexto caso
        if (y >= (tamanoMuestra * 2)) {
            cumpleCriterio = true;
            muestraArriba = tamanoMuestra * 2;
            for (int i = y; i >= (y - muestraArriba); i--) {
                Color color = new Color(imagen.getRGB(x, i));
                double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                if (promVecino > colorRGB) {
                    cumpleCriterio = false;
                    break;
                }
            }

            if (cumpleCriterio) {
                //System.out.println("Sexto caso");
                return true;
            }

        }

        return false;

    }

    private boolean vecinosSimilaresMayor(BufferedImage imagen, int x, int y, int tamanoMuestra, int colorRGB) {
        //Muestra de 10 pixeles, checar hacia la derecha, abajo, izquierda y arriba
        int muestraDerecha = imagen.getWidth() - x < tamanoMuestra ? imagen.getWidth() - x : tamanoMuestra;
        int muestraIzquierda = x <= (tamanoMuestra - 1) ? x : tamanoMuestra;
        int muestraAbajo = imagen.getHeight() - y < tamanoMuestra ? imagen.getHeight() - y : tamanoMuestra;
        int muestraArriba = y <= (tamanoMuestra - 1) ? y : tamanoMuestra;

        //Checamos que cumpla regla de muestras hacia la derecha y abajo o hacia la izquierda y arriba
        //o dos muestras hacia la derecha o izquierda
        //Primer caso
        boolean cumpleCriterio = true;
        for (int i = x; i < (x + muestraDerecha); i++) {
            Color color = new Color(imagen.getRGB(i, y));
            double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
            //System.out.println(i + " " + y + " " + promVecino);
            if (promVecino < colorRGB) {
                cumpleCriterio = false;
                break;
            }
        }

        for (int i = y; i < (y + muestraAbajo); i++) {
            Color color = new Color(imagen.getRGB(x, i));
            double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
            //System.out.println(x + " " + i + " " + promVecino);
            if (promVecino < colorRGB) {
                cumpleCriterio = false;
                break;
            }
        }

        if (cumpleCriterio) {
            //System.out.println("Primer caso");
            return true;
        }

        //Segundo caso
        cumpleCriterio = true;
        for (int i = x; i >= (x - muestraIzquierda); i--) {
            Color color = new Color(imagen.getRGB(i, y));
            double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
            if (promVecino < colorRGB) {
                cumpleCriterio = false;
                break;
            }
        }

        for (int i = y; i >= (y - muestraArriba); i--) {
            Color color = new Color(imagen.getRGB(x, i));
            double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
            if (promVecino < colorRGB) {
                cumpleCriterio = false;
                break;
            }
        }

        if (cumpleCriterio) {
            //System.out.println("Segundo caso");
            return true;
        }

        //Tercer caso
        if ((imagen.getWidth() - x) >= (tamanoMuestra * 2)) {
            cumpleCriterio = true;
            muestraDerecha = tamanoMuestra * 2;
            for (int i = x; i < (x + muestraDerecha); i++) {
                Color color = new Color(imagen.getRGB(i, y));
                double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                if (promVecino < colorRGB) {
                    cumpleCriterio = false;
                    break;
                }
            }
            if (cumpleCriterio) {
                //System.out.println("Tercer caso");
                return true;
            }

        }

        //Cuarto caso
        if (x >= (tamanoMuestra * 2)) {
            cumpleCriterio = true;
            muestraIzquierda = tamanoMuestra * 2;
            for (int i = x; i >= (x - muestraIzquierda); i--) {
                Color color = new Color(imagen.getRGB(i, y));
                double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                if (promVecino < colorRGB) {
                    cumpleCriterio = false;
                    break;
                }
            }

            if (cumpleCriterio) {
                //System.out.println("Cuarto caso");
                return true;
            }

        }

        //Quinto caso
        if ((imagen.getHeight() - y) >= (tamanoMuestra * 2)) {
            cumpleCriterio = true;
            muestraAbajo = tamanoMuestra * 2;
            for (int i = y; i < (y + muestraAbajo); i++) {
                Color color = new Color(imagen.getRGB(x, i));
                double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                if (promVecino < colorRGB) {
                    cumpleCriterio = false;
                    break;
                }
            }
            if (cumpleCriterio) {
                //System.out.println("Quinto caso");
                return true;
            }

        }

        //Sexto caso
        if (y >= (tamanoMuestra * 2)) {
            cumpleCriterio = true;
            muestraArriba = tamanoMuestra * 2;
            for (int i = y; i >= (y - muestraArriba); i--) {
                Color color = new Color(imagen.getRGB(x, i));
                double promVecino = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                if (promVecino < colorRGB) {
                    cumpleCriterio = false;
                    break;
                }
            }

            if (cumpleCriterio) {
                //System.out.println("Sexto caso");
                return true;
            }

        }

        return false;

    }

    private BufferedImage eliminarLuces(BufferedImage imagen) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        //Pasada en las xs
        for (int y = 0; y < imagenModificada.getHeight(); y++) {
            int x0 = -1, x1 = -1;
            boolean procesarFila = false;
            for (int x = 0; x < imagenModificada.getWidth(); x++) {
                //Definimos valor del umbral como 25
                //Color color = new Color(imagenModificada.getRGB(x, y));
                if (x0 == -1 && imagenModificada.getRGB(x, y) == Color.RED.getRGB()) {
                    x0 = x;
                    procesarFila = true;
                }

                if (x0 > -1 && imagenModificada.getRGB(x, y) == Color.RED.getRGB()) {
                    x1 = Math.max(x1, x);
                }
            }
            if (procesarFila) {
                //System.out.println(x0 + " " + x1);
                //Recorremos toda la fila
                for (int i = x0; i <= x1; i++) {
                    if (imagenModificada.getRGB(i, y) != Color.RED.getRGB()) {
                        //System.out.println("Deberia de pinta...");
                        imagenModificada.setRGB(i, y, Color.RED.getRGB());
                    }
                }
            }
        }

        //Pasada en las ys
        for (int x = 0; x < imagenModificada.getWidth(); x++) {
            int y0 = -1, y1 = -1;
            boolean procesarFila = false;
            for (int y = 0; y < imagenModificada.getHeight(); y++) {
                //Definimos valor del umbral como 25
                //Color color = new Color(imagenModificada.getRGB(x, y));
                if (y0 == -1 && imagenModificada.getRGB(x, y) == Color.RED.getRGB()) {
                    y0 = y;
                    procesarFila = true;
                }

                if (y0 > -1 && imagenModificada.getRGB(x, y) == Color.RED.getRGB()) {
                    y1 = Math.max(y1, y);
                }
            }
            if (procesarFila) {
                //System.out.println(x0 + " " + x1);
                //Recorremos toda la fila
                for (int i = y0; i <= y1; i++) {
                    if (imagenModificada.getRGB(x, i) != Color.RED.getRGB()) {
                        //System.out.println("Deberia de pinta...");
                        imagenModificada.setRGB(x, i, Color.RED.getRGB());
                    }
                }
            }
        }
        return imagenModificada;
    }

    //TODO: Encontrar centro y radio, despues encontrar un radio promedio y colorear
    private void encontrarCentroPupila(BufferedImage imagen) {
        //Encontrar x mas pequeño, sobre la misma fila encontrar el x mas grande, sacar punto medio
        int x0 = -1, x1 = -1;
        double pmX = 0.0;
        pf1:
        for (int x = 0; x < imagen.getWidth(); x++) {
            for (int y = 0; y < imagen.getHeight(); y++) {
                if (x0 < 0 && imagen.getRGB(x, y) == Color.RED.getRGB()) {
                    x0 = x;
                    for (int i = x0; i < imagen.getWidth(); i++) {
                        if (imagen.getRGB(i, y) == Color.RED.getRGB()) {
                            x1 = Math.max(x1, i);
                        }
                    }
                    break pf1;
                }
            }
        }
        pmX = (x0 + x1) / 2.0;

        //Encontrar y mas pequeño, sobre esa misma columna encontrar y mas grande, sacar punto medio
        int y0 = -1, y1 = -1;
        double pmY = 0.0;
        pf2:
        for (int y = 0; y < imagen.getHeight(); y++) {
            for (int x = 0; x < imagen.getWidth(); x++) {
                if (y0 < 0 && imagen.getRGB(x, y) == Color.RED.getRGB()) {
                    y0 = y;
                    for (int i = y0; i < imagen.getHeight(); i++) {
                        if (imagen.getRGB(x, i) == Color.RED.getRGB()) {
                            y1 = Math.max(y1, i);
                        }
                    }
                    break pf2;
                }
            }
        }
        pmY = (y0 + y1) / 2.0;
        System.out.println("x0 " + x0 + " x1 " + x1);
        System.out.println("y0 " + y0 + " y1 " + y1);
        System.out.println("centro " + pmX + "," + pmY);
        //Sumar dos puntos medios y encontrar media entre los dos
        //A partir de ese punto medio encontrar su distancia a los cuatro puntos que se tienen, sacar media y tomarlo como un radio tentativo
        centroPupila = new Point((int) pmX, (int) pmY);
        radioPupila = Math.max((pmX - x0), (pmY - y0));

    }

    //TODO: Colorear contorno entre pupila e iris
    private BufferedImage colorearZonasFaltantes(BufferedImage imagen) {

        //Partiendo del centro de la pupila y con el radio que se tiene, recorrer la circunferencia y cualquier color que no sea rojo colorearlo de rojo
        //Siempre y cuando este en el radio delimitado y no se pase del valor de 100 que se toma como umbral
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        double radioMax = radioPupila * 1.1;
        int umbral = 100;
        for (int y = 0; y < imagen.getHeight(); y++) {
            for (int x = 0; x < imagen.getWidth(); x++) {
                double radioPunto = Math.sqrt(Math.pow((x - centroPupila.getX()), 2) + Math.pow((y - centroPupila.getY()), 2));
                if (radioPunto <= radioMax) {
                    if (imagenModificada.getRGB(x, y) != Color.RED.getRGB()) {
                        Color colorPunto = new Color(imagenModificada.getRGB(x, y));
                        double media = (colorPunto.getRed() + colorPunto.getGreen() + colorPunto.getBlue()) / 3.0;
                        if (media <= umbral) {
                            imagenModificada.setRGB(x, y, Color.RED.getRGB());
                        }
                    }
                }
            }
        }
        return imagenModificada;
    }

    public BufferedImage primerEtapa(BufferedImage imagen) {
        BufferedImage imagenPrimerEtapa = eliminarLuces(pintarPupila(imagen));
        encontrarCentroPupila(imagenPrimerEtapa);
        return colorearZonasFaltantes(imagenPrimerEtapa);
        //return imagenPrimerEtapa;
    }

    private void encontrarRadioIris(BufferedImage imagen) {
        //Encontrar x0 tentativo, proponer dos medidas, un radio * 2  o * 3
        if (radioPupila <= 50) {
            radioIris = radioPupila * 2.5;
        } else if (radioPupila > 50 && radioPupila <= 60) {
            radioIris = radioPupila * 2;
        } else if (radioPupila > 60 && radioPupila <= 68) {
            radioIris = radioPupila * 1.8;
        } else {
            radioIris = radioPupila * 1.6;
        }
        System.out.println("radio pupila " + radioPupila + " radioIris tentativo " + radioIris);
        double mediaRojo = (Color.RED.getRed() + Color.RED.getGreen() + Color.RED.getBlue()) / 3.0;
        List<Double> valores = new ArrayList<>();
        for (int x = (int) centroPupila.getX(); x >= (centroPupila.getX() - radioIris); x--) {
            Color color = new Color(imagen.getRGB(x, (int) centroPupila.getY()));
            double promedio = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
            if (promedio != mediaRojo) {
                System.out.println("valor " + promedio);
                valores.add(promedio);
            }
        }
        umbralIris = MathUtils.media(valores);
        System.out.println("Media " + umbralIris);
    }

    private BufferedImage colorearTodoMenosIris(BufferedImage imagen) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        double radioMax1 = radioIris * 1.05;
        double radioMax2 = radioIris * 1.12;
        for (int y = 0; y < imagen.getHeight(); y++) {
            for (int x = 0; x < imagen.getWidth(); x++) {
                double radioPunto = Math.sqrt(Math.pow((x - centroPupila.getX()), 2) + Math.pow((y - centroPupila.getY()), 2));
                Color colorPunto = new Color(imagenModificada.getRGB(x, y));
                double media = (colorPunto.getRed() + colorPunto.getGreen() + colorPunto.getBlue()) / 3.0;
                if (radioPunto > radioMax2 && imagenModificada.getRGB(x, y) != Color.RED.getRGB()) {
                    imagenModificada.setRGB(x, y, Color.RED.getRGB());
                } /*else if (radioPunto <= radioMax2 && radioPunto >= radioMax1 && media > 50) {
                    imagenModificada.setRGB(x, y, Color.RED.getRGB());
                }*/ else if (radioPunto <= radioMax2 && radioPunto >= radioMax1 && media > umbralIris /*&& vecinosSimilares(imagen, x, y, 100, (int)media)*/) {
                    imagenModificada.setRGB(x, y, Color.RED.getRGB());

                }
            }
        }
        return imagenModificada;
    }

    private BufferedImage coloresOriginales(BufferedImage original, BufferedImage modificada) {
        BufferedImage imagenNormalizada = ImageUtils.deepCopy(modificada);
        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                if (modificada.getRGB(x, y) != Color.RED.getRGB()) {
                    if (modificada.getRGB(x, y) == Color.GREEN.getRGB()) {
                        System.out.println("Hay punto verde");
                    }
                    imagenNormalizada.setRGB(x, y, original.getRGB(x, y));
                }
            }
        }
        return imagenNormalizada;
    }

    public BufferedImage segundaEtapa(BufferedImage imagen) {

        BufferedImage imagenSegundaEtapa;
        imagenSegundaEtapa = new OperacionPunto().correccionGamaExcluyendo(imagen, 2.5, Color.RED.getRGB());
        ultimoHistograma = new Histograma(imagenSegundaEtapa);
        ultimoHistograma.obtenerHistogramaExcluyendo(Color.RED.getRGB());
        imagenSegundaEtapa = new OperacionPunto().contrasteAutomaticoMejorado(imagenSegundaEtapa, ultimoHistograma, 0, 255);
        //imagenSegundaEtapa = new OperacionPunto().correccionGamaExcluyendo(imagen, 2.5, Color.RED.getRGB());
        //encontrarRadioIris(imagen);
        encontrarRadioIris(imagenSegundaEtapa);
        imagenSegundaEtapa = colorearTodoMenosIris(imagenSegundaEtapa);
        //Encontrar ultimos pixeles que no podrian ser del area de interes
        //Regresar los pixeles que no sean rojos a su color original
        imagenSegundaEtapa = coloresOriginales(imagen, imagenSegundaEtapa);
        ultimoHistograma = new Histograma(imagenSegundaEtapa);
        ultimoHistograma.obtenerHistogramaExcluyendo(Color.RED.getRGB());
        return imagenSegundaEtapa;
    }

    private BufferedImage eliminarPuntosBlancosNegros(BufferedImage imagen) {
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        for (int x = 0; x < imagenModificada.getWidth(); x++) {
            for (int y = 0; y < imagenModificada.getHeight(); y++) {
                Color colorPunto = new Color(imagenModificada.getRGB(x, y));
                double media = (colorPunto.getRed() + colorPunto.getGreen() + colorPunto.getBlue()) / 3.0;
                if (media > 245  && colorPunto.getRGB() != Color.RED.getRGB()) {
                    imagenModificada.setRGB(x, y, Color.RED.getRGB());
                } else if (media <= 10  && colorPunto.getRGB() != Color.RED.getRGB()) {
                    imagenModificada.setRGB(x, y, Color.RED.getRGB());
                }
            }
        }
        return imagenModificada;
    }

    private BufferedImage eliminarPuntosRojos(BufferedImage imagen) {
        int minimosRojos = 10;
        BufferedImage imagenModificada = ImageUtils.deepCopy(imagen);
        for (int y = 0; y < imagenModificada.getHeight(); y++) {
            int contadorRojos = 0; boolean sensandoRoi = false;
            for (int x = 0; x < imagenModificada.getWidth(); x++) {
                if (imagenModificada.getRGB(x, y) == Color.RED.getRGB()) {
                    if (sensandoRoi) {
                        sensandoRoi = false;
                    }
                    contadorRojos++;
                } else {
                    if (contadorRojos <= minimosRojos && !sensandoRoi) {
                        for (int x1 = (x - contadorRojos); x1 <= x; x1++) {
                            imagenModificada.setRGB(x1, y, Color.GREEN.getRGB());
                        }
                    }
                    if (!sensandoRoi) {
                        sensandoRoi = true;
                        contadorRojos = 0;
                    }

                }
            }
        }
        minimosRojos = 3;
        for (int x = 0; x < imagenModificada.getWidth(); x++) {
            int contadorRojos = 0;boolean sensandoRoi = false;
            for (int y = 0; y < imagenModificada.getHeight(); y++) {
                if (imagenModificada.getRGB(x, y) == Color.RED.getRGB()) {
                    if (sensandoRoi) {
                        sensandoRoi = false;
                    }
                    contadorRojos++;
                } else {
                    if (contadorRojos <= minimosRojos && !sensandoRoi) {
                        for (int y1 = (y - contadorRojos); y1 <= y; y1++) {
                            imagenModificada.setRGB(x, y1, Color.GREEN.getRGB());
                        }
                    }
                    if (!sensandoRoi) {
                        sensandoRoi = true;
                        contadorRojos = 0;
                    }

                }
            }
        }
        return imagenModificada;
    }

    public BufferedImage terceraEtapa(BufferedImage imagen, BufferedImage imagenOriginal) {

        BufferedImage imagenTerceraEtapa;
        ultimoHistograma = new Histograma(imagen);
        ultimoHistograma.obtenerHistogramaExcluyendo(Color.RED.getRGB());
        imagenTerceraEtapa = new OperacionPunto().contrasteAutomaticoMejorado(imagen, ultimoHistograma, 0, 255);
        imagenTerceraEtapa = eliminarPuntosBlancosNegros(imagenTerceraEtapa);
        imagenTerceraEtapa = eliminarPuntosRojos(imagenTerceraEtapa);
        imagenTerceraEtapa = coloresOriginales(imagenOriginal, imagenTerceraEtapa);
        return imagenTerceraEtapa;
    }

    public BufferedImage cuartaEtapa(BufferedImage imagen) {
        BufferedImage imagenCuartaEtapa;
        imagenCuartaEtapa = new OperacionPunto().correccionGamaExcluyendo(imagen, 2.5, Color.RED.getRGB());
        ultimoHistograma = new Histograma(imagenCuartaEtapa);
        ultimoHistograma.obtenerHistogramaExcluyendo(Color.RED.getRGB());
        imagenCuartaEtapa = new OperacionPunto().contrasteAutomaticoMejorado(imagenCuartaEtapa, ultimoHistograma, 0, 255);
        ultimoHistograma = new Histograma(imagenCuartaEtapa);
        ultimoHistograma.obtenerHistogramaExcluyendo(Color.RED.getRGB());
        return imagenCuartaEtapa;
    }

    public Histograma getUltimoHistograma() {
        return ultimoHistograma;
    }

}
