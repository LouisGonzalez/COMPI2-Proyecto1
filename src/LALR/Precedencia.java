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
 * @author luisGonzalez
 */
public class Precedencia {

    public Integer manejarPrecedencia(ArrayList<Pila> listPila, Token tokenActual, ArrayList<Simbolos> listSimbolos, NodoTabla[][] tabla, int fila, int columna) {
        Integer iterador = null;
        Token ultimoToken = devolverUltimoToken(listPila, listSimbolos);
        if (ultimoToken != null) {
            int presUltimo = verPrecedencia(listSimbolos, ultimoToken.getIdentificador());
            int presActual = verPrecedencia(listSimbolos, tokenActual.getIdentificador());
            if (presActual > presUltimo) {
                //se toma el camino shift
                boolean hayShift = false;
                for (int i = 0; i < tabla[fila][columna].getAcciones().size(); i++) {
                    if (tabla[fila][columna].getAcciones().get(i).getAccion().equals("shift")) {
                        iterador = i;
                        hayShift = true;
                        break;
                    }
                }
                if (!hayShift) {
                    for (int i = 0; i < tabla[fila][columna].getAcciones().size(); i++) {
                        if (tabla[fila][columna].getAcciones().get(i).getAccion().equals("reduce")) {
                            iterador = i;
                            break;
                        }
                    }
                }
            } else {
                boolean hayReduce = false;
                for (int i = 0; i < tabla[fila][columna].getAcciones().size(); i++) {
                    if(tabla[fila][columna].getAcciones().get(i).getAccion().equals("reduce")){
                        iterador = i;
                        hayReduce = true;
                        break;
                    }
                }
                if(!hayReduce){
                    for (int i = 0; i < tabla[fila][columna].getAcciones().size(); i++) {
                        if(tabla[fila][columna].getAcciones().get(i).getAccion().equals("shift")){
                            iterador = i;
                            break;
                        }
                    }
                }
            }
        } else {
            //se toma el camino del reduce
            boolean hayReduce = false;
            for (int i = 0; i < tabla[fila][columna].getAcciones().size(); i++) {
                if(tabla[fila][columna].getAcciones().get(i).getAccion().equals("reduce")){
                    iterador = i;
                    hayReduce = true;
                    break;
                } 
            }
            if(!hayReduce){
                for (int i = 0; i < tabla[fila][columna].getAcciones().size(); i++) {
                    if(tabla[fila][columna].getAcciones().get(i).getAccion().equals("shift")){
                        iterador = i;
                        break;
                    }
                }
            }
        }
        return iterador;
    }

    private Token devolverUltimoToken(ArrayList<Pila> listPila, ArrayList<Simbolos> listSimbolos) {
        Token aDevolver = null;
        for (int i = listPila.size() - 1; i >= 0; i--) {
            if (listPila.get(i).getToken().isEsTerminal()) {
                aDevolver = listPila.get(i).getToken();
                break;
            }
        }
        return aDevolver;
    }

    private Integer verPrecedencia(ArrayList<Simbolos> listSimbolos, String id) {
        Integer precedencia = null;
        for (int i = 0; i < listSimbolos.size(); i++) {
            if (listSimbolos.get(i).getIdentificador().equals(id)) {
                precedencia = listSimbolos.get(i).getPrecedencia();
                break;
            }
        }
        return precedencia;
    }

}
