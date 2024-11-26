/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author USUARIA
 */

import P1.ParqueaderoApp;
import P2.TurnoEPS;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static JFrame frame;
    public static JButton[] botones;

    public static void main(String[] args) {
        // *** AJUSTAR EL TITULO DE LA VENTANA (JFRAME) DEL MENU ***
        String titulo = "Lab04: PILAS Y COLAS ";

        // *** AJUSTAR LOS MENSAJES QUE HAN DE APARECER EN EL MENU ***
        String opciones[] = {"INTERFAZ PARQUEADERO ", " INTERFAZ HOSPITAL "};

        final int numop = opciones.length;

        // Crear el marco de la ventana
        frame = new JFrame(titulo);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        frame.setSize(400, 50 * numop);
        frame.setLayout(new BorderLayout());

        // Crear un panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(numop, 1)); // Disponer los botones en una columna

        // Crear un array de botones
        botones = new JButton[numop];

        // Inicializar los botones y añadir ActionListener
        for (int i = 0; i < numop; i++) {
            final int index = i; // Variable final para usar en el ActionListener
            botones[i] = new JButton(opciones[i]);
            botones[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // *** AJUSTAR SEGUN LAS OPCIONES DEL MENU ***
                   switch (index) {
                case 0:
                    new Thread(() -> {
                        ParqueaderoApp.main(new String[]{});
                    }).start();
                    frame.dispose();  // Cerrar la ventana del menú
                    break;
                case 1:
                    new Thread(() -> {
                        new TurnoEPS().setVisible(true);
                    }).start();
                    frame.dispose();  // Cerrar la ventana del menú
                    break;
                        
                    }
                    // *** FINAL AJUSTES ***
                }
            });
            // Añadir el botón al panel
            panelBotones.add(botones[i]);
        }

        // Añadir el panel de botones al marco
        frame.add(panelBotones, BorderLayout.CENTER);

        // Mostrar la ventana
        frame.setVisible(true);
    }
}