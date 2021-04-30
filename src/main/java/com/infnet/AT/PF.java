package com.infnet.AT;

public class PF extends Contas {
    public String nomeDoCorrentista;
    public String cpf;
    public float chequeEspecial;

    public PF(int numeroDaConta, float saldo,String nomeDoCorrentista, String cpf, float chequeEspecial) {
        super(numeroDaConta, saldo);
        this.nomeDoCorrentista = nomeDoCorrentista;
        this.cpf = cpf;
        this.chequeEspecial = chequeEspecial;
    } 
    
    public String getNomeDoCorrentista() {
        return nomeDoCorrentista;
    }

    public void setNomeDoCorrentista(String nomeDoCorrentista) {
        this.nomeDoCorrentista = nomeDoCorrentista;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public float getChequeEspecial() {
        return chequeEspecial;
    }

    public void setChequeEspecial(float chequeEspecial) {
        this.chequeEspecial = chequeEspecial;
    }
    
    @Override
    public String toString() {
//        String[] divisoes = nomeDoCorrentista.split(" ");
//        StringBuilder nomeCompleto = new StringBuilder();
//        
//        nomeDoCorrentista = nomeCompleto.append(divisoes[0].substring(0, 1).toUpperCase())
//                    .append(divisoes[0].substring(1))
//                    .append(" ")
//                    .append(divisoes[1].substring(0, 1).toUpperCase())
//                    .append(divisoes[1].substring(1)).toString();
        
        return super.toString() + ";" + nomeDoCorrentista + ";" + cpf + ";" + chequeEspecial;
    }
}
