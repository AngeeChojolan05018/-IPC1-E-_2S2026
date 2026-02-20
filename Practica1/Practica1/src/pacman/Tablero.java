package pacman;

import java.util.Random;

public class Tablero {
    private char[][] tablero;
    private int filas;
    private int columnas;
    private int premiosRestantes;
    private Random random = new Random();
    
    public Tablero(int filas, int columnas, int cantPremios, int cantParedes, int cantFantasmas) {
        this.filas = filas;
        this.columnas = columnas;
        this.tablero = new char[filas][columnas];
        this.premiosRestantes = cantPremios;
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = ' ';
            }
        }
        
        colocarElementos('X', cantParedes);
        colocarElementos('@', cantFantasmas);
        colocarPremios(cantPremios);
    }
    
    private void colocarElementos(char elemento, int cantidad) {
        int colocados = 0;
        while (colocados < cantidad) {
            int fila = random.nextInt(filas);
            int columna = random.nextInt(columnas);
            if (tablero[fila][columna] == ' ') {
                tablero[fila][columna] = elemento;
                colocados++;
            }
        }
    }
    
    private void colocarPremios(int total) {
        int simples = (int)(total * 0.8);
        for (int i = 0; i < simples; i++) {
            colocarElementos('0', 1);
        }
        for (int i = 0; i < total - simples; i++) {
            colocarElementos('$', 1);
        }
    }
    
public void mostrar() {
    String lineaGuiones = "-".repeat(columnas * 3 + 1);
    
    System.out.println(lineaGuiones);
    
    for (int i = 0; i < filas; i++) {
        System.out.print("|");
        for (int j = 0; j < columnas; j++) {
            System.out.print(" " + tablero[i][j] + " ");
        }
        System.out.println("|");
    }
    
    System.out.println(lineaGuiones);
}
    
    public boolean colocarPacman(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas && 
            tablero[fila][columna] == ' ') {
            tablero[fila][columna] = '<';
            return true;
        }
        return false;
    }
    
    public void moverPacman(int filaActual, int columnaActual, int nuevaFila, int nuevaColumna) {
        nuevaFila = (nuevaFila + filas) % filas;
        nuevaColumna = (nuevaColumna + columnas) % columnas;
        
        char contenido = tablero[nuevaFila][nuevaColumna];
        
        if (contenido != 'X') {
            tablero[filaActual][columnaActual] = ' ';
            tablero[nuevaFila][nuevaColumna] = '<';
        }
    }
    
    public char getContenido(int fila, int columna) {
        fila = (fila + filas) % filas;
        columna = (columna + columnas) % columnas;
        return tablero[fila][columna];
    }
    
    public void limpiarCasilla(int fila, int columna) {
        fila = (fila + filas) % filas;
        columna = (columna + columnas) % columnas;
        tablero[fila][columna] = ' ';
    }
    
    public boolean hayPremio(int fila, int columna) {
        char c = getContenido(fila, columna);
        return c == '0' || c == '$';
    }
    
    public int getPuntosPremio(int fila, int columna) {
        char premio = getContenido(fila, columna);
        if (premio == '0') {
            premiosRestantes--;
            return 10;
        } else if (premio == '$') {
            premiosRestantes--;
            return 15;
        }
        return 0;
    }
    
    public boolean hayFantasma(int fila, int columna) {
        return getContenido(fila, columna) == '@';
    }
    
    public boolean hayPared(int fila, int columna) {
        return getContenido(fila, columna) == 'X';
    }
    
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
    public int getPremiosRestantes() { return premiosRestantes; }
}
