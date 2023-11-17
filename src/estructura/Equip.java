package estructura;

public class Equip implements Comparable<Equip>{

    private String nombre; // Nombre del equipo
    private int puntuacion; // Puntuación

    public Equip(String nombre) {
        this.nombre = nombre;
        this.puntuacion = -1; // Una puntuación por defecto de -1 significa que el partido no se jugó
    }
    public Equip(String nombre,int a){
        this.nombre=nombre;
        this.puntuacion = a;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int compareTo(Equip equipo) {
        return Integer.compare(this.puntuacion, equipo.puntuacion);
    }

    public String toString() {
        return nombre + " (" + puntuacion + ")"; // Devuelve una representación de cadena del nombre del equipo y la puntuación
    }

    public String toSave() {
        return nombre + ": " + puntuacion; // Devuelve una cadena en formato "nombre;puntuación" para guardar en un archivo
    }

    public String getNombre() {
        return nombre;
    }

    public boolean haJugado() {return puntuacion != -1;}
}
