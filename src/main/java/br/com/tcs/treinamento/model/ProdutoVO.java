package br.com.tcs.treinamento.model;

import java.util.Date;

public class ProdutoVO {
    private String nome;
    private Date data;

    public ProdutoVO() {}

    public ProdutoVO(String nome, Date data) {
        this.nome = nome;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
