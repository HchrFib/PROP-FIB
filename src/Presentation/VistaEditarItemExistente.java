package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Objects;

/** @file VistaEditarItemExistente.java
 @brief Implementación de la vista que nos permite concretar qué valores tendrá en cada atributo
 ael ítem (ya existente) que estamos editando.
 */

/** @class VistaEditarItemExistente

Esta vista permite al usuario definir los valores de los distintos atributos del ítem que estamos editando.
 */
public class VistaEditarItemExistente {
    private CtrlPresentacio ctrl_presentacio;
    private HashMap<String,String> nombre_tipo;
    private HashMap<String, JTextField> nombre_valor;
    private Integer itemId;

    // FRAME
    private JFrame frameVista = new JFrame("Editar Item Existente");

    // PANELS
    private JPanel mainPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel atributosPanel = new JPanel();

    // LABELS
    private JLabel title = new JLabel("Aquí puede editar los atributos del ítem:");
    private JLabel itemIdlabel = new JLabel("itemId: ");
    private JLabel itemIdSeleccionado = new JLabel("null");

    // BUTTONS
    private JButton submit = new JButton("Guardar Item");
    private JButton cancel = new JButton("Cancelar");

    // LIST AND SCROLL
    private JScrollPane jsp = new JScrollPane(atributosPanel);

    /** @brief Constructora por defecto. */
    public VistaEditarItemExistente() {
    }

    /** @brief Creadora con paso de parámetros.

    Crea la instancia de la clase VistaEditarItemExistente.
    \pre <em>Cierto</em>
    \post Crea la instancia, la asocia con el controlador Presentacion
    y inicializa los componentes de la vista asi como sus "action listeners".
     */
    public VistaEditarItemExistente(CtrlPresentacio ctrlPresentacio) {
        ctrl_presentacio = ctrlPresentacio;
        setComponents();
        setActionsListeners();
    }

