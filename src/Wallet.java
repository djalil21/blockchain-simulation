import java.security.*;
import java.util.*;

public class Wallet  {
    // creating the object of KeyPairGenerator



    Noeud owner;
    PrivateKey privetKey = null;
    PublicKey publicKey = null;


    float value;
    public Wallet(Noeud owner) throws NoSuchAlgorithmException {

        this.owner=owner;
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024);
        KeyPair pair = keyGenerator.generateKeyPair();

        //this.value=value;
        this.privetKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
        this.value=12;

    }
    @Override
    public String toString() {
        return "Wallet [privetKey=" + privetKey + ", publicKey=" + publicKey + ", value=" + value + "]";
    }







}
