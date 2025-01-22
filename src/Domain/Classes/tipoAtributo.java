package Domain.Classes;

/** @file Atributo.java
 @brief Implementación de la clase Atributo
 */

/** @enum Atributo
 @brief Representa los tipos de atributo que consideramos.

 Cada Atributo tiene un tipo, que puede ser booleano, categórico, numérico o de freetext.
 */
public enum tipoAtributo {
    booleano,
    categorico,
    numerico,
    freetext,
    vacio,
}

