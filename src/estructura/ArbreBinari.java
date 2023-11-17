package estructura;

import java.io.*;
import java.util.*;

public class ArbreBinari implements Serializable {


    private class Node {
        public Equip getContingut() {
            return contingut;
        }

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

        // Añadir el método preorder
        private void preorderWrite(BufferedWriter buw) throws Exception {
            // Escribe el contenido del nodo actual, o una línea en blanco si está vacío
            if (contingut != null) {
                buw.write(contingut.toSave());
            } else {
                buw.write("_"); // Utilice "_" para el contenido vacío
            }

            if (esq != null) {
                buw.newLine();
                esq.preorderWrite(buw);
            }

            if (dret != null) {
                buw.newLine();
                dret.preorderWrite(buw);
            }

        }

    }

    private Node arrel;

    public int getProfunditat() {
        return profunditat;
    }

    private int profunditat;

    // Constructor que toma un array de cadenas como argumentos
    public ArbreBinari(List<String> nombresEquipos) {
        // Este crea un nuevo torneo, un nuevo archivo, y crea el torneo basado en el número de equipos de entrada

        this.profunditat = (int) ((Math.log(nombresEquipos.size()) / Math.log(2)) + 1);
        int a = profunditat;
        arrel=crearArbreRecursivament(nombresEquipos,a);

    }

    public ArbreBinari(String filename, int profunditat) throws IOException {
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
        Node node = new Node(null);

        if (linia.equals("_") && ronda >= 0) {
            node=new Node (null);
        } else if (ronda < 0) {
            return null;
        } else if (!Objects.equals(linia, "_") && ronda >= 0) {

            // Si la línea actual no es "_", analiza el nombre del equipo y la puntuación y crea el nodo
            String[] parts = linia.split(":");

            if (parts.length != 2) {
                throw new IOException("Formato de línea no válido: " + linia);
            }

            String nomEquip = parts[0].trim();
            int puntuacio = Integer.parseInt(parts[1].trim());

            Equip equip = new Equip(nomEquip, puntuacio);
            node = new Node(equip);
        }

        // Construir subárboles izquierdo y derecho
        if (ronda > 0) {
            node.esq = preorderLoad(bur, ronda - 1);
            node.dret = preorderLoad(bur, ronda - 1);
        }

        return node;
    }

    private Node crearArbreRecursivament(List<String> nomsEquips, int profunditat) {
        if (profunditat <= 0 || nomsEquips.isEmpty()) {
            return null;
        }

        Node node = new Node(null);
        // Construir recursivamente los subárboles izquierdo y derecho
        node.esq = crearArbreRecursivament(nomsEquips, profunditat - 1);
        node.dret = crearArbreRecursivament(nomsEquips, profunditat - 1);

        // Al recorrer el último nodo del árbol, crea un Equip para reemplazarlo.
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
            String equipoSeleccionado = nomsEquips.remove(indiceAleatorio);

            return equipoSeleccionado;
        } else {
            // Maneja el caso en que la lista está vacía
            System.out.println("La lista de equipos está vacía.");
            return null;
        }
    }


    // Mostrar los resultados de la ronda especificada
    public void mostrar(int ronda) {
        mostrarRecursivament(arrel, 1, ronda);
    }


    private void mostrarRecursivament(Node node, int i, int ronda) {
        if (node != null) {
            if (i == ronda+1) {

                System.out.println("Ronda " + (profunditat+1 - i) + ": " + node.contingut);
            }
            mostrarRecursivament(node.dret, i + 1, ronda);
            mostrarRecursivament(node.esq, i + 1, ronda);

        }
    }

    // Guardar el árbol en un archivo
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




    // Obtener la ronda actual
    public int rondaActual() {
        return calculateProfunditatRecursivament(arrel);
    }

    // Calcular recursivamente la profundidad del árbol
    private int calculateProfunditatRecursivament(Node node) {
        if (node == null|| node.contingut != null) {
            return 0;
        } else {
            int esqProfunditat = calculateProfunditatRecursivament(node.esq);
            int dretProfunditat = calculateProfunditatRecursivament(node.dret);
            return Math.max(esqProfunditat, dretProfunditat) + 1;
        }
    }

    // Método de solicitud de resultados
    public void demanarResultats() {
        if(arrel != null){
            arrel.contingut.toSave();
        }
    }

    public void mostrar2(int ronda) {
        System.out.println("Ronda " + (profunditat - ronda) + ":");
        mostrarRecursivament2(arrel, 1, ronda);
    }

    // Visualización recursiva de los resultados de la carrera
    private void mostrarRecursivament2(Node node, int i, int ronda) {
        Scanner scanner = new Scanner(System.in);
        if (node != null) {
            if (i == ronda + 1) {
                System.out.println("Equipo " + node.contingut.getNombre() + " - Ronda " + (profunditat - ronda) + " - Puntuación: ");
                while (true) {
                    try {
                        int puntuacion = scanner.nextInt();
                        node.contingut.setPuntuacion(puntuacion);
                        break; // Salir del bucle porque la entrada es un entero válido
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Ingresa un valor numérico válido.");
                        // Borrar entradas no válidas
                        scanner.next();
                        System.out.println("Equipo " + node.contingut.getNombre() + " - Ronda " + (profunditat - ronda) + " - Puntuación: ");
                    }
                }
            }
            mostrarRecursivament2(node.esq, i + 1, ronda);
            mostrarRecursivament2(node.dret, i + 1, ronda);
        }
    }
    public Node ParaGanadorAvanza(){
        Node result = GanadorAvanza(arrel);
        if (result != null) {
            arrel = result;
        }
        return result;
    }

    private Node GanadorAvanza(Node node){

        if(node!=null){
            if(node.contingut==null && node.dret.contingut!=null && node.esq.contingut!=null){
                if(node.esq.contingut.compareTo(node.dret.contingut)>0){
                    node.esq.contingut.setPuntuacion(-1);
                    return new Node(node.esq.contingut, node.esq, node.dret);
                } else {
                    node.dret.contingut.setPuntuacion(-1);
                    return new Node(node.dret.contingut, node.esq, node.dret);
                }
            }
            Node esq = GanadorAvanza(node.esq);
            Node dret = GanadorAvanza(node.dret);
            return new Node(node.contingut, esq, dret);
        }

        return null;
    }

}