
package P2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class TurnoEPS extends JFrame {
    private Queue<Paciente> colaPacientes = new LinkedList<>();
    private JTextArea areaTurnos;
    private JTextArea areaColaPacientes;
    private JLabel etiquetaTurnoActual;
    private JButton botonExtenderTiempo;
    private Timer temporizador;
    private int tiempoRestante = 5; // 5 segundos para la simulación
    private Paciente pacienteActual;
    private int numeroTurnoActual = 0;
    private int contadorTurnos = 1; // Contador global para asignar turnos

    public TurnoEPS() {
        setTitle("Asignación de Turnos - EPS");
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JTextField campoNombre = new JTextField(15);
        JTextField campoEdad = new JTextField(3);
        JComboBox<String> comboAfiliacion = new JComboBox<>(new String[]{"Sin afiliación", "POS", "PC"});
        JComboBox<String> comboCondicion = new JComboBox<>(new String[]{"Ninguna", "Embarazo", "Limitación motriz"});

        JButton botonAgregar = new JButton("Agregar Paciente");
        areaTurnos = new JTextArea(12, 30);
        areaTurnos.setBorder(BorderFactory.createTitledBorder("Historial de Turnos"));
        areaColaPacientes = new JTextArea(12, 30);
        areaColaPacientes.setBorder(BorderFactory.createTitledBorder("Cola de Pacientes"));
        etiquetaTurnoActual = new JLabel("Turno actual: Ninguno");
        botonExtenderTiempo = new JButton("Extender Tiempo");

        add(new JLabel("Nombre:"));
        add(campoNombre);
        add(new JLabel("Edad:"));
        add(campoEdad);
        add(new JLabel("Afiliación:"));
        add(comboAfiliacion);
        add(new JLabel("Condición:"));
        add(comboCondicion);
        add(botonAgregar);
        add(etiquetaTurnoActual);
        add(botonExtenderTiempo);
        add(new JScrollPane(areaTurnos));
        add(new JScrollPane(areaColaPacientes));

        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText();
                int edad = Integer.parseInt(campoEdad.getText());
                String afiliacion = (String) comboAfiliacion.getSelectedItem();
                String condicion = (String) comboCondicion.getSelectedItem();

                if ("Sin afiliación".equals(afiliacion)) {
                    afiliacion = null;
                }

                Paciente nuevoPaciente = new Paciente(nombre, edad, afiliacion, condicion, contadorTurnos++);
                agregarPaciente(nuevoPaciente);
                campoNombre.setText("");
                campoEdad.setText("");
            }
        });
         botonExtenderTiempo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pacienteActual != null) {
                    tiempoRestante += 5; // Extiende el tiempo en 5 segundos
                } else {
                    JOptionPane.showMessageDialog(null, "No hay turno en curso.");
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void agregarPaciente(Paciente paciente) {
        // Se usa una lista temporal para manejar la prioridad y el orden de llegada
        LinkedList<Paciente> listaTemporal = new LinkedList<>();
        boolean prioridadAgregada = false;

        // Agregar pacientes existentes en la cola a la lista temporal
        while (!colaPacientes.isEmpty()) {
            listaTemporal.add(colaPacientes.poll());
        }

        // Verificar si el nuevo paciente tiene prioridad
        if (tienePrioridad(paciente)) {
            // Se inserta en la lista temporal en el primer lugar que haya pacientes sin prioridad
            for (int i = 0; i < listaTemporal.size(); i++) {
                if (!tienePrioridad(listaTemporal.get(i))) {
                    listaTemporal.add(i, paciente);
                    prioridadAgregada = true;
                    break;
                }
            }
        }
        
   // Si no se ha agregado, significa que el paciente puede ir al final
        if (!prioridadAgregada) {
            listaTemporal.add(paciente);
        }

        // Reinserta los pacientes en la cola original en el mismo orden
        colaPacientes.addAll(listaTemporal);

        actualizarColaPacientes();
        if (pacienteActual == null) {
            iniciarTurno();
        }
    }

    private boolean tienePrioridad(Paciente paciente) {
        return paciente.getEdad() >= 60 || paciente.getEdad() < 12 ||
               "PC".equals(paciente.getAfiliacion()) ||
               "Embarazo".equals(paciente.getCondicion()) ||
               "Limitación motriz".equals(paciente.getCondicion());
    }

    private void iniciarTurno() {
        if (colaPacientes.isEmpty()) {
            pacienteActual = null;
            etiquetaTurnoActual.setText("Turno actual: Ninguno");
            return;
        }
        
        pacienteActual = colaPacientes.poll();
        numeroTurnoActual = pacienteActual.getNumeroTurno();
        etiquetaTurnoActual.setText("Turno actual: " + numeroTurnoActual + " - " + pacienteActual.getNombre());
        areaTurnos.append("Llamando a: Turno " + numeroTurnoActual + " - " + pacienteActual.getNombre() + "\n");
        actualizarColaPacientes();

        tiempoRestante = 5;

        // Crear un temporizador
        temporizador = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--;
                if (tiempoRestante <= 0) {
                    temporizador.stop();
                    areaTurnos.append(pacienteActual.getNombre() + " ha sido atendido.\n");
                    pacienteActual = null;
                    iniciarTurno();
                } else {
                    etiquetaTurnoActual.setText("Turno actual: " + numeroTurnoActual + " - " + pacienteActual.getNombre() + " | Tiempo restante: " + tiempoRestante + "s");
                }
            }
        });
        temporizador.start();
    }

    private void actualizarColaPacientes() {
        areaColaPacientes.setText("");
        for (Paciente p : colaPacientes) {
            areaColaPacientes.append("Turno " + p.getNumeroTurno() + " - " + p.getNombre() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TurnoEPS::new);
    }
}