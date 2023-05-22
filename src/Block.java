
import javafx.util.converter.LocalTimeStringConverter;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;

public class Block implements Serializable {

    int id;
    static int numberOfBlock=0;


    public int version ;
    LocalTime timestamp=LocalTime.NOON;

    String HashPrevBlock="";

    String HashHeaderBlock="";
    int nonce;
    int target;
    LinkedList<Transaction> List_Trasaction=new LinkedList<Transaction>();



    String merkelRoot="";







    public Block (){
        this.id=numberOfBlock;
        numberOfBlock++;





    }


    public String hashBlock(){
        return ArbreDeMerkle.sha256(version+timestamp.toString()+HashPrevBlock+nonce+target+merkelRoot);
    }


    public void createTransaction(){
        Transaction T = new Transaction();
        List_Trasaction.add(T);
    }

    public String showTransactions (){
        String listeOfTransactions = "";
        for (int i=0;i<List_Trasaction.size();i++) {
            listeOfTransactions= " \n Transaction ID: "+List_Trasaction.get(i).getId()+". Transaction Number: "+i+".";
        }

        return listeOfTransactions;
    }
    @Override
    public String toString() {
        return " block: "+id + " time of creation : "+timestamp;
    }
}