package Domain.Classes;

/** @file atributoNumerico.java
 @brief Implementación de la clase atributoNumerico
 */

/** @class atributoNumerico
 @brief Representa la implementación de un atributo de tipo Numerico.

 Este tipo de Atributo tendra por valor Numerico en forma de double.
 */
public class atributoNumerico extends Atributo {
    //Attributes
    private Double valorNum;

    //CREADORA

    /** @brief Creadora con paso de parámetros.

    Crea un objeto de la clase atributoNumerico.
    \pre <em>Cierto</em>
    \post Crea un atributoNumerico con tipo = 'type' y valor = "valor", invocando la operacion creadora del padre (Atributo).
     */
    public atributoNumerico(tipoAtributo type, String valor) {
        super(type, valor);
    }

    //SETTERS

    /** @brief Asignadora del Valor de un atributoNumerico. En esta clase, el metodo hace Override del metodo del padre para pasar correctamente
     * Un valor en forma de String a valor numerico en forma de Double.
    \pre <em> Cierto </em>
    \post Modifica el valor del Atributo por valor. valorNum = valor numerico de valor
     */
    @Override
    public void setValor (String valor) {
        try { valorNum = Double.parseDouble(valor);}
        catch (NumberFormatException nfe) {
            valorNum=null;
        }
    }

    /** @brief Asignadora del Valor de un atributoNumerico. En esta clase, el metodo hace Override del metodo del padre para pasar correctamente
     * Un valor en forma de String a valor numerico en forma de Double.
    \pre <em> Cierto </em>
    \post Modifica el valor del Atributo por valor. valorNum = valor numerico de valor
     */
    public void setValor(Double d){valorNum=d;}


    /** @brief Consultora (Getter) del valor numerico del atributo.
    \pre <em> Cierto  </em>
    \post Retorna el valor en Double del atributo
     */
    public Double getValor(){return valorNum;}

}
