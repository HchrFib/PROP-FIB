package Domain.Controllers;

import Domain.Classes.Pair;

import java.util.*;
/** @file ValorarRecomendacion.java
 @brief Implementación de la clase ValorarRecomendacion
 */

/** @class ValorarRecomendacion
 @brief Representa la implementación de la funcionalidad que evalúa la calidad
 de las recomendaciones producidas por los algoritmos recomendadores.

 Esta clase funciona de forma que recibe las listas LR y LT por parámetro (en la creadora) y calcula
 los valores DCG, IDCG y NDCG. (Tanto el significado de estos tres valores como lo que representan las
 listas LR y LT está explicado en la documentación entregada).

 */
public class ValorarRecomendacion {
    // rating, itemId, posicion
    private LinkedList<Pair<Double, Pair<Integer, Integer>>> listaLR;
    // rating, itemId, posicion
    private LinkedList<Pair<Double, Pair<Integer, Integer>>> listaLT;

    private Double DCG; // Discounted Cumulative Gain
    private Double IDCG; // Ideal Discounted Cumulative Gain
    private Double nDCG; // Normalized DCG: valor que va de 0.0 a 1.0

    /** @brief Creadora con paso de parámetros.

    Crea la instancia de la clase ValorarRecomendacion
    \pre ambas "LinkedList" lr y lt están ordenadas de valoración mayor a menor
    \post Usando los dos parámetros, rellena las dos estructuras de datos "listaLR" y "listaLT"
    para que en caso de que dos ítems tengan la misma valoración se los considere como dos ítems en
    la misma posición (todo esto está más detallado en la documentación). Tras llenar correctamente
    las listas LR y LT, calcula los valores DCG, IDCG y NDCG.
     */
    public ValorarRecomendacion(LinkedList<Pair<Integer, Double>> lr, LinkedList<Pair<Integer, Double>> lt) {
        this.listaLT = new LinkedList<> ();

        Iterator<Pair<Integer,Double>> it = lt.iterator();
        while (it.hasNext()) {
            Pair<Integer,Double> aux = new Pair<>();
            aux = it.next();
            Pair<Integer,Integer> aux2 = new Pair<>(aux.getFirst(), -1);
            // afegirem un triplet: rating, itemid, -1
            Pair<Double, Pair<Integer,Integer>> p = new Pair<>(aux.getSecond(),aux2);
            listaLT.add(p);
        }

        Iterator<Pair<Double,Pair<Integer,Integer>>> it2 = listaLT.iterator();
        Double previousRating = -1.0;
        Integer previousPosition = 0;
        while (it2.hasNext()) {
            Pair<Double,Pair<Integer,Integer>> pair = it2.next();

            if (previousRating == -1.0) {
                previousRating = pair.getFirst();

                Integer itemId = pair.getSecond().getFirst();
                Pair<Integer,Integer> aux = new Pair<>(itemId, 1);

                previousPosition = 1;
                pair.setSecond(aux);
            }
            else {
                Double currentRating = pair.getFirst();
                if (Objects.equals(currentRating, previousRating)) {
                    Integer itemId = pair.getSecond().getFirst();
                    Pair<Integer,Integer> aux = new Pair<>(itemId, previousPosition);

                    pair.setSecond(aux);
                }
                else {
                    Integer itemId = pair.getSecond().getFirst();
                    Pair<Integer,Integer> aux = new Pair<>(itemId, previousPosition+1);

                    pair.setSecond(aux);
                    ++previousPosition;
                    previousRating = currentRating;
                }
            }
        }

        // Ara computem la llista LR
        this.listaLR = new LinkedList<> ();

        it = lr.iterator();
        while (it.hasNext()) {
            Pair<Integer,Double> aux = new Pair<>();
            aux = it.next();

            if (LTContains(aux.getFirst())) { // de totes les recomenacions nomes volem aquelles que siguin a LT
                Pair<Integer,Integer> aux2 = new Pair<>(aux.getFirst(), -1);
                // afegirem un triplet: rating, itemid, -1
                Pair<Double, Pair<Integer,Integer>> p = new Pair<>(aux.getSecond(),aux2);
                listaLR.add(p);
            }
        }

        it2 = listaLR.iterator();
        previousRating = -1.0;
        previousPosition = 0;
        while (it2.hasNext()) {
            Pair<Double,Pair<Integer,Integer>> pair = it2.next();
            if (previousRating == -1.0) {
                previousRating = pair.getFirst();

                Integer itemId = pair.getSecond().getFirst();
                Pair<Integer,Integer> aux = new Pair<>(itemId, 1);

                previousPosition = 1;
                pair.setSecond(aux);
            }
            else {
                Double currentRating = pair.getFirst();
                if (Objects.equals(currentRating, previousRating)) {
                    Integer itemId = pair.getSecond().getFirst();
                    Pair<Integer,Integer> aux = new Pair<>(itemId, previousPosition);
                    pair.setSecond(aux);
                }
                else {
                    Integer itemId = pair.getSecond().getFirst();
                    Pair<Integer,Integer> aux = new Pair<>(itemId, previousPosition+1);

                    pair.setSecond(aux);
                    ++previousPosition;
                    previousRating = currentRating;
                }
            }
        }

        // -----------------------------------------------
        System.out.println();
        System.out.println("Lista LR");
        it2 = listaLR.iterator();
        while (it2.hasNext()) {
            Pair<Double, Pair<Integer, Integer>> p = it2.next();
            System.out.println("Item " + p.getSecond().getFirst() + ", rating: " + p.getFirst() + " posicion: " + p.getSecond().getSecond());
        }

        System.out.println();
        System.out.println("Lista LT");
        it2 = listaLT.iterator();
        while (it2.hasNext()) {
            Pair<Double, Pair<Integer, Integer>> p = it2.next();
            System.out.println("Item " + p.getSecond().getFirst() + ", rating(reli): " + p.getFirst() + ", posicion: " + p.getSecond().getSecond());
        }
        System.out.println();

        // un cop tenim ordenades les llistes LR i LT, procedim a calcular DCG

        calculaDCG(); // calcula tanto el real como el ideal

        if (IDCG != 0.0) nDCG = DCG / IDCG;
        else nDCG = -1.0;
    }

