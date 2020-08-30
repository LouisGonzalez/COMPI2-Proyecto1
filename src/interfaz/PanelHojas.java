/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import Arbol.GeneracionArbol;
import Arbol.NodoArbol;
import Automata.Transiciones;
import Graficas.GraficaPila;
import Graficas.GraficaTabla;
import LALR.Carril;
import LALR.Estados;
import LALR.FuncionesTabla;
import LALR.GeneracionTabla;
import LALR.NodoCaso;
import LALR.OptimizacionLALR;
import LALR.Primeros;
import LALR.Tabla;
import gramaticaLEN.AnalizadorLexico;
import gramaticaLEN.SintaxLEN;
import hojas.NumeracionLineas;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pollitos.Lenguajes;
import pollitos.MisExpresiones;
import pollitos.NodoTabla;
import pollitos.Simbolos;
import pollitos.Token;

/**
 *
 * @author luisGonzalez
 */
public class PanelHojas extends javax.swing.JPanel {

    public static String pilaHTML = "";
    private NumeracionLineas numeracion;

    /* private ArrayList<MisExpresiones> listExpresiones2 = new ArrayList<>();
    private ArrayList<Estados> listEstados;
    private ArrayList<NodoCaso> listCasos;
    public static NodoTabla[][] miTabla = null;
    private ArrayList<Simbolos> listSimbolos = new ArrayList<>();*/
    private FuncionesTabla funciones = new FuncionesTabla();
    private Transiciones transicion = new Transiciones();
    private ArrayList<Token> listTokens = new ArrayList<>();
    private GraficaPila graficaPila = new GraficaPila();
    private Lenguajes elegido;

    /**
     * Creates new form PanelHojas
     */
    public PanelHojas(String texto, String path,  Lenguajes elegido) {
        initComponents();
        // this.listEstados = listEstados;
        //this.listCasos = listCasos;
        txtCodigo.setText(texto);
        this.elegido = elegido;
        numeracion = new NumeracionLineas(txtCodigo);
        jScrollPane1.setRowHeaderView(numeracion);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtCodigo = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        diagrama = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        estado = new javax.swing.JTextArea();

        txtCodigo.setColumns(20);
        txtCodigo.setRows(5);
        jScrollPane1.setViewportView(txtCodigo);

        jButton1.setText("compilar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        diagrama.setText("Diagrama");
        diagrama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diagramaActionPerformed(evt);
            }
        });

        jButton4.setText("Compilar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        estado.setColumns(20);
        estado.setRows(5);
        jScrollPane2.setViewportView(estado);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(diagrama, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(diagrama)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        /* AnalizadorLexico lexico = new AnalizadorLexico(new StringReader(txtCodigo.getText()));
        try {
            Lenguajes algo = new Lenguajes();
            new SintaxLEN(lexico, listEstados, listExpresiones2, listSimbolos, algo).parse();
            arbol2.agregarIdentificadorNodos(listExpresiones2);
            primero = arbol2.unirArboles(listExpresiones2);
            unico = new MisExpresiones("principal", primero, "");
            arbol2.pruebaExpresion(unico);
            for (int i = 0; i < listEstados.get(listEstados.size() - 1).getMisExpresiones().size(); i++) {
                System.out.println(listEstados.get(listEstados.size() - 1).getMisExpresiones().get(i).getIdentificador());
            }
        } catch (Exception ex) {
            Logger.getLogger(PanelHojas.class.getName()).log(Level.SEVERE, null, ex);
        }    */
    }//GEN-LAST:event_jButton1ActionPerformed

    int i = 0;
    private void diagramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diagramaActionPerformed
        /*   tabla.creacionCasos(listEstados, listCasos);
        System.out.println(listCasos.size() + "           EL NUMERO TOTAL DE CASOS");
        for (int i = 0; i < listCasos.size(); i++) {
            String vinculos = "";
            for (int j = 0; j < listCasos.get(i).getListEstados().size(); j++) {
                System.out.println(listCasos.get(i).getListEstados().get(j).getNoEstado() + "     " + listCasos.get(i).getListEstados().get(j).getIdentificador());
                String carriles = "";
                for (int k = 0; k < listCasos.get(i).getListEstados().get(j).getMisCarriles().size(); k++) {
                    carriles += listCasos.get(i).getListEstados().get(j).getMisCarriles().get(k).getId();
                }
                System.out.println("            Carriles   " + carriles);
            }
            System.out.println("--------------------VINCULOS CASO NO. " + listCasos.get(i).getIdCaso());
            for (int k = 0; k < listCasos.get(i).getListVinculos().size(); k++) {
                vinculos += "ID vinculo: " + listCasos.get(i).getListVinculos().get(k).getIdCasoVinculo() + "   objeto referencia: " + listCasos.get(i).getListVinculos().get(k).getVinculo();
            }
            System.out.println(vinculos);
            System.out.println("-----------------------------------------------------------------------");
        }
    
        miTabla = tabla2.creacionTabla(listCasos, listSimbolos);
        lalr.buscarCasosIguales(listCasos, miTabla, listSimbolos);
        tabla2.mostrarTabla(miTabla, listCasos, listSimbolos);*/
    }//GEN-LAST:event_diagramaActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (elegido != null) {
            listTokens = new ArrayList<>();
            String[] lineas = txtCodigo.getText().split("\n");
            String texto = "";
            for (String linea : lineas) {
                texto += linea;
            }
            transicion.transicionCadena(listTokens, elegido.getUnico().getListEstados().get(0), elegido.getUnico().getListEstados(), texto, elegido.getUnico().getTablaSiguientes());
            for (int j = 0; j < listTokens.size(); j++) {
                System.out.println("Cadena:  " + listTokens.get(j).getIdentificador() + " " + listTokens.get(j).getValor().toString());
            }
            funciones.transiciones(listTokens, elegido.getMiTabla(), elegido.getListSimbolos(), elegido.getListEstados());
            graficaPila.generarPilaHTML();
        } else {
            JOptionPane.showMessageDialog(null, "No has elegido un lenguaje aun.");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton diagrama;
    private javax.swing.JTextArea estado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtCodigo;
    // End of variables declaration//GEN-END:variables
}
