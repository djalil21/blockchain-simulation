import java.security.PublicKey;

public class Neighbor {
    public String num;
    public PublicKey publicKey;

    public Neighbor(String num) {
        this.num = num;
    }

    public Neighbor(String num, PublicKey publicKey) {
        this.num = num;
        this.publicKey = publicKey;
    }
}
