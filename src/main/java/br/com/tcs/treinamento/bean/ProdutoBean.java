package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.model.ProdutoVO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="produtoBean")
@ViewScoped
public class ProdutoBean {
    private ProdutoVO produto = new ProdutoVO();

    public ProdutoVO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoVO produto) {
        this.produto = produto;
    }
}
