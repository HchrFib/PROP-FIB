package Persistence;

import Domain.Classes.Pair;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/** @file CtrlPersistencia.java
 @brief Implementación del controlador de la capa de Persistencia
 */

/** @class CtrlPersistencia
 @brief Representa la implementación del controlador de la capa de Persistencia

 El controlador de la capa de Persistencia permite el acceso a los datos. Además, se relaciona con la capa de Dominio
 (concretamente con CtrlDominio) haciendo caso de sus peticiones sobre obtener datos o guardarlos.
 */
public class CtrlPersistencia {

    /** @brief Al usar CtrlPersistencia un patron singleton, esta estructura guarda la única instancia del controlador de
     *  persistencia.
     **/
    private static CtrlPersistencia instance = null;

    // controladors
    /** @brief Instancia del controlador de items
     **/
    private CtrlItems ctrlItems;

    /** @brief Instancia del controlador de tipos
     **/
    private CtrlTipos ctrlTipos;

    /** @brief Instancia del controlador de ratings
     **/
    private CtrlRatings ctrlRatings;

    /** @brief Instancia del controlador de recomanacio
     **/
    private CtrlRecomenacio ctrlRecomanacio;

    // ED
    /** @brief Cada String de este LinkedList representa una fila del archivo de ratings obtenido por el controlador
     *  ratings.
     **/
    private LinkedList<String> ratings;

    /** @brief Cada String de este LinkedList representa una fila del archivo de valoraciones que ya sabemos que se
     * han producio (ratings.test.known.csv)
     **/
    private LinkedList<String> testKnown;

    /** @brief Cada String de este LinkedList representa una fila del archivo de valoraciones que el sistema desconoce
     * han producio (ratings.test.unknown.csv)
     **/
    private LinkedList<String> testUnknown;

    /** @brief Cada String de este LinkedList un item, con sus atributos separados por comas.
     **/
    private LinkedList<HashMap<String, String>> items;

    /** @brief La key representa la cabecera de los atributos, y el value indica de que tipo es dicho atributo
     **/
    private HashMap<String, String> tipos;

    /** @brief Creadora por defecto
     *
     * \pre <em> Cierto </em>
     * \post Crea el objeto de la classe CtrlPersistencia
     */
    public CtrlPersistencia() {
        ctrlTipos = CtrlTipos.getInstance();
        ctrlItems = CtrlItems.getInstance();
        ctrlRatings = CtrlRatings.getInstance();
        ctrlRecomanacio = CtrlRecomenacio.getInstance();
    }

    /** @brief Obtener la instancia de CtrlPersistencia

    Permite la implementación del patrón singleton de esta clase.
    \pre <em> cierto </em>>
    \post Retorna la instancia de la clase en caso de que ya existiese una. En caso contrario,
    crea una nueva instancia y la retorna.
     */
    public static CtrlPersistencia getInstance() {
        if (instance == null) {
            instance = new CtrlPersistencia();
        }
        return instance;
    }

    /** @brief Comprueba que existan todos los archivos necesarios en el dataset
    \pre <em> cierto </em>>
    \post Devuelve <em>true</em> si existen todos los archivos; <em>false</em> al contrario
     */
    public boolean existeDataset(String directorio) {
        boolean existeItems = ctrlItems.existeixItems(directorio);
        boolean existeRatings = ctrlRatings.existeixRatings(directorio);
        boolean existeKnown = ctrlRecomanacio.existeKown(directorio);
        boolean existeUnknown = ctrlRecomanacio.existeUnkown(directorio);
        boolean tipos = ctrlTipos.existeTipos(directorio);

        if (!existeItems) System.out.println(">ERROR: falta fitxer \"items.csv\"");
        if (!existeRatings) System.out.println(">ERROR: falta fitxer \"ratings.db.csv\"");
        if (!existeKnown) System.out.println(">ERROR: falta fitxer \"ratings.test.known.csv\"");
        if (!existeUnknown) System.out.println(">ERROR: falta fitxer \"ratings.test.unknown.csv\"");
        if (!tipos) System.out.println(">ERROR: falta fitxer \"tipos.csv\"");

        return existeItems && existeRatings && existeKnown && existeUnknown && tipos;
    }

    /** @brief Se comunica con los diferentes controladores para leer los archivos del dataset y asignarlos a las
     *  correspondientes estructuras.
     *
     * \pre <em> Cierto </em>
     * \post Las diferentes estrcuturas tienes los datos leídos de los ficheros del dataset. Si no existe preprocesado
     * de los items se genera.
     */
    public void cargarDataset(String directorio) {
        tipos = ctrlTipos.loadTipos(directorio);
        ratings = ctrlRatings.loadRatings(directorio);
        testKnown = ctrlRecomanacio.loadRecom(directorio, 0);
        testUnknown = ctrlRecomanacio.loadRecom(directorio, 1);

        if (!ctrlItems.existeixPrepro(directorio)) {
            items = ctrlItems.loadItems(directorio);
            ctrlItems.saveItemsProcessats(directorio, items, tipos);
            System.out.println("|-- Llegits primer cop " + items.size() + " items");
        }
        else {
            items = ctrlItems.loadPre(directorio);
            System.out.println("|-- Llegits " + items.size() + " items preprocessats");
        }
    }

