package Domain.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** @file Item.java
 @brief Implementación de la clase Item
 */

/** @class Item
 @brief Representa la implementación de un Item.

 Cada Item se identifica por su "id" y tiene una lista
 de "Atributos" que forman parte de su conjunto.
 */

public class Item {
    //Attributes
    private Integer id;
    private Integer numValoraciones;
    //Relaciones
    private ArrayList<Atributo> atributos;
    // key -> userId, value -> rating
    private HashMap<Integer, Double> valoraciones;


    /** @brief Creadora básica con paso de parámetros.

    Crea un objeto de la clase Item.
    \pre <em>Cierto</em>
    \post Crea un Item con id = 'id' y una lista de valoraciones y atributos vacía.
     */
    public Item(int id) {
        this.id = id;
        this.numValoraciones = 0;
        this.atributos = new ArrayList<>();
        this.valoraciones = new HashMap<>();
    }


    /** @brief Creadora con paso de parámetros.

    Crea un objeto de la clase Item, asigna los atributos de la lista atributs y los inicializa.
    \pre <em>Cierto</em>
    \post Crea un Item con id = 'id' y una lista atributos = atributs, los inicializa a su tipo y valor.
     */
    public Item(int id, HashMap<String, String> atributs, HashMap<String, String> tipusAtributs)  {
        this.id = id;
        this.numValoraciones = 0;
        this.atributos = new ArrayList<>();
        this.valoraciones = new HashMap<>();
        /*for (Map.Entry<String, String> entry : tipusAtributs.entrySet()) {
            System.out.println("Clave: " + entry.getKey() + ", Valor: " + entry.getValue());
        }*/

        System.out.println("-------------ITEM: " + id + " -----------------------------");
        for (String att: atributs.keySet()) {
            String valor = atributs.get(att);
            String type = tipusAtributs.get(att); // Detecta ¿tipo? att = cabecera .csv
            //System.out.println(att + " tipus " + type + " valor " + valor);

            if(type == null) type = ""; // si type es null, entonces type = "" porque sera un tipo desconocido

            if (type.equals("bool")) {
                atributoBooleano ab = new atributoBooleano(tipoAtributo.booleano, att);
                ab.setValor(valor);
                //addAttribute(ab);
                atributos.add(ab);

            }
            else if (type.equals("categorico")){
                atributoCategorico ac = new atributoCategorico(tipoAtributo.categorico, att);
                ac.setValor(valor);
                //addAttribute(ac);
                atributos.add(ac);

            }
            else if (type.equals("num")){
                atributoNumerico an = new atributoNumerico(tipoAtributo.numerico, att);
                an.setValor(valor);
                //addAttribute(an);
                atributos.add(an);

            }
            else if (type.equals("text")){
                atributoFreetext af = new atributoFreetext(tipoAtributo.freetext, att);
                addAttribute(af);
                af.setValor(valor);
                //System.out.println(af.getName() + " " + valor);
            }
            else{
                Atributo a = new Atributo(tipoAtributo.vacio, att);
                atributos.add(a);
            }
        }
    }

    public Item(Item item) {
        this.id = item.id;
        this.numValoraciones = item.numValoraciones;
        this.atributos = new ArrayList<>(item.atributos);
        this.valoraciones = new HashMap<>(item.valoraciones);
    }
    // agregadas en nueva version
    // constructor por defecto
    public Item() {}
    // constructor
    public Item(HashMap<String, String> e) {}

    // CONSULTORAS

    /** @brief Consultora (Getter) del id de un Item.

    \pre <em> Cierto </em>
    \post Retorna el id del Item.
     */
    public Integer getId() { return this.id; }

    /*public Item(int id, ArrayList<Atributo> atributos, HashMap<Integer,Double> valoraciones) {
        this.id = id;
        this.atributos = atributos;
        this.valoraciones = valoraciones;
        this.numValoraciones = valoraciones.size();
    }*/

    /** @brief Consultora (Getter) de los atributos de un Item.

    \pre <em> Cierto </em>
    \post Retorna la lista de atributos del Item.
     */
    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }

    /** @brief Consultora (Getter) de las valoraciones de un Item.

    \pre <em> Cierto </em>
    \post Retorna la lista de valoraciones del Item.
     */
    public HashMap<Integer,Double> getValoraciones() {return this.valoraciones;}

    //SETTERS

    /** @brief Modificadora (Setter) de la lista de atributos de un Item.

    \pre <em> Cierto </em>
    \post Modifica la lista de atributos del item por la pasada por parametro.
     */
    public void setAtributos(ArrayList<Atributo> atributos) {this.atributos = atributos;}

    /** @brief Modificadora (Setter) de atributos un Item.

    \pre <em> Cierto </em>
    \post Añade un atributo a la lista de atributos de Item
     */
    public void addAttribute(Atributo a) {
        atributos.add(a);
    }

    /*public int getNumValoraciones() {
        return this.numValoraciones;
    }*/
    //public void setNumValoraciones(int n) {this.numValoraciones = n;}
    //public void setValoraciones(HashMap<Integer,Double> val) {this.valoraciones = val;}
    public void anadirValoracion(Integer userId, Double rating) throws Exception {
        if (this.valoraciones.containsKey(userId)) throw new Exception ("Aquest item ja va ser valorat per usuari " + userId);
        else {
            this.valoraciones.put(userId, rating);
            ++this.numValoraciones;
        }
    }

}
