/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.herramientas;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pdimagenes.beans.Rango;
import pdimagenes.enums.Canal;
import pdimagenes.utils.ImageUtils;

/**
 *
 * @author daniel
 */
public class Segmentacion {

    private BufferedImage imagen;
    private Map<Roi, List<ReglaSegmentacion>> reglasRois;

    public Segmentacion() {
    }

    public Segmentacion(BufferedImage imagen, List<Roi> rois) {
        this.imagen = ImageUtils.deepCopy(imagen);
        generarReglasRois(rois);
    }

    private void generarReglasRois(List<Roi> rois) {
        reglasRois = new HashMap<>();
        for (Roi roi : rois) {
            //Para cada roi se generar una regla nueva para cada canal
            List<ReglaSegmentacion> reglas = new ArrayList<>();
            for (Canal canal : Canal.values()) {
                ReglaSegmentacion regla = new ReglaSegmentacion(roi.getImagen(), canal, roi.getHistograma());
                reglas.add(regla);
            }
            reglasRois.put(roi, reglas);
        }
    }

    public BufferedImage segmentarRois() {

        //Iterar en el ancho y alto de la imagen para obtener el total de la imagen
        //En cada pixel preguntar para cada roi si se cumplen todas sus reglas en alguna de sus rangos de valores para cada canal, de ser asi es pixel objeto
        //De no ser asi lo mandamos a otro color
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                boolean cumpleReglas = true;
                Color c1 = new Color(imagen.getRGB(i, j));
                for (Map.Entry<Roi, List<ReglaSegmentacion>> entrada : reglasRois.entrySet()) {
                    cumpleReglas = true;
                    cicloReglas:
                    for (ReglaSegmentacion regla : entrada.getValue()) {
                        //Obtenemos valores de cada canal
                        switch (regla.getCanal()) {
                            case R:
                                if (!estaEnRango(c1.getRed(), regla.getRangos())) {
                                    cumpleReglas = false;
                                    break cicloReglas;
                                }
                                break;
                            case G:
                                if (!estaEnRango(c1.getGreen(), regla.getRangos())) {
                                    cumpleReglas = false;
                                    break cicloReglas;
                                }
                                break;
                            case B:
                                if (!estaEnRango(c1.getBlue(), regla.getRangos())) {
                                    cumpleReglas = false;
                                    break cicloReglas;
                                }
                                break;
                        }
                    }
                    if (cumpleReglas) {
                        break;
                    }
                }
                if (!cumpleReglas) {
                    //Si no cumple todas las reglas lo mandamos a negro
                    Color c2 = new Color(0, 0, 0);
                    imagen.setRGB(i, j, c2.getRGB());
                }
            }
        }

        return imagen;
    }

    private boolean estaEnRango(int rgb, List<Rango> rangos) {
        for (Rango rango : rangos) {
            if (rgb >= rango.getInicio() && rgb <= rango.getTermino()) {
                return true; //Esta en algun rango
            }
        }
        return false;
    }

    

}
