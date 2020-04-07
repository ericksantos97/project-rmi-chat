package teste;

import org.apache.commons.lang3.StringUtils;
import util.IChatAula;
import util.Message;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Teste {

    private static Connection conexao = null;
    private static String nome = "";
    private static String msgp = "";
    private static IChatAula objChat;

    public static void main(String[] args) {
        initChat();
        createConnectionDatabase();
        refreshChat();
    }

    private static void initChat() {
        while (StringUtils.isBlank(nome)) {
            nome = JOptionPane.showInputDialog("Bem vindo ao chat. Qual é o seu nome?");
        }
    }

    private static void refreshChat() {

        try {

            while (!"0".equals(msgp)) {
                msgp = JOptionPane.showInputDialog("Chat - " + nome + " entre com a mensagem. (Entre com 0 para sair)");

                if (msgp != null) {
                    insertIntoRMI();
                    insertIntoDatabase();
                } else {
                    generateReport();
                    closeConnectionDatabase();
                    break;
                }

            }

        } catch (RemoteException | NotBoundException | MalformedURLException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createConnectionDatabase() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL JDBC Driver Registrado!");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao registrar o driver MySql JDBC");
            e.printStackTrace();
        }

        try {
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat?serverTimezone=UTC&amp&&useSSL=false",
                    "root", "root");

            if (conexao != null) {
                System.out.println("Conexão realizada com sucesso!");
            } else {
                System.out.println("Falha - Conexão nula!");
            }

        } catch (SQLException e) {
            System.out.println("Falha ao se conectar com o MySql");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertIntoDatabase() throws SQLException {
        String insertQueryStatement = "INSERT INTO historic VALUES  (?,?, CURRENT_TIMESTAMP)";

        PreparedStatement preparedStatement = conexao.prepareStatement(insertQueryStatement);
        preparedStatement.setString(1, nome);
        preparedStatement.setString(2, msgp);
        preparedStatement.executeUpdate();
    }

    private static void insertIntoRMI() throws RemoteException, NotBoundException, MalformedURLException {
        objChat = (IChatAula) Naming.lookup("rmi://localhost:8282/chat");
        Message msg = new Message(nome, msgp);
        objChat.sendMessage(msg);
        System.out.println(returnMessage(objChat.retrieveMessage()));
    }

    private static String returnMessage(List<Message> list) {

        StringBuilder valor = new StringBuilder();

        for (Message message : list) {
            valor.append(message.getUsuario()).append(": ").append(message.getMensagem()).append("\n");
        }

        return valor.toString();
    }

    private static ResultSet getHistoric() {
        try {

            String searchQueryStatement = "SELECT * FROM historic";
            PreparedStatement preparedStatement = conexao.prepareStatement(searchQueryStatement);
            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void generateReport() {
        try {
            PrintWriter writerBD = new PrintWriter(String.format("F:\\FACULDADES - LOGATTI\\SISTEMAS DISTRIBUIDOS\\AulaProj3\\Relatorios\\historico.txt",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss"))), "UTF-8");

            ResultSet resultSet = getHistoric();

            while (resultSet.next()) {
                writerBD.println("Usuario: " + resultSet.getString("usuario") + " Mensagem: " + resultSet.getString("mensagem"));
            }

            writerBD.close();
            JOptionPane.showMessageDialog(null, "O Histórico de Conversas foi criado com sucesso!");
        } catch (FileNotFoundException | UnsupportedEncodingException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao gerar o histórico.");
            e.printStackTrace();
        }
    }

    private static void closeConnectionDatabase() {
        try {
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
