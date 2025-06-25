package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.model.ProdutoVO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name="produtoBean")
@ViewScoped
public class ProdutoBean {
    private ProdutoVO produto = new ProdutoVO();
    private List<ProdutoVO> produtos;

    public ProdutoVO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoVO produto) {
        this.produto = produto;
    }

    public List<ProdutoVO> getProdutos() {
        return produtos;
    }

    public void adicionarProduto() {
        this.produtos.add(produto);
    }
}
