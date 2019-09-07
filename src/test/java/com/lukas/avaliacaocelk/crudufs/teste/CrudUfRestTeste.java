package com.lukas.avaliacaocelk.crudufs.teste;

import com.lukas.avaliacaocelk.crudufs.bo.UfBO;
import com.lukas.avaliacaocelk.crudufs.controller.UfController;
import com.lukas.avaliacaocelk.crudufs.exception.BDException;
import com.lukas.avaliacaocelk.crudufs.exception.PreenchimentoCamposException;
import com.lukas.avaliacaocelk.crudufs.model.UF;
import io.restassured.RestAssured;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.powermock.api.mockito.PowerMockito;

/**
 *
 * @author lukas
 */
@RunWith(MockitoJUnitRunner.class)
public class CrudUfRestTeste {

    @InjectMocks
    UfController ufController;

    @Mock
    UfBO ufBO;

    @Before
    public void setup() {
        RestAssured.port = 8080;
        RestAssured.basePath = "/crudUFs/rest";
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void invocaPostCriacaoComUFInvalida() {
        UF uf = new UF();

        /*O objeto uf, passado no corpo da requisição, não apresenta a Sigla preenchida.
        Dessa forma, não será inserido o registro. O código de Status esperado como retorno
        é o 500.*/
        uf.setSigla(null);
        uf.setDescricao("São Paulo");

        RestAssured.given()
                .contentType("application/json")
                .body(uf)
                .when().post("/ufs/").then().statusCode(500);
    }

    @Test
    public void invocaPostCriacaoComUFValida() {
        UF uf = new UF();

        /*O objeto uf, passado no corpo da requisição, apresenta os campos necessários para inserção.
        Dessa forma, será inserido o registro. O código de Status esperado como retorno
        é o 200.*/
        uf.setSigla("AM");
        uf.setDescricao("Amazonas");

        Response res = ufController.criaUF(uf);

        assertEquals(res.getStatus(), 200);

        /*O trecho abaixo usa a biblioteca do Rest Assured.
         Como não há mock, então serão persistidos os dados.
         A inteção de manter estas linhas de código é verificar o funcionamento
         desta biblioteca.*/
        RestAssured.given()
                .contentType("application/json")
                .body(uf)
                .when().post("/ufs/").then().statusCode(200);
    }

    @Test
    public void invocaGetListagemUFs() {
        try {
            List<UF> listUF, listUFRetornada;
            UF uf = new UF();

            uf.setSigla("T1");
            uf.setDescricao("Teste 1");

            ufBO.incluiUf(uf);

            listUF = new ArrayList();

            listUF.add(uf);

            PowerMockito.when(ufBO.listaUfs()).thenReturn(listUF);

            listUFRetornada = ufController.listaUFs();

            Assert.assertEquals(listUF.size(), listUFRetornada.size());

            Assert.assertEquals(
                    Arrays.asList(listUF.get(0).getSigla()),
                    Arrays.asList(listUFRetornada.get(0).getSigla()));

            Assert.assertEquals(
                    Arrays.asList(listUF.get(0).getDescricao()),
                    Arrays.asList(listUFRetornada.get(0).getDescricao()));

            //Faz uma requisição via GET apenas para retornas as UFs existentes
            RestAssured.given()
                    .when().get("/ufs/").then().statusCode(200);
        } catch (BDException | PreenchimentoCamposException ex) {
            assertEquals(ex, BDException.class);
        }
    }

    @Test
    public void invocaPutComUFInvalida() {
        try {
            UF uf = new UF();
            List<UF> listUF, listUFRetornada;

            /*O objeto uf, passado no corpo da requisição, apresenta os campos necessários para inserção.
            Dessa forma, será inserido o registro. O código de Status esperado como retorno
            é o 200.*/
            uf.setId(99999L);
            uf.setSigla("AM");
            uf.setDescricao("Amazonas");

            Response res = ufController.criaUF(uf);

            assertEquals(res.getStatus(), 200);

            listUF = new ArrayList();

            listUF.add(uf);

            //Verificação de inserção correta da UF
            PowerMockito.when(ufBO.listaUfs()).thenReturn(listUF);

            listUFRetornada = ufController.listaUFs();

            Assert.assertEquals(listUF.size(), listUFRetornada.size());

            Assert.assertEquals(
                    Arrays.asList(listUF.get(0).getSigla()),
                    Arrays.asList(listUFRetornada.get(0).getSigla()));

            Assert.assertEquals(
                    Arrays.asList(listUF.get(0).getDescricao()),
                    Arrays.asList(listUFRetornada.get(0).getDescricao()));

            /*Tenta atualizar a UF inserida apagando a sigla. Isso irá ocasionar o status 500
            para o PUT.*/
            uf.setSigla(null);

            RestAssured.given()
                    .contentType("application/json")
                    .body(uf)
                    .when().put("/ufs/99999/").then().statusCode(500);
        } catch (BDException ex) {
            assertEquals(ex, BDException.class);
        }
    }

    @Test
    public void invocaPutComUFValida() {
        try {
            UF uf = new UF();
            List<UF> listUF, listUFRetornada;
            Response res;

            /*O objeto uf, passado no corpo da requisição, apresenta os campos necessários para inserção.
            Dessa forma, será inserido o registro. O código de Status esperado como retorno
            é o 200.*/
            uf.setId(99999L);
            uf.setSigla("AM");
            uf.setDescricao("Amazonas");

            res = ufController.criaUF(uf);

            assertEquals(res.getStatus(), 200);

            listUF = new ArrayList();

            listUF.add(uf);

            //Verificação de inserção correta da UF
            PowerMockito.when(ufBO.listaUfs()).thenReturn(listUF);

            listUFRetornada = ufController.listaUFs();

            Assert.assertEquals(listUF.size(), listUFRetornada.size());

            Assert.assertEquals(
                    Arrays.asList(listUF.get(0).getSigla()),
                    Arrays.asList(listUFRetornada.get(0).getSigla()));

            Assert.assertEquals(
                    Arrays.asList(listUF.get(0).getDescricao()),
                    Arrays.asList(listUFRetornada.get(0).getDescricao()));

            /*Atualiza a UF inserida, mantendo os dados obrigatórios. Isso irá ocasionar o status 200
            para o PUT.*/
            uf.setDescricao("Amazonass");

            res = ufController.atualizaUF(uf, 99999L);

            assertEquals(res.getStatus(), 200);

            listUF = new ArrayList();

            listUF.add(uf);

            PowerMockito.when(ufBO.listaUfs()).thenReturn(listUF);

            listUFRetornada = ufController.listaUFs();

            Assert.assertSame(listUFRetornada.size(), 1);

            Assert.assertEquals("Amazonass", listUFRetornada.get(0).getDescricao());

            /*O trecho abaixo usa a biblioteca do Rest Assured.
             Como não há mock, então serão persistidos os dados.
             A inteção de manter estas linhas de código é verificar o funcionamento
             desta biblioteca.*/
//            RestAssured.given()
//                    .contentType("application/json")
//                    .body(uf)
//                    .when().put("/ufs/1/").then().statusCode(200);
        } catch (BDException ex) {
            assertEquals(ex, BDException.class);
        }
    }

    @Test
    public void invocaDeleteParaUFInexistente() {
        /**
         * Supondo que não exista uma UF de código 99999, então o código
         * retornado pela requisição abaixo deve ser o 500.
         */
        RestAssured.given()
                .when().delete("/ufs/99999/").then().statusCode(500);
    }

    @Test
    public void invocaDeleteParaUFExistente() {
        try {
            UF uf = new UF(), ufBusca;
            Response res;

            uf.setSigla("AL");
            uf.setDescricao("Alagoas");

            //Insere uma UF para posterior exclusão
            res = ufController.criaUF(uf);

            assertEquals(res.getStatus(), 200);

            //Retorna a UF que foi inserida e será excluida
            PowerMockito.when(ufBO.selecionaUfPorId(1L)).thenReturn(uf);

            ufBusca = ufBO.selecionaUfPorId(1L);

            assertThat(ufBusca, is(notNullValue()));

            assertEquals(ufBusca.getSigla(), "AL");

            //Exclui a UF que foi inserida no início deste método
            res = ufController.deletaUF(1L);

            /*A verificação de que a UF foi de fato excluida é feita, iniciando pela verificação do 
            código retornado pela requisição.*/
            assertEquals(res.getStatus(), 200);

            PowerMockito.when(ufBO.selecionaUfPorId(1L)).thenReturn(null);

            ufBusca = ufBO.selecionaUfPorId(1L);

            assertThat(ufBusca, is(nullValue()));

            /*O trecho abaixo usa a biblioteca do Rest Assured.
             Como não há mock, então serão persistidos os dados.
             A inteção de manter estas linhas de código é verificar o funcionamento
             desta biblioteca.*/
//            RestAssured.given()
//                .when().delete("/ufs/1/").then().statusCode(200);
        } catch (BDException | PreenchimentoCamposException ex) {
            assertEquals(ex, BDException.class);
        }
    }
}
