/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import Automata.GeneracionEstados;
import java.util.ArrayList;
import pollitos.MisExpresiones;

/**
 *
 * @author luisGonzalez
 */
public class GeneracionArbol {

    public int numeracionHojas = 1;
    public GeneracionEstados estados = new GeneracionEstados();

    //metodo de prueba que sirve para verificar en una expresion si todo bine (probablemmente lo borre despues)
    public void pruebaExpresion(MisExpresiones nodoPrueba) {
        numeracionHojas(nodoPrueba.getNodoArbol(), true);
        agregarNulabilidadHojas(nodoPrueba.getNodoArbol());

        restoNulabilidad(nodoPrueba.getNodoArbol());

        calculoValoresIniciales(nodoPrueba.getNodoArbol());

        calcularPrimeros(nodoPrueba.getNodoArbol());

        llenarTablaSiguientes(nodoPrueba.getTablaSiguientes(), nodoPrueba.getNodoArbol());

        llenarSiguientes(nodoPrueba.getTablaSiguientes(), nodoPrueba.getNodoArbol());

        recorrerArbol(nodoPrueba.getNodoArbol(), 0);

        mostrarTablaSiguientes(nodoPrueba.getTablaSiguientes());

        estados.creacionEstados(nodoPrueba.getListEstados(), nodoPrueba.getTablaSiguientes(), nodoPrueba);
        
        estados.agregarPrimerConductor(nodoPrueba.getListEstados(), nodoPrueba.getTablaSiguientes());
        estados.agregarPrioridades(nodoPrueba.getListEstados(), nodoPrueba.getTablaSiguientes());
        estados.mostrarEstados(nodoPrueba.getListEstados(), nodoPrueba.getTablaSiguientes());

    }

    //numera cada nodo dentro del arbol
    public void numeracionHojas(NodoArbol nodo, boolean primero) {
        if (nodo.getNodo1() == null && nodo.getNodo2() == null) {
            if (!nodo.getTipo().equals("lambda")) {
                nodo.setNumero(numeracionHojas);
                numeracionHojas++;
            }
        } else {
            if (nodo.getNodo1() != null) {
                numeracionHojas(nodo.getNodo1(), false);
            }
            if (nodo.getNodo2() != null) {
                numeracionHojas(nodo.getNodo2(), false);
            }
        }
        if (primero) {
            numeracionHojas = 1;
        }
    }

    //agrega nulabilidad a cada hoja dentro del nodo
    public void agregarNulabilidadHojas(NodoArbol nodo) {
        if (nodo.getNodo1() == null && nodo.getNodo2() == null) {
            if (!nodo.getTipo().equals("lambda")) {
                nodo.setNulabilidad(false);
            } else {
                nodo.setNulabilidad(true);
            }
        } else {
            if (nodo.getNodo1() != null) {
                agregarNulabilidadHojas(nodo.getNodo1());
            }
            if (nodo.getNodo2() != null) {
                agregarNulabilidadHojas(nodo.getNodo2());
            }
        }
    }

