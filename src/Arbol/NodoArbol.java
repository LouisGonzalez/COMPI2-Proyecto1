/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author luisGonzalez
 */
public class NodoArbol implements Serializable {
    
    private NodoArbol nodo1;
    private NodoArbol nodo2;
    private String tipo;
    private Integer rangoChar1;
    private Integer rangoChar2;
    private String caracteres;
    private String palabraCompleta;
    private String idExpresion;
    private int prioridad;
    
    /*CARACTERISTICAS*/
    private ArrayList<NodoArbol> primeros = new ArrayList<>();
    private ArrayList<NodoArbol> siguientes = new ArrayList<>();
    private Integer numero;
    private Boolean nulabilidad;

    public NodoArbol(String tipo, Integer rangoChar1, Integer rangoChar2, String caracteres){
        this.tipo = tipo;
        this.rangoChar1 = rangoChar1;
        this.rangoChar2 = rangoChar2;
        this.caracteres = caracteres;
    }

    public String getPalabraCompleta() {
        return palabraCompleta;
    }

    public void setPalabraCompleta(String palabraCompleta) {
        this.palabraCompleta = palabraCompleta;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getIdExpresion() {
        return idExpresion;
    }

    public void setIdExpresion(String idExpresion) {
        this.idExpresion = idExpresion;
    }
    
    public ArrayList<NodoArbol> getPrimeros() {
        return primeros;
    }

    public void setPrimeros(ArrayList<NodoArbol> primeros) {
        this.primeros = primeros;
    }

    public ArrayList<NodoArbol> getSiguientes() {
        return siguientes;
    }

    public void setSiguientes(ArrayList<NodoArbol> siguientes) {
        this.siguientes = siguientes;
    }

    

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Boolean isNulabilidad() {
        return nulabilidad;
    }

    public void setNulabilidad(Boolean nulabilidad) {
        this.nulabilidad = nulabilidad;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getRangoChar1() {
        return rangoChar1;
    }

    public void setRangoChar1(Integer rangoChar1) {
        this.rangoChar1 = rangoChar1;
    }

    public Integer getRangoChar2() {
        return rangoChar2;
    }

    public void setRangoChar2(Integer rangoChar2) {
        this.rangoChar2 = rangoChar2;
    }

    public String getCaracteres() {
        return caracteres;
    }

    public void setCaracteres(String caracteres) {
        this.caracteres = caracteres;
    }

    public NodoArbol getNodo1() {
        return nodo1;
    }

    public void setNodo1(NodoArbol nodo1) {
        this.nodo1 = nodo1;
    }

    public NodoArbol getNodo2() {
        return nodo2;
    }

    public void setNodo2(NodoArbol nodo2) {
        this.nodo2 = nodo2;
    }
    
    
    
}
