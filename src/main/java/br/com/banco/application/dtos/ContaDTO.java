package br.com.banco.application.dtos;


import javax.validation.constraints.NotNull;

public class ContaDTO {
    private int id;
    @NotNull(message = "O nome do responsavel nao pode ser nulo")
    private String nomeResponsavel;

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
