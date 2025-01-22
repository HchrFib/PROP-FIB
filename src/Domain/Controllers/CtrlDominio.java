package Domain.Controllers;

import Domain.Classes.*;
import Persistence.*;
import java.util.*;

/** @file CtrlDominio.java
 @brief Implementación del controlador de la capa de Dominio
 */

/** @class CtrlDominio
 @brief Representa la implementación del controlador de la capa de Dominio

 El controlador de la capa de Dominio hace de puente y permite la interacción entre las diferentes capas. Procesa la
 información recopilada en la capa de presentación y recopila los datos obtenidos en la capa de persistencia. Es el
 núcleo de la aplicación.
 */
public class CtrlDominio {
    /** @brief Al usar CtrlDominio un patron singleton, esta estructura guarda la única instancia del controlador del
     *  dominio
     **/
    private static CtrlDominio instance = null;

    /** @brief Instancia del controlador de persistencia
     **/
    private CtrlPersistencia ctrl_persistencia;

    /** @brief Indica si la clase ha sido inicializada leyendo la instancia del controlador de persistencia.
     **/
    private boolean inicializado;

    /** @brief Indica si el dataset original ha sido modificado.
     **/
    private boolean modificado;

    /** @brief Identificardor del usuario para el cual se están pidiendo las recomendaciones.
     **/
    private int userId;

    /** @brief Cada String de este LinkedList representa una fila del archivo de ratings obtenido por la persistencia.
     **/
    private LinkedList<String> ratings;

    /** @brief Cada String de este LinkedList representa una fila del archivo de valoraciones que el sistema desconoce
     * han producio (ratings.test.unknown.csv)
     **/
    private LinkedList<String> testUnknown;

    /** @brief Cada String de este LinkedList representa una fila del archivo de valoraciones que ya sabemos que se
     * han producio (ratings.test.known.csv) */
    private LinkedList<String> testKnown;

    /** @brief Cada String de este LinkedList un item, con sus atributos separados por comas.
     **/
    private LinkedList<HashMap<String, String>> items;

    /** @brief La key representa la cabecera de los atributos, y el value indica de que tipo es dicho atributo
     **/
    private HashMap<String, String> tipos;


    /** @brief La unión de la estructura ratings y testKnown. Útil para los algoritmos de Kmeans y WeightedSlopeOne
     **/
    private LinkedList<String> ratingsCombinats;

    /** @brief La key del HashMap representa el id de un Item, y el value es ese Item en cuestión
     **/
    private HashMap<Integer, Item> itemsSlopeOne;

    /**@brief Estructura de datos donde guardamos los datos procesados y en un formato adecuado. Utilizado por el
     * atributo dataset_kmeans.
     **/
    private HashMap<Integer, Registro> registros;

    /** @brief Estructura de datos donde guardamos los datos procesados y en un formato adecuado. Utilizado por el
     *  atributo dataset_elbowMethod
     **/
    private HashMap<Integer, Registro> registros1;

    /**@brief Estructura de datos que representa un conjunto de registros donde cada elemento es una tupla: "idUser,
     * items valorados". Atributo utilizado en el algoritmo Kmeans
     **/
    private DataSet dataSet_Kmeans;

    /** @brief Estructura de datos que representa un conjunto de registros donde cada elemento es una tupla: "idUser,
     *  items valoraados". Atributo utilizado en 'Elbow Method'
     **/
    private DataSet dataSet_elbowMethod;

    /** @brief La key del HashMap representa el id de un Usuario y el value es ese Usuario en cuestión.
     **/
    private HashMap<Integer, Usuario> usuarios;

    /** @brief El set guarda los id de los Usuario que aparecen en el test unknown.
     **/
    private Set<Integer> usuariosUnknown;

    /** @brief Un ArrayList que contiene todos los Item.
     **/
    private ArrayList<Item> itemsCarregats;

    /** @brief El Set almacena todos los ids de los Item cargados.
     **/
    private Set<Integer> itemsExistents;

    /** @brief Almacena las recomendaciones obtenidas del test unknwon. El primer elemento del Pair corresponde al
     *  id del Item que se está recomendado, y el segundo elemento corresponde a la valoración dada.
     **/
    private LinkedList<Pair<Integer, Double>> recomendacionesUnknown;

    /** @brief Almacena las recomendaciones generadas a partir de ejecutar los diferentes algoritmos. El primer elemento
     *  del Pair corresponde al id del Item que se está valorando, y el segundo elemento corresponde a la valoración
     *  dada.
     **/
    private LinkedList<Pair<Integer,Double>> recomendaciones;

