/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import Arbol.NodoArbol;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author luisGonzalez
 */
public class Expresiones {
    
    private String identificador;
    private Boolean esTerminal;
    private String misCaracteres;
    private Boolean punto = null;
    private Boolean puntoFinal = false;
    private NodoArbol expresion;

    
    public Expresiones(Expresiones exp){
        identificador = exp.identificador;
        esTerminal = exp.esTerminal;
        misCaracteres = exp.misCaracteres;
        punto = exp.punto;
        puntoFinal = exp.puntoFinal;
        expresion = exp.expresion;
    }
    
    public Expresiones(String identificador, Boolean esTerminal, NodoArbol expresion, String misCaracteres) {
        this.identificador = identificador;
        this.esTerminal = esTerminal;
        this.misCaracteres = misCaracteres;
        this.expresion = expresion;
    }

    public NodoArbol getExpresion() {
        return expresion;
    }

    public void setExpresion(NodoArbol expresion) {
        this.expresion = expresion;
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

    
    
    
}
