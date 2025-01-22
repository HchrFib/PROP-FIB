package Presentation;

import Domain.Classes.Pair;
import Domain.Controllers.CtrlDominio;

import java.util.*;

/** @file CtrlPresentation.java
 @brief Implementación del controlador de la capa de Presentación
 */

/** @class CtrlPresentacio
 @brief Representa la implementación del controlador de la capa de Presentación

 El controlador de la capa de Presentación permite la interacción entre el usuario y el sistema, y gestiona
 la interfaz gráfica mostrando y ocultando las distintas vistas.
 Además, se relaciona con la capa de Dominio (concretamente con CtrlDominio) pasándole las peticiones del usuario
 y recogiendo los resultados que hay que presentar.
 */
public class CtrlPresentacio {

    /** @brief Se trata del controlador de la capa de dominio con el que se establece la comunicación */
    private CtrlDominio ctrl_dominio;

    /** @brief Se trata de la vista principal del sistema (la que aparece primero) */
    private VistaPrincipal vistaPrincipal;

    /** @brief Se trata de la vista que permite insertar ratings */
    private VistaInsertarRating vistaInsertarRating;

    /** @brief Se trata de la vista que permite obtener recomendaciones para un usuario determinado */
    private VistaRecomendacion vistaRecomendacion;

    /** @brief Se trata de la vista que permite definir nuevos tipos de ítems */
    private VistaDefinirTipo vistaTipo;

    /** @brief Se trata de la vista que permite concretar qué atributos tendrán los nuevos tipos de ítems */
    private VistaAnadirAtributo vistaAtributo;

    /** @brief Se trata de la vista que muestra las recomendaciones obtenidas */
    private VistaMostrarRecomendacion vistaMostrarRec;

    /** @brief Se trata de la vista que muestra las recomendaciones obtenidas junto con su calidad */
    private VistaMostrarValoracion vistaMostrarVal;

    /** @brief Se trata de la vista que permite la gestión de usuarios (añadir y eliminar) */
    private VistaGestionUsuarios vistaGestionU;

    /** @brief Se trata de la vista que permite obtener recomendaciones junto con su calidad */
    private VistaCalidad vistaCalidad;

    /** @brief Se trata de la vista que permite añadir nuevos usuarios */
    private VistaAnadirUsuario vistaAnadirUsuario;

    /** @brief Se trata de la vista que permite eliminar usuarios */
    private VistaEliminarUsuario vistaEliminarUsuario;

    /** @brief Se trata de la vista que permite gestionar los ítems (añadir, eliminar y editar) */
    private VistaGestionItems vistaGestionI;

    /** @brief Se trata de la vista que permite eliminar ítems */
    private VistaEliminarItem vistaEliminarItem;

    /** @brief Se trata de la vista que permite añadir nuevos ítems */
    private VistaAnadirItem vistaAnadirItem;

    /** @brief Se trata de la vista que permite añadir los atributos de un ítem que estamos intentando añadir */
    private VistaDefinirItemNuevo vistaDefinirItemNuevo;

    /** @brief Se trata de la vista que permite editar los ítems */
    private VistaEditarItem vistaEditarItem;

    /** @brief Se trata de la vista que permite modificar los atributos de un ítem que estamos intentando editar */
    private VistaEditarItemExistente vistaEditarItemExistente;

    /** @brief Instancia de la clase (se usa para implementar el patrón singleton)*/
    private static CtrlPresentacio instance = null;

    private CtrlPresentacio() {}

    /** @brief Obtener la instancia de CtrlPresentacio.

    Permite la implementación del patrón singleton de esta clase.
    \pre <em> cierto </em>>
    \post Retorna la instancia de la clase en caso que ya existiese una. En caso contrario,
     crea una nueva instancia y la retorna.
     */
    public static CtrlPresentacio getInstance() {
        if (instance == null) {
            instance = new CtrlPresentacio();
        }
        return instance;
    }

