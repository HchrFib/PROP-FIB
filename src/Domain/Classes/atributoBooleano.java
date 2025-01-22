package Domain.Classes;
/** @file atributoBooleano.java
 @brief Implementación de la clase atributoBooleano
 */

/** @class atributoBooleano
 @brief Representa la implementación de un atributo de tipo bool.

 Este tipo de Atributo tendrá por valor un booleano siempre.
 */

public class atributoBooleano extends Atributo {
    //Attributes
    private Boolean valorBool;

    //CREADORA

    /** @brief Creadora con paso de parámetros.

    Crea un objeto de la clase atributoBooleano.
    \pre <em>Cierto</em>
    \post Crea un atributoBooleano con tipo = 'type' y valor = "valor", invocando la operacion creadora del padre (Atributo).
     */
    public atributoBooleano(tipoAtributo type, String valor) {
        super(type, valor);
    }

    //GETTERS

    /** @brief Consultora (Getter) del valor booleano del atributo.
    \pre <em> Cierto </em>
    \post Retorna el valor booleano del atributo (puede ser null)
     */
    public Boolean getBool() {
        return valorBool;
    }

    /** @brief Consultora (Getter) del valor booleano del atributo en forma de Double.
    \pre <em> Ha de tener valor asignado </em>
    \post Retorna el valor en Double del valor booleano; 1 si es "True", 0 si es "False"
     */
    public Double boolToInt() {
        if (valorBool == null) return null;
        if (!valorBool) return 0.0;
        else if (valorBool) return 1.0;
        else return null;
    }

    //SETTERS

    /** @brief Metodo auxiliar que pasa una String a booleano. En el futuro podra expandirse para admitir mas tipos de input.
    \pre <em> s == "True" || s == "False" </em>
    \post Retorna valor booleano correspondiente a la string.
     */
    private Boolean stringToBool(String s) { //nomes admet True o False com a input
        if (!s.isEmpty()){
            if (s == "True" || s == "Cert" || s == "cert" || s == "true" || s == "CERT") return true;
            else return false;
        }
        else return null;
    }

    /** @brief Asignadora del Valor de un atributoBooleano. En esta clase, el metodo hace Override del metodo del padre para pasar correctamente
     * Un valor en forma de String a valor Booleano.
     * De momento, solo toma en cuenta las opciones de input "True" para Verdadero y "False" para Falso.
    \pre <em> Cierto </em>
    \post Modifica el valor del Atributo por valor.
     */
    @Override
    public void setValor(String valor) {
        valorBool = stringToBool(valor);
    }

}