    /** @brief Su valor esta comprendido en el rango de [0, 1], y determina la cualidad de las recomendaciones
     **/
    private Double NDCG;

    /** @brief Creadora por defecto
     *
     * \pre <em> Cierto </em>
     * \post Crea el objeto de la clase CtrlDominio
     */
    public CtrlDominio() {
        this.inicializado = false;
        this.modificado = false;
    }

    /** @brief Obtener la instancia de CtrlDominio

    Permite la implementación del patrón singleton de esta clase.
    \pre <em> cierto </em>>
    \post Retorna la instancia de la clase en caso de que ya existiese una. En caso contrario,
    crea una nueva instancia y la retorna.
     */
    public static CtrlDominio getInstance() {
        if (instance == null) {
            instance = new CtrlDominio();
        }
        return instance;
    }

    /** @brief Obtiene la instancia del controlador de persistencia.
     *
     * \pre <em> Cierto </em>
     * \post ctrl_persistencia pasa a ser la instancia del controlador de persistencia.
     */
    public void inicializar() {

        ctrl_persistencia = CtrlPersistencia.getInstance();
        inicializado = true;
    }

    /** @brief Ejecuta el método Content Based para obtener recomendaciones
     *
     *  El método Content Based se basa en ejecutar el algoritmo Knn
     * \pre <em> Cierto </em>
     * \post Se guarda en <em>recomendaciones</em> las <em>k</em> mejores recomendaciones sobre el <em>userId</em> a
     * partir de los Item en <em>itemsCarregats</em>.
     */
    private void ContentBased(int k) {
        Usuario usuario = usuarios.get(userId);
        Knn algoritmo = new Knn(k, usuario, itemsCarregats);

        Map<Integer, Double> resultats = algoritmo.calcularResultado();
        recomendaciones = new LinkedList<>();

        for (Integer it : resultats.keySet()) {
            Pair<Integer,Double> p = new Pair<>(it,resultats.get(it));
            recomendaciones.add(p);
        }
    }

    /** @brief Ejecuta el método Collaborative Filtering para obtener recomendaciones
     *
     *  El método Collaborative Filtering se basa en ejecutar el algoritmo Kmeans y el WeightedSlopeOne
     * \pre <em> Cierto </em>
     * \post Se guarda en <em>recomendaciones</em> <em>numRec</em> recomendaciones sobre el <em>userId</em>
     */
    private void CollaborativeFiltering(int numRec) {
        Kmeans kmeansEM = new Kmeans();
        int k = kmeansEM.elbowMethod(11, dataSet_elbowMethod, dataSet_elbowMethod);
        Kmeans kmeans = new Kmeans();
        kmeans.kmeans(k, dataSet_Kmeans);

        WeightedSlopeOne slopeOne = new WeightedSlopeOne(this.userId, this.usuarios, this.itemsSlopeOne);
        this.recomendaciones = slopeOne.getNRecomendaciones(numRec);
    }

