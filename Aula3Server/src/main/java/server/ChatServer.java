package server;

import util.ChatAula;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ChatServer {

    public static Connection conexao = null;

    public ChatServer() {

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

            LocateRegistry.createRegistry(8282);
            Naming.rebind("rmi://localhost:8282/chat", new ChatAula());
            System.out.println("Rodando servidor...");

        } catch (SQLException e) {
            System.out.println("Falha ao se conectar com o MySql");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws RemoteException {
        new ChatServer();
    }

}
