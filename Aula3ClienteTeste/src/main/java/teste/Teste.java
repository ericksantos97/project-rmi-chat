package teste;

import server.ChatServer;
import util.IChatAula;
import util.Message;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Teste {

    public static void main(String[] args) {

        String nome = "";
        String msgp = "";

        nome = JOptionPane.showInputDialog("Bem vindo, ao Chat, Qual é o seu nome? ");

        try {

            while (!"0".equals(msgp)) {
                msgp = JOptionPane.showInputDialog("Chat - " + nome + " entre com a mensagem. (Entre com 0 para sair)");

                IChatAula objChat = (IChatAula) Naming.lookup("rmi://localhost:8282/chat");
                Message msg = new Message(nome, msgp);
                objChat.sendMessage(msg);
                System.out.println(returnMessage(objChat.retrieveMessage()));

                String insertQueryStatement = "INSERT INTO history VALUES  (default, ?,?)";

                PreparedStatement preparedStatement = ChatServer.conexao.prepareStatement(insertQueryStatement, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, msgp);
                preparedStatement.executeUpdate();

            }

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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
