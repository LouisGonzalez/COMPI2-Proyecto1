/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollitos;

import Arbol.NodoArbol;
import LALR.Estados;
import LALR.NodoCaso;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author luisGonzalez
 */
public class Lenguajes implements Serializable{
    
    private ArrayList<Simbolos> listSimbolos = new ArrayList<>();
    private NodoTabla[][] miTabla = null;
    private ArrayList<MisExpresiones> listExpresiones = new ArrayList<>();
    private ArrayList<NodoCaso> listCasos = new ArrayList<>();
    private ArrayList<Estados> listEstados = new ArrayList<>();
    private NodoArbol primero = null;
    private MisExpresiones unico = null;
    private DatosLenguaje datos = new DatosLenguaje();
    private String miCodigo;

    public String getMiCodigo() {
        return miCodigo;
    }

    public void setMiCodigo(String miCodigo) {
        this.miCodigo = miCodigo;
    }

    public DatosLenguaje getDatos() {
        return datos;
    }

    public void setDatos(DatosLenguaje datos) {
        this.datos = datos;
    }

    public MisExpresiones getUnico() {
        return unico;
    }

    public void setUnico(MisExpresiones unico) {
        this.unico = unico;
    }

    public ArrayList<Simbolos> getListSimbolos() {
        return listSimbolos;
    }

    public void setListSimbolos(ArrayList<Simbolos> listSimbolos) {
        this.listSimbolos = listSimbolos;
    }

    public NodoTabla[][] getMiTabla() {
        return miTabla;
    }

    public void setMiTabla(NodoTabla[][] miTabla) {
        this.miTabla = miTabla;
    }

    public ArrayList<MisExpresiones> getListExpresiones() {
        return listExpresiones;
    }

    public void setListExpresiones(ArrayList<MisExpresiones> listExpresiones) {
        this.listExpresiones = listExpresiones;
    }

    public ArrayList<NodoCaso> getListCasos() {
        return listCasos;
    }

    public void setListCasos(ArrayList<NodoCaso> listCasos) {
        this.listCasos = listCasos;
    }

    public ArrayList<Estados> getListEstados() {
        return listEstados;
    }

    public void setListEstados(ArrayList<Estados> listEstados) {
        this.listEstados = listEstados;
    }

    public NodoArbol getPrimero() {
        return primero;
    }

    public void setPrimero(NodoArbol primero) {
        this.primero = primero;
    }
    
        
    
}
