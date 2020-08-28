/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

import Arbol.NodoArbol;
import Arbol.NodoSiguiente;
import java.io.Serializable;
import java.util.ArrayList;
import pollitos.Conductores;
import pollitos.MisExpresiones;

/**
 *
 * @author luisGonzalez
 */
public class GeneracionEstados implements Serializable{

    public Integer sinMarcar = null;

    public void generarPrimerEstado(MisExpresiones expresion) {
        NodoEstado primero = new NodoEstado(0);
        for (int i = 0; i < expresion.getNodoArbol().getPrimeros().size(); i++) {
            primero.getListVinculos().add(expresion.getNodoArbol().getPrimeros().get(i));
        }
        expresion.getListEstados().add(primero);
    }

    public void creacionEstados(ArrayList<NodoEstado> listEstados, ArrayList<NodoSiguiente> tablaSiguientes, MisExpresiones expresion) {
        generarPrimerEstado(expresion);
        while (!verificarMarcaje(listEstados)) {
            NodoEstado aTrabajar = listEstados.get(sinMarcar);
            listEstados.get(sinMarcar).setMarcado(true);
            for (int i = 0; i < aTrabajar.getListVinculos().size(); i++) {
                int noEstado = aTrabajar.getListVinculos().get(i).getNumero();
                ArrayList<NodoArbol> listAux = new ArrayList<>();
                listAux = buscarSiguientes(tablaSiguientes, noEstado);
                if (!listAux.isEmpty()) {
                    if (verificarExistenciaEstado(listEstados, listAux)) {

                    } else {
                        NodoEstado nuevoNodo = new NodoEstado(listEstados.get(listEstados.size() - 1).getId() + 1);
                        nuevoNodo.setListVinculos(listAux);
                        verificarAceptacion(nuevoNodo, tablaSiguientes.get(tablaSiguientes.size() - 1).getNodo().getNumero());
                        listEstados.add(nuevoNodo);
                    }
                }
            }

        }

    }

    public boolean verificarExistenciaEstado(ArrayList<NodoEstado> listEstados, ArrayList<NodoArbol> elementos) {
        boolean yaExiste = false;
        for (int i = 0; i < listEstados.size(); i++) {
            if (listEstados.get(i).getListVinculos().containsAll(elementos) && elementos.containsAll(listEstados.get(i).getListVinculos())) {
                yaExiste = true;
                break;
            }
        }
        return yaExiste;
    }

    public ArrayList<NodoArbol> buscarSiguientes(ArrayList<NodoSiguiente> tablaSiguiente, int numEstado) {
        ArrayList<NodoArbol> aDevolver = new ArrayList<>();
        for (int i = 0; i < tablaSiguiente.size(); i++) {
            if (tablaSiguiente.get(i).getNodo().getNumero() == numEstado) {
                aDevolver = tablaSiguiente.get(i).getListSiguientes();
                break;
            }
        }
        return aDevolver;
    }

    public boolean verificarMarcaje(ArrayList<NodoEstado> listEstados) {
        boolean todosMarcados = true;
        for (int i = 0; i < listEstados.size(); i++) {
            if (!listEstados.get(i).isMarcado()) {
                todosMarcados = false;
                sinMarcar = i;
                break;
            }
        }
        return todosMarcados;
    }

    public void mostrarEstados(ArrayList<NodoEstado> listEstados, ArrayList<NodoSiguiente> tablaSiguientes) {
        for (int i = 0; i < listEstados.size(); i++) {
            System.out.println("ESTADO No: " + listEstados.get(i).getId() + " ACEPTACION: " + listEstados.get(i).isAceptacion());
            for (int j = 0; j < listEstados.get(i).getListVinculos().size(); j++) {

                Integer estado = buscarEstado(listEstados, tablaSiguientes, listEstados.get(i).getListVinculos().get(j));
                if (estado != null) {
                    System.out.println("        Desde el simbolo: " + listEstados.get(i).getListVinculos().get(j).getTipo() + " me dirijo al estado: " + estado);
                }

            }
        }
    }

