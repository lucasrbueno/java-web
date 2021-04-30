package com.infnet.AT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoConexaoContas {
    private static Connection conn;
    
    public static Connection getConexao() {
        
        try {
            System.out.println("Conectando ao banco...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            String servidor = "localhost";
            String bd = "contas";
            String url = "jdbc:mysql://" + servidor + "/" + bd;
            String usuario = "root";
            String senha = "1234";
            conn = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Banco conectado");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver não localizado");
        }
        catch (SQLException e) {
            System.out.println("Erro: conexão ao banco");
        }
        return conn;
    }
    
    public static void FechaConexao() {
        
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Erro: fechamento do banco");;
        }
    }
}
