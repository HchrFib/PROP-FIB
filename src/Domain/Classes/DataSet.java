package Domain.Classes;

import java.util.HashMap;
/** @file DataSet.java
 @brief Implementación de la clase DataSet.
 */
/** @class DataSet
 @brief Esta clase está implementada con el objetivo de leer los datos de entrada y guardarlos en una estructura de datos adecuada en un formato para ser tratados por el <em>algoritmo KMeans</em>.
 Clase implementada por: Christian Chávez Apcho.

 */
public class DataSet {
    private HashMap<Integer, Registro> registros;
    /** @brief Constructor por defecto

    \pre <em> Cierto</em>
    \post Crea el objeto de tipo <em>DataSet</em> con sus valores por defecto
     */
    public DataSet() {
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
}

