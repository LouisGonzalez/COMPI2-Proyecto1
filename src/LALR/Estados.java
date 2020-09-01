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
    private String codigoAntesala;
    private String result;
    private boolean marcado;
    private Integer noIterador;

    public Integer getNoIterador() {
        return noIterador;
    }

    public void setNoIterador(Integer noIterador) {
        this.noIterador = noIterador;
    }
    
    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public Estados(){
        
    }
    
    public Estados(Estados estado){
        noEstado = estado.noEstado;
        identificador = estado.identificador;
        misExpresiones = estado.misExpresiones;
        misCarriles = estado.misCarriles;
        simplificado = estado.simplificado;
        result = estado.result;
        codigoAntesala = estado.codigoAntesala;
        noIterador = estado.noIterador;
        
    }
  
    public Estados(int noEstado, String identificador, ArrayList<Expresiones> misExpresiones, String result, String codigoAntesala) {
        this.identificador = identificador;
        this.misExpresiones = misExpresiones;
        this.noEstado = noEstado;
        this.result = result;
        this.codigoAntesala = codigoAntesala;
    }

    public String getCodigoAntesala() {
        return codigoAntesala;
    }

    public void setCodigoAntesala(String codigoAntesala) {
        this.codigoAntesala = codigoAntesala;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
