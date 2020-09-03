/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import gramaticaLEN.SintaxLEN;
import java.util.ArrayList;
import pollitos.Simbolos;

/**
 *
 * @author luisGonzalez
 */
public class ManejoResults {

    private Tipado tipado = new Tipado();

    public boolean verificarResults(ArrayList<Estados> listEstados, ArrayList<Simbolos> listSimbolos) {
        boolean error = false;
        for (int i = 0; i < listEstados.size(); i++) {
            String tipo = tipado.determinarTipo(listEstados.get(i).getIdentificador(), listSimbolos);
            if (listEstados.get(i).getResult() != null) {
                if (tipo.equals("") && !listEstados.get(i).getResult().equals("")) {
                    SintaxLEN.totalErrores += "(ERROR SEMANTICO) No es posible enviar un RESULT en el estado " + listEstados.get(i).getIdentificador() + " debido a que su variable de retorno no es la esperada.\n";
                    error = true;
                }
            }
        }
        return error;
    }

}
