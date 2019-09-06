package com.lukas.avaliacaocelk.crudufs.utils;

import com.lukas.avaliacaocelk.crudufs.model.UF;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Classe utilitária do Hibernate
 *
 * @author lukas
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Map<String, String> jdbcUrlSettings = new HashMap();

            String jdbcDbUrl = System.getenv("JDBC_DATABASE_URL");
            String jdbcDbUser = System.getenv("JDBC_DATABASE_USERNAME");
            String jdbcDbPassword = System.getenv("JDBC_DATABASE_PASSWORD");

            if (jdbcDbUrl != null && !jdbcDbUrl.isEmpty()) {
                jdbcUrlSettings.put("hibernate.connection.url", jdbcDbUrl);
            }

            if (jdbcDbUser != null && !jdbcDbUser.isEmpty()) {
                jdbcUrlSettings.put("hibernate.connection.hibernate.connection.username",
                        jdbcDbUser);
            }

            if (jdbcDbPassword != null && !jdbcDbPassword.isEmpty()) {
                jdbcUrlSettings.put("hibernate.connection.hibernate.connection.password",
                        jdbcDbPassword);
            }

            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().
                    configure().
                    applySettings(jdbcUrlSettings).
                    build();

            // Criaçao do Session Factory, baseado nos dados do registrador
            sessionFactory = new MetadataSources(standardRegistry).addAnnotatedClass(UF.class)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (HibernateException ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