    /** @brief Ejecuta el algoritmo para valorar las recomendaciones hechas por los diferentes métodos.
     *
     * \pre <em>estrategia</em> ha de tener como valor "content" para ejecutar la valoración del método Content Based.
     * Otro para la valoración del método Collaborative Filtering
     * \post Se guarda en <em>recomendaciones</em> <em>numRec</em> recomendaciones sobre el <em>userId</em>
     */
    private void ValorarCalidad(String estrategia) {
        limpiarUnknown();
        LinkedList<Pair<Integer,Double>> recomendacionesIdeales = recomendacionesUnknown;

        this.recomendaciones = new LinkedList<>();

        if (Objects.equals(estrategia, "content")) {
            Usuario usuario = usuarios.get(userId);
            Knn algoritmo = new Knn(itemsCarregats.size(), usuario, itemsCarregats);
            Map<Integer, Double> resultats = algoritmo.calcularResultado();
            for (Integer it : resultats.keySet()) {
                Pair<Integer,Double> p = new Pair<>(it,resultats.get(it));
                this.recomendaciones.add(p);
            }
        }
        else if (Objects.equals(estrategia, "collaborative")){
            Kmeans kmeansEM = new Kmeans();
            int k = kmeansEM.elbowMethod(11, dataSet_elbowMethod, dataSet_elbowMethod);
            System.out.println("|-- Mirando para k: " + k);
            Kmeans kmeans = new Kmeans();
            kmeans.kmeans(k, dataSet_Kmeans);
            WeightedSlopeOne slopeOne = new WeightedSlopeOne(this.userId, this.usuarios, this.itemsSlopeOne);
            this.recomendaciones = slopeOne.getNRecomendaciones(itemsSlopeOne.size());
        }
        else { // hybrid
            ContentBased(itemsCarregats.size());
            LinkedList<Pair<Integer, Double>> contentRecom = new LinkedList<>(recomendaciones);

            CollaborativeFiltering(itemsSlopeOne.size());

            LinkedList<Pair<Integer, Double>> resultat = new LinkedList<>();
            while (!contentRecom.isEmpty()
                    && !recomendaciones.isEmpty()) {
                int itemContent = -1;
                double valContent = -1;
                int itemCollaborative = -1;
                double valCollaborative = -1;

                if (contentRecom.size() > 0) {
                    valContent = contentRecom.get(0).getSecond();
                    itemContent = contentRecom.get(0).getFirst();
                    contentRecom.removeFirst();
                }

                if (recomendaciones.size() > 0) {
                    valCollaborative = recomendaciones.get(0).getSecond();
                    itemCollaborative = recomendaciones.get(0).getFirst();
                    recomendaciones.removeFirst();
                }

                if (valContent > valCollaborative) {
                    Pair<Integer, Double> aux1 = new Pair<>(itemContent, valContent);
                    resultat.add(aux1);

                    Pair<Integer, Double> aux2 = new Pair<>(itemCollaborative, valCollaborative);
                    recomendaciones.addFirst(aux2);
                }
                else {
                    Pair<Integer, Double> aux1 = new Pair<>(itemCollaborative, valCollaborative);
                    resultat.add(aux1);

                    Pair<Integer, Double> aux2 = new Pair<>(itemContent, valContent);
                    contentRecom.addFirst(aux2);
                }
            }
            this.recomendaciones = resultat;
        }

        ValorarRecomendacion valorarCalidad = new ValorarRecomendacion(this.recomendaciones, recomendacionesIdeales);
        this.NDCG = valorarCalidad.getNDCG();
        this.recomendaciones = valorarCalidad.getListaLR();
    }

    /** @brief Ejecuta un método híbrido para obtener recomendaciones
     *
     * Combina las técnicas de Content Based y Collaborative Filtering
     * \pre <em> Cierto </em>
     * \post Se guarda en <em>recomendaciones</em> las <em>numRec</em> mejores recomendaciones obtenidas a partir de
     * hacer la unión entre los resultados de las dos técnicas.
     */
    private void Hybrid(int numRec) {
        ContentBased(numRec);
        LinkedList<Pair<Integer, Double>> contentRecom = new LinkedList<>(recomendaciones);

        CollaborativeFiltering(numRec);

        Set<Integer> itemsAfegits = new HashSet<>();
        LinkedList<Pair<Integer, Double>> resultat = new LinkedList<>();
        for (int i = 0; i < numRec; ++i) {
            int itemContent = -1;
            double valContent = -1;
            int itemCollaborative = -1;
            double valCollaborative = -1;

            if (contentRecom.size() > 0) {
                valContent = contentRecom.get(0).getSecond();
                itemContent = contentRecom.get(0).getFirst();
                contentRecom.removeFirst();
            }

            if (recomendaciones.size() > 0) {
                valCollaborative = recomendaciones.get(0).getSecond();
                itemCollaborative = recomendaciones.get(0).getFirst();
                recomendaciones.removeFirst();
            }

            if (valContent > valCollaborative) {
                if (!itemsAfegits.contains(itemContent)) {
                    Pair<Integer, Double> aux1 = new Pair<>(itemContent, valContent);
                    resultat.add(aux1);
                    itemsAfegits.add(itemContent);
                }

                Pair<Integer, Double> aux2 = new Pair<>(itemCollaborative, valCollaborative);
                recomendaciones.addFirst(aux2);
            }
            else {
                if (!itemsAfegits.contains(itemCollaborative)) {
                    Pair<Integer, Double> aux1 = new Pair<>(itemCollaborative, valCollaborative);
                    resultat.add(aux1);
                    itemsAfegits.add(itemCollaborative);
                }

                Pair<Integer, Double> aux2 = new Pair<>(itemContent, valContent);
                contentRecom.addFirst(aux2);
            }
        }
        recomendaciones = resultat;
    }

