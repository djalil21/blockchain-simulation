import java.io.Serializable;
import java.security.PublicKey;

public class Output implements Serializable {
    float value;
    String scriptPubKey;

    public Output(float value, String scriptPubKey) {
        this.value = value;
        this.scriptPubKey = scriptPubKey;
    }

    @Override
    public String toString() {
        return " le destination: "+scriptPubKey+" value: "+value;
    }
}
