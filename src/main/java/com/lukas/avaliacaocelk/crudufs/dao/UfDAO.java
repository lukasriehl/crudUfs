package com.lukas.avaliacaocelk.crudufs.dao;

import com.lukas.avaliacaocelk.crudufs.model.UF;
import com.lukas.avaliacaocelk.crudufs.utils.HibernateUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author lukas
 */
public class UfDAO {

    public Long incluiUf(UF uf) throws Exception {
        uf.setDataCriacao(LocalDateTime.now());

        Session s = HibernateUtil.getSessionFactory().openSession();

        Transaction t = s.beginTransaction();

        s.save(uf);

        t.commit();

        return uf.getId();
    }

    public void alteraUf(UF uf) throws Exception {
        uf.setDataModificacao(LocalDateTime.now());

        Session s = HibernateUtil.getSessionFactory().openSession();

        Transaction t = s.beginTransaction();

        s.merge(uf);

        t.commit();
    }

    public void excluiUf(Long id) throws Exception {
        Session s = HibernateUtil.getSessionFactory().openSession();

        UF c = selecionaUfPorId(id);

        if (c == null) {
            throw new Exception("UF de ID " + id + " não encontrada para Exclusão!");
        }

        Transaction t = s.beginTransaction();

        s.delete(c);

        t.commit();
    }

    public UF selecionaUfPorId(long id) {
        return (UF) HibernateUtil.getSessionFactory()
                .openSession()
                .get(UF.class, id);
    }

    public UF selecionaUfPorSigla(String sigla) {
        return (UF) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from UF where sigla LIKE :sigla")
                .setParameter("sigla", sigla)
                .uniqueResult();
    }

    public List<UF> listaUFs() {
        return (List<UF>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from UF")
                .list();
    }

}
