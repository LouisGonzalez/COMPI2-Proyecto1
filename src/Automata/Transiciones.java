/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

import Arbol.NodoArbol;
import Arbol.NodoSiguiente;
import interfaz.PanelHojas;
import java.io.Serializable;
import java.util.ArrayList;
import pollitos.Token;

/**
 *
 * @author luisGonzalez
 */
public class Transiciones implements Serializable {

    public Integer iterador2 = null;
    
    public void transicionCadena(ArrayList<Token> listTokens, NodoEstado estadoInicial, ArrayList<NodoEstado> listEstados, String texto, ArrayList<NodoSiguiente> tablaSiguientes) {
        String cadena = "";
        int textoTotal = texto.length();
        int iterador = 0;
        NodoEstado estadoActual = estadoInicial;
        NodoEstado estadoNuevo = null;
        do {
            if (iterador < textoTotal) {
                char aux = texto.charAt(iterador);
                if (aux == ' ') {
                    estadoNuevo = null;
                } else {
                    estadoNuevo = transicion(estadoActual, aux, listEstados, tablaSiguientes);
                    if (estadoNuevo != null) {
                        estadoActual = estadoNuevo;
                        cadena += aux;
                        iterador++;
                    }
                }
            } else {
                break;
            }
        } while (estadoActual == estadoNuevo);
        if (estadoActual.isAceptacion()) {
            verificarPrioridad(estadoActual, cadena, listTokens);
            if (iterador < textoTotal) {
                String nuevoTexto = "";
                for (int i = iterador; i < texto.length(); i++) {
                    if (i == iterador) {
                        if (texto.charAt(iterador) == ' ') {
                        } else {
                            nuevoTexto += texto.charAt(i);
                        }
                    } else {
                        nuevoTexto += texto.charAt(i);
                    }
                }
                transicionCadena(listTokens, estadoInicial, listEstados, nuevoTexto, tablaSiguientes);
            }
        } else {
            if (iterador < textoTotal) {
                String nuevoTexto = "";
                for (int i = iterador+1; i < texto.length(); i++) {
                    if (i == iterador+1) {
                        if (texto.charAt(iterador+1) == ' ') {
                        } else {
                            nuevoTexto += texto.charAt(i);
                        }
                    } else {
                        nuevoTexto += texto.charAt(i);
                    }
                }
                String textoError = "";
                for (int i = 0; i <= iterador; i++) {
                    textoError += texto.charAt(i);
                }
                Token nuevo = new Token(null, "error", textoError, null);
                listTokens.add(nuevo);
                
                //AQUI INGRESO UN TOKEN DE TIPO ERROR PARA EL ANALIZADOR SINTACTICO Y QUE AHI LO MUESTRE :DDD
                transicionCadena(listTokens, estadoInicial, listEstados, nuevoTexto, tablaSiguientes);
            }
        }
    }

    public NodoEstado transicion(NodoEstado estadoActual, char c, ArrayList<NodoEstado> listEstados, ArrayList<NodoSiguiente> tablaSiguientes) {
        NodoEstado aEnviar = null;
        char actual = c;
        for (int i = 0; i < estadoActual.getListVinculos().size(); i++) {
                if (estadoActual.getListVinculos().get(i).getTipo().equals("rango")) {
                int codigo = (int) actual;
                if (codigo >= estadoActual.getListVinculos().get(i).getRangoChar1() && codigo <= estadoActual.getListVinculos().get(i).getRangoChar2()) {
                    //aqui buscar a donde ir
                    aEnviar = buscarDireccion(listEstados, tablaSiguientes, estadoActual.getListVinculos().get(i).getNumero());
                    break;
                }
            } else if (estadoActual.getListVinculos().get(i).getTipo().equals("caracteres")) {
                if (actual == estadoActual.getListVinculos().get(i).getCaracteres().charAt(0)) {
                    aEnviar = buscarDireccion(listEstados, tablaSiguientes, estadoActual.getListVinculos().get(i).getNumero());
                    break;
                }
            }
        }
        return aEnviar;
    }

    public NodoEstado buscarDireccion(ArrayList<NodoEstado> listEstados, ArrayList<NodoSiguiente> tablaSiguientes, int id) {
        ArrayList<NodoArbol> aux = new ArrayList<>();
        NodoEstado aEnviar = null;
        aux = buscarSiguiente(tablaSiguientes, id);
        for (int i = 0; i < listEstados.size(); i++) {
            if (listEstados.get(i).getListVinculos().containsAll(aux) && aux.containsAll(listEstados.get(i).getListVinculos())) {
                aEnviar = listEstados.get(i);
                break;
            }
        }
        return aEnviar;
    }

    public ArrayList<NodoArbol> buscarSiguiente(ArrayList<NodoSiguiente> tablaSiguiente, int id) {
        ArrayList<NodoArbol> aux = new ArrayList<>();
        for (int i = 0; i < tablaSiguiente.size(); i++) {
            if (tablaSiguiente.get(i).getNodo().getNumero() == id) {
                for (int j = 0; j < tablaSiguiente.get(i).getListSiguientes().size(); j++) {
                    aux.add(tablaSiguiente.get(i).getListSiguientes().get(j));
                }
            }
        }
        return aux;
    }

    public void verificarPrioridad(NodoEstado aceptacion, String cadena, ArrayList<Token> listTokens) {
        if (aceptacion.getConductores().size() > 1) {
            int aux = aceptacion.getConductores().get(0).getPrioridad();
            String token = aceptacion.getConductores().get(0).getId();
            boolean encontrado = false;
            for (int i = 0; i < aceptacion.getConductores().size(); i++) {
                if (aceptacion.getConductores().get(i).getTipo().equals("caracteres")) {
                    if (aceptacion.getConductores().get(i).getPalabraCompleta().equals(cadena)) {
                        aux = aceptacion.getConductores().get(i).getPrioridad();
                        token = aceptacion.getConductores().get(i).getId();
                        encontrado = true;
                        break;
                    }
                }
            }
            if (!encontrado) {
                for (int i = 1; i < aceptacion.getConductores().size(); i++) {

                    if (aceptacion.getConductores().get(i - 1).getPrioridad() < aceptacion.getConductores().get(i).getPrioridad()) {
                        aux = aceptacion.getConductores().get(i).getPrioridad();
                        token = aceptacion.getConductores().get(i).getId();
                    }
                }
            }
            Token miToken = new Token(token, null, cadena, null);

            //    System.out.println("el token devuelto es un token: " + miToken.getIdentificador());
            listTokens.add(miToken);
        } else {
            Token miToken = new Token(aceptacion.getConductores().get(0).getId(), null, cadena, null);
            listTokens.add(miToken);
            //   System.out.println("el token devuelto es un token: " + miToken.getIdentificador());
        }
    }

}
