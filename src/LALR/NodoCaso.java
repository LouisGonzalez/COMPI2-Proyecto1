/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author luisGonzalez
 */
public class NodoCaso implements Serializable {
    
    private int idCaso;
    private ArrayList<Estados> listEstados;
    private ArrayList<Vinculos> listVinculos;
    private boolean derivado = false;

    public NodoCaso(int idCaso, ArrayList<Estados> listEstados, ArrayList<Vinculos> listVinculos) {
        this.idCaso = idCaso;
        this.listEstados = listEstados;
        this.listVinculos = listVinculos;
    }

    public boolean isDerivado() {
        return derivado;
    }

    public void setDerivado(boolean derivado) {
        this.derivado = derivado;
    }
    
    public int getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(int idCaso) {
        this.idCaso = idCaso;
    }

    public ArrayList<Estados> getListEstados() {
        return listEstados;
    }

    public void setListEstados(ArrayList<Estados> listEstados) {
        this.listEstados = listEstados;
    }

    public ArrayList<Vinculos> getListVinculos() {
        return listVinculos;
    }

    public void setListVinculos(ArrayList<Vinculos> listVinculos) {
        this.listVinculos = listVinculos;
    }
        
    
    
}
