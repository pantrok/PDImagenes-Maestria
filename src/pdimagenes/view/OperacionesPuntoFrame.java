/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import pdimagenes.enums.Canal;
import pdimagenes.herramientas.FiltroGris;
import pdimagenes.herramientas.Histograma;
import pdimagenes.herramientas.OperacionPunto;

/**
 *
 * @author daniel
 */
public class OperacionesPuntoFrame extends javax.swing.JFrame {

    private final ImagenesFrame imagenesFrame;
    private final BufferedImage imagen;
    private BufferedImage imagenProcesada;
    private BufferedImage imagenGrises;
    private CorreccionGamaPanel panelCorreccionGama;
    private ContrastePanel contrastePanel;
    private BrilloPanel brilloPanel;
    private UmbralPanel umbralPanel;
    private OperacionPunto operacionPunto;
    private FiltroGris filtroGris;
    private ContrasteAutomaticoCum automaticoCumPanel;
    private HistogramaVisual visual;

    /**
     * Creates new form OperacionesPuntoFrame
     *
     * @param imagenesFrame
     */
    public OperacionesPuntoFrame(ImagenesFrame imagenesFrame) {
        initComponents();
        this.imagenesFrame = imagenesFrame;
        this.imagen = imagenesFrame.getImage();
        operacionPunto = new OperacionPunto();
        filtroGris = new FiltroGris();
        panelCorreccionGama = new CorreccionGamaPanel();
        contrastePanel = new ContrastePanel();
        brilloPanel = new BrilloPanel();
        umbralPanel = new UmbralPanel();
        automaticoCumPanel = new ContrasteAutomaticoCum();
        init();
    }

