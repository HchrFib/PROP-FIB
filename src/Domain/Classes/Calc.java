package Domain.Classes;

import java.util.*;
/** @file Calc.java
 @brief Implementación de la clase Calc.
 */
/** @class Calc
 @brief Clase para calcular la distancia entre una <em>observación y un centroide</em>(en nuestro caso un caso, valoraciones hechas por los usuarios).
 Clase implementada por: Christian Chávez Apcho.
 DEFINICIÓN DE REGISTRO: es una tupla 'userId, item i = valoración, item i+1= valoración, ... , item n= valoración'
 */

public class Calc {

    /** @brief Calcula la suma del cuadrado de la diferencia, recibe dos parámetros: observación y un centroide(user,item,etc) el método es utilizado para el algoritmo <em>KMeans</em>
    \pre recibe dos parámetros de tipo registro no null, Es decir, con los items valorados.
    \post Retorna un real que representa el cuadrado de la diferencia entre una <em>observación y su centroide</em>

     * */
    public static double sumCuadradoDeLaDiferencia(Registro itemP, Registro itemQ) {

        double infinito = Double.POSITIVE_INFINITY;
        if(itemP.equals(itemQ))  {
            return 0.0;
        }
        if(Collections.disjoint(itemP.getRegistro().keySet(), itemQ.getRegistro().keySet())) {
            return infinito;
        }
        double sumCuadraDif = 0.0;

        boolean flag = false;
        if(itemP.getRegistro().size() < itemQ.getRegistro().size()) {
            for(var  c: itemP.getRegistro().entrySet()) {
                if(itemQ.getRegistro().get(c.getKey()) != null) {
                    flag = true;
                    if(sumCuadraDif == infinito) sumCuadraDif =0.0;

                    sumCuadraDif  += Math.pow((c.getValue() - itemQ.getRegistro().get(c.getKey())),2);
                }
                if(!flag) {sumCuadraDif = infinito;}
            }
        } else {
            for(var  c: itemQ.getRegistro().entrySet()) {
                if(itemP.getRegistro().get(c.getKey()) != null) {
                    flag = true;
                    if(sumCuadraDif == infinito) sumCuadraDif =0.0;

                    sumCuadraDif  += Math.pow((c.getValue() - itemP.getRegistro().get(c.getKey())),2);
                }
                if(!flag) {sumCuadraDif = infinito;}
            }
        }
        return sumCuadraDif;

    }
    /** @brief Calcula la distancia euclídea entre una <em>observación y su centroide</em>.
    \pre Recibe dos parámetros de tipo registro que contiene las valoraciones de un usuario(observación).
    \post Retorna un real que representa la distancia entre una <em>observación y su centroide</em>
     */
    public static double DistanciaEuclidea(Registro itemCentroide, Registro item) {

        return Math.sqrt(sumCuadradoDeLaDiferencia(itemCentroide,item));
    }
}

