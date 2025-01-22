package Persistence;

import Domain.Classes.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/** @file importarRecomendaciones.java
 @brief Implementación de la clase importarRecomendaciones
 */

/** @class importarRecomendaciones
 @brief Representa la implementación de importarRecomendaciones.

 Esta clase sirve para hacer la importacion de Recomendaciones, a partir de un .csv en cada fila separada por comas.
 */
public class ImportarRecomendaciones {
    private PriorityQueue<Pair<Integer, Double>> ratingsIdeales;
    private LinkedList<Pair<Integer, Double>> result;


    /** @brief Creadora de la clase importadora de Recomendaciones.

    \pre <em> Cierto </em>
    \post Crea la clase importarRecomendaciones.
     */
    public ImportarRecomendaciones() {
        Comparator<Pair<Integer, Double>> comparadorPair = Comparator.comparing(Pair<Integer, Double>::getSecond);
        ratingsIdeales = new PriorityQueue<Pair<Integer, Double>>(Collections.reverseOrder(comparadorPair));
        result = new LinkedList<Pair<Integer,Double>>();
    }

    /** @brief Operación principal de la clase importadora. A partir de un archivo csv con recomendaciones y un usuario activo,
     * importa las recomendaciones para este usuario.
    \pre <em> Cierto </em>
    \post Crea en el sistema la lista de recomendaciones ideales para el usuario activo.
     */
    public void archivoCsvIn(String nomArchivoCsv, Integer idUsuarioActivo) {
        String fila;
        System.out.println("Class ImportarTipos: " + nomArchivoCsv);
        final String separador = ",";
        try {

            FileReader fr = new FileReader(nomArchivoCsv);
            BufferedReader br = new BufferedReader(fr);
            fila = br.readLine();

            if(!(null == fila)) fila = br.readLine();  //no nos interesa los headers

            while(!(null == fila)) { //campos: userId, itemId , rating

                String[] campo = fila.split(separador);
                int userId = Integer.parseInt(campo[0]);
                int itemId = Integer.parseInt(campo[1]);
                double rating =  Double.parseDouble(campo[2]);

                if (userId == idUsuarioActivo) {

                    Pair<Integer, Double> p = new Pair<>(itemId, rating);
                    ratingsIdeales.add(p);
                }
                fila = br.readLine();

            }
            if (ratingsIdeales.isEmpty()) {
                throw new Exception();
            }
            while(!ratingsIdeales.isEmpty()) {
                result.add(ratingsIdeales.poll());
            }


        } catch (FileNotFoundException e) {
            System.out.println("Error lectura archivo csv" + nomArchivoCsv);
        } catch (Exception e) {
            System.out.println("Puede que el fichero este vacío o que el usuario introducido no exista");

        }
    }

    /** @brief Carga el archivo especificado por el parámetro <em>path</em>
     * \pre <em> cierto </em>>
     * \post Devuelve una LinkedList donde cada String es una fila leída
     **/
    public LinkedList<String> load(String path) {
        String fila;
        LinkedList<String> resultat = new LinkedList<>();

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            fila = br.readLine();

            if(!(null == fila)) fila = br.readLine();  //no nos interesa los headers

            while(fila != null) { //campos: userId, itemId , rating
                resultat.add(fila);
                fila = br.readLine();
            }
            br.close();
            return resultat;
        } catch (Exception e) {
            System.out.println("Error lectura archivo csv");
        }

        return null;
    }

    /** @brief Getter de la recomendación importada
    \pre <em> Cierto </em>
    \post Retorna la lista de recomendaciones importada.
     */
    public LinkedList<Pair<Integer, Double>> getRatingsIdeales() {return this.result;}

    /** @brief Comprueba que exista el archivo en <em>path</em>
     * \pre <em> cierto </em>>
     * \post Devuelve <em>true</em> si existe el archivo; <em>false</em> al contrario
     **/
    public boolean existeArchivo(String path) {
        File f = new File(path);

        return f.exists();
    }
}



