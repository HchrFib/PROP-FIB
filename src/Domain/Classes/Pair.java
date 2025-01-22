package Domain.Classes;
/** @file Pair.java
 @brief Implementación de la clase Pair
 */

/** @class Pair
 @brief Representa la implementación de una estructura de datos que permite almacenar pares de datos.
 Cada par contiene dos elementos. Ambos pueden ser del mismo tipo, o de tipos de datos diferentes.

*/
public class Pair <E, T> {

    /** @brief Primer elemento del Pair */
    private E first;

    /** @brief Segundo element del Pair */
    private T second;

    // CREADORAS

    /** @brief Creadora por defecto.

    Crea un objeto de la clase Pair.
    \pre <em>Cierto</em>
    \post Crea un par vacío (no contiene ningún elemento).
     */
    public Pair () {}

    /** @brief Creadora con paso de parámetros.

    Crea un objeto de la clase Pair.
    \pre <em>Cierto</em>
    \post Crea un par con el primer elemento igual a 'first' y el segundo igual a 'second'.
     */
    public Pair (E first, T second) {
        this.first = first;
        this.second = second;
    }

    // CONSULTORAS

    /** @brief Consultora (Getter) del primer elemento de un Pair.

    \pre <em> Cierto </em>
    \post Retorna el elemento 'first'.
     */
    public E getFirst() {
        return first;
    }

    /** @brief Consultora (Getter) del segundo elemento de un Pair.

    \pre <em> Cierto </em>
    \post Retorna el elemento 'second'.
     */
    public T getSecond() {
        return second;
    }

    // MODIFICADORAS

    /** @brief Modificadora (Setter) del primer elemento de un Pair.

    \pre <em> Cierto </em>
    \post Modifica el elemento 'first' del par para que sea el que se pasa por parámetro.
     */
    public void setFirst(E first) {
        this.first = first;
    }

    /** @brief Modificadora (Setter) del segundo elemento de un Pair.

    \pre <em> Cierto </em>
    \post Modifica el elemento 'second' del par para que sea el que se pasa por parámetro.
     */
    public void setSecond(T second) {
        this.second = second;
    }

}

