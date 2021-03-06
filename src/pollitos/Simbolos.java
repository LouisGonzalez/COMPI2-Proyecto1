/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollitos;

import java.io.Serializable;

/**
 *
 * @author luisitopapurey
 */
public class Simbolos implements Serializable {
    
    private String tipo;
    private String identificador;
    private String valor;
    private int precedencia;

    public Simbolos(String tipo, String identificador, String valor, int precedencia) {
        this.tipo = tipo;
        this.identificador = identificador;
        this.valor = valor;
        this.precedencia = precedencia;
    }

    public int getPrecedencia() {
        return precedencia;
    }

    public void setPrecedencia(int precedencia) {
        this.precedencia = precedencia;
    }
    
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
}
