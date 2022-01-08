package sistema;
import estructuras.*;

public class SistemaHabitaciones {
	
	private static String opcionesMenu() {
		return "1. Mostrar habitación\n" +
				"2. Habitaciones contiguas\n" +
				"3. Es posible llegar\n" +
				"4. Minimo puntaje\n" +
				"5. Sin pasar por\n" +
				"0. Volver";
	}
	
	public static void menuHabitaciones(ArbolAVL habitaciones) {
		int opcionMenu;
		
		do {
			System.out.println("Ingrese una opción: ");
			System.out.println(opcionesMenu());
			opcionMenu = SistemaJuego.pedirCodigo();
			
			switch(opcionMenu) {
				case 1:
					System.out.println(mostrarHabitacion(habitaciones));
					break;
				case 2:
					break;
				case 0:
					break;
				default:
					System.out.println("Opción no contemplada");
					break;
			}
		} while(opcionMenu != 0);

	}
	
	private static String mostrarHabitacion(ArbolAVL habitaciones) {
		System.out.println("Ingrese el código:");
		int codigo = SistemaJuego.pedirCodigo();
		String habitacion = "El código no corresponde a una habitación cargada";
		
		if(habitaciones.getObjeto(codigo) != null) {
			habitacion = habitaciones.getObjeto(codigo).toString();
		}
		
		return habitacion;
	}
	
}
