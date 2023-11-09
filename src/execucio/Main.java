package execucio;

import estructura.ArbreBinari;
import estructura.Equip;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        ArbreBinari arbre = new ArbreBinari();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 1 && choice != 2) {
            System.out.println("Seleccione la operación:");
            System.out.println("1. Crear un nuevo torneo");
            System.out.println("2. Gestionar un torneo existente");
            choice = scanner.nextInt();

            if (choice != 1 && choice != 2) {
                System.out.println("Las opciones son sólo 1 ó 2. Por favor, seleccione de nuevo.");
            }
        }

        if (choice == 1) {
            // 创建新的锦标赛
            System.out.println("Introduzca el nombre del torneo (que se convertirá en el nombre del archivo):");
            String nomTorneig = scanner.next();

            System.out.println("Introduzca los nombres de todos los equipos, separados por punto y coma:");
            String input = scanner.next();
            String[] nombresEquipos = input.split(";");

            System.out.println("Introduzca el número de rondas:");
            int rounds = scanner.nextInt();

            arbre.crearTorneig(nomTorneig, nombresEquipos, rounds);
        } else if (choice == 2) {
            // 从文件中读取二叉树
            // TODO: 添加从文件读取的逻辑，可以调用相应的方法
        } else {
            System.out.println("Las opciones son sólo 1 ó 2.");
            return;
        }

        // 显示当前轮次
        System.out.println("请输入当前轮次：");
        int currentRound = scanner.nextInt();
        arbre.mostrar(currentRound);

        // 输入当前轮次的比赛结果
        System.out.println("请输入比赛结果：");
        // TODO: 添加输入比赛结果的逻辑，可以调用相应的方法
        String winnerName = scanner.next();
        Equip guanyador = new Equip(winnerName);
        arbre.introduirResultats(currentRound, guanyador);

        // 最终保存树到文件
        // TODO: 添加保存树到文件的逻辑，可以调用相应的方法
    }
}