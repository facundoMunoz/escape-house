package sistema;

import estructuras.ArbolAVL;
import estructuras.TablaHash;
import estructuras.Grafo;
import estructuras.Lista;
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
				System.out.println(posiblesDesafios(equipos, habitaciones, mapa, desafios));
				break;
			case 3:
				System.out.println(jugarDesafio(equipos, desafios));
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

	private static String posiblesDesafios(TablaHash equipos, ArbolAVL habitaciones, Grafo mapa, ArbolAVL desafios) {
		// Cargar equipo
		System.out.println("Ingrese el nombre del equipo:");
		String nombre = SistemaJuego.pedirNombre();
		Equipo equipo = (Equipo) equipos.getObjeto(nombre);
		// Cargar desafío
		System.out.println("Ingrese el código de la habitación:");
		int codigo = SistemaJuego.pedirCodigo();
		Habitacion habitacion = (Habitacion) habitaciones.getObjeto(codigo);

		String desafiosPosibles = "";

		if (equipo != null && habitacion != null) {
			int puntajeMinimo = (int) mapa.etiquetaArco(equipo.getHabitacion(), habitacion);
			int puntajeActualEquipo = equipo.getPuntajeActual();

			if (puntajeMinimo != -1) {
				// Buscamos los desafíos posibles
				Lista listaDesafios = desafios.listar();
				int longitudListaDesafios = listaDesafios.longitud();

				for (int posicion = 0; posicion <= longitudListaDesafios; posicion++) {
					Desafio desafioActual = (Desafio) listaDesafios.recuperar(posicion);

					if ((int) desafioActual.getPuntaje() + puntajeActualEquipo >= puntajeMinimo) {
						desafiosPosibles += desafioActual.toString() + "/n";
					}
				}
				
				if(desafiosPosibles.equals("")) {
					desafiosPosibles = "No hay desafíos cuyo puntaje alcance para pasar de habitación";
				} else {
					desafiosPosibles = "Desafíos cuyo puntaje alcanza para pasar de habitación:/n" + desafiosPosibles;
				}
			} else {
				desafiosPosibles = "La habitación no es adyacente";
			}
		} else {
			desafiosPosibles = "El equipo o la habitación no existen";
		}

		return desafiosPosibles;
	}

	private static String jugarDesafio(TablaHash equipos, ArbolAVL desafios) {
		// Cargar equipo
		System.out.println("Ingrese el nombre del equipo:");
		String nombre = SistemaJuego.pedirNombre();
		Equipo equipo = (Equipo) equipos.getObjeto(nombre);
		// Cargar desafío
		System.out.println("Ingrese el puntaje del desafío:");
		int puntaje = SistemaJuego.pedirCodigo();
		Desafio desafio = (Desafio) desafios.getObjeto(puntaje);

		String desafioJugado;

		if (equipo != null && desafio != null) {
			if (equipo.getDesafiosResueltos().insertar(puntaje, desafio)) {
				// Si el desafío no fue jugado anteriormente se suma el puntaje correspondiente
				equipo.setPuntajeActual(equipo.getPuntajeActual() + puntaje);
				equipo.setPuntajeAcumulado(equipo.getPuntajeAcumulado() + puntaje);
				desafioJugado = "Equipo '" + nombre + "' juega desafío " + puntaje + " - " + desafio.getNombre();
			} else {
				desafioJugado = "El desafio " + puntaje + " - " + desafio.getNombre() + " ya fue jugado por el equipo '"
						+ nombre + "'";
			}
		} else {
			desafioJugado = "El equipo o el desafío no existen";
		}

		return desafioJugado;
	}

	private static String pasarAHabitacion(TablaHash equipos, ArbolAVL habitaciones, Grafo mapa) {
		// Cargar equipo
		System.out.println("Ingrese el nombre del equipo:");
		String nombre = SistemaJuego.pedirNombre();
		Equipo equipo = (Equipo) equipos.getObjeto(nombre);
		// Cargar habitación
		System.out.println("Ingrese el código de la habitación:");
		int codigoHabitacion = SistemaJuego.pedirCodigo();
		Habitacion habitacionLlegada = (Habitacion) habitaciones.getObjeto(codigoHabitacion);

		String puede;

		if (equipo != null && habitacionLlegada != null) {
			if (mapa.existeArco(equipo.getHabitacion(), habitacionLlegada)) {
				if (equipo.getPuntajeActual() >= (int) mapa.etiquetaArco(equipo.getHabitacion(), habitacionLlegada)) {
					// Si es adyacente y el puntaje actual alcanza el equipo cambia de habitación
					// reiniciando el puntaje actual
					equipo.setHabitacion(habitacionLlegada);
					equipo.setPuntajeActual(0);
					puede = "Equipo '" + equipo.getNombre() + "' se mueve a la habitación:\n"
							+ habitacionLlegada.toString();
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
		Equipo equipo = (Equipo) equipos.getObjeto(nombre);
		String puede = "El equipo no puede salir";

		if (equipo != null && (equipo.getPuntajeAcumulado() >= equipo.getPuntajeSalida())
				&& equipo.getHabitacion().getSalidaExterior()) {
			puede = "El equipo puede salir";
		}

		return puede;
	}

}
