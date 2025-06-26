package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.model.ProdutoVO;
import org.primefaces.PrimeFaces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name="produtoBean")
@SessionScoped
public class ProdutoBean implements Serializable {
    private ProdutoVO produto = new ProdutoVO();
    private List<ProdutoVO> produtos = new ArrayList<>();
    private String errorMessage;

    public ProdutoVO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoVO produto) {
        this.produto = produto;
    }

    public List<ProdutoVO> getProdutos() {
        return this.produtos;
    }

    public void adicionarProduto() {
        if(produto.getNome() != null && !produto.getNome().isEmpty() && produto.getData() != null) {
            this.produtos.add(produto);
            this.produto = new ProdutoVO();
            // Exibe o popup de sucesso após a confirmação
            PrimeFaces.current().executeScript("PF('successDialog').show();");
        } else {
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        }
    }

    public void excluirProduto(int id) {
        try {
            produtos.remove(id);
            // Exibe o popup de sucesso após a confirmação
            PrimeFaces.current().executeScript("PF('successDialog').show();");
        } catch (Exception e) {
            // Em caso de erro na persistência, exibe o diálogo de erro
            errorMessage = "Erro ao remover produto: " + e.getMessage();
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
            return;
        }
    }
}
