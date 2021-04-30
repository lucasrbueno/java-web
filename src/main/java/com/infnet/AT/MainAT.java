package com.infnet.AT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MainAT {
    static final int FIM = 5;
    
    public static void main(String[] args) {       
        escolhaMenuOriginal();       
    }
    
    public static void escolhaMenuOriginal(){
        ArrayList<Contas> contas = new ArrayList<>();
        int escolha;
        
        Connection conn = BancoConexaoContas.getConexao();
        
        if(conn == null){
            System.out.println("Erro: Sem conexão");
            return;
        }
//        System.out.println(listaAlunos(conn));
//        System.out.println(alteraAlunos(conn));
        
        escolha = menuOriginal();
        while(escolha != FIM){
           switch (escolha) {
            case 1:
                incluirConta(conn);
                break;
            case 2:
                alterarSaldo(contas);
                break;
            case 3:
                removerConta(contas);
                break; 
            case 4:
                escolhaMenuRelatorio(conn);
                break;
            default:
                System.out.println("Opção inválida, escolha outra opção.");
                break;
            }
           escolha = menuOriginal();
        }        
        
        BancoConexaoContas.FechaConexao();
    }
    
    public static int menuOriginal(){
        int escolha;
        boolean bool;
        
        StringBuilder menu = new StringBuilder();
        menu.append("--------------------------------------------")
                .append("\n[1] Inclusão de conta")
                .append("\n[2] Alterar saldo")
                .append("\n[3] Exclusão de conta")
                .append("\n[4] Relatórios Gerenciais")
                .append("\n[5] Sair.")
                .append("\nQual opção deseja?");  
        
        do {  
            System.out.println(menu);
            escolha = validarInteiro();
            bool = true;
        } while (!bool);
        return escolha;
    }
    
    private static void incluirConta(Connection conn){
        System.out.println("Escolha o número da conta");
        int conta = validarInteiro();
        boolean existe = pesquisaConta(conn, conta);
        
        if(existe == true){
            System.out.println("Erro: Conta já existe.");
        } else {
            System.out.println("Pessoa física ou jurídica? Escolha seu tipo de conta \n[1] Pessoa Física\n[2] Pessoa Jurídica: ");
            int tipoConta = validarInteiro();

            switch (tipoConta){
                case 1:
                    incluirContaPessoaFisica(conn, conta);
                    break;
                case 2:
//                    incluirContaPessoaJuridica(contas, conta);
                    break;
                default:
                    System.out.println("Valor inválido");   
            }
        }  
    }
    
    public static void listagemDeContas(Connection conn){
//        if(contas.size() > 0) {
        System.out.println("Qual tipo de conta quer listar? \n[1] Conta Física\n[2] Conta Jurídica");
        int tipoConta = validarInteiro();
        String comando;
        String retorno = "";
        
        switch(tipoConta){
            case 1:
               comando = "SELECT * FROM pessoafisica";
        
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
                break;
            case 2:
                comando = "SELECT * FROM pessoajuridica";
        
                try {
                    PreparedStatement ps = conn.prepareStatement(comando);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        String id = rs.getString("idpessoajuridica");
                        String numeroDaConta = rs.getString("numerodaconta");
                        String saldo = rs.getString("saldo");
                        String nomeDaEmpresa = rs.getString("nomedaempresa");
                        String cnpj = rs.getString("cnpj");
                        retorno += id + " " + numeroDaConta + " " + saldo + " " + nomeDaEmpresa + " " + cnpj + "\n";
                    }
                } catch (SQLException ex) {
                    System.out.println("Erro: Comando SQL");
                }
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }  
//        } else {
//            System.out.println("Nenhuma conta cadastrada!");
//        }
        System.out.println(retorno);
    }
    
    public static String validarNome(){
        Scanner scan;
        scan = new Scanner(System.in);
        String nome, divisoes[];
 
        do {
            nome = scan.nextLine();
            
            divisoes = nome.split(" ");
            if(divisoes.length != 2){
                System.out.println("Inválido, digite o nome completo");
            }        
        } while(divisoes.length != 2); 
        return nome;
    }
    
    public static void removerConta(ArrayList<Contas> contas){
        if(contas.size() > 0) {
            System.out.println("Escreva o número da conta que você deseja apagar: ");
            int escolha = validarInteiro();

            contas.remove(localizaConta(contas, escolha));
            System.out.println("Conta " + escolha + " apagada com sucesso!");           
        } else {
            System.out.println("Nenhuma conta cadastrada para ser removida.");
        }
    }
    
    public static int menuRelatório(){
        int escolha;
        boolean bool;
        
        StringBuilder menu = new StringBuilder();
        menu.append("--------------------------------------------")
                .append("\n[1] Listar Clientes com saldo negativo")
                .append("\n[2] Listar Clientes com saldo acima de 100 reais")
                .append("\n[3] Listar todas as contas")
                .append("\n[4] Listar operações feitas nas contas")
                .append("\n[5] Voltar")
                .append("\nQual opção deseja?");   
        
        do {     
            System.out.println(menu);
            escolha = validarInteiro();
            bool = true;
        } while (!bool);
        return escolha;
    }
    
    public static void escolhaMenuRelatorio(Connection conn){
        int escolha;

        escolha = menuRelatório();
        while(escolha != FIM){
           switch (escolha) {
            case 1:
//                clienteNegativo(contas);
                break;
            case 2:
//                clienteAcima(contas);
                break;
            case 3:
                listagemDeContas(conn);
                break; 
            case 4:
//                impressaoOperacoes(contas);
                break;
            default:
                System.out.println("Opção inválida, escolha outra opção.");
                break;
            }
           escolha = menuRelatório();
        }
    }
    
    public static void alterarSaldo(ArrayList<Contas> contas){
//        Scanner scan = new Scanner(System.in);
//        if(contas.size() > 0) {  
//            System.out.println("Escolha o número da conta");
//            int conta = validarInteiro();
//            boolean existe = pesquisaConta(contas, conta);
//
//            if(existe == false){
//                System.out.println("Erro: Conta não existe.");
//            } else {
//                System.out.println("Qual tipo de operação? \n[1] Crédito\n[2] Débito ");
//                int tipoOperacao = scan.nextInt();
//
//                switch (tipoOperacao){
//                    case 1:
//                        calculoCredito(contas, conta);
//                        break;
//                    case 2:
//                        calculoDebito(contas, conta);
//                        break;
//                    default:
//                     System.out.println("Operação inválida");   
//                }
//            }
//        } else {
//            System.out.println("Nenhuma conta cadastrada para ter alteração de saldo.");
//        }
    }
    
    public static String incluirContaPessoaFisica(Connection conn, int conta){
        Scanner scan = new Scanner(System.in);
        String nomeCorrentista, cpf;
        float saldo, chequeEspecial;
        String retorno = "";
        
        System.out.println("Insira nome do correntista");
        nomeCorrentista = validarNome();
        System.out.println("CPF do correntista: ");
        cpf = scan.next();
        System.out.println("Cheque Especial do correntista: ");
        chequeEspecial = validarFloat();
        System.out.println("Saldo do correntista: ");
        saldo = validarFloat();
        
        String comandoInclusão = "INSERT INTO pessoafisica(numerodaconta, saldo, nomedocorrentista, cpf, chequeespecial) "
                + "values("+conta+", "+saldo+", '"+nomeCorrentista+"', '"+cpf+"', '"+chequeEspecial+"')";
        
        try {
            PreparedStatement ps = conn.prepareStatement(comandoInclusão);
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
    
    public static void incluirContaPessoaJuridica(ArrayList<Contas> contas, int conta){
        Scanner scan = new Scanner(System.in);
        String cnpj, nomeEmpresa;
        int contaNumero;
        float saldo;
        
        contaNumero = conta;
        System.out.println("Nome da Empresa: ");
        nomeEmpresa = validarNome();
        System.out.println("CNPJ da Empresa: ");
        cnpj = scan.next();
        System.out.println("Saldo da conta: ");
        saldo = validarFloat();

        PJ pj = new PJ(contaNumero, saldo, nomeEmpresa, cnpj);

        contas.add(pj);
    }
    
    public static boolean pesquisaConta(Connection conn, int opcao){
        boolean existe = false;
        
//        for(Contas c : conn) {
//                if(conn.getNumeroDaConta() == opcao){
//                    existe = true;
//                    break;
//                } 
//            }
       return existe; 
    }
    
    public static void clienteNegativo(ArrayList<Contas> contas){
        float saldo;
        
        if(contas.size() > 0) {
            System.out.println("Listagem de contas negativadas:");
            for(Contas c : contas) { 
                saldo = c.getSaldo();
                if(c instanceof PF){ 
                    if(saldo < 0){
                        System.out.println("--------------------------------------------");
                        System.out.println("PF:\n" + c);
                    } else {
                        System.out.println("Conta de pessoa física não está negativa");
                    }
                } else if(c instanceof PJ) {
                    if(saldo < 0){
                        System.out.println("--------------------------------------------");
                        System.out.println("PJ:\n" + c);
                    } else {
                        System.out.println("Conta de pessoa jurídica não está negativa");
                    }
                }
            }
        } else {
            System.out.println("Nenhuma conta cadastrada!");
        }
    }
    
    public static void clienteAcima(ArrayList<Contas> contas){
        float saldo;
        
        if(contas.size() > 0) {
            System.out.println("Listagem de contas acima de um valor previsto:");
            for(Contas c : contas) {
                saldo = c.getSaldo();
                if(c instanceof PF){
                    if(saldo > 100){
                        System.out.println("--------------------------------------------");
                        System.out.println("PF:\n" + c);
                    } else {
                        System.out.println("Conta de pessoa física está abaixo de 100 reais de saldo");
                    }
                } else if(c instanceof PJ) {
                    if(saldo > 100){
                        System.out.println("--------------------------------------------");
                        System.out.println("PJ:\n" + c);
                    } else {
                        System.out.println("Conta de pessoa jurídica está abaixo de 100 reais de saldo");
                    }
                }
            }
        } else {
            System.out.println("Nenhuma conta cadastrada!");
        }
    }
    
 
    
    public static void calculoCredito(ArrayList<Contas> contas, int conta){
        String tipoOperacoes = "Crédito";
        Date dataHora = new Date();
        String data, hora;
        float saldo;
        int indice = localizaConta(contas, conta);

        if(contas.get(indice) instanceof PF){
            System.out.println("Quanto de saldo quer creditar na conta de Pessoa Física? ");
            saldo = validarFloat();
            data = new SimpleDateFormat("dd/MM/yyyy").format(dataHora);
            hora = new SimpleDateFormat("HH:mm:ss").format(dataHora);
            contas.get(indice).credito(saldo);
            contas.get(indice).salvarOperacao(data, hora, tipoOperacoes, saldo);
        } else {
            System.out.println("Quanto de saldo quer creditar na conta de Pessoa Jurídica? ");
            saldo = validarFloat();
            data = new SimpleDateFormat("dd/MM/yyyy").format(dataHora);
            hora = new SimpleDateFormat("HH:mm:ss").format(dataHora);
            contas.get(indice).credito(saldo);
            contas.get(indice).salvarOperacao(data, hora, tipoOperacoes, saldo);
        }
    }    
    
    public static void calculoDebito(ArrayList<Contas> contas, int conta){
        String tipoOperacoes = "Débito";
        Date dataHora = new Date();
        String data, hora;
        float saldo;
        int indice = localizaConta(contas, conta);
                
        if(contas.get(indice) instanceof PF){
            System.out.println("Quanto de saldo quer debitar na conta de Pessoa Física? ");
            saldo = validarFloat();
            float devendo = -((PF) contas.get(indice)).getChequeEspecial();
            float calculo = contas.get(indice).getSaldo() - saldo;

            if((contas.get(indice).getSaldo() == devendo) || calculo < devendo){
                System.out.println("Saldo insuficiente para débito.");
            } else {
                data = new SimpleDateFormat("dd/MM/yyyy").format(dataHora);
                hora = new SimpleDateFormat("HH:mm:ss").format(dataHora);
                contas.get(indice).debito(saldo);                       
                contas.get(indice).salvarOperacao(data, hora, tipoOperacoes, saldo);
            }
        } else {
            System.out.println("Quanto de saldo quer debitar na conta de Pessoa Jurídica? ");
            saldo = validarFloat();
            float calculo = contas.get(indice).getSaldo() - saldo;

            if((contas.get(indice).getSaldo() < 0) || (calculo < 0) ){
                System.out.println("Saldo insuficiente para débito.");
            } else {
                data = new SimpleDateFormat("dd/MM/yyyy").format(dataHora);
                hora = new SimpleDateFormat("HH:mm:ss").format(dataHora);
                contas.get(indice).debito(saldo);                        
                contas.get(indice).salvarOperacao(data, hora, tipoOperacoes, saldo);
            }
        }
    }

    private static void impressaoOperacoes(ArrayList<Contas> contas) {
        System.out.println("Escolha o número da conta para ver extrato: ");
        int conta = validarInteiro();
//        boolean existe = pesquisaConta(contas, conta);
//
//        if(existe == false){
//            System.out.println("Erro: Conta não existe.");
//        } else {
//            ArrayList<String> operacoes = contas.get(localizaConta(contas, conta)).getOperacoes();
//            for(String oper: operacoes){
//                System.out.println(oper);
//            }
//        }
    }  
    
    private static float validarFloat() {
        Scanner scan = new Scanner(System.in);
        float escolha = 0;
        boolean falso = false;

        do {
            String a = scan.next();
            try {
                escolha = Float.valueOf(a);                
                falso = true;
            }
            catch(Exception ex) {
                System.out.println("Escolha um número válido ");
            }
        } while(!falso);
        return escolha;
    }
    
    private static int validarInteiro() {
        Scanner scan = new Scanner(System.in);
        int escolha = 0;
        boolean falso = false;

        do {
            String a = scan.next();
            try {
                escolha = Integer.valueOf(a);                
                falso = true;
            }
            catch(Exception ex) {
                System.out.println("Escolha um número válido ");
            }
        } while(!falso);
        return escolha;
    }
    
    public static int localizaConta(ArrayList<Contas> contas, int opcao){
        int indice = -1;
        
        for(int i = 0; i < contas.size(); i++){
            if(opcao == contas.get(i).getNumeroDaConta()){
                indice = i;
            }
        }
        return indice;
    }
}
