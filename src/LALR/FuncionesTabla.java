/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.util.ArrayList;
import pollitos.NodoTabla;
import pollitos.Pila;
import pollitos.Simbolos;
import pollitos.Token;

/**
 *
 * @author luisGonzalezx
 */
public class FuncionesTabla {

    private Integer noFila = null;

    public void transiciones(ArrayList<Token> listTokens, NodoTabla[][] tabla, ArrayList<Simbolos> listSimbolos, ArrayList<Estados> listEstados) {
        ArrayList<Pila> miPila = new ArrayList<>();
        boolean cadenaAceptada = true;
        miPila.add(new Pila(null, 1));
        mostrarPila(miPila);
        listTokens.add(new Token("$", "aceptacion", "$"));
        noFila = 1;
        for (int i = 0; i < listTokens.size(); i++) {
            if (listTokens.get(i).getTipo() != null) {
                if (listTokens.get(i).getTipo().equals("error")) {
                    cadenaAceptada = false;
                }
            }
            Integer columna = buscarColumna(tabla, listTokens.get(i).getIdentificador(), listSimbolos);
            if (columna != null) {
                if (tabla[noFila][columna] != null) {
                    if (tabla[noFila][columna].getAccion().equals("reduce")) {
                        //                 System.out.println("REDUCE ");
                        if (!reduce(listTokens.get(i), miPila, listEstados, tabla[noFila][columna].getNoCaso(), tabla, listSimbolos)) {
                            cadenaAceptada = false;
                            break;
                        }
                    } else if (tabla[noFila][columna].getAccion().equals("goTo")) {
                    } else if (tabla[noFila][columna].getAccion().equals("shift")) {
                        //                  System.out.println("SHIFT");
                        if (!shift(listTokens.get(i), miPila, tabla[noFila][columna].getNoCaso())) {
                            cadenaAceptada = false;
                            break;
                        }
                    }
                } else {

                    cadenaAceptada = false;
                    System.out.println("AQUI CAMBIO " + i + " xd");
                    break;
                }
            }
            if (i == listTokens.size() - 1) {
                if (!tabla[noFila][columna].getAccion().equals("aceptacion")) {
                    cadenaAceptada = false;
                }
            }
        }
        if (!cadenaAceptada) {
            System.out.println("Esta entrada no es valida para el lenguaje seleccionado.");
        } else {
            System.out.println("Esta cadena si es valida para el lenguaje seleccionado");
        }
    }

    public boolean shift(Token actual, ArrayList<Pila> miPila, int casoTransicion) {
        Pila nuevo = new Pila(actual, casoTransicion);
        miPila.add(nuevo);
        noFila = casoTransicion;
        mostrarPila(miPila);
        System.out.println("--------------------------------------------------------------------------");
        return true;
    }

    public boolean reduce(Token actual, ArrayList<Pila> miPila, ArrayList<Estados> listEstados, int casoTransicion, NodoTabla[][] tabla, ArrayList<Simbolos> listSimbolos) {
        boolean todoCorrecto = true;
        Estados analizado = buscarEstado(listEstados, casoTransicion);
        ArrayList<String> expresiones = new ArrayList<>();
        for (int i = 0; i < analizado.getMisExpresiones().size(); i++) {
            expresiones.add(analizado.getMisExpresiones().get(i).getIdentificador());
        }
        if (desapilarReduce(miPila, listEstados, expresiones)) {
            //        System.out.println(miPila.size() +"   "+expresiones.size()+"                 FASFDSFSDFS");
            for (int i = (miPila.size() - expresiones.size()); i < miPila.size(); i++) {
                miPila.remove(i);
                i--;
            }
        } else {
            todoCorrecto = false;
        }
        if (todoCorrecto) {
            mostrarPila(miPila);
            System.out.println("--------------------------------------------------------------------------");
            Token nuevo = new Token(analizado.getIdentificador(), null, null);
            nuevo.setEsTerminal(false);
            Pila nuevo2 = new Pila(nuevo, null);
            Integer numPila = null;
            Integer columna = buscarColumna(tabla, analizado.getIdentificador(), listSimbolos);
            if (tabla[miPila.get(miPila.size() - 1).getNoCaso()][columna] != null) {
                //System.out.println("fila: "+miPila.get(miPila.size() - 1).getNoCaso()+"  columna: "+tabla[0][columna].getSimbolo().getIdentificador());
                if (tabla[miPila.get(miPila.size() - 1).getNoCaso()][columna].getAccion().equals("goTo")) {
                    numPila = tabla[miPila.get(miPila.size() - 1).getNoCaso()][columna].getNoCaso();
                    noFila = numPila;
                    nuevo2.setNoCaso(numPila);
                    miPila.add(nuevo2);
                    mostrarPila(miPila);
                    System.out.println("--------------------------------------------------------------------------");
                    if (!reingresoToken(actual, miPila, tabla, listSimbolos, listEstados)) {
                        todoCorrecto = false;
                    }
                }

            } else {
                todoCorrecto = false;
            }
        }
        return todoCorrecto;

    }

