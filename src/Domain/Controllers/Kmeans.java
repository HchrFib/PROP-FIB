package Domain.Controllers;

import Domain.Classes.Calc;
import Domain.Classes.Cluster;
import Domain.Classes.Registro;
import Domain.Classes.Usuario;
import Domain.Classes.DataSet;

import java.util.*;
/** @file Kmeans.java
 @brief Implementación de la clase <em>Kmeans</em>.
 */
/** @class Kmeans
 @brief Representa la implementación del algoritmo <em>KMeans</em>.
 Clase implementada por: Christian Chávez Apcho.

 KMeans es un método de agrupamiento, que tiene como objetivo la partición de un conjunto de n observaciones en k grupos(Clusters) en el que cada observación pertenece al grupo cuyo valor medio es más cercano.
 Basicamente consta de los 4 pasos siguientes:

 paso 1: calculamos k centroides (en nuestro caso de modo aleatorio)
 paso 2: asignamos cada observación( que hemos denominado registro) a uno de los K clusters.
 paso 3: recalculamos los k centroides en función a las observaciones que pertenezcan al cluster K-sub-i
 paso 4: volvemos al paso 2 hasta que o bien lleguemos a un error o bien a en el caso de que las observaciones no cambien de cluster.
 */

public class Kmeans {
    /**@brief Estructura de datos que contiene los centroides asignados */
    private HashMap<Integer, Registro> centroides;
    /**@brief Estructura de datos que contiene los clusters par (id cluster, cluster)
     *  donde cluster contiene las observaciones que pertenecen a un cluster dado. */
    private HashMap<Integer, Cluster> clusters;
    //===== variables kmeans++ ======//
    /**@brief Atributo que contiene la suma de las distancias de cada observación a su centroíde.
     * Atributo usado para el  método kmeans++ (cálculo de los  k primeros centroídes iniciales). */
    private double sumaDistancias;
    //===============================//

    //===== variables elbow Method ======//
    /**@brief Estructura de datos(HashMap) que contiene pares (k, SSE), donde k es el número de clusters y
     * SSE sumatoria de la sumatoria de las distancias al cuadrado de las observaciones a su centroide. */
    private HashMap<Integer, Double> datosElbowMethod;

    /**@brief Atributo que contiene la suma de las distancias al cuadrado de cada observación a su centroíde.
     * Atributo usado para 'elbow Method'. */
    private double sumaDistanciasAlCuadrado;
    //===============================//

    /** @brief Constructor por defecto
     *
    \pre <em> Cierto</em>
    \post Crea una instancia de la clase <em>Kmeans</em> con sus valores por defecto.
     */
    public Kmeans() {
        this.sumaDistancias = 0.0;
        this.sumaDistanciasAlCuadrado= 0.0;
        this.clusters = new HashMap<>();
        this.centroides = new HashMap<>();
        this.datosElbowMethod = new HashMap<>();

    }
    //Getters
    /** @brief Getter - Consultora de los k centroides.
    \pre <em> Cierto </em>
    \post Retorna los centroides de los K clusters.
     */
    public HashMap<Integer, Registro> getCentroides() {
        return centroides;
    }

    /** @brief Getter - Consultora de los k clusters.
    \pre <em> Cierto </em>
    \post Retorna los K clusters.
     */
    public HashMap<Integer, Cluster> getClusters() {
        return clusters;
    }
    /** @brief Getter - Consultora SumaDistancias.
    \pre <em> Cierto </em>
    \post Retorna la suma de las distancias de las observaciones a su centroide.
     */
    public double getSumaDistancias() {
        return sumaDistancias;
    }

    /** @brief Getter - Consultora DatosElbowMethod.
    \pre <em> Cierto </em>
    \post Retorna un map donde la clave es el numero k y el valor es SSE (que es la sumatoria de la media de las distancias al cuadrado de las observaciones a sus centroides).
     */
    public HashMap<Integer, Double> getDatosElbowMethod() {
        return datosElbowMethod;
    }

    /** @brief Getter - Consultora SumasDistancias al cuadrado.
    \pre <em> Cierto </em>
    \post Retorna SSE sumatoria de la sumatoria de las distancias al cuadrado de las observaciones a su centroide.
     */
    public double getSumaDistanciasAlCuadrado() {
        return sumaDistanciasAlCuadrado;
    }

    // setters
    /** @brief Setter - Modificadora de un grupo de centroides.
    \pre <em> Cierto </em>
    \post Asigna un grupo de centroides
     */
    public void setCentroides(HashMap<Integer, Registro> centroides) {
        this.centroides = centroides;
    }

