package org.params.sort;

import org.junit.jupiter.api.Test;
import org.params.sort.Afternoon.Knapsack01DynamicProgramming;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Knapsack01DynamicProgrammingTest {

    @Test
    void shouldFindTheOptimalValueAndItems() {
        Knapsack01DynamicProgramming.Result result = new Knapsack01DynamicProgramming()
                .solve(new int[]{2, 3, 4, 5}, new int[]{3, 4, 5, 6}, 8);

        assertEquals(10, result.maxValue());
        assertEquals(List.of(1, 3), result.selectedItems());
    }
}
