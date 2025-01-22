package Domain.Classes;

import java.util.*;
/** @file Usuario.java
 @brief Implementación de la clase Usuario
 */

/** @class Usuario
 @brief Representa la implementación de un Usuario.

 Cada usuario se identifica por su "id" y tiene una lista
 de "valoraciones" que ha realizado sobre un conjunto de items.
 */
public class Usuario {
    private int id;
    private int numValoraciones;
    //first == id item; second == rating
    private HashMap<Integer, Double> valoraciones;

    /** @brief Creadora con paso de parámetros.

    Crea un objeto de la clase Usuario.
    \pre <em>Cierto</em>
    \post Crea un usuario con id = 'id', una lista de valoraciones vacía y el número total de valoraciones a 0.
     */
    public Usuario (int id) {
        this.id = id;
        this.valoraciones = new HashMap<>();
        this.numValoraciones = 0;
    }

    /** @brief Creadora con paso de parámetros (2).

    Crea un objeto de la clase Usuario.
    \pre <em>Cierto</em>
    \post Crea un usuario con id = 'id' y almacena la lista de valoraciones
    pasada por parámetro. También guarda el valor "numValoraciones", que es igual
    al tamaño de la lista "valoraciones".
     */
    public Usuario (int id, Map<Integer, Double> valoraciones) {
        this.id = id;
        this.valoraciones = new HashMap<>(valoraciones);
        numValoraciones = valoraciones.size();
    }

    // CONSULTORAS

    /** @brief Consultora (Getter) del id de un usuario.

    \pre <em> Cierto </em>
    \post Retorna el id del usuario.
     */
    public int getId() {
        return id;
    }

    /** @brief Consultora (Getter) de la lista de valoraciones de un usuario.

    \pre <em> Cierto </em>
    \post Retorna la lista de valoraciones del usuario.
     */
    public HashMap<Integer, Double> getValoraciones() {
        return this.valoraciones;
    }

    /** @brief Consultora (Getter) del número de valoraciones realizadas por un usuario.

    \pre <em> Cierto </em>
    \post Retorna el número de valoraciones realizadas por el usuario.
     */
    public Integer getNumValoraciones() {return this.numValoraciones;}

    /** @brief Consultora de la existencia de una valoración

    \pre <em> Cierto </em>
    \post Retorna "cierto" en caso de que el usuario haya valorado ese item y "falso" en caso contrario.
     */
    public boolean haValorado(Integer itemId) {
        return valoraciones.containsKey(itemId);
    }

    // MODIFICADORAS

    /** @brief Modificadora (Setter) del id de un usuario.

    \pre <em> Cierto </em>
    \post Modifica el id del usuario para que sea el que se pasa por parámetro.
     */
    public void setId(int id) {
        this.id = id;
    }

    /** @brief Modificadora (Setter) de las valoraciones de un usuario.

    \pre <em> Cierto </em>
    \post Las valoraciones del usuario pasan a ser las que se pasan por parámetro.
     */
    public void setValoraciones(HashMap<Integer, Double> valoraciones) {
        this.valoraciones = valoraciones;
    }

    /** @brief Modificadora (Setter) del número de valoraciones de un usuario.

    \pre <em> Cierto </em>
    \post El número de valoraciones realizadas por el usuario pasa a ser el que se pasa por parámetro.
     */
    public void setNumValoraciones(Integer n) {this.numValoraciones = n;}

    /** @brief Añade una nueva valoración a la lista.

    \pre El usuario no ha valorado todavía el item con id = itemId
    \post Se añade a la estructura de datos "valoraciones" una nueva valoración
    del item con id = itemId y rating = rating.
     */
    public void anadirValoracion(Integer itemId, Double rating) throws Exception {
        if (this.valoraciones.containsKey(itemId)) {
            throw new Exception("Item de id: " + itemId + " ja valorat per l'usuari");
        }
        else {
            this.valoraciones.put(itemId,rating);
            ++this.numValoraciones;
        }
    }
}
