package P2;

public class Paciente {
    private String nombre;       // Nombre del paciente
    private int edad;            // Edad del paciente
    private String afiliacion;   // Tipo de afiliación (POS o PC)
    private String condicion;    // Condición especial (embarazo, limitación motriz, etc.)
    private int numeroTurno;     // Número de turno asignado

    public Paciente(String nombre, int edad, String afiliacion, String condicion, int numeroTurno) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa.");
        }
        if (afiliacion == null || afiliacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La afiliación no puede estar vacía.");
        }
        if (numeroTurno <= 0) {
            throw new IllegalArgumentException("El número de turno debe ser mayor a 0.");
        }

        this.nombre = nombre.trim();
        this.edad = edad;
        this.afiliacion = afiliacion.trim();
        this.condicion = (condicion != null) ? condicion.trim() : "Ninguna";
        this.numeroTurno = numeroTurno;
    }

    // Getters
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

    // Setter
    public void setNumeroTurno(int numeroTurno) {
        if (numeroTurno <= 0) {
            throw new IllegalArgumentException("El número de turno debe ser mayor a 0.");
        }
        this.numeroTurno = numeroTurno;
    }

    public boolean esPrioritario() {
        return edad < 12 || edad > 60 || "embarazo".equalsIgnoreCase(condicion) || "limitación motriz".equalsIgnoreCase(condicion);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", afiliacion='" + afiliacion + '\'' +
                ", condicion='" + condicion + '\'' +
                ", numeroTurno=" + numeroTurno +
                '}';
    }
}
