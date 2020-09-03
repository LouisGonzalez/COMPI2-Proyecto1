/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modificaciones;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luisGonzalez
 */
public class GuardadoCodigos {

    public void guardarArchivo(String path, String texto) {
        File archivo = new File(path);
        if (archivo.exists()) {
            archivo.delete();
            guardarArchivo(path, texto);
        } else {
            try {
                archivo.createNewFile();
                FileWriter writer = new FileWriter(archivo);
                BufferedWriter buffer = new BufferedWriter(writer);
                buffer.write(texto);
                buffer.close();
            } catch (IOException ex) {
                Logger.getLogger(GuardadoCodigos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
