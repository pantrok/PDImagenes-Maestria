/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.herramientas;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import pdimagenes.beans.Rango;
import pdimagenes.enums.Canal;

/**
 *
 * @author daniel
 */
public class ReglaSegmentacion {

    private List<Rango> rangos;
    private Canal canal;

    public ReglaSegmentacion() {
    }

    public ReglaSegmentacion(BufferedImage imagen, Canal canal, Histograma h) {
        this.canal = canal;
        rangos = new ArrayList<>();
        seleccionarCanal(h);
    }

    public Canal getCanal() {
        return canal;
    }

    public List<Rango> getRangos() {
        return rangos;
    }

    private void seleccionarCanal(Histograma h) {

        switch (canal) {
            case R:
                generarRango(h.getRed(), 0);
                break;
            case G:
                generarRango(h.getGreen(), 0);
                break;
            case B:
                generarRango(h.getBlue(), 0);
                break;
        }

    }

    private void generarRango(double frecuenciasCanal[], double promedio) {

        //Sacamos el promedio de valores
        if (promedio == 0) {
            double totalValores = 0;
            for (int i = 0; i < 256; i++) {
                totalValores += frecuenciasCanal[i];
            }
            promedio = totalValores / 256;
        }
        //Encontramos maximo considerando que el valor sea mayor al promedio y que no este en un rango si ya existe alguno
        double max = 0;
        int posicionMax = 0;
        for (int i = 0; i < 256; i++) {
            if (frecuenciasCanal[i] > max && frecuenciasCanal[i] > promedio
                    && !existeRango(i)) {
                max = frecuenciasCanal[i];
                posicionMax = i;
            }
        }

        if (max > 0) {
            //Encontramos el valor mas grande y cuando lo encontramos intentamos encontrar el rango de valores 
            //Para ello hay que tomar muestras de valores a partir del mayor, tal vez dos hacia la izquierda y derecha, encontrar la media
            //El ultimo valor tomado hacia la izquierda y derecha sera el nuevo punto mayor, se hace el mismo analisis
            System.out.println("Canal " + canal);
            System.out.println("Valor maximo " + frecuenciasCanal[posicionMax] + " " + max);
            int pivote = 0;
            double deltaAnterior = tomarMuestra(frecuenciasCanal, posicionMax); //La primera vez este valor es el promedio de la muestra del maximo
            pivote = pivote == 0 ? (posicionMax - 3) : (pivote - 3);
            double deltaActual = tomarMuestra(frecuenciasCanal, pivote);
            double deltaGorro = 0;
            int inicioRango = 0, terminoRango = 0;

            //Iteramos hacia la izquierda
            System.out.println("Iterando hacia la izquierda");
            System.out.println("deltaAnterior " + deltaAnterior + " deltaActual " + deltaActual);
            while ((deltaAnterior - deltaActual) > deltaGorro || pivote > 0) {
                //Recorremos hacia la izquierda partiendo de la posicion de max
                deltaAnterior = deltaActual;
                pivote = pivote == 0 ? (posicionMax - 3) : (pivote - 3);
                deltaActual = tomarMuestra(frecuenciasCanal, pivote);
                System.out.println("deltaAnterior " + deltaAnterior + " deltaActual " + deltaActual);
            }
            //Cuando termine este ciclo hay que obtener el rango de inicio
            inicioRango = encontrarValorInicioRango(frecuenciasCanal, pivote);

            //Iteramos hacia la derecha
            pivote = 0;
            deltaAnterior = tomarMuestra(frecuenciasCanal, posicionMax); //La primera vez este valor es el promedio de la muestra del maximo
            pivote = pivote == 0 ? (posicionMax + 3) : (pivote + 3);
            deltaActual = tomarMuestra(frecuenciasCanal, pivote);
            while ((deltaAnterior - deltaActual) > deltaGorro || pivote < 255) {
                //Recorremos hacia la derecha partiendo de la posicion de max
                deltaAnterior = deltaActual;
                pivote = pivote == 0 ? (posicionMax + 3) : (pivote + 3);
                deltaActual = tomarMuestra(frecuenciasCanal, pivote);
            }
            //Cuando termine este ciclo hay que obtener el rango de termino
            terminoRango = encontrarValorTerminoRango(frecuenciasCanal, pivote);

            Rango rango = new Rango(inicioRango, terminoRango);
            rangos.add(rango);

            //Despues llamamos una vez mas a la funcion para checar si se puede encontrar un rango nuevo
            generarRango(frecuenciasCanal, promedio);
        }
    }

    private boolean existeRango(int i) {
        if (rangos.isEmpty()) {
            return false;
        } else {
            boolean existeRango = false;
            for (Rango rango : rangos) {
                if (i >= rango.getInicio() && i <= rango.getTermino()) {
                    existeRango = true;
                }
            }
            return existeRango;
        }
    }

    private double tomarMuestra(double frecuenciasCanal[], int pivote) {

        //Sumar valores de dos posiciones hacia la izquierda y dos a la derecha y hacer media
        //Validar que al querer obtener los valores de la izquierda o derecha aun se este en el rango de 0 a 255
        double media = 0;
        if (pivote < 256 && pivote > 0) {
            int tamanoMuestra = 1; //Siempre trae uno pues es la posicion del pivote
            double muestraIzquierda = 0;
            double muestraDerecha = 0;
            if (pivote - 2 >= 0) {
                //Podemos tomar dos valores
                muestraIzquierda = frecuenciasCanal[pivote - 2] + frecuenciasCanal[pivote - 1];
                tamanoMuestra += 2;
            } else if (pivote - 1 >= 0) {
                //Podemos tomar solo un valor
                muestraIzquierda = frecuenciasCanal[pivote - 1];
                tamanoMuestra += 1;
            }

            if (pivote + 2 <= 255) {
                //Podemos tomar dos valores
                muestraDerecha = frecuenciasCanal[pivote + 2] + frecuenciasCanal[pivote + 1];
                tamanoMuestra += 2;
            } else if (pivote + 1 <= 255) {
                //Podemos tomar solo un valor
                muestraDerecha = frecuenciasCanal[pivote + 1];
                tamanoMuestra += 1;
            }
            media = Math.abs((frecuenciasCanal[pivote] + muestraIzquierda + muestraDerecha) / tamanoMuestra);
        }

        return media;
    }

    private int encontrarValorInicioRango(double frecuenciasCanal[], int pivote) {
        int posicion = 0;
        if (pivote < 0) {
            pivote = 0;
        }
        for (posicion = pivote; posicion < 255; posicion++) {
            if (frecuenciasCanal[posicion] != 0) {
                return posicion;
            }
        }
        return posicion;
    }

    private int encontrarValorTerminoRango(double frecuenciasCanal[], int pivote) {
        int posicion = 0;
        if (pivote > 255) {
            pivote = 255;
        }
        for (posicion = pivote; posicion >= 0; posicion--) {
            if (frecuenciasCanal[posicion] != 0) {
                return posicion;
            }
        }
        return posicion;
    }

}
