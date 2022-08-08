package org.lunic.observer;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ItemExpirationObserverTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getDaysBetween_OverDueDate() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(2);
        long days = ObserverUtils.getDaysBetween(today, tomorrow);
        assertEquals(-2, days);
    }

    @Test
    void getSoonExpiringItems() {
    }
}