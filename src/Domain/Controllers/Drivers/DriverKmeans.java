package Domain.Controllers.Drivers;

import Domain.Controllers.CtrlDominio;
import Domain.Controllers.Kmeans;

import Domain.Classes.*;
import Persistence.Data;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Objects;

/** @file DataSet.java
 @brief Implementación de la clase Driver.
 */
/** @class Driver
 @brief Esta clase está implementada con el objetivo instanciar objetos necesarios para la ejecución del <em>algoritmo KMeans</em>.
 Clase implementada por: Christian Chávez Apcho.

 */

public class DriverKmeans {

    private static Scanner scan = new Scanner(System.in);
    private static boolean opcion1 = false;
    private static String inputFile;
    private static boolean cerrar = false;


    private static Data dataKM = new Data();
    private static Kmeans kmeans= new Kmeans();
    private static Data dataEM = new Data();
    private static long tiempoTotal ;
    private static long tiempoTotaleEM ;


    private Integer userId;
    private Integer k;
    private String archivoRatings;


    public DriverKmeans() {


    }
    public DriverKmeans(Integer userId, Integer k, String archivoRatings) {
        this.userId = userId;
        this.k = k;
        this.archivoRatings = archivoRatings;
    }

    public static void main(String[] args) {

        System.out.println("#################################################");
        System.out.println("#####     Indique qué juego de pruebas      #####");
        System.out.println("#####                KMeans                 #####");
        System.out.println("#################################################");
        System.out.println("#####    [1] Archivo de prueba     	        #####");
        System.out.println("#####    [2] Eficiencia movies250  	        #####");
        System.out.println("#####    [3] Eficiencia movies750           #####");
        System.out.println("#####    [4] Eficiencia movies2250          #####");
        System.out.println("#####    [5] Eficiencia movies6750	        #####");
        System.out.println("#####    [6] Eficiencia series250	        #####");
        System.out.println("#####    [7] Eficiencia series750	        #####");
        System.out.println("#####    [8] Eficiencia series2250	        #####");
        System.out.println("#####    [9] Eficiencia ratings combinats   #####");
        System.out.println("#################################################");
        System.out.println("#################################################");
        System.out.println("#####    Juego de Prueba (Excepciones)      #####");
        System.out.println("#################################################");
        System.out.println("#####    [10] Archivo vacío                 #####");
        System.out.println("#####    [11] Archivo no existe             #####");
        System.out.println("#####    [12] Cerrar y Salir                #####");
        System.out.println("#################################################");

        System.out.println("");
        switch (scan.nextInt()) {
            case 1:
                opcion1 = true;
                inputFile = "archivos/sampleKmeans.csv";
                break;
            case 2:

                inputFile = "archivos/movies250/ratings.db.csv";
                break;
            case 3:
                inputFile = "archivos/movies750/ratings.db.csv";
                break;
            case 4:
                inputFile = "archivos/movies2250/ratings.db.csv";
                break;
            case 5:
                inputFile = "archivos/movies6750/ratings.db.csv";
                break;
            case 6:
                inputFile = "archivos/series250/ratings.db.csv";
                break;
            case 7:
                inputFile = "archivos/series750/ratings.db.csv";
                break;
            case 8:
                inputFile = "archivos/series2250/ratings.db.csv";
                break;
            case 9:
                inputFile = "archivos/ratings.combinats.db.csv";
                break;
            case 10:

                inputFile = "archivos/emptyFile.csv";
                break;
            case 11:

                inputFile = "archivos/archivoNoExiste.csv";
                break;
            case 12:
                cerrar = true;
                System.out.println("Por qué te vas? :(");
                scan.close();
                break;
        }
        if(!cerrar) {
            setUp();
            //if (Objects.equals(inputFile, "archivos/sampleKmeans.csv")) infoAdicional();
        }
        if(opcion1){
            allRegistros();
            infocluster();
            itemsValorados();
            numValoracionPerUsers();
            printClusterUserId(8);

        } else {

            try {
                if(!dataEM.getRegistros().isEmpty()) {
                    System.out.println("Tiempo empleado por 'Elbow Method' es: "  +  tiempoTotaleEM + "ms.");
                    System.out.println("Tiempo empleado por el algoritmo KMeans es: "  +  tiempoTotal + "ms.");

                    System.out.println("Desea ver todos los registros? escriba 's' (para mostrar) cualquier otra tecla rechazar");
                    String respuesta = scan.next();
                    if(respuesta.equals("s")) {
                        allRegistros();
                    }
                    System.out.println("Desea ver la informacion de los clusters ? escriba 's' (para mostrar) cualquier otra tecla rechazar");
                    System.out.println("Mostrará los registros de cada clusters");
                    respuesta = scan.next();
                    if(respuesta.equals("s")) {
                        infocluster();
                    }
                    System.out.println("Desea ver los items valorados ? escriba 's' (para mostrar) cualquier otra tecla rechazar");
                    respuesta = scan.next();
                    if(respuesta.equals("s")) {
                        infocluster();
                    }

                    System.out.println("Desea ver los items valoradospor cada usuario ? escriba 's' (para mostrar) cualquier otra tecla rechazar");
                    respuesta = scan.next();
                    if(respuesta.equals("s")) {
                        numValoracionPerUsers();
                    }

                }


            } catch (Exception e) {
                System.out.println("Error");
            }
        }

    }
    public static void setUp() {
        try {

            dataKM.archivoCsvIn(inputFile);
            dataEM.archivoCsvIn(inputFile);
            Kmeans kmeansEM = new Kmeans();

            if(!dataKM.getRegistros().isEmpty()) {
                DataSet dataSetEM = new DataSet();
                dataSetEM.setRegistros(dataEM.getRegistros());
                long startTimeEM = System.currentTimeMillis();
                int k = kmeansEM.elbowMethod(11,dataSetEM, dataSetEM);
                long endTimeKM = System.currentTimeMillis();
                DataSet dataSetKmeans = new DataSet();
                tiempoTotaleEM = endTimeKM - startTimeEM;
                dataSetKmeans.setRegistros(dataKM.getRegistros());


                long startTime = System.currentTimeMillis();
                System.out.println("El valor de k es: " + k);
                kmeans.kmeans(k, dataSetKmeans);
                long endTime = System.currentTimeMillis();
                tiempoTotal = endTime -  startTime;
            }


        } catch (Exception e) {
            System.out.println("Ha ocurrido un error");
            System.out.println(e);
        }
    }
    public static void allRegistros() {
        try {
            if (!dataKM.getRegistros().isEmpty()) {
                System.out.println("Userid - items valorados: - Nº cluster: ");
                System.out.println(" ----------------------------------------");
                for (var a : dataKM.getRegistros().entrySet()) {
                    System.out.println(a.getValue().getUserRegistro() + "   " + a.getValue().getRegistro().entrySet() + "  " + a.getValue().getNumCluster());
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
        }

    }
    public static void infocluster() {

        try {
            if (!dataKM.getRegistros().isEmpty()) {
                for(var cl : kmeans.getClusters().entrySet()) {
                    System.out.println("Info users cluster: " + cl.getKey());
                    kmeans.getClusters().get(cl.getKey()).printClusterId();
                }
                System.out.println();
            }
        } catch(Exception e) {
            System.out.println("Se ha producido un error.");
        }
    }
    public static void itemsValorados() {
        try {
            if (!dataKM.getRegistros().isEmpty()) {
                for (var cluster : kmeans.getClusters().entrySet()) {
                    System.out.println("Info items valorados users cluster: " + cluster.getKey());
                    kmeans.getClusters().get(cluster.getKey()).printClusterId();
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
        }

    }
    public static void numValoracionPerUsers() {
        try {
            if (!dataKM.getRegistros().isEmpty()) {
                for (var cluster : kmeans.getClusters().entrySet()) {
                    if (cluster.getValue().getUsersCluster() != null) {
                        System.out.println("Cluster: " + cluster.getValue().getId() + " " + cluster.getKey());
                        for (var d : cluster.getValue().getUsersCluster().entrySet()) {
                            if (cluster.getValue().getRegistrosCluster() != null) {
                                System.out.println("idUser: " + d.getKey() + " Nº valoraciones: " + d.getValue().getNumValoraciones());
                                for (var h : d.getValue().getValoraciones().entrySet()) {
                                    System.out.println(h.toString());
                                }
                                System.out.println();
                            }
                        }
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
        }

    }

    public static void printClusterUserId(int userId) {

        System.out.println("Info: el  Hashmap<Integer, Usuario> usersCluster es 'cluster.getUsersCluster()'" );
        System.out.println("Cluster de usuarios de idUser: " + userId);
        Cluster cluster;
        cluster =  kmeans.getClusterUser(userId);

        if(cluster != null) {
            for(var cl : cluster.getUsersCluster().entrySet()) {
                if(cl.getKey().equals(userId)) System.out.println( userId + " <-- UserId buscado");
                else System.out.println(cl.getKey());
            }
        } else {
            System.out.println("El usuario: " + userId + " no existe!");
        }
    }
    public HashMap<Integer,Usuario> getCluster() {

        DataSet dataSet = new DataSet();
        dataKM.archivoCsvIn(archivoRatings);
        dataSet.setRegistros(dataKM.getRegistros());  // new add
        Kmeans kmeans  = new Kmeans();
        kmeans.kmeans(k, dataSet);
        /*for(var cl : kmeans.getClusters().entrySet()){
            System.out.println("Info users cluster: " + cl.getKey());
            kmeans.getClusters().get(cl.getKey()).printClusterId();
        }*/
        return kmeans.getClusterUser(userId).getUsersCluster();
    }
}

