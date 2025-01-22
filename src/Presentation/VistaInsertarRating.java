package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

/** @file VistaInsertarRating.java
 @brief Implementación de la vista que permite insertar nuevos ratings.
 */

/** @class VistaInsertarRating

Esta vista permite al usuario introducir nuevos ratings para cualquier
 usuario, sobre cualquier ítem, y con cualquier nota/rating (entre 0 y 5).
 */
public class VistaInsertarRating {
    private CtrlPresentacio ctrl_presentacio;
    private boolean fijo = false;
    private JFrame frameVista = new JFrame("Insertar Nuevo Rating");
    private JPanel mainPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JLabel title = new JLabel("Introduzca los siguientes tres campos:");
    private JButton submit = new JButton("Añadir Rating");
    private JButton cancel = new JButton("Cancelar");
    private JTextField userIdField = new JTextField("userId");
    private JTextField itemIdField = new JTextField("itemId");
    private JTextField ratingField = new JTextField("rating");

    /** @brief Constructora por defecto.

     */
    public VistaInsertarRating(){}

    /** @brief Creadora con paso de parámetros.

    Crea la instancia de la clase VistaInsertarRating.
    \pre <em>Cierto</em>
    \post Crea la instancia, la asocia con el controlador Presentacion
    y inicializa los componentes de la vista asi como sus "action listeners".
     */
    public VistaInsertarRating(CtrlPresentacio ctrlPresentacio) {
        ctrl_presentacio = ctrlPresentacio;
        setComponents();
        setActionsListeners();
    }

    /** @brief Configura los componentes de la vista

    \pre <em>Cierto</em>
    \post Inicializa el frame principal y configura los text fields principales, así como los buttons.
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

        itemIdField.setPreferredSize(new Dimension(100,25));
        itemIdField.setForeground(Color.gray);
        itemIdField.setFont(new Font("Roboto",Font.PLAIN,12));

        ratingField.setPreferredSize(new Dimension(100,25));
        ratingField.setForeground(Color.gray);
        ratingField.setFont(new Font("Roboto",Font.PLAIN,12));

        // PANEL OPTIONS

            // center panel
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setPreferredSize(new Dimension(500, 100));
        centerPanel.add(userIdField);
        centerPanel.add(itemIdField);
        centerPanel.add(ratingField);

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
    \post Permite que el usuario pueda interactuar con el sistema a través de los distintos buttons
    así como de los text fields.
     */
    private void setActionsListeners() {
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String userId = userIdField.getText();
                String itemId = itemIdField.getText();
                String rating = ratingField.getText();

                if (!(isNumeric(userId) && isNumeric(itemId) && isNumeric(rating))) {
                    JOptionPane.showMessageDialog(null,"Los tres datos introducidos tienen que ser de tipo numérico",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    int userValue = Integer.parseInt(userId);
                    int itemValue = Integer.parseInt(itemId);
                    double ratingValue = Double.parseDouble(rating);

                    if (ratingValue < 0.0 || ratingValue > 5.0) {
                        JOptionPane.showMessageDialog(null,"La valoración debe ser un valor numérico entre 0.0 y 5.0",
                                "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        boolean existeUsuario = ctrl_presentacio.existeUsuario(userValue);
                        boolean existeItem = ctrl_presentacio.existeItem(itemValue);
                        boolean existeRating = ctrl_presentacio.existeRating(userValue, itemValue);

                        if (existeItem && existeUsuario && !existeRating) {
                            ctrl_presentacio.ratingNuevo(userValue,itemValue,ratingValue);
                        }
                        else if (!existeItem){
                            JOptionPane.showMessageDialog(null,"El item introducido no existe", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (!existeUsuario) {
                            JOptionPane.showMessageDialog(null,"El usuario introducido no existe", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            JOptionPane.showMessageDialog(null,"Ya existe una valoración de este usuario a este item", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

                System.out.println("User " + userId + ", item " + itemId + ", rating " + rating);
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int answer = JOptionPane.showConfirmDialog(null,"¿Estas seguro de que quieres cancelar la operacion?", "Warning", JOptionPane.YES_NO_OPTION);

                if (answer == 0)  {  // yes
                    if (!fijo) ctrl_presentacio.ratingCancelado();
                    else ctrl_presentacio.insertarRatingsUsuarioNuevoCancelado();
                }
            }
        });

        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int answer = JOptionPane.showConfirmDialog(null,"¿Estas seguro de que quieres cancelar la operacion?", "Warning", JOptionPane.YES_NO_OPTION);

                if (answer == 0)  {  // yes
                    if (!fijo) ctrl_presentacio.ratingCancelado();
                    else ctrl_presentacio.insertarRatingsUsuarioNuevoCancelado();
                }
            }
        });

        userIdField.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (Objects.equals(userIdField.getText(), "userId")) {
                    userIdField.setText("");
                    userIdField.setForeground(Color.black);
                }
                if (Objects.equals(itemIdField.getText(), "")) {
                    itemIdField.setText("itemId");
                    itemIdField.setForeground(Color.gray);
                }
                if (Objects.equals(ratingField.getText(), "")) {
                    ratingField.setText("rating");
                    ratingField.setForeground(Color.gray);
                }
            }
        });

        itemIdField.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (Objects.equals(userIdField.getText(), "")) {
                    userIdField.setText("userId");
                    userIdField.setForeground(Color.gray);
                }
                if (Objects.equals(itemIdField.getText(), "itemId")) {
                    itemIdField.setText("");
                    itemIdField.setForeground(Color.black);
                }
                if (Objects.equals(ratingField.getText(), "")) {
                    ratingField.setText("rating");
                    ratingField.setForeground(Color.gray);
                }
            }
        });

        ratingField.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (Objects.equals(userIdField.getText(), "")) {
                    userIdField.setText("userId");
                    userIdField.setForeground(Color.gray);
                }
                if (Objects.equals(itemIdField.getText(), "")) {
                    itemIdField.setText("itemId");
                    itemIdField.setForeground(Color.gray);
                }
                if (Objects.equals(ratingField.getText(), "rating")) {
                    ratingField.setText("");
                    ratingField.setForeground(Color.black);
                }
            }
        });
    }

    /** @brief Muestra la vista (2)

    \pre <em>Cierto</em>
    \post Muestra la vista con el textfield del userId bloqueado.
     */
    public void userFixed(int userId) {
        fijo = true;
        title.setText("Introduzca las valoraciones del usuario " + userId);
        userIdField.setText(Integer.toString(userId));
        userIdField.setEditable(false);
        frameVista.setVisible(true);
    }

    /** @brief Muestra la vista (1)

    \pre <em>Cierto</em>
    \post Muestra la vista por pantalla.
     */
    public void hacerVisible() {
        fijo = false;
        title.setText("Introduzca los siguientes tres campos:");
        userIdField.setText("userId");
        userIdField.setEditable(true);
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