    //esto cuando se acaba de meter un token gracias a un reduce
    public boolean reingresoToken(Token actual, ArrayList<Pila> miPila, NodoTabla[][] tabla, ArrayList<Simbolos> listSimbolos, ArrayList<Estados> listEstados) {
        boolean todoCorrecto = true;
        int ultimoNodo = miPila.get(miPila.size() - 1).getNoCaso();
        int columna = buscarColumna(tabla, actual.getIdentificador(), listSimbolos);
        if (tabla[ultimoNodo][columna] != null) {
            if (tabla[ultimoNodo][columna].getAccion().equals("reduce")) {
                //   System.out.println("REDUCE2");
                //        System.out.println("fila: "+ultimoNodo+" columnax2: "+columna);
                if (!reduce(actual, miPila, listEstados, tabla[ultimoNodo][columna].getNoCaso(), tabla, listSimbolos)) {
                    todoCorrecto = false;
                }
            } else if (tabla[ultimoNodo][columna].getAccion().equals("goTo")) {

            } else if (tabla[ultimoNodo][columna].getAccion().equals("shift")) {
                // System.out.println("SHIFT2");
                if (!shift(actual, miPila, tabla[ultimoNodo][columna].getNoCaso())) {
                    todoCorrecto = false;
                }
            }
        } else {
            todoCorrecto = false;
        }
        return todoCorrecto;

    }

    public boolean desapilarReduce(ArrayList<Pila> miPila, ArrayList<Estados> listEstados, ArrayList<String> expresiones) {
        boolean sePuede = true;
        int cont = 1;
        for (int i = miPila.size() - 1; i >= (miPila.size() - expresiones.size()); i--) {
            if (!miPila.get(i).getToken().getIdentificador().equals(expresiones.get(expresiones.size() - cont))) {
                sePuede = false;
                break;
            }
            cont++;
        }
        return sePuede;
    }

    //busca la columna dentro de la tabla con la coincidencia
    public Integer buscarColumna(NodoTabla[][] tabla, String id, ArrayList<Simbolos> listSimbolos) {
        Integer noColumna = null;
        for (int i = 1; i < listSimbolos.size() + 2; i++) {
            if (tabla[0][i] != null) {
                if (tabla[0][i].getSimbolo().getIdentificador().equals(id)) {
                    noColumna = i;
                }
            }
        }
        return noColumna;
    }

    public Estados buscarEstado(ArrayList<Estados> listEstados, int noEstado) {
        Estados aDevolver = null;
        for (int i = 0; i < listEstados.size(); i++) {
            if (listEstados.get(i).getNoEstado() == noEstado) {
                aDevolver = listEstados.get(i);
                break;
            }
        }
        return aDevolver;
    }

    public void mostrarPila(ArrayList<Pila> miPila) {
        for (int i = 0; i < miPila.size(); i++) {
            if (miPila.get(i).getToken() != null) {
                System.out.println("    " + miPila.get(i).getNoCaso() + "    " + miPila.get(i).getToken().getIdentificador());
            } else {
                System.out.println("    " + miPila.get(i).getNoCaso());
            }
        }
    }

}
