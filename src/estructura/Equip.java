package estructura;

public class Equip implements Comparable<Equip>{



    private String nombre; // 队伍名称
    private int puntuacion; // 得分

    public Equip(String nombre) {
        this.nombre = nombre;
        this.puntuacion = -1; // 默认分数为-1表示比赛未进行
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
        return nombre + " (" + puntuacion + ")"; // 返回队伍名称和得分的字符串表示
    }

    public String toSave() {
        return nombre + ";" + puntuacion; // 以 "名称;得分" 格式返回字符串，用于保存到文件
    }

    public boolean haJugado() {
        return puntuacion != -1; // 返回一个布尔值，表示队伍是否已经进行比赛
    }

    public int getPuntuacion() {
        return puntuacion;
    }
    public String getNombre() {
        return nombre;
    }
}
