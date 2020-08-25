/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author luisitopapurey
 */
public class Primeros {

    
    public void calculoPrimeros(ArrayList<Estados> listEstados, String identificador, ArrayList<Carril> listPrimeros, int iterador) {
        for (int i = listEstados.size() - 1; i >= 0; i--) {
            if (listEstados.get(i).getIdentificador().equals(identificador)) {
                Estados aRevisar = listEstados.get(i);
                if (aRevisar.getMisExpresiones() != null) {
                    for (int j = 0; j < aRevisar.getMisExpresiones().size(); j++) {
                        if (!aRevisar.getMisExpresiones().get(j).isEsTerminal()) {
                            boolean lambda = verificarEstadoLambda(listEstados, aRevisar.getMisExpresiones().get(j).getIdentificador());
                            //si no es lambda se termina el ciclo for con iterador J    
                            if(!lambda){
                                calculoPrimeros(listEstados, aRevisar.getMisExpresiones().get(j).getIdentificador(), listPrimeros, 0);
                                
                            } 
                        } else {
                            retornoPrimeros(listPrimeros, aRevisar.getMisExpresiones().get(j));
                            break;
                        }

                    }

                }
            }
        }
        
    }

    
    
    public boolean verificarEstadoLambda(ArrayList<Estados> listEstados, String identificador) {
        boolean esLambda = false;
        for (int i = 0; i < listEstados.size(); i++) {
            if (listEstados.get(i).getIdentificador().equals(identificador)) {
                if (listEstados.get(i).getMisExpresiones().isEmpty()) {
                    esLambda = true;
                }
            }
        }
        return esLambda;
    }
    
    public void retornoPrimeros(ArrayList<Carril> listCarril, Expresiones actual){
        if(actual.getExpresion()== null){
       //     System.out.println(actual.getIdentificador());
       //     System.out.println("-------------");
            listCarril.add(new Carril(actual.getIdentificador(), actual.getMisCaracteres()));
        } else {
        //    System.out.println(actual.getIdentificador());
         //   System.out.println("-------------");
            listCarril.add(new Carril(actual.getIdentificador(), actual.getMisCaracteres()));
        }
       
    }

}