    //agrega la nulabilidad a los demas nodos dentro del arbol
    public void restoNulabilidad(NodoArbol nodo) {
        if (nodo.getNodo1() != null && nodo.getNodo2() != null) {
            if (nodo.getNodo1().isNulabilidad() != null && nodo.getNodo2().isNulabilidad() != null) {
                if (nodo.getTipo().equals("simbolo")) {
                    switch (nodo.getCaracteres().charAt(0)) {
                        case '.':
                            if (nodo.getNodo1().isNulabilidad() && nodo.getNodo2().isNulabilidad()) {
                                nodo.setNulabilidad(true);
                            } else {
                                nodo.setNulabilidad(false);
                            }
                            break;
                        case '|':
                            System.out.println("ENTRO A ESTA CONDICION MADERFAKERS");
                            if (!nodo.getNodo1().isNulabilidad() && !nodo.getNodo2().isNulabilidad()) {
                                nodo.setNulabilidad(false);
                            } else {
                                nodo.setNulabilidad(true);
                            }
                            break;
                        default:
                            System.out.println("Ha ingresado un simbolo no valido");
                            break;
                    }
                }
            } else {
                if (nodo.getNodo1().isNulabilidad() == null) {
                    restoNulabilidad(nodo.getNodo1());
                    restoNulabilidad(nodo);
                }

                if (nodo.getNodo2().isNulabilidad() == null) {
                    restoNulabilidad(nodo.getNodo2());
                    restoNulabilidad(nodo);
                }
            }
        } else {
            if (nodo.getNodo1() != null) {
                if (nodo.getNodo1().isNulabilidad() != null) {
                    if (nodo.getTipo().equals("simbolo")) {
                        switch (nodo.getCaracteres().charAt(0)) {
                            case '?':
                                nodo.setNulabilidad(true);
                                break;
                            case '*':
                                nodo.setNulabilidad(true);
                                break;
                            case '+':
                                if (nodo.getNodo1().isNulabilidad()) {
                                    nodo.setNulabilidad(true);
                                } else {
                                    nodo.setNulabilidad(false);
                                }
                                break;
                            default:
                                System.out.println("HA INGRESADO UN SIMBOLO NO VALIDO");
                                break;
                        }
                    }
                } else {
                    restoNulabilidad(nodo.getNodo1());
                    restoNulabilidad(nodo);
                }
            }
            //posiblemente agregare condicion para saber si el nodo derecho tiene nulabilidad.
        }

    }

    public void calcularPrimeros(NodoArbol nodo) {
        if (nodo.getNodo1() != null && nodo.getNodo2() != null) {
            if (!nodo.getNodo1().getPrimeros().isEmpty() && !nodo.getNodo1().getSiguientes().isEmpty() && !nodo.getNodo2().getPrimeros().isEmpty() && !nodo.getNodo2().getSiguientes().isEmpty()) {
                switch (nodo.getCaracteres().charAt(0)) {
                    case '|':
                        ArrayList<NodoArbol> misPrimeros = new ArrayList<>();
                        agregarValores(nodo.getNodo1().getPrimeros(), misPrimeros);
                        agregarValores(nodo.getNodo2().getPrimeros(), misPrimeros);
                        ArrayList<NodoArbol> misSiguientes = new ArrayList<>();
                        agregarValores(nodo.getNodo1().getSiguientes(), misSiguientes);
                        agregarValores(nodo.getNodo2().getSiguientes(), misSiguientes);
                        nodo.setPrimeros(misPrimeros);
                        nodo.setSiguientes(misSiguientes);
                        break;
                    case '.':
                        ArrayList<NodoArbol> misPrimeros2 = new ArrayList<>();
                        if (nodo.getNodo1().isNulabilidad()) {
                            agregarValores(nodo.getNodo1().getPrimeros(), misPrimeros2);
                            agregarValores(nodo.getNodo2().getPrimeros(), misPrimeros2);
                        } else {
                            agregarValores(nodo.getNodo1().getPrimeros(), misPrimeros2);
                        }
                        ArrayList<NodoArbol> misSiguientes2 = new ArrayList<>();
                        if (nodo.getNodo2().isNulabilidad()) {
                            agregarValores(nodo.getNodo1().getSiguientes(), misSiguientes2);
                            agregarValores(nodo.getNodo2().getSiguientes(), misSiguientes2);
                        } else {
                            agregarValores(nodo.getNodo2().getSiguientes(), misSiguientes2);
                        }
                        nodo.setPrimeros(misPrimeros2);
                        nodo.setSiguientes(misSiguientes2);
                        break;
                    default:
                        break;
                }
            } else {
                if (nodo.getNodo1().getPrimeros().isEmpty() && nodo.getNodo1().getSiguientes().isEmpty()) {
                    calcularPrimeros(nodo.getNodo1());
                    calcularPrimeros(nodo);
                }
                if (nodo.getNodo2().getPrimeros().isEmpty() && nodo.getNodo2().getSiguientes().isEmpty()) {
                    calcularPrimeros(nodo.getNodo2());
                    calcularPrimeros(nodo);
                }
            }
        } else {
            if (nodo.getNodo1() != null) {
                if (!nodo.getNodo1().getPrimeros().isEmpty() && !nodo.getNodo1().getSiguientes().isEmpty()) {
                    switch (nodo.getCaracteres().charAt(0)) {
                        case '?':
                            ArrayList<NodoArbol> listPrimeros = new ArrayList<>();
                            agregarValores(nodo.getNodo1().getPrimeros(), listPrimeros);
                            ArrayList<NodoArbol> listSiguientes = new ArrayList<>();
                            agregarValores(nodo.getNodo1().getSiguientes(), listSiguientes);
                            nodo.setPrimeros(listPrimeros);
                            nodo.setSiguientes(listSiguientes);
                            break;
                        case '+':
                            ArrayList<NodoArbol> listPrimeros2 = new ArrayList<>();
                            agregarValores(nodo.getNodo1().getPrimeros(), listPrimeros2);
                            ArrayList<NodoArbol> listSiguientes2 = new ArrayList<>();
                            agregarValores(nodo.getNodo1().getSiguientes(), listSiguientes2);
                            nodo.setPrimeros(listPrimeros2);
                            nodo.setSiguientes(listSiguientes2);

                            break;
                        case '*':
                            ArrayList<NodoArbol> listPrimeros3 = new ArrayList<>();
                            agregarValores(nodo.getNodo1().getPrimeros(), listPrimeros3);
                            ArrayList<NodoArbol> listSiguientes3 = new ArrayList<>();
                            agregarValores(nodo.getNodo1().getSiguientes(), listSiguientes3);
                            nodo.setPrimeros(listPrimeros3);
                            nodo.setSiguientes(listSiguientes3);
                            break;
                        default:
                            break;
                    }
                } else {
                    calcularPrimeros(nodo.getNodo1());
                    calcularPrimeros(nodo);
                }
            }
        }
    }