    /** @brief Inicializa todas las vistas

    \pre <em> cierto </em>>
    \post Inicializa las vistas y establece la comunicación entre estas y el controlador de presentación.
     También muestra la pantalla inicial (vistaPrincipal).
     */
    public void inicializarPresentacion() {
        ctrl_dominio = CtrlDominio.getInstance();
        vistaPrincipal = new VistaPrincipal(this);
        vistaInsertarRating = new VistaInsertarRating(this);
        vistaRecomendacion = new VistaRecomendacion(this);
        vistaTipo = new VistaDefinirTipo(this);
        vistaAtributo = new VistaAnadirAtributo(this);
        vistaMostrarRec = new VistaMostrarRecomendacion(this);
        vistaGestionU = new VistaGestionUsuarios(this);
        vistaCalidad = new VistaCalidad(this);
        vistaMostrarVal = new VistaMostrarValoracion(this);
        vistaAnadirUsuario = new VistaAnadirUsuario(this);
        vistaEliminarUsuario = new VistaEliminarUsuario(this);
        vistaGestionI = new VistaGestionItems(this);
        vistaEliminarItem = new VistaEliminarItem(this);
        vistaAnadirItem = new VistaAnadirItem(this);
        vistaDefinirItemNuevo = new VistaDefinirItemNuevo(this);
        vistaEditarItem = new VistaEditarItem(this);
        vistaEditarItemExistente = new VistaEditarItemExistente(this);
        vistaPrincipal.hacerVisible();
    }

    // ----- CONSULTORAS DEL DOMINIO -----

    /** @brief Consultora de la existencia del archivo "tipos.csv"

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para comprobar
    si existe el archivo "tipos.csv" en el directorio indicado.
     */
    public boolean carpetaContieneTipos(String directorio) {
        // return false;
        if (ctrl_dominio.getInicializado()) {
            return ctrl_dominio.carpetaContieneTipos("archivos/"+ directorio);
        } else {
            ctrl_dominio.inicializar();
            return ctrl_dominio.carpetaContieneTipos("archivos/"+ directorio);
        }
    }

    /** @brief Consultora de la existencia de todos los archivos necesarios

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para comprobar
    si existe todos los archivos necesarios (en el directorio indicado) para
    ejecutar las distintas funcionalidades.
     */
    public boolean datasetCompleto(String directorio) {
        if (ctrl_dominio.getInicializado()) {
            return ctrl_dominio.datasetCompleto("archivos/"+ directorio);
        } else {
            ctrl_dominio.inicializar();
            return ctrl_dominio.datasetCompleto("archivos/"+ directorio);
        }
    }

    /** @brief Consultora de la existencia de un usuario concreto

    \pre <em> cierto </em>>
    \post Se comunica con el controlador de la capa de dominio para devolver Cierto ssi existe un usuario
    con el ID que nos pasan por parámetro.
     */
    public boolean existeUsuario(int userId) {
        return ctrl_dominio.existeUsuario(userId);
    }

    /** @brief Consultora de la existencia de un usuario concreto(2)

    \pre <em> cierto </em>>
    \post Se comunica con el controlador de la capa de dominio para devolver Cierto ssi existe un usuario
    con el ID que nos pasan por parámetro, en el dataset "Unknown".
     */
    public boolean existeUsuarioUnknown(int userId) {
        return ctrl_dominio.existeUsuarioUnknown(userId);
    }

    /** @brief Consultora de la existencia de un ítem concreto

    \pre <em> cierto </em>>
    \post Se comunica con el controlador de la capa de dominio para devolver Cierto ssi existe un ítem
    con el ID que nos pasan por parámetro.
     */
    public boolean existeItem(int itemId) {
        // return false;
        return ctrl_dominio.existeItem(itemId);
    }

    /** @brief Consultora de la existencia de una valoración concreta

    \pre <em> cierto </em>>
    \post Se comunica con el controlador de la capa de dominio para devolver Cierto ssi existe
    una valoración del usuario con ID = userId al ítem con ID = itemId.
     */
    public boolean existeRating(int userId, int itemId) {
        //return false;
        return ctrl_dominio.existeRating(userId,itemId);
    }

