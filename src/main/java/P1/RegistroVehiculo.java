package P1;


import java.time.LocalTime;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USUARIA
 */
public class RegistroVehiculo {
    private int numeroVehiculo;
    private String placa;
    private String tipoVehiculo;
    private LocalTime horaIngreso;

    public RegistroVehiculo(int numeroVehiculo, String placa, String tipoVehiculo, LocalTime horaIngreso) {
        this.numeroVehiculo = numeroVehiculo;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
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

    public LocalTime getHoraIngreso() {
        return horaIngreso;
    }
}


