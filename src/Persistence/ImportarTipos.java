package Persistence;

import java.io.*;
import java.util.*;

public class ImportarTipos {

    /** @brief Creadora por vacia
     *
     * \pre <em> Cierto </em>
     * \post Crea el objeto de la classe importarTipos
     */
    public ImportarTipos() {}

    /** @brief Comprueba que exista el archivo en <em>path</em>
     * \pre <em> cierto </em>>
     * \post Devuelve <em>true</em> si existe el archivo; <em>false</em> al contrario
     **/
    public boolean existeArchivo(String path) {
        File f = new File(path);

        return f.exists();
    }

    /** @brief Carga el archivo especificado por el parámetro <em>path</em>
     * \pre <em> cierto </em>>
     * \post Devuelve un HashMap donde la key es la primera línea leída y el value es la segunda
     **/
    public HashMap<String, String> load(String path) {
        System.out.println("Class ImportarTipos: " + path);
        String fila;
        LinkedList<String> resultat = new LinkedList<>();

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            fila = br.readLine();

            while(fila != null) {
                resultat.add(fila);
                fila = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error: lectura tipos");
        }

        return transformData(resultat);
    }

    /** @brief Transforma los datos de la LinkedList a un HashMap.
    \pre <em> cierto </em>>
    \post Devuelve los datos transformados.
     */
    private HashMap<String, String> transformData(LinkedList<String> tiposSucio) {
        HashMap<String, String> tiposAtributo = new LinkedHashMap<>();
        String[] cabecera = tiposSucio.get(0).split(",");
        String[] tiposCabecera = tiposSucio.get(1).split(",");

        for (int i=0; i<cabecera.length; i++) {
            tiposAtributo.put(cabecera[i], tiposCabecera[i]);
        }

        return tiposAtributo;
    }
}

