package com.example.lab10;

import com.example.lab10.entities.Household;
import com.example.lab10.entities.Pet;
import com.example.lab10.services.HouseholdService;
import com.example.lab10.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class HouseholdServiceIntegrationTest {

    @Autowired
    private HouseholdService householdService;

    @Test
    void testFindHouseholdByEircode_Found() throws NotFoundException {
        Household household = householdService.findHouseholdByEircode("D02XY45");
        assertNotNull(household);
        assertEquals("D02XY45", household.getEircode());
    }

    @Test
    void testFindHouseholdByEircode_NotFound() {
        Exception exception = assertThrows(NotFoundException.class, () -> {
            householdService.findHouseholdByEircode("INVALID123");
        });
        assertEquals("Household not found with eircode INVALID123", exception.getMessage());
    }

    @Test
    void testFindHouseholdWithPetsByEircode_Found() throws NotFoundException {
        Household household = householdService.findHouseholdWithPetsByEircode("D02XY45");
        assertNotNull(household);
        assertNotNull(household.getPets());
        assertFalse(household.getPets().isEmpty());
    }

    @Test
    void testFindHouseholdWithPetsByEircode_NotFound() {
        Exception exception = assertThrows(NotFoundException.class, () -> {
            householdService.findHouseholdWithPetsByEircode("INVALID123");
        });
        assertEquals("Household containing pets not found with eircode INVALID123", exception.getMessage());
    }

    @Test
    void testFindHouseholdsWithNoPets() {
        List<Household> households = householdService.findHouseholdsWithNoPets();
        assertNotNull(households);
        assertFalse(households.isEmpty());

        for (Household household : households) {
            List<Pet> pets = household.getPets();
            assertTrue(pets == null || pets.isEmpty());
        }
    }

    @Test
    void testSaveHousehold_Success() {
        Household newHousehold = new Household("N12OP34", 2, 4, true, null);
        Household savedHousehold = householdService.saveHousehold(newHousehold);

        assertNotNull(savedHousehold);
        assertEquals("N12OP34", savedHousehold.getEircode());
        assertEquals(2, savedHousehold.getNumberOfOccupants());
    }

    @Test
    void testDeleteHousehold_Success() throws NotFoundException {
        householdService.deleteHousehold("S23EF45");

        Exception exception = assertThrows(NotFoundException.class, () -> {
            householdService.findHouseholdByEircode("S23EF45");
        });
        assertEquals("Household not found with eircode S23EF45", exception.getMessage());
    }

    @Test
    void testDeleteHousehold_NotFound() {
        assertDoesNotThrow(() -> householdService.deleteHousehold("INVALID123"));
    }
}
