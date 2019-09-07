package com.lukas.avaliacaocelk.crudufs.controller;

import com.lukas.avaliacaocelk.crudufs.bo.UfBO;
import com.lukas.avaliacaocelk.crudufs.exception.BDException;
import com.lukas.avaliacaocelk.crudufs.exception.PreenchimentoCamposException;
import com.lukas.avaliacaocelk.crudufs.model.UF;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author lukas
 */
@Path("ufs")
public class UfController {

    private UfBO ufBO;

    public UfController() {
        this.ufBO = new UfBO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<UF> listaUFs() {
        try {
            return this.ufBO.listaUfs();
        } catch (BDException ex) {
            Logger.getLogger(UfController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public UF getUF(@PathParam("id") Long id) {
        try {
            return this.ufBO.selecionaUfPorId(id);
        } catch (BDException | PreenchimentoCamposException ex) {
            Logger.getLogger(UfController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response criaUF(UF uf) {
        try {
            this.ufBO.incluiUf(uf);

            return Response.status(Response.Status.OK).build();
        } catch (BDException | PreenchimentoCamposException ex) {
            Logger.getLogger(UfController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public Response atualizaUF(UF uf, @PathParam("id") Long id) {
        try {
            uf.setId(id);

            this.ufBO.alteraUf(uf);

            return Response.status(Response.Status.OK).build();
        } catch (BDException | PreenchimentoCamposException ex) {
            Logger.getLogger(UfController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response deletaUF(@PathParam("id") Long id) {
        try {
            this.ufBO.excluiUf(id);

            return Response.status(Response.Status.OK).build();
        } catch (BDException | PreenchimentoCamposException ex) {
            Logger.getLogger(UfController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}