package com.latitude.code.challenge.services;

import com.latitude.code.challenge.exceptions.ProfitCalculationException;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:stock-insights.properties")
public class ProfitCalculationService {

    @Value("${exception-message.profit-calculation-service.price-list-parameter-empty}")
    private String exceptionMessagePriceListIsEmpty;

    /**
     * Function to find the maximum profit
     * @param price the list of dollar values representing price/minute
     * @return the maximum profit using past data
     */
    int getMaxProfit(int[] price) throws ProfitCalculationException {

        if(ArrayUtils.isEmpty(price)){
            throw new ProfitCalculationException(exceptionMessagePriceListIsEmpty);
        }

        int maxProfit = -1;
        int currentPrice = 0;
        int futurePrice;

        // Flag to signalise if the next stock should be bought
        boolean buyNextStock = true;

        // loop through list of stock prices once
        for (int i = 0; i < price.length-1; i++) {

            // selling price is the next element in list
            futurePrice = price[i+1];

            // if we have not found a suitable cheap buying price yet
            // we set the buying price equal to the current element
            if (buyNextStock) { currentPrice = price[i]; }

            if (futurePrice < currentPrice) {
                // if the futurePrice is less than the currentPrice
                // we know we cannot make a profit so we continue to the
                // next element in the list which will be the new buying price
                buyNextStock = true;
            } else {
                // if the selling price is greater than the buying price
                // we check to see if these two indices give us a better
                // profit then what we currently have
                int tempProfit = futurePrice - currentPrice;
                if (tempProfit > maxProfit) { maxProfit = tempProfit; }
                buyNextStock = false;
            }

        }

        return maxProfit;
    }
}