    /** @brief Limpia y pone en un formato más cómodo para manipular los datos de las recomendaciones desconocidas
     * obtenidos por la capa de persistencia.
     *
     * \pre <em> Cierto </em>.
     * \post Por cada String de la LinkedList de <em>testUnknown</em>, se crea un Pair y se almacena en
     * <em>recomendacionesUnknown</em>.
     */
    private void limpiarUnknown() {
        recomendacionesUnknown = new LinkedList<>();
        Comparator<Pair<Integer, Double>> comparadorPair = Comparator.comparing(Pair<Integer, Double>::getSecond);
        PriorityQueue<Pair<Integer, Double>> ratingsIdeales = new PriorityQueue<>(Collections.reverseOrder(comparadorPair));

        for (String fila : testUnknown) {

            String[] campo = fila.split(",");
            int userId = Integer.parseInt(campo[0]);
            int itemId = Integer.parseInt(campo[1]);
            double rating = Double.parseDouble(campo[2]);

            if (userId == this.userId) {
                Pair<Integer, Double> p = new Pair<>(itemId, rating);
                ratingsIdeales.add(p);
            }
        }

        while (!ratingsIdeales.isEmpty()) {
            recomendacionesUnknown.add(ratingsIdeales.poll());
        }
    }

    /** @brief Indica si el Usuario con <em>userId</em> existe.
     *
     * \pre <em> Cierto </em>
     * \post Devuelve <em>cierto</em> en caso de que el Usuario con <em>userId</em> se encuentre en la estructura
     * de datos <em>usuarios</em>, <em>falso</em> en caso contrario.
     */
    public boolean existeUsuario(int userId) {
        if (inicializado) return usuarios.containsKey(userId);

        return false;
    }

    /** @brief Indica si el Usuario con <em>userId</em> existe en los ficheros de prueba.
     *
     * \pre <em> Cierto </em>
     * \post Devuelve <em>cierto</em> en caso de que el Usuario con <em>userId</em> se encuentre en la estructura
     * de datos <em>usuariosUnknown</em>, <em>falso</em> en caso contrario.
     */
    public boolean existeUsuarioUnknown(int userId) {
        if (inicializado) return usuariosUnknown.contains(userId);

        return false;
    }

    /** @brief Indica si el Item con <em>itemId</em> existe.
     *
     * \pre <em> Cierto </em>
     * \post Devuelve <em>cierto</em> en caso de que el Item con <em>itemId</em> se encuentre en la estructura
     * de datos <em>itemsExistents</em>, <em>falso</em> en caso contrario.
     */
    public boolean existeItem(int itemId) {
        if (inicializado) return itemsExistents.contains(itemId);

        return false;
    }

    /** @brief Indica si Usuario <em>userId</em> ha valorado el Item con id <em>itemId</em>.
     *
     * \pre <em> Cierto </em>
     * \post Devuelve <em>cierto</em> en caso de que el Usuario con <em>userId</em> haya valorado el Item con id
     * <em>itemId</em>, <em>falso</em> en caso contrario.
     */
    public boolean existeRating(int userId, int itemId) {
        if (inicializado && usuarios.containsKey(userId)) return usuarios.get(userId).haValorado(itemId);

        return false;
    }

    /** @brief Comprueba si la carpeta donde se encuentra el dataset contiene el archivo <em>tipos.csv</em>
     *
     * El archivo <em>tipos.csv</em> indica de que tipo son los atributos de los Item en ese dataset
     * \pre <em> Cierto </em>
     * \post Devuelve <em>cierto</em> en caso de que se encuentre el archivo en el directorio <em>directorio</em>,
     * <em>falso</em> en caso contrario.
     */
    public boolean carpetaContieneTipos(String directorio) {
        return ctrl_persistencia.carpetaContieneTipos(directorio);
    }

    /** @brief Dependiendo de la <em>estrategia</em> escogida calcula las recomendaciones usando métodos diferents.
     *
     * Los métodos pueden ser Content Based, Collaborative Filtering o Hibrido.
     * \pre <em> Cierto </em>
     * \post Devuelve <em>numRec</em> recomendaciones sobre el usuario con id <em>userId</em> con el método escogido en
     * el String <em>estrategia</em>.
     */
    public LinkedList<Pair<Integer,Double>> obtenerRecomendaciones(int userId, int numRec, String estrategia) {
        this.userId = userId;

        if (Objects.equals(estrategia, "Content-Based Filtering")) {
            ContentBased(numRec);
            System.out.println("Solicitando recomendaciones Content Based");
            return recomendaciones;
        } else if (Objects.equals(estrategia, "Collaborative Filtering")){
            CollaborativeFiltering(numRec);
            System.out.println("Solicitando recomendaciones Collaborative");
            return recomendaciones;
        } else { // hybrid
            Hybrid(numRec);
            System.out.println("Solicitando recomendaciones Hybrid");
            return recomendaciones;
        }
    }

