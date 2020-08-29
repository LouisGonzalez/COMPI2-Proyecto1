/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seccion_2;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.*;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ScriptEvaluator;
import org.codehaus.janino.SimpleCompiler;

/**
 *
 * @author luisGonzalez
 */
public class CreacionClases {

    public static void nuevaClase(String nombreClase, String primerosMetodos) {
        String codigo = "import java.util.Comparator;\n"
                + "import java.util.*;"
                + "import java.io.*;\n"
                + "public class " + nombreClase + " {\n\n"
                + primerosMetodos + "\n\n"
                + "}";

        RuntimeCompiler r = new RuntimeCompiler();
        r.addClass(nombreClase, codigo);
        r.compile();

  //      MethodInvocationUtils.invokeStaticMethod(r.getCompiledClass(nombreClase), "prueba");
    }

    public static void pruebaJanino() {
        ScriptEvaluator se = new ScriptEvaluator();
        String metodo = "public static void method1(){\n"
                + "System.out.println(\"Hola que pex\");\n"
                + "}\n"         
                + "method1();"; 
      try {
            se.cook(metodo);
            se.evaluate(new Object[0]);
         
        } catch (CompileException ex) {
            Logger.getLogger(CreacionClases.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(CreacionClases.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    String codigo = "class clasePrueba{\n"
            + "public static void main(String[] args){\n"
            + "     System.out.println(\"Hola mundo\");"
            + "}\n\n"
            + "}";
    
    public static void pruebaJanino2(String codigo){
        SimpleCompiler compiler = new SimpleCompiler();
        try {
            compiler.cook(new StringReader(codigo));
            Class c1 = compiler.getClassLoader().loadClass("clasePrueba");
            Object arne = c1.newInstance();
            Method doWork = c1.getDeclaredMethod("mains");
            Object resultado = doWork.invoke(arne, new Object[0]);
            System.out.println(resultado);
            
        } catch (CompileException | IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(CreacionClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
