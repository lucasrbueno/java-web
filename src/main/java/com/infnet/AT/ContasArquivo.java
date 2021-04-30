package com.infnet.AT;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ContasArquivo {
    private static String nomeArq;
    
    public static void setNome(String nome) {
        
        nomeArq = nome;
    }
    
    public static Scanner abreLeitura() {
        Scanner entrada = null;
        
        try {
            entrada = new Scanner(new File(nomeArq));
        }
        catch (FileNotFoundException erro) {
            System.out.println("Erro: arquivo nao existe");
        }
        return entrada;
    }    
    
    public static Formatter abreGravacao() {
        Formatter saida = null;
        
        try {
            saida = new Formatter(nomeArq);
        }
        catch (FileNotFoundException erro) {
            System.out.println("Erro: criacao do arquivo");
            System.exit(1);
        }
        catch (SecurityException erro) {
            System.out.println("Erro: problema de acesso ao arquivo");
            System.exit(1);
        }
        return saida;
    }    
    
    public static void leArquivo(Scanner entrada, ArrayList<Contas> contas) {
        int contaNumero;
        
        try {
            String linha;
            String[] campos;
            while(entrada.hasNext()) {
                linha = entrada.nextLine();
                campos = linha.split(";");
                int tipoConta = Integer.parseInt(campos[0]);
                if(tipoConta == 1){
                    contaNumero = Integer.parseInt(campos[1]);
                    float saldo = Float.parseFloat(campos[2]);
                    String nomeCorrentista = campos[3];                    
                    String cpf = campos[4];
                    float chequeEspecial = Float.parseFloat(campos[5]);
                    
                    PF pf = new PF(contaNumero, saldo, nomeCorrentista, cpf, chequeEspecial);
                    contas.add(pf);
                } else if (tipoConta == 2) {
                    contaNumero = Integer.parseInt(campos[1]);
                    float saldo = Float.parseFloat(campos[2]);
                    String nomeEmpresa = campos[3];
                    String cnpj = campos[4];

                    PJ pj = new PJ(contaNumero, saldo, nomeEmpresa, cnpj);
                    contas.add(pj);
                }
            }
        }
        catch (NoSuchElementException erro) {
            System.out.println("Erro: formatacao do arquivo");
        }
        catch (IllegalStateException erro) {
            System.out.println("Erro: leitura do arquivo");
        }
    }
        
    public static void gravaContas(Formatter saida, ArrayList<Contas> contas) {
    
        for (int i = 0; i < contas.size(); i++) { 
            
            System.out.println(contas.get(i));    
            try {
                if (contas.get(i) instanceof PF){
                    saida.format("1;%s;%s;%s;%s;%s\n", 
                            contas.get(i).getNumeroDaConta(),
                            contas.get(i).getSaldo(),                            
                            ((PF)contas.get(i)).getNomeDoCorrentista(), 
                            ((PF)contas.get(i)).getCpf(), 
                            ((PF)contas.get(i)).getChequeEspecial());
                } else if (contas.get(i) instanceof PJ){
                    saida.format("2;%s;%s;%s;%s;\n", 
                            contas.get(i).getNumeroDaConta(),
                            contas.get(i).getSaldo(),
                            ((PJ)contas.get(i)).getNomeDaEmpresa(),
                            ((PJ)contas.get(i)).getCnpj());           
                }
            }
            catch (FormatterClosedException erro) {
                System.err.println("Erro: gravação no arquivo" );
            }
        }
    }
    
    public static void fechaArquivo(Scanner entrada) {
        
        if (entrada != null) {
            entrada.close();
        }
    }    
    
    public static void fechaArquivo(Formatter saida) {
        
        if (saida != null) {
            saida.close();
        }
    }    
}
