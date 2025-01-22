package Persistence;

import Domain.Classes.Pair;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class CtrlRecomenacio {
    /** @brief Al usar CtrlRecomenacio un patron singleton, esta estructura guarda la única instancia del controlador de
     *  ratings
     **/
    private static CtrlRecomenacio instance = null;

    /** @brief Instancia de la clase importarRecomendaciones
     **/
    private final ImportarRecomendaciones recomanacions;

    /** @brief Creadora por defecto
     *
     * \pre <em> Cierto </em>
     * \post Crea el objeto de la classe CtrlRecomenacio
     */
    public CtrlRecomenacio() {
        this.recomanacions = new ImportarRecomendaciones();
    }

    /** @brief Obtener la instancia de CtrlRecomenacio

    Permite la implementación del patrón singleton de esta clase.
    \pre <em> cierto </em>>
    \post Retorna la instancia de la clase en caso de que ya existiese una. En caso contrario,
    crea una nueva instancia y la retorna.
     */
    public static CtrlRecomenacio getInstance() {
        if (instance == null) {
            instance = new CtrlRecomenacio();
        }
        return instance;
    }

    /** @brief Copia los ficheros "ratings.test.unknown.csv" o "ratings.test.known.csv" dependiendo del <em>index</em>
     *  especificado, de la carpeta <em>origen</em> a la carpeta <em>destino</em>
     *
     * \pre <em> Cierto </em>
     * \post El fichero ha sido movido de <em>origen</em> a <em>destino</em>
     */
    public void copyFile(String origen, String destino, int index) {
        String file;
        if (index == 0) file = "/ratings.test.unknown.csv";
        else file = "/ratings.test.known.csv";

        Path src = Paths.get(origen+file);
        Path dst = Paths.get(destino+file);

        try {
            Files.copy(src, dst);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** @brief Guardar en el directorio <em>path</em>, en un fichero con el nombre "ratings.test.unknown.csv" o
     * "ratings.test.known.csv" dependiendo del <em>index</em> especificado,el parámetro <em>valora</em>

    \pre <em> cierto </em>>
    \post Genera el archivo correspondiente en el directorio especificado. Cada String de la LinkedList pasa ser
    una fila de un archivo .csv.
     */
    public void saveFiles(String path, LinkedList<String> valora, int index) {
        try {
            String fileName;
            if (index == 0) fileName = "/ratings.test.unknown.csv";
            else fileName = "/ratings.test.known.csv";

            FileWriter ratingFile = new FileWriter(path+fileName);

            ratingFile.write("userId,itemId,rating");
            ratingFile.write(System.getProperty( "line.separator" ));

            for (String it : valora) {
                ratingFile.write(it);
                ratingFile.write(System.getProperty( "line.separator" ));
            }
            ratingFile.close();
            System.out.println("|-- Escriptura "+ fileName +" correctament");
        }
        catch (IOException e) {
            System.out.println("|-- Error al generar el fitxer");
            e.printStackTrace();
        }
    }

    /** @brief Pide a la clase <em>importarRecomendaciones</em> cargar el archivo de prueba de ratings desconocido.

    \pre <em> cierto </em>>
    \post Devuelve los ratings leídos por la clase <em>importarRecomendaciones</em>
     */
    public LinkedList<Pair<Integer, Double>> loadUnknown(String path, int userId) {
        path += "/ratings.test.unknown.csv";
        recomanacions.archivoCsvIn(path, userId);

        return recomanacions.getRatingsIdeales();
    }

    /** @brief Pide a la clase <em>importarRecomendaciones</em> cargar el archivo de prueba de ratings conocido.

    \pre <em> cierto </em>>
    \post Devuelve los ratings leídos por la clase <em>importarRecomendaciones</em>
     */
    public LinkedList<String> loadRecom (String path, int index) {
        if (index == 0) path += "/ratings.test.known.csv";
        else path += "/ratings.test.unknown.csv";

        return recomanacions.load(path);
    }

    /** @brief Guardar en la carpeta <em>Recomendaciones</em> la recomendacion <em>recomendacion</em>

    \pre <em> cierto </em>>
    \post Genera un archivo con las recomendaciones hechas para el Usuario <em>userId</em>  en el directorio especificado.
    Cada Pair de la LinkedList representa una línea en el archivo csv.
     */
    public void saveRecomenacions(int userId, LinkedList<Pair<Integer, Double>> recomendacion) {
        String path = "archivos/Recomendaciones/";

        try {
            Date ahora = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

            path = path + "recomendacion_user" + userId + "_" + formato.format(ahora) + ".csv";

            FileWriter recomendacionFile = new FileWriter(path);

            recomendacionFile.write("itemId, rating");
            recomendacionFile.write(System.getProperty("line.separator"));

            for (Pair<Integer, Double> it : recomendacion) {
                recomendacionFile.write(it.getFirst() + ", " + it.getSecond());
                recomendacionFile.write(System.getProperty("line.separator"));
            }
            recomendacionFile.close();
            System.out.println("|-- Escriptura de la recomanació correctament");
        } catch (IOException e) {
            System.out.println("|-- Error al generar el fitxer");
            e.printStackTrace();
        }
    }

    /** @brief Comprueba que exista el archivo "ratings.test.known.csv"
     * \pre <em> cierto </em>>
     * \post Devuelve <em>true</em> si existe el archivo; <em>false</em> al contrario
     **/
    public boolean existeKown(String path) {
        path += "/ratings.test.known.csv";

        return recomanacions.existeArchivo(path);
    }

    /** @brief Comprueba que exista el archivo "ratings.test.unknown.csv"
     * \pre <em> cierto </em>>
     * \post Devuelve <em>true</em> si existe el archivo; <em>false</em> al contrario
     **/
    public boolean existeUnkown(String path) {
        path += "/ratings.test.unknown.csv";

        return recomanacions.existeArchivo(path);
    }
}
