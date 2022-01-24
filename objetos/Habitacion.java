package objetos;

public class Habitacion {

	// Atributos
	private final int codigo;
	private String nombre;
	private int planta;
	private int metrosCuadrados;
	private boolean salidaExterior;

	// Constructores
	public Habitacion(int codigo) {
		this.codigo = codigo;
	}

	public Habitacion(int codigo, String nombre, int planta, int metrosCuadrados, boolean salidaExterior) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.planta = planta;
		this.metrosCuadrados = metrosCuadrados;
		this.salidaExterior = salidaExterior;
	}

	// Observadores
	public int getCodigo() {
		return this.codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getPlanta() {
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

	public void setPlanta(int planta) {
		this.planta = planta;
	}

	public void setMetrosCuadrados(int metrosCuadrados) {
		this.metrosCuadrados = metrosCuadrados;
	}

	public void setSalidaExterior(boolean salidaExterior) {
		this.salidaExterior = salidaExterior;
	}

	// Propias de tipo
	@Override
	public String toString() {
		return "Codigo: " + this.codigo + "\nNombre: " + this.nombre + "\nPlanta: " + this.planta
				+ "\nMetros cuadrados: " + this.metrosCuadrados + "\nTiene salida al exterior: " + this.salidaExterior;

	}

}
