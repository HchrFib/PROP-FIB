package Persistence;

import java.io.*;
import java.util.*;

public class CtrlTipos {
    /** @brief nombre del fichero donde se almacenan los tipos
     **/
    final String nomFitxer = "/tipos.csv";

    /** @brief Al usar CtrlTipos un patron singleton, esta estructura guarda la única instancia del controlador de
     *  tipos
     **/
    private static CtrlTipos instance = null;

    /** @brief Instancia de la clase importarTipos
     **/
    private final ImportarTipos tipos;

    /** @brief Creadora por defecto
     *
     * \pre <em> Cierto </em>
     * \post Crea el objeto de la clase CtrlTipos
     */
    public CtrlTipos() {
        tipos = new ImportarTipos();
    }

    /** @brief Obtener la instancia de CtrlTipos

    Permite la implementación del patrón singleton de esta clase.
    \pre <em> cierto </em>>
    \post Retorna la instancia de la clase en caso de que ya existiese una. En caso contrario,
    crea una nueva instancia y la retorna.
     */
    public static CtrlTipos getInstance() {
        if (instance == null) {
            instance = new CtrlTipos();
        }
        return instance;
    }

    /** @brief Pide a la clase <em>importarTipos</em> cargar el archivo de tipos.

    \pre <em> cierto </em>>
    \post Devuelve los tipos leídos por la clase <em>importarTipos</em>
     */
    public HashMap<String, String> loadTipos(String path) {
        path += nomFitxer;
        return tipos.load(path);
    }

    /** @brief Guardar en el directorio <em>path</em> en un fichero con el nombre "tipos.csv" el parámetro
     * <em>tipos</em>
     * \pre <em> cierto </em>>
     * \post Genera el archivo "tipos.csv" en el directorio especificado. La key del HashMap se escribe en la primera
     * línea que será la cabecera del .csv, y el value será la primera línea del csv
     */
    public void saveTipos(String path, HashMap<String, String> tipos) {
        path += nomFitxer;

        try {
            // Leer el archivo existente (si existe)
            List<String> lines = new ArrayList<>();
            File file = new File(path);

            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                reader.close();
            } else {
                // Si el archivo no existe, inicializamos las líneas
                lines.add(""); // Línea 1
                lines.add(""); // Línea 2
            }

            // Asegurarnos de que haya al menos 2 líneas
            while (lines.size() < 2) {
                lines.add("");
            }
            // Convertir las líneas existentes en mapas para facilitar la actualización
            String[] labels = lines.get(0).split(",");
            String[] values = lines.get(1).split(",");
            Map<String, String> currentMap = new LinkedHashMap<>();

            // Convertir solo las claves a minúsculas, quitar todos los espacios internos y los extremos
            for (int i = 0; i < labels.length; i++) {
                String key = labels[i].trim().toLowerCase().replaceAll("\\s+", "");  // Eliminar todos los espacios y convertir a minúsculas
                String value = (i < values.length) ? values[i] : "";  // Mantener los valores tal cual
                if (!key.isEmpty()) { // Evitar entradas vacías
                    currentMap.put(key, value);
                }
            }

            // Actualizar el mapa con las nuevas claves y valores
            for (var entry : tipos.entrySet()) {
                String key = entry.getKey().trim().toLowerCase().replaceAll("\\s+", "");  // Eliminar todos los espacios y convertir a minúsculas
                String value = entry.getValue();  // Mantener el valor tal cual
                currentMap.put(key, value);  // Se actualizará si ya existe
            }

            // Reconstruir las líneas
            StringBuilder newLine1 = new StringBuilder();
            StringBuilder newLine2 = new StringBuilder();

            for (var entry : currentMap.entrySet()) {
                if (newLine1.length() > 0) {
                    newLine1.append(",");
                    newLine2.append(",");
                }
                newLine1.append(entry.getKey());
                newLine2.append(entry.getValue());
            }

            // Actualizar las líneas en la lista
            lines.set(0, newLine1.toString());
            lines.set(1, newLine2.toString());

            // Reescribir el archivo actualizado
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();

            System.out.println("|-- Escritura del tipos realizada correctamente");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /** @brief Comprueba que exista el archivo "tipos.csv"
     *  \pre <em> cierto </em>>
     * \post Devuelve <em>true</em> si existe el archivo; <em>false</em> al contrario
     **/
    public boolean existeTipos(String path) {
        path += nomFitxer;
        return tipos.existeArchivo(path);
    }
}

