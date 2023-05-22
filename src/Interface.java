import javax.swing.*;
import java.awt.event.*;

//import java.io.Serializable;


public class Interface extends JFrame  {


    private JPanel contentPane;
    public JButton sendBlock;

    public JButton sendPubKey;

    public JButton transactionButton;
    public JButton newBlockButton;
    public JButton tache5Button;
    public JTextPane textPane2;
    public JTextPane textPane3;
    public  JTextPane textPane4;
    public JTextPane textPane1;


    public Interface()  {
        setContentPane(contentPane);

        /*noeud1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onnoeud1();
            }
        });*/


        //addText(textPane1,"hello", Color.red);



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });


    }



    private void onCancel() {
        // add your code here if necessary
        dispose();
    }




}
