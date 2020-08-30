/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.SimpleCompiler;

/**
 *
 * @author luisGonzalez
 */
public class CodigoJava {

    public String generarCodigo(String variables, String sentencia, String varDevolver, String tipoDev) {
        String codigo = "";
        if (tipoDev.equals("void")) {
            codigo = "public class claseGeneral{\n"
                    + variables + "\n"
                    + "public " + tipoDev + " mains(){\n"
                    +  sentencia + ";\n"
                    + "}\n"
                    + "}";

        } else {
            codigo = "public class claseGeneral{\n"
                    + variables + "\n"
                    + "public " + tipoDev + " mains(){\n"
                    + "     " + tipoDev + " " + varDevolver + " = null;\n"
                    + "     " + varDevolver + "=" + sentencia + ";\n"
                    + "return " + varDevolver + ";\n"
                    + "}\n"
                    + "}";
        }
        return codigo;
    }

    public Object devolverDatos(String variables, String sentencia, String varDevolver, String tipoDev) {
        Object resultado = null;
        if (sentencia != null) {
            String codigo = generarCodigo(variables, sentencia, varDevolver, tipoDev);
            SimpleCompiler compiler = new SimpleCompiler();

            System.out.println(codigo);
            try {
                compiler.cook(new StringReader(codigo));
                Class c1 = compiler.getClassLoader().loadClass("claseGeneral");
                Object arne = c1.newInstance();
                Method doWork = c1.getDeclaredMethod("mains");
                resultado = doWork.invoke(arne, new Object[0]);
                //   System.out.println(resultado);
            } catch (CompileException | IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(CodigoJava.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

}
