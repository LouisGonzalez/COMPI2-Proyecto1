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
public class Vinculos implements Serializable{
    
    private int idCasoVinculo;
    private Object vinculo;

    public int getIdCasoVinculo() {
        return idCasoVinculo;
    }

    public void setIdCasoVinculo(int idCasoVinculo) {
        this.idCasoVinculo = idCasoVinculo;
    }

    public Object getVinculo() {
        return vinculo;
    }

    public void setVinculo(Object vinculo) {
        this.vinculo = vinculo;
    }
    
    
    
}