    /** @brief Dependiendo de la <em>estrategia</em> escogida valora las recomendaciones obtenidas por el método
     *  especificado
     *
     * Los métodos pueden ser Content Based, Collaborative Filtering o Hibrido.
     * \pre <em> Cierto </em>
     * \post Devuelve las recomendaciones sobre <em>userId</em> sobre las que se ha evaluado su calidad usando el método
     * especificado en <em>estrategia</em>
     */
    public LinkedList<Pair<Integer,Double>> valorarRecomendaciones(int userId, String estrategia) {
        this.userId = userId;
        if (Objects.equals(estrategia, "Content-Based Filtering")) {
            ValorarCalidad("content");
            System.out.println("|-- Valorando recomendaciones Content Based");
        } else if (Objects.equals(estrategia, "Collaborative Filtering")){
            ValorarCalidad("collaborative");
            System.out.println("|-- Valorando recomendaciones Collaborative");
        } else {
            ValorarCalidad("hybrid");
            System.out.println("|-- Valorando recomendaciones Hybrid");
        }

        return recomendaciones;
    }

    /** @brief Añade un nuevo rating al conjunto de ratings ya obtenido.
     *
     * \pre <em> Cierto </em>
     * \post Se ha insertado el nuevo rating del <em>userId</em> sobre el <em>itemId</em> con valoración <em>rating</em>,
     *  en la estructura de datos <em>ratings</em>. También se pone <em>modificado</em> a true para indicar que ya no
     *  tratamos con el dataset original.
     */
    public void insertarRating(int userId, int itemId, double rating) {
        try {
            String userS = Integer.toString(userId);
            String itemS = Integer.toString(itemId);
            String ratingS = Double.toString(rating);

            String valoracion = userS + "," + itemS + "," + ratingS;
            ratings.add(valoracion);
            ratingsCombinats.add(valoracion);
            this.modificado = true;
            limpiarRatings();
        } catch (Exception e) {
            System.out.println("|-- Rating ya existente");
        }
    }

    /** @brief Indica si el dataset sobre el que se está haciendo la ejecución ha sido modificado.
     *
     * \pre <em> Cierto </em>
     * \post Devuelve <em>true</em> si ha sido modificado; <em>false</em> al contrario
     */
    public boolean dataSetModificado() { return modificado; }

    /** @brief Indica si la carpeta encontrada en la ruta <em>directorio</em> contiene todos los archivos que los
     * conjuntos datos necesitan.
     *
     * Los archivos necesarios son: items.csv, ratings.db.csv, ratings.test.known.csv, ratings.test.unknown.csv y
     * tipos.csv
     * \pre <em> Cierto </em>
     * \post Devuelve <em>true</em> existen todos los archivos necesarios; <em>false</em> al contrario
     */
    public boolean datasetCompleto(String directorio) {
        return ctrl_persistencia.existeDataset(directorio);
    }

    /** @brief Obtiene los diferentes datos del controlador de persistencia y los pone en sus estructuras
     * correspondientes
     *
     * También une los datos de <em>ratings</em> y <em>testKnown</em> y los almacena en <em>ratingsCombinats</em>.
     * \pre <em> Cierto </em>
     * \post Asigna los datos de la persistencia a las estructuras de datos correspondientes, también llama a la función
     * <em>limpiar_y_guardar</em>.
     */
    public void cargarDataset(String directorio) {
        ctrl_persistencia.cargarDataset(directorio);

        ratings = ctrl_persistencia.getRatings();
        testKnown = ctrl_persistencia.getTestKnown();
        testUnknown = ctrl_persistencia.getTestUnknown();
        items = ctrl_persistencia.getItems();
        tipos = ctrl_persistencia.getTipos();

        ratingsCombinats = new LinkedList<>(ratings);
        ratingsCombinats.addAll(testKnown);
        limpiar_y_guardar();
    }

    /** @brief Le comunica al controlador de persistencia que desea guardar los datos con los que se están trabajando
     * actualment, en un dataset separado.
     *
     * \pre <em> Cierto </em>
     * \post Indica al controlador de persistencia que guarde el dataset y devuelve un String con el nombre que se le ha
     * puesto al nuevo dataset.
     */
    public String guardarDataset() {
        this.modificado = false;
        return ctrl_persistencia.saveDataset(ratings, testKnown, testUnknown, items, tipos);
    }

