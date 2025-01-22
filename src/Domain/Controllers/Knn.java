package Domain.Controllers;

import Domain.Classes.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** @class Knn
 @brief Representa la implementación del algoritmo k-Nearest Neighbors (k-NN)

 Este algoritmo funciona de forma que dado un determinado Usuario, <em>usuario</em>, y el conjunto total de Item, <em>items</em>,
 predice cuáles son los k items no valorados que tendrían una mayor valoración (en caso de valorarlos),
 basándose en la similitud de estos con los items ya valorados
 */
public class Knn {
    int k;
    Set<Item> items;
    Usuario usuario;

    //Creadoras

    /** @brief Creadora con paso
     *
     * \pre <em> Cierto</em>
     * \post Crea el objeto con sus atributos vacíos (útil para el JUnit)
     */
    public Knn(int k, ArrayList<Item> items) {
        this.k = k;
        this.items = new HashSet<>(items);
    }

    /** @brief Creadora por defecto
     *
     * \pre <em> Cierto </em>
     * \post Crea el objeto de la clase Knn asignando el valor de la <em>k</em>, el del Usuario <em>usuario</em>
     * y el del conjunto total de Item <em>items</em>
     */
    public Knn(int k, Usuario usuario, ArrayList<Item> items) {
        this.k = k;
        //this.n = n;
        this.usuario = usuario;
        this.items = new HashSet<>(items);
    }

    //Métodos privados

    /** @brief Obtiene los k Item más similares al Item <em>item</em>
     *
     * \pre Existe el Item <em>item</em>
     * \post Retorna una Map donde la key es la similitud con el Item <em>item</em> y el value es
     * Item en cuestión
     */
    public  LinkedList<Pair<Integer, Double>> kmejores(Item item) {
        PriorityQueue<Double> kbest = new PriorityQueue<>();
        Map<Double, ArrayList<Item>> aux = new HashMap<>();

        for (Item it : items) {
            double similitudCos;
            double textSim= 0;

            //int nFreeText = 0;
            for (int i = 0; i < item.getAtributos().size(); ++i) {
                Atributo a1 = item.getAtributos().get(i);
                if (a1.getTipo() == tipoAtributo.freetext) {
                    atributoFreetext a2 = (atributoFreetext) it.getAtributos().get(i);
                    atributoFreetext a3 = (atributoFreetext) item.getAtributos().get(i);

                    textSim += textSimilarity(a2, a3);
                }
            }

            similitudCos = cosSimilarity(item, it);
            similitudCos = (similitudCos+1)/2; //Normalizamos rango de la similitud de coseno [-1, 1] a [0, 1]




            double similitud = similitudCos*0.8+textSim*0.2;


            if (kbest.peek() == null || kbest.size() < k || similitud > kbest.peek()) {
                //System.out.println("Añadido: " + it.getId() + " con similitud " + similitudCos);
                kbest.add(similitud);

                ArrayList<Item> itemsAux = aux.get(similitud);
                if (itemsAux == null) itemsAux = new ArrayList<>();
                itemsAux.add(it);

                aux.put(similitud, itemsAux);

                if (kbest.size() > k) kbest.poll();
            }
        }

        LinkedList<Pair<Integer, Double>> resultat = new LinkedList<>();
        for (int i = 0; i < k; ++i) {
            // && i < aux.size()
            Double top = kbest.poll();
            if (top != null) {
                //System.out.println("Valoracio top: " + top);
                ArrayList<Item> itemsAux1 = aux.get(top);
                Item itemTop = new Item(itemsAux1.get(0));
                itemsAux1.remove(0);
                aux.put(top, itemsAux1);

                //if (itemTop.getId() == 161) System.out.println("Similitud aaaaaaaa: " + top);
                Pair<Integer, Double> par = new Pair<>(itemTop.getId(), top);
                resultat.add(par);
            }
        }

        return resultat;
    }


