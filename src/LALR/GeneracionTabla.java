/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.util.ArrayList;

/**
 *
 * @author luisGonzalez
 */
public class GeneracionTabla {

    private Primeros primeros = new Primeros();
    private Integer posibleCaso = null;
    private ArregloExpresiones arreglos = new ArregloExpresiones();
    private Integer casoUnion = null;

    public void arregloIdEstados(ArrayList<Estados> listEstados) {
        for (int i = 0; i < listEstados.size(); i++) {
            listEstados.get(i).setNoEstado(listEstados.size() - i);
        }
    }

    public void generarCopia(ArrayList<Estados> listGeneral, ArrayList<Estados> listAux2) {
        for (int i = 0; i < listGeneral.size(); i++) {
            listAux2.add(new Estados(listGeneral.get(i)));
            listAux2.get(i).setMisExpresiones(new ArrayList<>());
            listAux2.get(i).setMisCarriles(new ArrayList<>());
            generarCopia2(listGeneral.get(i).getMisExpresiones(), listAux2.get(i).getMisExpresiones());
            generarCopia3(listGeneral.get(i).getMisCarriles(), listAux2.get(i).getMisCarriles());
        }
    }

    public void generarCopia2(ArrayList<Expresiones> listGeneral, ArrayList<Expresiones> listAux) {
        for (int i = 0; i < listGeneral.size(); i++) {
            listAux.add(new Expresiones(listGeneral.get(i)));
        }
    }

    public void generarCopia3(ArrayList<Carril> listGeneral, ArrayList<Carril> listAux) {
        for (int i = 0; i < listGeneral.size(); i++) {
            listAux.add(new Carril(listGeneral.get(i)));
        }
    }

    public void creacionCasos(ArrayList<Estados> listEstados, ArrayList<NodoCaso> listCasos) {
        //Agrega a la lista de estados el estado 0 
        arregloIdEstados(listEstados);
        for (int i = 0; i < listEstados.size(); i++) {
            listEstados.get(i).setMisExpresiones(arreglos.arregloExpresiones(listEstados.get(i).getMisExpresiones()));
        }
        ArrayList<Estados> listAux = new ArrayList<>();
        ArrayList<Expresiones> expInicial = new ArrayList<>();
        expInicial.add(new Expresiones(listEstados.get(listEstados.size() - 1).getIdentificador(), false, null, null, null));
        expInicial.add(new Expresiones("$", true, null, null, null));
        listAux.add(new Estados(0, listEstados.get(listEstados.size() - 1).getIdentificador() + "I", expInicial, null, null));
        for (int i = listEstados.size() - 1; i >= 0; i--) {
            listAux.add(listEstados.get(i));
        }
        int primerCaso = 1;
        
        listCasos.add(new NodoCaso(primerCaso, new ArrayList<>(), new ArrayList<>()));
        buscarNuevosCasos(listAux, listEstados, listCasos);
        for (int i = 0; i < listCasos.size(); i++) {
            String vinculos = "";
            for (int j = 0; j < listCasos.get(i).getListEstados().size(); j++) {
                System.out.println(listCasos.get(i).getListEstados().get(j).getNoEstado() + "     " + listCasos.get(i).getListEstados().get(j).getIdentificador());
                String carriles = "";
                for (int k = 0; k < listCasos.get(i).getListEstados().get(j).getMisCarriles().size(); k++) {
                    carriles += listCasos.get(i).getListEstados().get(j).getMisCarriles().get(k).getId();
                }
                System.out.println("            Carriles   " + carriles);
            }
            System.out.println("--------------------VINCULOS CASO NO. " + listCasos.get(i).getIdCaso());
            for (int k = 0; k < listCasos.get(i).getListVinculos().size(); k++) {
                vinculos += "ID vinculo: " + listCasos.get(i).getListVinculos().get(k).getIdCasoVinculo() + "   objeto referencia: " + listCasos.get(i).getListVinculos().get(k).getVinculo();
            }
            System.out.println(vinculos);
            System.out.println("-----------------------------------------------------------------------");
        }

    }

    //metodo para la creacion dinamica de todos los estados del analizador sintactico
    public void buscarNuevosCasos(ArrayList<Estados> listAux, ArrayList<Estados> listEstados, ArrayList<NodoCaso> listCasos) {
        int totalInicial = listCasos.size();

        for (int i = 0; i < totalInicial; i++) {
            if (!listCasos.get(i).isDerivado()) {
                if (totalInicial == 1) {
                    cerradura(listAux, listEstados.get(listEstados.size() - 1).getIdentificador() + "I", listCasos, null, listCasos.get(i), null);
                }
                posiblesNuevosCasos(listCasos.get(i), listCasos, listAux);
                listCasos.get(i).setDerivado(true);
           }
        }
        int totalFinal = listCasos.size();
        if (totalFinal > totalInicial) {
            buscarNuevosCasos(listAux, listEstados, listCasos);
        }
    }

