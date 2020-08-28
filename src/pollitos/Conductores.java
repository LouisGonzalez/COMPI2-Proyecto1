/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollitos;

import java.io.Serializable;

/**
 *
 * @author luisGonzalez
 */
public class Conductores implements Serializable{
    
    private int prioridad;
    private String id;
    private String caracter;
    private String tipo;
    private String palabraCompleta;

    public Conductores(int prioridad, String id, String tipo, String palabraCompleta) {
        this.prioridad = prioridad;
        this.id = id;
        this.tipo = tipo;
        this.palabraCompleta = palabraCompleta;
    }

    public String getPalabraCompleta() {
        return palabraCompleta;
    }

    public void setPalabraCompleta(String palabraCompleta) {
        this.palabraCompleta = palabraCompleta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCaracter() {
        return caracter;
    }

    public void setCaracter(String caracter) {
        this.caracter = caracter;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
