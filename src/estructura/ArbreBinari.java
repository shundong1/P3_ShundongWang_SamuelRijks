package estructura;

import java.util.Scanner;

public class ArbreBinari {
    private class Node<T> {
        T contingut;
        Node<T> esq, dret;

        Node(T contingut) {
            this(contingut, null, null);
        }

        Node(T contingut, Node<T> esq, Node<T> dret) {
            this.contingut = contingut;
            this.esq = esq;
            this.dret = dret;
        }
    }
    private Node <Equip> arrel;
    public ArbreBinari(){
        arrel= null;
    }
    public void mostrar(int ronda){
        System.out.println("Ronda " + ronda + ":");
        mostrarRecursivament(arrel,1,ronda);
    }

    private void mostrarRecursivament(Node<Equip>node, int i, int ronda) {
        if(node!=null){

            if(i==ronda){
                System.out.println("Ronda"+ronda+": ");
            }
            mostrarRecursivament(node.esq,i+1,ronda);
            mostrarRecursivament(node.dret,i+1,ronda);
        }
    }
    // 输入当前轮次的结果
    public void introduirResultats(int ronda, Equip guanyador) {
        introduirResultatsRecursivament(arrel, 1, ronda, guanyador);
    }

    private void introduirResultatsRecursivament(Node<Equip> node, int nivell, int ronda, Equip guanyador) {
        if (node != null) {
            if (nivell == ronda) {
                node.contingut.setPuntuacion(guanyador.getPuntuacion());
            }
            introduirResultatsRecursivament(node.esq, nivell + 1, ronda, guanyador); // 递归左子树
            introduirResultatsRecursivament(node.dret, nivell + 1, ronda, guanyador); // 递归右子树
        }
    }
    //插入Equip节点
    public void insert(Equip equip){
        arrel = inserirRecursivament(arrel, equip);
    }

    private Node<Equip> inserirRecursivament(Node<Equip> arrel, Equip equip) {
        if(arrel==null){
            arrel = new Node<Equip>(equip);
        }
        if(equip.compareTo(arrel.contingut)<0){
            arrel.esq=inserirRecursivament(arrel.esq,equip);
        }else if(equip.compareTo(arrel.contingut)>0){
            arrel.dret=inserirRecursivament(arrel.dret,equip);
        }
        return arrel;
    }
    // 创建锦标赛
    public void crearTorneig(String nomTorneig, String[] nombresEquipos, int rounds) {
        int profunditat = calcularProfunditat(nombresEquipos.length);
        crearArbre(nombresEquipos, profunditat);

        System.out.println("锦标赛创建成功！");
    }


    // 计算树的深度
    private int calcularProfunditat(int numEquipos) {
        int profunditat = 0;
        int potencia = 1;

        while (potencia < numEquipos) {
            potencia *= 2;
            profunditat++;
        }

        return profunditat + 1;
    }

    // 创建适当尺寸的树
    private void crearArbre(String[] nombresEquipos, int profunditat) {
        arrel = null;

        for (String nombreEquipo : nombresEquipos) {
            Equip equipo = new Equip(nombreEquipo.trim());
            arrel = inserirRecursivament(arrel, equipo);
        }
    }

}
