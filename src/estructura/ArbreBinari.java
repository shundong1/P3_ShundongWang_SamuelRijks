package estructura;

import java.io.*;
import java.util.*;

public class ArbreBinari implements Serializable {
    private class Node {
        Equip contingut;
        Node esq, dret;

        Node(Equip contingut) {
            this(contingut, null, null);
        }

        Node(Equip contingut, Node esq, Node dret) {
            this.contingut = contingut;
            this.esq = esq;
            this.dret = dret;
        }

        // 添加 preorder 方法
        private void preorderWrite(BufferedWriter buw) throws Exception {
            if (contingut != null) {
                buw.write(contingut.toSave());
                if(esq != null ){
                    buw.newLine();
                    esq.preorderWrite(buw);
                }
                if(dret != null){
                    buw.newLine();
                    dret.preorderWrite(buw);
                }

            }
        }
    }

    private Node arrel;

    private int profunditat;

    // 构造函数，接受一个字符串数组作为参数
    public ArbreBinari(List<String> nombresEquipos) {
        //这个是创建一个新的锦标赛，新的文件，根据输入团队的数量创建比赛

        this.profunditat = (int) ((Math.log(nombresEquipos.size()) / Math.log(2)) + 1);;
        arrel=crearArbreRecursivament(nombresEquipos,profunditat);

    }

    public ArbreBinari(String filename, int profunditat) throws IOException {
        // 读取文件内容并创建树
        // Llegeix el contingut del fitxer i crea un arbre
        BufferedReader bur = new BufferedReader(new FileReader(filename));
        this.profunditat = profunditat;
        arrel = preorderLoad(bur, profunditat-1);

        // Si arrel és nul, vol dir que la lectura del fitxer ha fallat o que el fitxer està buit.
        if (arrel == null) {
            System.out.println("Problema en llegir dades del fitxer i crear un arbre");
        }
    }

    private Node preorderLoad(BufferedReader bur, int ronda) throws IOException {

            String linia = bur.readLine();

            if(linia == null || ronda <= 0){
                return null;
            }

            if(linia.equals("_")){
                Node node = new Node(null);
                node.esq = preorderLoad(bur, ronda - 1);
                node.dret = preorderLoad(bur, ronda - 1);
            }
            // Divide la línea en nombre del equipo y puntuación
            String[] parts = linia.split(";");
            String nomEquip = parts[0];
            int puntuacio = Integer.parseInt(parts[1]);

            // Crea el nodo con el equipo y su puntuación
            Equip equip = new Equip(nomEquip, puntuacio);
            Node node = new Node(equip);


            // Construye el árbol de forma recursiva
            node.esq = preorderLoad(bur, ronda - 1);
            node.dret = preorderLoad(bur, ronda - 1);

            return node;

    }

   private Node crearArbreRecursivament(List<String> nomsEquips, int profunditat) {
        if (profunditat <= 0 || nomsEquips.isEmpty()) {
            return null;
        }

        Node node = new Node(null);
        // 递归构建左右子树
        node.esq = crearArbreRecursivament(nomsEquips, profunditat - 1);
        node.dret = crearArbreRecursivament(nomsEquips, profunditat - 1);

        // 当遍历到树的最后节点时，创建 Equip 替代该节点
        if (profunditat == 1 && node.esq == null && node.dret == null) {
            node.contingut = new Equip(obtenerEquipoAleatorio(nomsEquips));
        }

        return node;
    }


    private String obtenerEquipoAleatorio(List<String> nomsEquips) {
        // Verifica si la lista no está vacía
        if (!nomsEquips.isEmpty()) {
            // Crea una instancia de la clase Random
            Random rand = new Random();

            // Genera un índice aleatorio dentro del rango de la lista
            int indiceAleatorio = rand.nextInt(nomsEquips.size());

            // Obtiene el elemento en el índice aleatorio
            return nomsEquips.get(indiceAleatorio);
        } else {
            // Maneja el caso en que la lista está vacía
            System.out.println("La lista de equipos está vacía.");
            return null;
        }
    }



    // 插入节点的方法
    public boolean inserir(Node parentNode, Equip equip, int direccio) {
        if (parentNode == null || parentNode.contingut == null || direccio < 0 || direccio > 1) {
            return false;
        }

        Node fill = new Node(equip);
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
    private void mostrarRecursivament(Node node, int i, int ronda) {
        if (node != null) {
            if (i == ronda+1) {
                System.out.println("Ronda" + ronda + ": "+node.contingut);
            }
            mostrarRecursivament(node.esq, i + 1, ronda);
            mostrarRecursivament(node.dret, i + 1, ronda);
        }
    }

    // 显示指定节点和轮次的比赛结果
    public void mostrar(Node node, int ronda) {
        System.out.println("Ronda " + ronda + ":");
        mostrarRecursivament1(node, 1, ronda);
    }

    private void mostrarRecursivament1(Node node, int i, int ronda) {
        if (node != null) {
            if (i == ronda + 1) {
                System.out.println("Equipo " + node.contingut.getNombre() + " - Ronda " + ronda + " - Puntuación: ");
                try (Scanner scanner = new Scanner(System.in)) {
                    int puntuacion = scanner.nextInt();
                    node.contingut.setPuntuacion(puntuacion);
                } catch (InputMismatchException e) {
                    System.out.println("Error: Ingresa un valor numérico válido.");
                }
            }
            mostrarRecursivament1(node.esq, i + 1, ronda);
            mostrarRecursivament1(node.dret, i + 1, ronda);
        }
    }

    // 保存树到文件
    // 保存树到文件
    public void save(String filename) throws Exception {

        BufferedWriter buw = null;
        try {
            buw = new BufferedWriter(new FileWriter(filename));//crea el buw sobre l'arxiu
            arrel.preorderWrite(buw);
            buw.close();

        } catch (IOException e) {
            System.err.println("saveToTextFile failed: " + e);
            System.exit(0);
        }
    }


    // 递归保存树到文件
    private void saveRecursivament(Node node, PrintWriter printWriter) {
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
    private int calculateProfunditatRecursivament(Node node) {
        if (node == null|| node.contingut != null) {
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
    //可以删除
    public void mostrarArbre() {
        mostrarArbreRecursivament(arrel, 0);
    }

    private void mostrarArbreRecursivament(Node node, int nivel) {
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
