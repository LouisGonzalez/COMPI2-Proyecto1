/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LALR;

import interfaz.PanelHojas;
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

    public String generarCodigo(String variables, String sentencia, String varDevolver, String tipoDev, String codigo2, String codigoAntesala) {
        String codigo = "";
        String antesala = "";
        if (codigoAntesala != null) {
            antesala = codigoAntesala;
        }
        if (tipoDev.equals("void")) {
            if (sentencia != null) {
                codigo = "import java.util.*;\n"
                        + "import java.io.*;\n"
                        + "public class claseGeneral{\n"
                        + variables + "\n"
                        + codigo2 + "\n"
                        + "public " + tipoDev + " mains(){\n"
                        + antesala + "\n"
                        + sentencia + ";\n"
                        + "}\n"
                        + "}";
            } else {
                codigo = "import java.util.*;\n"
                        + "import java.io.*;\n"
                        + "public class claseGeneral{\n"
                        + variables + "\n"
                        + codigo2 + "\n"
                        + "public " + tipoDev + " mains(){\n"
                        + antesala + "\n"
                        + "}\n"
                        + "}";

            }
        } else {
            codigo = "import java.util.*;\n"
                    + "import java.io.*;\n"
                    + "public class claseGeneral{\n"
                    + variables + "\n"
                    + codigo2 + "\n"
                    + "public " + tipoDev + " mains(){\n"
                    + antesala + "\n"
                    + "     " + tipoDev + " " + varDevolver + " = null;\n"
                    + "     " + varDevolver + "=" + sentencia + ";\n"
                    + "return " + varDevolver + ";\n"
                    + "}\n"
                    + "}";
        }
        return codigo;
    }

    public Object devolverDatos(String variables, String sentencia, String varDevolver, String tipoDev, String codigo2, String codigoAntesala) {
        Object resultado = null;
        String codigo = generarCodigo(variables, sentencia, varDevolver, tipoDev, codigo2, codigoAntesala);
        SimpleCompiler compiler = new SimpleCompiler();

        System.out.println(codigo);
        try {
            compiler.cook(new StringReader(codigo));
            Class c1 = compiler.getClassLoader().loadClass("claseGeneral");
            Object arne = c1.newInstance();
            Method doWork = c1.getDeclaredMethod("mains");
            resultado = doWork.invoke(arne, new Object[0]);
        } catch (CompileException | IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(CodigoJava.class.getName()).log(Level.SEVERE, null, ex);
            PanelHojas.totalErrores += generarCodigo(variables, sentencia, varDevolver, tipoDev, codigo2, codigoAntesala) + "\n";
            PanelHojas.totalErrores += "(ERROR SEMANTICO) " + CodigoJava.class.getName() + " " + ex+"\n";
            PanelHojas.totalErrores += "Porfavor revise el archivo de entrada del lenguaje.\n";
        }
        return resultado;
    }

}
