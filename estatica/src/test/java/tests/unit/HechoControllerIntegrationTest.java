package tests.unit;

import ar.edu.utn.frba.dds.EstaticaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest(classes = EstaticaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HechoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Test
    void testObtenerHechosNuevos() {
        String path = "src/test/resources/hechos_con_id.csv";
        String url = "http://localhost:" + port + "/hechos/nuevos?csvPath=" + path;

        ResponseEntity<String> response = rest.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("T1"));
        assertTrue(response.getBody().contains("T2"));
    }

    private void assertEquals(HttpStatus httpStatus, HttpStatusCode statusCode) {
    }
}
