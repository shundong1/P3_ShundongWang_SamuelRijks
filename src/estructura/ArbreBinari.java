package estructura;

import java.io.*;
import java.util.Scanner;

public class ArbreBinari implements Serializable {
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

    // 创建锦标赛
    public void crearTorneig(String nomTorneig, String[] nombresEquipos, int rounds) {
        int profunditat = rounds;
        crearArbre(nombresEquipos, profunditat);
        guardarTorneigEnArchivo(nomTorneig);

        System.out.println("锦标赛创建成功！");
    }


    // 创建适当尺寸的树
    private void crearArbre(String[] nombresEquipos, int profunditat) {
        arrel = null;
        // 计算需要的节点数量
        int requiredNodes = (int) Math.pow(2, profunditat);
        int[] index = new int[]{0};  // 使用数组来模拟引用传递
        arrel = crearArbreRecursivament(1, requiredNodes, nombresEquipos, index);
    }

    private Node<Equip> crearArbreRecursivament(int i, int requiredNodes, String[] nombresEquipos, int[] index) {
        if (i > requiredNodes) {
            return null;
        }
        // 递归构建左右子树
        Node<Equip> node = new Node<Equip>(null);
        node.esq = crearArbreRecursivament(2 * i, requiredNodes, nombresEquipos, index);
        node.dret = crearArbreRecursivament(2 * i + 1, requiredNodes, nombresEquipos, index);
        if (node.esq == null && node.dret == null) {
            // 如果左右子树都为 null，说明是叶子节点，可以替换
            if (index[0] < nombresEquipos.length) {
                // 替换为 Equip 对象
                node.contingut = new Equip(nombresEquipos[index[0]].trim());
                index[0]++;
            }
        }
        return node;
    }


    public void guardarTorneigEnArchivo(String nomTorneig) {
        try {
            FileWriter fileWriter = new FileWriter(nomTorneig + ".txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // 将树的信息写入文件
            guardarRecursivament(arrel, printWriter);

            printWriter.close();
        } catch (IOException e) {
            System.out.println("保存树到文件时出现错误：" + e.getMessage());
        }
    }

    private void guardarRecursivament(Node<Equip> node, PrintWriter printWriter) {
        if (node != null) {
            // 如果 node.contingut 为 null，则使用占位符表示
            String contingutStr = (node.contingut != null) ? node.contingut.toSave() : "NULL_CONTENT";

            printWriter.println(contingutStr);

            guardarRecursivament(node.esq, printWriter);
            guardarRecursivament(node.dret, printWriter);
        }
    }


    // 在 ArbreBinari 类中添加以下方法
    public ArbreBinari cargarTorneigDesdeArchivo(String nombreArchivo) {
        ArbreBinari arbre = new ArbreBinari();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            arbre = (ArbreBinari) ois.readObject();
            System.out.println("锦标赛从文件加载成功。");
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定的文件。");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("加载文件时出现错误。");
            e.printStackTrace();
        }
        return arbre;
    }
    /////////////打印所有的树
    // 在 ArbreBinari 类中添加以下方法
    public void mostrarArbre() {
        mostrarArbreRecursivament(arrel, 0);
    }

    private void mostrarArbreRecursivament(Node<Equip> node, int nivel) {
        if (node != null) {
            mostrarArbreRecursivament(node.dret, nivel + 1);

            for (int i = 0; i < nivel; i++) {
                System.out.print("    "); // 缩进，使树形结构更清晰
            }

            System.out.println(node.contingut);

            mostrarArbreRecursivament(node.esq, nivel + 1);
        }
    }


}
