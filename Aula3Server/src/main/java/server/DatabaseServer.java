/*
package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseServer {

    public static Connection conexao = null;

    public DatabaseServer() {

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
        }
    }

    public static void main(String[] args) {
        new DatabaseServer();
    }
}
*/
