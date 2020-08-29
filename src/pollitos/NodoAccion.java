/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollitos;

import java.io.Serializable;

/**
 *
 * @author luisGonzalez
 */
public class NodoAccion implements Serializable{
    
    private String accion;
    private Integer noCaso;

    public NodoAccion(String accion, Integer noCaso) {
        this.accion = accion;
        this.noCaso = noCaso;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Integer getNoCaso() {
        return noCaso;
    }

    public void setNoCaso(Integer noCaso) {
        this.noCaso = noCaso;
    }
    
}
