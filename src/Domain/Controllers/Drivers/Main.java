package Domain.Controllers.Drivers;

import Domain.Classes.Pair;
import Domain.Classes.Usuario;
import Domain.Controllers.SlopeOne;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static Integer userId;
    private static String archivoRating;
    private static String archivoItems;

    public static void main(String[] args) {
        try{
            System.out.println("###############################################");
            System.out.println("############   ¿Qué desea hacer?   ############");
            System.out.println("###############################################");
            System.out.println("###    [1] Pedir Recomendaciones 	        ###");
            System.out.println("###    [2] Valorar Recomendaciones          ###");
            System.out.println("###        y evaluar su calidad             ###");
            System.out.println("###    [3] Cerrar y Salir                   ###");
            System.out.println("###############################################");

            switch(scan.nextInt()){
                case 1:
                    PedirRecomendaciones();
                    break;
                case 2:
                    ValorarRecomendaciones();
                    break;
                case 3:
                    scan.close();
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void PedirRecomendaciones() {
        try {
            System.out.println("#################################################");
            System.out.println("##### Indique el algoritmo de recomendación #####");
            System.out.println("#####          que desea ejecutar:          #####");
            System.out.println("#################################################");
            System.out.println("#####    [1] Collaborative Filtering        #####");
            System.out.println("#####    [2] Content-Based Filtering 	    #####");
            System.out.println("#####    [3] Cerrar y Salir                 #####");
            System.out.println("#################################################");

            switch(scan.nextInt()) {
                case 1:
                    CollaborativeFiltering();
                    break;
                case 2:
                    ContentBased();
                    break;
                case 3:
                    scan.close();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private static void CollaborativeFiltering() {
       try {
           System.out.println("Introduzca el valor 'k', para el algoritmo k-Means:");
           Integer k = scan.nextInt();

           System.out.println("#################################################");
           System.out.println("#####     Indique qué juego de pruebas      #####");
           System.out.println("#####            desea ejecutar:            #####");
           System.out.println("       (CONSULTAR MANUAL DE INSTRUCCIONES        ");
           System.out.println("                 EN CASO DE DUDA)                ");
           System.out.println("#################################################");
           System.out.println("#####    [1] Eficiencia 250                 #####");
           System.out.println("#####    [2] Eficiencia 750                 #####");
           System.out.println("#####    [3] Eficiencia 2250                #####");
           System.out.println("#####    [4] Eficiencia 6750                #####");
           System.out.println("#####    [5] Cerrar y Salir                 #####");
           System.out.println("#################################################");

           String nomArchivoCsv = "";
           String directorioRatings = "";
           switch(scan.nextInt()) {
               case 1:
                   nomArchivoCsv = "archivos/movies250/ratings.combinats.csv";
                   directorioRatings ="archivos/movies250";
                   break;
               case 2:
                   nomArchivoCsv = "archivos/movies750/ratings.combinats.csv";
                   directorioRatings ="archivos/movies750";
                   break;
               case 3:
                   nomArchivoCsv = "archivos/2250/ratings.combinats.csv";
                   directorioRatings ="archivos/movies2250";
                   break;
               case 4:
                   nomArchivoCsv = "archivos/6750/ratings.combinats.csv";
                   directorioRatings ="archivos/movies6750";
                   break;
               case 5:
                   scan.close();
                   break;
           }
           System.out.println("Introduzca el ID del usuario activo:");
           System.out.println("(Sobre el que pediremos recomendaciones)");
           userId = scan.nextInt();

           System.out.println("Introduzca el numero de recomendaciones que desea:");
           int numRec = scan.nextInt();

           DriverKmeans kmeans = new DriverKmeans(userId,k,nomArchivoCsv);

           DriverSlopeOne slopeOne = new DriverSlopeOne();
           for (Pair<Integer,Double> val : slopeOne.getNRecomendaciones(userId, kmeans.getCluster(),directorioRatings,numRec)) {
               System.out.println("Recomendamos el item " + val.getFirst() + " que predecimos que valorará con " + val.getSecond());
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    private static void ContentBased() {
        Integer k = 0;

        System.out.println("#################################################");
        System.out.println("#####     Indique qué juego de pruebas      #####");
        System.out.println("#####            desea ejecutar:            #####");
        System.out.println("       (CONSULTAR MANUAL DE INSTRUCCIONES        ");
        System.out.println("                 EN CASO DE DUDA)                ");
        System.out.println("#################################################");
        System.out.println("#####    [1] Eficiencia 250     	        #####");
        System.out.println("#####    [2] Eficiencia 750 	            #####");
        System.out.println("#####    [3] Eficiencia 2250 	            #####");
        System.out.println("#####    [4] Eficiencia 6750     	        #####");
        System.out.println("#####    [5] Cerrar y Salir                 #####");
        System.out.println("#################################################");

        String directorioRatings = "";
        switch(scan.nextInt()) {
            case 1:

                directorioRatings = "archivos/movies250";
                break;
            case 2:

                directorioRatings = "archivos/movies750";
                break;
            case 3:

                directorioRatings = "archivos/movies2250";
                break;
            case 4:

                directorioRatings = "archivos/movies6750";
                break;
            case 5:
                scan.close();
                break;
        }

        System.out.println("Introduzca el ID del usuario activo:");
        System.out.println("(Sobre el que pediremos recomendaciones)");
        userId = scan.nextInt();

        System.out.println("Introduzca el numero de recomendaciones que desea:");
        k = scan.nextInt();

        DriverKnn knn = new DriverKnn(userId,k, directorioRatings);
        LinkedList<Pair<Integer,Double>> l = knn.getRecomendaciones();
        for (Pair<Integer, Double> it : l) {
            System.out.println("--> Recomendamos el ítem " + it.getFirst() + " con rating predecido de " + it.getSecond());
        }
    }
    private static void ValorarRecomendaciones() {
        DriverValorarRecomendacion valoracionCalidad = new DriverValorarRecomendacion();
        String[] args = new String[0];
        valoracionCalidad.main(args);
    }

}