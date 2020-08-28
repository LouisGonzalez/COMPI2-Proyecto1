/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollitos;

import Arbol.NodoArbol;
import Arbol.NodoSiguiente;
import Automata.NodoEstado;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author luisGonzalez
 */
public class MisExpresiones implements Serializable {

    private String identificador;
    private String caracteres;
    private NodoArbol nodoArbol;
    private ArrayList<NodoSiguiente> tablaSiguientes = new ArrayList<>();
    private ArrayList<NodoEstado> listEstados = new ArrayList<>();
    private int prioridad;

    public MisExpresiones(String identificador, NodoArbol nodoArbol, String caracteres) {
        this.identificador = identificador;
        this.caracteres = caracteres;
        this.nodoArbol = nodoArbol;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
   
    public ArrayList<NodoEstado> getListEstados() {
        return listEstados;
    }

    public void setListEstados(ArrayList<NodoEstado> listEstados) {
        this.listEstados = listEstados;
    }

    public ArrayList<NodoSiguiente> getTablaSiguientes() {
        return tablaSiguientes;
    }

    public void setTablaSiguientes(ArrayList<NodoSiguiente> tablaSiguientes) {
        this.tablaSiguientes = tablaSiguientes;
    }

    public NodoArbol getNodoArbol() {
        return nodoArbol;
    }

    public void setNodoArbol(NodoArbol nodoArbol) {
        this.nodoArbol = nodoArbol;
    }

    public String getCaracteres() {
        return caracteres;
    }

    public void setCaracteres(String caracteres) {
        this.caracteres = caracteres;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

  
}
