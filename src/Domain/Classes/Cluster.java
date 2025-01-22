package Domain.Classes;

import java.util.HashMap;
import java.util.LinkedList;
/** @file CLuster.java
 @brief Implementación de la clase Cluster.
 */
/** @class Cluster
 @brief Esta clase está implementada con el objetivo de agrupar usuarios a un cluster y gestionar esos grupos(clusters).
 Clase implementada por: Christian Chávez Apcho.
 DEFINICIÓN DE REGISTRO: es una tupla 'userId, item i = valoración, item i+1= valoración, ... , item n= valoración'
 */

public class Cluster {
    /**@brief atributo que respresenta el id de un cluster */
    private int id;
    /**@brief Estructura de datos que representa un cluster, el cual contiene registros(usersId's y sus respectivas valoraciones de items) */
    private HashMap<Integer,Registro> registrosCluster; // conjunto de registros.
    /**@brief Estructura de datos que representa un conjuntos de ususarios de un cluster dado */
    private HashMap<Integer, Usuario> usersCluster;
    /**@brief atributo que respresenta la sumade las distancias de las observaciones a su centroide de un cluster dado(utilizado para el algoritmo de Kmeans++) */
    private double sumaDistancias;
    /**@brief atributo que respresenta la sumade las distancias al cuadrado de las observaciones a su centroide de un cluster dado (utilizado para 'elbow method')*/
    private double sumaDistanciasAlCuadrado;
    /** @brief Constructor por defecto
     *
    \pre <em> Cierto</em>
    \post Crea el objeto de tipo <em>Cluster</em> con sus valores por defecto
     */
    public Cluster() {
        this. id = 0;
        this.registrosCluster = new HashMap<>();
        this.usersCluster = new HashMap<>();
        this.sumaDistancias = 0.0;
        this.sumaDistanciasAlCuadrado = 0.0;
    }
    //getters
    /** @brief Getter - Consultora del id de un cluster.
    \pre <em> Cierto </em>
    \post Retorna el id de un cluster.
     */
    public int getId() {
        return this.id;
    }
    /** @brief Getter - Consultora de las instancias(item,valoraciones) de un cluster.
    \pre <em> Cierto </em>
    \post Retorna los registros(lista de items valorados de un usuario que pertenece a un cluster.
     */

    public HashMap<Integer, Registro> getRegistrosCluster() {
        return this.registrosCluster ;
    }
    /** @brief Getter - Consultora de usuarios de un cluster.
    \pre <em> Cierto </em>
    \post Retorna los id's de usuarios de un cluster.
     */
    public HashMap<Integer, Usuario> getUsersCluster() {
        return usersCluster;
    }

    /** @brief Getter - Consultora sumaDistancias.
    \pre <em> Cierto </em>
    \post Retorna la suma de las distancias de las observaciones a su centroide en un cluster dado.
     */
    public double getSumaDistancias() {
        return sumaDistancias;
    }
    /** @brief Getter - Consultora sumaDistanciasAlCuadrado.
    \pre <em> Cierto </em>
    \post Retorna la suma de las distancias al cuadrado de las observaciones a su centroide en un cluster dado.
     */
    public double getSumaDistanciasAlCuadrado() {
        return sumaDistanciasAlCuadrado;
    }
    //setters
    /** @brief Setter - Modificadora de un id de un cluster.
    \pre <em> Cierto </em>
    \post Asigna un id a un cluster.
     */
    public void setId(int id) {
        this.id = id;
    }

    /** @brief Setter - Modificadora de un grupo registros de un cluster.
    \pre <em> Cierto </em>
    \post Modifica asigna un grupo de registros a un cluster
     */
    public void setRegistrosCluster(HashMap<Integer,Registro> registrosCluster) {
        this.registrosCluster = registrosCluster;
    }

    /** @brief Setter - Modificadora de un cluster.
    \pre <em> Cierto </em>
    \post Asigna un grupo de usuario a un cluster.
     */
    public void setUsersCluster(HashMap<Integer, Usuario> usersCluster) {
        this.usersCluster = usersCluster;
    }

    /** @brief Setter - Modificadora suma Distancias.
    \pre <em> Cierto </em>
    \post Asigna la suma de Distancias de las observaciones a su centroide de un cluster dado.
     */
    public void setSumaDistancias(double sumaDistancias) {
        this.sumaDistancias = sumaDistancias;
    }

    /** @brief Setter - Modificadora suma Distancias al cuadrado.
    \pre <em> Cierto </em>
    \post Asigna la suma de Distancias al cuadrado de las observaciones a su centroide de un cluster dado.
     */
    public void setSumaDistanciasAlCuadrado(double sumaDistanciasAlCuadrado) {
        this.sumaDistanciasAlCuadrado = sumaDistanciasAlCuadrado;
    }
    /** @brief Añade un registro(un conjuntos de pares <em>item-valoración de un usuario</em> a un cluster .
    \pre recibe un Id de usuario valido y el registro (un conjuntos de pares <em>item-valoración de un usuario</em>).
    \post cierto
     */
    public void addRegisterToCluster(int idRegistro, Registro registro) {
        this.registrosCluster.put(idRegistro,registro);
    }

    /** @brief Calcula un nuevo centroide a partir de un <em>cluster</em>.
    \pre cierto.
    \post devuelve un registro que contiene el nuevo centroide de un cluster.
     */
    public Registro calculaCentroide() {
        HashMap<Integer, Double> sumaCol = new HashMap<>();
        double sum = 0.0;
        for(var reg : registrosCluster.entrySet()) {
            for(var atri : reg.getValue().getRegistro().entrySet()) {
                sumaCol.putIfAbsent(atri.getKey(), 0.0);
                sum = sumaCol.get(atri.getKey()) + atri.getValue();
                sumaCol.put(atri.getKey(), sum);
            }
        }
        double media = 0.0;
        for(var valor : sumaCol.entrySet()) {
            media  = valor.getValue()/registrosCluster.size();
            valor.setValue(media);
        }
        Registro registro = new Registro();
        registro.setRegistro(sumaCol);
        return registro;
    }
    /** @brief Imprime por pantalla todos los clusters<em>cluster</em>.
    \pre cierto.
    \post Imprime por pantalla todos los <em>cluster</em>.
     */
    public  void printClusterId() {

        for(var x : registrosCluster.entrySet()) {
            System.out.println(x.getValue().getUserRegistro() + " " +  x.getValue().getRegistro().entrySet());
        }
    }
}





