
import com.lukas.avaliacaocelk.crudufs.bo.UfBO;
import com.lukas.avaliacaocelk.crudufs.model.UF;
import io.restassured.RestAssured;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;

/**
 *
 * @author lukas
 */
public class UfControllerTeste {

    @Mock
    UfBO ufBO;

    @BeforeClass
    public void setup() {
        RestAssured.basePath = "/rest";
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = Exception.class)
    public void criaUfSemSigla() throws Exception {
        UF uf = new UF();

        uf.setSigla(null);
        uf.setDescricao("São Paulo");
        
        Long idInclusao = ufBO.incluiUf(uf);
        
        assertThat(idInclusao, is(nullValue()));
    }
    
    @Test(expected = Exception.class)
    public void criaUfSemDescricao() throws Exception {
        UF uf = new UF();

        uf.setSigla("SP");
        uf.setDescricao(null);        
        
        Long idInclusao = ufBO.incluiUf(uf);
        
        assertThat(idInclusao, is(nullValue()));
    }
    
    @Test
    public void criaUfComSucesso() throws Exception{
        UF uf = new UF();

        uf.setSigla("SP");
        uf.setDescricao("Sao Paulo");
        
        Long idInclusao = ufBO.incluiUf(uf);
        
        assertThat(idInclusao, is(notNullValue()));
        
        assertThat(ufBO.selecionaUfPorId(idInclusao), is(notNullValue()));
        
        //A Sigla e Descriçao do que eh recuperado pela funcao selecionaUfPorId
        //devem ser iguais a do objeto instanciado no inicio
        assertEquals(ufBO.selecionaUfPorId(idInclusao).getSigla(), uf.getSigla());
        assertEquals(ufBO.selecionaUfPorId(idInclusao).getDescricao(), uf.getDescricao());        
    }


    @Test
    public void invocaRestCriaUF() {
        UF uf = new UF();

        uf.setSigla("SP");
        uf.setDescricao("São Paulo");

        RestAssured.given()
                .contentType("application/json")
                .body(uf)
                .when().post("/ufs/").then().statusCode(200);
    }
    
    //TODO: CRIAR OS METODOS PARA VERIFICAR:
    
    //1 - CRIAR UMA LISTA DE UFS E SALVA-LAS. DEPOIS DISSO SELECIONAR TODAS PELA FUNCAO DO BO
    //VERIFICAR SE OS ELEMENTOS SAO OS MESMOS
    //2 - CHAMAR O REST PARA LISTAR AS UFS. VERIFICAR SE O CODIGO DE SAIDA SERA 200
    //3 - CRIAR METODO PARA VERIFICAR SE NA ALTERACAO SERA PREENCHIDA A DATA DE MODIFICACAO
    //TAMBEM INSERIR E EM SEGUIDA ALTERAR UMA UF. VERIFICAR SE A SIGLA OU DESCRICAO ALTERADAS 
    //FORAM MANTIDAS
    //4 - CHAMAR O REST PARA ATUALIZAR E VERIFICAR SE O CODIGO DE SAIDA SERA O 200
    //5 - INSERIR UM ELEMENTO E SALVAR. DEPOIS DISSO ASSEGURAR Q ELE EXISTE, POR MEIO DA BUSCA POR ID.
    //DEPOIS DISSO APAGA-LO. ASSEGURAR, POR MEIO DA BUSCA POR ID, DE QUE ELE ESTA NULO, APOS A REMOCAO
    //6 - CHAMAR O REST PARA apagar E VERIFICAR SE O CODIGO DE SAIDA SERA O 200 
}
