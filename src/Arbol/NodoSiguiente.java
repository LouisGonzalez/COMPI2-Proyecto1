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
public class NodoSiguiente implements Serializable{
    
    private NodoArbol nodo;
    private ArrayList<NodoArbol> listSiguientes = new ArrayList<>();

    public NodoSiguiente(NodoArbol nodo) {
        this.nodo = nodo;
    }

    public NodoArbol getNodo() {
        return nodo;
    }

    public void setNodo(NodoArbol nodo) {
        this.nodo = nodo;
    }

    public ArrayList<NodoArbol> getListSiguientes() {
        return listSiguientes;
    }

    public void setListSiguientes(ArrayList<NodoArbol> listSiguientes) {
        this.listSiguientes = listSiguientes;
    }

  
    
    
    
}