    /** @brief Consultora de la carga del dataset

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para comprobar
    si se ha cargado algún dataset con el que trabajar o no.
     */
    public boolean dataSetCargado() {
        // return true; // borrar despres
        return ctrl_dominio.getInicializado();
    }

    /** @brief Consultora de la modificación del dataset

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para comprobar
    si se ha modificado en algun momento el dataset con el que estamos trabajando.
     */
    public boolean datasetModificado() {
        return ctrl_dominio.dataSetModificado();
    }

    // ----- MODIFICADORES DEL DOMINIO -----

    /** @brief Crea un rating nuevo

    \pre No existe una valoración de el usuario 'userId' al ítem 'itemId'.
    \post Se comunica con el controlador de la capa de dominio para crear una nueva
     valoración de dicho usuario a dicho ítem con el rating = 'rating'. Seguidamente,
     vuelve a la pantalla principal.
     */
    public void ratingNuevo(int userId, int itemId, double rating) {
        //implementar llamada al ctrl_dominio
        ctrl_dominio.insertarRating(userId,itemId,rating);
        vistaPrincipal.enable();
        vistaInsertarRating.esconder();
    }

    /** @brief Crea un usuario nuevo

    \pre No existe un usuario con ese ID.
    \post Se comunica con el controlador de la capa de dominio para crear un
     nuevo usuario con ese ID. Posteriormente, vuelve a mostrar la vista
     de gestión de usuarios.
     */
    public void usuarioNuevo(int userId) {
        ctrl_dominio.anadirUsuario(userId);
        vistaAnadirUsuario.esconder();
        vistaPrincipal.hacerVisible();
        vistaPrincipal.disable();
        vistaGestionU.hacerVisible();
        vistaGestionU.enable();
    }

    /** @brief Guarda las modificaciones del dataset.

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para que éste guarde
     las modificaciones hechas en el dataset en una carpeta nueva (que será la copia).
     */
    public String guardarDataset() {
        return ctrl_dominio.guardarDataset();
    }

    /** @brief Carga un nuevo dataset para poder ejecutar las distintas funcionalidades

    \pre El parámetro es el nombre de un directorio real y existente en la carpeta "archivos", que
    contiene una serie de datasets necesarios para ejecutar las funcionalidades programadas.
    \post Se comunica con el controlador de la capa de dominio para cargar dichos datasets.
     */
    public void cargarDataset(String directorio) {
        // IMPLEMENTACIO CORRECTA
        if (ctrl_dominio.getInicializado()) {
            ctrl_dominio.cargarDataset("archivos/"+ directorio);
        }
        else {
            ctrl_dominio.inicializar();
            ctrl_dominio.cargarDataset("archivos/"+ directorio);
        }
    }

    /** @brief Solicta recomendaciones

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para solicitar y obtener
     'numRec' recomendaciones para el usuario 'userId' usando la estrategia indicada.
     */
    public void solicitarRecomendaciones(int userId, int numRec, String estrategia) {
        vistaRecomendacion.esconder();
        vistaPrincipal.hacerVisible();
        vistaPrincipal.disable();

        LinkedList<Pair<Integer,Double>> recomendaciones = ctrl_dominio.obtenerRecomendaciones(userId, numRec, estrategia);
        vistaMostrarRec.mostrarRecomendaciones(userId, recomendaciones);
        vistaMostrarRec.hacerVisible();
    }

    /** @brief Solicta recomendaciones y su calidad correspondiente

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para solicitar y obtener
    recomendaciones para el usuario 'userId' usando la estrategia indicada, además de su calidad (nDCG).
     */
    public void valorarRecomendaciones(int userId, String estrategia) {
        vistaCalidad.esconder();
        vistaPrincipal.hacerVisible();
        vistaPrincipal.disable();

        LinkedList<Pair<Integer,Double>> recomendaciones = ctrl_dominio.valorarRecomendaciones(userId, estrategia);

        double nDCG = ctrl_dominio.getNDCG();

        vistaMostrarVal.mostrarRecomendaciones(userId, recomendaciones, nDCG);
        vistaMostrarVal.hacerVisible();
    }

