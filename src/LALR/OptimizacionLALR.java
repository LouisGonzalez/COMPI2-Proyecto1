/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.util.ArrayList;
import pollitos.NodoAccion;
import pollitos.NodoSimplificado;
import pollitos.NodoTabla;
import pollitos.Parejas;
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
        
    }

    //elimina cada nodo que tenga un estado dentro de su arraylist
    public void eliminacionNodos1(ArrayList<NodoSimplificado> simplificados) {
        for (int i = 0; i < simplificados.size(); i++) {
            if (simplificados.get(i).getListUniones().size() == 1) {
                simplificados.remove(i);
                i--;
            }
        }

        for (int i = 0; i < simplificados.size(); i++) {

            for (int j = 0; j < simplificados.size(); j++) {
                if (i != j) {
                    if (simplificados.get(i).getListUniones().containsAll(simplificados.get(j).getListUniones()) && simplificados.get(j).getListUniones().containsAll(simplificados.get(i).getListUniones())) {
                        simplificados.remove(j);
                        j--;
                    }
                }
            }

        }

    }

    //verifica si dentro de la tabla LR(1) no habria conflicto al simplificar dos o mas estados.
    public void verificarNodosTabla(ArrayList<NodoCaso> listCasos, NodoTabla[][] tabla, ArrayList<NodoSimplificado> simplificados, ArrayList<Simbolos> listSimbolos) {
        for (int i = 0; i < simplificados.size(); i++) {
            if (simplificados.get(i).getListUniones().size() > 2) {
                for (int j = 1; j < simplificados.get(i).getListUniones().size(); j++) {
                    ArrayList<Parejas> listParejas = new ArrayList<>();
                    if (tabla[simplificados.get(i).getListUniones().get(0)][0].isFilaActiva() && tabla[simplificados.get(i).getListUniones().get(j)][0].isFilaActiva()) {
                        Parejas pareja = new Parejas(simplificados.get(i).getListUniones().get(0), simplificados.get(i).getListUniones().get(j));
                        listParejas.add(pareja);
                        if (!comparacionFilas(i, tabla, simplificados.get(i).getListUniones().get(0), simplificados.get(i).getListUniones().get(j), listSimbolos, listCasos, simplificados, true, listParejas, true)) {
                            break;
                        }
                    }
                }
            } else {
                ArrayList<Parejas> listParejas = new ArrayList<>();
                if (tabla[simplificados.get(i).getListUniones().get(0)][0].isFilaActiva() && tabla[simplificados.get(i).getListUniones().get(1)][0].isFilaActiva()) {
                    Parejas pareja = new Parejas(simplificados.get(i).getListUniones().get(0), simplificados.get(i).getListUniones().get(1));
                    listParejas.add(pareja);
                    comparacionFilas(i, tabla, simplificados.get(i).getListUniones().get(0), simplificados.get(i).getListUniones().get(1), listSimbolos, listCasos, simplificados, true, listParejas, true);
                }
            }
        }
    }

    public boolean comparacionFilas(int nodoActual, NodoTabla[][] tabla, int idFila1, int idFila2, ArrayList<Simbolos> listSimbolos, ArrayList<NodoCaso> listCasos, ArrayList<NodoSimplificado> simplificados, boolean primero, ArrayList<Parejas> listParejas, boolean padre) {
        boolean todoCorrecto = true;
        for (int i = 1; i < listSimbolos.size() + 2; i++) {
            if (tabla[idFila1][i] != null && tabla[idFila2][i] != null) {
                if (tabla[idFila1][0].isFilaActiva() && tabla[idFila2][0].isFilaActiva()) {
                    if (tabla[idFila1][i].getAcciones().size() == 1 && tabla[idFila2][i].getAcciones().size() == 1) {
                        if (tabla[idFila1][i].getAcciones().get(0).getAccion().equals(tabla[idFila2][i].getAcciones().get(0).getAccion())) {
                            if (tabla[idFila1][i].getAcciones().get(0).getNoCaso() != tabla[idFila2][i].getAcciones().get(0).getNoCaso()) {
                               if (!tabla[idFila1][i].getAcciones().get(0).getAccion().equals("reduce")) {
                                    if ((idFila1 == tabla[idFila1][i].getAcciones().get(0).getNoCaso() && idFila2 == tabla[idFila2][i].getAcciones().get(0).getNoCaso()) || (idFila1 == tabla[idFila2][i].getAcciones().get(0).getNoCaso() && idFila2 == tabla[idFila1][i].getAcciones().get(0).getNoCaso())) {
                                    } else {
                                        if (verificarPosibleVinculo(nodoActual, simplificados, tabla[idFila1][i].getAcciones().get(0).getNoCaso(), tabla[idFila2][i].getAcciones().get(0).getNoCaso(), primero)) {
                                            if (!verificarPasoDoble(tabla[idFila1][i].getAcciones().get(0).getNoCaso(), tabla[idFila2][i].getAcciones().get(0).getNoCaso(), listParejas)) {
                                                Parejas pareja = new Parejas(tabla[idFila1][i].getAcciones().get(0).getNoCaso(), tabla[idFila2][i].getAcciones().get(0).getNoCaso());
                                                listParejas.add(pareja);
                                                if (!comparacionFilas(nodoActual, tabla, tabla[idFila1][i].getAcciones().get(0).getNoCaso(), tabla[idFila2][i].getAcciones().get(0).getNoCaso(), listSimbolos, listCasos, simplificados, false, listParejas, false)) {
                                                    todoCorrecto = false;
                                                    break;
                                                }
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
                        } else {
                            todoCorrecto = false;
                            break;
                        }
                    } else {
                        todoCorrecto = false;
                        break;
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
                    rellenoCasillas(tabla, idFila2, idFila1, listSimbolos);
                } else {
                    tabla[idFila2][0].setFilaActiva(false);
                    cambiarCasosTabla(tabla, idFila1, idFila2, listCasos, listSimbolos);
                    rellenoCasillas(tabla, idFila1, idFila2, listSimbolos);
                }
        } else {

        }
        return todoCorrecto;
    }

    //cambia el noCaso al simplificado dentro de cada coincidencia en la tabla del LALR
    public NodoTabla[][] cambiarCasosTabla(NodoTabla[][] tabla, int id, int idACambiar, ArrayList<NodoCaso> listCasos, ArrayList<Simbolos> listSimbolos) {
        for (int i = 1; i < listCasos.size() + 2; i++) {
            for (int j = 1; j < listSimbolos.size() + 2; j++) {
                if (tabla[i][j] != null) {

                    for (int k = 0; k < tabla[i][j].getAcciones().size(); k++) {
                        if (!tabla[i][j].getAcciones().get(k).getAccion().equals("reduce")) {
                            if (tabla[i][j].getAcciones().get(k).getNoCaso() != null) {
                                if (tabla[i][j].getAcciones().get(k).getNoCaso() == idACambiar) {
                                    tabla[i][j].getAcciones().get(k).setNoCaso(id);
                                }
                            }
                        }
                    }

                }
            }
        }
        return tabla;
    }

    //verifica si dos filas estan dentro del arraylist simpllificacion, si es asi proceder
    public boolean verificarPosibleVinculo(int nodoActual, ArrayList<NodoSimplificado> simplificados, int no1, int no2, boolean primero) {
        boolean encontrados = false;
        for (int i = 0; i < simplificados.size(); i++) {
            boolean encontrado1 = false;
            boolean encontrado2 = false;
            if (primero) {
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
            } else {
                if (nodoActual != i) {
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
            }
        }
        return encontrados;
    }

    //rellena las casillas en blanco que la otra fila si tenga ocupadas
    public void rellenoCasillas(NodoTabla[][] tabla, int nodo1, int nodo2, ArrayList<Simbolos> listSimbolos) {
        for (int i = 1; i < listSimbolos.size() + 2; i++) {
            if (tabla[nodo1][i] == null && tabla[nodo2][i] != null) {
                NodoTabla nuevo = new NodoTabla();
                for (int j = 0; j < tabla[nodo2][i].getAcciones().size(); j++) {
                    nuevo.getAcciones().add(new NodoAccion(tabla[nodo2][i].getAcciones().get(j).getAccion(), tabla[nodo2][i].getAcciones().get(j).getNoCaso()));
                }
                tabla[nodo1][i] = nuevo;
            }
        }
    }

    //verifica si ya se paso por un camino
    public boolean verificarPasoDoble(int idFila1, int idFila2, ArrayList<Parejas> listParejas) {
        boolean yaPaso = false;
        for (int i = 0; i < listParejas.size(); i++) {
            if (listParejas.get(i).getPareja1() == idFila1 && listParejas.get(i).getPareja2() == idFila2) {
                yaPaso = true;
                break;
            } else if (listParejas.get(i).getPareja1() == idFila2 && listParejas.get(i).getPareja2() == idFila1) {
                yaPaso = true;
                break;
            }
        }
        return yaPaso;
    }
}
