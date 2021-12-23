package objetos;

public class Habitacion {

    // Atributos
    private final int codigo;
    private String nombre;
    private byte planta;
    private int metrosCuadrados;
    private boolean salidaExterior;

    // Constructores
    public Habitacion(int codigo) {
        this.codigo = codigo;
    }

    public Habitacion(int codigo, String nombre, byte planta, int metrosCuadrados, boolean salidaExterior) {
        this.codigo = codigo;
    }

    // Observadores
    public int getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public byte getPlanta() {
        return this.planta;
    }

    public int getMetrosCuadrados() {
        return this.metrosCuadrados;
    }

    public boolean getSalidaExterior() {
        return this.salidaExterior;
    }

    // Modificadores
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPlanta(byte planta) {
        this.planta = planta;
    }

    public void setMetrosCuadrados(int metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    public void setSalidaExterior(boolean salidaExterior) {
        this.salidaExterior = salidaExterior;
    }

}