    /** @brief Guarda un nuevo tipo de item

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para guardar, en el directorio
      indicado, los tipos de atributos que tiene el nuevo tipo de items definido.
     */
    public void guardarNuevoTipo(String directorio, Vector<String> tipos) {
        vistaPrincipal.enable();
        vistaTipo.esconder();
        ctrl_dominio.guardarTipos(directorio, tipos);
    }

    /** @brief Elimina un usuario concreto

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para eliminar
    el usuario con ID = userId, además de todo lo que tiene que ver con él (incluso valoraciones).
     */
    public void usuarioEliminado(Integer userId) {
        ctrl_dominio.eliminarUsuario(userId);
        vistaEliminarUsuario.esconder();
        vistaPrincipal.hacerVisible();
        vistaPrincipal.disable();
        vistaGestionU.hacerVisible();
        vistaGestionU.enable();
    }

    /** @brief Guarda las recomendaciones obtenidas

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para guardar
      las recomendaciones obtenidas para el usuario con ID = userId.
     */
    public void guardarRecomendaciones(Integer userId, LinkedList<Pair<Integer,Double>> recomendacionesAGuardar) {
        ctrl_dominio.guardarRecomendaciones(userId, recomendacionesAGuardar);
    }

    /** @brief Elimina un ítem concreto.

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para eliminar
     el ítem de ID = itemId, además de todas sus valoraciones.
     */
    public void itemEliminado(Integer itemId) {
        ctrl_dominio.eliminarItem(itemId);
        vistaEliminarItem.esconder();
        vistaPrincipal.hacerVisible();
        vistaPrincipal.disable();
        vistaGestionI.hacerVisible();
        vistaGestionI.enable();
    }

    /** @brief Añade un ítem nuevo.

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para añadir
    un ítem nuevo con ID = itemId, y junto a todos sus atributos.
     */
    public void guardarItemNuevo(HashMap<String, String> nombre_valor) {
        ctrl_dominio.anadirItem(nombre_valor);
    }

    /** @brief Edita un item existente

    \pre <em>Cierto</em>
    \post Se comunica con el controlador de la capa de dominio para guardar
     los cambios aplicados al ítem editado.
     */
    public void guardarCambiosItem(Integer itemId, HashMap<String, String> nombre_valor) {
        ctrl_dominio.editarItem(itemId, nombre_valor);
    }

    // --------- CAMBIOS DE VISTAS -----------

    /** @brief Cambio de vista: Principal --> Insertar Rating

    \pre <em>Cierto</em>
    \post Deshabilita la pantalla principal y muestra la vista "insertarRating"
     */
    public void insertarRating() {
        vistaPrincipal.disable();
        vistaInsertarRating.hacerVisible();
    }

    /** @brief Cambio de vista: Insertar Rating --> Principal

    \pre <em>Cierto</em>
    \post Esconde la vista "insertarRating" y muestra la pantalla principal
     */
    public void ratingCancelado() {
        vistaPrincipal.enable();
        vistaInsertarRating.esconder();
    }

    /** @brief Cambio de vista: Insertar Rating --> Gestion Usuarios

    \pre <em>Cierto</em>
    \post Esconde la vista "insertarRating" y habilita la vista "vistaGestionU"
     */
    public void insertarRatingsUsuarioNuevoCancelado() {
        vistaPrincipal.disable();
        vistaGestionU.enable();
        vistaInsertarRating.esconder();
    }

    /** @brief Cambio de vista: Principal --> Obtener Recomendacion

    \pre <em>Cierto</em>
    \post Deshabilita la pantalla principal y muestra la vista "vistaRecomendacion"
     */
    public void obtenerRecomendacion() {
        vistaPrincipal.disable();
        vistaRecomendacion.hacerVisible();
    }

