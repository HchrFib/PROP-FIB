package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

/** @file VistaEditarItem.java
 @brief Implementación de la vista que representa la funcionalidad de editar ítems
 */

/** @class VistaEditarItem

Esta vista permite al usuario interactuar con el sistema para editar ítems existentes.
 */
public class VistaEditarItem {

    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista = new JFrame("Editar Item");
    private JPanel mainPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JLabel title = new JLabel("Introduzca el ID del item que quiere editar:");
    private JButton submit = new JButton("Editar Item");
    private JButton cancel = new JButton("Cancelar");
    private JTextField itemIdField = new JTextField("itemId");

    /** @brief Constructora por defecto. */
    public VistaEditarItem(){}

    /** @brief Creadora con paso de parámetros.

    Crea la instancia de la clase VistaEditarItem.
    \pre <em>Cierto</em>
    \post Crea la instancia, la asocia con el controlador Presentacion
    y inicializa los componentes de la vista asi como sus "action listeners".
     */
    public VistaEditarItem(CtrlPresentacio ctrlPresentacio) {
        ctrl_presentacio = ctrlPresentacio;
        setComponents();
        setActionsListeners();
    }

    /** @brief Configura los componentes de la vista

    \pre <em>Cierto</em>
    \post Inicializa el frame principal y configura los buttons.
    Se define la estructura de la vista así como su estética.
     */
    private void setComponents() {
        // FRAME OPTIONS
        frameVista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameVista.setPreferredSize(new Dimension(450, 200));
        frameVista.setLocationRelativeTo(null);
        frameVista.add(mainPanel);
        frameVista.pack();

        // LABEL OPTIONS
        title.setBackground(Color.darkGray);
        title.setOpaque(true);
        title.setForeground(Color.lightGray);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Roboto",Font.PLAIN,16));

        // BUTTONS OPTIONS
        submit.setFocusable(false);
        submit.setFont(new Font("Roboto",Font.PLAIN,12));

        cancel.setFocusable(false);
        cancel.setFont(new Font("Roboto",Font.PLAIN,12));

        // TEXT FIELD OPTIONS
        itemIdField.setPreferredSize(new Dimension(100,25));
        itemIdField.setForeground(Color.gray);
        itemIdField.setFont(new Font("Roboto",Font.PLAIN,12));

        // PANEL OPTIONS

        // center panel
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setPreferredSize(new Dimension(500, 100));
        centerPanel.add(itemIdField);

        // north panel
        northPanel.setPreferredSize(new Dimension(500,40));
        northPanel.setLayout(new GridLayout());
        northPanel.setBackground(Color.darkGray);
        northPanel.add(title);

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
                String itemId = itemIdField.getText();

                if (!(isNumeric(itemId))) {
                    JOptionPane.showMessageDialog(null,"El dato introducido tiene que ser de tipo numerico", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    int itemValue = Integer.parseInt(itemId);

                    boolean existeItem = ctrl_presentacio.existeItem(itemValue);

                    if (existeItem) {
                        ctrl_presentacio.editarItemExistente(itemValue);
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"No existe ningun item con este ID", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ctrl_presentacio.editarCancelado();

            }
        });

        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ctrl_presentacio.editarCancelado();
            }
        });

        itemIdField.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (Objects.equals(itemIdField.getText(), "itemId")) {
                    itemIdField.setText("");
                    itemIdField.setForeground(Color.black);
                }
            }
        });
    }

    /** @brief Resetea los text fields

    \pre <em>Cierto</em>
    \post Resetea los text fields
     */
    private void resetear() {
        itemIdField.setText("itemId");
        itemIdField.setForeground(Color.gray);
    }

    /** @brief Muestra la vista

    \pre <em>Cierto</em>
    \post Muestra la vista por pantalla.
     */
    public void hacerVisible() {
        resetear();
        frameVista.setVisible(true);
    }

    /** @brief Esconde la vista

    \pre <em>Cierto</em>
    \post Hace que la vista desaparezca de la pantalla.
     */
    public void esconder() {
        frameVista.setVisible(false);
    }
}

