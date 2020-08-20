/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.util.ArrayList;

/**
 *
 * @author luisGonzalez
 */
public class ArregloExpresiones {
    
    
    public ArrayList<Expresiones> arregloExpresiones(ArrayList<Expresiones> listActual){
        ArrayList<Expresiones> listAux = new ArrayList<>();
        for (int i = listActual.size()-1; i >= 0; i--) {
            listAux.add(new Expresiones(listActual.get(i)));
        }
        return listAux;
    }
    
}