    /** @brief Cambio de vista: Principal --> Valorar Calidad Recomendacion

    \pre <em>Cierto</em>
    \post Deshabilita la pantalla principal y muestra la vista "vistaCalidad"
     */
    public void valorarCalidad() {
        vistaPrincipal.disable();
        vistaCalidad.hacerVisible();
    }

    /** @brief Cambio de vista: Obtener Recomendacion --> Principal

    \pre <em>Cierto</em>
    \post Muestra la pantalla principal y esconde la vista "vistaRecomendacion"
     */
    public void recomendacionCancelada() {
        vistaPrincipal.enable();
        vistaRecomendacion.esconder();
    }

    /** @brief Cambio de vista: Principal --> Definir Tipo Item Nuevo

    \pre <em>Cierto</em>
    \post Deshabilita la pantalla principal y muestra la vista "vistaTipo"
     */
    public void definirTipoNuevo() {
        vistaPrincipal.disable();
        vistaTipo.hacerVisible();
    }

    /** @brief Cambio de vista: Definir Tipo Item Nuevo --> Principal

    \pre <em>Cierto</em>
    \post Habilita la pantalla principal y esconde la vista "vistaTipo"
     */
    public void tipoNuevoCancelado() {
        vistaPrincipal.enable();
        vistaTipo.esconder();
    }

    /** @brief Cambio de vista: Valorar Calidad Recomendacion --> Principal

    \pre <em>Cierto</em>
    \post Habilita la pantalla principal y esconde la vista "vistaCalidad"
     */
    public void valoracionCancelada() {
        vistaPrincipal.enable();
        vistaCalidad.esconder();
    }

    /** @brief Cambio de vista: Vista Definir Tipo Item Nuevo --> Anadir Atributos Tipo Item

    \pre <em>Cierto</em>
    \post Deshabilita la "vistaTipo" y muestra la vista "vistaAtributo"
     */
    public void anadirAtributo() {
        vistaPrincipal.disable();
        vistaTipo.disable();
        vistaAtributo.hacerVisible();
    }

    /** @brief Cambio de vista:  Anadir Atributos Tipo Item --> Vista Definir Tipo Item Nuevo

    \pre <em>Cierto</em>
    \post Habilita la "vistaTipo" y esconde la vista "vistaAtributo"
     */
    public void atributoNuevoCancelado() {
        vistaTipo.enable();
        vistaAtributo.esconder();
    }

    /** @brief Cambio de vista:  Anadir Atributos Tipo Item --> Vista Definir Tipo Item Nuevo

    \pre <em>Cierto</em>
    \post Habilita la "vistaTipo", actualizando los atributos que contiene, y esconde la vista "vistaAtributo".
     */
    public void atributoAnadido(String tipo, String nombre) {
        vistaTipo.anadirAtributo(tipo,nombre);
        vistaTipo.enable();
        vistaAtributo.esconder();
    }

    /** @brief Cambio de vista: Mostrar Recomendaciones --> Principal

    \pre <em>Cierto</em>
    \post Habilita la pantalla principal y esconde la vista "vistaMostrarRec".
     */
    public void recomendacionesMostradas() {
        vistaPrincipal.enable();
        vistaMostrarRec.esconder();
    }

    /** @brief Cambio de vista: Mostrar Calidad Recomendaciones --> Principal

    \pre <em>Cierto</em>
    \post Habilita la pantalla principal y esconde la vista "vistaMostrarVal".
     */
    public void valoracionMostrada() {
        vistaPrincipal.enable();
        vistaMostrarVal.esconder();
    }

    /** @brief Cambio de vista: Principal --> Gestion Usuarios

    \pre <em>Cierto</em>
    \post Deshabilita la pantalla principal y muestra la vista "vistaGestionU".
     */
    public void gestionarUsuarios() {
        vistaPrincipal.disable();
        vistaGestionU.hacerVisible();
    }

    /** @brief Cambio de vista: Gestion Usuarios --> Principal

    \pre <em>Cierto</em>
    \post Habilita la pantalla principal y esconde la vista "vistaGestionU".
     */
    public void gestionarUsuariosCancelado() {
        vistaPrincipal.enable();
        vistaGestionU.esconder();
    }

