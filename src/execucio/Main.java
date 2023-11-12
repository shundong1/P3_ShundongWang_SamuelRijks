package execucio;

import estructura.ArbreBinari;
import estructura.Equip;
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

        //显示现在是第几轮
        int rondaActual = arbreBinari.rondaActual();
        System.out.println("Ronda actual: " + rondaActual);


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
}





