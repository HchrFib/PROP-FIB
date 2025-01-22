package Domain.Controllers.Drivers;
import Domain.Classes.*;
import Domain.Controllers.CtrlDominio;
import Persistence.ImportarItems;

import java.util.*;

public class DriverImportarItems {
    public static void primeraPrueba() {

        ImportarItems impDat = new ImportarItems();
        //datos contiene las tuplas, donde cada HashMap<cabecera, valor> es una tupla (fila del archivo .csv).
        LinkedList<HashMap<String, String>> datos = new LinkedList<>();
        datos = impDat.archivoCsvIn("archivos/movies250/items.csv");

        CtrlDominio ctrlDominio = new CtrlDominio();
        ctrlDominio.inicializar(); //inicializamos el dominio
        ctrlDominio.cargarTipos("archivos/movies250");

        //Imprimimos los tipos de datos de cada cabecera
        /*for (Map.Entry<String, String> entry: ctrlDominio.getTipos().entrySet()) {
            System.out.println("Cabecera: " + entry.getKey() + ", Tipo de dato: " + entry.getValue());
        }*/
        for(HashMap<String, String> tupla : datos) {    // por cada tupla obtenemos detectamos su tipo
            Item itm  =  new  Item(Integer.parseInt(tupla.get("id")), tupla, ctrlDominio.getTipos());
            ArrayList<Atributo> atributos = itm.getAtributos();
            for (Atributo a : atributos) {
                if (a != null) { // Verificación para evitar NPE si 'a' es null
                    System.out.println("Atributo: " + a.getName() + " Tipo:  " + a.getTipo());
                    System.out.println("Valor: ");
                    switch (a.getTipo()) {
                        case categorico:
                            atributoCategorico aC = (atributoCategorico) a;
                            BitSet categoria = aC.getCategoria(); // Suponemos que getCategoria() retorna un BitSet
                            if (categoria != null && !categoria.isEmpty()) {
                                // Iterar sobre el BitSet, recorriendo todos los bits activos
                                for (int i = categoria.nextSetBit(0); i >= 0; i = categoria.nextSetBit(i + 1)) {
                                    System.out.println(" " + i); // Imprime los índices donde el bit está activado (1)
                                }
                            }
                            break;
                        case numerico:
                            atributoNumerico aN = (atributoNumerico) a;
                            if (aN.getValor() != null) {
                                System.out.println(" " + aN.getValor());
                            }
                            break;
                        case booleano:
                            atributoBooleano aB = (atributoBooleano) a;
                            if (aB.getBool() != null) {
                                System.out.println(" " + aB.boolToInt());
                            }
                            break;
                        case freetext:
                            atributoFreetext aF = (atributoFreetext) a;
                            if (aF.getText() != null) {
                                System.out.println(" " + aF.getText());
                            }
                            break;
                        default:
                            System.out.println("Tipo desconocido de atributo");
                            break;
                    }
                }
            }
        }
        if (!datos.isEmpty()) System.out.println("Primera prueba: Todo OK!");
    }

    public static void segundaPrueba() {

        long startTime = System.currentTimeMillis();
        ImportarItems impDat = new ImportarItems();
        //datos contiene las tuplas, donde cada HashMap<cabecera, valor> es una tupla (fila del archivo .csv).
        LinkedList<HashMap<String, String>> datos = new LinkedList<>();
        datos = impDat.archivoCsvIn("archivos/movies6750/items.csv");
        CtrlDominio ctrlDominio = new CtrlDominio();
        ctrlDominio.inicializar(); //inicializamos el dominio
        ctrlDominio.cargarTipos("archivos");
        long endTime = System.currentTimeMillis();
        if(!ctrlDominio.getTipos().isEmpty()) {
            System.out.println("Segunda prueba: Todo OK!");
            System.out.println("Segunda prueba, size=6750: Tiempo total: " + (endTime-startTime));
            int sizeItems = datos.size();
            System.out.println("Segunda prueba, size=6750: Total procesado: " + datos.size() + " de 6857 filas");
            System.out.println("Porcentaje processado : " + (sizeItems/6841.0000)*100 + "%" );
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("#################################################");
        System.out.println("#####     Indique qué juego de pruebas      #####");
        System.out.println("#####            desea ejecutar:            #####");
        System.out.println("       (CONSULTAR MANUAL DE INSTRUCCIONES        ");
        System.out.println("                 EN CASO DE DUDA)                ");
        System.out.println("#################################################");
        System.out.println("#####    [1] Eficiencia 250                 #####");
        System.out.println("#####    [2] Eficiencia 6750     	        #####");
        System.out.println("#####    [3] Cerrar y Salir                 #####");
        System.out.println("#################################################");
        switch(scan.nextInt()){
            case 1:
                primeraPrueba();
                break;
            case 2:
                segundaPrueba();
                break;
            case 3:
                scan.close();
                break;
        }
    }
}

