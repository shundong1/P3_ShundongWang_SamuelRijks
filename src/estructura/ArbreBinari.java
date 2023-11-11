package estructura;

import java.io.*;

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

        // 添加 preorder 方法
        public void preorder(BufferedWriter buw, int nivel) throws Exception {
            if (contingut != null) {
                // 输出当前节点的内容
                for (int i = 0; i < nivel; i++) {
                    buw.write("    "); // 缩进，使树形结构更清晰
                }
                buw.write(contingut.toString());
                buw.newLine();

                // 递归左右子树
                if (esq != null) {
                    esq.preorder(buw, nivel + 1);
                }
                if (dret != null) {
                    dret.preorder(buw, nivel + 1);
                }
            }
        }
    }

    private Node <Equip> arrel;

    // 构造函数，接受一个字符串数组作为参数
    public ArbreBinari(String[] nombresEquipos) {
        arrel = crearArbre(nombresEquipos);
    }

    public ArbreBinari(String nombresEquipos,int a){
        Equip aa = new Equip(nombresEquipos,a);
        arrel=  new Node<>(aa);
    }

    public int calculateProfunditat(int a) {
        return calculateProfunditatRecursivament(arrel);
    }

    // 递归计算树的深度
    private int calculateProfunditatRecursivament(Node<Equip> node) {
        if (node == null) {
            return 0;
        } else {
            int esqProfunditat = calculateProfunditatRecursivament(node.esq);
            int dretProfunditat = calculateProfunditatRecursivament(node.dret);
            return Math.max(esqProfunditat, dretProfunditat) + 1;
        }
    }

    // 创建树的方法
    private Node<Equip> crearArbre(String[] nombresEquipos) {
        arrel = null;
        int requiredNodes = nombresEquipos.length;
        int[] index = new int[]{0};
        return crearArbreRecursivament(nombresEquipos, index);
    }

    // 递归创建树的方法
    private Node<Equip> crearArbreRecursivament(String[] nombresEquipos, int[] index) {
        Node<Equip> node = new Node<>(null);
        if (index[0] < nombresEquipos.length) {
            node.contingut = new Equip(nombresEquipos[index[0]].trim());
            index[0]++;
        }
        return node;
    }

    // 计算树的深度


    // 插入节点的方法
    public boolean inserir(Node<Equip> parentNode, Equip equip, int direccio) {
        if (parentNode == null || parentNode.contingut == null || direccio < 0 || direccio > 1) {
            return false;
        }

        Node<Equip> fill = new Node<>(equip);
        if (direccio == 0) {
            parentNode.esq = fill;
        } else {
            parentNode.dret = fill;
        }

        return true;
    }

    // 显示指定轮次的比赛结果
    public void mostrar(int ronda) {
        System.out.println("Ronda " + ronda + ":");
        mostrarRecursivament(arrel, 1, ronda);
    }

    // 递归显示比赛结果
    private void mostrarRecursivament(Node<Equip> node, int i, int ronda) {
        if (node != null) {
            if (i == ronda) {
                System.out.println("Ronda" + ronda + ": ");
            }
            mostrarRecursivament(node.esq, i + 1, ronda);
            mostrarRecursivament(node.dret, i + 1, ronda);
        }
    }

    // 显示指定节点和轮次的比赛结果
    public void mostrar(Node<Equip> node, int ronda) {
        System.out.println("Ronda " + ronda + ":");
        mostrarRecursivament(node, 1, ronda);
    }

    // 保存树到文件
    public void save(String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            saveRecursivament(arrel, printWriter);
            printWriter.close();
        } catch (IOException e) {
            System.out.println("保存树到文件时出现错误：" + e.getMessage());
        }
    }

    // 递归保存树到文件
    private void saveRecursivament(Node<Equip> node, PrintWriter printWriter) {
        if (node != null) {
            String contingutStr = (node.contingut != null) ? node.contingut.toSave() : "NULL_CONTENT";
            printWriter.println(contingutStr);
            saveRecursivament(node.esq, printWriter);
            saveRecursivament(node.dret, printWriter);
        }
    }

    // 获取当前轮次
    public int rondaActual() {
        return calculateProfunditat();
    }

    // 请求比赛结果的方法
    public void demanarResultats() {
        // 实现你的逻辑
    }
}
