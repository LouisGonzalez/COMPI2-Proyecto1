/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hojas;

import LALR.Estados;
import LALR.NodoCaso;
import interfaz.PanelHojas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import pollitos.MisExpresiones;

/**
 *
 * @author luisGonzalez
 */
public class NuevaHoja {
    
    public void crearHoja(File archivo, FileReader reader, BufferedReader buffer, String texto, JTabbedPane principal, ArrayList<Estados> listEstados, ArrayList<NodoCaso> listCasos){
        try {
            reader = new FileReader(archivo.toString());
            buffer = new BufferedReader(reader);
            while(buffer.ready()){
                texto += buffer.readLine() + "\n";
            }
            abrirPanel(texto, archivo.getPath(), archivo.getName(), principal, listEstados, listCasos);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NuevaHoja.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NuevaHoja.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void abrirPanel(String texto, String path, String titulo, JTabbedPane principal, ArrayList<Estados> listEstados, ArrayList<NodoCaso> listCasos){
        PanelHojas panel = new PanelHojas(texto, path, listEstados, listCasos);
        principal.addTab(titulo, panel);
        principal.setTabComponentAt(principal.getTabCount() - 1, crearCabecera(titulo, principal));
    }
    
    public JPanel crearCabecera(String texto, JTabbedPane principal){
        final String titulo = texto;
        JPanel pnlTab = new JPanel();
        pnlTab.setLayout(new BoxLayout(pnlTab, BoxLayout.LINE_AXIS));
        pnlTab.setOpaque(false);
        JButton btnCerrar = new JButton("x");
        btnCerrar.setPreferredSize(new java.awt.Dimension(10, 10));
        JLabel lblTitulo = new JLabel(texto + "    ");
        btnCerrar.setBorderPainted(false);
        btnCerrar.setOpaque(false);
        btnCerrar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int i;
                for (i = 0; i < principal.getTabCount() - 1; i++) {
                    if(titulo.equals(principal.getTitleAt(i))){
                        break;
                    }
                }
                principal.removeTabAt(i);
            }
        });
        pnlTab.add(lblTitulo);
        pnlTab.add(btnCerrar);
        return pnlTab;
    }
    
}
