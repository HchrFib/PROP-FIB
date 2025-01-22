package Domain.Classes;
/** @file Atributo.java
 @brief Implementación de la clase Atributo
 */

/** @class Atributo
 @brief Representa la implementación de un Atributo.

 Cada Atributo tiene un tipo y un valor asignado Ex: "tipo: Categorico, valor: [ingles, frances, catalan]."
 Cada Atributo pertenece en forma de Composición a un Item.
 */

public class Atributo {
    //Attributes
    private tipoAtributo tipo;
    private String valor;
    //Relaciones
    //private Item item;

    /** @brief Creadora con paso de parámetros.

    Crea un objeto de la clase Atributo.
    \pre <em>Cierto</em>
    \post Crea un Atributo con tipo = 'type' y valor = "valor".
     */

    public Atributo(tipoAtributo type, String valor) {
        tipo = type;
        this.valor = valor;
    }

    //CONSULTORAS

    /** @brief Consultora (Getter) del tipo de un Atributo.

    \pre <em> Cierto </em>
    \post Retorna el tipo de Atributo.
     */
    public tipoAtributo getTipo() {
        return tipo;
    }

    /** @brief Consultora (Getter) del valor de un Atributo.

    \pre <em> Cierto </em>
    \post Retorna el valor de Atributo.
     */
    public String getName() {
        return valor;
    }

    //MODIFICADORAS

    /** @brief Asignadora del Valor inicial de un Atributo. En esta clase no está implementado como método, sino en las hijas de la clase Atributo.

    \pre <em> Cierto </em>
    \post Modifica el valor del Atributo por valor.
     */
    public void setValor(String valor){}

    public tipoAtributo detectaTipo(String valor) {
        if (valor.equals("-")) return tipoAtributo.vacio;
        else if (valor.equals("True") || valor.equals("False")) return tipoAtributo.booleano;
        else if (valor.startsWith("\"") && valor.endsWith("\"")) return tipoAtributo.freetext;
        else {
            try {
                Integer i = Integer.parseInt(valor);  // Para enteros
                return tipoAtributo.categorico;
            } catch (NumberFormatException nfe) {
                try {
                    Double d = Double.parseDouble(valor);  // Para decimales
                    return tipoAtributo.categorico;
                } catch (NumberFormatException nfe2) {
                    return tipoAtributo.categorico;
                }
            }
        }
    }


    /** @brief Modificadora (Setter) del valor de un Atributo.

    \pre <em> Cierto </em>
    \post Modifica el valor del Atributo por "name".
     */
    public void setName(String name) {
        this.valor = name;
    }
    /** @brief Modificadora (Setter) del tipo de un Atributo.

    \pre <em> Cierto </em>
    \post Modifica el tipo del Atributo por "tipo".
     */
    public void setTipo(tipoAtributo tipo) {
        this.tipo = tipo;
    }

}