    /** @brief Configura los componentes de la vista

    \pre <em>Cierto</em>
    \post Inicializa el frame principal y configura el scroll panel, así como los buttons.
    Se define la estructura de la vista así como su estética.
     */
    private void setComponents() {
        // FRAME OPTIONS
        frameVista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameVista.setPreferredSize(new Dimension(900, 500));
        frameVista.setResizable(false);
        frameVista.setLocationRelativeTo(null);
        frameVista.add(mainPanel);
        frameVista.pack();

        // LABEL OPTIONS
        title.setBackground(Color.darkGray);
        title.setOpaque(true);
        title.setForeground(Color.lightGray);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Roboto",Font.PLAIN,16));

        itemIdlabel.setFont(new Font("Roboto",Font.PLAIN,14));
        itemIdSeleccionado.setFont(new Font("Roboto",Font.PLAIN,14));


        // BUTTONS OPTIONS
        submit.setFocusable(false);
        submit.setFont(new Font("Roboto",Font.PLAIN,14));

        cancel.setFocusable(false);
        cancel.setFont(new Font("Roboto",Font.PLAIN,14));

        // SCROLL BAR OPTIONS
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setPreferredSize(new Dimension(500, 300));

        // PANEL OPTIONS

        // center panel
        centerPanel.setLayout(new GridBagLayout());
        //centerPanel.setPreferredSize(new Dimension(500, 100));
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(itemIdlabel, c);

        c.gridx = 1;
        c.gridy = 0;
        centerPanel.add(Box.createRigidArea(new Dimension(25, 0)), c);

        c.gridx = 2;
        c.gridy = 0;
        centerPanel.add(itemIdSeleccionado, c);

        c.gridx = 0;
        c.gridy = 1;
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)), c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        centerPanel.add(jsp, c);

        // north panel
        northPanel.setPreferredSize(new Dimension(800,75));
        northPanel.setBackground(Color.darkGray);
        northPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        northPanel.add(title, gbc);

        // buttons panel
        southPanel.setLayout(new FlowLayout());
        southPanel.setBackground(Color.darkGray);
        southPanel.add(submit);
        southPanel.add(cancel);

        // main panel
        mainPanel.setBackground(Color.lightGray);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel,BorderLayout.NORTH);
        //mainPanel.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.EAST);
        //mainPanel.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.WEST);
        mainPanel.add(centerPanel,BorderLayout.CENTER);
        mainPanel.add(southPanel,BorderLayout.SOUTH);
    }

    /** @brief Función privada para comprobar si un string almacena una información numérica

    \pre <em>Cierto</em>
    \post Devuelve cierto ssi "str" es numerico.
     */
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /** @brief Configura los "action listeners" de los distintos componentes

    \pre <em>Cierto</em>
    \post Permite que el usuario pueda interactuar con el sistema a través de los distintos componentes.
     */
    private void setActionsListeners() {
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                boolean todoCorrecto = true;
                for (var it : nombre_valor.keySet()) {
                    if (Objects.equals(nombre_tipo.get(it), "bool")) {
                        if (!nombre_valor.get(it).getText().toLowerCase().equals("true")
                                && !nombre_valor.get(it).getText().toLowerCase().equals("false")
                                && !nombre_valor.get(it).getText().equals("no definido")) {
                            JOptionPane.showMessageDialog(null,"El atributo " + it + " debe ser de tipo booleano",
                                    "Error", JOptionPane.ERROR_MESSAGE,null);
                            todoCorrecto = false;
                            break;
                        }
                    }
                    else if (Objects.equals(nombre_tipo.get(it), "num")) {
                        if (!isNumeric(nombre_valor.get(it).getText())
                                && !nombre_valor.get(it).getText().equals("no definido")) {
                            JOptionPane.showMessageDialog(null,"El atributo " + it + " debe ser de tipo numerico",
                                    "Error", JOptionPane.ERROR_MESSAGE,null);
                            todoCorrecto = false;
                            break;
                        }
                    }
                }

                if (todoCorrecto) {
                    HashMap<String, String> resultat = new HashMap<>();
                    for (var it : nombre_valor.keySet()) {
                        if (Objects.equals(nombre_valor.get(it).getText(), "no definido")) {
                            resultat.put(it, "");
                        }
                        else if (nombre_valor.get(it).getText().toLowerCase().equals("true")
                                || nombre_valor.get(it).getText().toLowerCase().equals("false")) {
                            resultat.put(it, nombre_valor.get(it).getText().toLowerCase());
                        }
                        else {
                            resultat.put(it, nombre_valor.get(it).getText());
                        }
                    }
                    ctrl_presentacio.guardarCambiosItem(itemId, resultat);
                    ctrl_presentacio.itemEditado();
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int answer = JOptionPane.showConfirmDialog(null,"¿Estas seguro de que quieres cancelar la operacion?\n" +
                        "No se guardará el ítem que estaba definiendo.", "Warning", JOptionPane.YES_NO_OPTION);

                if (answer == 0)  {  // yes
                    ctrl_presentacio.editarItemCancelado();
                }
            }
        });

        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int answer = JOptionPane.showConfirmDialog(null,"Si cierra la ventana no se guardarán los cambios sobre ítem: " + itemId +
                        "\n ¿Desea cerrarla igualmente?", "Warning", JOptionPane.YES_NO_OPTION);

                if (answer == 0)  {  // yes
                    ctrl_presentacio.editarItemCancelado();
                }
            }
        });
    }

    /** @brief Configura el Scroll Panel

    \pre <em>Cierto</em>
    \post Llena la tabla del scroll panel con las nombres, tipos y valores de los atributos que componen al ítem que estamos definiendo.
     */
    public void editarItem(int itemId, HashMap<String, String> cabecera_y_tipos, HashMap<String, String> cabecera_valor) {
        this.nombre_valor = new HashMap<>();
        this.nombre_tipo = cabecera_y_tipos;
        this.itemId = itemId;

        resetear();
        itemIdSeleccionado.setText(Integer.toString(itemId));

        GridLayout gl = new GridLayout(cabecera_y_tipos.size() + 1,3);
        atributosPanel.setLayout(gl);

        JTextField cabecera1 = new JTextField();
        cabecera1.setEditable(false);
        cabecera1.setText("Tipo Atributo");
        cabecera1.setFont(new Font("Roboto",Font.PLAIN,14));

        JTextField cabecera2 = new JTextField();
        cabecera2.setEditable(false);
        cabecera2.setText("Nombre Atributo");
        cabecera2.setFont(new Font("Roboto",Font.PLAIN,14));

        JTextField cabecera3 = new JTextField();
        cabecera3.setEditable(false);
        cabecera3.setText("Valor Atributo");
        cabecera3.setFont(new Font("Roboto",Font.PLAIN,14));

        atributosPanel.add(cabecera1);
        atributosPanel.add(cabecera2);
        atributosPanel.add(cabecera3);

        for (var it : nombre_tipo.keySet()) {
            JTextField nombre = new JTextField();
            JTextField tipo = new JTextField();
            JTextField valor = new JTextField();

            if (Objects.equals(it, "id")) {
                valor.setText(String.valueOf(itemId));
                valor.setEditable(false);
            }
            else {
                if (cabecera_valor.get(it).length() > 25) {
                    String upToNCharacters = cabecera_valor.get(it).substring(0,25);
                    valor.setText(upToNCharacters + "...");
                }
                else if (Objects.equals(cabecera_valor.get(it), "")){
                    valor.setText("no definido");
                    valor.setForeground(Color.gray);
                }
                else {
                    valor.setText(cabecera_valor.get(it));
                }
                valor.setEditable(true);
            }

            nombre.setText(it);
            tipo.setText(nombre_tipo.get(it));

            nombre.setEditable(false);
            tipo.setEditable(false);

            nombre.setFont(new Font("Roboto",Font.PLAIN,12));
            tipo.setFont(new Font("Roboto",Font.PLAIN,12));
            valor.setFont(new Font("Roboto",Font.PLAIN,12));

            atributosPanel.add(tipo);
            atributosPanel.add(nombre);
            atributosPanel.add(valor);

            nombre_valor.put(it, valor);

            // ------- ACTION LISTENERS --------
            valor.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (Objects.equals(valor.getText(), "no definido")) {
                        valor.setText("");
                        valor.setForeground(Color.black);
                    }

                    for (var it : nombre_valor.keySet()) {
                        if (nombre_valor.get(it) != valor) {
                            if (Objects.equals(nombre_valor.get(it).getText(), "")) {
                                nombre_valor.get(it).setText("no definido");
                                nombre_valor.get(it).setForeground(Color.gray);
                            }
                        }
                    }
                }
            });

        }
    }

    /** @brief Resetea el Scroll Panel

    \pre <em>Cierto</em>
    \post Resetea el Scroll Panel.
     */
    private void resetear() {
        atributosPanel.removeAll();
    }

    /** @brief Muestra la vista

    \pre <em>Cierto</em>
    \post Muestra la vista por pantalla.
     */
    public void hacerVisible() {
        frameVista.setVisible(true);
    }

    /** @brief Esconde la vista

    \pre <em>Cierto</em>
    \post Hace que la vista desaparezca de la pantalla.
     */
    public void esconder() {
        frameVista.setVisible(false);
    }

    /** @brief Habilita la vista

    \pre <em>Cierto</em>
    \post Permite la interacción del usuario con la vista.
     */
    public void enable() { frameVista.setEnabled(true); }

    /** @brief Deshabilita la vista

    \pre <em>Cierto</em>
    \post Impide la interacción del usuario con la vista.
     */
    public void disable() { frameVista.setEnabled(false); }
}


