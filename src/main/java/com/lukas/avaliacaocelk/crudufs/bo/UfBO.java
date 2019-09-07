package com.lukas.avaliacaocelk.crudufs.bo;

import com.lukas.avaliacaocelk.crudufs.dao.UfDAO;
import com.lukas.avaliacaocelk.crudufs.exception.BDException;
import com.lukas.avaliacaocelk.crudufs.exception.PreenchimentoCamposException;
import com.lukas.avaliacaocelk.crudufs.model.UF;
import java.util.List;

/**
 *
 * @author lukas
 */
public class UfBO {

    private UfDAO ufDAO;

    public UfBO() {
        this.ufDAO = new UfDAO();
    }

    public Long incluiUf(UF uf) throws PreenchimentoCamposException, BDException {
        if (uf.getSigla() == null || uf.getSigla().isEmpty()
                || uf.getDescricao() == null || uf.getDescricao().isEmpty()) {
            throw new PreenchimentoCamposException("Os campos Sigla e Descrição são obrigatórios!");
        }

        uf.setSigla(uf.getSigla().toUpperCase());

        return this.ufDAO.incluiUf(uf);
    }

    public void alteraUf(UF uf) throws PreenchimentoCamposException, BDException {
        if (uf.getSigla() == null || uf.getSigla().isEmpty()
                || uf.getDescricao() == null || uf.getDescricao().isEmpty()) {
            throw new PreenchimentoCamposException("Os campos Sigla e Descrição são obrigatórios!");
        }

        uf.setSigla(uf.getSigla().toUpperCase());

        this.ufDAO.alteraUf(uf);
    }

    public void excluiUf(Long id) throws PreenchimentoCamposException, BDException {
        if (id == null) {
            throw new PreenchimentoCamposException("É necessário informar o ID para a exclusão!");
        }

        UF ufExclusao = selecionaUfPorId(id);

        if (ufExclusao == null) {
            throw new PreenchimentoCamposException("UF de ID " + id + " não encontrada para Exclusão!");
        }

        this.ufDAO.excluiUf(ufExclusao.getId());
    }

    public List<UF> listaUfs() throws BDException {
        return this.ufDAO.listaUFs();
    }

    public UF selecionaUfPorId(Long id) throws PreenchimentoCamposException, BDException {
        if (id == null) {
            throw new PreenchimentoCamposException("É necessário informar o ID para buscar a UF!");
        }

        return this.ufDAO.selecionaUfPorId(id);
    }

}
