package org.lunic.data;

import org.junit.jupiter.api.Test;
import org.lunic.data.type.ItemType;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testEquals() {
        Item testItem1 = new ItemBuilder().setName("TestItem").setAmount(1).setType(ItemType.CONSUMABLE).setExpirationDate(LocalDate.of(1,1,1)).setConsumptionDate(LocalDate.of(1,1,1)).setTags(new HashSet<>()).build();
        Item testItem2 = new ItemBuilder().setName("TestItem2").setAmount(1).setType(ItemType.CONSUMABLE).setExpirationDate(LocalDate.of(1,1,1)).setConsumptionDate(LocalDate.of(1,1,1)).setTags(new HashSet<>()).build();
        Item testItem3 = new ItemBuilder().setName("TestItem").setAmount(1).setType(ItemType.CONSUMABLE).setExpirationDate(LocalDate.of(2,2,2)).setConsumptionDate(LocalDate.of(2,2,2)).setTags(new HashSet<>()).build();

        assertEquals(testItem1, testItem3);
        assertNotEquals(testItem1, testItem2);
    }
}