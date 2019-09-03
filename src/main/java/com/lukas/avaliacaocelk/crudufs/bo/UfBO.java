package com.lukas.avaliacaocelk.crudufs.bo;

import com.lukas.avaliacaocelk.crudufs.dao.UfDAO;
import com.lukas.avaliacaocelk.crudufs.model.UF;
import java.util.List;

/**
 *
 * @author lukas
 */
public class UfBO {

    public Long incluiUf(UF uf) throws Exception {
        if (uf.getSigla() == null || uf.getSigla().isEmpty()
                || uf.getDescricao() == null || uf.getDescricao().isEmpty()) {
            throw new Exception("Os campos Sigla e Descrição são obrigatórios!");
        }

        UfDAO ufDAO = new UfDAO();

        return ufDAO.incluiUf(uf);
    }

    public void alteraUf(UF uf) throws Exception {
        if (uf.getSigla() == null || uf.getSigla().isEmpty()
                || uf.getDescricao() == null || uf.getDescricao().isEmpty()) {
            throw new Exception("Os campos Sigla e Descrição são obrigatórios!");
        }

        UfDAO ufDAO = new UfDAO();

        ufDAO.alteraUf(uf);
    }

    public void excluiUf(Long id) throws Exception {
        if (id == null) {
            throw new Exception("É necessário informar o ID para a exclusão!");
        }

        UfDAO ufDAO = new UfDAO();

        ufDAO.excluiUf(id);
    }

    public List<UF> listaUfs() {
        UfDAO ufDAO = new UfDAO();

        return ufDAO.listaUFs();
    }

    public UF selecionaUfPorId(Long id) throws Exception {
        if (id == null) {
            throw new Exception("É necessário informar o ID para buscar a UF!");
        }

        UfDAO ufDAO = new UfDAO();

        return ufDAO.selecionaUfPorId(id);
    }

}
