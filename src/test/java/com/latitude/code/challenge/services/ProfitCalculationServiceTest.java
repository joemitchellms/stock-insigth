package com.latitude.code.challenge.services;

import com.latitude.code.challenge.exceptions.ProfitCalculationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProfitCalculationService.class)
@TestPropertySource(value = "classpath:stock-insights-test.properties")
public class ProfitCalculationServiceTest {

    @Autowired
    ProfitCalculationService profitCalculationService;

    @DisplayName("Test getMaxProfit - should raise exception for empty calls")
    @Test
    public void shouldRaiseAnException() throws ProfitCalculationException {

        ProfitCalculationException thrown = assertThrows(ProfitCalculationException.class, () -> {
            this.profitCalculationService.getMaxProfit(new int[]{});
        });
        assertEquals("TestMSG - Stock price list cannot be empty", thrown.getMessage());
    }

    @DisplayName("Test getMaxProfit - using a good payload")
    @Test
    public void getMaxProfitGoodPayload() throws ProfitCalculationException {

        int result = this.profitCalculationService.getMaxProfit(new int[]{10, 7, 5, 8, 11, 9});
        assertEquals(6, result);
    }
}