package com.lukas.avaliacaocelk.crudufs.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author lukas
 */
@Entity
@Table(name = "unidade_federativa", schema = "avaliacao_celk")
public class UF implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sigla", nullable = false)
    @Size(min = 2, max = 2)
    private String sigla;

    @NotNull
    @Column(name = "descricao", nullable = false)
    @Size(max = 500)
    private String descricao;

    @Column(name = "data_criacao", columnDefinition = "DATETIME", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_modificacao", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime dataModificacao;

    public UF() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getDataCriacaoFormatada() {
        if (this.dataCriacao == null) {
            return null;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            return this.dataCriacao.format(formatter);
        }
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public String getDataModificacaoFormatada() {
        if (this.dataModificacao == null) {
            return null;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            return this.dataModificacao.format(formatter);
        }
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UF other = (UF) obj;

        return !(this.id != other.id && (this.id == null || !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "UF{" + "id=" + id + ", sigla=" + sigla + ", descricao=" + descricao + '}';
    }

}
