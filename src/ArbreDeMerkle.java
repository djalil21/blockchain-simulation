import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;


public class ArbreDeMerkle {
    String racineMerkle;

    public static String sha256(String input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
    }

    public static String calculRacineMerkle(LinkedList<Transaction> l) {

        if (l.size() <= 1) {
            if (l.size()==0){
                return "";
            }
            return ArbreDeMerkle.sha256(l.get(0).toString());
        } else {
            LinkedList<Transaction> firstPart = new LinkedList<Transaction>();
            LinkedList<Transaction> secondPart = new LinkedList<Transaction>();

            for (int i = 0; i < l.size(); i++) {
                if (i < l.size() / 2) {
                    firstPart.add(l.get(i));
                } else {
                    secondPart.add((l.get(i)));
                }
            }


            return ArbreDeMerkle.sha256(ArbreDeMerkle.sha256(calculRacineMerkle(firstPart) + calculRacineMerkle(secondPart)));
        }
    }

    public ArbreDeMerkle() {
    }
}