    /** @brief Se comunica con el controlador de Item para leer los items del dataset
     *
     * \pre <em> Cierto </em>
     * \post <em>items</em> contiene los items leídos del dataset
     */
    public void cargarItems(String directorio) {
        items = ctrlItems.loadItems(directorio);
    }

    /** @brief Se comunica con el controlador de Item para leer los items del dataset
     *
     * \pre <em> Cierto </em>
     * \post <em>items</em> contiene los items leídos del dataset
     */
    public void cargarTipos(String directorio) {
        tipos = ctrlTipos.loadTipos(directorio);
    }

    /** @brief Se comunica con el controlador de rating para leer los ratings que no son del test del dataset
     *
     * \pre <em> Cierto </em>
     * \post <em>ratings</em> contiene los ratings leídos que no son del test del dataset
     */
    public void cargarRatings(String directorio) {
        ratings = ctrlRatings.loadRatings(directorio);
    }

    /** @brief Se comunica con el controlador de recomendación para leer los ratings que son del test conocido
     *
     * \pre <em> Cierto </em>
     * \post <em>testKnown</em> contiene los ratings leídos que son del test conocido
     */
    public void cargarKnown(String directorio) {
        testKnown = ctrlRecomanacio.loadRecom(directorio, 0);
    }

    /** @brief Se comunica con el controlador de recomendación para leer los ratings que son del test no conocido
     *
     * \pre <em> Cierto </em>
     * \post <em>testUnknown</em> contiene los ratings leídos que son del test no conocido
     */
    public void cargarUnnown(String directorio) {
        testKnown = ctrlRecomanacio.loadRecom(directorio, 1);
    }

    /** @brief Comprueba que exista el archivo de tipos en el dataset
    \pre <em> cierto </em>>
    \post Devuelve <em>true</em> si existe el archivo "tipos.csv"; <em>false</em> al contrario
     */
    public boolean carpetaContieneTipos(String directorio) {
        return ctrlTipos.existeTipos(directorio);
    }

    /** @brief Devuelve la estructura de datos <em>ratings</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve <em>ratings</em>
     */
    public LinkedList<String> getRatings() {
        return this.ratings;
    }

    /** @brief Devuelve la estructura de datos <em>testKnown</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve <em>testKnown</em>
     */
    public LinkedList<String> getTestKnown() {
        return this.testKnown;
    }

    /** @brief Devuelve la estructura de datos <em>testUnknown</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve <em>testUnknown</em>
     */
    public LinkedList<String> getTestUnknown() {
        return this.testUnknown;
    }

    /** @brief Devuelve la estructura de datos <em>items</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve <em>items</em>
     */
    public LinkedList<HashMap<String, String>> getItems() {
        return this.items;
    }

    /** @brief Devuelve la estructura de datos <em>tipos</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve <em>tipos</em>
     */
    public HashMap<String, String> getTipos() { return this.tipos;}

    /** @brief Envia la instrucción al controlador recomendación de guardar el parámetro <em>recomendacion</em>
     *
     * \pre <em>Cierto</em>
     * \post Se llama la función del controlador recomendación para guardar el parámetro <em>recomendacion</em>
     */
    public void saveRecomendacion(int userId, LinkedList<Pair<Integer, Double>> recomendacion) {
        ctrlRecomanacio.saveRecomenacions(userId, recomendacion);
    }

    /** @brief Envia la instrucción al controlador de tipos de guardar el parámetro <em>t</em>
     *
     * \pre <em>Cierto</em>
     * \post Se llama la función del controlador de tipos para guardar el parámetro <em>t</em> en el directorio
     * <em>path</em>
     * */
    public void saveTipos(String path, HashMap<String, String> t) {
        System.out.println(path);
        ctrlTipos.saveTipos(path, t);
    }

    /** @brief Envia la instrucción al conjunto de controladores para generar un nuevo dataset con los elementos
     * pasados como parámetro
     *
     * \pre <em>Cierto</em>
     * \post Se llama la función del conjunto de controladores para generar un nuevo dataset en el directorio
     * <em>path</em>, con los elementos pasados como parámetro. Devuelve el nombre del directorio donde se ha creado
     * el dataset.
     * */
    public String saveDataset(LinkedList<String> r, LinkedList<String> known, LinkedList<String> unknown,
                              LinkedList<HashMap<String, String>> i, HashMap<String, String> t) {
        Date ahora = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

        String path = "archivos/" + formato.format(ahora);

        if (new File(path).mkdirs()) {
            System.out.println("Directori " + path + " creat correctament");

            ctrlRecomanacio.saveFiles(path, known, 0);
            ctrlRecomanacio.saveFiles(path, unknown, 1);
            ctrlItems.saveItems(path, i);
            ctrlRatings.saveRatings(path, r);
            ctrlTipos.saveTipos(path, t);

            return formato.format(ahora);
        }
        else {
            System.out.println("Error al generar el directori");
        }

        return null;
    }
}