    /** @brief Setter - Modificadora de un grupo <em>clusters</em>.
    \pre <em> Cierto </em>
    \post Asigna un grupo de <em>clusters</em>
     */
    public void setClusters(HashMap<Integer, Cluster> clusters) {
        this.clusters = clusters;
    }

    /** @brief Getter - Modificadora SumaDistancias.
    \pre <em> Cierto </em>
    \post Asigna la suma de las distancias al atributo sumaDistancias.
     */
    public void setSumaDistancias(double sumaDistancias) {
        this.sumaDistancias = sumaDistancias;
    }

    /** @brief Getter - Modificadora ELbow Method.
    \pre <em> Cierto </em>
    \post Asigna el conjunto de pares (k, SSE) al Hashmap datosElbow</> .
     */
    public void setDatosElbowMethod(HashMap<Integer, Double> datosElbowMethod) {
        this.datosElbowMethod = datosElbowMethod;
    }

    /** @brief Getter - Modificadora suma distancia al cuadrado.
    \pre <em> Cierto </em>
    \post Asigna la suma de las distancias al cuadrado al atributo sumaDistanciasAlCuadrado</> .
     */
    public void setSumaDistanciasAlCuadrado(double sumaDistanciasAlCuadrado) {
        this.sumaDistanciasAlCuadrado = sumaDistanciasAlCuadrado;
    }

