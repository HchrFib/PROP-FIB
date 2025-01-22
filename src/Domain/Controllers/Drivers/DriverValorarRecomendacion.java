package Domain.Controllers.Drivers;

import Domain.Classes.*;
import Domain.Controllers.Knn;
import Domain.Controllers.ValorarRecomendacion;
import Domain.Controllers.CtrlDominio;
import Persistence.ImportarRecomendaciones;

import java.util.*;

public class DriverValorarRecomendacion {
    private static Scanner scan = new Scanner(System.in);
    private static Integer userId;
    private static LinkedList<Pair<Integer, Double>> recomendacionesReales;
    private static LinkedList<Pair<Integer, Double>> recomendacionesIdeales;
    private static LinkedList<String> testUnknown;
    private static LinkedList<String> testKnown;
    private static int archivo;

    public DriverValorarRecomendacion() {}

    public static void main(String[] args) {
        System.out.println("#################################################");
        System.out.println("#####       Evaluación de la calidad        #####");
        System.out.println("#####        de las recomendaciones         #####");
        System.out.println("#################################################");
        System.out.println("#####    [1] Collaborative Filtering        #####");
        System.out.println("#####    [2] Content-Based Filtering 	    #####");
        System.out.println("#####    [3] Cerrar y Salir                 #####");
        System.out.println("#################################################");

        switch(scan.nextInt()){
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
    }

    private static void limpiarKnownUnkown() {
        recomendacionesIdeales = new LinkedList<>();
        recomendacionesReales = new LinkedList<>();

        Comparator<Pair<Integer, Double>> comparadorPair = Comparator.comparing(Pair<Integer, Double>::getSecond);
        PriorityQueue<Pair<Integer, Double>> ratingsIdeales = new PriorityQueue<>(Collections.reverseOrder(comparadorPair));
        PriorityQueue<Pair<Integer, Double>> ratingsReales = new PriorityQueue<>(Collections.reverseOrder(comparadorPair));

        for (String fila : testUnknown) {

            String[] campo = fila.split(",");
            int userIdfield = Integer.parseInt(campo[0]);
            int itemId = Integer.parseInt(campo[1]);
            double rating = Double.parseDouble(campo[2]);

            if (userIdfield == userId) {
                Pair<Integer, Double> p = new Pair<>(itemId, rating);
                ratingsIdeales.add(p);
            }
        }

        while (!ratingsIdeales.isEmpty()) {
            recomendacionesIdeales.add(ratingsIdeales.poll());
        }

        for (String fila : testKnown) {

            String[] campo = fila.split(",");
            int userIdfield = Integer.parseInt(campo[0]);
            int itemId = Integer.parseInt(campo[1]);
            double rating = Double.parseDouble(campo[2]);

            if (userIdfield == userId) {
                Pair<Integer, Double> p = new Pair<>(itemId, rating);
                ratingsReales.add(p);
            }
        }

        while (!ratingsReales.isEmpty()) {
            recomendacionesReales.add(ratingsReales.poll());
        }
    }

    private static void JuegoDePruebasBasico() {
        CtrlDominio dominio = CtrlDominio.getInstance();
        dominio.inicializar();

        testKnown = dominio.getTestKnown();
        testUnknown = dominio.getTestUnknown();

        limpiarKnownUnkown();

        ValorarRecomendacion valoracion = new ValorarRecomendacion(recomendacionesReales, recomendacionesIdeales);
        Double NDCG = valoracion.getNDCG();

        System.out.println("Calidad de las recomendaciones realizadas para el usuario " + userId + ": " + NDCG);
    }

    private static void importarTesting() {
        String nomArchivoCsv = "";
        switch(archivo){
            case 1:
                nomArchivoCsv = "archivos/movies250/ratings.test.unknown.csv";
                break;
            case 2:
                nomArchivoCsv = "archivos/movies750/ratings.test.unknown.csv";
                break;
            case 3:
                nomArchivoCsv = "archivos/movies2250/ratings.test.unknown.csv";
                break;
            case 4:
                nomArchivoCsv = "archivos/movies6750/ratings.test.unknown.csv";
                break;
            case 5:
                scan.close();
                break;
        }
        ImportarRecomendaciones ratingsIdeales = new ImportarRecomendaciones();
        ratingsIdeales.archivoCsvIn(nomArchivoCsv, userId);
        recomendacionesIdeales = ratingsIdeales.getRatingsIdeales();
    }

    private static void CollaborativeFiltering() {
        System.out.println("Introduzca el valor 'k', para el algoritmo k-Means:");
        Integer k = scan.nextInt();

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

        String nomArchivoCsv = "";

        switch(scan.nextInt()){
            case 1:
                nomArchivoCsv = "archivos/movies250/ratings.combinats.csv";
                archivo = 1;
                break;
            case 2:
                nomArchivoCsv = "archivos/movies750/ratings.combinats.csv";

                archivo = 2;
                break;
            case 3:
                nomArchivoCsv = "archivos/movies2250/ratings.combinats.csv";

                archivo = 3;
                break;
            case 4:
                nomArchivoCsv = "archivos/movies6750/ratings.combinats.csv";

                archivo = 4;
                break;
            case 5:
                archivo = 5;
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida");
                System.exit(0);
                break;
        }

        System.out.println("Introduzca el ID del usuario activo:");
        System.out.println("(Sobre el que pediremos recomendaciones)");
        userId = scan.nextInt();

        DriverKmeans kmeans = new DriverKmeans(userId,k,nomArchivoCsv);

        DriverSlopeOne slopeOne = new DriverSlopeOne(userId, kmeans.getCluster(), nomArchivoCsv);
        recomendacionesReales = slopeOne.getRecomendaciones();
        importarTesting(); // Generamos las "recomendacionesIdeales"
        valorar();
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

        String dirArchivoCsvRatings = "";
        String nomArchivoCsvItems = "";

        switch(scan.nextInt()) {
            case 1:
                dirArchivoCsvRatings = "archivos/movies250";
                k = 250;
                archivo = 1;
                break;
            case 2:
                dirArchivoCsvRatings = "archivos/movies750";
                k = 750;
                archivo = 2;
                break;
            case 3:
                dirArchivoCsvRatings = "archivos/movies2250";
                archivo = 3;
                k = 2250;
                break;
            case 4:
                dirArchivoCsvRatings = "archivos/movies6750";
                k = 6750;
                archivo = 4;
                break;
            case 5:
                archivo = 5;
                scan.close();
                break;
        }

        System.out.println("Introduzca el ID del usuario activo:");
        System.out.println("(Sobre el que pediremos recomendaciones)");
        userId = scan.nextInt();

        CtrlDominio dominio = CtrlDominio.getInstance();
        dominio.inicializar();

        dominio.cargarDataset(dirArchivoCsvRatings);

        ArrayList<Item> items = dominio.getItemsCarregats();
        Map<Integer, Usuario> users = dominio.getUsuarios();
        Usuario user = users.get(userId);

        Knn algoritmo = new Knn(k, user, items);
        Map<Integer, Double> resultats = algoritmo.calcularResultado();
        recomendacionesReales = new LinkedList<>();

        for (Integer it : resultats.keySet()) {
            Pair<Integer,Double> p = new Pair<>(it,resultats.get(it));
            recomendacionesReales.add(p);
        }

        importarTesting();
        valorar();
    }

    private static void valorar() {

        ValorarRecomendacion valoracion = new ValorarRecomendacion(recomendacionesReales, recomendacionesIdeales);
        Double NDCG = valoracion.getNDCG();
        System.out.println("Calidad de las recomendaciones realizadas para el usuario " + userId + ": " + NDCG);
    }


}