    private void init() {
        jLabel1.setIcon(new ImageIcon(imagen));
        jPanel1.setLayout(new BorderLayout());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Operaciones Punto");
        setPreferredSize(new java.awt.Dimension(950, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(960, 700));
        getContentPane().setLayout(null);

        jScrollPane1.setViewportView(jLabel1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(23, 12, 599, 650);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Negativo", "Correción Gama", "Brillo", "Contraste", "Umbral", "Contraste Automático", "Contraste Automático Mejorado", "Contraste Automático Acumulado" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(640, 293, 314, 20);

        jPanel1.setMaximumSize(new java.awt.Dimension(0, 295));
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 295));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 314, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 295, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(640, 326, 314, 295);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 314, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(640, 12, 314, 270);

        jButton3.setText("Visualizar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("Aceptar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton1))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3);
        jPanel3.setBounds(640, 627, 314, 35);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        imagenesFrame.setImage(imagenProcesada);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //Encontrar que operacion se quiere realizar al ver la opcion seleccionada del combo y aplicar operacion
        if (jComboBox1.getSelectedItem() instanceof String) {
            String operacion = (String) jComboBox1.getSelectedItem();
            switch (operacion) {
                case "Negativo":
                    imagenProcesada = operacionPunto.negativo(imagen);
                    jLabel1.setIcon(new ImageIcon(imagenProcesada));
                    break;
                case "Correción Gama": {
                    //Tomar valores de gama del panel de gama y aplicar operacion
                    double valorGama = panelCorreccionGama.getSliderValue();
                    imagenProcesada = operacionPunto.correccionGama(imagen, valorGama);
                    jLabel1.setIcon(new ImageIcon(imagenProcesada));
                    break;
                }
                case "Brillo": {
                    //Tomar valores de gama del panel de gama y aplicar operacion
                    double factorBrillo = brilloPanel.getSliderValue();
                    imagenProcesada = operacionPunto.brillo(imagen, factorBrillo);
                    jLabel1.setIcon(new ImageIcon(imagenProcesada));
                    break;
                }
                case "Contraste": {
                    //Tomar valores de gama del panel de gama y aplicar operacion
                    double factorContraste = contrastePanel.getSliderValue();
                    imagenProcesada = operacionPunto.contraste(imagen, factorContraste);
                    jLabel1.setIcon(new ImageIcon(imagenProcesada));
                    break;
                }
                case "Umbral": {
                    //Tomar valores de gama del panel de gama y aplicar operacion
                    double valorUmbral = umbralPanel.getSliderValue();
                    imagenProcesada = operacionPunto.umbral(imagenGrises, valorUmbral, umbralPanel.getA0Value(), umbralPanel.getA1Value());
                    jLabel1.setIcon(new ImageIcon(imagenProcesada));
                    break;
                } 
                case "Contraste Automático":
                    //imagenProcesada = operacionPunto.contrasteAutomatico(imagen, imagenesFrame.getHistograma(),0,255);
                    imagenProcesada = operacionPunto.contrasteAutomatico(imagenGrises, new Histograma(imagenGrises), 0, 255);
                    visual = new HistogramaVisual(imagenProcesada);
                    jPanel2.removeAll();
                    jPanel2.revalidate();
                    jPanel2.setLayout(new java.awt.BorderLayout());
                    jPanel2.add(visual.getHistograma(Canal.R, Color.GRAY, "GRAY"), BorderLayout.CENTER);
                    jPanel2.repaint();
                    jLabel1.setIcon(new ImageIcon(imagenProcesada));
                    break;
                case "Contraste Automático Mejorado":
                    imagenProcesada = operacionPunto.contrasteAutomaticoMejorado(imagenGrises, new Histograma(imagenGrises), 0, 255);
                    visual = new HistogramaVisual(imagenProcesada);
                    jPanel2.removeAll();
                    jPanel2.revalidate();
                    jPanel2.setLayout(new java.awt.BorderLayout());
                    jPanel2.add(visual.getHistograma(Canal.R, Color.GRAY, "GRAY"), BorderLayout.CENTER);
                    jPanel2.repaint();
                    jLabel1.setIcon(new ImageIcon(imagenProcesada));
                    break;
                case "Contraste Automático Acumulado":
                    double porcentajeQ = automaticoCumPanel.getPorcentaje();
                    imagenProcesada = operacionPunto.contrasteAutomaticoAcumulado(imagenGrises, new Histograma(imagenGrises), porcentajeQ, 0, 255);
                    visual = new HistogramaVisual(imagenProcesada);
                    jPanel2.removeAll();
                    jPanel2.revalidate();
                    jPanel2.setLayout(new java.awt.BorderLayout());
                    jPanel2.add(visual.getHistograma(Canal.R, Color.GRAY, "GRAY"), BorderLayout.CENTER);
                    jPanel2.repaint();
                    jLabel1.setIcon(new ImageIcon(imagenProcesada));
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("Operacion no reconocida");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        imagenProcesada = null;
        jLabel1.setIcon(new ImageIcon(imagen));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if (jComboBox1.getSelectedItem() instanceof String) {
            String operacion = (String) jComboBox1.getSelectedItem();
            System.out.println("Removiendo todo");
            jPanel1.removeAll();
            jPanel1.revalidate();
            jPanel2.removeAll();
            jPanel2.revalidate();
            if (operacion.equals("Negativo")) {
                jLabel1.setIcon(new ImageIcon(imagen));
            } else if (operacion.equals("Correción Gama")) {
                jLabel1.setIcon(new ImageIcon(imagen));
                jPanel1.add(panelCorreccionGama);
            } else if (operacion.equals("Brillo")) {
                jLabel1.setIcon(new ImageIcon(imagen));
                jPanel1.add(brilloPanel);
            } else if (operacion.equals("Contraste")) {
                jLabel1.setIcon(new ImageIcon(imagen));
                jPanel1.add(contrastePanel);
            } else if (operacion.equals("Umbral")) {
                //Convertimos imagen a grises
                imagenGrises = filtroGris.grisPromedio(imagen);
                visual = new HistogramaVisual(imagenGrises);
                jPanel2.setLayout(new java.awt.BorderLayout());
                jPanel2.add(visual.getHistograma(Canal.R, Color.GRAY, "GRAY"), BorderLayout.CENTER);
                jPanel2.repaint();
                jLabel1.setIcon(new ImageIcon(imagenGrises));
                //Genero histograma
                jPanel1.add(umbralPanel);
            } else if (operacion.equals("Contraste Automático")) {
                //Convertimos imagen a grises
                imagenGrises = filtroGris.grisPromedio(imagen);
                visual = new HistogramaVisual(imagenGrises);
                jPanel2.setLayout(new java.awt.BorderLayout());
                jPanel2.add(visual.getHistograma(Canal.R, Color.GRAY, "GRAY"), BorderLayout.CENTER);
                jPanel2.repaint();
                jLabel1.setIcon(new ImageIcon(imagenGrises));
            } else if (operacion.equals("Contraste Automático Mejorado")) {
                //Convertimos imagen a grises
                imagenGrises = filtroGris.grisPromedio(imagen);
                visual = new HistogramaVisual(imagenGrises);
                jPanel2.setLayout(new java.awt.BorderLayout());
                jPanel2.add(visual.getHistograma(Canal.R, Color.GRAY, "GRAY"), BorderLayout.CENTER);
                jPanel2.repaint();
                jLabel1.setIcon(new ImageIcon(imagenGrises));
            } else if (operacion.equals("Contraste Automático Acumulado")) {
                //Convertimos imagen a grises
                imagenGrises = filtroGris.grisPromedio(imagen);
                visual = new HistogramaVisual(imagenGrises);
                jPanel2.setLayout(new java.awt.BorderLayout());
                jPanel2.add(visual.getHistograma(Canal.R, Color.GRAY, "GRAY"), BorderLayout.CENTER);
                jPanel2.repaint();
                jLabel1.setIcon(new ImageIcon(imagenGrises));
                jPanel1.add(automaticoCumPanel);
            }
            jPanel1.repaint();
            jPanel2.repaint();
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
