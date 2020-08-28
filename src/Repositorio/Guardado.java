/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import pollitos.Lenguajes;

/**
 *
 * @author luisGonzalez
 */
public class Guardado implements Serializable {

    public void guardarLenguaje(String nombreArchivo, Lenguajes nuevo, String path) {
        String fichero = path+nombreArchivo + ".dat";

        try {
            ObjectOutputStream objetoLenguaje = new ObjectOutputStream(new FileOutputStream(fichero));
            objetoLenguaje.writeObject(nuevo);
            objetoLenguaje.flush();
            objetoLenguaje.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Guardado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Guardado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Lenguajes abrirLenguaje(String nombre) {
        Lenguajes miLenguaje = new Lenguajes();

        try {

            try (ObjectInputStream salidaLenguaje = new ObjectInputStream(new FileInputStream(nombre))) {
                miLenguaje = (Lenguajes) salidaLenguaje.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Guardado.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Guardado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return miLenguaje;
    }

}
