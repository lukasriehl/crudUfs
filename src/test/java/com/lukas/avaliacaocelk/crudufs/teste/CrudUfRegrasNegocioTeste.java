package com.lukas.avaliacaocelk.crudufs.teste;

import com.lukas.avaliacaocelk.crudufs.bo.UfBO;
import com.lukas.avaliacaocelk.crudufs.dao.UfDAO;
import com.lukas.avaliacaocelk.crudufs.exception.BDException;
import com.lukas.avaliacaocelk.crudufs.exception.PreenchimentoCamposException;
import com.lukas.avaliacaocelk.crudufs.model.UF;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

/**
 *
 * @author lukas
 */
@RunWith(MockitoJUnitRunner.class)
public class CrudUfRegrasNegocioTeste {

    @InjectMocks
    UfBO ufBO;

    @Mock
    UfDAO ufDAO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = PreenchimentoCamposException.class)
    public void criaUfSemSigla() throws PreenchimentoCamposException, BDException {
        UF uf = new UF();

        uf.setSigla(null);
        uf.setDescricao("teste");

        ufBO.incluiUf(uf);
    }

    @Test(expected = PreenchimentoCamposException.class)
    public void criaUfSemDescricao() throws PreenchimentoCamposException, BDException {
        UF uf = new UF();

        uf.setSigla("SP");
        uf.setDescricao(null);

        ufBO.incluiUf(uf);
    }

    @Test
    public void criaUfComSucesso() {
        try {
            UF uf = new UF(), ufBusca;

            uf.setSigla("TL");
            uf.setDescricao("TESTE LUKAS");

            Long idInclusao = ufBO.incluiUf(uf);

            assertThat(idInclusao, is(notNullValue()));

            PowerMockito.when(ufDAO.selecionaUfPorId(idInclusao)).thenReturn(uf);

            ufBusca = ufDAO.selecionaUfPorId(idInclusao);

            assertThat(ufBusca, is(notNullValue()));
            assertEquals(ufBusca.getSigla(), uf.getSigla());
            assertEquals(ufBusca.getDescricao(), uf.getDescricao());
        } catch (BDException | PreenchimentoCamposException ex) {
            assertEquals(ex, BDException.class);
        }
    }

    @Test
    public void retornaListaUfs() {
        try {
            UF uf = new UF();
            List<UF> listUfsAdicionadas, listUfsBusca;

            uf.setSigla("SP");
            uf.setDescricao("Sao Paulo");

            Long idInclusao = ufBO.incluiUf(uf);

            assertThat(idInclusao, is(notNullValue()));

            listUfsAdicionadas = new ArrayList();

            listUfsAdicionadas.add(uf);

            uf = new UF();

            uf.setSigla("MG");
            uf.setDescricao("Minas Gerais");

            idInclusao = ufBO.incluiUf(uf);

            assertThat(idInclusao, is(notNullValue()));

            listUfsAdicionadas.add(uf);

            //Verificações após a inclusão de UFs
            PowerMockito.when(ufDAO.listaUFs()).thenReturn(listUfsAdicionadas);

            listUfsBusca = ufDAO.listaUFs();

            //Verifica se a lista preenchida através da busca de todas as UFs é igual
            //à lista com as UFs adicionadas anteriormente
            assertThat(listUfsBusca, is(notNullValue()));

            assertEquals(listUfsBusca.size(), listUfsAdicionadas.size());

            Assert.assertEquals(
                    Arrays.asList(listUfsAdicionadas.get(0).getSigla(),
                            listUfsAdicionadas.get(1).getSigla()),
                    Arrays.asList(listUfsBusca.get(0).getSigla(),
                            listUfsBusca.get(1).getSigla()));

            Assert.assertEquals(
                    Arrays.asList(listUfsAdicionadas.get(0).getDescricao(),
                            listUfsAdicionadas.get(1).getDescricao()),
                    Arrays.asList(listUfsBusca.get(0).getDescricao(),
                            listUfsBusca.get(1).getDescricao()));
        } catch (BDException | PreenchimentoCamposException ex) {
            assertThat(ex.getMessage(),
                    containsString("erro ao realizar a transação com o Banco de Dados!"));
        }
    }

    @Test
    public void selecionaUfPorId() {
        try {
            UF uf = new UF(), ufBusca;

            uf.setSigla("PR");
            uf.setDescricao("Paraná");

            Long idInclusao = ufBO.incluiUf(uf);

            assertThat(idInclusao, is(notNullValue()));

            PowerMockito.when(ufDAO.selecionaUfPorId(idInclusao)).thenReturn(uf);

            ufBusca = ufDAO.selecionaUfPorId(idInclusao);

            assertThat(ufBusca, is(notNullValue()));

            /*Verifica se a UF incluida inicialmente é igual à que foi retornada pelo método de busca
            Essa verificação é feita por meio da comparação de Sigla e Descrição.*/
            Assert.assertEquals(
                    Arrays.asList("PR", "Paraná"),
                    Arrays.asList(ufBusca.getSigla(), ufBusca.getDescricao()));
        } catch (BDException | PreenchimentoCamposException ex) {
            assertThat(ex.getMessage(),
                    containsString("erro ao realizar a transação com o Banco de Dados!"));
        }
    }

    @Test(expected = PreenchimentoCamposException.class)
    public void atualizaUfSemSigla() throws PreenchimentoCamposException, BDException {
        UF uf = new UF(), ufBusca;

        uf.setSigla("SC");
        uf.setDescricao("Santa Catarina");

        Long idInclusao = ufBO.incluiUf(uf);

        assertThat(idInclusao, is(notNullValue()));

        PowerMockito.when(ufDAO.selecionaUfPorId(idInclusao)).thenReturn(uf);

        ufBusca = ufDAO.selecionaUfPorId(idInclusao);

        assertThat(ufBusca, is(notNullValue()));

        ufBusca.setSigla(null);

        ufBO.alteraUf(ufBusca);
    }

    @Test(expected = PreenchimentoCamposException.class)
    public void atualizaUfSemDescricao() throws PreenchimentoCamposException, BDException {
        UF uf = new UF(), ufBusca;

        uf.setSigla("SC");
        uf.setDescricao("Santa Catarina");

        Long idInclusao = ufBO.incluiUf(uf);

        assertThat(idInclusao, is(notNullValue()));

        PowerMockito.when(ufDAO.selecionaUfPorId(idInclusao)).thenReturn(uf);

        ufBusca = ufDAO.selecionaUfPorId(idInclusao);

        assertThat(ufBusca, is(notNullValue()));

        ufBusca.setDescricao(null);

        ufBO.alteraUf(ufBusca);
    }

    @Test
    public void atualizaUf() {
        try {
            UF uf = new UF(), ufBusca;

            uf.setSigla("SC");
            uf.setDescricao("Santa Catarina");

            Long idInclusao = ufBO.incluiUf(uf);

            assertThat(idInclusao, is(notNullValue()));

            PowerMockito.when(ufDAO.selecionaUfPorId(idInclusao)).thenReturn(uf);

            ufBusca = ufDAO.selecionaUfPorId(idInclusao);

            assertThat(ufBusca, is(notNullValue()));

            Assert.assertEquals(
                    Arrays.asList("SC", "Santa Catarina"),
                    Arrays.asList(ufBusca.getSigla(), ufBusca.getDescricao()));

            //Atualiza apenas a descriçao
            ufBusca.setDescricao("St. Catarina");

            ufBO.alteraUf(ufBusca);

            //Verifica se a Uf foi de fato atualizada, com base na Descrição.
            assertTrue(ufBusca.getDescricao().equals("St. Catarina"));

            assertFalse(ufBusca.getDescricao().equals("Santa Catarina"));
        } catch (BDException | PreenchimentoCamposException ex) {
            assertThat(ex.getMessage(), containsString("erro ao realizar a transação com o Banco de Dados!"));
        }
    }

    @Test(expected = PreenchimentoCamposException.class)
    public void excluiUfInexistente() throws PreenchimentoCamposException, BDException {
        UF ufBusca = ufDAO.selecionaUfPorId(99999L);

        assertThat(ufBusca, is(nullValue()));

        //Tenta realizar a exclusão ao informar um ID não existente.
        ufBO.excluiUf(99999L);
    }

    @Test
    public void excluiUf() {
        UF uf, ufBusca;

        try {
            uf = new UF();

            uf.setSigla("AL");
            uf.setDescricao("Alagoas");

            //Insere uma UF para posterior exclusão
            Long idInclusao = ufBO.incluiUf(uf);

            assertThat(idInclusao, is(notNullValue()));

            PowerMockito.when(ufDAO.selecionaUfPorId(idInclusao)).thenReturn(uf);

            ufBusca = ufDAO.selecionaUfPorId(idInclusao);

            assertThat(ufBusca, is(notNullValue()));

            //Exclui a UF que foi inserida no início deste método
            ufBO.excluiUf(idInclusao);

            //A verificação de que a UF foi de fato excluida é feita 
            PowerMockito.when(ufDAO.selecionaUfPorId(idInclusao)).thenReturn(null);

            ufBusca = ufDAO.selecionaUfPorId(idInclusao);

            assertThat(ufBusca, is(nullValue()));
        } catch (PreenchimentoCamposException | BDException ex) {
            assertEquals(ex, PreenchimentoCamposException.class);
            assertThat(ex.getMessage(), containsString("não encontrada para Exclusão!"));
        }
    }
}
