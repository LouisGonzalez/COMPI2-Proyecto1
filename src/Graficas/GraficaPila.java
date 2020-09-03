/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficas;

import interfaz.PanelHojas;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pollitos.Pila;

/**
 *
 * @author luisGonzalez
 */
public class GraficaPila {
    
    public void generarPilaHTML(){
        File archivoPila = new File("pila.html");
        try {
            FileWriter redactor = new FileWriter(archivoPila);
            BufferedWriter buffer = new BufferedWriter(redactor);
            buffer.write(generarEncabezado());
            buffer.newLine();
            buffer.write(PanelHojas.pilaHTML);
            buffer.newLine();
            buffer.write(parteFinal());
            buffer.newLine();
            buffer.close();
            redactor.close();
            Desktop.getDesktop().open(archivoPila);
        } catch (IOException ex) {
            Logger.getLogger(GraficaPila.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String generarEncabezado(){
        String encabezado = "";
        encabezado += "<!DOCTYPE html>\n";
        encabezado += "<html lang ='en'>\n";
        encabezado += "<head><meta charset='UTF-8'><title>PILA</title><link rel='stylesheet' href='estilo.css'></head>\n" + "<body>\n";
        encabezado += "<div id ='main-container'>\n";
        encabezado += "<h1>PILA</h1>\n";
        return encabezado;
    }
    
    public void tablaPila(ArrayList<Pila> miPila){
        PanelHojas.pilaHTML += "<table>\n"
                + "<thead>\n"
                + "<tr>\n"
                + "<td><strong>Caso</strong></td>\n"
                + "<td><strong>ID</strong></td>\n"
                + "</tr>\n"
                + "</thead>\n";
      
        for (int i = 0; i < miPila.size(); i++) {
            
            if(miPila.get(i).getToken() != null){
            PanelHojas.pilaHTML += "<tr>\n";
            PanelHojas.pilaHTML += "<td>"+miPila.get(i).getNoCaso()+"</td>\n";
            PanelHojas.pilaHTML += "<td>"+miPila.get(i).getToken().getIdentificador()+"</td>\n";
            PanelHojas.pilaHTML += "</tr>\n";
            } else {
                PanelHojas.pilaHTML += "<tr>\n";
                PanelHojas.pilaHTML += "<td>"+miPila.get(i).getNoCaso()+"</td>";
                PanelHojas.pilaHTML += "<td></td>\n";
                PanelHojas.pilaHTML += "</tr>";
            }
        }
        PanelHojas.pilaHTML += "</table>";
    }
    
    private String parteFinal(){
        String ultimo = "";
        ultimo += "</div>\n"
                + "</body>\n"
                + "</html>";
        return ultimo;
    }
    
}
