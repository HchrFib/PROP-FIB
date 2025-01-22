package Domain.Classes;

/** @file atributoFreetext.java
 @brief Implementación de la clase atributoFreetext
 */

/** @class atributoFreetext
 @brief Representa la implementación de un atributo de tipo Freetext.
 */
public class atributoFreetext extends Atributo {
    //Attributes
    private String text;

    //CREADORA

    /** @brief Creadora con paso de parámetros.

    Crea un objeto de la clase atributoFreetext.
    \pre <em>Cierto</em>
    \post Crea un atributoFreetext con tipo = 'type' y valor = "valor", invocando la operacion creadora del padre (Atributo).
     */
    public atributoFreetext(tipoAtributo type, String valor) {
        super(type, valor);
    }

    //SETTERS

    /** @brief Asignadora del Valor de un atributoFreetext. En esta clase, el metodo hace Override del metodo del padre para pasar correctamente
     * Un valor en forma de String.
    \pre <em> Cierto </em>
    \post this.text = valor
     */
    @Override
    public void setValor (String valor) {
        text = valor;
    }

    //GETTERS
    /** @brief Getter del valor textual
    \pre <em>Cierto</em>
    \post Devuelve text
     */
    public String getText(){return  text;}

}

