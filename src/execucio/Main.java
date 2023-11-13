package execucio;

import estructura.ArbreBinari;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcio;
        ArbreBinari arbreBinari=null;
        do {
            System.out.println("Opcions:");
            System.out.println("1. Crear un nou torneig");
            System.out.println("2. Carregar un torneig");
            System.out.print("Introdueix la teva opció (1 o 2): ");
            opcio = scanner.nextInt();

            if (opcio == 1) {
                // Crear un nou torneig
                System.out.print("Introdueix el nom del fitxer: ");
                String nombresFitxer = scanner.next();

                List<String> nombresEquipos;
                do {
                    System.out.print("Introdueix els noms dels equips separats per ; : ");
                    String inputEquipos = scanner.next();
                    nombresEquipos = parseNombresEquipos(inputEquipos);
                } while (nombresEquipos == null);

                 arbreBinari = new ArbreBinari(nombresEquipos, nombresFitxer);


                // Puedes realizar otras acciones según tus necesidades
            } else if (opcio == 2) {
                // Carregar un torneig
                System.out.print("Introdueix el nom del fitxer: ");
                String nombresFitxer = scanner.next();

                System.out.print("Introdueix la profunditat: ");
                int profunditat = scanner.nextInt();

                 arbreBinari = new ArbreBinari(nombresFitxer, profunditat);

                // Puedes realizar otras acciones según tus necesidades
            } else {
                System.out.println("Opció no vàlida. Torna a intentar.");
            }

        } while (opcio != 1 && opcio != 2);
        //打印树
        arbreBinari.mostrarArbre();
        //显示现在是第几轮
        int rondaActual = arbreBinari.rondaActual();
        System.out.println("Ronda actual: " + rondaActual);

        runMainProgram(arbreBinari,rondaActual);


        scanner.close();
    }

    private static List<String> parseNombresEquipos(String input) {
        String[] equiposArray = input.split(";");
        if (equiposArray.length < 2) {
            System.out.println("Has d'introduir almenys dos equips.");
            return null;
        } else {
            return new ArrayList<>(List.of(equiposArray));
        }
    }
    private static void runMainProgram(ArbreBinari arbreBinari,int ronda) {
        Scanner scanner = new Scanner(System.in);


        int opcion;

        do {
            System.out.println("Opciones:");
            System.out.println("1 muestra una ronda");
            System.out.println("2 introducir resultados en la ronda actual");
            System.out.println("3 guardar el árbol y salir");
            System.out.print("Elija una opción (1, 2 o 3): ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            if (opcion == 1) {
                // Muestra una ronda
                arbreBinari.mostrar(ronda);

            } else if (opcion == 2) {
                // Introducir resultados en la ronda actual
                // 在这里调用相应的方法
                //在这里我要先展示每一回合的每一个equib,然后询问客户然后读取客户的输入然后setPuntuacion，然后分数高的晋级
            } else if (opcion == 3) {
                // Guardar el árbol y salir
                // 在这里调用保存树到文件的方法
                System.out.println("Saliendo del programa. ¡Hasta luego!");
            } else {
                System.out.println("Opción no válida. Por favor, elija 1, 2 o 3.");
            }
        } while (opcion != 3);
    }
}





