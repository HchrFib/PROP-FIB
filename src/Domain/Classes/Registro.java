package Domain.Classes;

import java.util.HashMap;
/** @file Registro.java
 @brief Implementación de la clase <em>Registro</em>.
 */
/** @class Registro
 @brief Representa la implementación del algoritmo <em>Registro</em>.
 Clase implementada por: Christian Chávez Apcho.

 EL objetivo de esta clase es la de representar un Registro que no es más que un conjunto de usuarios y sus valoraciones.
 Por ejemplo:
 idUser    -    item   -   valoracion
 789454           1           2.0
 789454           2           3.0
 345345           1           4.0
 DEFINICIÓN DE REGISTRO: es una tupla 'userId, item i = valoración, item i+1= valoración, .... , item n= valoración'
 */
public class Registro {
    /**@brief Estructura de datos que contiene registros. */
    private HashMap<Integer, Double> registro;
    //====== variables kmeans ======//
    /**@brief Atributo que contiene el número de cluster al cual pertenece un registro. */
    private int numCluster;
    /**@brief Atributo que contiene el userId de un registro. */
    private Integer userRegistro;
    //===============================//
    //===== variables kmeans++ =====//
    /**@brief Atributo que contiene la distancia de una observación(registro) a su cluster */
    private double distancia;
    /**@brief Atributo que contiene la distancia al cuadrado de una observación(registro) a su cluster */
    private double distanciaAlCuadrado;
    /**@brief Atributo que contiene la suma acumulada de las distancias desde el registro 1 hasta el registro de la posición i (utilizado en kmeans++) */
    private double sumaAcumulada;
    //===============================//
    // ======= elbow Method =========//
    /**@brief Atributo que contiene la suma acumulada de las distancias al cuadrado desde el registro 1 hasta el registro de la posición i (utilizado en 'Elbow Method') */
    private double sumaAcumuladaAlCuadrado;
    //===============================//
    //Constructores
    /** @brief Constructor por defecto
     *
    \pre <em> Cierto</em>
    \post crea una instancia de la clase Registro.
     */
    public Registro() {}

    /** @brief Constructor con parámetros
     *
    \pre <em> Cierto</em>
    \post crea una instancia de la clase Registro.
     */
    public Registro(HashMap<Integer, Double> registro) {
        this.registro = registro;
        this.numCluster = 0;
        this.distancia = 0.0;
        this.sumaAcumulada = 0.0;
        this.distanciaAlCuadrado= 0.0;
    }
    //getters
    /** @brief Getter - Consultora de un conjunto de registros.
    \pre <em> Cierto </em>
    \post Retorna un conjunto de registros.
     */
    public HashMap<Integer, Double> getRegistro() {
        return this.registro;
    }

    /** @brief Getter - Consultora que devuelve un número de cluster válido 1 < i <= k.
    \pre <em> Cierto </em>
    \post Retorna un cluster(un conjunto de registros).
     */
    public int getNumCluster() {
        return numCluster;
    }

    /** @brief Getter - Consultora que devuelve el registro de un usuario existente.
    \pre <em> Cierto </em>
    \post Retorna el registro de un usuario.
     */
    public Integer getUserRegistro() {
        return userRegistro;
    }
    /** @brief Getter - Consultora  Distancia
    \pre <em> Cierto </em>
    \post Retorna la distancia de una observación(registro) a su centroide.
     */
    public double getDistancia() {
        return distancia;
    }

    /** @brief Getter - Consultora suma acumulada
    \pre <em> Cierto </em>
    \post Retorna la suma acumulada de las distancias de las observaciones(registros) desde la posición 1 hasta el registro de la posición i(utilizado en KMeans++).
     */
    public double getSumaAcumulada() {
        return sumaAcumulada;
    }

    public double getDistanciaAlCuadrado() {
        return distanciaAlCuadrado;
    }
    /** @brief Getter - Consultora  suma acumulada al cuadrado
    \pre <em> Cierto </em>
    \post Retorna la suma acumulada de las distancias al cuadrado de las observaciones(registros) desde la posición 1 hasta el registro de la posición i(utilizado en 'Elbow Method').
     */
    public double getSumaAcumuladaAlCudrado() {
        return sumaAcumuladaAlCuadrado;
    }
    //Setters
    /** @brief Setter - Modificadora de un registro.
    \pre <em>Cierto</em>
    \post <em>Asigna un registro</em>.
     */
    public void setRegistro(HashMap<Integer, Double> registro) {
        this.registro = registro ;
    }

    /** @brief Setter - Asigna un indentificador a un cluster.
    \pre <em>Cierto</em>
    \post Asigna un identificador a un clsuter.
     */

    public void setNumCluster(int numCluster) {
        this.numCluster = numCluster;
    }
    /** @brief Setter - Asigna un idUser válido a un registro.
    \pre <em>Cierto</em>.
    \post Asigna un id válido a un registro
     */
    public void setUserRegistro(Integer userRegistro) {
        this.userRegistro = userRegistro;
    }

    /** @brief Setter - Modifica la distancia de una observación(registro) a su centroide.
    \pre <em>Cierto</em>.
    \post Asigna la distancia a un  registro
     */
    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    /** @brief Setter - Modifica suma Acumulada.
    \pre <em>Cierto</em>.
    \post Asigna la suma acumulada a un registro. Que no es más que la suma de las distancias de los registros anteriores a él(utilizado en el algoritmo kmeans++).
     */
    public void setSumaAcumulada(double sumaAcumulada) {
        this.sumaAcumulada = sumaAcumulada;
    }
    /** @brief Setter - Modifica la distancia al cuadrado de una observación(registro) a su centroide.
    \pre <em>Cierto</em>.
    \post Asigna la distancia al cuadrado a un registro
     */
    public void setDistanciaAlCuadrado(double distanciaAlCuadrado) {
        this.distanciaAlCuadrado = distanciaAlCuadrado;
    }

    /** @brief Setter - Modifica suma Acumulada al cuadrado.
    \pre <em>Cierto</em>.
    \post Asigna la suma acumulada al cuadrado a un registro. Que no es más que la suma de la distancias al cuadrado de todos los registros anteriores a él.(utilizado en 'Elbow Method').
     */
    public void setSumaAcumuladaAlCudrado(double sumaAcumuladaAlCudrado) {
        this.sumaAcumuladaAlCuadrado = sumaAcumuladaAlCudrado;
    }

    /** @brief Añade un par item-valoración a un registro válido</em> a un registro.
    \pre <em>Cierto</em>
    \post Asigna un nuevo campo a un registro.
     */
    public void anadirCampoRegistro(int itemId, double rating) {
        if(!this.registro.containsKey(itemId)) {
            this.registro.put(itemId,rating);
        }
    }


}


