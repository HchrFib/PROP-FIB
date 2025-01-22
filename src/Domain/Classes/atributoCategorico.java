package Domain.Classes;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

/** @file atributoCategorico.java
 @brief Implementación de la clase atributoCategorico
 */

/** @class atributoCategorico
 @brief Representa la implementación de un atributo de tipo Categorico.

 Este tipo de Atributo tendrá por valor un conjunto de categorías.
 */

public class atributoCategorico extends Atributo {

    //Attributes
    private BitSet categoria;
    private static List<String> posicionsBitset;


    static {
        posicionsBitset = new LinkedList<>();
    }

    //CREADORA

    /** @brief Creadora con paso de parámetros.

    Crea un objeto de la clase atributoCategorico.
    \pre <em>Cierto</em>
    \post Crea un atributoCategorico con tipo = 'type' y valor = "valor", invocando la operación creadora del padre (Atributo).
     */
    public atributoCategorico(tipoAtributo type, String id) {
        super(type, id);
        categoria = new BitSet();
    }

    //GETTERS

    /** @brief Consultora (Getter) del tamaño de la lista de categorías.
    \pre <em> Cierto </em>
    \post Retorna el tamaño de la lista de categorias.
     */
    public Integer getSize() {return  categoria.size();}


    /** @brief Consultora (Getter) de la lista de categorias.
    \pre <em> Cierto </em>
    \post Retorna la lista de categorias en formato one-hot;.
     */
    public BitSet getCategoria(){return  this.categoria;}

    /** @brief Consultora (Getter) por índice de la lista de categorías.
    \pre <em> 0 <= index < this.categoria.size()  </em>
    \post Retorna el elemento en posicion index de la lista de categorías.
     */
    public Boolean getPos(Integer index){return this.categoria.get(index);}

    //SETTERS

    /** @brief Asignadora del Valor de un atributoCategorico. En esta clase, el metodo hace Override del metodo del padre para pasar correctamente
     * Un valor en forma de String a valor de conjunto de Strings que representa categorias.
    \pre <em> Cierto </em>
    \post Modifica el valor del Atributo por valor. this.categoria = conjunto de categorias en formato one-hot. Asumimos como separador de categorias el caracter ';'
     */
    @Override
    public void setValor (String valor) {
        System.out.println("String valor "+ valor);
        if (!valor.isEmpty()) {
            String[] cat = valor.split(";");
            for (int i = 0; i < cat.length; ++i) {
                if (!posicionsBitset.contains(cat[i])) posicionsBitset.add(cat[i]);
                Integer pos = posicionsBitset.indexOf(cat[i]);
                categoria.set(pos);
            }
        }
    }

    /** @brief Asignadora de categorias del atributo por la lista de categorias pasada por parametro.
    \pre <em> Cierto </em>
    \post Modifica el valor de this.categoria por categoria.
     */
    public void setCategorias(BitSet categoria){this.categoria=categoria;}

}
