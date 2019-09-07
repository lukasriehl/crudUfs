package com.lukas.avaliacaocelk.crudufs.dao;

import com.lukas.avaliacaocelk.crudufs.exception.BDException;
import com.lukas.avaliacaocelk.crudufs.exception.PreenchimentoCamposException;
import com.lukas.avaliacaocelk.crudufs.model.UF;
import com.lukas.avaliacaocelk.crudufs.utils.HibernateUtil;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.RollbackException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author lukas
 */
public class UfDAO {

    public Long incluiUf(UF uf) throws BDException {
        try {
            uf.setDataCriacao(LocalDateTime.now());

            Session s = HibernateUtil.getSessionFactory().openSession();

            Transaction t = s.beginTransaction();

            s.save(uf);

            t.commit();

            return uf.getId();
        } catch (IllegalStateException | RollbackException | HibernateException ex1) {
            throw new BDException("Ocorreu um erro ao realizar a transação com o Banco de Dados!"
                    .concat(ex1.getMessage() != null && !ex1.getMessage().isEmpty()
                            ? "Erro: ".concat(ex1.getMessage()) : ""));
        }
    }

    public void alteraUf(UF uf) throws BDException {
        try {
            uf.setDataModificacao(LocalDateTime.now());

            Session s = HibernateUtil.getSessionFactory().openSession();

            Transaction t = s.beginTransaction();

            s.merge(uf);

            t.commit();
        } catch (IllegalStateException | RollbackException | HibernateException ex1) {
            throw new BDException("Ocorreu um erro ao realizar a transação com o Banco de Dados!"
                    .concat(ex1.getMessage() != null && !ex1.getMessage().isEmpty()
                            ? "Erro: ".concat(ex1.getMessage()) : ""));
        }
    }

    public void excluiUf(Long id) throws BDException, PreenchimentoCamposException {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();

            UF c = selecionaUfPorId(id);

            if (c == null) {
                throw new PreenchimentoCamposException("UF de ID " + id + " não encontrada para Exclusão!");
            }

            Transaction t = s.beginTransaction();

            s.delete(c);

            t.commit();
        } catch (PreenchimentoCamposException ex) {
            throw ex;
        } catch (IllegalStateException | RollbackException | HibernateException ex1) {
            throw new BDException("Ocorreu um erro ao realizar a transação com o Banco de Dados!"
                    .concat(ex1.getMessage() != null && !ex1.getMessage().isEmpty()
                            ? "Erro: ".concat(ex1.getMessage()) : ""));
        }
    }

    public UF selecionaUfPorId(long id) throws BDException {
        try {
            return (UF) HibernateUtil.getSessionFactory()
                    .openSession()
                    .get(UF.class, id);
        } catch (IllegalStateException | RollbackException | HibernateException ex1) {
            throw new BDException("Ocorreu um erro ao realizar a transação com o Banco de Dados!"
                    .concat(ex1.getMessage() != null && !ex1.getMessage().isEmpty()
                            ? "Erro: ".concat(ex1.getMessage()) : ""));
        }
    }

    public UF selecionaUfPorSigla(String sigla) throws BDException {
        try {
            return (UF) HibernateUtil.getSessionFactory()
                    .openSession()
                    .createQuery("from UF where sigla LIKE :sigla")
                    .setParameter("sigla", sigla)
                    .uniqueResult();
        } catch (IllegalStateException | RollbackException | HibernateException ex1) {
            throw new BDException("Ocorreu um erro ao realizar a transação com o Banco de Dados!"
                    .concat(ex1.getMessage() != null && !ex1.getMessage().isEmpty()
                            ? "Erro: ".concat(ex1.getMessage()) : ""));
        }
    }

    public List<UF> listaUFs() throws BDException {
        try {
            return (List<UF>) HibernateUtil.getSessionFactory()
                    .openSession()
                    .createQuery("from UF")
                    .list();
        } catch (IllegalStateException | RollbackException | HibernateException ex1) {
            throw new BDException("Ocorreu um erro ao realizar a transação com o Banco de Dados!"
                    .concat(ex1.getMessage() != null && !ex1.getMessage().isEmpty()
                            ? "Erro: ".concat(ex1.getMessage()) : ""));
        }
    }

}