    /** @brief Cambio de vista: Gestion Usuarios --> Anadir Usuario

    \pre <em>Cierto</em>
    \post Deshabilita la vista de gestión de usuario y muestra la vista "vistaAnadirUsuario".
     */
    public void anadirUsuario(){
        vistaGestionU.disable();
        vistaAnadirUsuario.hacerVisible();
    }

    /** @brief Cambio de vista: Anadir Usuario --> Gestion Usuarios

    \pre <em>Cierto</em>
    \post Habilita la vista de gestión de usuario y esconde la vista "vistaAnadirUsuario".
     */
    public void anadirUsuarioCancelado(){
        vistaGestionU.enable();
        vistaAnadirUsuario.esconder();
    }

    /** @brief Cambio de vista: Gestion Usuarios --> Eliminar Usuario

    \pre <em>Cierto</em>
    \post Deshabilita la vista de gestión de usuario y muestra la vista "vistaEliminarUsuario".
     */
    public void eliminarUsuario() {
        vistaGestionU.disable();
        vistaEliminarUsuario.hacerVisible();
    }

    /** @brief Cambio de vista: Eliminar Usuario --> Gestion Usuarios

    \pre <em>Cierto</em>
    \post Habilita la vista de gestión de usuario y esconde la vista "vistaEliminarUsuario".
     */
    public void eliminarUsuarioCancelado(){
        vistaGestionU.enable();
        vistaEliminarUsuario.esconder();
    }

    /** @brief Cambio de vista: Principal --> Gestion Items

    \pre <em>Cierto</em>
    \post Deshabilita la pantalla principal y muestra la vista "vistaGestionI".
     */
    public void gestionarItems() {
        vistaPrincipal.disable();
        vistaGestionI.hacerVisible();
    }

    /** @brief Cambio de vista: Gestion Items --> Añadir Item

    \pre <em>Cierto</em>
    \post Deshabilita la vista "vistaGestionI" y muestra la vista "vistaAnadirItem".
     */
    public void anadirItem(){
        vistaGestionI.disable();
        vistaAnadirItem.hacerVisible();
    }

    /** @brief Cambio de vista: Añadir Item --> Gestion Items

    \pre <em>Cierto</em>
    \post Habilita la vista de gestión de item y esconde la vista "vistaAnadirIt4em".
     */
    public void anadirItemCancelado(){
        vistaGestionI.enable();
        vistaAnadirItem.esconder();
    }

    /** @brief Cambio de vista: Gestion Items --> Eliminar Item

    \pre <em>Cierto</em>
    \post Deshabilita la vista "vistaGestionI" y muestra la vista "vistaEliminarItem".
     */
    public void eliminarItem(){
        vistaGestionU.disable();
        vistaEliminarItem.hacerVisible();
    }

    /** @brief Cambio de vista: Gestion Items --> Principal

    \pre <em>Cierto</em>
    \post Habilita la pantalla principal y esconde la vista "vistaGestionI".
     */
    public void gestionarItemsCancelado() {
        vistaPrincipal.enable();
        vistaGestionI.esconder();
    }

    /** @brief Cambio de vista: Eliminar Item --> Gestion Items

    \pre <em>Cierto</em>
    \post Habilita la vista "vistaGestionI" y esconde la vista "vistaEliminarItem".
     */
    public void eliminarItemCancelado(){
        vistaGestionI.enable();
        vistaEliminarItem.esconder();
    }

    /** @brief Cambio de vista: Definir Item Nuevo --> Pantalla Principal

    \pre <em>Cierto</em>
    \post Habilita la pantalla principal y esconde la vista "vistaDefinirItemNuevo".
     */
    public void itemNuevoDefinido() {
        vistaDefinirItemNuevo.esconder();
        vistaPrincipal.hacerVisible();
        vistaPrincipal.disable();
        vistaGestionI.hacerVisible();
        vistaGestionI.enable();
    }

