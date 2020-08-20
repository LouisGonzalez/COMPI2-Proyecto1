/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author luisGonzalez
 */
public class Estados implements Serializable {

    private int noEstado;
    private String identificador;
    private ArrayList<Expresiones> misExpresiones;
    private ArrayList<Carril> misCarriles = new ArrayList<>();
    private boolean simplificado = false;

    public Estados(){
        
    }
    
    public Estados(Estados estado){
        noEstado = estado.noEstado;
        identificador = estado.identificador;
        misExpresiones = estado.misExpresiones;
        misCarriles = estado.misCarriles;
        simplificado = estado.simplificado;
    }
  
    public Estados(int noEstado, String identificador, ArrayList<Expresiones> misExpresiones) {
        this.identificador = identificador;
        this.misExpresiones = misExpresiones;
        this.noEstado = noEstado;
    }

    public boolean isSimplificado() {
        return simplificado;
    }

    public void setSimplificado(boolean simplificado) {
        this.simplificado = simplificado;
    }

    public int getNoEstado() {
        return noEstado;
    }

    public void setNoEstado(int noEstado) {
        this.noEstado = noEstado;
    }

    public ArrayList<Carril> getMisCarriles() {
        return misCarriles;
    }

    public void setMisCarriles(ArrayList<Carril> misCarriles) {
        this.misCarriles = misCarriles;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public ArrayList<Expresiones> getMisExpresiones() {
        return misExpresiones;
    }

    public void setMisExpresiones(ArrayList<Expresiones> misExpresiones) {
        this.misExpresiones = misExpresiones;
    }

}
