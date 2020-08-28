/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.util.ArrayList;
import pollitos.NodoTabla;
import pollitos.Simbolos;

/**
 *
 * @author luisGonzalez
 */
public class Tabla {

    GeneracionTabla gnTabla = new GeneracionTabla();

    public NodoTabla[][] creacionTabla(ArrayList<NodoCaso> listCasos, ArrayList<Simbolos> listSimbolos) {
        NodoTabla[][] tabla = new NodoTabla[listCasos.size() + 2][listSimbolos.size() + 2];
        System.out.println("TOTAL DE CASOS: " + listCasos.size() + " TOTAL DE ESTADOS: " + listSimbolos.size());

        rellenarFilaGuia(tabla, listSimbolos);
        rellenarColumnaGuia(tabla, listCasos);
        crearEstadoAceptacion(tabla, listCasos, listSimbolos);
        llenadoTabla(listCasos, tabla, listSimbolos);

        llenadoTablaReduces(listCasos, tabla, listSimbolos);
        //mostrarTabla(tabla, listCasos, listSimbolos);
        return tabla;
    }

    public NodoTabla[][] rellenarFilaGuia(NodoTabla[][] tabla, ArrayList<Simbolos> listSimbolos) {
        for (int i = 0; i < listSimbolos.size(); i++) {
            NodoTabla nuevo = new NodoTabla();
            nuevo.setSimbolo(listSimbolos.get(i));
            tabla[0][i + 1] = nuevo;
        }
        return tabla;
    }

    public NodoTabla[][] rellenarColumnaGuia(NodoTabla[][] tabla, ArrayList<NodoCaso> listCasos) {
        for (int i = 0; i < listCasos.size(); i++) {
            NodoTabla nuevo = new NodoTabla();
            nuevo.setFilaActiva(true);
            nuevo.setIdCasoFila(listCasos.get(i).getIdCaso());
            nuevo.setCasoColumna(listCasos.get(i));
            tabla[i + 1][0] = nuevo;
        }
        return tabla;
    }
    
    public NodoTabla[][] crearEstadoAceptacion(NodoTabla[][] tabla, ArrayList<NodoCaso> listCasos, ArrayList<Simbolos> listSimbolos){
        for (int i = 0; i < listCasos.size(); i++) {
            for (int j = 0; j < listCasos.get(i).getListEstados().get(0).getMisExpresiones().size(); j++) {
                Integer aceptacion = gnTabla.verUbicacionPunto(listCasos.get(i).getListEstados().get(0).getMisExpresiones());
                if(aceptacion != null){
                    if(listCasos.get(i).getListEstados().get(0).getMisExpresiones().get(aceptacion).getIdentificador().equals("$")){
                        Integer columna = determinarColumna("$", tabla, listSimbolos);
                        if(columna != null){
                            NodoTabla nuevo = new NodoTabla();
                            nuevo.setAccion("aceptacion");
                            tabla[listCasos.get(i).getIdCaso()][columna] = nuevo;
                            break;
                        }
                    }
                }
            }
        }
        return tabla;
    }

    public NodoTabla[][] llenadoTabla(ArrayList<NodoCaso> listCasos, NodoTabla[][] tabla, ArrayList<Simbolos> listSimbolos) {
        for (int i = 0; i < listCasos.size(); i++) {
            //System.out.println(listCasos.get(i).getIdCaso());
            for (int j = 0; j < listCasos.get(i).getListVinculos().size(); j++) {
                //System.out.println("            "+listCasos.get(i).getListVinculos().get(j).getVinculo()+" "+listCasos.get(i).getListVinculos().get(j).getIdCasoVinculo());
                Integer columna = determinarColumna(listCasos.get(i).getListVinculos().get(j).getVinculo().toString(), tabla, listSimbolos);
                if (determinarTipoSimbolo(listSimbolos, listCasos.get(i).getListVinculos().get(j).getVinculo().toString())) {
                    NodoTabla nuevo = new NodoTabla();
                    nuevo.setNoCaso(listCasos.get(i).getIdCaso());
                    nuevo.setAccion("shift");
                    tabla[listCasos.get(i).getListVinculos().get(j).getIdCasoVinculo()][columna] = nuevo;
                } else {
                    NodoTabla nuevo = new NodoTabla();
                    nuevo.setNoCaso(listCasos.get(i).getIdCaso());
                    nuevo.setAccion("goTo");
                    tabla[listCasos.get(i).getListVinculos().get(j).getIdCasoVinculo()][columna] = nuevo;
                }
            }
        }
        return tabla;
    }

