import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;

public class Transaction implements Serializable {
    int version = 1;
    int id;
    static int numOfTransactions=0;
    String senderHash;
    LocalTime timestamp;
    ArrayList<Input> ListInputs = new ArrayList<Input>();
    ArrayList<Output> ListOutputs = new ArrayList<Output>();




    public Transaction() {

        id=numOfTransactions;
        numOfTransactions++;
        //this.senderHash=senderhash;
        timestamp=java.time.LocalTime.now();
    }

    @Override
    public String toString() {
        String toStr="";

        toStr=toStr+"\n"+"les inputs: ";
        for(int i=0;i<ListInputs.size();i++){
            toStr=toStr+"\n"+ListInputs.get(i).toString();
        }

        toStr=toStr+"\n"+"les outputs: ";
        for(int i=0;i<ListOutputs.size();i++){
            toStr=toStr+"\n"+ListOutputs.get(i).toString();
        }

        return " transaction id: "+id+toStr;
    }

    public int getVersion() {
        return version;
    }

    public int getId() {
        return id;
    }

    public String getSenderHash() {
        return senderHash;
    }

    public LocalTime getTimestamp() {
        return getTimestamp();
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSenderHash(String senderHash) {
        this.senderHash = senderHash;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }
}
