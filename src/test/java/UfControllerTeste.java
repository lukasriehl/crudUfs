
import com.lukas.avaliacaocelk.crudufs.bo.UfBO;
import com.lukas.avaliacaocelk.crudufs.model.UF;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

/**
 *
 * @author lukas
 */
public class UfControllerTeste {
    
    @Mock
    UfBO ufBO;

    @BeforeClass
    public static void setup() {
        RestAssured.basePath = "/rest";
    }

    @Test
    public void criaUF() {
        UF uf = new UF();
       
        uf.setSigla("SP");
        uf.setDescricao("São Paulo");

        RestAssured.given()
                .contentType("application/json")
                .body(uf)
                .when().post("/ufs").then().statusCode(200);
        
    }
}
