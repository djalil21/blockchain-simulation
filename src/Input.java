import java.io.Serializable;
import java.security.PublicKey;

public class Input implements Serializable {
    int previousTx;
    int index;
    String scriptSig;

    public Input(int previousTx, int index, String scriptSig) {
        this.previousTx = previousTx;
        this.index = index;
        this.scriptSig = scriptSig;
    }

    @Override
    public String toString() {
        return " le proprietere: "+scriptSig;
    }
}