    /** @brief Obtiene los Item valorados por el Usuario <em>usuario</em> y su correspondiente rating
     *
     * \pre <em>cierto</em>
     * \post Devuelve un Map donde la key es el Item valorado y el value es el rating dado a dicho Item
     */
    private Map<Item, Double> getItemsValoraciones() {
        Map<Item, Double> resultat = new HashMap<>();
        try {

            Map<Integer, Double> valoraciones = usuario.getValoraciones();
            Set<Item> itemsNoValorats = new HashSet<>();

            for (Item item : items) {
                int itemId = item.getId();
                if (valoraciones.containsKey(itemId)) {
                    double valoracio = valoraciones.get(itemId);
                    resultat.put(item, valoracio);
                }
                else itemsNoValorats.add(item);
            }
            items = itemsNoValorats;


        } catch (NullPointerException e) {
                e.printStackTrace();
        }
        return resultat;
    }


    //Métodos públicos
    public double textSimilarity(atributoFreetext a1, atributoFreetext a2) {
        String text1 = a1.getText();
        String text2 = a2.getText();

        if (text1.isEmpty() || text2.isEmpty()) return 0;

        //System.out.println("Text1: " + text1);
        //System.out.println("cText2: " + text2);

        String[] stopwords = new String[] {"a", "about", "above", "after", "again", "against", "ain", "all", "am", "an",
                "and", "any", "are", "aren", "aren't", "as", "at", "be", "because", "been",
                "before", "being", "below", "between", "both", "but", "by", "can", "couldn",
                "couldn't", "d", "did", "didn", "didn't", "do", "does", "doesn", "doesn't",
                "doing", "don", "don't", "down", "during", "each", "few", "for", "from",
                "further", "had", "hadn", "hadn't", "has", "hasn", "hasn't", "have",
                "haven", "haven't", "having", "he", "her", "here", "hers", "herself", "him",
                "himself", "his", "how", "i", "if", "in", "into", "is", "isn", "isn't",
                "it", "it's", "its", "itself", "just", "ll", "m", "ma", "me", "mightn",
                "mightn't", "more", "most", "mustn", "mustn't", "my", "myself", "needn",
                "needn't", "no", "nor", "not", "now", "o", "of", "off", "on", "once",
                "only", "or", "other", "our", "ours", "ourselves", "out", "over", "own",
                "re", "s", "same", "shan", "shan't", "she", "she's", "should", "should've",
                "shouldn", "shouldn't", "so", "some", "such", "t", "than", "that",
                "that'll", "the", "their", "theirs", "them", "themselves", "then", "there",
                "these", "they", "this", "those", "through", "to", "too", "under", "until",
                "up", "ve", "very", "was", "wasn", "wasn't", "we", "were", "weren",
                "weren't", "what", "when", "where", "which", "while", "who", "whom", "why",
                "will", "with", "won", "won't", "wouldn", "wouldn't", "y", "you", "you'd",
                "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves",
                "could", "he'd", "he'll", "he's", "here's", "how's", "i'd", "i'll", "i'm",
                "i've", "let's", "ought", "she'd", "she'll", "that's", "there's", "they'd",
                "they'll", "they're", "they've", "we'd", "we'll", "we're", "we've", "what's",
                "when's", "where's", "who's", "why's", "would"};

        //if (text1.charAt(0) == '\"') text1 =text1.substring(1);
        // if (text2.charAt(0) == '\"') text2 =text2.substring(1);

        text1 = text1.replaceAll("\\p{Punct}", "");
        text2 = text2.replaceAll("\\p{Punct}", "");

        ArrayList<String> cleanText1 = Stream.of(text1.toLowerCase().split(" ")).collect(Collectors.toCollection(ArrayList<String>::new));
        cleanText1.removeAll(List.of(stopwords));

        ArrayList<String> cleanText2 = Stream.of(text2.toLowerCase().split(" ")).collect(Collectors.toCollection(ArrayList<String>::new));
        cleanText2.removeAll(List.of(stopwords));

        List<String> intersect = cleanText1.stream().filter(cleanText2::contains).collect(Collectors.toList());
        List<String> union = Stream.concat(cleanText1.stream(), cleanText2.stream()).distinct().collect(Collectors.toList());

        if (union.size() == 0) return 0;

        return intersect.size() / (double) union.size();
    }


