package Domain.Controllers.Drivers;

import java.util.*;

import Domain.Classes.*;
import Domain.Controllers.WeightedSlopeOne;
import Domain.Controllers.CtrlDominio;
import Persistence.ImportarRatings;

/** @file DriverSlopeOne.java
 @brief Driver para probar el algoritmo Slope One
 */

/** @class DriverSlopeOne
 @brief

 */
public class DriverSlopeOne {

    private static HashMap<Integer, Usuario> users;
    private static HashMap<Integer, Item> items;
    private static Integer userId;
    private static String directorioRatings;
    private static Scanner scan = new Scanner(System.in);

    public DriverSlopeOne (){};
    public DriverSlopeOne(Integer userId, HashMap<Integer, Usuario> usuarios, String directorioRatings){
        this.userId = userId;
        this.users = usuarios;
        this.directorioRatings = directorioRatings;
    }


    public static void main(String[] args) {
        System.out.println("#################################################");
        System.out.println("#####     Indique qué juego de pruebas      #####");
        System.out.println("#####            desea ejecutar:            #####");
        System.out.println("       (CONSULTAR MANUAL DE INSTRUCCIONES        ");
        System.out.println("                 EN CASO DE DUDA)                ");
        System.out.println("#################################################");
        System.out.println("#####    [1] Eficiencia 250                 #####");
        System.out.println("#####    [2] Eficiencia 750                 #####");
        System.out.println("#####    [3] Eficiencia 2250                #####");
        System.out.println("#####    [4] Eficiencia 6750 	            #####");
        System.out.println("#####    [5] Cerrar y Salir                 #####");
        System.out.println("#################################################");

        boolean pedirUserId = true;

        switch(scan.nextInt()){
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

        if (pedirUserId) {

            System.out.println("Introduzca el ID del usuario activo:");
            System.out.println("(Sobre el que pediremos recomendaciones)");
            userId = scan.nextInt();
        }

        users = new HashMap<>();
        items = new HashMap<>();

        CtrlDominio dominio = CtrlDominio.getInstance();
        dominio.inicializar();
        dominio.cargarRatings(directorioRatings);

        HashMap<Integer, Item> items = dominio.getItemsSlopeOne();
        HashMap<Integer, Usuario> users = dominio.getUsuarios();

        long startTime = System.currentTimeMillis();

        if(users.get(userId) == null) {
            System.out.println("Mensaje desde el Main(Clase DriverSlopeOne)");
            System.out.println("El usuario con id: "+ userId + " no existe.");
            System.exit(0);
        }
        WeightedSlopeOne algoritmo = new WeightedSlopeOne(userId, users, items);
        long endTime = System.currentTimeMillis();

        /*if (Objects.equals(directorioRatings, "archivos/movies250")) {
            for (Pair<Integer, Double> val : algoritmo.getRecomendaciones()) {
                System.out.println("Recomendamos el item " + val.getFirst() + " que predecimos que valorará con " + val.getSecond());
            }
        }*/
        for (Pair<Integer, Double> val : algoritmo.getRecomendaciones()) {
            System.out.println("Recomendamos el item " + val.getFirst() + " que predecimos que valorará con " + val.getSecond());
        }
        long tiempoTotal = endTime -  startTime;
        System.out.println("Ha tardado: " + tiempoTotal + "ms");

    }
    public static LinkedList<Pair<Integer,Double>> getRecomendaciones() {
        items = new HashMap<>();
        ImportarRatings importar = new ImportarRatings();

        importar.archivoCsvIn(directorioRatings);

        items = importar.getItemsValorados();
        if(users.get(userId) == null) {
            System.out.println("Mensaje desde getRecomendaciones(Clase DriverSlopeOne)");
            System.out.println("El Usuario: "+ userId + " no existe: ");
            System.exit(0);
        }
        //if(items.isEmpty()) System.out.println("No hay items en el archivo");
        WeightedSlopeOne algoritmo = new WeightedSlopeOne(userId, users, items);


        for (Pair<Integer,Double> val : algoritmo.getRecomendaciones()) {
            System.out.println("Recomendamos el item " + val.getFirst() + " que predecimos que valorará con " + val.getSecond());
        }
        return algoritmo.getRecomendaciones();
    }
    public LinkedList<Pair<Integer,Double>> getNRecomendaciones(Integer userId, HashMap<Integer, Usuario> users, String directorioRatings, int numRec) {
        if(users.get(userId) == null) {
            System.out.println("Mensaje desde getNRecomendaciones(Clase DriverSlopeOne)");
            System.out.println("El Usuario: "+ userId + " no existe: ");
            System.exit(0);
        }
        items = new HashMap<>();
        CtrlDominio dominio = CtrlDominio.getInstance();
        dominio.inicializar();
        dominio.cargarRatings(directorioRatings);

        HashMap<Integer, Item> items = dominio.getItemsSlopeOne();

        long startTime = System.currentTimeMillis();
        WeightedSlopeOne algoritmo = new WeightedSlopeOne(userId, users, items);
        long endTime = System.currentTimeMillis();
        return algoritmo.getNRecomendaciones(numRec);
    }



}

