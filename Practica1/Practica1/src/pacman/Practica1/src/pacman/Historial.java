package pacman;

public class Historial {
    private String[] usuarios;
    private int[] puntajes;
    private int contador;
    private String ultimoUsuario;
    private int ultimoPuntaje;
    
    public Historial() {
        usuarios = new String[100];
        puntajes = new int[100];
        contador = 0;
        ultimoUsuario = "";
        ultimoPuntaje = 0;
    }
    
    public void agregarPartida(String usuario, int puntaje) {
        if (contador < usuarios.length) {
            for (int i = contador; i > 0; i--) {
                usuarios[i] = usuarios[i-1];
                puntajes[i] = puntajes[i-1];
            }
            usuarios[0] = usuario;
            puntajes[0] = puntaje;
            contador++;
            ultimoUsuario = usuario;
            ultimoPuntaje = puntaje;
        }
    }
    
    public void mostrarHistorial() {
        if (contador == 0) {
            System.out.println("\n**HISTORIAL DE PARTIDAS**");
            System.out.println("No hay partidas en el historial.");
            return;
        }
        
        System.out.println("\n**HISTORIAL DE PARTIDAS**");
        System.out.println("No.\tUSUARIO\t\tPUNTEO");
        System.out.println("----------------------------------------");
        for (int i = 0; i < contador; i++) {
            System.out.println((i+1) + ".\t" + usuarios[i] + "\t\t" + puntajes[i]);
        }
    }
    
    public String getUltimoUsuario() {
        return contador > 0 ? usuarios[0] : "";
    }
    
    public int getUltimoPuntaje() {
        return contador > 0 ? puntajes[0] : 0;
    }
}
