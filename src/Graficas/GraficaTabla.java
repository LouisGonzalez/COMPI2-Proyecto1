/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficas;

import LALR.NodoCaso;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pollitos.NodoTabla;
import pollitos.Simbolos;

/**
 *
 * @author luisGonzalez
 */
public class GraficaTabla {

    public void generarTablaHTML(ArrayList<Simbolos> listSimbolos, NodoTabla[][] tabla, ArrayList<NodoCaso> listCasos) {
        File archivoTabla = new File("tabla.html");
        FileWriter redactor;
        try {
            redactor = new FileWriter(archivoTabla);
            BufferedWriter buffer = new BufferedWriter(redactor);
            buffer.write(generarEncabezado());
            buffer.newLine();
            buffer.write(titulos(listSimbolos));
            buffer.newLine();
            buffer.write(mostrarTabla(tabla, listCasos, listSimbolos));
            buffer.newLine();
            buffer.write(parteFinal());
            buffer.newLine();
            buffer.close();
            redactor.close();
        } catch (IOException ex) {
            Logger.getLogger(GraficaTabla.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String generarEncabezado() {
        String encabezado = "";
        encabezado += "<!DOCTYPE html>\n";
        encabezado += "<html lang ='en'>\n";
        encabezado += "<head><meta charset='UTF-8'><title>LALR</title><link rel='stylesheet' href='estilo.css'></head>\n" + "<body>\n";
        encabezado += "<div id ='main-container'>\n";
        encabezado += "<h1>TABLA LALR</h1>\n";
        encabezado += "<table>\n";
        encabezado += "<thead>\n";
        encabezado += "<tr>\n";
        return encabezado;
    }

    public String titulos(ArrayList<Simbolos> listSimbolos) {
        String datos = "";
        String[] titulos = new String[listSimbolos.size()];
        for (int i = 0; i < listSimbolos.size(); i++) {
            titulos[i] = listSimbolos.get(i).getIdentificador();
        }
        datos += "<td></td>\n";
        for (int i = 0; i < titulos.length; i++) {
            datos += "<td><strong>" + titulos[i] + "</strong></td>";
        }
        datos += "</tr>\n";
        datos += "</thead>\n";
        return datos;
    }

    public String mostrarTabla(NodoTabla[][] tabla, ArrayList<NodoCaso> listCasos, ArrayList<Simbolos> listSimbolos) {
        String fila = "";
        for (int i = 0; i < listCasos.size(); i++) {
            if (tabla[listCasos.get(i).getIdCaso()][0].isFilaActiva()) {
                fila += "\n<tr>\n";
                fila += "<td>" + listCasos.get(i).getIdCaso() + "</td>\n";
                for (int j = 0; j < listSimbolos.size(); j++) {

                    if (tabla[i + 1][j + 1] != null) {
                        fila += "<td>";
                        for (int k = 0; k < tabla[i + 1][j + 1].getAcciones().size(); k++) {
                            fila += tabla[i + 1][j + 1].getAcciones().get(k).getAccion() + "" + tabla[i + 1][j + 1].getAcciones().get(k).getNoCaso() + " ";
                        }
                        fila += "</td>\n";
                    } else {
                        fila += "<td></td>\n";
                    }
                }
                fila += "</tr>\n";
            }
        }
        return fila;
    }

    public String parteFinal() {
        String ultimo = "";
        ultimo += "</table>\n";
        ultimo += "</div>\n";
        ultimo += "</body>\n";
        ultimo += "</html>";
        return ultimo;
    }

}