    /** @brief Le comunica al controlador de persistencia que desea guardar en el fichero <em>tipos.csv</em> en el
     * directorio especificado, el contenido almacenado en el parámetro <em>contenido</em>
     *
     * \pre <em> Cierto </em>
     * \post Indica al controlador de persistencia que guarde como tipos el <em>contenido</em>
     */
    public void guardarTipos(String path, Vector<String> contenido) {
        tipos = new HashMap<>();
        inicializar();
        for (String fila : contenido) {
            String[] valores = fila.split(",");
            System.out.println(fila);
            tipos.put(valores[1], valores[0]);
        }

        ctrl_persistencia.saveTipos(path, tipos);
    }

    /** @brief Le comunica al controlador de persistencia que desea guardar las recomendaciones generadas para
     * <em>userId</em> que se pasan como el parámetro <em>recom</em>.
     *
     * \pre <em> Cierto </em>
     * \post Indica al controlador de persistencia que guarde las recomendaciones.
     */
    public void guardarRecomendaciones(int userId, LinkedList<Pair<Integer,Double>> recom) {
        ctrl_persistencia.saveRecomendacion(userId, recom);
    }

    /** @brief Substituye los atributos del item con id <em>idItem</em> por los atributos <em>it</em>
     *
     * \pre <em>idItem</em> es un id de un Item que se encuentra en la estructura <em>items</em>
     * \post El item con id <em>idItem</em> tiene como atributos <em>it</em>
     */
    public void editarItem(int idItem, HashMap<String, String> it) {
        LinkedList<HashMap<String, String>> itemsNew = new LinkedList<>();
        for (HashMap<String, String> item : items) {
            int id = Integer.parseInt(item.get("id"));
            if (idItem == id)  itemsNew.add(it);
            else itemsNew.add(item);
        }
        items = itemsNew;

        this.modificado = true;
        limpiarItems();
    }

    /** @brief Se añade a <em>items</em> un nuevo Item <em>it</em>
     *
     * \pre El HashMap <em>it</em> tiene alguna de sus key con valor <me>id</me>
     * \post Se añade el item <em>it</em> a <em>items</em>. Se especifica el dataset ha quedado modificado
     */
    public void anadirItem(HashMap<String, String> it) {
        items.add(it);

        this.modificado = true;
        limpiarItems();
    }

    /** @brief Se añade a <em>usuarios</em> un nuevo Usuario con idenficiador <em>userId</em>
     *
     * \pre <em>userId</em> no esta previamente en <em>usuarios</em>
     * \post Se añade a <em>usuarios</em> como key el <em>userId</em> y como value el Usuario con ese id
     */
    public void anadirUsuario(int userId) {
        Usuario usuario = new Usuario(userId);
        usuarios.put(userId, usuario);
    }

    /** @brief Eliminar el Item con id <em>idItem</em>
     *
     * \pre <em>Cierto</em>
     * \post Se eliminar el Item con id <em>idItem</em> y también de todas las estructuras de datos que hacen referencia
     * a él.
     */
    public void eliminarItem(int idItem) {
        LinkedList<HashMap<String, String>> itemsNew = new LinkedList<>();
        for (HashMap<String, String> item : items) {
            int id = Integer.parseInt(item.get("id"));
            if (idItem != id) itemsNew.add(item);
        }
        items = itemsNew;

        ratings = eliminaR(idItem, ratings, 1);
        testUnknown =  eliminaR(idItem, testKnown, 1);
        testKnown =  eliminaR(idItem, testUnknown, 1);

        ratingsCombinats = new LinkedList<>(ratings);
        ratingsCombinats.addAll(testKnown);

        this.modificado = true;
        limpiarItems();
        limpiarRatings();
    }

    /** @brief Eliminar el Usuario con id <em>userId</em> de todas las estructuras de datos que hacen referencia a él.
     *
     * \pre <em>Cierto</em>
     * \post El Usuario con <em>userId</em> ha sido eliminado de todas las estructuras de datos que hacian referencia a
     * él.
     */
    public void eliminarUsuario(int userId) {
        ratings = eliminaR(userId, ratings, 0);
        testUnknown =  eliminaR(userId, testKnown, 0);
        testKnown =  eliminaR(userId, testUnknown, 0);

        ratingsCombinats = new LinkedList<>(ratings);
        ratingsCombinats.addAll(testKnown);

        this.modificado = true;
        limpiarRatings();
    }

