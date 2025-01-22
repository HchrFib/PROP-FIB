package Persistence;

import java.io.*;
import java.util.*;

public class CtrlItems {
    /** @brief nombre del fichero donde se almacenan los ratings
     **/
    final String nomFitxer = "/items.csv";

    /** @brief Instancia de la clase importarRatings
     **/
    final ImportarItems items;

    /** @brief Al usar CtrlRating un patron singleton, esta estructura guarda la única instancia del controlador de
     *  ratings
     **/
    private static CtrlItems instance = null;

    /** @brief Se guardan los nombres de las categorias
     **/
    private List<String> posicionsCategories;

    /** @brief Creadora por defecto
     *
     * \pre <em> Cierto </em>
     * \post Crea el objeto de la classe CtrlItems
     */
    public CtrlItems() {
        posicionsCategories = new LinkedList<>();
        this.items = new ImportarItems();
    }

    /** @brief Obtener la instancia de CtrlItems

    Permite la implementación del patrón singleton de esta clase.
    \pre <em> cierto </em>>
    \post Retorna la instancia de la clase en caso de que ya existiese una. En caso contrario,
    crea una nueva instancia y la retorna.
     */
    public static CtrlItems getInstance() {
        if (instance == null) {
            instance = new CtrlItems();
        }
        return instance;
    }

    /** @brief Pide a la clase <em>importarItems</em> cargar el archivo de items.

    \pre <em> cierto </em>>
    \post Devuelve los items leídos por la clase <em>importarItems</em>
     */
    public LinkedList<HashMap<String, String>> loadItems(String path) {
        path += nomFitxer;

        return items.archivoCsvIn(path);
    }

    /** @brief Pide a la clase <em>importarItems</em> cargar el archivo de los items preprocesados.

    \pre <em> cierto </em>>
    \post Devuelve los items preprocesados leídos por la clase <em>importarItems</em>
     */
    public LinkedList<HashMap<String, String>> loadPre(String path) {
        path += "/itemsPreprocessat.csv";

        return items.archivoCsvIn(path);
    }

    /** @brief Guardar en el directorio <em>path</em> en un fichero con el nombre "items.csv" el parámetro
     * <em>cjtItems</em>

    \pre <em> cierto </em>>
    \post Genera el archivo "items.csv" en el directorio especificado. Cada HashMap representa una item (una línea en
    el csv, donde la key es la cabecera y el value son los valores de los diferentes atributos).
     */
    public void saveItems(String path, LinkedList<HashMap<String, String>> cjtItems) {
        try {
            FileWriter itemsFile = new FileWriter(path+"/items.csv");

            if (!cjtItems.isEmpty()) {
                int count = 1;
                int mida = cjtItems.get(0).size();
                for(var it : cjtItems.get(0).entrySet()) {
                    if (count == mida) itemsFile.write(it.getKey());
                    else itemsFile.write(it.getKey() + ",");

                    ++count;
                }
                itemsFile.write(System.getProperty( "line.separator" ));
            }

            for (HashMap<String, String> it1 : cjtItems) {
                int count = 1;
                int mida = cjtItems.get(0).size();
                for (var it2 : it1.entrySet()) {
                    if (count == mida) itemsFile.write(it2.getValue());
                    else itemsFile.write(it2.getValue() + ",");

                    ++count;
                }
                itemsFile.write(System.getProperty( "line.separator"));
            }

            itemsFile.close();
            System.out.println("|-- Escriptura dels items realitzada correctament");
        }
        catch (IOException e) {
            System.out.println("|-- Error al generar el fitxer");
            e.printStackTrace();
        }
    }

    /** @brief Guardar en el directorio <em>path</em> en un fichero con el nombre "itemsPreprocessat.csv" el parámetro
     * <em>cjtItems</em>, en el caso de los atributos categóricos, guarda su representación obtenida del one-hot
     * encoding.
     *
     * \pre <em> cierto </em>>
     * \post Genera el archivo "itemsPreprocessat.csv" en el directorio especificado. Cada HashMap representa una
     * item (una línea en el csv, donde la key es la cabecera y el value son los valores de los diferentes atributos).
     * Para los atributos categóricos, los guarda en formato one-hot encoding.
     */
    public void saveItemsProcessats(String path, LinkedList<HashMap<String, String>> cjtItems, HashMap<String, String> tipos) {
        try {
            FileWriter itemsFile = new FileWriter(path+"/itemsPreprocessat.csv");

            if (!cjtItems.isEmpty()) {
                int count = 1;
                int mida = cjtItems.get(0).size();
                for(var it : cjtItems.get(0).entrySet()) {

                    if (count == mida) itemsFile.write(it.getKey());
                    else itemsFile.write(it.getKey() + ",");

                    ++count;
                }
                itemsFile.write(System.getProperty( "line.separator" ));
            }

            for (HashMap<String, String> it1 : cjtItems) {
                int count = 1;
                int mida = cjtItems.get(0).size();
                for (var it2 : it1.entrySet()) {
                    if (Objects.equals(tipos.get(it2.getKey()), "categorico")) {
                        BitSet atributoCat = setValor(it2.getValue());
                        String bitSetCategoric = atributoCat.toString();
                        bitSetCategoric = bitSetCategoric.replace(',', ';');
                        bitSetCategoric = bitSetCategoric.replace("{", "");
                        bitSetCategoric = bitSetCategoric.replace("}", "");
                        bitSetCategoric = bitSetCategoric.replace(" ", "");
                        if (count==mida) itemsFile.write(bitSetCategoric);
                        else itemsFile.write(bitSetCategoric + ",");
                    }
                    else if (count == mida) itemsFile.write(it2.getValue());
                    else itemsFile.write(it2.getValue() + ",");

                    ++count;
                }
                itemsFile.write(System.getProperty( "line.separator"));
            }

            itemsFile.close();
            System.out.println("|-- Preprocessament realitzat correctament");
        }
        catch (IOException e) {
            System.out.println("|-- Error al generar el fitxer");
            e.printStackTrace();
        }
    }

    /** @brief Dado el nombre <em>valor</em> de una categoría, devuelve su representación en one-hot
     *
     * \pre <em> cierto </em>>
     * \post Devuelve la representación en one-hot de la categoría con nombre <em>valor</em>
     */
    private BitSet setValor (String valor) {
        BitSet categoria = new BitSet();
        if (!valor.isEmpty()) {
            String[] cat = valor.split(";");
            for (int i = 0; i < cat.length; ++i) {
                if (!posicionsCategories.contains(cat[i])) posicionsCategories.add(cat[i]);
                Integer pos = posicionsCategories.indexOf(cat[i]);
                categoria.set(pos);
            }
        }
        return  categoria;
    }

    /** @brief Comprueba que exista el archivo "items.csv"
     * \pre <em> cierto </em>>
     * \post Devuelve <em>true</em> si existe el archivo; <em>false</em> al contrario
     **/
    public boolean existeixItems(String path) {
        path += nomFitxer;

        return items.existeArchivo(path);
    }

    /** @brief Comprueba que exista el archivo "itemsPreprocessat.csv"
     * \pre <em> cierto </em>>
     * \post Devuelve <em>true</em> si existe el archivo; <em>false</em> al contrario
     **/
    public boolean existeixPrepro(String path) {
        path += "/itemsPreprocessat.csv";

        return items.existeArchivo(path);
    }
}
