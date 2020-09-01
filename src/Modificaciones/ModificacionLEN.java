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
public class ModificacionLEN {
    
    
    public void guardarArchivoLEN(String texto, String path){
        File archivo = new File(path);
        if(archivo.exists()){
            archivo.delete();
            guardarArchivoLEN(texto, path);
        } else {
            try {
                archivo.createNewFile();
                FileWriter writer = new FileWriter(archivo);
                BufferedWriter buffer = new BufferedWriter(writer);
                buffer.write(texto);
                buffer.close();
            } catch (IOException ex) {
                Logger.getLogger(ModificacionLEN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
