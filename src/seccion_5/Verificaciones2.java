/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seccion_5;

import gramaticaLEN.SintaxLEN;
import java.util.ArrayList;
import pollitos.Simbolos;

/**
 *
 * @author luisGonzalez
 */
public class Verificaciones2 {

    public boolean verificarId(ArrayList<Simbolos> listSimbolos, String id, Integer linea, Integer columna) {
        boolean existe = false;
        for (int i = 0; i < listSimbolos.size(); i++) {
            if (listSimbolos.get(i).getIdentificador().equals(id)) {
                existe = true;
                break;
            }
        }
        if(!existe){
            SintaxLEN.totalErrores += "(ERROR SEMANTICO) El simbolo "+id+" no existe dentro del lenguaje. Linea: "+linea+" Columna: "+columna+".\n";
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
    
    //Existe un estado existe dentro de los simbolos del lenguaje
    public Boolean verificarEstado(ArrayList<Simbolos> listSimbolos, String id, Integer linea, Integer columna){
        Boolean existe = false;
        for (int i = 0; i < listSimbolos.size(); i++) {
            if(listSimbolos.get(i).getIdentificador().equals(id)){
                if(listSimbolos.get(i).getTipo().equals("NoTerminal")){
                    existe = true;
                    break;
                }
            }
        }
        if(!existe){
            SintaxLEN.totalErrores += "(ERROR SEMANTICO) El estado "+id+" no existe  dentro de los simbolos sentenciados del lenguaje. Linea: "+linea+" Columna: "+columna+".\n";
        }
        return existe;
    }

}
