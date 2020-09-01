/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seccion_4;

import gramaticaLEN.SintaxLEN;
import java.util.ArrayList;
import pollitos.MisExpresiones;
import pollitos.Simbolos;

/**
 *
 * @author luisGonzalez
 */
public class Verificaciones {
    
    public Boolean verificarIdentificador(ArrayList<Simbolos> listSimbolos, String nombre, Integer linea, Integer columna){
        Boolean siExiste = false;
        for (int i = 0; i < listSimbolos.size(); i++) {
            if(listSimbolos.get(i).getIdentificador().equals(nombre)){
                System.out.println("Ya existe un dato con el identificador: "+nombre);
                SintaxLEN.totalErrores += "(ERROR SEMANTICO) Ya existe un simbolo con el identificador: "+nombre+". Linea: "+linea+" Columna: "+columna+".\n";
                
                siExiste = true;
                break;
            }
        }
        return siExiste;
    }
    
    
    public boolean verificarIdExpresion(ArrayList<MisExpresiones> listExpresiones, String id, Integer linea, Integer columna){
        boolean siExiste = false;
        for (int i = 0; i < listExpresiones.size(); i++) {
            if(listExpresiones.get(i).getIdentificador().equals(id)){
                siExiste = true;
                break;
            }
        }
        if(siExiste){
            SintaxLEN.totalErrores += "(ERROR SEMANTICO) Ya existe una expresion regular con el identificador: "+id+". Linea: "+linea+" Columna: "+columna+".\n";
        }
        return siExiste;
    }
    
}
