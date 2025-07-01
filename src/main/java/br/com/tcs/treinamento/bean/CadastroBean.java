package br.com.tcs.treinamento.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.tcs.treinamento.entity.Pessoa;
import br.com.tcs.treinamento.model.PessoaVO;
import br.com.tcs.treinamento.service.PessoaService;
import br.com.tcs.treinamento.service.impl.PessoaServiceImpl;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "cadastroBean")
@ViewScoped
public class CadastroBean implements Serializable {
    private static final long serialVersionUID = 3450069247988201468L;

    // Classe VO para os dados da pessoa
    private PessoaVO cadastrarPessoa = new PessoaVO();

    // Propriedade para armazenar as mensagens de erro
    private String errorMessage;

    // Instancia manualmente o serviço – assim, o container não fará a injeção de dependências.
    private transient PessoaService pessoaService = new PessoaServiceImpl();

    /**
     * Método que converte o VO para a entidade e chama o service para persistir.
     * Após persistir, exibe o popup de sucesso.
     */

    public void confirmar() {
        List<String> erros = new ArrayList<>();

        if (cadastrarPessoa.getNome() == null || cadastrarPessoa.getNome().trim().isEmpty()) {
            erros.add("Nome não informado.");
        }
        if (cadastrarPessoa.getIdade() == null) {
            erros.add("Idade não informada.");
        }
        if (cadastrarPessoa.getEmail() == null || cadastrarPessoa.getEmail().trim().isEmpty()) {
            erros.add("E-mail não informado.");
        }
        if (cadastrarPessoa.getData() == null) {
            erros.add("Data de nascimento não informada.");
        }
        if (cadastrarPessoa.getTipoDocumento() == null || cadastrarPessoa.getTipoDocumento().trim().isEmpty()) {
            erros.add("Tipo de documento não informado.");
        } else {
            if ("CPF".equals(cadastrarPessoa.getTipoDocumento())) {
                String cpfSemMascara = cadastrarPessoa.getNumeroCPF() != null ? cadastrarPessoa.getNumeroCPF().replaceAll("[^0-9]", "") : "";
                if (cpfSemMascara.isEmpty() || cpfSemMascara.length() < 11) {
                    erros.add("CPF não informado ou incompleto (deve conter 11 dígitos).");
                }
            } else if ("CNPJ".equals(cadastrarPessoa.getTipoDocumento())) {
                String cnpjSemMascara = cadastrarPessoa.getNumeroCNPJ() != null ? cadastrarPessoa.getNumeroCNPJ().replaceAll("[^0-9]", "") : "";
                if (cnpjSemMascara.isEmpty() || cnpjSemMascara.length() < 14) {
                    erros.add("CNPJ não informado ou incompleto (deve conter 14 dígitos).");
                }
            }
        }

        if (!erros.isEmpty()) {
            this.errorMessage = String.join("<br/>", erros);
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
            return;
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(cadastrarPessoa.getNome());
        pessoa.setIdade(cadastrarPessoa.getIdade());
        pessoa.setEmail(cadastrarPessoa.getEmail());
        pessoa.setData(cadastrarPessoa.getData());
        pessoa.setTipoDocumento(cadastrarPessoa.getTipoDocumento());
        pessoa.setAtivo(true);

        if ("CPF".equals(cadastrarPessoa.getTipoDocumento())) {
            pessoa.setNumeroCPF(cadastrarPessoa.getNumeroCPF().replaceAll("[^0-9]", ""));
        } else if ("CNPJ".equals(cadastrarPessoa.getTipoDocumento())) {
            pessoa.setNumeroCNPJ(cadastrarPessoa.getNumeroCNPJ().replaceAll("[^0-9]", ""));
        }

        try {
            pessoaService.cadastrar(pessoa);
            PrimeFaces.current().executeScript("PF('successDialog').show();");
            limpar();
        } catch (Exception e) {
            this.errorMessage = "Erro ao cadastrar pessoa: " + e.getMessage();
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        }
    }

    public void limpar() {
        cadastrarPessoa.setNome(null);
        cadastrarPessoa.setIdade(null);
        cadastrarPessoa.setEmail(null);
        cadastrarPessoa.setData(null);
        cadastrarPessoa.setTipoDocumento(null);
        cadastrarPessoa.setNumeroCPF(null);
        cadastrarPessoa.setNumeroCNPJ(null);
        errorMessage = null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public PessoaVO getCadastrarPessoa() {
        return cadastrarPessoa;
    }

    public void setCadastrarPessoa(PessoaVO cadastrarPessoa) {
        this.cadastrarPessoa = cadastrarPessoa;
    }

    public PessoaService getPessoaService() {
        return pessoaService;
    }

    public void setPessoaService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        // Realiza a deserialização padrão
        ois.defaultReadObject();
        // Re-inicializa o serviço para evitar que seja nulo ou uma instância não serializável
        this.pessoaService = new PessoaServiceImpl();
    }

}