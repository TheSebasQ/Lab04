package Main;

import P1.ParqueaderoApp;
import P2.TurnoEPS;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main {
    public static JFrame frame;
    public static JButton[] botones;

    public static void main(String[] args) {
        // Título de la ventana
        String titulo = "Lab04: PILAS Y COLAS";

        // Opciones del menú
        String[] opciones = {"Interfaz Parqueadero", "Interfaz Hospital"};
        final int numop = opciones.length;

        // Crear el marco de la ventana
        frame = new JFrame(titulo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            botones[i].addActionListener((ActionEvent e) -> {
                // Ejecutar según la opción seleccionada
                switch (index) {
                    case 0 -> {
                        new Thread(() -> ParqueaderoApp.main(new String[]{})).start();
                        frame.dispose(); // Cerrar la ventana del menú
                    }
                    case 1 -> {
                        new Thread(() -> new TurnoEPS().setVisible(true)).start();
                        frame.dispose(); // Cerrar la ventana del menú
                    }
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
