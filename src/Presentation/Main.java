package Presentation;
import javax.swing.*;

public class Main {
    public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater (
                new Runnable() {
                    public void run() {

                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        CtrlPresentacio ctrlPresentacio = CtrlPresentacio.getInstance();
                        ctrlPresentacio.inicializarPresentacion();
                    }
                }
        );
    }
}
