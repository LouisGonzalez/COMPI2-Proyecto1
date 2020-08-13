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

    public MisExpresiones(String identificador, Pattern expresion) {
        this.identificador = identificador;
        this.expresion = expresion;
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