    //agrega a cada estado de aceptacion prioridades entre todas las expresiones
    public void agregarPrioridades(ArrayList<NodoEstado> listEstados, ArrayList<NodoSiguiente> tablaSiguiente) {
        for (int i = 0; i < listEstados.size(); i++) {
            for (int j = 0; j < listEstados.get(i).getListVinculos().size(); j++) {
                Integer numEstado = buscarEstado(listEstados, tablaSiguiente, listEstados.get(i).getListVinculos().get(j));
                NodoEstado estado1 = buscarEstado2(numEstado, listEstados);
                String tipo = listEstados.get(i).getListVinculos().get(j).getTipo();
                if (estado1 != null) {
                    for (int k = 0; k < listEstados.get(i).getListVinculos().size(); k++) {
                        if (j != k) {
                            Integer numEstado2 = buscarEstado(listEstados, tablaSiguiente, listEstados.get(i).getListVinculos().get(k));
                            NodoEstado estado2 = buscarEstado2(numEstado2, listEstados);
                            String tipo2 = listEstados.get(i).getListVinculos().get(k).getTipo();
                            if (estado2 != null) {
                                if (estado1.isAceptacion() && estado2.isAceptacion()) {
                                    if (tipo.equals(tipo2)) {
                                        if (tipo.equals("rango")) {
                                            int rangoA1 = listEstados.get(i).getListVinculos().get(j).getRangoChar1();
                                            int rangoA2 = listEstados.get(i).getListVinculos().get(j).getRangoChar2();
                                            int rangoB1 = listEstados.get(i).getListVinculos().get(k).getRangoChar1();
                                            int rangoB2 = listEstados.get(i).getListVinculos().get(k).getRangoChar2();
                                            if (rangoA1 == rangoB1 && rangoA2 == rangoB2) {
                                                Conductores conductor1 = new Conductores(listEstados.get(i).getListVinculos().get(j).getPrioridad(), listEstados.get(i).getListVinculos().get(j).getIdExpresion(), listEstados.get(i).getListVinculos().get(j).getTipo(), listEstados.get(i).getListVinculos().get(j).getPalabraCompleta());
                                                Conductores conductor2 = new Conductores(listEstados.get(i).getListVinculos().get(k).getPrioridad(), listEstados.get(i).getListVinculos().get(k).getIdExpresion(), listEstados.get(i).getListVinculos().get(k).getTipo(), listEstados.get(i).getListVinculos().get(k).getPalabraCompleta());
                                                estado1.getConductores().add(conductor2);
                                                estado2.getConductores().add(conductor1);
                                            }
                                        } else if (tipo.equals("caracteres")) {
                                            String caracter1 = listEstados.get(i).getListVinculos().get(j).getCaracteres();
                                            String caracter2 = listEstados.get(i).getListVinculos().get(k).getCaracteres();
                                            if (caracter1.equals(caracter2)) {
                                                Conductores conductor1 = new Conductores(listEstados.get(i).getListVinculos().get(j).getPrioridad(), listEstados.get(i).getListVinculos().get(j).getIdExpresion(), listEstados.get(i).getListVinculos().get(j).getTipo(), listEstados.get(i).getListVinculos().get(j).getPalabraCompleta());
                                                Conductores conductor2 = new Conductores(listEstados.get(i).getListVinculos().get(k).getPrioridad(), listEstados.get(i).getListVinculos().get(k).getIdExpresion(), listEstados.get(i).getListVinculos().get(k).getTipo(), listEstados.get(i).getListVinculos().get(k).getPalabraCompleta());
                                                estado1.getConductores().add(conductor2);
                                                estado2.getConductores().add(conductor1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    //agrega el primer vinculo conductor a un estado, si es de aceptacion
    public void agregarPrimerConductor(ArrayList<NodoEstado> listEstados, ArrayList<NodoSiguiente> tablaSiguiente) {
        for (int i = 0; i < listEstados.size(); i++) {
            //if (listEstados.get(i).isAceptacion()) {
                for (int j = 0; j < listEstados.get(i).getListVinculos().size(); j++) {
                    
                    Integer numEstado = buscarEstado(listEstados, tablaSiguiente, listEstados.get(i).getListVinculos().get(j));
                    NodoEstado estado1 = buscarEstado2(numEstado, listEstados);
                    if (estado1 != null) {
                        System.out.println(listEstados.get(i).getListVinculos().get(j).getTipo());
                        System.out.println(listEstados.get(i).getListVinculos().get(j).getPalabraCompleta()+"       PALABRA AGREGAFD");
                        Conductores conductor = new Conductores(listEstados.get(i).getListVinculos().get(j).getPrioridad(), listEstados.get(i).getListVinculos().get(j).getIdExpresion(), listEstados.get(i).getListVinculos().get(j).getTipo(), listEstados.get(i).getListVinculos().get(j).getPalabraCompleta());
                        if(listEstados.get(i).getListVinculos().get(j).getTipo().equals("caracteres")){
                            conductor.setCaracter(listEstados.get(i).getListVinculos().get(j).getCaracteres());
                        }
                        estado1.getConductores().add(conductor);
                    }
                }
          //  }
        }
    }

    public Integer buscarEstado(ArrayList<NodoEstado> listEstados, ArrayList<NodoSiguiente> tablaSiguiente, NodoArbol nodoGuia) {
        Integer estadoGuia = null;
        ArrayList<NodoArbol> aux = new ArrayList<>();
        for (int i = 0; i < tablaSiguiente.size(); i++) {
            if (tablaSiguiente.get(i).getNodo().getNumero() == nodoGuia.getNumero()) {
                for (int j = 0; j < tablaSiguiente.get(i).getListSiguientes().size(); j++) {
                    aux.add(tablaSiguiente.get(i).getListSiguientes().get(j));
                }
                break;
            }
        }
        if (!aux.isEmpty()) {
            for (int i = 0; i < listEstados.size(); i++) {
                if (listEstados.get(i).getListVinculos().containsAll(aux) && aux.containsAll(listEstados.get(i).getListVinculos())) {
                    estadoGuia = listEstados.get(i).getId();
                }
            }
        }
        return estadoGuia;

    }

    public void verificarAceptacion(NodoEstado actual, int nodoAceptacion) {
        boolean esAceptable = false;
        for (int i = 0; i < actual.getListVinculos().size(); i++) {
            if (actual.getListVinculos().get(i).getNumero() == nodoAceptacion) {
                String id = actual.getListVinculos().get(i).getIdExpresion();
                actual.setIdExpresion(id);
                esAceptable = true;

                break;
            }
        }
        if (esAceptable) {
            actual.setAceptacion(true);
        }
    }

    public NodoEstado buscarEstado2(Integer id, ArrayList<NodoEstado> listEstados) {
        NodoEstado aDevolver = null;
        if (id != null) {
            for (int i = 0; i < listEstados.size(); i++) {
                if (listEstados.get(i).getId() == id) {
                    aDevolver = listEstados.get(i);
                }
            }
        }
        return aDevolver;
    }

}
