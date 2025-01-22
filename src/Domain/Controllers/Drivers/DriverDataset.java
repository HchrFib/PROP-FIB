package Domain.Controllers.Drivers;
import Domain.Classes.*;
import Persistence.Data;
import java.util.Scanner;
public class DriverDataset {
    private static Scanner scan = new Scanner(System.in);
    private static String inputFile;
    public static void main(String[] args) {


        System.out.println("#################################################");
        System.out.println("#####     Indique qué juego de pruebas      #####");
        System.out.println("#####                                       #####");
        System.out.println("#################################################");
        System.out.println("#####    [1] Eficiencia 250 	            #####");
        System.out.println("#####    [2] Eficiencia 750 	            #####");
        System.out.println("#####    [3] Eficiencia 2250 	            #####");
        System.out.println("#####    [4] Eficiencia 6750 	            #####");
        System.out.println("#################################################");
        System.out.println("#####    Juego de Prueba (Excepciones)      #####");
        System.out.println("#################################################");
        System.out.println("#####    [5] Archivo vacío                  #####");
        System.out.println("#####    [6] Archivo no existe              #####");
        System.out.println("#####    [7] Cerrar y Salir                 #####");
        System.out.println("#################################################");

        System.out.println("");

        switch(scan.nextInt()){
            case 1:
                inputFile = "archivos/movies250/ratings.db.csv";
                break;
            case 2:
                inputFile = "archivos/movies750/ratings.db.csv";
                break;
            case 3:
                inputFile = "archivos/movies2250/ratings.db.csv";
                break;
            case 4:
                inputFile = "archivos/movies6750/ratings.db.csv";
                break;
            case 5:
                inputFile = "archivos/emptyFile.csv";
                break;
            case 6:
                inputFile = "archivos/archivoNoExiste.csv";
                break;
            case 7:
                scan.close();
                break;
        }
        setUp();

    }
    public static void setUp() {
        Data dataSet = new Data();
        long startTime = System.currentTimeMillis();
        try {

            dataSet.archivoCsvIn(inputFile);
            long endTime = System.currentTimeMillis();
            long tiempoTotal = endTime -  startTime;


            if(!dataSet.getRegistros().isEmpty()) {
                System.out.println("El tiempo de lectura del archivo y almacenamiento en una estructura de datos es de:");
                System.out.println(tiempoTotal + "ms y ha leído: " + dataSet.getRegistros().size() + " registros.");
                System.out.println("###########################################################");
                System.out.println("Ejemplo de usuario y formato del registro.");
                System.out.println("Usuario con id 231564");
                System.out.println("Tiene su registro de la forma: {{itemId, rating} ,{} ...{}}");
                System.out.println("Registro: {{1, 2.5},{3, 5.0},{},{},....{},{},{}}");
                System.out.println("###########################################################");
            }
        }catch (Exception e) {
            System.out.println("Ha sucedido el siguiente error: ");
        }

    }

}

