package objetos;

public class Desafio {

    // Atributos
    private String nombre;
    private final int puntaje;
    private String tipo;

    // Constructor
    public Desafio(String nombre, int puntaje, String tipo) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.tipo = tipo;
    }

    // Observadores
    public String getNombre() {
        return this.nombre;
    }

    public int getPuntaje() {
        return this.puntaje;
    }

    public String getTipo() {
        return this.tipo;
    }

    // Modificadores
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
