/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seccion_5;

import java.util.ArrayList;
import pollitos.Simbolos;

/**
 *
 * @author luisGonzalez
 */
public class Verificaciones2 {

    public boolean verificarId(ArrayList<Simbolos> listSimbolos, String id) {
        boolean existe = false;
        for (int i = 0; i < listSimbolos.size(); i++) {
            if (listSimbolos.get(i).getIdentificador().equals(id)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    public Boolean verificarTerminal(ArrayList<Simbolos> listSimbolos, String id) {
        Boolean esTerminal = false;
        for (int i = 0; i < listSimbolos.size(); i++) {
            if (listSimbolos.get(i).getIdentificador().equals(id)) {
                if (listSimbolos.get(i).getTipo().equals("Terminal")) {
                    esTerminal = true;
                    break;
                } else {
                    esTerminal = false;
                    break;

                }
            }
        }
        return esTerminal;
    }
    
    public Boolean verificarEstado(ArrayList<Simbolos> listSimbolos, String id){
        Boolean existe = false;
        for (int i = 0; i < listSimbolos.size(); i++) {
            if(listSimbolos.get(i).getIdentificador().equals(id)){
                if(listSimbolos.get(i).getTipo().equals("NoTerminal")){
                    existe = true;
                    break;
                }
            }
        }
        return existe;
    }

}
