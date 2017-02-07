/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdimagenes.beans;

/**
 *
 * @author daniel
 */
public class Rango {

    private int inicio;
    private int termino;

    public Rango(int inicio, int termino) {
        this.inicio = inicio;
        this.termino = termino;
    }

    public Rango() {
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getTermino() {
        return termino;
    }

    public void setTermino(int termino) {
        this.termino = termino;
    }
    
}
