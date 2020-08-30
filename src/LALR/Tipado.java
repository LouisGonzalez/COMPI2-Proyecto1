/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.util.ArrayList;
import pollitos.Simbolos;

/**
 *
 * @author luisGonzalez
 */
public class Tipado {
    
    
    public String determinarTipo(String id, ArrayList<Simbolos> listSimbolos){
        String tipo = null;
        for (int i = 0; i < listSimbolos.size(); i++) {
            if(listSimbolos.get(i).getIdentificador().equals(id)){
                tipo = listSimbolos.get(i).getValor();
                break;
            }
        }
        return tipo;
    }
    
}
