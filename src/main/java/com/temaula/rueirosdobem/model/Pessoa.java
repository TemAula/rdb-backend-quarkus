package com.temaula.rueirosdobem.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@Table(name = "pessoa")
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false)
    private String nome;

    @Column(length = 32, nullable = false)
    private String email;

    @Column(length = 16, nullable = false)
    private String telefone;

    //@Column(length = 128)
    //private String endereco;

    @Column(length = 16, nullable = false)
    private String senha;
    
    //private Endereco endereco2;

    @Column(nullable = false)
    private boolean administrador; 	/* FALSE = Doador, TRUE = Administrador */
    
    /* ENDEREÇO */
    @Column(length = 9, nullable = false)
    private String cep; 			//VARCHAR(9) NOT NULL,
    
    @Column(length = 64, nullable = false)
    private String logradouro; 		//VARCHAR(64) NOT NULL,
    
    @Column(length = 8, nullable = false)
    private String numero; 			//VARCHAR(8) NOT NULL DEFAULT 'S/N',
    
    @Column(length = 16)
    private String complemento; //VARCHAR(16),
    
    @Column(length = 32)
    private String bairro; 			//VARCHAR(32),
    
    @Column(length = 32, nullable = false)
    private String cidade; 			//VARCHAR(32) NOT NULL,
    
    @Column(length = 2, nullable = false)
    private String uf; 	
    
    
    public Pessoa() {}
    
    
    
    public Pessoa(Long id, String nome, String email, String telefone, String senha, boolean administrador, String cep,
			String logradouro, String numero, String complemento, String bairro, String cidade, String uf) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.senha = senha;
		this.administrador = administrador;
		this.cep = cep;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /*public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }*/

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}



	public String getNumero() {
		return numero;
	}



	public void setNumero(String numero) {
		this.numero = numero;
	}



	public String getComplemento() {
		return complemento;
	}



	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}



	public String getBairro() {
		return bairro;
	}



	public void setBairro(String bairro) {
		this.bairro = bairro;
	}



	public String getCidade() {
		return cidade;
	}



	public void setCidade(String cidade) {
		this.cidade = cidade;
	}



	public String getUf() {
		return uf;
	}



	public void setUf(String uf) {
		this.uf = uf;
	}



	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    
    @Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", email=" + email + ", telefone=" + telefone + ", senha="
				+ senha + ", administrador=" + administrador + ", cep=" + cep + ", logradouro=" + logradouro
				+ ", numero=" + numero + ", complemento=" + complemento + ", bairro=" + bairro + ", cidade=" + cidade
				+ ", uf=" + uf + "]";
	}
}