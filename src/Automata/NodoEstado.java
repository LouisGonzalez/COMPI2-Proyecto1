/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

import Arbol.NodoArbol;
import java.io.Serializable;
import java.util.ArrayList;
import pollitos.Conductores;

/**
 *
 * @author luisGonzalez
 */
public class NodoEstado implements Serializable{
    
    private int id;
    ArrayList<NodoArbol> listVinculos = new ArrayList<>();
    private boolean marcado = false;
    private boolean aceptacion = false;
    private String idExpresion;
    private ArrayList<Conductores> conductores = new ArrayList<>();

    public ArrayList<Conductores> getConductores() {
        return conductores;
    }

    public void setConductores(ArrayList<Conductores> conductores) {
        this.conductores = conductores;
    }

    public NodoEstado(int id) {
        this.id = id;
    }

    public String getIdExpresion() {
        return idExpresion;
    }

    public void setIdExpresion(String idExpresion) {
        this.idExpresion = idExpresion;
    }

    public boolean isAceptacion() {
        return aceptacion;
    }

    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }
    
    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<NodoArbol> getListVinculos() {
        return listVinculos;
    }

    public void setListVinculos(ArrayList<NodoArbol> listVinculos) {
        this.listVinculos = listVinculos;
    }
    
    
    
    
}
