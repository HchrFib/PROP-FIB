package Domain.Controllers;

import Domain.Classes.Item;
import Domain.Classes.Pair;
import Domain.Classes.Usuario;

import java.util.*;

/** @file WeightedSlopeOne.java
 @brief Implementación de la funcionalidad Weighted Slope One
 */

/** @class WeightedSlopeOne
 @brief Representa la implementación del algoritmo Slope One, en la versión "Weighted".

 Este algoritmo funciona de forma que dado un determinado usuario, "usuarioActivo",
 y el conjunto de items que éste no ha valorado todavía, "itemsNoValorados", trata de
 predecir de qué forma valoraría estos items el usuario para poder, así, recomendarle los
 items que potencialmente obtendrían un mejor rating. Guarda el resultado en la estructura
 de datos "Nrecomendaciones".

 */
public class WeightedSlopeOne {

    /** @brief Conjunto total de los usuarios */
    private HashMap<Integer, Usuario> usuarios;

    /** @brief Conjunto total de los items (que tienen alguna valoracion) */
    private HashMap<Integer, Item> items;

    private double media;
    // util posteriormente para computar las predicciones
    // de las valoraciones

    /** @brief Usuario Activo, sobre el que se piden las recomendaciones */
    private Usuario usuarioActivo;
    // identificador del usuario activo

    /** @brief conjunto de items no valorados por el usuario activo */
    private LinkedList<Item> itemsNoValorados;

    private HashMap<Integer, HashMap<Integer, Double>> dev;
    // dev.get(i).get(j) retorna el valor dev i, j

    private HashMap<Integer, HashMap<Integer, Integer>> card;
    // card.get(i).get(j) retorna el valor card i, j

    private PriorityQueue<Pair<Integer,Double>> recomendaciones;

    /** @brief ED donde guardamos las recomendaciones finales */
    private LinkedList<Pair<Integer,Double>> Nrecomendaciones;

