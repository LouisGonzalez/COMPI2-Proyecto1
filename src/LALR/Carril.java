/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.io.Serializable;

/**
 *
 * @author luisGonzalez
 */
public class Carril implements Serializable{
    
    private String id;
    private Object simbolo;

    
    public Carril(Carril carril){
        id = carril.id;
        simbolo = carril.simbolo;
    }
    
    public Carril(String id, Object simbolo) {
        this.id = id;
        this.simbolo = simbolo;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(Object simbolo) {
        this.simbolo = simbolo;
    }
    
    
    
}
