/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class MathUtils {
    
    public static double desviacionEstandar(List<Double> valores, double valorRespectoA) {
        //Calculando desviacion estandar
        List<Double> deviations = new ArrayList<>();
        for (Double value : valores) {
            deviations.add(Math.pow((value - valorRespectoA), 2));
        }
        double standardDeviation = 0.0;
        for (Double deviation : deviations) {
            standardDeviation += deviation;
        }
        standardDeviation /= valores.size();
        standardDeviation = Math.sqrt(standardDeviation);
        return standardDeviation;
    }
    
    public static double media(List<Double> valores) {
        double media = 0.0;
        for (Double value : valores) {
            media += value;
        }
        media /= valores.size();
        return media;
    }
    
    public static double mediana(List<Double> valores) {
        //Mediana
        int size = valores.size();
        double mediana = 0.0;
        if (size % 2 == 0) {
            int posicion1 = size / 2;
            int posicion2 = posicion1 + 1;
            mediana = (valores.get(posicion1) + valores.get(posicion2)) / 2;
        } else {
            int posicion = (int)Math.ceil(size / 2);
            mediana = valores.get(posicion);
        }
        return mediana;
    }
    
}
