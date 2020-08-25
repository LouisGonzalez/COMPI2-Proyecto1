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
        expInicial.add(new Expresiones(listEstados.get(listEstados.size() - 1).getIdentificador(), false, null, null));
        expInicial.add(new Expresiones("$", true, null, null));
        listAux.add(new Estados(0, listEstados.get(listEstados.size() - 1).getIdentificador() + "I", expInicial));
        for (int i = listEstados.size() - 1; i >= 0; i--) {
            listAux.add(listEstados.get(i));
            // listAux.get(i).setMisExpresiones(arreglos.arregloExpresiones(listEstados.get(i).getMisExpresiones()));
        }
        int primerCaso = 1;
        for (int i = 0; i < listAux.size(); i++) {
            System.out.println(listAux.get(i).getNoEstado() + "           NO ESTADO PE    " + listAux.get(i).getIdentificador());
            for (int j = 0; j < listAux.get(i).getMisExpresiones().size(); j++) {
                //         System.out.println("                expresion: "+listAux.get(i).getMisExpresiones().get(j).getIdentificador());
            }
        }
        listCasos.add(new NodoCaso(primerCaso, new ArrayList<>(), new ArrayList<>()));
        // cerradura(listAux, listEstados.get(listEstados.size() - 1).getIdentificador() + "I", listCasos, null, listCasos.get(listCasos.size() - 1), null);

        /*        for (int i = 0; i < listCasos.get(listCasos.size() - 1).getListEstados().size(); i++) {
            Integer ubicacion = verUbicacionPunto(listCasos.get(listCasos.size() - 1).getListEstados().get(i).getMisExpresiones());
            System.out.println("el punto en la produccion " + listCasos.get(listCasos.size() - 1).getListEstados().get(i).getIdentificador() + "  esta en la posicion   " + ubicacion);

        }*/
        //posiblesNuevosCasos(listCasos.get(listCasos.size() - 1), listCasos, listAux);
        buscarNuevosCasos(listAux, listEstados, listCasos);
        for (int i = 0; i < listCasos.get(4).getListEstados().size(); i++) {
            Integer ubicacion = verUbicacionPunto(listCasos.get(4).getListEstados().get(i).getMisExpresiones());
            System.out.println("el punto en la produccion " + listCasos.get(4).getListEstados().get(i).getIdentificador() + "  esta en la posicion   " + ubicacion);
        }
        //         System.out.println("punto final:            "+ listCasos.get(4).getListEstados().get(0).getMisExpresiones().get(listCasos.get(4).getListEstados().get(0).getMisExpresiones().size() - 1).getPuntoFinal()+"      PUTNO FIANL");
    }

    //metodo para la creacion dinamica de todos los estados del analizador sintactico
    public void buscarNuevosCasos(ArrayList<Estados> listAux, ArrayList<Estados> listEstados, ArrayList<NodoCaso> listCasos) {
        //cerradura(listAux, listEstados.get(listEstados.size() - 1).getIdentificador() + "I", listCasos, null, listCasos.get(listCasos.size()-1), null);
        //      posiblesNuevosCasos(listCasos.get(listCasos.size() - 1), listCasos, listAux);
        int totalInicial = listCasos.size();

        for (int i = 0; i < totalInicial; i++) {
            if (!listCasos.get(i).isDerivado()) {
                if (totalInicial == 1) {
                    cerradura(listAux, listEstados.get(listEstados.size() - 1).getIdentificador() + "I", listCasos, null, listCasos.get(i), null);
                } else {
                    //   cerradura(listAux, listCasos.get(i).getListEstados().get(0).getIdentificador(), listCasos, null, listCasos.get(i), null);
                }
                posiblesNuevosCasos(listCasos.get(i), listCasos, listAux);
                listCasos.get(i).setDerivado(true);
            }
        }
        int totalFinal = listCasos.size();
        if (totalFinal > totalInicial) {
            buscarNuevosCasos(listAux, listEstados, listCasos);
        }
        //   posiblesNuevosCasos(listCasos.get(5), listCasos, listEstados);
    }

    //COMPLETADO ->>>>>        
    //FALTA AGREGAR LA VINCULACION QUE SE HACE CON LA LINEA Y EL SIMBOLO CON EL QUE SE HACE
    //COMPLETADO ->>>>>        xEl estado NUEVO de cerradura debe ser agregado dentro del caso que estamos manejando
    //COMPLETADO ->>>>>        METODO PARA REVISAR TAMBIEN SI SE PUEDE OPTIMIZAR UN CAMINO YENDO HACIA UN ESTADO YA CREADO EN LUGAR DE CREAR UNO NUEVO
    //CREO QUE SE DEBE CAMBIAR LA REFERENCIA DE listEstados EN EL METODO CERRADURA
    //COMPLETADO ->>>>         donde se meten los estados que se vayan generando por las cerraduras
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
                            nuevo.getMisExpresiones().add(new Expresiones("lambda", true, null, null));
                            nuevo.getMisExpresiones().get(0).setPunto(true);
                            ArrayList<Expresiones> expresionAux = creacionExpresionAux(nuevo, 1);
                            nuevo.setMisCarriles(agregarCarriles(listEstados, expresionAux, estadoPadre));
                            casoActual.getListEstados().add(nuevo);
                        } else {
                            int nodo = 0;
                            nuevo.getMisExpresiones().get(nodo).setPunto(true);
                            ArrayList<Expresiones> expresionAux = creacionExpresionAux(nuevo, nodo + 1);
                            nuevo.setMisCarriles(agregarCarriles(listEstados, expAux, estadoPadre));
                            //Si el simbolo donde esta ubicado el punto NO es terminal se procede a hacer cerradura con el
                            casoActual.getListEstados().add(nuevo);
                            if (!nuevo.getMisExpresiones().get(nodo).getEsTerminal()) {
                                System.out.println(nuevo.getMisExpresiones().get(nodo).getIdentificador() + " NO ES TERMINAL, PROCEDO A DERIVAR");
                                cerradura(listOriginal, nuevo.getMisExpresiones().get(nodo).getIdentificador(), listCasos, casoActual.getListEstados().get(casoActual.getListEstados().size() - 1), casoActual, expresionAux);
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
                        casoActual.getListEstados().add(nuevo);
                        /*if (!nuevo.getMisExpresiones().get(puntoUbicacion).getEsTerminal()) {
                            cerradura(listOriginal, nuevo.getMisExpresiones().get(puntoUbicacion).getIdentificador(), listCasos, nuevo, casoActual);
                        }*/
                    } else {
                        nuevo.getMisExpresiones().get(puntoUbicacion).setPunto(false);
                        nuevo.getMisExpresiones().get(puntoUbicacion + 1).setPunto(true);
                        casoActual.getListEstados().add(nuevo);
                        if (!nuevo.getMisExpresiones().get(puntoUbicacion + 1).getEsTerminal()) {
                            cerradura(listOriginal, nuevo.getMisExpresiones().get(puntoUbicacion + 1).getIdentificador(), listCasos, nuevo, casoActual, expresionAux);
                        }
                    }
                }
            }
        }
        verificarEstadosIguales(casoActual.getListEstados());
        //AQUI VA LA GENERACION DE NUEVOS CASOS PUES
        //posiblesNuevosCasos(casoActual, listCasos, listOriginal);
    }

    //metodo para ver si dos producciones pueden simplificarse en un solo caso (Esto es al final- PENDIENTE)
    public void ddddd() {
    }

    //metodo para ver si dos producciones pueden ser operadas dentro de un mismo caso (Esto si el simbolo que se quiere derivar es el mismo en ambos)
    public void optimizarProducciones(ArrayList<Estados> listEstados, ArrayList<Estados> estadosCasoPadre, String expresion, Integer ubicacion, NodoCaso casoActual, ArrayList<NodoCaso> listCasos, int noCaso, int y) {

        ArrayList<Estados> listAux = new ArrayList<>();
        generarCopia(estadosCasoPadre, listAux);
        for (int i = 0; i < listAux.size(); i++) {
            if (listAux.get(i).getNoEstado() != noCaso) {
                Integer ubPosibleEstado = verUbicacionPunto(listAux.get(i).getMisExpresiones());
                if (ubPosibleEstado != null && ubicacion != null) {
                    System.out.println(ubPosibleEstado + "            " + ubicacion);
                    //    if (ubPosibleEstado == ubicacion) {
                    String idPosibleEstado = listAux.get(i).getMisExpresiones().get(ubPosibleEstado).getIdentificador();
                    //  System.out.println(idPosibleEstado + "            " + expresion);

                    if (expresion.equals(idPosibleEstado)) {
                        if (listAux.get(i).getMisExpresiones().get(ubPosibleEstado).getPunto() && estadosCasoPadre.get(y).getMisExpresiones().get(ubicacion).getPunto()) {

                            Estados nuevoIntegrante = listAux.get(i);
                            if (ubPosibleEstado == listAux.get(i).getMisExpresiones().size() - 1) {
                                nuevoIntegrante.getMisExpresiones().get(ubPosibleEstado).setPunto(false);
                                nuevoIntegrante.getMisExpresiones().get(ubPosibleEstado).setPuntoFinal(true);
                                casoActual.getListEstados().add(nuevoIntegrante);

                                /* if (!estadosCasoPadre.get(i).getMisExpresiones().get(ubPosibleEstado).getEsTerminal()) {
                                    String idDerivado = estadosCasoPadre.get(i).getMisExpresiones().get(ubPosibleEstado).getIdentificador();
                                    cerradura(listEstados, idDerivado, listCasos, estadosCasoPadre.get(noCaso), casoActual);
                                }*/
                            } else {
                                nuevoIntegrante.getMisExpresiones().get(ubPosibleEstado).setPunto(false);
                                nuevoIntegrante.getMisExpresiones().get(ubPosibleEstado + 1).setPunto(true);
                                casoActual.getListEstados().add(nuevoIntegrante);

                                if (!listAux.get(i).getMisExpresiones().get(ubPosibleEstado + 1).getEsTerminal()) {
                                    String idDerivado = listAux.get(i).getMisExpresiones().get(ubPosibleEstado + 1).getIdentificador();
                                    System.out.println("SE HA SIMPLIFICADO CON EXITOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                                    cerradura(listEstados, idDerivado, listCasos, listAux.get(noCaso), casoActual, null);
                                }
                            }

                            estadosCasoPadre.get(i).setSimplificado(true);
                        }
                    }
                }
                //  }
            }
        }

    }

    //metodo para comprobar si ya existe un caso con el cual poder vincularnos para no crear un caso nuevo
    public boolean comprobarSimplificacion(NodoCaso actual, ArrayList<NodoCaso> listCasos, Estados posibleEstado) {
        boolean siExiste = false;
        for (int i = 0; i < listCasos.size(); i++) {
            //  if (listCasos.get(i).getIdCaso() != actual.getIdCaso()) {
            if (listCasos.get(i).getListEstados().get(0).getNoEstado() == posibleEstado.getNoEstado()) {
                boolean vinculacion = comparacionSimbolos(posibleEstado, listCasos.get(i).getListEstados().get(0), listCasos.get(i).getIdCaso(), listCasos);
                if (vinculacion) {
                    siExiste = true;
                    System.out.println("LA VINCULACION ES REAL D:");
                    System.out.println(listCasos.size());
                    System.out.println(listCasos.get(i).getIdCaso());
                    posibleCaso = listCasos.get(i).getIdCaso();
                    break;
                }
            }
            //}
        }
        return siExiste;
    }

    //verifica que el punto del estado inicial de un caso este en la misma posicion que el de otro para una posible vinculacion
    public boolean comparacionUbPuntos(Estados estado1, Estados estado2, int idCaso, ArrayList<NodoCaso> listCasos) {
        boolean mismaPosicion = false;
        Integer ubicacion1 = verUbicacionPunto(estado1.getMisExpresiones());
        Integer ubicacion2 = verUbicacionPunto(estado2.getMisExpresiones());
        System.out.println(ubicacion1);
        System.out.println(ubicacion2);
        if (ubicacion1 != null && ubicacion2 != null) {
            System.out.println("CUARTA FASE DE OPTIMIZACION VAS CON TODO PUTO AMO");
            int ub2 = ubicacion1 + 1;
            if (ubicacion2 == ub2) {
                mismaPosicion = true;
            }
        } else {
            System.out.println(estado1.getNoEstado() + "          no estado estado1");
            System.out.println(estado2.getNoEstado() + "          no estado estado2");
            System.out.println(estado1.getMisExpresiones().get(0).getIdentificador() + "          nombre estado1");
            System.out.println(estado2.getMisExpresiones().get(0).getIdentificador() + "          nombre estado1");
            Integer ubicacionPunto = verUbicacionPunto(estado1.getMisExpresiones());
            System.out.println(ubicacionPunto + "         ESTA ES LA UBICACION VERDADERA DEL PUNTO AL QUE SE QUIERE OPTIMIZAR");
            System.out.println(estado1.getMisExpresiones().size());
            System.out.println(estado1.getMisExpresiones().get(0).getPunto() + ":0");
            //System.out.println(estado1.getMisExpresiones().get(estado1.getMisExpresiones().size()-1).getPuntoFinal());
            System.out.println(estado2.getMisExpresiones().get(estado2.getMisExpresiones().size() - 1).getPuntoFinal());
            System.out.println(idCaso + "         ID CASO QUE ESTOY REVISANDO");
            System.out.println("punto final:            " + listCasos.get(4).getListEstados().get(0).getMisExpresiones().get(listCasos.get(4).getListEstados().get(0).getMisExpresiones().size() - 1).getPuntoFinal() + "      PUTNO FIANL");
            System.out.println("idCasoooo:            " + listCasos.get(4).getIdCaso());

            if (estado1.getMisExpresiones().get(estado1.getMisExpresiones().size() - 1).getPunto() != null) {
                if (estado1.getMisExpresiones().get(estado1.getMisExpresiones().size() - 1).getPunto() && estado2.getMisExpresiones().get(estado2.getMisExpresiones().size() - 1).getPuntoFinal()) {
                    System.out.println("ENTRE A LA QUINTA FASE LOOOOOOOL");
                    mismaPosicion = true;
                }
            }
        }
        return mismaPosicion;
    }

    //verifica que los simbolos del estado inicial de un caso sean iguales a los de otro caso para una posible vinculacion
    public boolean comparacionSimbolos(Estados estado1, Estados estado2, int idCaso, ArrayList<NodoCaso> listCasos) {
        if (estado1.getMisCarriles().size() == estado2.getMisCarriles().size()) {
            System.out.println("ENTRO A LA SEGUNDA FASE DE OPTIMIZAFCION SUPUTAMDRE CRAAAACK    ");
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
                System.out.println("ENTRE A LA TERCERA FASE DE OPTIMMIZCION ALP SOS UN GRANDE ");
                if (!comparacionUbPuntos(estado1, estado2, idCaso, listCasos)) {
                    todoCorrecto = false;
                }
            }
            return todoCorrecto;
        } else {
            return false;
        }
    }

    //YA ENCONTRE EL PROBLEMA (TENGO QUE PASAR CADA PRODUCCION DE LOS ESTADOS DE CUENTA REGRESIVA A CUENTA NORMAL PARA QUE ASI NO HAYAN CONFUSIONES, ESTA ONDA SI FUNCIONA O BUENO ESO CREO YO ;'V  )
    //metodo para crear nuevos casos provenientes de otro
    public void posiblesNuevosCasos(NodoCaso actual, ArrayList<NodoCaso> listCasos, ArrayList<Estados> listEstados) {
        ArrayList<Estados> listAux = new ArrayList<>();
        generarCopia(actual.getListEstados(), listAux);
        for (int i = 0; i < listAux.size(); i++) {

            if (!actual.getListEstados().get(i).isSimplificado()) {

                if (!finTransicion(listAux.get(i).getMisExpresiones())) {

                    //aqui es donde deberia pasar el punto a una nueva posicion hacia la derecha
                    if (comprobarSimplificacion(actual, listCasos, listAux.get(i))) {

                        //SI ENTRA AQUI SIGNIFICA QUE HAY UN CASO CON EL CUAL ES POSIBLE VINCULARSE EN LUGAR DE CREAR UNO NUEVO
                        Vinculos vinculo = new Vinculos();
                        Integer ubicacionPunto = verUbicacionPunto(listAux.get(i).getMisExpresiones());
                        vinculo.setIdCasoVinculo(actual.getIdCaso());
                        vinculo.setVinculo(listAux.get(i).getMisExpresiones().get(ubicacionPunto).getIdentificador());
                        listCasos.get(posibleCaso - 1).getListVinculos().add(vinculo);
                        System.out.println("ENTRO A AQUI A SIMPLIFICAR LA VIDA DE LAS PERSOANAS");
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

                                    //       escribirDatosOriginales(listOriginal, listEstados);
                                    optimizarProducciones(listEstados, actual.getListEstados(), nuevoIntegrante.getMisExpresiones().get(ubicacionPunto).getIdentificador(), ubicacionPunto, listCasos.get(listCasos.size() - 1), listCasos, nuevoIntegrante.getNoEstado(), i);
                                }
                                /*     if (!nuevoIntegrante.getMisExpresiones().get(ubicacionPunto).isEsTerminal()) {
                                    String nombre = nuevoIntegrante.getMisExpresiones().get(ubicacionPunto).getIdentificador();
                                    cerradura(listEstados, nombre, listCasos, null, listCasos.get(listCasos.size() - 1));
                                }*/
                            } else {
                                listCasos.add(new NodoCaso(listCasos.get(listCasos.size() - 1).getIdCaso() + 1, new ArrayList<>(), new ArrayList<>()));
                                vinculo.setIdCasoVinculo(actual.getIdCaso());
                                vinculo.setVinculo(listAux.get(i).getMisExpresiones().get(ubicacionPunto).getIdentificador());
                                listCasos.get(listCasos.size() - 1).getListVinculos().add(vinculo);
                                nuevoIntegrante.getMisExpresiones().get(ubicacionPunto).setPunto(false);
                                nuevoIntegrante.getMisExpresiones().get(ubicacionPunto + 1).setPunto(true);
                                listCasos.get(listCasos.size() - 1).getListEstados().add(nuevoIntegrante);
                                optimizarProducciones(listEstados, actual.getListEstados(), nuevoIntegrante.getMisExpresiones().get(ubicacionPunto).getIdentificador(), ubicacionPunto, listCasos.get(listCasos.size() - 1), listCasos, nuevoIntegrante.getNoEstado(), i);

                                //        escribirDatosOriginales(listOriginal, listEstados);
                                if (!nuevoIntegrante.getMisExpresiones().get(ubicacionPunto + 1).isEsTerminal()) {

                                    if (nuevoIntegrante.getMisExpresiones().size() - 1 < ubicacionPunto + 2) {
                                        String nombre = nuevoIntegrante.getMisExpresiones().get(ubicacionPunto + 1).getIdentificador();
                                        //aqui es donde debe ir la list aux?    ?
                                        cerradura(listEstados, nombre, listCasos, nuevoIntegrante, listCasos.get(listCasos.size() - 1), null);

                                    } else {
                                        ArrayList<Expresiones> expresionAux = creacionExpresionAux(nuevoIntegrante, ubicacionPunto + 2);
                                        String nombre = nuevoIntegrante.getMisExpresiones().get(ubicacionPunto + 1).getIdentificador();
                                        //aqui es donde debe ir la list aux?    ?
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

}
