package Persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import Domain.Classes.Item;
import Domain.Classes.Pair;

/** @file importarItems.java
 @brief Implementación de la clase importarItems
 */

/** @class importarItems
 @brief Representa la implementación de importarItems.

 Esta clase sirve para hacer la importación de Items, a partir de un .csv en cada fila habiendo atributos
 separados por comas.
 */

public class ImportarItems {

    private LinkedList<Item> Items;
    final String separador = ",";
    //Apuntador de id del Item que es creara a continuacio

    /** @brief Creadora de la clase importadora de Items.

    \pre <em> Cierto </em>
    \post Crea la clase importarItems.
     */
    public ImportarItems(){
        Items = new LinkedList<>();
    }

    /** @brief A partir de un hashmap con atributo, valor de atributo y otro hashmap de atributo, tipo de atributo
     * crea el item, le asigna su id y llama a la creadora de item con asignador de atributos.

    \pre <em> Cierto </em>
    \post Crea el item con id=atributos.id y le asigna sus valores de atributo.
     */
    private void datosToItem(HashMap<String, String> atributs, HashMap<String, String> tipusAtributs) throws FileNotFoundException {
        String idAux = atributs.get("id");
        Integer id = -1;
        try { id = Integer.parseInt(idAux);}
        catch (NumberFormatException nfe) {
            ////System.out.println("Error de atribut numeric");
        }
        atributs.remove("id");
        Item i = new Item(id, atributs, tipusAtributs);
        Items.add(i);
    }
    /** @brief Operación auxiliar que cuenta las comillas de una string.
     * Se utiliza para poder identificar cuando un freetext de comillas se acaba.
    \pre <em> Cierto </em>
    \post Retorna el número de comillas de s
     */
    public Integer countComma(String s){
        Integer result = 0;
        //System.out.println(s);
        for (int i = 0; i < s.length(); ++i){
            if (s.charAt(i)=='"') ++result;
        }
        //System.out.println(result + " result");
        return result;
    }
    /** @brief Operación auxiliar que pone en un solo hashmap el nombre del atributo y su valor, a partir de dos listas/arrays.
     * También tiene funcionalidad de detectar texto en estos atributos, ya que a veces se separan por comas y quedan desordenados.
    \pre <em> Cierto </em>
    \post Retorna hashmap de <atributo, valor de atributo>
     */
    public HashMap<String, String> toAttribute(String [] cabecera, List<String> valor) {
       /*

        System.out.println("Cabecera");
        for (String atributo : cabecera) {
            System.out.println(atributo);
        }
        System.out.println("Tupla");
        for (String s : valor) {
            System.out.println(s);
        }
        System.exit(0);

        */
        HashMap<String, String> attributeMap = new HashMap<>();
        int auxAtt, auxVal;
        auxAtt  = 0;
        auxVal = 0;
        // Mientras auxAtt sea menor al numero de elementos de la cabecera
        while (auxAtt < cabecera.length){
            String label = cabecera[auxAtt];
            //Si el atributo es vacio, tendra como valor "-"
            String val = "";
            if (!valor.get(auxVal).isEmpty()) {                                               //Si el atributo no es vacio (como en belongs_to_collection)
                val = valor.get(auxVal);                                                     //Hi ha problemes si es separa per comes; es pot separar un atribut de tipus FreeText en varis atributs degut al separador.
                char c = '"';
                if (val.charAt(0)==c) {                                                  // si troba FreeText comencat per " ,
                    Integer comma = countComma(valor.get(auxVal));
                    //System.out.println(comma + " COMMA ");
                    while ((valor.get(auxVal).charAt(valor.get(auxVal).length()-1)!=c) || (comma%2!=0)) {
                        ++auxVal;                                                           //mira cada linia fins que troba el final del FreeText senyalat amb ".
                        val += separador;
                        val += valor.get(auxVal);
                        comma += countComma(valor.get(auxVal));
                        //System.out.println(comma + " COMMA ");
                    }
                }
            }
            attributeMap.put(label, val);
            ++auxAtt;
            ++auxVal;
            //System.out.println(label+ "-" + val);
        }

        // Retorna  hashmap <atributo,  valor de atributo>
        //          HashMap <String,    String>
        /*for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
            System.out.println("Clave: " + entry.getKey() + ", Valor: " + entry.getValue());
        }*/
        return attributeMap;
    }

    /** @brief Operación principal de la clase importadora. A partir de un archivo csv con items, y otro con los tipos de atributos,
     * crea los items respectivos en el sistema y los inicializa correctamente con sus atributos.
    \pre <em> Cierto </em>
    \post Crea en el sistema items a partir del fichero .csv de items
     */
    public LinkedList<HashMap<String, String>> archivoCsvIn(String nomArchivoCsv ) {
        //Pre: recibe un nombre de fichero con extensión csv válido que contiene la información de las
        //     normalizadas para posterior tratamiento.
        //Post: lee correctamente el archivo y almacena en una estructura de datos.
        String fila;
        String tipos;
        LinkedList<HashMap<String, String>> resultat = new LinkedList<>();
        try {
            // Creamos un objeto fileReader y le pasamos el nombre del archivo csv
            FileReader fr = new FileReader(nomArchivoCsv);
            BufferedReader br = new BufferedReader(fr);
            // Leemos la primera linea del fichero .csv
            fila = br.readLine(); // obtenemos la linea completa
            // creamos un vector donde el size es cero,
            // pero cambiara dinamicamente para ajustarse al numero de elementos de la cabecera
            String [] cabecera =new String [0];
            HashMap<String, String> tiposAtributo = new HashMap<>();
            if(!(null == fila)) {
                cabecera = fila.split(separador);
                fila = br.readLine();
            }
            while(!(null == fila)) {
                // vector vector_registros contiene los campos de la fila i [campo1, campo2, ..., campo_n]
                // ejemplo: [false,"", 3000000, animation,...]
                // cabecera es un vector que contiene los valores de las cabeceras de las columnas
                String[] vector_registros = fila.split(separador, -1); // -1 no hay límites de divisiones (es decir, ilimitado numeros de campos)

                List<String> tupla = new ArrayList<>();
                Collections.addAll(tupla, vector_registros);

                /*

                System.out.println("Tupla: " + tupla);
                System.out.println("Vector_registros : ");
                for(String valor_registro: vector_registros) {
                    System.out.println(valor_registro);
                }
                System.out.println("---------------------------");

                */

                while (tupla.size() < cabecera.length) {

                    String fila2 = br.readLine();
                    fila = fila.concat(fila2);
                    vector_registros = fila.split(separador, -1);
                    tupla = new ArrayList<>();
                    Collections.addAll(tupla, vector_registros);

                }

                // le pasamos el vector cabecera y la tupla
                //System.out.println("Tupla: " + tupla);
                HashMap<String, String> newItem;
                newItem = toAttribute(cabecera, tupla); // HashMap<String, String>
                resultat.add(newItem);

                fila = br.readLine();
            }
            br.close();
            // LinkedList<HashMap<String, String>> resultat = new LinkedList<>();
            /*for(HashMap<String, String> item : resultat) {
                System.out.println(item.get("id"));
            }*/

            return resultat;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("SIZE :" + size);
        return null;
    }

    /** @brief Getter de la lista de items del sistema.
    \pre <em> Cierto </em>
    \post Retorna los items que han sido creados por esta clase.
     */
    public LinkedList<Item> getItems() {
        return Items;
    }

    public boolean existeArchivo(String path) {
        File f = new File(path);
        return f.exists();
    }
}



