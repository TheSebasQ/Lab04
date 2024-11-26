package P2;

public class Paciente {
   private String nombre;
    private int edad;
    private String afiliacion;
    private String condicion;
    private int numeroTurno;

    public Paciente(String nombre, int edad, String afiliacion, String condicion, int numeroTurno) {
        this.nombre = nombre;
        this.edad = edad;
        this.afiliacion = afiliacion;
        this.condicion = condicion;
        this.numeroTurno = numeroTurno;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getAfiliacion() {
        return afiliacion;
    }

    public String getCondicion() {
        return condicion;
    }

    public int getNumeroTurno() {
        return numeroTurno;
    }

    public void setNumeroTurno(int numeroTurno) {
        this.numeroTurno = numeroTurno;
    }

}