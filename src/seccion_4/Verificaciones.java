/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seccion_4;

import java.util.ArrayList;
import pollitos.Simbolos;

/**
 *
 * @author luisGonzalez
 */
public class Verificaciones {
    
    public Boolean verificarIdentificador(ArrayList<Simbolos> listSimbolos, String nombre){
        Boolean siExiste = false;
        for (int i = 0; i < listSimbolos.size(); i++) {
            if(listSimbolos.get(i).getIdentificador().equals(nombre)){
                System.out.println("Ya existe un dato con el identificador: "+nombre);
                siExiste = true;
                break;
            }
        }
        return siExiste;
    }
    
}
