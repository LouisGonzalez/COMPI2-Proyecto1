/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollitos;

/**
 *
 * @author luisitopapurey
 */
public class Token {

    private String identificador;
    private Integer noEstado;
    private String tipo;
    private Object valor;
    private boolean esTerminal = true;
    private String idTerminal;
    
    public Token(String identificador, String tipo, Object valor, Integer noEstado) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.valor = valor;
        this.noEstado = noEstado;
    }

    public String getIdTerminal() {
        return idTerminal;
    }

    public void setIdTerminal(String idTerminal) {
        this.idTerminal = idTerminal;
    }

    public Integer getNoEstado() {
        return noEstado;
    }

    public void setNoEstado(Integer noEstado) {
        this.noEstado = noEstado;
    }
    
    public boolean isEsTerminal() {
        return esTerminal;
    }

    public void setEsTerminal(boolean esTerminal) {
        this.esTerminal = esTerminal;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    
    
    
}
