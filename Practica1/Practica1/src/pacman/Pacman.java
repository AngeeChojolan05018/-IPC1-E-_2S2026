package pacman;

import java.util.Scanner;
import java.util.Random;

public class Pacman {
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    private static Historial historial = new Historial();
    
    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\nUSUARIO: " + (historial.getUltimoUsuario() != null ? historial.getUltimoUsuario() : ""));
            System.out.println("PUNTEO: " + historial.getUltimoPuntaje());
            System.out.println("VIDAS: 3");
            System.out.println("\n===MENU DE INICIO ====");
            System.out.println("1. Iniciar Juego");
            System.out.println("2. Historial de partidas");
            System.out.println("3. Salir");
            System.out.print("POR FAVOR, INGRESE LOS SIGUIENTES VALORES: ");
            
            opcion = leerOpcion(1, 3);
            
            switch (opcion) {
                case 1:
                    iniciarJuego();
                    break;
                case 2:
                    historial.mostrarHistorial();
                    break;
                case 3:
                    System.out.println("¡Gracias por jugar!");
                    break;
            }
        } while (opcion != 3);
    }
    
    private static int leerOpcion(int min, int max) {
        int opcion;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("Error. Ingrese un número válido: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            if (opcion < min || opcion > max) {
                System.out.print("Opción fuera de rango. Intente nuevamente: ");
            }
        } while (opcion < min || opcion > max);
        return opcion;
    }
    
    private static void iniciarJuego() {
        System.out.print("\nINGRESE SU NOMBRE DE USUARIO: ");
        String nombre = scanner.next();
        
        System.out.println("\n==== ESPECIFICAR TABLERO ====");
        System.out.println("POR FAVOR, INGRESE LOS SIGUIENTES VALORES");
        System.out.println("TABLERO: P (Pequeno 5x6)");
        System.out.println("         G (Grande 10x10)");
        System.out.print("SELECCIONE: ");
        String tipoTablero = scanner.next().toUpperCase();
        
        while (!tipoTablero.equals("P") && !tipoTablero.equals("G")) {
            System.out.print("TABLERO INVÁLIDO. SELECCIONE P o G: ");
            tipoTablero = scanner.next().toUpperCase();
        }
        
        int filas = tipoTablero.equals("P") ? 5 : 10;
        int columnas = tipoTablero.equals("P") ? 6 : 10;
        int totalEspacios = filas * columnas;
        
        System.out.print("PREMIOS [1-" + (int)(totalEspacios * 0.4) + "]: ");
        int cantidadPremios = scanner.nextInt();
        while (cantidadPremios < 1 || cantidadPremios > (int)(totalEspacios * 0.4)) {
            System.out.print("VALOR INVÁLIDO. INGRESE [1-" + (int)(totalEspacios * 0.4) + "]: ");
            cantidadPremios = scanner.nextInt();
        }
        
        System.out.print("PAREDES [1-" + (int)(totalEspacios * 0.2) + "]: ");
        int cantidadParedes = scanner.nextInt();
        while (cantidadParedes < 1 || cantidadParedes > (int)(totalEspacios * 0.2)) {
            System.out.print("VALOR INVÁLIDO. INGRESE [1-" + (int)(totalEspacios * 0.2) + "]: ");
            cantidadParedes = scanner.nextInt();
        }
        
        System.out.print("TRAMPAS [1-" + (int)(totalEspacios * 0.2) + "]: ");
        int cantidadFantasmas = scanner.nextInt();
        while (cantidadFantasmas < 1 || cantidadFantasmas > (int)(totalEspacios * 0.2)) {
            System.out.print("VALOR INVÁLIDO. INGRESE [1-" + (int)(totalEspacios * 0.2) + "]: ");
            cantidadFantasmas = scanner.nextInt();
        }
        
        Tablero tablero = new Tablero(filas, columnas, cantidadPremios, cantidadParedes, cantidadFantasmas);
        tablero.mostrar();
        
        System.out.println("\n**ESPECIFICAR TABLERO**");
        System.out.println("POR FAVOR, INGRESE LA POSICION INICIAL DE JUEGO");
        System.out.print("FILA: ");
        int filaPacman = scanner.nextInt();
        System.out.print("COLUMNA: ");
        int columnaPacman = scanner.nextInt();
        
        Partida partida = new Partida(nombre, tablero, filaPacman, columnaPacman, historial);
        partida.jugar();
    }
}
