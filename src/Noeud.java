import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
//import java.io.Serializable;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Noeud extends Agent {

    Block actualBlock = new Block();
     ArrayList<String> neighbors;
    ArrayList<PublicKey> neighborsKeys;


    Interface anInterface = new Interface();

    Wallet wallet = new Wallet(this);

    public PublicKey pubKey = wallet.publicKey;


    public String logs = "";

    public String blockLogs = "";

    public Noeud() throws NoSuchAlgorithmException {
    }


    protected void setup() {

        blockLogs = blockLogs + "\n" + "noeud " + getLocalName() + "my actual block is: " + actualBlock.id;
        //Block block = new Block(getLocalName());
        Object[] args = getArguments();

        neighborsKeys =new ArrayList<PublicKey>();
        neighbors = new ArrayList<String>();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                neighbors.add(args[i].toString());
            }
        }


        anInterface.setTitle(getLocalName());
        anInterface.pack();
        anInterface.setVisible(true);

        anInterface.textPane1.setText(blockLogs);


        refreshInfo();

        anInterface.sendBlock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBehaviour(new envoieBlock());
            }
        });
        anInterface.sendPubKey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBehaviour(new envoieKey());
            }
        });

        anInterface.transactionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBehaviour(new doTransaction());
            }
        });

        anInterface.newBlockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBehaviour(new newBlock());
            }
        });


        addBehaviour(new consulter());
        //System.exit(0);
    }


    public void refreshInfo() {
        for (int i=0;i<actualBlock.List_Trasaction.size();i++){

            Transaction actualTransaction=actualBlock.List_Trasaction.get(i);

            for (int j=0;j<actualTransaction.ListInputs.size();j++){
                Input a=actualTransaction.ListInputs.get(j);
                if (getLocalName().equals(a.scriptSig)){
                    wallet.value=wallet.value - 4;
                }
            }

            for (int j=0;j<actualTransaction.ListOutputs.size();j++){
                Output a=actualTransaction.ListOutputs.get(j);
                if (getLocalName().equals(a.scriptPubKey)){
                    wallet.value=wallet.value + 4;
                }
            }
        }
        String infoText = "wallet value is: " + String.valueOf(wallet.value) + "\n";
        infoText = infoText + "public key is: " + pubKey;
        anInterface.textPane2.setText(infoText);
    }

    public class envoieBlock extends OneShotBehaviour {

        @Override
        public void action() {
            // TODO Auto-generated method stub
            actualBlock.merkelRoot = ArbreDeMerkle.calculRacineMerkle(actualBlock.List_Trasaction);

            actualBlock.version = 1;
            actualBlock.timestamp = java.time.LocalTime.now();
            actualBlock.nonce = 1;
            actualBlock.target = 1;

            blockLogs = blockLogs + "\n" + "noeud " + getLocalName() + " my actual block is: " + actualBlock.id;
            anInterface.textPane1.setText(blockLogs);

            for (int i = 0; i < neighbors.size(); i++) {
                ACLMessage msgEnvoi = new ACLMessage(ACLMessage.INFORM);
                msgEnvoi.addReceiver(new AID(neighbors.get(i), AID.ISLOCALNAME));
                //msgEnvoi.setContent("message from: "+ getLocalName());

                try {
                    msgEnvoi.setContentObject(new Packet(actualBlock, "Block", getLocalName()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                send(msgEnvoi);
            }

        }



    }

    public class doTransaction extends OneShotBehaviour {

        @Override
        public void action() {

            Transaction transaction = new Transaction();

            /*//get pubkey of noeud 2
            PublicKey publicKeyOf2 = null;
            for (int i = 0; i < neighbors.size(); i++) {
                if ("2".equals(neighbors.get(i).num)) {
                    publicKeyOf2 = neighbors.get(i).publicKey;
                }
            }*/

            //value,pubKey de benificiare
            Output output = new Output(4, "2");
            transaction.ListOutputs.add(output);

            //id de transaction,position de output,pubKey de destination
            Input input = new Input(transaction.id, transaction.ListInputs.indexOf(output), "1");
            transaction.ListInputs.add(input);

            blockLogs = blockLogs + "\n" + transaction.toString();
            anInterface.textPane1.setText(blockLogs);

            ACLMessage msgEnvoi = new ACLMessage(ACLMessage.INFORM);
            msgEnvoi.addReceiver(new AID(getLocalName(), AID.ISLOCALNAME));

            for (int i = 0; i < neighbors.size(); i++) {
                msgEnvoi.addReceiver(new AID(neighbors.get(i), AID.ISLOCALNAME));
                //msgEnvoi.setContent("message from: "+ getLocalName());

                try {
                    msgEnvoi.setContentObject(new Packet(transaction, "Transaction", getLocalName()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                send(msgEnvoi);
                msgEnvoi = new ACLMessage(ACLMessage.INFORM);

            }

        }
    }

    public class envoieKey extends OneShotBehaviour {

        @Override
        public void action() {
            // TODO Auto-generated method stub

            for (int i = 0; i < neighbors.size(); i++) {
                ACLMessage msgEnvoi = new ACLMessage(ACLMessage.INFORM);
                msgEnvoi.addReceiver(new AID(neighbors.get(i), AID.ISLOCALNAME));
                //msgEnvoi.setContent("message from: "+ getLocalName());

                try {
                    msgEnvoi.setContentObject(new Packet(pubKey, "PublicKey", getLocalName()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                send(msgEnvoi);
            }


        }

    }


    public class newBlock extends OneShotBehaviour {

        @Override
        public void action() {
            Block newBlock = new Block();

            String hashHeader = actualBlock.HashHeaderBlock;
            newBlock.HashPrevBlock = hashHeader;
            actualBlock = newBlock;
            actualBlock.HashHeaderBlock = actualBlock.hashBlock();
            actualBlock = newBlock;


            blockLogs = blockLogs + "\n" + "noeud " + getLocalName() + "my actual block is: " + actualBlock.id;
            anInterface.textPane1.setText(blockLogs);

        }
    }

    public class consulter extends CyclicBehaviour {

        @Override
        public void action() {
            // TODO Auto-generated method stub

            ACLMessage msgRecu = receive();

            Packet packetRecu;
            Block blockRecu;
            PublicKey pubKeyRecu;
            Transaction transactionRecu;
            if (msgRecu != null) {
                try {
                    packetRecu = (Packet) msgRecu.getContentObject();


                    if (packetRecu.type.equals("Block")) {
                        blockRecu = (Block) packetRecu.content;
                        System.out.println(" Noued " + getLocalName() + " receive " + packetRecu.toString());
                        logs = logs + "\n" + (" Noued " + getLocalName() + " receive " + packetRecu.toString());
                        String hashHeader = actualBlock.HashHeaderBlock;
                        blockRecu.HashPrevBlock = hashHeader;
                        actualBlock = blockRecu;
                        actualBlock.HashHeaderBlock = actualBlock.hashBlock();
                        anInterface.textPane4.setText(logs);

                        blockLogs = blockLogs + "\n" + "noeud " + getLocalName() + "my actual block is: " + actualBlock.id;
                        anInterface.textPane1.setText(blockLogs);


                    } else if (packetRecu.type.equals("PublicKey")) {
                        pubKeyRecu = (PublicKey) packetRecu.content;
                        System.out.println(" Noued " + getLocalName() + " receive " + packetRecu.toString());
                        logs = logs + "\n" + (" Noued " + getLocalName() + " receive " + packetRecu.toString());
                        anInterface.textPane4.setText(logs);

                        for (int i = 0; i < neighbors.size(); i++) {
                            if (packetRecu.sender.equals(neighbors.get(i))) {
                                neighborsKeys.add(pubKeyRecu);
                            }
                        }


                    } else if (packetRecu.type.equals("Transaction")) {
                        transactionRecu = (Transaction) packetRecu.content;
                        System.out.println(" Noued " + getLocalName() + " receive " + packetRecu.toString());
                        logs = logs + "\n" + (" Noued " + getLocalName() + " receive " + packetRecu.toString());
                        actualBlock.List_Trasaction.add(transactionRecu);
                        anInterface.textPane4.setText(logs);
                        refreshInfo();

                    }
                } catch (UnreadableException e) {
                    throw new RuntimeException(e);
                }

                //anInterface.textPane1.setText(msgContenu.showTransactions());


            }

        }
    }


}
