
import jade.core.Agent;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AgentTest  {

    public static void main(String[] args) {
        String[] commande = new String[3];
        String argument = "";
        argument = argument +  "1:Noeud(2,3,4,5)";
        argument = argument + ";2:Noeud(1,3,4,5)";
        argument = argument + ";3:Noeud(1,2,4,5)";
        argument = argument + ";4:Noeud(1,2,3,5)";
        argument = argument + ";5:Noeud(1,2,3,4)";

        commande[0] = "-cp";
        commande[1] = "jade.boot";
        commande[2] = argument;
        jade.Boot.main(commande);





    }














}
