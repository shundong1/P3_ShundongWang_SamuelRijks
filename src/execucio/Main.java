package execucio;

import estructura.ArbreBinari;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String nomFitxer = null;

        int opcio;
        ArbreBinari arbreBinari=null;
        do {
            System.out.println("Opcions:");
            System.out.println("1. Crear un nou torneig");
            System.out.println("2. Carregar un torneig");
            System.out.print("Introdueix la teva opció (1 o 2): ");
            opcio = scanner.nextInt();

            if (opcio == 1) {

                List<String> nombresEquipos;
                do {
                    System.out.print("Introdueix els noms dels equips separats per ; : ");
                    String inputEquipos = scanner.next();
                    nombresEquipos = parseNombresEquipos(inputEquipos);
                } while (nombresEquipos == null);

                arbreBinari = new ArbreBinari(nombresEquipos);


                // Puedes realizar otras acciones según tus necesidades
            } else if (opcio == 2) {
                // Carregar un torneig
                System.out.print("Introdueix el nom del fitxer: ");
                String nombresFitxer = scanner.next();
                nomFitxer =nombresFitxer;

                System.out.print("Introdueix la profunditat: ");
                int profunditat = scanner.nextInt();

                arbreBinari = new ArbreBinari(nombresFitxer, profunditat);
                System.out.println("Mostrar información anterior");
                arbreBinari.mostrarArbre();
                System.out.println("El siguiente paso será borrar los datos anteriores e iniciar una nueva entrada de información");
                arbreBinari.ClaridadDeLosResultadosAnteriores();

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

        runMainProgram(arbreBinari,rondaActual,nomFitxer);


        scanner.close();
    }

    private static List<String> parseNombresEquipos(String input) {
        List<String> nombresEquipos;

        while (true) {
            String[] equiposArray = input.split(";");
            int numEquipos = equiposArray.length;

            // 检查是否是大于2的2的次方
            if (numEquipos > 2 && (numEquipos & (numEquipos - 1)) == 0) {
                nombresEquipos = new ArrayList<>(List.of(equiposArray));
                break;  // 输入符合要求，退出循环
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Has d'introduir una quantitat major a 2 i que sigui una potència de 2.");
                // 重新询问用户输入
                System.out.print("Introdueix els noms dels equips separats per ; : ");
                input = scanner.next();
            }
        }

        return nombresEquipos;
    }


    private static void runMainProgram(ArbreBinari arbreBinari,int ronda,String nomFitxer) throws Exception {
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
                if(ronda!=0){// Introducir resultados en la ronda actual
                // 在这里调用相应的方法
                //在这里我要先展示每一回合的每一个equib,然后询问客户然后读取客户的输入然后setPuntuacion，然后分数高的晋级
                arbreBinari.mostrar2(ronda);
                arbreBinari.ParaGanadorAvanza();
                }else{
                    System.out.println("El juego ha terminado y puedes guardarlo seleccionando el número tres");
                }
            } else if (opcion == 3) {
                // Guardar el árbol y salir
                // 在这里调用保存树到文件的方法
                if(nomFitxer==null){
                System.out.println("Escriu el nom del arxiu");

                System.out.print("Introdueix el nom del fitxer: ");
                nomFitxer = scanner.next();
                }
                arbreBinari.save(nomFitxer);

                System.out.println("Saliendo del programa. ¡Hasta luego!");
            } else {
                System.out.println("Opción no válida. Por favor, elija 1, 2 o 3.");
            }
            ronda=arbreBinari.rondaActual();

        } while (opcion != 3);
    }
}