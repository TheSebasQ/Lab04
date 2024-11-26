package P1;

import java.time.LocalTime;

public class RegistroVehiculo {
    private int numeroVehiculo;       
    private String placa;            
    private String tipoVehiculo;    
    private LocalTime horaIngreso;   

    public RegistroVehiculo(int numeroVehiculo, String placa, String tipoVehiculo, LocalTime horaIngreso) {
        if (numeroVehiculo <= 0) {
            throw new IllegalArgumentException("El número del vehículo debe ser mayor a 0.");
        }
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía.");
        }
        if (tipoVehiculo == null || tipoVehiculo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de vehículo no puede estar vacío.");
        }
        if (horaIngreso == null) {
            throw new IllegalArgumentException("La hora de ingreso no puede ser nula.");
        }

        this.numeroVehiculo = numeroVehiculo;
        this.placa = placa.trim();
        this.tipoVehiculo = tipoVehiculo.trim();
        this.horaIngreso = horaIngreso;
    }

    public int getNumeroVehiculo() {
        return numeroVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    @Override
    public String toString() {
        return "RegistroVehiculo{" +
                "numeroVehiculo=" + numeroVehiculo +
                ", placa='" + placa + '\'' +
                ", tipoVehiculo='" + tipoVehiculo + '\'' +
                ", horaIngreso=" + horaIngreso +
                '}';
    }
}
