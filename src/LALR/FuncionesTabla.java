/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import Graficas.GraficaPila;
import interfaz.PanelHojas;
import java.util.ArrayList;
import pollitos.ExpresionesAux;
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
    private Precedencia precedencia = new Precedencia();
    private GraficaPila grafica = new GraficaPila();
    private String variables = "";
    private CodigoJava codigo = new CodigoJava();
    private Tipado tipado = new Tipado();

    public void transiciones(ArrayList<Token> listTokens, NodoTabla[][] tabla, ArrayList<Simbolos> listSimbolos, ArrayList<Estados> listEstados, String miCodigo) {
        ArrayList<Pila> miPila = new ArrayList<>();
        boolean cadenaAceptada = true;
        miPila.add(new Pila(null, 1));
        mostrarPila(miPila);
        grafica.tablaPila(miPila);
        listTokens.add(new Token("$", "aceptacion", "$", null));
        noFila = 1;
        for (int i = 0; i < listTokens.size(); i++) {
            if (listTokens.get(i).getTipo() != null) {
                if (listTokens.get(i).getTipo().equals("error")) {
                    PanelHojas.totalErrores += "(ERROR LEXICO) token: "+listTokens.get(i).getValor()+" no reconocido dentro de la cadena de entrada.\n";
                    cadenaAceptada = false;
                }
            }
            Integer columna = buscarColumna(tabla, listTokens.get(i).getIdentificador(), listSimbolos);
            if (columna != null) {
                if (tabla[noFila][columna] != null) {
                    Integer noAccion = null;
                    if (tabla[noFila][columna].getAcciones().size() == 1) {
                        noAccion = 0;
                    } else if (tabla[noFila][columna].getAcciones().size() > 1) {
                        noAccion = precedencia.manejarPrecedencia(miPila, listTokens.get(i), listSimbolos, tabla, noFila, columna);
                    }
                    if (noAccion != null) {
                        if (tabla[noFila][columna].getAcciones().get(noAccion).getAccion().equals("reduce")) {
                            if (!reduce(listTokens.get(i), miPila, listEstados, tabla[noFila][columna].getAcciones().get(0).getNoCaso(), tabla, listSimbolos, miCodigo)) {
                                
                                PanelHojas.totalErrores += "(ERROR SINTACTICO) token: "+listTokens.get(i).getIdentificador()+" con valor: "+listTokens.get(i).getIdentificador()+" insertado de forma incorrecta.\n";
                                cadenaAceptada = false;
                                break;
                            }
                        } else if (tabla[noFila][columna].getAcciones().get(noAccion).getAccion().equals("goTo")) {
                        } else if (tabla[noFila][columna].getAcciones().get(noAccion).getAccion().equals("shift")) {
                            if (!shift(listTokens.get(i), miPila, tabla[noFila][columna].getAcciones().get(0).getNoCaso())) {
                                cadenaAceptada = false;
                                break;
                            }
                        }
                    }
                } else {
                    PanelHojas.totalErrores += "(ERROR SINTACTICO) token: "+listTokens.get(i).getIdentificador()+" con valor: "+listTokens.get(i).getValor()+" insertado de forma incorrecta.\n";
                    cadenaAceptada = false;
                    break;
                }
            }
            if (i == listTokens.size() - 1) {
                if (!tabla[noFila][columna].getAcciones().get(0).getAccion().equals("aceptacion")) {
                    PanelHojas.totalErrores += "(ERROR SINTACTICO) se esperaba finalizacion de cadena.\n";
                    cadenaAceptada = false;
                }
            }
        }
        if (!cadenaAceptada) {
            PanelHojas.totalErrores += "Esta cadena no es valida para el lenguaje seleccionado.\n";
            System.out.println("Esta entrada no es valida para el lenguaje seleccionado.");
        } else {
            PanelHojas.todoBien += "Esta cadena si es valida para el lenguaje seleccionado.\n";
            System.out.println("Esta cadena si es valida para el lenguaje seleccionado");
        }
    }

    public boolean shift(Token actual, ArrayList<Pila> miPila, int casoTransicion) {
        Pila nuevo = new Pila(actual, casoTransicion);
        miPila.add(nuevo);
        noFila = casoTransicion;
        mostrarPila(miPila);
        grafica.tablaPila(miPila);
        return true;
    }

    public boolean reduce(Token actual, ArrayList<Pila> miPila, ArrayList<Estados> listEstados, int casoTransicion, NodoTabla[][] tabla, ArrayList<Simbolos> listSimbolos, String miCodigo) {
        boolean todoCorrecto = true;
        Estados analizado = buscarEstado(listEstados, casoTransicion);
        ArrayList<ExpresionesAux> expresiones = new ArrayList<>();
        for (int i = 0; i < analizado.getMisExpresiones().size(); i++) {
            //   expresiones.add(analizado.getMisExpresiones().get(i).getIdentificador());
            expresiones.add(new ExpresionesAux(analizado.getMisExpresiones().get(i).getIdentificador(), analizado.getMisExpresiones().get(i).getVarAsociada()));
        }
        if (desapilarReduce(miPila, listEstados, expresiones, listSimbolos)) {
            for (int i = (miPila.size() - expresiones.size()); i < miPila.size(); i++) {
                miPila.remove(i);
                i--;
            }
        } else {
            PanelHojas.totalErrores += "(ERROR SINTACTICO) token: "+actual.getIdentificador()+" con valor: "+actual.getValor()+" insertado de forma incorrecta.\n";
            todoCorrecto = false;
        }
        if (todoCorrecto) {
            //   mostrarPila(miPila);
            //  grafica.tablaPila(miPila);
            
            Token nuevo = new Token(analizado.getIdentificador(), null, null, analizado.getNoEstado());
            String tipoDev = tipado.determinarTipo(analizado.getIdentificador(), listSimbolos);
            if (tipoDev.equals("entero")) {
                nuevo.setValor(codigo.devolverDatos(variables, analizado.getResult(), "varDevolver", "Integer", miCodigo, analizado.getCodigoAntesala()));
                PanelHojas.todoBien += nuevo.getValor()+"\n";
            } else if (tipoDev.equals("cadena")) {
                nuevo.setValor(codigo.devolverDatos(variables, analizado.getResult(), "varDevolver", "String", miCodigo, analizado.getCodigoAntesala()));
                PanelHojas.todoBien += nuevo.getValor()+"\n";
            } else if (tipoDev.equals("real")) {
                nuevo.setValor(codigo.devolverDatos(variables, analizado.getResult(), "varDevolver", "Double", miCodigo, analizado.getCodigoAntesala()));
                PanelHojas.todoBien += nuevo.getValor()+"\n";
            } else if (tipoDev.equals("")) {
                nuevo.setValor(codigo.devolverDatos(variables, analizado.getResult(), "varDevolver", "void", miCodigo, analizado.getCodigoAntesala()));
            }
            variables = "";
            nuevo.setEsTerminal(false);
            Pila nuevo2 = new Pila(nuevo, null);
            Integer numPila = null;
            Integer columna = buscarColumna(tabla, analizado.getIdentificador(), listSimbolos);
            if (tabla[miPila.get(miPila.size() - 1).getNoCaso()][columna] != null) {
                Integer noAccion = null;
                if (tabla[miPila.get(miPila.size() - 1).getNoCaso()][columna].getAcciones().size() == 1) {
                    noAccion = 0;
                } else if (tabla[miPila.get(miPila.size() - 1).getNoCaso()][columna].getAcciones().size() > 1) {
                    noAccion = precedencia.manejarPrecedencia(miPila, actual, listSimbolos, tabla, miPila.get(miPila.size() - 1).getNoCaso(), columna);
                }
                if (noAccion != null) {
                    if (tabla[miPila.get(miPila.size() - 1).getNoCaso()][columna].getAcciones().get(noAccion).getAccion().equals("goTo")) {
                        numPila = tabla[miPila.get(miPila.size() - 1).getNoCaso()][columna].getAcciones().get(noAccion).getNoCaso();
                        noFila = numPila;
                        nuevo2.setNoCaso(numPila);
                        miPila.add(nuevo2);
                        mostrarPila(miPila);
                        grafica.tablaPila(miPila);
                        if (!reingresoToken(actual, miPila, tabla, listSimbolos, listEstados, miCodigo)) {
                            todoCorrecto = false;
                        }
                    }
                }
            } else { 
                PanelHojas.totalErrores += "(ERROR SINTACTICO) token: "+actual.getIdentificador()+" con valor: "+actual.getIdentificador()+" insertado de forma incorrecta.\n"; 
                todoCorrecto = false;
            }
        }
        return todoCorrecto;

    }

    //esto cuando se acaba de meter un token gracias a un reduce
    public boolean reingresoToken(Token actual, ArrayList<Pila> miPila, NodoTabla[][] tabla, ArrayList<Simbolos> listSimbolos, ArrayList<Estados> listEstados, String miCodigo) {
        boolean todoCorrecto = true;
        int ultimoNodo = miPila.get(miPila.size() - 1).getNoCaso();
        int columna = buscarColumna(tabla, actual.getIdentificador(), listSimbolos);
        if (tabla[ultimoNodo][columna] != null) {
            Integer noAccion = null;
            if (tabla[ultimoNodo][columna].getAcciones().size() == 1) {
                noAccion = 0;
            } else if (tabla[ultimoNodo][columna].getAcciones().size() > 1) {
                noAccion = precedencia.manejarPrecedencia(miPila, actual, listSimbolos, tabla, ultimoNodo, columna);
            }
            if (noAccion != null) {
                if (tabla[ultimoNodo][columna].getAcciones().get(noAccion).getAccion().equals("reduce")) {
                    if (!reduce(actual, miPila, listEstados, tabla[ultimoNodo][columna].getAcciones().get(noAccion).getNoCaso(), tabla, listSimbolos, miCodigo)) {
                        todoCorrecto = false;
                    }
                } else if (tabla[ultimoNodo][columna].getAcciones().get(noAccion).getAccion().equals("goTo")) {

                } else if (tabla[ultimoNodo][columna].getAcciones().get(noAccion).getAccion().equals("shift")) {
                    if (!shift(actual, miPila, tabla[ultimoNodo][columna].getAcciones().get(noAccion).getNoCaso())) {
                        todoCorrecto = false;
                    }
                }
            }
        } else {
            PanelHojas.totalErrores += "(ERROR SINTACTICO) token: "+actual.getIdentificador()+" con valor: "+actual.getValor()+" insertado de forma incorrecta.\n";
            todoCorrecto = false;
        }
        return todoCorrecto;

    }

    public boolean desapilarReduce(ArrayList<Pila> miPila, ArrayList<Estados> listEstados, ArrayList<ExpresionesAux> expresiones, ArrayList<Simbolos> listSimbolos) {
        boolean sePuede = true;
        int cont = 1;
        for (int i = miPila.size() - 1; i >= (miPila.size() - expresiones.size()); i--) {
            if (!miPila.get(i).getToken().getIdentificador().equals(expresiones.get(expresiones.size() - cont).getId())) {
                sePuede = false;
                break;
            } else {
                if (expresiones.get(expresiones.size() - cont).getRepresentante() != null) {
                    String tipoVar = tipado.determinarTipo(miPila.get(i).getToken().getIdentificador(), listSimbolos);
                        if (tipoVar.equals("entero")) {
                            variables += "Integer " + expresiones.get(expresiones.size() - cont).getRepresentante() + "= " + miPila.get(i).getToken().getValor() + ";\n";
                        } else if (tipoVar.equals("real")) {
                            variables += "Double " + expresiones.get(expresiones.size() - cont).getRepresentante() + "= " + miPila.get(i).getToken().getValor() + ";\n";
                        } else if (tipoVar.equals("cadena")) {
                            variables += "String " + expresiones.get(expresiones.size() - cont).getRepresentante() + "= \"" + miPila.get(i).getToken().getValor() + "\";\n";
                        } else if(tipoVar.equals("")){
                            variables += "Object " + expresiones.get(expresiones.size() - cont).getRepresentante() + "= " + miPila.get(i).getToken().getValor() + ";\n";
                        }
                    
                }
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