    public NodoTabla[][] llenadoTablaReduces(ArrayList<NodoCaso> listCasos, NodoTabla[][] tabla, ArrayList<Simbolos> listSimbolos) {
        for (int i = 0; i < listCasos.size(); i++) {
            for (int j = 0; j < listCasos.get(i).getListEstados().size(); j++) {
                if (!listCasos.get(i).getListEstados().get(j).getMisExpresiones().isEmpty()) {
                    Integer ubicacionPunto = gnTabla.verUbicacionPunto(listCasos.get(i).getListEstados().get(j).getMisExpresiones());
                    if (ubicacionPunto == null) {
                        if (listCasos.get(i).getListEstados().get(j).getMisExpresiones().get(listCasos.get(i).getListEstados().get(j).getMisExpresiones().size() - 1).getPuntoFinal()) {
                            int noEstado = listCasos.get(i).getListEstados().get(j).getNoEstado();
                            for (int k = 0; k < listCasos.get(i).getListEstados().get(j).getMisCarriles().size(); k++) {
                                Integer columna = determinarColumna(listCasos.get(i).getListEstados().get(j).getMisCarriles().get(k).getId(), tabla, listSimbolos);
                                NodoTabla nuevo = new NodoTabla();
                                nuevo.setAccion("reduce");
                                nuevo.setNoCaso(noEstado);
                                tabla[listCasos.get(i).getIdCaso()][columna] = nuevo;

                            }
                        }

                    }
                }
            }
        }
        return tabla;
    }

    public Boolean determinarTipoSimbolo(ArrayList<Simbolos> listSimbolos, String id) {
        Boolean esTerminal = null;
        for (int i = 0; i < listSimbolos.size(); i++) {
            if (listSimbolos.get(i).getIdentificador().equals(id)) {
                if (listSimbolos.get(i).getTipo().equals("Terminal")) {
                    esTerminal = true;
                    break;
                } else {
                    esTerminal = false;
                    break;
                }
            }
        }
        return esTerminal;
    }

    //determina en que columna debe ser llenada la casilla
    public Integer determinarColumna(String id, NodoTabla[][] tabla, ArrayList<Simbolos> listSimbolos) {
        Integer columna = null;
        for (int i = 0; i < listSimbolos.size(); i++) {
            System.out.println(tabla[0][i + 1].getSimbolo().getIdentificador() + "    " + id + "      PARA LOS REDUCES");
            if (tabla[0][i + 1].getSimbolo().getIdentificador().equals(id)) {
                columna = i + 1;
                break;
            }
        }
        return columna;
    }

    public void mostrarTabla(NodoTabla[][] tabla, ArrayList<NodoCaso> listCasos, ArrayList<Simbolos> listSimbolos) {
        for (int i = 0; i < listCasos.size(); i++) {
            if (tabla[listCasos.get(i).getIdCaso()][0].isFilaActiva()) {
                System.out.println("Caso no: " + listCasos.get(i).getIdCaso());
                for (int j = 0; j < listSimbolos.size(); j++) {
                    if (tabla[i + 1][j + 1] != null && tabla[i + 1][j + 1] != null) {
                        System.out.println("    " + tabla[i + 1][j + 1].getAccion() + " " + tabla[i + 1][j + 1].getNoCaso()+ " "+tabla[0][j+1].getSimbolo().getIdentificador());
                    }
                }
            }
        }
    }


}