    /** @brief Devuelve los mismos datos dados en el parámetro <em>archivo</em> eliminando los Strings que coinciden
     *  con el parámetro <em>objetivo</em>.
     *
     * Si <em>index</em> es 0, con lo que se compara es con id de Usuario y si es 1 se compara con id de Item
     * \pre <em>Cierto</em>
     * \post Devuelve  los mismos datos dados en el parámetro <em>archivo</em> eliminando los Strings que coinciden
     * con el parámetro <em>objetivo</em>.
     */
    private LinkedList<String> eliminaR(int objectiu, LinkedList<String> archivo, int index) {
        LinkedList<String> ratingsNew = new LinkedList<>();
        for (String s : archivo) {
            String[] campo = s.split(",");
            int id = Integer.parseInt(campo[index]);
            if (id != objectiu) ratingsNew.add(s);
        }

        return ratingsNew;
    }

    /** @brief Llama a diferentes funciones que limpian los datos obtenidos por la persistencia.
     */
    private void limpiar_y_guardar() {
        limpiarItems();
        limpiarRatings();
        setUsersUnknown();
    }

    /** @brief Dado la estructura <em>items</em>, para cada String de la LinkedList, crea un Item y lo pone en un
     *  ArrayList de Item
     *
     * \pre <em>Cierto</em>
     * \post <em>itemsCarregats</em> pasa tener los datos limpios de <em>items</em>. También se añaden todos los id de
     * los items en <em>itemsExistents</em>.
     */
    private void limpiarItems() {
        itemsCarregats = new ArrayList<>();
        itemsExistents = new HashSet<>();

        for (HashMap<String, String> it : items) {
            String idAux = it.get("id");
            int id = -1;
            try { id = Integer.parseInt(idAux);}
            catch (NumberFormatException nfe) {
                System.out.println("Error de atribut numeric");
            }
            HashMap<String, String> newIt = new HashMap<>(it);
            newIt.remove("id");
            Item i = new Item(id, newIt, tipos);
            itemsExistents.add(id);

            itemsCarregats.add(i);
        }
    }

