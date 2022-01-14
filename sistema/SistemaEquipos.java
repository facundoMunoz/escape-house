package sistema;

import estructuras.ArbolAVL;
import estructuras.TablaHash;
import estructuras.Grafo;
import objetos.*;

public class SistemaEquipos {

	private static String opcionesMenu() {
		return "1. Mostrar información de equipo\n" + "2. Posibles desafíos\n" + "3. Jugar desafío\n"
				+ "4. Pasar a habitación\n" + "5. Puede salir\n" + "0. Volver";
	}

	public static void menuEquipos(TablaHash equipos, ArbolAVL desafios, ArbolAVL habitaciones, Grafo mapa) {
		int opcionMenu;

		do {
			System.out.println("Ingrese una opción: ");
			System.out.println(opcionesMenu());
			opcionMenu = SistemaJuego.pedirCodigo();

			switch (opcionMenu) {
			case 1:
				System.out.println(mostrarEquipo(equipos));
				break;
			case 2:
				System.out.println("Falta implementar");
				break;
			case 3:
				System.out.println("Falta implementar");
				break;
			case 4:
				System.out.println(pasarAHabitacion(equipos, habitaciones, mapa));
				break;
			case 5:
				System.out.println(puedeSalir(equipos));
				break;
			case 0:
				break;
			default:
				System.out.println("Opción no contemplada");
				break;
			}
			System.out.println();
		} while (opcionMenu != 0);

	}
	
	private static String mostrarEquipo(TablaHash equipos) {
		System.out.println("Ingrese el nombre del equipo:");
		String nombre = SistemaJuego.pedirNombre();
		String habitacion = "El código no corresponde a un equipo cargado";

		if (equipos.pertenece(nombre)) {
			habitacion = equipos.getObjeto(nombre).toString();
		}

		return habitacion;
	}
	
	private static String pasarAHabitacion(TablaHash equipos, ArbolAVL habitaciones, Grafo mapa) {
		// Cargar equipo
		System.out.println("Ingrese el nombre del equipo:");
		String nombre = SistemaJuego.pedirNombre();
		Equipo equipo = (Equipo)equipos.getObjeto(nombre);
		// Cargar habitación
		System.out.println("Ingrese el código de la habitación:");
		int codigoHabitacion = SistemaJuego.pedirCodigo();
		Habitacion habitacionLlegada = (Habitacion)habitaciones.getObjeto(codigoHabitacion);
		
		String puede;
		
		if(equipo != null && habitacionLlegada != null) {
			if (mapa.existeArco(equipo.getHabitacion(), habitacionLlegada)) {
				if (equipo.getPuntajeActual() >= (int)mapa.etiquetaArco(equipo.getHabitacion(), habitacionLlegada)) {
					// Si es adyacente y el puntaje actual alcanza el equipo cambia de habitación reiniciando el puntaje actual
					equipo.setHabitacion(habitacionLlegada);
					equipo.setPuntajeActual(0);
					puede = "Equipo " + equipo.getNombre() + " se mueve a la habitación:\n" + habitacionLlegada.toString();
				} else {
					puede = "El puntaje es insuficiente";
				}
			} else {
				puede = "Las habitaciones no son contiguas";
			}
		} else {
			puede = "El equipo o la habitación no existen";
		}
		
		return puede;
	}
	
	private static String puedeSalir(TablaHash equipos) {
		System.out.println("Ingrese el nombre del equipo:");
		String nombre = SistemaJuego.pedirNombre();
		Equipo equipo = (Equipo)equipos.getObjeto(nombre);
		String puede = "El equipo no puede salir";
		
		if (equipo != null && (equipo.getPuntajeAcumulado() >= equipo.getPuntajeSalida()) && equipo.getHabitacion().getSalidaExterior()) {
			puede = "El equipo puede salir";
		}
		
		return puede;
	}
	
}
