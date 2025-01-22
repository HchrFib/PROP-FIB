package Persistence;

import Domain.Classes.Registro;

import java.io.*;
import java.util.HashMap;

/** @file Data.java
 @brief Implementación de la clase Data.
 */
/** @class DataSet
 @brief Esta clase está implementada con el objetivo de leer los datos de entrada y guardalos en una estrucura de datos adecuada en un formato para ser tratados por el <em>algoritmo KMeans</em>.
 Clase implementada por: Christian Chávez Apcho.
 DEFINICIÓN DE REGISTRO: es una tupla 'userId, item i = valoración, item i+1= valoración, .... , item n= valoración'
 */
public class Data {

    private HashMap<Integer, Registro> registros;
    /** @brief Constructor por defecto

    \pre <em> Cierto</em>
    \post Crea el objeto de tipo <em>DataSet</em> con sus valores por defecto
     */
    public Data() {
        this.registros = new HashMap<>();
    }
    // Getters
    /** @brief Getter - Consultora de registros (pares item-valoración) de <em>usuarios</em>.
    \pre <em> Cierto </em>
    \post Retorna un conjunto de pares item-valoración en un LinkedList de registros.
     */
    public HashMap<Integer, Registro> getRegistros() {
        return registros;
    }
    //Setters
    /** @brief Setter - Asigna registros.
    \pre <em> Cierto </em>
    \post Asigna un conjunto de registros.
     */
    public void setRegistros(HashMap<Integer, Registro> registros) {
        this.registros = registros;
    }

    /** @brief Lee un archivo .csv(en nuestro caso ratings.db.csv) que contiene
     * que contiene los usuario y sus respectivas valoraciones que serán usados como entrada del
     * del algoritmo KMeans.

    \pre Recibe un nombre de fichero válido no vacío.
    \post cierto-
     */
    public void archivoCsvIn(String nomArchivoCsv ) {
        String fila;
        final String separador = ",";
        try {
            FileReader fr = new FileReader(nomArchivoCsv);
            BufferedReader br = new BufferedReader(fr);
            fila = br.readLine();
            if(!(null == fila))  fila = br.readLine();
            else throw new NullPointerException();



            assert fila != null;
            String[] campo = fila.split(separador);
            int userId = Integer.parseInt(campo[0]);
            int itemId = Integer.parseInt(campo[1]);
            Double rating =  Double.parseDouble(campo[2]);

            int anterior = userId;
            HashMap<Integer, Double> registro = new HashMap<>();
            registro.put(itemId, rating);

            fila = br.readLine();
            int i = 1; // empezamos a contar los registros desde 1
            while(!(null == fila)) {
                campo = fila.split(separador);

                userId = Integer.parseInt(campo[0]);
                itemId = Integer.parseInt(campo[1]);
                rating =  Double.parseDouble(campo[2]);

                if(userId == anterior) {
                    registro.put(itemId, rating);

                } else {
                    Registro reg = new Registro(registro);
                    reg.setUserRegistro(anterior);
                    registros.put(i, reg);
                    anterior = userId;
                    registro = new HashMap<>();
                    registro.put(itemId, rating);
                    ++i;
                }


                fila = br.readLine();
            }

            Registro reg = new Registro(registro);
            reg.setUserRegistro(anterior);
            registros.put(i, reg);
            //System.out.println("Acabo de terminar de leer ;)");

        } catch (FileNotFoundException e) {
            System.out.println(nomArchivoCsv + ": no encontrado"  );
        } catch (NullPointerException e) {
            System.out.println(nomArchivoCsv + " está vacío");
        } catch (Exception e) {
            System.out.println(nomArchivoCsv + " contiene algún tipo de error");
        }
    }
}