    /** @brief Dado la estructura <em>ratingsCombinats</em>, se extraen los datos necesarios para obtener las diferentes
     * estructuras de datos necesarias para el correcto funcionamiento del programa.
     *
     * \pre <em>Cierto</em>
     * \post <em>itemsSlopeOne</em>, <em>usuarios</em>, <em>registros</em>, <em>registros</em>, <em>registros1</em>,
     * <em>dataSet_Kmeans</em>, <em>dataSet_elbowMethod</em> han sido asignados con sus correspondientes valores a
     * partir de los datos de <<em>ratingsCombinats</em>
     */
    private void limpiarRatings() {
        itemsSlopeOne = new HashMap<>();
        usuarios = new HashMap<>();
        registros = new HashMap<>();
        registros1 = new HashMap<>();
        dataSet_Kmeans = new DataSet();
        dataSet_elbowMethod = new DataSet();

        String[] campos = ratingsCombinats.getFirst().split(","); //Exception si ratings is NUll
        int anterior = Integer.parseInt(campos[0]);
        HashMap<Integer, Double> registro = new HashMap<>();
        int numRegistro = 1;
        for (String fila : ratingsCombinats) {
            String[] campo = fila.split(",");
            int userId = Integer.parseInt(campo[0]);
            int itemId = Integer.parseInt(campo[1]);
            double rating = Double.parseDouble(campo[2]);

            // slope one
            if (!itemsSlopeOne.containsKey(itemId)) {
                Item item = new Item(itemId);
                itemsSlopeOne.put(itemId, item);
            }

            // Leer usuarios
            if (!usuarios.containsKey(userId)) {
                Usuario usuario = new Usuario(userId);
                usuarios.put(userId, usuario);
            }

            try {
                usuarios.get(userId).anadirValoracion(itemId, rating);
                itemsSlopeOne.get(itemId).anadirValoracion(userId, rating);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Kmeans
            if(userId == anterior) {
                registro.put(itemId, rating);

            } else {
                Registro reg = new Registro(registro);
                Registro reg1 = new Registro(registro);
                reg.setUserRegistro(anterior);
                reg1.setUserRegistro(anterior);
                registros.put(numRegistro,reg);
                registros1.put(numRegistro, reg1);
                anterior = userId;
                registro = new HashMap<>();
                registro.put(itemId,rating);
                ++numRegistro;
            }
        }

        Registro reg = new Registro(registro);
        reg.setUserRegistro(anterior);
        registros.put(numRegistro, reg);

        Registro reg1 = new Registro(registro);
        reg1.setUserRegistro(anterior);
        registros1.put(numRegistro, reg1);

        dataSet_Kmeans.setRegistros(registros);
        dataSet_elbowMethod.setRegistros(registros1);
    }

    /** @brief Dado la estructura <em>testUnknown</em>, almacena en <em>usuariosUnknown</em> los id de todos los Usuario
     * mencionados.
     *
     * \pre <em>Cierto</em>
     * \post <em>usuariosUnknown</em> contiene todos los id de Usuario mencionados en <em>testUnknown</em>.
     */
    private void setUsersUnknown() {
        usuariosUnknown = new HashSet<>();

        for (String s : testUnknown) {
            String[] campo = s.split(",");
            int id = Integer.parseInt(campo[0]);
            usuariosUnknown.add(id);
        }
    }

    /** @brief Se comunica con el controlador de persistencia para cargar los tipos de los atributos
     *
     * \pre <em>path</em> es un directorio válido.
     * \post <em>tipos</em> pasa a tener los datos obtenidos por la persistencia.
     */
    public void cargarTipos(String path) {

        ctrl_persistencia.cargarTipos(path);

        tipos = ctrl_persistencia.getTipos();
    }

    /** @brief Se comunica con el controlador de persistencia para cargar los items
     *
     * \pre <em>path</em> es un directorio válido.
     * \post <em>items</em> pasa a tener los datos obtenidos por la persistencia.
     */
    public void cargarItems(String path) {
        ctrl_persistencia.cargarItems(path);
        items = ctrl_persistencia.getItems();
        limpiarItems();
    }

    /** @brief Se comunica con el controlador de persistencia para cargar las valoraciones de ratings y las conocidas
     * del test.
     *
     * \pre <em>path</em> es un directorio válido.
     * \post <em>ratings</em>, <em>testKnown</em> pasa a tener los datos obtenidos por la persistencia.
     * <em>ratingsCombinats</em> es la union de los datos de <em>ratings</em> y <em>testKnown</em>.
     */
    public void cargarRatings(String path) {
        ctrl_persistencia.cargarRatings(path);
        ctrl_persistencia.cargarKnown(path);
        ratings = ctrl_persistencia.getRatings();
        testKnown = ctrl_persistencia.getTestKnown();

        ratingsCombinats = new LinkedList<>(ratings);
        ratingsCombinats.addAll(testKnown);
        limpiarRatings();
    }

    /** @brief Devuelve el valor de <em>inicializado</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve el valor de <em>inicializado</em>
     */
    public boolean getInicializado() {
        return inicializado;
    }

    /** @brief Devuelve la estructura de datos <em>tipos</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve <em>tipos</em>
     */
    public HashMap<String, String> getTipos() { return tipos; }

    /** @brief Devuelve la estructura de datos <em>itemsCarregats</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve <em>itemsCarregats</em>
     */
    public ArrayList<Item> getItemsCarregats() { return itemsCarregats; }

    /** @brief Devuelve el Item identificado por el id <em>itemId</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve el Item identificado por el id <em>itemId</em> que se encuentra en <em>items</em>. Devuelve
     * <em>null</em> en caso de que no haya ningun item con esa id.
     */
    public HashMap<String, String> getItem(int itemId) {
        for (HashMap<String, String> item : items) {
            int id = Integer.parseInt(item.get("id"));
            if (itemId == id) return item;
        }
        return null;
    }

    /** @brief Devuelve la estructura de datos <em>itemsSlopeOne</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve <em>itemsSlopeOne</em>
     */
    public HashMap<Integer, Item> getItemsSlopeOne() { return itemsSlopeOne; }

    /** @brief Devuelve la variable <em>NDCG</em>
     *
     * \pre <em>Cierto</em>
     * \post  Devuelve la variable <em>NDCG</em>
     */
    public Double getNDCG() {
        return this.NDCG;
    }

    /** @brief Devuelve la estructura de datos <em>usuarios</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve <em>usuarios</em>
     */
    public HashMap<Integer, Usuario> getUsuarios() { return usuarios; }

    /** @brief Devuelve la estructura de datos <em>testKnown</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve <em>testKnown</em>
     */
    public LinkedList<String> getTestKnown() {return testKnown;}

    /** @brief Devuelve la estructura de datos <em>testUnknown</em>
     *
     * \pre <em>Cierto</em>
     * \post Devuelve <em>testUnknown</em>
     */
    public LinkedList<String> getTestUnknown() {return testUnknown;}
}
