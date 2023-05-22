import java.util.LinkedList;

public class Test {
    public static void main(String[] args) {

        Transaction t1=new Transaction();
        Transaction t2=new Transaction();


        LinkedList<Transaction> list = new LinkedList<>();
        list.add(t1);
        list.add(t2);
        list.add(t1);
        ArbreDeMerkle  arbreDeMerkle = new ArbreDeMerkle();

        System.out.println(arbreDeMerkle.calculRacineMerkle(list));

}
}