    /** @brief Comprueba si la lista LT contiene la valoración de un item determinado

    Dada la id de un ítem (itemId), devuelve cierto ssi la lista LT contiene una valoración
    de dicho ítem.
    \pre <em>Cierto</em>
    \post devuelve cierto ssi la lista LT contiene una valoración de item con id = itemId.
     */
    private boolean LTContains(Integer itemId) {
        Iterator<Pair<Double, Pair<Integer,Integer>>> it = listaLT.iterator();
        while (it.hasNext()) {
            Pair<Double, Pair<Integer,Integer>> p = it.next();
            if (Objects.equals(p.getSecond().getFirst(), itemId)) {
                return true;
            }
        }
        return false;
    }


    /** @brief Calcular rel(i)

    Dada la id de un ítem (itemId), devuelve la valoración que recibe dicho ítem en la lista LT.
    \pre Existe una valoración para dicho ítem en la lista LT
    \post Devuelve rel(i), es decir, la valoración del dicho item en la lista LT.
     */
    private Double valoracioEnLT(Integer itemId) {
        // LT está ordenado por rating

        Iterator<Pair<Double, Pair<Integer,Integer>>> it = listaLT.iterator();
        while (it.hasNext()) {
            Pair<Double, Pair<Integer,Integer>> p = it.next();
            if (Objects.equals(p.getSecond().getFirst(), itemId)) {
                return p.getFirst();
            }
        }
        return -1.0; // en caso de que no exista el item en la lista LT, retornamos -1.0
    }

    /** @brief Obtiene el valor de DCG y IDCG

    Recorre los ítems de la lista LR así como los de la lista LT para calcular los valores de DCG y
    IDCG usando las fórmulas descritas en la documentación entregada.
    \pre <em> Cierto </em>
    \post Guarda en los atributos DCG y IDCG, sus valores correspondientes además de mostrarlos por pantalla.
     */
    private void calculaDCG() {
        DCG = 0.0;
        IDCG = 0.0;

        Iterator<Pair<Double,Pair<Integer,Integer>>> it = listaLR.iterator();
        while (it.hasNext()) {
            Pair<Double,Pair<Integer,Integer>> p = it.next();
            //System.out.println("Analizando item " + p.getSecond().getFirst() + " en posicion " + p.getSecond().getSecond() + " y rating " + p.getFirst());
            Integer itemId = p.getSecond().getFirst();

            Double reli = valoracioEnLT(itemId);

            //Double reli = result.getSecond();
            // idealPosition = result.getFirst();
            //System.out.println("Reli " + reli);

            Integer i = p.getSecond().getSecond();

            if (reli != -1) DCG += ((Math.pow(2.0, reli) - 1.0) / (Math.log(i + 1)/Math.log(2.0)));
            else System.out.println("Algo ha ido mal");
            // numerador = 2^reli - 1
            // denominador = log 2 (i + 1) <- (hago cambio de base)

            /*
            if (idealPosition != -1) {
                IDCG += ((Math.pow(2.0, reli) - 1.0) / (Math.log(idealPosition + 1)/Math.log(2.0)));
                Pair <Integer,Double> aux = new Pair<>(p.getSecond().getFirst(), p.getFirst());
                recomendacionesAcertadas.add(aux);
            }*/
        }

        // Calculamos IDCG
        it = listaLT.iterator();
        IDCG = 0.0;
        while (it.hasNext()) {
            Pair<Double,Pair<Integer,Integer>> p = it.next();
            Double reli = p.getFirst();
            Integer idealPosition = p.getSecond().getSecond();
            IDCG += ((Math.pow(2.0, reli) - 1.0) / (Math.log(idealPosition + 1)/Math.log(2.0)));
            //System.out.println("item LT" + p.getSecond().getFirst() + " en posicion " + p.getSecond().getSecond() + " y rating " + p.getFirst());
        }

        System.out.println("DCG = " + DCG);
        System.out.println("iDCG = " + IDCG);
    }

    // CONSULTORAS

    /** @brief Consultora de NDCG

    \pre <em> Cierto </em>
    \post Devuelve el valor de NDCG.
     */
    public Double getNDCG() {return nDCG;}

    public LinkedList<Pair<Integer,Double>> getListaLR() {
        // rating, itemId, posicion
        //LinkedList<Pair<Double, Pair<Integer, Integer>>> listaLR;

        LinkedList<Pair<Integer,Double>> resultat = new LinkedList<>();
        for (var it : listaLR) {
            Pair<Integer,Double> p = new Pair<>(it.getSecond().getFirst(), it.getFirst());
            resultat.add(p);
        }

        return resultat;
    }
}

