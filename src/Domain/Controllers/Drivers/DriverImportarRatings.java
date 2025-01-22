package Domain.Controllers.Drivers;

import Domain.Classes.Item;
import Domain.Classes.Usuario;
import Persistence.ImportarRatings;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class DriverImportarRatings {
    private static HashMap<Integer, Usuario> users;
    private static HashMap<Integer, Item> items;
    private static String archivoRatings;
    private static Scanner scan = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("#################################################");
        System.out.println("#####     Indique qué juego de pruebas      #####");
        System.out.println("#####            desea ejecutar:            #####");
        System.out.println("       (CONSULTAR MANUAL DE INSTRUCCIONES        ");
        System.out.println("                 EN CASO DE DUDA)                ");
        System.out.println("#################################################");
        System.out.println("#####    [1] Eficiencia 250 	            #####");
        System.out.println("#####    [2] Eficiencia 750 	            #####");
        System.out.println("#####    [3] Eficiencia 2250 	            #####");
        System.out.println("#####    [4] Eficiencia 6750 	            #####");
        System.out.println("#####    [5] Cerrar y Salir                 #####");
        System.out.println("#################################################");
        switch(scan.nextInt()){

            case 1:
                archivoRatings = "archivos/movies250/ratings.db.csv";
                break;
            case 2:
                archivoRatings = "archivos/movies750/ratings.db.csv";
                break;
            case 3:
                archivoRatings = "archivos/movies2250/ratings.db.csv";
                break;
            case 4:
                archivoRatings = "archivos/movies6750/ratings.db.csv";
                break;
            case 5:
                scan.close();
                break;
        }

        users = new HashMap<>();
        items = new HashMap<>();

        ImportarRatings importar = new ImportarRatings();

        long startTime = System.currentTimeMillis();
        importar.archivoCsvIn(archivoRatings);
        long endTime = System.currentTimeMillis();



        users = importar.getUsuarios();
        items = importar.getItemsValorados();

        System.out.println("Fichero leído correctamente!!");

        if (Objects.equals(archivoRatings, "../../../archivos/prueba.db.csv")) {
            for (var user : users.entrySet()) {
                System.out.println("Usuario con id = " + user.getValue().getId());
                System.out.println("Ha valorado:");
                for (var num : user.getValue().getValoraciones().entrySet()) {
                    System.out.println("El item " + num.getKey() + " con rating: " + num.getValue());
                }
                System.out.println();
            }
        }

        long tiempoTotal = endTime -  startTime;
        System.out.println("Ha tardado: " + tiempoTotal + "ms");

    }
}

