package Domain.Controllers.Drivers;

import java.util.*;

import Domain.Classes.*;
import Domain.Controllers.Knn;
import Domain.Controllers.CtrlDominio;

public class DriverKnn {
    private static int k;
    private static Integer userId;
    private static String directory;
    private static long time;

    public DriverKnn (int userId, int k, String  directory){
        this.userId = userId;
        this.k = k;
        this.directory = directory;
        this.time = 0;
    }

    /** @brief Obtiene las recomendaciones del algoritmo k-Nearest Neighbours
     *
     * \pre <em>cierto</em>
     * \post Devuelve un Map donde la key es el Item valorado y el value es el rating dado a dicho Item
     */
    public static LinkedList<Pair<Integer, Double>> getRecomendaciones() {


        CtrlDominio dominio = CtrlDominio.getInstance();
        dominio.inicializar();

        dominio.cargarTipos(directory);
        dominio.cargarItems(directory);
        dominio.cargarRatings(directory);


        ArrayList<Item> items = dominio.getItemsCarregats();

        Map<Integer, Usuario> users = dominio.getUsuarios();
        Usuario user = users.get(userId);
        if(user == null){
            System.out.println("Usuario no encontrado (Programa finalizado).");
            System.exit(0);
            //throw new NullPointerException("Usuario no encontrado");

        }
        System.out.println("|-- IMPORTANDO ARCHIVOS --|");
        System.out.println("Directory: " + directory);
        System.out.println("|-- " + users.size() + " usuarios leídos --|");

        long startTime = System.currentTimeMillis();
        Knn algoritmo = new Knn(k, user, items);
        Map<Integer, Double> resultado = algoritmo.calcularResultado();
        time = System.currentTimeMillis() - startTime;


        System.out.println("|-- Resultado algortimo k-Nearest Neighbours --|");
        LinkedList<Pair<Integer, Double>> recomendaciones = new LinkedList<>();
        for (var it : resultado.entrySet()) {
            Pair<Integer, Double> par = new Pair<>(it.getKey(), it.getValue());
            recomendaciones.add(par);
        }

        return recomendaciones;
    }

    private static void showOptions() {
        System.out.println("#################################################");
        System.out.println("#####     Indique qué juego de pruebas      #####");
        System.out.println("#####            desea ejecutar:            #####");
        System.out.println("#####   (CONSULTAR MANUAL DE INSTRUCCIONES  #####");
        System.out.println("#####            EN CASO DE DUDA)           #####");
        System.out.println("#####---------------------------------------#####");
        System.out.println("#####    [1] Eficiencia 250     	        #####");
        System.out.println("#####    [2] Eficiencia 750 	            #####");
        System.out.println("#####    [3] Eficiencia 2250 	            #####");
        System.out.println("#####    [4] Eficiencia 6750     	        #####");
        System.out.println("#####    [5] Cerrar y Salir                 #####");
        System.out.println("#################################################");

        Scanner scan = new Scanner(System.in);
        switch(scan.nextInt()){
            case 1:
                directory = "archivos/movies250";
                break;
            case 2:
                directory = "archivos/movies750";
                break;
            case 3:
                directory = "archivos/movies2250";
                break;
            case 4:
                directory = "archivos/movies6750";
                break;
            case 5:
                scan.close();
                break;
        }

        System.out.println("|-- Introduzca el ID del usuario activo --|");
        System.out.println("|-- (Sobre el que pediremos recomendaciones) --|");
        userId = scan.nextInt();

        System.out.println("|-- Introduzca el numero de recomendaciones que quieras obtener (k) --|");
        k = scan.nextInt();
    }
    public static void main(String[] args) {
        showOptions();
        LinkedList<Pair<Integer, Double>> recomendaciones = new LinkedList<>(getRecomendaciones());

        for (Pair<Integer, Double> it : recomendaciones) {
            System.out.println("--> Recomendamos el ítem " + it.getFirst() + " con rating predecido de " + it.getSecond());
        }
        System.out.println("|-- Tiempo transcurrido " + time + " ms --|");
    }
}