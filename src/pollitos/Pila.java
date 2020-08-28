/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollitos;

/**
 *
 * @author luisGonzalez
 */
public class Pila {
    
    private Token token;
    private Integer noCaso;

    public Pila(Token token, Integer noCaso) {
        this.token = token;
        this.noCaso = noCaso;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Integer getNoCaso() {
        return noCaso;
    }

    public void setNoCaso(Integer noCaso) {
        this.noCaso = noCaso;
    }

}
