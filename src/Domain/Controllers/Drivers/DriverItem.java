package Domain.Controllers.Drivers;
import Domain.Classes.Atributo;
import Domain.Classes.Item;
import Domain.Classes.tipoAtributo;

import java.util.HashMap;

public class DriverItem {
    public static void main(String[] args){
        try {
            testingConstructora();
            testingAnadirVal();
            testingAnadirAtt();
        } catch(Exception e) {
            System.out.println("Algo ha ido mal");
        }
    }

    private static void testingConstructora() {
        Item u1 = new Item(1);

        HashMap<String, String> val = new HashMap<>();
        Item u2 = new Item(2, val, val);

        System.out.println("Todo OK!");
    }

    private static void testingAnadirVal() throws Exception {
        Item u3 = new Item(3);

        u3.anadirValoracion(1,2.0);

        System.out.println("Todo OK!");
    }

    private static void testingAnadirAtt() throws Exception {
        Item u3 = new Item(3);
        Atributo a = new Atributo(tipoAtributo.vacio, "");
        u3.addAttribute(a);

        System.out.println("Todo OK!");
    }

}