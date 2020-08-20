/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollitos;

import java.util.regex.Pattern;

/**
 *
 * @author luisGonzalez
 */
public class MisExpresiones {

    private String identificador;
    private Pattern expresion;
    private String caracteres;

    public MisExpresiones(String identificador, Pattern expresion, String caracteres) {
        this.identificador = identificador;
        this.expresion = expresion;
        this.caracteres = caracteres;
    }

    public String getCaracteres() {
        return caracteres;
    }

    public void setCaracteres(String caracteres) {
        this.caracteres = caracteres;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Pattern getExpresion() {
        return expresion;
    }

    public void setExpresion(Pattern expresion) {
        this.expresion = expresion;
    }

}
