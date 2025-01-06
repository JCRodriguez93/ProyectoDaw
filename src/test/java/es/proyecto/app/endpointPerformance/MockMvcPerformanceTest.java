package es.proyecto.app.endpointPerformance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcPerformanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEndpointPerformance() throws Exception {
        long startTime = System.currentTimeMillis();

        mockMvc.perform(get("/Users"))
                .andExpect(status().isOk());

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Tiempo de respuesta: " + duration + " ms");
        assertTrue(duration < 2000, "El tiempo de respuesta debe ser menor a 2000 ms");
    }
}
