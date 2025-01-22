package Persistence;
import Domain.Classes.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;


/** @file importarRatings.java
 @brief Implementación de la clase importarRatings
 */

/** @class importarRatings
 @brief Representa la implementación de importarRatings.

 Esta clase sirve para hacer la importación de Ratings, a partir de un .csv en cada fila separada por comas.
 Además, asignará a los items las valoraciones que se han hecho sobre ellos, asimismo a los usuarios las valoraciones que
 ha hecho
 */
public class ImportarRatings {

    private HashMap<Integer, Usuario> usuarios;
    private HashMap<Integer, Item> itemsValorados;

    /** @brief Creadora de la clase importadora de Ratings.

    \pre <em> Cierto </em>
    \post Crea la clase importarRatings.
     */
    //Constructor por defecto
    public  ImportarRatings() {
        this.usuarios = new HashMap<>();
        this.itemsValorados = new HashMap<>();
    }
    /** @brief Getter de los usuarios importados.

    \pre <em> Cierto </em>
    \post Retorna los usuarios importados.
     */
    // Getters
    public HashMap<Integer, Usuario> getUsuarios() {
        return usuarios;
    }

    /** @brief Getter de los items valorados.

    \pre <em> Cierto </em>
    \post Retorna los items que se han valorado importados.
     */
    public HashMap<Integer, Item> getItemsValorados() {
        return itemsValorados;
    }
    //Setters

    /** @brief Setter de los usuarios.

    \pre <em> Cierto </em>
    \post this.usuarios = usuarios.
     */
    public void setUsuarios(HashMap<Integer, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /** @brief Setter de los itemsValoraos.

    \pre <em> Cierto </em>
    \post this.itemsValorados=itemsValorados.
     */
    public void setItemsValorados(HashMap<Integer, Item> itemsValorados) {
        this.itemsValorados = itemsValorados;
    }
    //Método

    /** @brief Operación principal de la clase importadora. A partir de un archivo csv con ratings de items por usuarios,
     * crea en el sistema los items, usuarios y los relaciona según las valoraciones.
    \pre <em> Cierto </em>
    \post Items y Usuarios del .csv son creados, se asocian entre ellos.
     */
    public void archivoCsvIn(String nomArchivoCsv ) {
        String fila;
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

                //actualitzem Usuarios
                if (!usuarios.containsKey(userId)) {
                    Usuario user = new Usuario(userId);
                    usuarios.put(userId,user);
                }
                usuarios.get(userId).anadirValoracion(itemId,rating);
                //actualitzem items
                if (!itemsValorados.containsKey(itemId)) {
                    Item item = new Item(itemId);
                    itemsValorados.put(itemId,item);
                }
                itemsValorados.get(itemId).anadirValoracion(userId,rating);
                fila = br.readLine(); //add new
            }
        } catch (Exception e) {
            System.out.println("Error lectura archivo csv");
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

    /** @brief Comprueba que exista el archivo en <em>path</em>
     * \pre <em> cierto </em>>
     * \post Devuelve <em>true</em> si existe el archivo; <em>false</em> al contrario
     **/
    public boolean existeArchivo(String path) {
        File f = new File(path);

        return f.exists();
    }
}