    public double cosSimilarityOneHot(atributoCategorico a1, atributoCategorico a2) {
        BitSet ba1 = (BitSet) a1.getCategoria().clone();
        BitSet ba2 =  (BitSet) a2.getCategoria().clone();

        if (ba1.isEmpty()|| ba2.isEmpty()) return 0;

        int card1 = ba1.cardinality();
        int card2 = ba2.cardinality();

        BitSet ba3 =  (BitSet) ba1.clone();
        ba3.and(ba2);

        return ba3.cardinality()/(Math.sqrt(card1)* Math.sqrt(card2));
    }

    /** @brief Calcula la Similitud Coseno entre dos Item
     *
     * \pre Existen los Item <em>item1</em> y <em>item2</em>
     * \post Devuelve la similitud coseno entre los dos Item, un valor entre [-1, 1]
     */
    public double cosSimilarity(Item item1, Item item2) {
        double producto = 0.0;
        double normal1 = 0.0;
        double normal2 = 0.0;

        double resCategorico = 0.0;
        int nCategorico = 0;

        ArrayList<Atributo> atributos1 = new ArrayList<>(item1.getAtributos());
        ArrayList<Atributo> atributos2 = new ArrayList<>(item2.getAtributos());

        for (int i = 0; i < atributos1.size(); ++i) {
            if (atributos1.get(i).getTipo() != tipoAtributo.categorico && atributos1.get(i).getTipo() != tipoAtributo.freetext) {
                Double valor1;
                Double valor2;

                if (atributos1.get(i).getTipo() == tipoAtributo.numerico) {
                    atributoNumerico an1 = (atributoNumerico) atributos1.get(i);
                    valor1 = an1.getValor();

                    atributoNumerico an2 = (atributoNumerico) atributos2.get(i);
                    valor2 = an2.getValor();
                }
                else {
                    atributoBooleano an1 = (atributoBooleano) atributos1.get(i);
                    valor1 = an1.boolToInt();

                    atributoBooleano an2 = (atributoBooleano) atributos1.get(i);
                    valor2 = an2.boolToInt();
                }

                if (valor1 != null && valor2 != null) {
                    producto += valor1 * valor2; // AND
                    normal1 += Math.pow(valor1, 2);
                    normal2 += Math.pow(valor2, 2);
                }
            }
            else if (atributos1.get(i).getTipo() == tipoAtributo.categorico) {

                ++nCategorico;
            }
        }
        double resultado = producto / (Math.sqrt(normal1) * Math.sqrt(normal2));

        return (resultado * 0.3 + 0.7 * resCategorico / (double) nCategorico);
    }

    /** @brief Calcula los k Item con mayor probabilidad de ser mejor puntudados por el Usuario <em>usuario</em>
     *
     * \pre <em>cierto</em>
     * \post Devuelve un Map donde la key es rating predecido y el value es el Item con dicho rating predicho
     */
    public Map<Integer, Double> calcularResultado() {
        Map<Item, Double> itemsValoraciones = new HashMap<>(getItemsValoraciones());

        TreeMap<Double, ArrayList<Integer>> resultat = new TreeMap<>(Collections.reverseOrder());
        for (var it : itemsValoraciones.entrySet()) {
            Double rating = it.getValue();

            LinkedList<Pair<Integer, Double>> similitudItem = new LinkedList<>(kmejores(it.getKey()));

            for (Pair<Integer, Double> it2 : similitudItem) {
                Double similitud = it2.getSecond();
                int item = it2.getFirst();

                ArrayList<Integer> itemsAux = resultat.get(similitud*rating);
                if (itemsAux == null) itemsAux = new ArrayList<>();
                itemsAux.add(item);

                resultat.put(similitud*rating, itemsAux);
            }
        }

        //Eliminar items repetidos
        Map<Integer, Double> kmillors = new LinkedHashMap<>();
        for (var it : resultat.entrySet()) {
            if (kmillors.size() >= k) break;

            ArrayList<Integer> aux = it.getValue();
            if (!aux.isEmpty()) {
                for (Integer itemTopId : aux) {
                    if (kmillors.size() >= k) break;

                    kmillors.putIfAbsent(itemTopId, it.getKey());
                }
            }
        }

        return kmillors;
    }
}
