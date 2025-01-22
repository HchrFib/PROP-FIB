package Domain.Controllers.Drivers;
import Domain.Classes.Usuario;

import java.util.HashMap;

public class DriverUsuario {
    public static void main(String[] args){
        try {
            testingConstructora();
            testingAnadirVal();
            testingHaValorado();
        } catch(Exception e) {
            System.out.println("Algo ha ido mal");
        }
    }

    private static void testingConstructora() {
        Usuario u1 = new Usuario(1);

        HashMap<Integer,Double> val = new HashMap<>();
        Usuario u2 = new Usuario(2,val);

        System.out.println("Todo OK!");
    }

    private static void testingAnadirVal() throws Exception {
        Usuario u3 = new Usuario(3);

        u3.anadirValoracion(1,2.0);

        System.out.println("Todo OK!");
    }

    private static void testingHaValorado() throws Exception {
        Usuario u4 = new Usuario(4);
        u4.anadirValoracion(1,2.0);
        boolean b = u4.haValorado(1);

        if (b) System.out.println("Todo OK!");
        else System.out.println("Algo ha ido mal");
    }
}
