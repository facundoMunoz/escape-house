package objetos;

import estructuras.TablaHash;
import estructuras.Lista;

public class Equipo {

	// Atributos
	private final String nombre;
	private int puntajeSalida;
	private int puntajeAcumulado;
	private int puntajeActual;
	private Habitacion habitacion;
	private TablaHash desafiosResueltos;
	private Lista habitacionesVisitadas;

	// Constructores
	public Equipo(String nombre, int puntajeSalida, int puntajeAcumulado, int puntajeActual, Habitacion habitacion) {
		this.nombre = nombre;
		this.puntajeSalida = puntajeSalida;
		this.puntajeAcumulado = puntajeAcumulado;
		this.puntajeActual = puntajeActual;
		this.habitacion = habitacion;
		this.desafiosResueltos = new TablaHash();
		this.habitacionesVisitadas = new Lista();
	}

	// Observadores
	public String getNombre() {
		return this.nombre;
	}

	public int getPuntajeSalida() {
		return this.puntajeSalida;
	}

	public int getPuntajeAcumulado() {
		return this.puntajeAcumulado;
	}

	public int getPuntajeActual() {
		return this.puntajeActual;
	}

	public Habitacion getHabitacion() {
		return this.habitacion;
	}

	public TablaHash getDesafiosResueltos() {
		return this.desafiosResueltos;
	}
	
	public Lista getHabitacionesVisitadas() {
		return this.habitacionesVisitadas;
	}

	// Modificadores
	public void setPuntajeSalida(int puntajeSalida) {
		this.puntajeSalida = puntajeSalida;
	}

	public void setPuntajeAcumulado(int puntajeAcumulado) {
		this.puntajeAcumulado = puntajeAcumulado;
	}

	public void setPuntajeActual(int puntajeActual) {
		this.puntajeActual = puntajeActual;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	// Propias del tipo
	@Override
	public String toString() {
		return "" + this.nombre;
	}
	
	public String fullString() {
		return "Nombre: " + this.nombre +
				"\nPuntaje para salir: " + this.puntajeSalida +
				"\nPuntaje total: " + this.puntajeAcumulado +
				"\nPuntaje actual: " + this.puntajeActual + 
				"\nHabitación actual: " + this.habitacion.getCodigo();
	}

}
