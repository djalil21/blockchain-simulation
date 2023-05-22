import java.io.Serializable;

public class Packet implements Serializable {
    Object content;
    String type;

    String sender;

    public Packet(Object content, String type,String sender) {
        this.content = content;
        this.type = type;
        this.sender=sender;
    }

    @Override
    public String toString() {
        return content.toString() + " sent by "+ sender;
    }
}
