package com.lukas.avaliacaocelk.crudufs;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author lukas
 */
@ApplicationPath("rest")
public class MyApp extends ResourceConfig {

    public MyApp() {
        packages("com.lukas.avaliacaocelk.crudufs.controller");
    }
}
