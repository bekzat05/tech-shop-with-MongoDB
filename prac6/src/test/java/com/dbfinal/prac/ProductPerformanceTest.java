package com.dbfinal.prac;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(classes = {TestConfig.class})
@AutoConfigureMockMvc
public class ProductPerformanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoadAllProductsPerformance() throws Exception {

        // Define the number of iterations (e.g., simulate 100 requests)
        int numberOfRequests = 100;

        // Start timing
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfRequests; i++) {
            mockMvc.perform(get("/api/products")) // Adjust the endpoint path as needed
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        }

        // End timing
        long endTime = System.currentTimeMillis();

        // Calculate and print the average time per request
        long totalTime = endTime - startTime;
        double averageTime = (double) totalTime / numberOfRequests;

        System.out.println("Total time for " + numberOfRequests + " requests: " + totalTime + " ms");
        System.out.println("Average time per request: " + averageTime + " ms");
    }
}
