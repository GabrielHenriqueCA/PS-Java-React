package br.com.banco.domain.models;

import br.com.banco.application.dtos.ContaDTO;

public class Conta {
    private int id;
    private String nomeResponsavel;

    public Conta() {
    }

    public Conta(ContaDTO contaDTO) {
        this.nomeResponsavel = contaDTO.getNomeResponsavel();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }
}