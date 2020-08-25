/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.util.ArrayList;
import pollitos.NodoSimplificado;
import pollitos.NodoTabla;
import pollitos.Simbolos;

/**
 *
 * @author luisGonzalez
 */
public class OptimizacionLALR {

    private GeneracionTabla gnTabla = new GeneracionTabla();

    public void buscarCasosIguales(ArrayList<NodoCaso> listCasos, NodoTabla[][] tabla, ArrayList<Simbolos> listSimbolos) {
        ArrayList<NodoSimplificado> simplificados = new ArrayList<>();
        for (int i = 0; i < listCasos.size(); i++) {
            NodoSimplificado posible = new NodoSimplificado();
            posible.getListUniones().add(listCasos.get(i).getIdCaso());
            Integer ubPunto1 = gnTabla.verUbicacionPunto(listCasos.get(i).getListEstados().get(0).getMisExpresiones());
            for (int j = 0; j < listCasos.size(); j++) {
                if (i != j) {
                    Integer ubPunto2 = gnTabla.verUbicacionPunto(listCasos.get(j).getListEstados().get(0).getMisExpresiones());
                    if (listCasos.get(i).getListEstados().get(0).getNoEstado() == listCasos.get(j).getListEstados().get(0).getNoEstado()) {
                        if (ubPunto1 != null && ubPunto2 != null) {
                            if (ubPunto1 == ubPunto2) {
                                posible.getListUniones().add(listCasos.get(j).getIdCaso());
                            }
                        } else {
                            if (listCasos.get(j).getListEstados().get(0).getMisExpresiones().get(listCasos.get(j).getListEstados().get(0).getMisExpresiones().size() - 1).getPuntoFinal() && listCasos.get(i).getListEstados().get(0).getMisExpresiones().get(listCasos.get(i).getListEstados().get(0).getMisExpresiones().size() - 1).getPuntoFinal()) {
                                posible.getListUniones().add(listCasos.get(j).getIdCaso());
                            }
                        }
                    }
                }
            }
            simplificados.add(posible);
        }
        eliminacionNodos1(simplificados);
        verificarNodosTabla(listCasos, tabla, simplificados, listSimbolos);
        /*for (int i = 0; i < simplificados.size(); i++) {
            String posibles = "";
            for (int j = 0; j < simplificados.get(i).getListUniones().size(); j++) {
                posibles += simplificados.get(i).getListUniones().get(j)+" ";
            }
            System.out.println("Posibles: "+posibles);
        }*/
    }

    //elimina cada nodo que tenga un estado dentro de su arraylist
    public void eliminacionNodos1(ArrayList<NodoSimplificado> simplificados) {
        for (int i = 0; i < simplificados.size(); i++) {
            if (simplificados.get(i).getListUniones().size() == 1) {
                simplificados.remove(i);
                i--;
            }
        }
        int mitad = simplificados.size() / 2;
        for (int i = mitad; i < simplificados.size(); i++) {
            simplificados.remove(i);
            i--;
        }
    }

    //verifica si dentro de la tabla LR(1) no habria conflicto al simplificar dos o mas estados.
    public void verificarNodosTabla(ArrayList<NodoCaso> listCasos, NodoTabla[][] tabla, ArrayList<NodoSimplificado> simplificados, ArrayList<Simbolos> listSimbolos) {
        for (int i = 0; i < simplificados.size(); i++) {
            if (simplificados.get(i).getListUniones().size() > 1) {
                for (int j = 1; j < simplificados.get(i).getListUniones().size(); j++) {
                    if (tabla[simplificados.get(i).getListUniones().get(0)][0].isFilaActiva() && tabla[simplificados.get(i).getListUniones().get(j)][0].isFilaActiva()) {
                        if (!comparacionFilas(tabla, simplificados.get(i).getListUniones().get(0), simplificados.get(i).getListUniones().get(j), listSimbolos, listCasos, simplificados)) {
                            break;
                        }
                    }
                }
            } else {
                if (tabla[simplificados.get(i).getListUniones().get(0)][0].isFilaActiva() && tabla[simplificados.get(i).getListUniones().get(1)][0].isFilaActiva()) {
                    comparacionFilas(tabla, simplificados.get(i).getListUniones().get(0), simplificados.get(i).getListUniones().get(1), listSimbolos, listCasos, simplificados);
                }
            }

        }
    }

    public boolean comparacionFilas(NodoTabla[][] tabla, int idFila1, int idFila2, ArrayList<Simbolos> listSimbolos, ArrayList<NodoCaso> listCasos, ArrayList<NodoSimplificado> simplificados) {
        boolean todoCorrecto = true;
        for (int i = 1; i < listSimbolos.size() + 2; i++) {
            if (tabla[idFila1][i] != null && tabla[idFila2][i] != null) {
                if (tabla[idFila1][i].getAccion().equals(tabla[idFila2][i].getAccion())) {
                    if (tabla[idFila1][i].getNoCaso() != tabla[idFila2][i].getNoCaso()) {
                        if (verificarPosibleVinculo(simplificados, idFila1, idFila2)) {
                            if (!comparacionFilas(tabla, tabla[idFila1][i].getNoCaso(), tabla[idFila1][i].getNoCaso(), listSimbolos, listCasos, simplificados)) {
                                todoCorrecto = false;
                                break;
                            }
                        } else {
                            todoCorrecto = false;
                            break;
                        }
                    }
                } else {
                    todoCorrecto = false;
                    break;
                }
            }

        }
        if (todoCorrecto) {
            if (idFila1 > idFila2) {
                tabla[idFila1][0].setFilaActiva(false);
                cambiarCasosTabla(tabla, idFila2, idFila1, listCasos, listSimbolos);
            } else {
                tabla[idFila2][0].setFilaActiva(false);
                cambiarCasosTabla(tabla, idFila1, idFila2, listCasos, listSimbolos);
            }
        }
        return todoCorrecto;
    }

    //cambia el noCaso al simplificado dentro de cada coincidencia en la tabla del LALR
    public NodoTabla[][] cambiarCasosTabla(NodoTabla[][] tabla, int id, int idACambiar, ArrayList<NodoCaso> listCasos, ArrayList<Simbolos> listSimbolos) {
        for (int i = 1; i < listCasos.size() + 2; i++) {
            for (int j = 1; j < listSimbolos.size() + 2; j++) {
                if (tabla[i][j] != null) {
                    if (!tabla[i][j].getAccion().equals("reduce")) {
                        if (tabla[i][j].getNoCaso() == idACambiar) {
                            tabla[i][j].setNoCaso(id);
                        }
                    }
                }
            }
        }
        return tabla;
    }

    //verifica si dos filas estan dentro del arraylist simpllificacion, si es asi proceder
    public boolean verificarPosibleVinculo(ArrayList<NodoSimplificado> simplificados, int no1, int no2) {
        boolean encontrados = false;
        for (int i = 0; i < simplificados.size(); i++) {
            boolean encontrado1 = false;
            boolean encontrado2 = false;
            for (int j = 0; j < simplificados.get(i).getListUniones().size(); j++) {
                if (no1 == simplificados.get(i).getListUniones().get(j)) {
                    encontrado1 = true;
                }
                if (no2 == simplificados.get(i).getListUniones().get(j)) {
                    encontrado2 = true;
                }
            }
            if (encontrado1 && encontrado2) {
                encontrados = true;
                break;
            }
        }
        return encontrados;
    }

}