    /** @brief Creadora con paso de parámetros.

    Crea la instancia de la clase Slope One.
    \pre Existe en el map "usuarios" un Usuario con id = userId.
    \post Primero guarda en usuarios y items, la lista de usuarios que han hecho alguna
    valoracion y la lista de items que tienen alguna valoración, respectivamente.
    Posteriormente, se ejecuta el algoritmo Slope One y se guarda en "Nrecomendaciones"
    las recomendaciones resultantes de aplicar el algoritmo.
     */
    public WeightedSlopeOne(int userId, HashMap<Integer,Usuario> usuarios, HashMap<Integer, Item> items) {
        try {
            usuarioActivo = usuarios.get(userId);
            /*if(usuarioActivo == null) {
                System.out.println("El Usuario: " + userId + " no existe: ");
                System.exit(0);
            }*/
            this.items = items;
            this.usuarios = usuarios;

            Comparator<Pair<Integer,Double>> comparadorVal = Comparator.comparing(Pair<Integer,Double>::getSecond);
            // recordar añadir el reversedOrder()
            recomendaciones = new PriorityQueue<>(Collections.reverseOrder(comparadorVal));
            Nrecomendaciones = new LinkedList<>();

            // operaciones que tengo que computar para poder ejecutar mi algoritmo -
            dev = new HashMap<>();
            card = new HashMap<>();
            itemsNoValorados = new LinkedList<>();

            computarItemsNoValorados();
            pedirRecomendaciones();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** @brief Obtiene el conjunto de items no valorados por el usuario activo.

    Nos permite determinar el conjunto de items sobre los que haremos las predicciones.
    \pre <em> Cierto </em>>
    \post Se almacena en la estructura de datos "itemsNoValorados", el conjunto de items
    no valorados por el usuario activo.
     */
    private void computarItemsNoValorados() {
        for (var item : items.entrySet()) {
            if (!usuarioActivo.haValorado(item.getValue().getId())) itemsNoValorados.add(item.getValue());
        }
    }

    /** @brief Conjunto de usuarios que han valorado dos items.

    Dados los ids de dos items, nos permite obtener el conjunto de usuarios que tienen alguna
    valoración de ambos items.
    \pre <em>Cierto</em>
    \post Retorna una lista de pares de valoraciones "vik" y "vjk" donde "vik" y "vjk" son
    los ratings que da el usuario 'k' a los items 'i' y 'j', respectivamente.
     */
    private LinkedList<Pair<Pair<Integer,Double>,Pair<Integer, Double>>> interseccion(Integer itemJid, Integer itemIid) {
        LinkedList<Pair<Pair<Integer,Double>,Pair<Integer, Double>>> Sji = new LinkedList<>();
        for (var usuario : usuarios.entrySet()) {

            if (usuario.getValue().haValorado(itemJid) && usuario.getValue().haValorado(itemIid)) {
                Map<Integer, Double> aux = usuario.getValue().getValoraciones();

                Pair<Integer, Double> p1 = new Pair<>(itemJid, aux.get(itemJid));
                Pair<Integer, Double> p2 = new Pair<>(itemIid, aux.get(itemIid));
                Pair<Pair<Integer,Double>,Pair<Integer, Double>> p = new Pair<> (p1,p2);
                Sji.add(p);
            }
        }
        return Sji;
    }

    /** @brief Calcula cardIJ

    "cardIJ" se refiere al número de usuarios que tienen entre sus valoraciones,
    ratings de los items con ids: itemIid y itemJid.
    \pre <em>Cierto</em>
    \post Se calcula cardIJ y se retorna su valor.
     */
    private Integer calcularCard(Integer itemIid, Integer itemJid) {
        Integer cardinalitat = 0;

        for (var usuario : usuarios.entrySet()) {
            if (usuario.getValue().haValorado(itemIid) && usuario.getValue().haValorado(itemJid)) {
                ++cardinalitat;
            }
        }
        return cardinalitat;
    }

    /** @brief Calcula devJI

    calcula la desviación de los ratings del item J respecto a los del item I.
    \pre <em>Cierto</em>
    \post Se calcula devJI y se retorna su valor.
     */
    private Double calcularDev(Integer itemJid, Integer itemIid) {
        if (itemIid != itemJid) {
            LinkedList<Pair<Pair<Integer, Double>, Pair<Integer, Double>>> valJI = new LinkedList<>();
            valJI = interseccion(itemJid, itemIid);

            double desviacionJI = 0.0;

            for (var k : valJI) {
                desviacionJI += (k.getFirst().getSecond() - k.getSecond().getSecond());
            }
            if (valJI.size() > 0) desviacionJI /= valJI.size();
            else desviacionJI = -1.0;
            return desviacionJI;
        }
        else return 0.0;
    }


    /** @brief Función de predicción

    Predice el rating que podría dar el usuario activo al item "itemJ"
    \pre itemJ no es un objeto "null"
    \post Retorna el valor del rating predicho, o -1, si el conjunto "Rj"
    está vacío. (el conjunto "Rj". se explica en la documentación así como
    en el documento de "información adicional" de la página web de PROP, en el
    apartado de "Slope One")
     */
    private double predecir(Item itemJ) {
        Integer itemIdJ = itemJ.getId();

        double numerador = 0.0;
        double denominador = 0.0;

        for (var val : usuarioActivo.getValoraciones().entrySet()) {
            Integer itemIdI = val.getKey();
            double desviacionJI = calcularDev(itemIdJ, itemIdI);
            double valoracionItemI = val.getValue();
            Integer cJI = calcularCard(itemIdI, itemIdJ);

            if (desviacionJI != -1.0) {
                numerador += (desviacionJI + valoracionItemI) * cJI;
                denominador += cJI;
            }
        }

        if (denominador > 0.0) return numerador/denominador;
        else return -1;

    }

    /** @brief Computa todas las recomendaciones

    Para cada item que no ha valorado el usuario activo, predice que rating
    le daría usando el algoritmo Weighted Slope One.
    \pre <em> Cierto </em>
    \post Guarda en la estructura de datos Nrecomendaciones, los ratings predichos
    de cada item no valorado.
     */
    private void pedirRecomendaciones() {
        Iterator<Item> it = itemsNoValorados.iterator();
        while (it.hasNext()) {
            Item itemAPredecir = it.next();

            double prediccion = predecir(itemAPredecir);
            //System.out.println("Item a predecir = " + itemAPredecir.getId());
            //System.out.println("Prediccion = " + prediccion);
            if (prediccion != -1) {
                Pair<Integer, Double> p = new Pair<>(itemAPredecir.getId(), prediccion);
                recomendaciones.add(p);
            }
        }

        while (!recomendaciones.isEmpty()) {
            Nrecomendaciones.add(recomendaciones.poll());
        }
    }

    /** @brief Consultora de las recomendaciones

    \pre <em> Cierto </em>
    \post Devuelve una lista con las predicciones de los ratings de los items no valorados.
     */
    public LinkedList<Pair<Integer,Double>> getRecomendaciones() {
        return Nrecomendaciones;
    }

    /** @brief Consultora de las recomendaciones

    \pre <em> Cierto </em>
    \post Devuelve una lista con las predicciones de los ratings de los items no valorados.
     */
    public LinkedList<Pair<Integer,Double>> getNRecomendaciones(int n) {
        LinkedList<Pair<Integer,Double>> result = new LinkedList<>();

        int num = Math.min(n,Nrecomendaciones.size());

        for (int i = 0; i < num; ++i) {
            result.add(Nrecomendaciones.get(i));
        }
        return result;
    }
}


