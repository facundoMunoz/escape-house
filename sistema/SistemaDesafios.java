package sistema;

import estructuras.ArbolAVL;
import estructuras.TablaHash;
import estructuras.Lista;
import objetos.*;

public class SistemaDesafios {

	private static String opcionesMenu() {
		return "1. Mostrar desafío\n" + "2. Mostrar desafíos resueltos\n" + "3. Verificar desafío resuelto\n"
				+ "4. Mostrar desafíos tipo\n" + "0. Volver";
	}

	public static void menuDesafios(ArbolAVL desafios, TablaHash equipos) {
		int opcionMenu;

		do {
			System.out.println("Ingrese una opción: ");
			System.out.println(opcionesMenu());
			opcionMenu = SistemaJuego.pedirCodigo();

			switch (opcionMenu) {
			case 1:
				System.out.println(mostrarDesafio(desafios));
				break;
			case 2:
				System.out.println(mostrarDesafiosResueltos(desafios, equipos));
				break;
			case 3:
				System.out.println(verificarDesafioResuelto(desafios, equipos));
				break;
			case 4:
				System.out.println(mostrarDesafiosTipo(desafios));
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

	private static String mostrarDesafio(ArbolAVL desafios) {
		System.out.println("Ingrese el puntaje:");
		int codigo = SistemaJuego.pedirCodigo();
		String desafio = "El código no corresponde a un desafío cargado";

		if (desafios.pertenece(codigo)) {
			desafio = ((Desafio) desafios.getObjeto(codigo)).fullString();
		}

		return desafio;
	}

	private static String mostrarDesafiosResueltos(ArbolAVL desafios, TablaHash equipos) {
		System.out.println("Ingrese el nombre del equipo:");
		String codigoEquipo = SistemaJuego.pedirNombre();
		String listadoResueltos = "El equipo ingresado no existe";
		Equipo equipo = (Equipo) equipos.getObjeto(codigoEquipo);

		if (equipo != null) {
			// Si el equipo existe listamos sus desafíos resueltos
			TablaHash desafiosResueltos = equipo.getDesafiosResueltos();

			if (!desafiosResueltos.esVacia()) {
				listadoResueltos = "Desafíos resueltos por '" + equipo.getNombre() + "':\n";
				listadoResueltos += desafiosResueltos.toString();
			} else {
				listadoResueltos = "El equipo no tiene desafíos resueltos";
			}
		}

		return listadoResueltos;
	}

	private static String verificarDesafioResuelto(ArbolAVL desafios, TablaHash equipos) {
		// Cargar equipo
		System.out.println("Ingrese el nombre del equipo:");
		String codigoEquipo = SistemaJuego.pedirNombre();
		Equipo equipo = (Equipo) equipos.getObjeto(codigoEquipo);
		// Cargar desafío
		System.out.println("Ingrese el puntaje del desafío:");
		int puntajeDesafio = SistemaJuego.pedirCodigo();
		Desafio desafio = (Desafio) desafios.getObjeto(puntajeDesafio);

		String resuelto;

		if (equipo != null && desafio != null) {
			TablaHash desafiosResueltos = equipo.getDesafiosResueltos();

			if (desafiosResueltos.pertenece(desafio.getPuntaje())) {
				resuelto = "El equipo resolvió el desafío";
			} else {
				resuelto = "El equipo NO resolvió el desafío";
			}
		} else {
			resuelto = "El equipo o el desafío no existen";
		}

		return resuelto;
	}

	private static String mostrarDesafiosTipo(ArbolAVL desafios) {
		// Cargar tipo desafío
		System.out.println("Ingrese el tipo de desafío:");
		String tipoDesafio = SistemaJuego.pedirNombre();
		// Cargar intervalo
		System.out.println("Ingrese el puntaje mínimo:");
		int puntajeMinimo = SistemaJuego.pedirCodigo();
		System.out.println("Ingrese el puntaje máximo:");
		int puntajeMaximo = SistemaJuego.pedirCodigo();
		Lista posiblesDesafios = desafios.listarRango(puntajeMinimo, puntajeMaximo);
		// Longitud
		int longitudLista = posiblesDesafios.longitud();

		Lista listaFinalDesafios = new Lista();
		String salida = "No hay desafíos de tipo " + tipoDesafio + " entre " + puntajeMinimo + " y " + puntajeMaximo
				+ " puntos:\n";

		for (int posicion = 1; posicion <= longitudLista; posicion++) {
			Desafio desafioActual = (Desafio) posiblesDesafios.recuperar(posicion);

			if (desafioActual.getTipo().equals(tipoDesafio)) {
				listaFinalDesafios.insertar(desafioActual, listaFinalDesafios.longitud() + 1);
			}
		}

		if (!listaFinalDesafios.esVacia()) {
			salida = "Desafíos de tipo " + tipoDesafio + " entre " + puntajeMinimo + " y " + puntajeMaximo
					+ " puntos:\n" + listaFinalDesafios.toString();
		}

		return salida;
	}

}
