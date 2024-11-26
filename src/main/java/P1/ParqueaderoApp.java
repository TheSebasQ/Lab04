package P1;

import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Stack;

public class ParqueaderoApp extends JFrame {
    private ArrayList<RegistroVehiculo> listaVehiculos;
    private Stack<RegistroVehiculo> vehiculos2Ruedas;
    private Stack<RegistroVehiculo> vehiculos4Ruedas;
    private int contadorVehiculos = 0;

    private JTextField txtPlaca;
    private JComboBox<String> comboTipo;
    private JTable tablaVehiculos;
    private DefaultTableModel modeloTabla;

    public ParqueaderoApp() {
        listaVehiculos = new ArrayList<>();
        vehiculos2Ruedas = new Stack<>();
        vehiculos4Ruedas = new Stack<>();
        configurarVentana();
        inicializarComponentes();

        // Timer para actualizar la tabla cada 10 segundos
        new Timer(10000, e -> actualizarTabla()).start();
    }

    private void configurarVentana() {
        setTitle("Administración de Parqueadero");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        // Panel para ingreso de vehículos
        JPanel panelIngreso = new JPanel(new GridLayout(3, 2, 10, 10));

        panelIngreso.add(new JLabel("Placa:"));
        txtPlaca = new JTextField();
        panelIngreso.add(txtPlaca);

        panelIngreso.add(new JLabel("Tipo de Vehículo:"));
        comboTipo = new JComboBox<>(new String[]{"Bicicleta", "Ciclomotor", "Motocicleta", "Carro"});
        panelIngreso.add(comboTipo);

        JButton btnIngresar = new JButton("Ingresar Vehículo");
        btnIngresar.addActionListener(e -> ingresarVehiculo());
        panelIngreso.add(btnIngresar);

        // Panel con botones de opciones
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 10, 10));
        JButton btnVisualizarTabla = new JButton("Visualizar Tabla");
        JButton btnVisualizar2Ruedas = new JButton("Visualizar 2 Ruedas");
        JButton btnVisualizar4Ruedas = new JButton("Visualizar 4 Ruedas");
        JButton btnCantidadTotal = new JButton("Cantidad y Total a Pagar");
        JButton btnEliminarVehiculo = new JButton("Eliminar Vehículo");
        JButton btnSalir = new JButton("Salir");

        btnVisualizarTabla.addActionListener(e -> actualizarTabla());
        btnVisualizar2Ruedas.addActionListener(e -> visualizarVehiculos2Ruedas());
        btnVisualizar4Ruedas.addActionListener(e -> visualizarVehiculos4Ruedas());
        btnCantidadTotal.addActionListener(e -> mostrarCantidadYTotal());
        btnEliminarVehiculo.addActionListener(e -> eliminarVehiculo());
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnVisualizarTabla);
        panelBotones.add(btnVisualizar2Ruedas);
        panelBotones.add(btnVisualizar4Ruedas);
        panelBotones.add(btnCantidadTotal);
        panelBotones.add(btnEliminarVehiculo);
        panelBotones.add(btnSalir);

        // Tabla para mostrar los datos
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Número");
        modeloTabla.addColumn("Placa");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Hora Ingreso");
        modeloTabla.addColumn("Valor a Pagar");

        tablaVehiculos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaVehiculos);

        // Agregar componentes al marco principal
        add(panelIngreso, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void ingresarVehiculo() {
        String placa = txtPlaca.getText().trim();
        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese la placa del vehículo.");
            return;
        }

        String tipo = (String) comboTipo.getSelectedItem();
        LocalTime horaIngreso = LocalTime.now();

        contadorVehiculos++;
        RegistroVehiculo nuevoVehiculo = new RegistroVehiculo(contadorVehiculos, placa, tipo, horaIngreso);
        listaVehiculos.add(nuevoVehiculo);

        if (tipo.equals("Bicicleta") || tipo.equals("Ciclomotor") || tipo.equals("Motocicleta")) {
            vehiculos2Ruedas.push(nuevoVehiculo);
        } else if (tipo.equals("Carro")) {
            vehiculos4Ruedas.push(nuevoVehiculo);
        }

        actualizarTabla();
        txtPlaca.setText("");
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (RegistroVehiculo vehiculo : listaVehiculos) {
            modeloTabla.addRow(generarFila(vehiculo));
        }
    }

    private void visualizarVehiculos2Ruedas() {
        modeloTabla.setRowCount(0);
        for (RegistroVehiculo vehiculo : vehiculos2Ruedas) {
            modeloTabla.addRow(generarFila(vehiculo));
        }
    }

    private void visualizarVehiculos4Ruedas() {
        modeloTabla.setRowCount(0);
        for (RegistroVehiculo vehiculo : vehiculos4Ruedas) {
            modeloTabla.addRow(generarFila(vehiculo));
        }
    }

    private void mostrarCantidadYTotal() {
        int cantidad = listaVehiculos.size();
        int total = listaVehiculos.stream()
                .mapToInt(v -> calcularTarifa(v.getTipoVehiculo(), calcularTiempoEnParqueadero(v.getHoraIngreso())))
                .sum();
        JOptionPane.showMessageDialog(this, "Cantidad de vehículos: " + cantidad + "\nTotal a pagar: " + total + " COP");
    }

    private void eliminarVehiculo() {
        int filaSeleccionada = tablaVehiculos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int numeroVehiculo = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            listaVehiculos.removeIf(v -> v.getNumeroVehiculo() == numeroVehiculo);
            vehiculos2Ruedas.removeIf(v -> v.getNumeroVehiculo() == numeroVehiculo);
            vehiculos4Ruedas.removeIf(v -> v.getNumeroVehiculo() == numeroVehiculo);
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un vehículo para eliminar.");
        }
    }

    private Object[] generarFila(RegistroVehiculo vehiculo) {
        long minutos = calcularTiempoEnParqueadero(vehiculo.getHoraIngreso());
        int tarifa = calcularTarifa(vehiculo.getTipoVehiculo(), minutos);
        return new Object[]{
                vehiculo.getNumeroVehiculo(),
                vehiculo.getPlaca(),
                vehiculo.getTipoVehiculo(),
                vehiculo.getHoraIngreso().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                tarifa
        };
    }

    private long calcularTiempoEnParqueadero(LocalTime horaIngreso) {
        return Duration.between(horaIngreso, LocalTime.now()).toMinutes();
    }

    private int calcularTarifa(String tipo, long minutos) {
        return switch (tipo) {
            case "Bicicleta", "Ciclomotor" -> (int) minutos * 20;
            case "Motocicleta" -> (int) minutos * 30;
            case "Carro" -> (int) minutos * 60;
            default -> 0;
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ParqueaderoApp().setVisible(true));
    }
}
