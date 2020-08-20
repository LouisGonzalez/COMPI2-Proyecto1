/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author luisGonzalez
 */
public class Expresiones {
    
    private String identificador;
    private Boolean esTerminal;
    private Pattern miExpresion;
    private String misCaracteres;
    private Boolean punto = null;
    private Boolean puntoFinal = false;

    
    public Expresiones(Expresiones exp){
        identificador = exp.identificador;
        esTerminal = exp.esTerminal;
        miExpresion = exp.miExpresion;
        misCaracteres = exp.misCaracteres;
        punto = exp.punto;
        puntoFinal = exp.puntoFinal;
    }
    
    public Expresiones(String identificador, Boolean esTerminal, Pattern miExpresion, String misCaracteres) {
        this.identificador = identificador;
        this.esTerminal = esTerminal;
        this.miExpresion = miExpresion;
        this.misCaracteres = misCaracteres;
    }

    

    public Boolean getPuntoFinal() {
        return puntoFinal;
    }

    public void setPuntoFinal(Boolean puntoFinal) {
        this.puntoFinal = puntoFinal;
    }

    public Boolean getEsTerminal() {
        return esTerminal;
    }

    public void setEsTerminal(Boolean esTerminal) {
        this.esTerminal = esTerminal;
    }

    public Boolean getPunto() {
        return punto;
    }

    public void setPunto(Boolean punto) {
        this.punto = punto;
    }

    public String getMisCaracteres() {
        return misCaracteres;
    }

    public void setMisCaracteres(String misCaracteres) {
        this.misCaracteres = misCaracteres;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public boolean isEsTerminal() {
        return esTerminal;
    }

    public void setEsTerminal(boolean esTerminal) {
        this.esTerminal = esTerminal;
    }

    public Pattern getMiExpresion() {
        return miExpresion;
    }

    public void setMiExpresion(Pattern miExpresion) {
        this.miExpresion = miExpresion;
    }
    
    
    
}