    public void agregarValores(ArrayList<NodoArbol> list, ArrayList<NodoArbol> aAgregar) {
        for (int i = 0; i < list.size(); i++) {
            aAgregar.add(list.get(i));
        }
    }

    //llena la tabla siguientes con los identificadores de los nodoHoja
    public void llenarTablaSiguientes(ArrayList<NodoSiguiente> listSiguientes, NodoArbol nodo) {
        if (nodo.getNodo1() == null && nodo.getNodo2() == null) {
            NodoSiguiente nuevo = new NodoSiguiente(nodo);
            listSiguientes.add(nuevo);
        } else {
            if (nodo.getNodo1() != null) {
                llenarTablaSiguientes(listSiguientes, nodo.getNodo1());
            }
            if (nodo.getNodo2() != null) {
                llenarTablaSiguientes(listSiguientes, nodo.getNodo2());
            }
        }
    }

    //llena la tabla siguientes con los siguientes de cada hoja
    public void llenarSiguientes(ArrayList<NodoSiguiente> listSiguientes, NodoArbol nodo) {
        if (nodo.getNodo1() != null && nodo.getNodo2() != null) {
            switch (nodo.getCaracteres().charAt(0)) {
                case '.':
                    for (int i = 0; i < nodo.getNodo1().getSiguientes().size(); i++) {
                        int nodoUsar = nodo.getNodo1().getSiguientes().get(i).getNumero();
                        for (int j = 0; j < listSiguientes.size(); j++) {
                            if (listSiguientes.get(j).getNodo().getNumero() == nodoUsar) {
                                for (int k = 0; k < nodo.getNodo2().getPrimeros().size(); k++) {
                                    listSiguientes.get(j).getListSiguientes().add(nodo.getNodo2().getPrimeros().get(k));
                                }
                                break;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
            llenarSiguientes(listSiguientes, nodo.getNodo1());
            llenarSiguientes(listSiguientes, nodo.getNodo2());
        } else {
            if (nodo.getNodo1() != null) {
                switch (nodo.getCaracteres().charAt(0)) {
                    case '*':
                        for (int i = 0; i < nodo.getNodo1().getPrimeros().size(); i++) {
                            int nodoUsar = nodo.getNodo1().getPrimeros().get(i).getNumero();
                            for (int j = 0; j < listSiguientes.size(); j++) {
                                if (listSiguientes.get(j).getNodo().getNumero() == nodoUsar) {
                                    for (int k = 0; k < nodo.getNodo1().getSiguientes().size(); k++) {
                                        listSiguientes.get(j).getListSiguientes().add(nodo.getNodo1().getSiguientes().get(k));
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    case '+':
                        for (int i = 0; i < nodo.getNodo1().getPrimeros().size(); i++) {
                            int nodoUsar = nodo.getNodo1().getPrimeros().get(i).getNumero();
                            for (int j = 0; j < listSiguientes.size(); j++) {
                                if (listSiguientes.get(j).getNodo().getNumero() == nodoUsar) {
                                    for (int k = 0; k < nodo.getNodo1().getSiguientes().size(); k++) {
                                        listSiguientes.get(j).getListSiguientes().add(nodo.getNodo1().getSiguientes().get(k));
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
                llenarSiguientes(listSiguientes, nodo.getNodo1());
            }
        }
    }

    //calcula los primeros y los siguientes de las hojas del arbol
    public void calculoValoresIniciales(NodoArbol nodo) {
        if (nodo.getNodo1() == null && nodo.getNodo2() == null) {
            Integer numero = nodo.getNumero();
            if (numero != null) {
                nodo.getPrimeros().add(nodo);
                nodo.getSiguientes().add(nodo);
            }
        } else {
            if (nodo.getNodo1() != null) {
                calculoValoresIniciales(nodo.getNodo1());
            }
            if (nodo.getNodo2() != null) {
                calculoValoresIniciales(nodo.getNodo2());
            }
        }
    }

    public void calcularSiguientes() {

    }

    public void recorrerArbol(NodoArbol nodo, int valor) {
        if (valor == 0) {
            System.out.println("El nodo padre de todos es: " + nodo.getTipo());
        }
        if (nodo.getNodo1() != null) {
            System.out.println("El nodo derecho de " + nodo.getTipo() + " es: " + nodo.getNodo1().getTipo() + "  id: " + nodo.getNodo1().getNumero() + " nulabilidad: " + nodo.getNodo1().isNulabilidad()+" simbolo: "+nodo.getNodo1().getCaracteres());
            String primeros = "", siguientes = "";
            for (int i = 0; i < nodo.getNodo1().getPrimeros().size(); i++) {
                primeros += nodo.getNodo1().getPrimeros().get(i).getNumero();
            }
            for (int i = 0; i < nodo.getNodo1().getSiguientes().size(); i++) {
                siguientes += nodo.getNodo1().getSiguientes().get(i).getNumero();
            }
            System.out.println("                Sus primeros: " + primeros);
            System.out.println("                Sus siguientes: " + siguientes);
            recorrerArbol(nodo.getNodo1(), 1);
        }
        if (nodo.getNodo2() != null) {
            System.out.println("El nodo izquierdo de " + nodo.getTipo() + " es: " + nodo.getNodo2().getTipo() + " id: " + nodo.getNodo2().getNumero() + " nulabilidad: " + nodo.getNodo2().isNulabilidad()+" simbolo: "+nodo.getNodo2().getCaracteres());
            String primeros = "", siguientes = "";
            for (int i = 0; i < nodo.getNodo2().getPrimeros().size(); i++) {
                primeros += nodo.getNodo2().getPrimeros().get(i).getNumero();
            }
            for (int i = 0; i < nodo.getNodo2().getSiguientes().size(); i++) {
                siguientes += nodo.getNodo2().getSiguientes().get(i).getNumero();
            }
            System.out.println("                Sus primeros: " + primeros);
            System.out.println("                Sus siguientes: " + siguientes);
            recorrerArbol(nodo.getNodo2(), 1);
        }
    }

    public void mostrarTablaSiguientes(ArrayList<NodoSiguiente> tablaSiguientes) {
        for (int i = 0; i < tablaSiguientes.size(); i++) {
            System.out.println("No. " + tablaSiguientes.get(i).getNodo().getNumero());
            System.out.println("                Sus siguientes:");
            for (int j = 0; j < tablaSiguientes.get(i).getListSiguientes().size(); j++) {
                System.out.println("                " + tablaSiguientes.get(i).getListSiguientes().get(j).getNumero());
            }
        }
    }

    //para la creacion de nodos concatenacion si existe una palabra reservada
    public NodoArbol creacionNodoPalabras(String palabra) {
        NodoArbol conc = null;
        if (palabra.length() > 1) {
            for (int i = 1; i < palabra.length(); i++) {
                if (i == 1) {
                    conc = new NodoArbol("simbolo", null, null, ".");
                    NodoArbol izq = new NodoArbol("caracteres", null, null, Character.toString(palabra.charAt(0)));
                    NodoArbol der = new NodoArbol("caracteres", null, null, Character.toString(palabra.charAt(1)));
                    izq.setPalabraCompleta(palabra);
                    der.setPalabraCompleta(palabra);
                    conc.setNodo1(izq);
                    conc.setNodo2(der);

                }  else {
                    NodoArbol der = new NodoArbol("caracteres", null, null, Character.toString(palabra.charAt(i)));
                    der.setPalabraCompleta(palabra);
                    NodoArbol izq = conc;
                    conc = new NodoArbol("simbolo", null, null, ".");
                    conc.setNodo1(izq);
                    conc.setNodo2(der);
                }
            }
        } else {
            conc = new NodoArbol("caracteres", null, null, Character.toString(palabra.charAt(0)));
            conc.setPalabraCompleta(palabra);
        }
        return conc;
    }

    //metodo para unir todos los arboles en uno solo
    public NodoArbol unirArboles(ArrayList<MisExpresiones> listExpresiones) {
        NodoArbol primero = null;
        NodoArbol union = null;
        if (listExpresiones.size() > 1) {
            for (int i = 1; i < listExpresiones.size(); i++) {
                if (i == 1) {
                    primero = new NodoArbol("simbolo", null, null, "|");
                    NodoArbol izq = listExpresiones.get(0).getNodoArbol();
                    NodoArbol der = listExpresiones.get(1).getNodoArbol();
                    primero.setNodo1(izq);
                    primero.setNodo2(der);
                } else {
                    NodoArbol der = listExpresiones.get(i).getNodoArbol();
                    NodoArbol izq = primero;
                    primero = new NodoArbol("simbolo", null, null, "|");
                    primero.setNodo1(izq);
                    primero.setNodo2(der);
                }
            }
            union = new NodoArbol("simbolo", null, null, ".");
            NodoArbol aceptacion = new NodoArbol("aceptacion", null, null, "#");
            union.setNodo1(primero);
            union.setNodo2(aceptacion);
            
        } else {
            primero = listExpresiones.get(0).getNodoArbol();
            NodoArbol aceptacion = new NodoArbol("aceptacion", null, null, "#");
            union = new NodoArbol("simbolo", null, null, ".");
            union.setNodo1(primero);
            union.setNodo2(aceptacion);
        }
        return union;
    }
    
    
    //agrega el identificador de la expresion regular a cada nodo dentro de su arbol
    public void agregarIdentificadorNodos(ArrayList<MisExpresiones> listExpresiones){
        for (int i = 0; i < listExpresiones.size(); i++) {
            nombrarNodos(listExpresiones.get(i).getNodoArbol(), listExpresiones.get(i).getIdentificador(), listExpresiones.get(i).getPrioridad());
        }
    }
    
    public void nombrarNodos(NodoArbol nodo, String idExpresion, int prioridad){
        if(nodo.getNodo1() == null && nodo.getNodo2() == null){
            nodo.setIdExpresion(idExpresion);
            nodo.setPrioridad(prioridad);
            System.out.println("AGREGADO EL ID: "+nodo.getIdExpresion()+"   CON PRIORIDAD DE NIVEL: "+nodo.getPrioridad());
        } else {
            if(nodo.getNodo1() != null){
                nombrarNodos(nodo.getNodo1(), idExpresion, prioridad);
            }
            if(nodo.getNodo2() != null){
                nombrarNodos(nodo.getNodo2(), idExpresion, prioridad);
            }
        }
    }

}
