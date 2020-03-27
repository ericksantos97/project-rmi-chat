package teste;

import util.IChatAula;
import util.Message;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class Teste {

    public static void main(String[] args) {

        String nome = "";
        String msgp = "";

        nome = JOptionPane.showInputDialog("Bem vindo, ao Chat, Qual é o seu nome? ");

        try {

            while (msgp != "0") {
                msgp = JOptionPane.showInputDialog("Chat - " + nome + " entre com a mensagem. (Entre com 0 para sair)");

                IChatAula objChat = (IChatAula) Naming.lookup("rmi://localhost:8282/chat");
                Message msg = new Message(nome, msgp);
                objChat.sendMessage(msg);
                System.out.println(returnMessage(objChat.retrieveMessage()));

            }

        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private static String returnMessage(List<Message> list) {

        StringBuilder valor = new StringBuilder();

        for (Message message : list) {
            valor.append(message.getUsuario()).append(": ").append(message.getMensagem()).append("\n");
        }

        return valor.toString();
    }
}
