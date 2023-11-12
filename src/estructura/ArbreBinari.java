package estructura;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public ArbreBinari(List<String> nombresEquipos,String nombresFitxer) {
        //这个是创建一个新的锦标赛，新的文件，根据输入团队的数量创建比赛

        int profunditat = 2 * nombresEquipos.size() - 1;
        arrel=crearArbreRecursivament(nombresEquipos,profunditat);

        // 创建新文件
        File nuevoArchivo = new File(nombresFitxer);

        try {
            if (nuevoArchivo.createNewFile()) {
                System.out.println("新文件已创建：" + nuevoArchivo.getName());
            } else {
                System.out.println("文件已存在，无需创建新文件。");
            }
        } catch (IOException e) {
            System.out.println("创建文件时出现错误：" + e.getMessage());
            e.printStackTrace();
        }

    }

    public ArbreBinari(String nombresFitxer, int a) {
        // 读取文件内容并创建树
        arrel = llegirFitxerICrearArbre(nombresFitxer, a);

        // 如果 arrel 为 null，说明文件读取失败或者文件为空
        if (arrel == null) {
            System.out.println("从文件中读取数据并创建树时出现问题。");
        }
    }

    private Node<Equip> llegirFitxerICrearArbre(String nomFitxer, int a) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomFitxer))) {
            // 读取文件的每一行
            String linia = br.readLine();

            // 你需要根据文件内容的格式来解析数据并构建树
            // 下面的示例代码仅用于说明，并假设文件中每行包含一个队伍的名称
            String[] nomsEquips = linia.split(","); // 假设数据使用逗号分隔
            int profunditat = (int) Math.pow(2, a + 2) - 1;

            // 创建树
            arrel=crearArbreRecursivament(new ArrayList<>(Arrays.asList(nomsEquips)), profunditat);
            return arrel;
        } catch (IOException e) {
            System.out.println("读取文件时出现错误：" + e.getMessage());
            return null;
        }
    }

    private Node<Equip> crearArbreRecursivament(List<String> nomsEquips, int profunditat) {
        if (profunditat <= 0 || nomsEquips.isEmpty()) {
            return null;
        }

        Node<Equip> node = new Node<>(null);
        // 递归构建左右子树
        node.esq = crearArbreRecursivament(nomsEquips, profunditat - 1);
        node.dret = crearArbreRecursivament(nomsEquips, profunditat - 1);

        // 当遍历到树的最后节点时，创建 Equip 替代该节点
        if (profunditat == 1) {
            node.contingut = new Equip(nomsEquips.remove(0).trim());
        }

        return node;
    }







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
    // 保存树到文件
    public void save(String filename) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filename))) {
            saveRecursivament(arrel, printWriter);
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
        return calculateProfunditatRecursivament(arrel);
    }

    // 递归计算树的深度
    private int calculateProfunditatRecursivament(Node<Equip> node) {
        if (node == null|| node.contingut == null) {
            return 0;
        } else {
            int esqProfunditat = calculateProfunditatRecursivament(node.esq);
            int dretProfunditat = calculateProfunditatRecursivament(node.dret);
            return Math.max(esqProfunditat, dretProfunditat) + 1;
        }
    }

    // 请求比赛结果的方法
    public void demanarResultats() {
        // 实现你的逻辑
    }
}
