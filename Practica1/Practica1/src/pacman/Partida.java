package pacman;

import java.util.Scanner;

public class Partida {
    private String nombreUsuario;
    private Tablero tablero;
    private int filaPacman;
    private int columnaPacman;
    private int vidas;
    private int puntaje;
    private boolean juegoActivo;
    private Historial historial;
    private Scanner scanner = new Scanner(System.in);
    
    public Partida(String nombreUsuario, Tablero tablero, int filaInicial, int columnaInicial, Historial historial) {
        this.nombreUsuario = nombreUsuario;
        this.tablero = tablero;
        this.filaPacman = filaInicial;
        this.columnaPacman = columnaInicial;
        this.vidas = 3;
        this.puntaje = 0;
        this.juegoActivo = true;
        this.historial = historial;
        
        if (!tablero.colocarPacman(filaInicial, columnaInicial)) {
            colocarPacmanEnPosicionLibre();
        }
    }
    
    private void colocarPacmanEnPosicionLibre() {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (tablero.getContenido(i, j) == ' ') {
                    filaPacman = i;
                    columnaPacman = j;
                    tablero.colocarPacman(i, j);
                    return;
                }
            }
        }
    }
    
    public void jugar() {
        while (juegoActivo) {
            mostrarEstado();
            System.out.println("8: ARRIBA    5: ABAJO    6: DERECHA    4: IZQUIERDA    F: PAUSA");
            System.out.print("MOVIMIENTO: ");
            String entrada = scanner.next();
            char tecla = entrada.charAt(0);
            
            if (tecla == 'F' || tecla == 'f') {
                mostrarMenuPausa();
            } else {
                procesarMovimiento(tecla);
            }
            
            if (tablero.getPremiosRestantes() == 0) {
                System.out.println("¡FELICIDADES! Has ganado la partida.");
                juegoActivo = false;
            }
            
            if (vidas == 0) {
                System.out.println("¡GAME OVER!");
                juegoActivo = false;
            }
        }
        
        historial.agregarPartida(nombreUsuario, puntaje);
    }
    
    private void mostrarEstado() {
        System.out.println("\nUSUARIO: " + nombreUsuario);
        System.out.println("PUNTEO: " + puntaje);
        System.out.println("VIDAS: " + vidas);
        tablero.mostrar();
    }
    
    private void procesarMovimiento(char tecla) {
        int nuevaFila = filaPacman;
        int nuevaColumna = columnaPacman;
        
        switch (tecla) {
            case '8': nuevaFila--; break;
            case '5': nuevaFila++; break;
            case '6': nuevaColumna++; break;
            case '4': nuevaColumna--; break;
            default:
                System.out.println("Tecla inválida");
                return;
        }
        
        nuevaFila = (nuevaFila + tablero.getFilas()) % tablero.getFilas();
        nuevaColumna = (nuevaColumna + tablero.getColumnas()) % tablero.getColumnas();
        
        if (tablero.hayPared(nuevaFila, nuevaColumna)) {
            System.out.println("¡Hay una pared! No puedes pasar.");
            return;
        }
        
        if (tablero.hayFantasma(nuevaFila, nuevaColumna)) {
            vidas--;
            System.out.println("¡Te encontraste con un fantasma! Vidas restantes: " + vidas);
            tablero.limpiarCasilla(nuevaFila, nuevaColumna);
        }
        
        if (tablero.hayPremio(nuevaFila, nuevaColumna)) {
            int puntos = tablero.getPuntosPremio(nuevaFila, nuevaColumna);
            puntaje += puntos;
            System.out.println("¡Premio! +" + puntos + " puntos");
        }
        
        tablero.moverPacman(filaPacman, columnaPacman, nuevaFila, nuevaColumna);
        filaPacman = nuevaFila;
        columnaPacman = nuevaColumna;
    }
    
    private void mostrarMenuPausa() {
        System.out.println("\n**PAUSA**");
        System.out.println("POR FAVOR, SELECCIONE UNA OPCIÓN");
        System.out.println("1. REGRESAR");
        System.out.println("2. TERMINAR PARTIDA");
        System.out.print("OPCIÓN: ");
        
        int opcion = scanner.nextInt();
        
        if (opcion == 2) {
            juegoActivo = false;
        }
    }
}