    //Methods
    /** @brief Método elbowMethod.
    \pre Recibe como parámetros un entero k > 0 y dos conjuntos de datos dataset y dataSetOriginal.
    La idea de este método simplemente es calcular el valor de SSE para cada k, donde k = {1,...,10},
    de tal forma que estos valores dibujen una especie de codo y elegir el k donde la pendiente de la función registre
    un cambio brusco, esto es así porque a medida que se incrementa la k el valor de SSE decrece.
    \post cierto
     */
    public int elbowMethod(int k,DataSet dataSet, DataSet dataSetOriginal) {
        int i = 1;
        System.out.println("Ejecutando algoritmo 'Elbow Method'");
        System.out.println("===================================");
        while(i < k) {
            if(dataSet.getRegistros().size() == 20) return 3;
            int vueltas = 10;
            if(centroides.size() == 0) {

                primerCentroide(dataSet);
            }

            else calcula_centroides_ponderados(dataSet);
            while(vueltas > 0) {
                asignaObservacionAcluster(k,dataSet);
                recalculaCentroides();
                --vueltas;
            }
            double sse = calculaTotalSSE();
            System.out.println("Ejecutando 'Elbow Method' para k = " + i +" el valor de SSE = " + sse );
            datosElbowMethod.put(i, sse);
            dataSet = dataSetOriginal;
            ++i;
        }
        System.out.println("Valor de 'K'  -  valor de 'SSE'");
        /*for(var de : datosElbowMethod.entrySet()) {
            System.out.println(de.getKey() + " "  + de.getValue());
        }*/
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double pendiente = 0.0;
        HashMap<Integer,Double> pendientes = new HashMap<>();
        for(int e = 2 ; e <= datosElbowMethod.size();++e) {
            pendiente = ((datosElbowMethod.get(e - 1)-datosElbowMethod.get(e))); //simplemente restamos porque la diferencia entre x2 y x1 es siempre =1
            if(pendiente < min) min = pendiente;
            if(pendiente > max) max = pendiente;
            pendientes.put( e - 1 , pendiente);
        }
        /*  Estructura de datos pendientesNormalizadas(la pendiente normalizada nos permite saber cuanto cae la pendiente en cada tramo k , k' en tanto %).
         *   la idea es que al tener archivos de diferentes tamaños los valores de SSE varian mucho en magnitud en orden de los miles
         *   y como utilizamos los valores de SSE para calcular las pendientes entre dos valores de k, una forma de saber de forma 'general'
         *   cuanto cae la pendiente en porcentaje  cuando el valor es 100, esto indica que para ese valor de k la caida de la pendiente es la máxima.
         *   Esto es que independientemente del tamaño del archivo ratings.db.csv.
         *   Por ejemplo: cuando entre k y k+1 la pendiente normalizada marque 100 sabremos con toda seguridad que en este tramo
         *   la pendiente presenta la máxima caída de toda la gráfica y que punto del codo es o bien k o o bien k + 1.

         * */
        HashMap<Integer, Double> pendientesNormalizadas = new HashMap<>();
        for(var p : pendientes.entrySet()) {
            pendientesNormalizadas.put(p.getKey(), ((p.getValue() - min)/(max - min)*100));
        }
        boolean flagCaidaMaxima = false;
        for(var pn : pendientesNormalizadas.entrySet()) {
            if(pn.getValue() == 100.00) {
                flagCaidaMaxima = true;
            }
            if(flagCaidaMaxima) {
                if(pn.getValue() >= 0 && pn.getValue() <= 25.00  || (datosElbowMethod.get(pn.getKey()) < datosElbowMethod.get(1)*0.20)) {
                    return pn.getKey();
                }
            }

        }
        return 3; // numero de clusters por defecto
    }
    /** @brief Método Kmeans.
    \pre Recibe como parámetros un entero k > 0, que representa el número de clusters que se crearán y una instancia de la clase Dataset que representa los datos de entrada para el algoritmo KMeans, que basicmenente son un conjunto no nulo de usuarios con sus repectivas valoraciones.
    \post cierto
     */
    public void kmeans(int k, DataSet dataSet) {

        //================ Algoritmo kmeans++ ================//
        primerCentroide(dataSet);
        kmeansPLusPLus(k, dataSet);                                   // paso 1: calculamos los centroides (KMeans++)
        //====================================================//
        int vueltas = 10;

        while(vueltas > 0) {
            asignaObservacionAcluster(k, dataSet);                    // paso 2: asignamos cada item(registro) a un cluster
            recalculaCentroides();                                    // paso 3: recalculamos los centroides
            --vueltas;
        }
    }
    /** @brief Método calculaTotalSSE.
    \pre cierto.
    \post Retorna sse que contiene la sumatoria de la sumatoria de las distancias al cuadrado de las observaciones a su centroide.
     */
    public Double calculaTotalSSE() {
        double sse = 0.0;
        for(var c : clusters.entrySet()) {
            //suma de las distancias de los registros de un cluster.
            //Por cada cluster.
            sse += c.getValue().getSumaDistanciasAlCuadrado();
        }
        return sse;
    }
    /** @brief Método primerCentroide.
    \pre recibe un parámetro que es el conjunto de datos en donde cada elemento(que hemos denominado registro) contiene la siguiente información: userID con su respectivas valoraciones de items .
    \post Agrega un registro que hará de primer centroide y que servirá como centroide inicial el cual nos permitirá
    calcular los k - 1 centroides restantes mediante el método kmeans++.
     */
    public void primerCentroide(DataSet dataSet) {
        int indice;

        indice = genera_indice_centroide(dataSet);

        centroides.put(indice,dataSet.getRegistros().get(indice));
    }
    /** @brief Método kmeans++.
    \pre recibe dos parámetros un entero que representa el número de centroides a calcular y el conjunto de datos en donde cada elemento(que hemos denominado registro) contiene la siguiente información: userID con su respectivas valoraciones de items .
    \post Agrega un registro que hará de primer centroide y que servirá como centroide incial el cual nos permitirá
    calcular los k - 1 centroides restantes mediante el método kmeans++, que son los k-1 puntos más lejanos respecto del primer centroíde generado aleatoriamente.
     */
    public void kmeansPLusPLus(int k, DataSet dataSet) {

        for(int i = 1 ;  i < k; i++) {
            calcula_centroides_ponderados(dataSet);
        }
    }
    /** @brief Almacena k centroides inciales en una estructura de datos.
    \pre recibe dos parámetros un entero k > 0 que representa el número de centroides que necesitamos almacenar y un segundo para que es una instancia de la clase DataSet que representa los datos sobre los cuales calcularemos los k centroides.
    \post Asigna un registro válido a una estructura de datos(un contenedor) que representan los k centroides inciales.
     */
    public void calcula_centroides_ponderados(DataSet dataSet) {

        asignaDistanciasAcentroides(dataSet);
        Random r = new Random();
        double umbral = sumaDistancias*r.nextDouble();
        for(var registro : dataSet.getRegistros().entrySet()) {
            if(registro.getValue().getSumaAcumulada() > umbral) {
                if(!centroides.containsKey(registro.getKey())) {
                    centroides.put(registro.getKey(), registro.getValue());
                    break;
                }

            }
        }
    }
    /** @brief Este método genera el indice del primer centroide, básicamente genera un entero entre  0 < i < el numero total de registros(usuarios valoraciones).
    \pre Recibe un parámetro que es una instancia de la clase DataSet que representa los datos.
    \post Devuelve un entero que representa el índice que apunta un registro que está almacenado en una estructura de datos.
     */
    public int genera_indice_centroide(DataSet dataSet) {

        Random r  = new Random();
        return r.nextInt(dataSet.getRegistros().size() - 1 + 1) + 1;
    }
    /** @brief Este método asigna un registro válido(en nuestro caso: un usuario y valoraciones) a uno de los K clústeres.
    \pre Recibe dos parámetros un entero k > 0 que representa el número de centroides que necesitamos almacenar y un segundo para que es una instancia de la clase DataSet que representa los datos sobre los cuales calcularemos los k centroides.
    \post cierto.
     */
    public void asignaDistanciasAcentroides(DataSet dataSet) {
        sumaDistancias = 0.0;
        double distancia;

        for(var registro : dataSet.getRegistros().entrySet()) {

            double minimaDistancia = Double.MAX_VALUE;
            for(var c : centroides.entrySet()) {
                distancia = Calc.DistanciaEuclidea(c.getValue(), registro.getValue());

                if(distancia < minimaDistancia) {

                    minimaDistancia = distancia;
                    registro.getValue().setDistancia(distancia);
                    registro.getValue().setDistanciaAlCuadrado(Math.pow(distancia,2));
                    registro.getValue().setNumCluster(c.getKey());

                }
            }

            if(!Double.isInfinite(minimaDistancia) && minimaDistancia != Double.MAX_VALUE) {
                sumaDistancias += minimaDistancia;
                sumaDistanciasAlCuadrado += Math.pow(minimaDistancia,2);
                dataSet.getRegistros().get(registro.getKey()).setSumaAcumulada(sumaDistancias);
                dataSet.getRegistros().get(registro.getKey()).setSumaAcumuladaAlCudrado(sumaDistanciasAlCuadrado);

            }

        }
    }
    /** @brief Este método asigna un registro válido(en nuestro caso: un usuario y valoraciones) a uno de los K clústeres.
    \pre Recibe dos parámetros un entero k > 0 que representa el número de centroides que necesitamos almacenar y un segundo para que es una instancia de la clase DataSet que representa los datos sobre los cuales calcularemos los k centroides.
    \post cierto.
     */
    public void asignaObservacionAcluster(int k, DataSet dataSet) {
        asignaDistanciasAcentroides(dataSet);
        int id;
        for (var c : centroides.entrySet()) {
            clusters.put(c.getKey(),new Cluster());
            clusters.get(c.getKey()).setId(c.getKey());
        }
        for(var registro : dataSet.getRegistros().entrySet()) {
            id = registro.getValue().getNumCluster();

            if(id > 0 && clusters.get(id)!= null) {
                clusters.get(id).addRegisterToCluster(registro.getKey(), registro.getValue());
                clusters.get(id).setId(id);
                double sumaDistancias = clusters.get(id).getSumaDistancias() + registro.getValue().getDistancia();
                double sumaDistanciasAlCuadrado = clusters.get(id).getSumaDistanciasAlCuadrado() + registro.getValue().getDistanciaAlCuadrado();
                clusters.get(id).setSumaDistancias(sumaDistancias);
                clusters.get(id).setSumaDistanciasAlCuadrado(sumaDistanciasAlCuadrado);
            }
        }
        for(var cls : clusters.entrySet()) {

            HashMap<Integer, Usuario> info = new HashMap<>();
            int idUser;

            for(var cl : cls.getValue().getRegistrosCluster().entrySet()) {
                idUser = cl.getValue().getUserRegistro();
                Usuario user = new Usuario(idUser);
                user.setValoraciones(cl.getValue().getRegistro());
                user.setNumValoraciones(cl.getValue().getRegistro().size());
                info.put(idUser, user);
            }
            clusters.get(cls.getKey()).setUsersCluster(info);
        }
    }
    /** @brief Este método recalcula los nuevos K centroides con el onjetivo de reasignar los
    registros(conjunto de usuarios y sus respectivas valoraciones) a uno de los K clusters,
    si se da el caso en el que la distancia es menor a un centroide que no es de su cluster actual, en tal caso
    cambiará de cluster.
    \pre true.
    \post Reasigna o no un registro a un cluster en función de la distancia a los k centroides.
     */
    public void recalculaCentroides() {

        HashMap<Integer, Registro> nuevos_centros = new HashMap<>();
        Registro registro;
        centroides.clear();
        for(var i : clusters.entrySet()) {
            registro = clusters.get(i.getKey()).calculaCentroide();
            nuevos_centros.put(i.getKey(), registro);
        }
        setCentroides(nuevos_centros);
    }
    /**
     * @brief Este método tiene como objetivo que dado un usuario devuelve un cluster con el conjunto de usuarios al que pertenece el id de usuario pasado como parámetro.

    \pre Recibe un parámetros que es un id de usuario entero positivo (válido).Es decir un id que existe.
    \post Devuelve el cluster al que pertenece el usuario identificado con idUser.
     */
    public Cluster getClusterUser(int userId) {
        for(var cls :clusters.entrySet()) {
            if(cls.getValue().getUsersCluster() != null) {
                if(cls.getValue().getUsersCluster().containsKey(userId)) return cls.getValue();
            }
        }
        return null;
    }
}