    /** @brief Cambio de vista: Definir Item Nuevo --> Pantalla Principal

    \pre <em>Cierto</em>
    \post Habilita la pantalla principal y esconde la vista "vistaDefinirItemNuevo".
     */
    public void definirItemNuevoCancelado() {
        vistaDefinirItemNuevo.esconder();
        vistaPrincipal.hacerVisible();
        vistaPrincipal.disable();
        vistaGestionI.hacerVisible();
        vistaGestionI.enable();
    }

    /** @brief Cambio de vista: Gestion Items --> Editar Item

    \pre <em>Cierto</em>
    \post Deshabilita la vista "vistaGestionI" y muestra la vista "vistaEditarItem".
     */
    public void editarItem(){
        vistaGestionI.disable();
        vistaEditarItem.hacerVisible();
    }

    /** @brief Cambio de vista: Editar Item Existente --> Pantalla Principal

    \pre <em>Cierto</em>
    \post Habilita la vista "vistaGestionI" y esconde la vista "vistaEditarItemExistente".
     */
    public void itemEditado() {
        vistaEditarItemExistente.esconder();
        vistaPrincipal.hacerVisible();
        vistaPrincipal.disable();
        vistaGestionI.hacerVisible();
        vistaGestionI.enable();
    }

    /** @brief Cambio de vista: Editar Item Existente --> Pantalla Principal

    \pre <em>Cierto</em>
    \post Habilita la pantalla principal y esconde la vista "vistaEditarItemExistente".
     */
    public void editarItemCancelado() {
        vistaEditarItemExistente.esconder();
        vistaPrincipal.hacerVisible();
        vistaPrincipal.disable();
        vistaGestionI.hacerVisible();
        vistaGestionI.enable();
    }

    /** @brief Cambio de vista: Editar Item --> Pantalla Principal

    \pre <em>Cierto</em>
    \post Habilita la pantalla principal y esconde la vista "vistaEditar".
     */
    public void editarCancelado() {
        vistaEditarItem.esconder();
        vistaPrincipal.hacerVisible();
        vistaPrincipal.disable();
        vistaGestionI.hacerVisible();
        vistaGestionI.enable();
    }

    /** @brief Cambio de vista: Gestión Items --> Definir Item Nuevo

    \pre <em>Cierto</em>
    \post Deshabilita la vista "vistaGestionI" y muestra la vista "vistaDefinirItemNuevo".
     Además se comunica con el controlador dominio para obtener lo que esta segunda vista necesita (tipo de atributos del item).
     */
    public void itemNuevo(int itemId) {
        vistaAnadirItem.esconder();
        vistaPrincipal.hacerVisible();
        vistaPrincipal.disable();
        vistaGestionI.hacerVisible();
        vistaGestionI.disable();
        HashMap<String, String> nombre_tipo = ctrl_dominio.getTipos();
        vistaDefinirItemNuevo.definirItemNuevo(itemId, nombre_tipo);
        vistaDefinirItemNuevo.hacerVisible();
    }

    /** @brief Cambio de vista: Gestión Items --> Editar Item Existente

    \pre <em>Cierto</em>
    \post Deshabilita la vista "vistaGestionI" y muestra la vista "vistaEditarItemExistente".
    Además se comunica con el controlador dominio para obtener lo que esta segunda vista necesita (tipo de atributos del item + sus valores).
     */
    public void editarItemExistente(Integer itemId) {
        vistaEditarItem.esconder();
        vistaPrincipal.hacerVisible();
        vistaPrincipal.disable();
        vistaGestionI.hacerVisible();
        vistaGestionI.disable();
        HashMap<String, String> nombre_tipo = ctrl_dominio.getTipos();
        HashMap<String, String> nombre_valor = ctrl_dominio.getItem(itemId);

        for (var it : nombre_tipo.keySet()) {
            System.out.println("tipo: " + nombre_tipo.get(it) + ", nombre: " + it + ", valor: " +nombre_valor.get(it));
        }

        vistaEditarItemExistente.editarItem(itemId, nombre_tipo, nombre_valor);
        vistaEditarItemExistente.hacerVisible();
    }
}