/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import LALR.GeneracionTabla;
import java.beans.Statement;
import interfaz.Inicio;
import seccion_2.CreacionClases;
import seccion_3.ExpresionesRegulares;

/**
 *
 * @author luisGonzalez
 */
public class main {

    CreacionClases clase = new CreacionClases();
    static GeneracionTabla tabla = new GeneracionTabla();
   
    public static void main(String[] args) throws Exception {
        Inicio inicio = new Inicio();
        inicio.setVisible(true);
        ExpresionesRegulares b = new ExpresionesRegulares();
        b.prueva();
        
        Object prueba = 3.2 +3.0009;
        
        System.out.println(prueba);
        
          String codigo = "public class clasePrueba{\n"
                  + "int a = 1;\n"
            + "public void mains(){\n"
            + "     System.out.println(\"Hola mundo\");\n"
                  + "System.out.println(retorno());\n"
            + "}\n"
                  + "public Integer retorno(){\n"
                  + "return 7;\n"
                  + "}" 
            + "}";
          
          CreacionClases.pruebaJanino2(codigo);
        
    }
    
    

}
