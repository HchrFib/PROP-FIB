package Domain.Controllers.Drivers;
import Domain.Classes.Pair;

import java.util.Objects;


public class DriverPair {
    public static void main(String[] args){
        try {
            testingConstructora();
            testingGettersNSetters();
        } catch(Exception e) {
            System.out.println("Algo ha ido mal");
        }
    }

    private static void testingConstructora() {
        System.out.println("Probando creadoras");
        Pair<Integer,Integer> p1 = new Pair<Integer,Integer>();

        Pair<Integer,Integer> p2 = new Pair<Integer,Integer>(1,2);

        System.out.println("Todo OK!");
        System.out.println();
    }

    private static void testingGettersNSetters() throws Exception {
        Pair<Integer,String> p3 = new Pair<Integer,String>();
        p3.setFirst(1);
        p3.setSecond("uno");

        System.out.println("Probando getters y setters...");
        System.out.println("Hemos creado un par con primer elemento = '1' y segundo elemento = 'uno'");
        Integer first = p3.getFirst();
        String second = p3.getSecond();
        if (first == 1 && Objects.equals(second, "uno")) {
            System.out.println("Todo OK!");
        }
        else {
            throw new Exception();
        }

    }
}

