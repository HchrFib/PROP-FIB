package Persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class CtrlRatings {
    /** @brief nombre del fichero donde se almacenan los ratings
     **/
    final String nomFitxer = "/ratings.db.csv";

    /** @brief Instancia de la clase importarRatings
     **/
    private ImportarRatings ratings;

    /** @brief Al usar CtrlRating un patron singleton, esta estructura guarda la única instancia del controlador de
     *  ratings
     **/
    private static CtrlRatings instance = null;

    /** @brief Creadora por defecto
     *
     * \pre <em> Cierto </em>
     * \post Crea el objeto de la classe CtrlRating
     */
    public CtrlRatings() {
        this.ratings = new ImportarRatings();
    }

    /** @brief Obtener la instancia de CtrlRating

    Permite la implementación del patrón singleton de esta clase.
    \pre <em> cierto </em>>
    \post Retorna la instancia de la clase en caso de que ya existiese una. En caso contrario,
    crea una nueva instancia y la retorna.
     */
    public static CtrlRatings getInstance() {
        if (instance == null) {
            instance = new CtrlRatings();
        }
        return instance;
    }

    /** @brief Guardar en el directorio <em>path</em> en un fichero con el nombre "ratings.db.csv" el parámetro
     * <em>valora</em>

    \pre <em> cierto </em>>
    \post Genera el archivo "ratings.db.csv" en el directorio especificado. Cada String de la LinkedList pasa ser
    una fila de un archivo .csv.
     */
    public void saveRatings(String path, LinkedList<String> valora) {
        try {
            FileWriter ratingFile = new FileWriter(path+"/ratings.db.csv");

            ratingFile.write("userId,itemId,rating");
            ratingFile.write(System.getProperty( "line.separator" ));

            for (String it : valora) {
                ratingFile.write(it);
                ratingFile.write(System.getProperty( "line.separator" ));
            }
            ratingFile.close();
            System.out.println("|-- Escriptura dels ratings realitzada correctament");
        }
        catch (IOException e) {
            System.out.println("|-- Error al generar el fitxer");
            e.printStackTrace();
        }
    }

    /** @brief Pide a la clase <em>importarRatings</em> cargar el archivo de ratings.

    \pre <em> cierto </em>>
    \post Devuelve los ratings leídos por la clase <em>importarRatings</em>
     */
    public LinkedList<String> loadRatings(String path) {
        path += nomFitxer;

        return ratings.load(path);
    }

    /** @brief Comprueba que exista el archivo "ratings.db.csv"
     * \pre <em> cierto </em>>
     * \post Devuelve <em>true</em> si existe el archivo; <em>false</em> al contrario
     **/
    public boolean existeixRatings(String path) {
        path += nomFitxer;

        return ratings.existeArchivo(path);
    }
}

