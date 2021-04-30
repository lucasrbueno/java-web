package com.infnet.AT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BD1 {
    public static void main(String[] args) {
        Connection conn;
        
        conn = BancoConexaoContas.getConexao();
        if(conn == null){
            System.out.println("Erro: Sem conexão");
            return;
        }
        System.out.println(listaAlunos(conn));
//        System.out.println(alteraAlunos(conn));
        
        BancoConexaoContas.FechaConexao();
    }
    
    public static String listaAlunos(Connection conn){
        String retorno = "";
        String comando = "SELECT * FROM pessoafisica";
        
        try {
            PreparedStatement ps = conn.prepareStatement(comando);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String id = rs.getString("idpessoafisica");
                String numeroDaConta = rs.getString("numerodaconta");
                String saldo = rs.getString("saldo");
                String nomeDoCorrentista = rs.getString("nomedocorrentista");
                String cpf = rs.getString("cpf");
                String chequeEspecial = rs.getString("chequeespecial");
                retorno += id + " " + numeroDaConta + " " + saldo + " " + nomeDoCorrentista + " " + cpf + " " + chequeEspecial +  "\n";
            }
        } catch (SQLException ex) {
            System.out.println("Erro: Comando SQL");
        }
        
        return retorno;
    }
    
    public static String alteraAlunos(Connection conn){
        String retorno = "";
        String comandoInclusão = "INSERT INTO alunos(nome, telefone) values('juan', '753123698')";
        String comandoAlteracao = "UPDATE alunos SET telefone = '2222222' WHERE id = 1";
        String comandoExclusao = "DELETE FROM alunos WHERE id = 4";
        
        try {
            PreparedStatement ps = conn.prepareStatement(comandoExclusao);
            int n = ps.executeUpdate();
            if(n < 0){
                retorno = "Operação realizada";
            } else {
                retorno = "Operação não realizada";
            }           
        } catch (SQLException ex) {
            System.out.println("Erro: Comando SQL");
        }
        
        return retorno;
    }
}
