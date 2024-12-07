package com.example.lab10;
import com.example.lab10.entities.Household;
import com.example.lab10.entities.Pet;
import com.example.lab10.repositories.HouseholdRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback
public class HouseholdRepositoryTests {

    @Autowired
    private HouseholdRepository householdRepository;

    @Test
    void testFindWithPetsByEircode_Found() {
        Optional<Household> householdOpt = householdRepository.findWithPetsByEircode("D02XY45");
        assertTrue(householdOpt.isPresent());

        Household household = householdOpt.get();
        assertEquals(3, household.getNumberOfOccupants());
        assertEquals(5, household.getMaxNumberOfOccupants());
        assertTrue(household.isOwnerOccupied());

        assertNotNull(household.getPets());
        assertEquals(3, household.getPets().size());

        Pet pet1 = household.getPets().get(0);
        assertEquals("Buddy", pet1.getName());
        assertEquals("Dog", pet1.getAnimalType());
        assertEquals("Golden Retriever", pet1.getBreed());
        assertEquals(3, pet1.getAge());

        Pet pet2 = household.getPets().get(1);
        assertEquals("Mittens", pet2.getName());
        assertEquals("Cat", pet2.getAnimalType());
        assertEquals("Siamese", pet2.getBreed());
        assertEquals(2, pet2.getAge());

        Pet pet3 = household.getPets().get(2);
        assertEquals("Nibbles", pet3.getName());
        assertEquals("Hamster", pet3.getAnimalType());
        assertEquals("Syrian Hamster", pet3.getBreed());
        assertEquals(1, pet3.getAge());
    }

    @Test
    void testFindWithPetsByEircode_NotFound() {
        Optional<Household> householdOpt = householdRepository.findWithPetsByEircode("NONEXIST");
        assertFalse(householdOpt.isPresent());
    }

    @Test
    void testFindHouseholdsWithNoPets() {
        List<Household> householdsWithNoPets = householdRepository.findHouseholdsWithNoPets();
        assertNotNull(householdsWithNoPets);
        assertEquals(8, householdsWithNoPets.size());
    }

    @Test
    void testSaveHousehold_Success() {
        Household newHousehold = new Household("N12OP34", 2, 4, true, null);
        Household savedHousehold = householdRepository.save(newHousehold);

        assertNotNull(savedHousehold);
        assertEquals("N12OP34", savedHousehold.getEircode());
        assertEquals(2, savedHousehold.getNumberOfOccupants());
        assertEquals(4, savedHousehold.getMaxNumberOfOccupants());
        assertTrue(savedHousehold.isOwnerOccupied());

        Optional<Household> fetchedHousehold = householdRepository.findById("N12OP34");
        assertTrue(fetchedHousehold.isPresent());
    }

    @Test
    void testSaveHousehold_InvalidEircode() {
        Household invalidHousehold = new Household(null, 2, 4, true, null);
        assertThrows(JpaSystemException.class, () -> {
            householdRepository.save(invalidHousehold);
        });
    }
    @Test
    void testUpdateHousehold_Success() {
        Optional<Household> householdOpt = householdRepository.findById("T12AB34");
        assertTrue(householdOpt.isPresent());

        Household household = householdOpt.get();
        household.setNumberOfOccupants(3);
        household.setOwnerOccupied(false);
        household.setMaxNumberOfOccupants(5);

        Household updatedHousehold = householdRepository.save(household);

        assertEquals(3, updatedHousehold.getNumberOfOccupants());
        assertFalse(updatedHousehold.isOwnerOccupied());
        assertEquals(5, updatedHousehold.getMaxNumberOfOccupants());

        Optional<Household> fetchedHousehold = householdRepository.findById("T12AB34");
        assertTrue(fetchedHousehold.isPresent());
        assertEquals(3, fetchedHousehold.get().getNumberOfOccupants());
        assertFalse(fetchedHousehold.get().isOwnerOccupied());
        assertEquals(5, fetchedHousehold.get().getMaxNumberOfOccupants());
    }

    @Test
    void testDeleteHouseholdByEircode_Found() {
        Optional<Household> householdOpt = householdRepository.findById("F12GH89");
        assertTrue(householdOpt.isPresent());

        householdRepository.deleteById("F12GH89");

        householdOpt = householdRepository.findById("F12GH89");
        assertFalse(householdOpt.isPresent());
    }

    @Test
    void testDeleteHouseholdByEircode_NotFound() {
        assertDoesNotThrow(() -> {
            householdRepository.deleteById("NONEXIST");
        });
    }
}
