package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

/** @file VistaAnadirUsuario.java
 @brief Implementación de la vista que representa la funcionalidad de añadir usuarios
 */

/** @class VistaAnadirUsuario

Esta vista permite al usuario interactuar con el sistema para añadir usuarios nuevos.
 */
public class VistaAnadirUsuario {

    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista = new JFrame("Añadir Usuario");
    private JPanel mainPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JLabel title = new JLabel("Introduzca el ID del nuevo usuario que quiere añadir:");
    private JButton submit = new JButton("Añadir");
    private JButton cancel = new JButton("Cancelar");
    private JTextField userIdField = new JTextField("userId");

    /** @brief Constructora por defecto. */
    public VistaAnadirUsuario(){}

    /** @brief Creadora con paso de parámetros.

    Crea la instancia de la clase VistaAnadirUsuario.
    \pre <em>Cierto</em>
    \post Crea la instancia, la asocia con el controlador Presentacion
    y inicializa los componentes de la vista asi como sus "action listeners".
     */
    public VistaAnadirUsuario(CtrlPresentacio ctrlPresentacio) {
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
        userIdField.setPreferredSize(new Dimension(100,25));
        userIdField.setForeground(Color.gray);
        userIdField.setFont(new Font("Roboto",Font.PLAIN,12));

        // PANEL OPTIONS

        // center panel
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setPreferredSize(new Dimension(500, 100));
        centerPanel.add(userIdField);

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
                String userId = userIdField.getText();

                if (!(isNumeric(userId))) {
                    JOptionPane.showMessageDialog(null,"El dato introducido tiene que ser de tipo numerico", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    int userValue = Integer.parseInt(userId);

                    boolean existeUsuario = ctrl_presentacio.existeUsuario(userValue);

                    if (!existeUsuario) {
                        ctrl_presentacio.usuarioNuevo(userValue);
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Ya existe un usuario con este ID", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int answer = JOptionPane.showConfirmDialog(null,"¿Estas seguro de que quieres cancelar la operacion?", "Warning", JOptionPane.YES_NO_OPTION);

                if (answer == 0)  {  // yes
                    ctrl_presentacio.anadirUsuarioCancelado();
                }
            }
        });

        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int answer = JOptionPane.showConfirmDialog(null,"¿Estas seguro de que quieres cancelar la operacion?", "Warning", JOptionPane.YES_NO_OPTION);

                if (answer == 0)  {  // yes
                    ctrl_presentacio.anadirUsuarioCancelado();
                }
            }
        });

        userIdField.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (Objects.equals(userIdField.getText(), "userId")) {
                    userIdField.setText("");
                    userIdField.setForeground(Color.black);
                }
            }
        });
    }

    /** @brief Resetea los text fields

    \pre <em>Cierto</em>
    \post Resetea los text fields
     */
    private void resetear() {
        userIdField.setText("userId");
        userIdField.setForeground(Color.gray);
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