    public void cerradura(ArrayList<Estados> listOriginal, String id, ArrayList<NodoCaso> listCasos, Estados estadoPadre, NodoCaso casoActual, ArrayList<Expresiones> expAux) {
        ArrayList<Estados> listEstados = new ArrayList<>();
        generarCopia(listOriginal, listEstados);
        for (int i = 0; i < listEstados.size(); i++) {
            if (listEstados.get(i).getIdentificador().equals(id)) {
                //SI ENCUENTRA UN ESTAnuevoDO QUE COINCIDA CON LA COMPARACION DEBE SER AGREGADO AL CASO EN EL QUE SE ESTE ACTUALMENTE
                //Estados nuevo = new Estados(listEstados.get(i).getNoEstado(), listEstados.get(i).getIdentificador(), listEstados.get(i).getMisExpresiones());
                Estados nuevo = new Estados(listEstados.get(i));
                Integer puntoUbicacion = verUbicacionPunto(nuevo.getMisExpresiones());
                if (puntoUbicacion == null) {
                    if (!finTransicion(nuevo.getMisExpresiones())) {
                        if (verificarEstadoLambda(nuevo.getMisExpresiones())) {
                            nuevo.getMisExpresiones().add(new Expresiones("lambda", true, null, null, null));
                            nuevo.getMisExpresiones().get(0).setPuntoFinal(true);
                            ArrayList<Expresiones> expresionAux = creacionExpresionAux(nuevo, 1);
                            nuevo.setMisCarriles(agregarCarriles(listEstados, expAux, estadoPadre));
                            if (!verificarEstados2(nuevo, casoActual)) {
                                casoActual.getListEstados().add(nuevo);
                            } else {
                                break;
                            }
                        } else {
                            int nodo = 0;
                            nuevo.getMisExpresiones().get(nodo).setPunto(true);
                            ArrayList<Expresiones> expresionAux = creacionExpresionAux(nuevo, nodo + 1);
                            nuevo.setMisCarriles(agregarCarriles(listEstados, expAux, estadoPadre));
                            //Si el simbolo donde esta ubicado el punto NO es terminal se procede a hacer cerradura con el
                            if (!verificarEstados2(nuevo, casoActual)) {
                                casoActual.getListEstados().add(nuevo);
                                if (!nuevo.getMisExpresiones().get(nodo).getEsTerminal()) {
                                    cerradura(listOriginal, nuevo.getMisExpresiones().get(nodo).getIdentificador(), listCasos, casoActual.getListEstados().get(casoActual.getListEstados().size() - 1), casoActual, expresionAux);
                                }
                            } else {
                                break;
                            }
                        }
                    }
                } else {
                    //crea los simbolos del carril
                    ArrayList<Expresiones> expresionAux = creacionExpresionAux(nuevo, puntoUbicacion + 1);
                    nuevo.setMisCarriles(agregarCarriles(listEstados, expAux, estadoPadre));
                    if (puntoUbicacion == listEstados.size() - 1) {
                        nuevo.getMisExpresiones().get(puntoUbicacion).setPunto(false);
                        nuevo.getMisExpresiones().get(puntoUbicacion).setPuntoFinal(true);
                        if (!verificarEstados2(nuevo, casoActual)) {
                            casoActual.getListEstados().add(nuevo);
                        } else {
                            break;
                        }
                    } else {
                        nuevo.getMisExpresiones().get(puntoUbicacion).setPunto(false);
                        nuevo.getMisExpresiones().get(puntoUbicacion + 1).setPunto(true);
                        if (!verificarEstados2(nuevo, casoActual)) {
                            casoActual.getListEstados().add(nuevo);
                            if (!nuevo.getMisExpresiones().get(puntoUbicacion + 1).getEsTerminal()) {
                                cerradura(listOriginal, nuevo.getMisExpresiones().get(puntoUbicacion + 1).getIdentificador(), listCasos, nuevo, casoActual, expresionAux);
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        verificarEstadosIguales3(casoActual.getListEstados());
    }

    //metodo para ver si dos producciones pueden ser operadas dentro de un mismo caso (Esto si el simbolo que se quiere derivar es el mismo en ambos)
    public void optimizarProducciones(ArrayList<Estados> listEstados, ArrayList<Estados> estadosCasoPadre, String expresion, Integer ubicacion, NodoCaso casoActual, ArrayList<NodoCaso> listCasos, int noCaso, int y) {
        ArrayList<Estados> listAux = new ArrayList<>();
        generarCopia(estadosCasoPadre, listAux);
        for (int i = 0; i < listAux.size(); i++) {
            if (i != y) {
                Integer ubPosibleEstado = verUbicacionPunto(listAux.get(i).getMisExpresiones());
                if (ubPosibleEstado != null && ubicacion != null) {
                    String idPosibleEstado = listAux.get(i).getMisExpresiones().get(ubPosibleEstado).getIdentificador();
                    if (expresion.equals(idPosibleEstado)) {
                        if (listAux.get(i).getMisExpresiones().get(ubPosibleEstado).getPunto() && estadosCasoPadre.get(y).getMisExpresiones().get(ubicacion).getPunto()) {

                            Estados nuevoIntegrante = listAux.get(i);
                            if (ubPosibleEstado == listAux.get(i).getMisExpresiones().size() - 1) {
                                nuevoIntegrante.getMisExpresiones().get(ubPosibleEstado).setPunto(false);
                                nuevoIntegrante.getMisExpresiones().get(ubPosibleEstado).setPuntoFinal(true);
                                casoActual.getListEstados().add(nuevoIntegrante);

                            } else {
                                nuevoIntegrante.getMisExpresiones().get(ubPosibleEstado).setPunto(false);
                                nuevoIntegrante.getMisExpresiones().get(ubPosibleEstado + 1).setPunto(true);
                                casoActual.getListEstados().add(nuevoIntegrante);

                                if (!listAux.get(i).getMisExpresiones().get(ubPosibleEstado + 1).getEsTerminal()) {
                                    String idDerivado = listAux.get(i).getMisExpresiones().get(ubPosibleEstado + 1).getIdentificador();
                                    cerradura(listEstados, idDerivado, listCasos, nuevoIntegrante, casoActual, null);
                                }
                            }
                            estadosCasoPadre.get(i).setSimplificado(true);
                        }
                    }
                }
            }
        }

    }

    //metodo para comprobar si ya existe un caso con el cual poder vincularnos para no crear un caso nuevo
    public boolean comprobarSimplificacion(NodoCaso actual, ArrayList<NodoCaso> listCasos, Estados posibleEstado, ArrayList<Estados> listEstados, int y) {
        boolean siExiste = false;
        ArrayList<Estados> aux = new ArrayList<>();
        Estados aux2 = posibleEstado;
        aux2.setNoIterador(y);
        aux.add(posibleEstado);
        Integer ubPunto1 = verUbicacionPunto(posibleEstado.getMisExpresiones());
        if (ubPunto1 != null) {
            verificarPosiblesNodos(actual.getListEstados(), y, posibleEstado.getMisExpresiones().get(ubPunto1).getIdentificador(), aux);
        }
        if (aux.size() == 1) {
            for (int i = 0; i < listCasos.size(); i++) {
                if (listCasos.get(i).getListEstados().get(0).getNoEstado() == posibleEstado.getNoEstado()) {
                    boolean vinculacion = comparacionSimbolos(posibleEstado, listCasos.get(i).getListEstados().get(0), listCasos.get(i).getIdCaso(), listCasos);
                    if (vinculacion) {
                        siExiste = true;
                        posibleCaso = listCasos.get(i).getIdCaso();
                        break;
                    }
                }
            }
        } else {
            siExiste = buscarCoincidenciasMultiples(aux, listCasos);
            if (siExiste) {
                posibleCaso = casoUnion;
                for (int i = 0; i < aux.size(); i++) {
                    actual.getListEstados().get(aux.get(i).getNoIterador()).setSimplificado(true);
                }
            }
        }
        return siExiste;
    }

    //cuando se busca un estado que tenga especificas condiciones
    public boolean buscarCoincidenciasMultiples(ArrayList<Estados> aux, ArrayList<NodoCaso> listCasos) {
        boolean encontrado = false;
        for (int i = 0; i < listCasos.size(); i++) {
            boolean encontrado2 = true;
            for (int j = 0; j < aux.size(); j++) {
                Integer ubPunto1 = verUbicacionPunto(aux.get(j).getMisExpresiones());
                boolean prosigue = false;
                for (int k = 0; k < listCasos.get(i).getListEstados().size(); k++) {
                    Integer ubPunto2 = verUbicacionPunto(listCasos.get(i).getListEstados().get(k).getMisExpresiones());
                    if (aux.get(j).getNoEstado() == listCasos.get(i).getListEstados().get(k).getNoEstado()) {
                        if (ubPunto2 == null) {
                            if ((ubPunto1 == listCasos.get(i).getListEstados().get(k).getMisExpresiones().size() - 1)) {
                                prosigue = true;
                                if (comparacionSimbolos2(aux.get(j), listCasos.get(i).getListEstados().get(k), listCasos)) {
                                    prosigue = true;
                                }
                                break;
                            }
                        } else {
                            if (ubPunto2 == ubPunto1 + 1) {
                                if (comparacionSimbolos2(aux.get(j), listCasos.get(i).getListEstados().get(k), listCasos)) {
                                    prosigue = true;
                                }
                                break;
                            }
                        }
                    }
                }
                if (!prosigue) {
                    encontrado2 = false;
                    break;
                }
            }
            if (encontrado2) {
                casoUnion = listCasos.get(i).getIdCaso();
                encontrado = true;
                break;
            }
        }
        return encontrado;
    }

    //verifica si hay algun nodo que pueda ser unido en un arreglo, esto mediante posicion de punto y simbolo analizado
    public void verificarPosiblesNodos(ArrayList<Estados> estadosPadre, int i, String idEstado, ArrayList<Estados> aux) {
        for (int j = 0; j < estadosPadre.size(); j++) {
            if (i != j) {
                Integer ubPunto2 = verUbicacionPunto(estadosPadre.get(j).getMisExpresiones());
                if (ubPunto2 != null) {
                    if (estadosPadre.get(j).getMisExpresiones().get(ubPunto2).getIdentificador().equals(idEstado)) {
                        Estados aux2 = estadosPadre.get(j);
                        aux2.setNoIterador(j);
                        aux.add(aux2);
                    }
                }
            }
        }
    }

    //verifica que el punto del estado inicial de un caso este en la misma posicion que el de otro para una posible vinculacion
    public boolean comparacionUbPuntos(Estados estado1, Estados estado2, int idCaso, ArrayList<NodoCaso> listCasos) {
        boolean mismaPosicion = false;
        Integer ubicacion1 = verUbicacionPunto(estado1.getMisExpresiones());
        Integer ubicacion2 = verUbicacionPunto(estado2.getMisExpresiones());
        if (ubicacion1 != null && ubicacion2 != null) {
            int ub2 = ubicacion1 + 1;
            if (ubicacion2 == ub2) {
                mismaPosicion = true;
            }
        } else {
            Integer ubicacionPunto = verUbicacionPunto(estado1.getMisExpresiones());
            if (estado1.getMisExpresiones().get(estado1.getMisExpresiones().size() - 1).getPunto() != null) {
                if (estado1.getMisExpresiones().get(estado1.getMisExpresiones().size() - 1).getPunto() && estado2.getMisExpresiones().get(estado2.getMisExpresiones().size() - 1).getPuntoFinal()) {
                    mismaPosicion = true;
                }
            }
        }
        return mismaPosicion;
    }

    //verifica que los simbolos del estado inicial de un caso sean iguales a los de otro caso para una posible vinculacion
    public boolean comparacionSimbolos(Estados estado1, Estados estado2, int idCaso, ArrayList<NodoCaso> listCasos) {
        if (estado1.getMisCarriles().size() == estado2.getMisCarriles().size()) {
            boolean todoCorrecto = true;
            for (int i = 0; i < estado1.getMisCarriles().size(); i++) {
                String ID1 = estado1.getMisCarriles().get(i).getId();
                boolean datoEncontrado = false;
                for (int j = 0; j < estado2.getMisCarriles().size(); j++) {
                    String ID2 = estado2.getMisCarriles().get(j).getId();
                    if (ID1.equals(ID2)) {
                        datoEncontrado = true;
                        break;
                    }
                }
                if (!datoEncontrado) {
                    todoCorrecto = false;
                    break;
                }

            }
            if (todoCorrecto) {
                if (!comparacionUbPuntos(estado1, estado2, idCaso, listCasos)) {
                    todoCorrecto = false;
                }
            }
            return todoCorrecto;
        } else {
            return false;
        }
    }

    public boolean comparacionSimbolos2(Estados estado1, Estados estado2, ArrayList<NodoCaso> listCasos) {
        if (estado1.getMisCarriles().size() == estado2.getMisCarriles().size()) {
            boolean todoCorrecto = true;
            for (int i = 0; i < estado1.getMisCarriles().size(); i++) {
                String ID1 = estado1.getMisCarriles().get(i).getId();
                boolean datoEncontrado = false;
                for (int j = 0; j < estado2.getMisCarriles().size(); j++) {
                    String ID2 = estado2.getMisCarriles().get(j).getId();
                    if (ID1.equals(ID2)) {
                        datoEncontrado = true;
                        break;
                    }
                }
                if (!datoEncontrado) {
                    todoCorrecto = false;
                    break;
                }

            }
            return todoCorrecto;
        } else {
            return false;
        }

    }
   
    public void posiblesNuevosCasos(NodoCaso actual, ArrayList<NodoCaso> listCasos, ArrayList<Estados> listEstados) {
        ArrayList<Estados> listAux = new ArrayList<>();
        generarCopia(actual.getListEstados(), listAux);
        for (int i = 0; i < listAux.size(); i++) {
            if (!actual.getListEstados().get(i).isSimplificado()) {
                if (!finTransicion(listAux.get(i).getMisExpresiones())) {
                    //aqui es donde deberia pasar el punto a una nueva posicion hacia la derecha
                    if (comprobarSimplificacion(actual, listCasos, listAux.get(i), listEstados, i)) {
                        //SI ENTRA AQUI SIGNIFICA QUE HAY UN CASO CON EL CUAL ES POSIBLE VINCULARSE EN LUGAR DE CREAR UNO NUEVO
                        Vinculos vinculo = new Vinculos();
                        Integer ubicacionPunto = verUbicacionPunto(listAux.get(i).getMisExpresiones());
                        vinculo.setIdCasoVinculo(actual.getIdCaso());
                        vinculo.setVinculo(listAux.get(i).getMisExpresiones().get(ubicacionPunto).getIdentificador());
                        listCasos.get(posibleCaso - 1).getListVinculos().add(vinculo);
                    } else {
                        posibleCaso = null;
                        //Agrega el vinculo con el nuevo caso. 
                        Integer ubicacionPunto = verUbicacionPunto(listAux.get(i).getMisExpresiones());
                        if (ubicacionPunto != null) {
                            Estados nuevoIntegrante = listAux.get(i);
                            Vinculos vinculo = new Vinculos();
                            if (ubicacionPunto == listAux.get(i).getMisExpresiones().size() - 1) {
                                if (!nuevoIntegrante.getMisExpresiones().get(ubicacionPunto).getIdentificador().equals("$")) {
                                    listCasos.add(new NodoCaso(listCasos.get(listCasos.size() - 1).getIdCaso() + 1, new ArrayList<>(), new ArrayList<>()));
                                    vinculo.setIdCasoVinculo(actual.getIdCaso());
                                    vinculo.setVinculo(listAux.get(i).getMisExpresiones().get(ubicacionPunto).getIdentificador());
                                    listCasos.get(listCasos.size() - 1).getListVinculos().add(vinculo);
                                    nuevoIntegrante.getMisExpresiones().get(ubicacionPunto).setPunto(false);
                                    nuevoIntegrante.getMisExpresiones().get(ubicacionPunto).setPuntoFinal(true);
                                    listCasos.get(listCasos.size() - 1).getListEstados().add(nuevoIntegrante);
                                    optimizarProducciones(listEstados, actual.getListEstados(), nuevoIntegrante.getMisExpresiones().get(ubicacionPunto).getIdentificador(), ubicacionPunto, listCasos.get(listCasos.size() - 1), listCasos, nuevoIntegrante.getNoEstado(), i);
                                }
                            } else {
                                listCasos.add(new NodoCaso(listCasos.get(listCasos.size() - 1).getIdCaso() + 1, new ArrayList<>(), new ArrayList<>()));
                                vinculo.setIdCasoVinculo(actual.getIdCaso());
                                vinculo.setVinculo(listAux.get(i).getMisExpresiones().get(ubicacionPunto).getIdentificador());
                                listCasos.get(listCasos.size() - 1).getListVinculos().add(vinculo);
                                nuevoIntegrante.getMisExpresiones().get(ubicacionPunto).setPunto(false);
                                nuevoIntegrante.getMisExpresiones().get(ubicacionPunto + 1).setPunto(true);
                                listCasos.get(listCasos.size() - 1).getListEstados().add(nuevoIntegrante);
                                optimizarProducciones(listEstados, actual.getListEstados(), nuevoIntegrante.getMisExpresiones().get(ubicacionPunto).getIdentificador(), ubicacionPunto, listCasos.get(listCasos.size() - 1), listCasos, nuevoIntegrante.getNoEstado(), i);
                                if (!nuevoIntegrante.getMisExpresiones().get(ubicacionPunto + 1).isEsTerminal()) {
                                    if (nuevoIntegrante.getMisExpresiones().size() - 1 < ubicacionPunto + 2) {
                                        String nombre = nuevoIntegrante.getMisExpresiones().get(ubicacionPunto + 1).getIdentificador();
                                        cerradura(listEstados, nombre, listCasos, nuevoIntegrante, listCasos.get(listCasos.size() - 1), null);
                                    } else {
                                        ArrayList<Expresiones> expresionAux = creacionExpresionAux(nuevoIntegrante, ubicacionPunto + 2);
                                        String nombre = nuevoIntegrante.getMisExpresiones().get(ubicacionPunto + 1).getIdentificador();
                                        cerradura(listEstados, nombre, listCasos, nuevoIntegrante, listCasos.get(listCasos.size() - 1), expresionAux);
                                    }

                                }

                            }

                        }

                    }
                }
            }
        }
        for (int i = 0; i < listAux.size(); i++) {
            listAux.get(i).setSimplificado(false);
        }
    }

    //metodo para verificar si dentro de un caso hay dos estados repetidos para asi unirlos
    public Integer verificarEstadosIguales(ArrayList<Estados> listEstados) {
        Integer estadosIguales = null;
        boolean encontrado = false;
        Integer nodoGuia = null;
        for (int i = 0; i < listEstados.size(); i++) {
            Integer ubPunto1 = verUbicacionPunto(listEstados.get(i).getMisExpresiones());
            int noEstado = listEstados.get(i).getNoEstado();
            for (int j = 0; j < listEstados.size(); j++) {
                Integer ubPunto2 = verUbicacionPunto(listEstados.get(j).getMisExpresiones());
                if (j != i) {
                    if (ubPunto1 != null && ubPunto2 != null) {
                        if (ubPunto1 == ubPunto2) {
                            if (listEstados.get(j).getNoEstado() == noEstado) {
                                estadosIguales = noEstado;
                                nodoGuia = i;
                                encontrado = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (encontrado) {
                break;
            }
        }
        //aqui debe ir el metodo para unificacion de metodos
        if (encontrado) {
            Estados nuevo = listEstados.get(nodoGuia);
            nuevo.setMisCarriles(unificarCarriles(estadosIguales, listEstados));
            eliminarNodosCoincidencia(listEstados, estadosIguales);
            listEstados.add(nuevo);
            verificarEstadosIguales(listEstados);
        }

        return estadosIguales;
    }

    public boolean verificarEstados2(Estados actual, NodoCaso casoActual) {
        boolean yaExiste = false;
        for (int i = 0; i < casoActual.getListEstados().size(); i++) {
            if (actual.getNoEstado() == casoActual.getListEstados().get(i).getNoEstado()) {
                Integer ubPunto1 = verUbicacionPunto(actual.getMisExpresiones());
                Integer ubPunto2 = verUbicacionPunto(casoActual.getListEstados().get(i).getMisExpresiones());
                if (ubPunto1 != null && ubPunto2 != null) {
                    if (ubPunto1 == ubPunto2) {
                        boolean siEstan = true;
                        for (int j = 0; j < actual.getMisCarriles().size(); j++) {
                            String id = actual.getMisCarriles().get(j).getId();
                            boolean encontrado = false;
                            for (int k = 0; k < casoActual.getListEstados().get(i).getMisCarriles().size(); k++) {
                                String id2 = casoActual.getListEstados().get(i).getMisCarriles().get(k).getId();
                                if (id == id2) {
                                    encontrado = true;
                                    break;
                                }
                            }
                            if (!encontrado) {
                                siEstan = false;
                                break;
                            }
                        }
                        if (siEstan) {
                            yaExiste = true;
                            break;
                        }

                    }
                }
            }
        }
        return yaExiste;
    }

    //metodo para unificar los carriles de dos diferentes producciones
    public ArrayList<Carril> unificarCarriles(int noEstado, ArrayList<Estados> listEstados) {
        ArrayList<Estados> listIguales = new ArrayList<>();
        for (int i = 0; i < listEstados.size(); i++) {
            if (listEstados.get(i).getNoEstado() == noEstado) {
                listIguales.add(listEstados.get(i));
            }
        }
        ArrayList<Carril> reemplazante = new ArrayList<>();
        for (int i = 0; i < listIguales.size(); i++) {

            for (int j = 0; j < listIguales.get(i).getMisCarriles().size(); j++) {
                String id = listIguales.get(i).getMisCarriles().get(j).getId();
                if (!verificarExistenciaCarril(id, reemplazante)) {
                    reemplazante.add(listIguales.get(i).getMisCarriles().get(j));
                }
            }
        }
        return reemplazante;
    }

    public void eliminarNodosCoincidencia(ArrayList<Estados> listEstados, int noEstado) {
        for (int i = 0; i < listEstados.size(); i++) {
            if (listEstados.get(i).getNoEstado() == noEstado) {
                listEstados.remove(i);
                i--;
            }
        }
    }

    public Boolean verificarExistenciaCarril(String id, ArrayList<Carril> reemplazante) {
        Boolean yaExiste = false;
        for (int i = 0; i < reemplazante.size(); i++) {
            if (reemplazante.get(i).getId().equals(id)) {
                yaExiste = true;
                break;
            }
        }
        return yaExiste;
    }

    //metodo para crear la expresion auxiliar que ayuda a encontrar los PRIMEROS para un carril
    public ArrayList<Expresiones> creacionExpresionAux(Estados estadoActual, int iterador) {
        ArrayList<Expresiones> listAux = new ArrayList<>();
        for (int i = iterador; i < estadoActual.getMisExpresiones().size(); i++) {
            listAux.add(estadoActual.getMisExpresiones().get(i));
        }
        return listAux;
    }

    public ArrayList<Carril> agregarCarriles(ArrayList<Estados> listEstados, ArrayList<Expresiones> expresionAux, Estados estadoPadre) {
        ArrayList<Carril> listCarril = new ArrayList<>();
        if (expresionAux == null) {
            if (estadoPadre == null) {
                listCarril.add(new Carril("question_mark", "?"));

            } else {
                if (estadoPadre.getMisCarriles().get(0) == null) {
                    listCarril.add(new Carril("question_mark", "?"));

                } else {
                    for (int j = 0; j < estadoPadre.getMisCarriles().size(); j++) {
                        listCarril.add(estadoPadre.getMisCarriles().get(j));
                    }

                }
            }
        } else {
            if (!expresionAux.isEmpty()) {
                for (int i = 0; i < expresionAux.size(); i++) {
                    if (i < expresionAux.size() - 1) {
                        if (!expresionAux.get(i).getEsTerminal()) {
                            primeros.calculoPrimeros(listEstados, expresionAux.get(i).getIdentificador(), listCarril, 0);
                            if (!primeros.verificarEstadoLambda(listEstados, expresionAux.get(i).getIdentificador())) {
                                break;
                            }
                        } else {
                            listCarril.add(new Carril(expresionAux.get(i).getIdentificador(), expresionAux.get(i).getMisCaracteres()));
                            break;
                        }
                    } else {
                        if (!expresionAux.get(i).getEsTerminal()) {
                            primeros.calculoPrimeros(listEstados, expresionAux.get(i).getIdentificador(), listCarril, 0);
                            if (!primeros.verificarEstadoLambda(listEstados, expresionAux.get(i).getIdentificador())) {
                                break;
                            } else {
                                for (int j = 0; j < estadoPadre.getMisCarriles().size(); j++) {
                                    listCarril.add(estadoPadre.getMisCarriles().get(j));
                                }
                            }
                        } else {
                            listCarril.add(new Carril(expresionAux.get(i).getIdentificador(), expresionAux.get(i).getMisCaracteres()));
                            break;
                        }
                    }
                }

            } else {
                for (int i = 0; i < estadoPadre.getMisCarriles().size(); i++) {
                    listCarril.add(estadoPadre.getMisCarriles().get(i));
                }
            }
        }
        return listCarril;
    }

    public Integer verUbicacionPunto(ArrayList<Expresiones> listExpresiones) {
        Integer ubicacion = null;
        for (int i = 0; i < listExpresiones.size(); i++) {
            if (listExpresiones.get(i).getPunto() != null) {
                if (listExpresiones.get(i).getPunto()) {
                    ubicacion = i;
                }
            }
        }
        return ubicacion;
    }

    public boolean finTransicion(ArrayList<Expresiones> listExpresiones) {
        boolean fin = false;
        if (!listExpresiones.isEmpty()) {
            if (listExpresiones.get(listExpresiones.size() - 1).getPuntoFinal()) {
                fin = true;
            }
        }
        return fin;
    }

    public boolean verificarEstadoLambda(ArrayList<Expresiones> listExpresiones) {
        return listExpresiones.isEmpty();
    }

    public void verificarEstadosIguales2(ArrayList<Estados> listEstados) {
        Integer nodo1 = null, nodo2 = null;
        boolean encontrados = false;
        for (int i = 0; i < listEstados.size(); i++) {
            Integer ubPunto1 = verUbicacionPunto(listEstados.get(i).getMisExpresiones());
            for (int j = 0; j < listEstados.size(); j++) {
                Integer ubPunto2 = verUbicacionPunto(listEstados.get(j).getMisExpresiones());
                if (i != j) {
                    if (listEstados.get(i).getNoEstado() == listEstados.get(j).getNoEstado()) {
                        if (ubPunto1 != null && ubPunto2 != null) {
                            if (ubPunto1 == ubPunto2) {
                                nodo1 = i;
                                nodo2 = j;
                                encontrados = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (encontrados) {
            Estados nuevo = listEstados.get(nodo1);
            nuevo.setMisCarriles(new ArrayList<>());
            nuevo.setMisCarriles(devolverCarriles(nodo1, nodo2, listEstados));
            listEstados.set(nodo1, nuevo);
            int nodoAux = nodo2;
            listEstados.remove(nodoAux);
            verificarEstadosIguales2(listEstados);
        }
    }

    public void verificarEstadosIguales3(ArrayList<Estados> listEstados) {
        for (int i = 0; i < listEstados.size(); i++) {
            if (!listEstados.get(i).isMarcado()) {
                ArrayList<Integer> list = new ArrayList<>();
                Integer ubPunto1 = verUbicacionPunto(listEstados.get(i).getMisExpresiones());
                list.add(i);
                for (int j = 0; j < listEstados.size(); j++) {
                    Integer ubPunto2 = verUbicacionPunto(listEstados.get(j).getMisExpresiones());
                    if (i != j) {
                        if (listEstados.get(i).getNoEstado() == listEstados.get(j).getNoEstado()) {
                            if (ubPunto1 != null && ubPunto2 != null) {
                                if (ubPunto1 == ubPunto2) {
                                    list.add(j);
                                }
                            }
                        }
                    }
                }
                if (list.size() > 1) {
                    ArrayList<Carril> reemplazo = new ArrayList<>();
                    for (int j = 0; j < list.size(); j++) {
                        if (!listEstados.get(j).isMarcado()) {
                            for (int k = 0; k < listEstados.get(list.get(j)).getMisCarriles().size(); k++) {
                                String id = listEstados.get(list.get(j)).getMisCarriles().get(k).getId();
                                boolean encontrado = false;
                                for (int l = 0; l < reemplazo.size(); l++) {
                                    if (id.equals(reemplazo.get(l).getId())) {
                                        encontrado = true;
                                        break;
                                    }
                                }
                                if (!encontrado) {
                                    reemplazo.add(listEstados.get(list.get(j)).getMisCarriles().get(k));
                                }

                            }
                        }
                    }
                    Estados nuevo = listEstados.get(list.get(0));
                    nuevo.setMisCarriles(reemplazo);
                    for (int j = 0; j < listEstados.size(); j++) {
                        for (int k = 1; k < list.size(); k++) {
                            if (j == list.get(k)) {
                                listEstados.get(j).setMarcado(true);
                            }
                        }
                    }

                }
            }

        }
        for (int i = 0; i < listEstados.size(); i++) {
            if (listEstados.get(i).isMarcado()) {
                listEstados.remove(i);
                i--;
            }

        }
    }

    public ArrayList<Carril> devolverCarriles(int nodo1, int nodo2, ArrayList<Estados> listEstados) {
        ArrayList<Carril> reemplazo = new ArrayList<>();
        for (int i = 0; i < listEstados.get(nodo1).getMisCarriles().size(); i++) {
            reemplazo.add(listEstados.get(nodo1).getMisCarriles().get(i));
        }
        for (int i = 0; i < listEstados.get(nodo2).getMisCarriles().size(); i++) {
            String id = listEstados.get(nodo2).getMisCarriles().get(i).getId();
            boolean encontrado = false;
            for (int j = 0; j < reemplazo.size(); j++) {
                if (id.equals(reemplazo.get(j).getId())) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                reemplazo.add(listEstados.get(nodo2).getMisCarriles().get(i));
            }
        }
        return reemplazo;

    }

}
