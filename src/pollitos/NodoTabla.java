/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollitos;

import Automata.NodoEstado;
import LALR.NodoCaso;
import java.util.ArrayList;

/**
 *
 * @author luisGonzalez
 */
public class NodoTabla {
    
    private String accion;
    private int noCaso;
    private Simbolos simbolo;
    private NodoCaso casoColumna;
    private int idCasoFila;
    private boolean filaActiva;

    public int getIdCasoFila() {
        return idCasoFila;
    }

    public void setIdCasoFila(int idCasoFila) {
        this.idCasoFila = idCasoFila;
    }

    public boolean isFilaActiva() {
        return filaActiva;
    }

    public void setFilaActiva(boolean filaActiva) {
        this.filaActiva = filaActiva;
    }

    
    public NodoCaso getCasoColumna() {
        return casoColumna;
    }

    public void setCasoColumna(NodoCaso casoColumna) {
        this.casoColumna = casoColumna;
    }
   
    public Simbolos getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(Simbolos simbolo) {
        this.simbolo = simbolo;
    }
    
    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public int getNoCaso() {
        return noCaso;
    }

    public void setNoCaso(int noCaso) {
        this.noCaso = noCaso;
    }
    
    
    
}
