/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import pdimagenes.enums.Canal;

/**
 *
 * @author daniel
 */
public class HistogramaVisual {

    private double red[];
    private double green[];
    private double blue[];
    private final BufferedImage imagen;
    private final int tamanoImagen;

    public HistogramaVisual(BufferedImage imagen) {
        this.imagen = imagen;
        tamanoImagen = this.imagen.getHeight() * this.imagen.getWidth();
        obtenerCanales();
    }

    //Tener los arreglos de valores para cada canal
    private void obtenerCanales() {
        
        red = new double[256];
        green = new double[256];
        blue = new double[256];
        
        for (int i = 0; i < 256; i++) {
            red[i] = 0;
            green[i] = 0;
            blue[i] = 0;
        }
        
        for (int x = 0; x < imagen.getWidth(); x++) {
            for (int y = 0; y < imagen.getHeight(); y++) {
                Color color = new Color(imagen.getRGB(x, y));
                red[color.getRed()]++;
                green[color.getGreen()]++;
                blue[color.getBlue()]++;
            }
        }
        
        //Se normaliza el histograma
        for (int i = 0; i < 256; i++) {
            red[i] = red[i] / tamanoImagen;
            green[i] = green[i] / tamanoImagen;
            blue[i] = blue[i] / tamanoImagen;
        }
        
        

    }
    
    public ChartPanel getHistograma(Canal canal, Color color, String nombre) {
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        double canalArray[];
        switch (canal) {
            case R:
                canalArray = red;
                break;
            case G:
                canalArray = green;
                break;
            case B:
                canalArray = blue;
                break;
            default: canalArray = new double[1];
        }
        for (int i = 0; i < canalArray.length; i++) {
            datos.addValue(canalArray[i], nombre, String.valueOf(i) + " ");
        }
        JFreeChart graph = ChartFactory.createBarChart(null, null, null, datos, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot cplot = (CategoryPlot) graph.getPlot();
        cplot.setBackgroundPaint(Color.white);
        ((BarRenderer) cplot.getRenderer()).setBarPainter(new StandardBarPainter());
        BarRenderer r = (BarRenderer) graph.getCategoryPlot().getRenderer();
        r.setSeriesPaint(0, color);
        return new ChartPanel(graph);
    }
    
    public ChartPanel getHistograma(double valores[], Color color, String nombre) {
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        for (int i = 0; i < valores.length; i++) {
            datos.addValue(valores[i], nombre, String.valueOf(i) + " ");
        }
        JFreeChart graph = ChartFactory.createBarChart(null, null, null, datos, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot cplot = (CategoryPlot) graph.getPlot();
        cplot.setBackgroundPaint(Color.white);
        ((BarRenderer) cplot.getRenderer()).setBarPainter(new StandardBarPainter());
        BarRenderer r = (BarRenderer) graph.getCategoryPlot().getRenderer();
        r.setSeriesPaint(0, color);
        return new ChartPanel(graph);
    }

}
