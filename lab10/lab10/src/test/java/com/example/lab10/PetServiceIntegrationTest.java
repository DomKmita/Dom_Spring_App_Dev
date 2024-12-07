package com.example.lab10;

import com.example.lab10.dtos.PetNameBreedAnimalType;
import com.example.lab10.dtos.PetStatistics;
import com.example.lab10.entities.Household;
import com.example.lab10.entities.Pet;
import com.example.lab10.services.HouseholdService;
import com.example.lab10.services.PetService;
import com.example.lab10.exceptions.NotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@Rollback
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetServiceIntegrationTest {

    @Autowired
    private PetService petService;

    @Autowired
    private HouseholdService householdService;

    @Test
    void testCreatePet() {
        Household household = new Household();
        household.setEircode("D02XY45");
        Pet newPet = new Pet("Bella", "Dog", "Labrador", 4, household);
        Pet createdPet = petService.createPet(newPet);

        assertEquals("Bella", createdPet.getName());
    }

    @Test
    void testGetAllPets() {
        List<Pet> pets = petService.getAllPets();
        assertEquals(9, pets.size());
    }

    @Test
    void testGetPetById_Found() throws NotFoundException {
        Pet foundPet = petService.getPetById(2);
        assertNotNull(foundPet);
        assertEquals("Mittens", foundPet.getName());
    }

    @Test
    void testGetPetById_NotFound() {
        Exception exception = assertThrows(NotFoundException.class, () -> {
            petService.getPetById(999);
        });

        assertEquals("Pet not found with id: 999", exception.getMessage());
    }

    @Test
    void testUpdatePet() throws NotFoundException {
        Household household = new Household();
        household.setEircode("T12XXXX");
        Pet updatedDetails = new Pet("Maximus", "Dog", "Beagle", 4, household);
        Pet updatedPet = petService.updatePet(3, updatedDetails);

        assertEquals("Maximus", updatedPet.getName());
        assertEquals(4, updatedPet.getAge());
    }

    @Test
    void testUpdatePet_NotFound() {
        Household household = new Household();
        household.setEircode("T12XXXX");
        Pet updatedDetails = new Pet("Ghost", "Dog", "Bulldog", 5, household);
        Exception exception = assertThrows(NotFoundException.class, () -> {
            petService.updatePet(999, updatedDetails);
        });

        assertEquals("Pet not found with id: 999", exception.getMessage());
    }

    @Test
    void testDeletePetById_Found() throws NotFoundException {
        petService.deletePetById(2);

        Exception exception = assertThrows(NotFoundException.class, () -> {
            petService.getPetById(2);
        });

        assertEquals("Pet not found with id: " + 2, exception.getMessage());
    }

    @Test
    void testDeletePetById_NotFound() {
        Exception exception = assertThrows(NotFoundException.class, () -> {
            petService.deletePetById(999);
        });

        assertEquals("Pet not found with id: 999", exception.getMessage());
    }

    @Order(2)
    @Test
    void testDeletePetsByName() {
        int deletedCount = petService.deletePetsByName("buddy");
        assertEquals(1, deletedCount);

        List<Pet> pets = petService.getAllPets();
        assertEquals(9, pets.size());
    }

    @Test
    void testFindPetsByAnimalType() {
        List<Pet> cats = petService.findPetsByAnimalType("Cat");
        assertEquals(3, cats.size());

        for (Pet cat : cats) {
            assertEquals("Cat", cat.getAnimalType());
        }
    }

    @Test
    void testFindPetsByBreedOrderByAge() throws NotFoundException {
        Household household = householdService.findHouseholdByEircode("D02XY45");
        Pet dog1 = petService.createPet(new Pet("Oscar", "Dog", "Beagle", 2, household));
        Pet dog2 = petService.createPet(new Pet("Rocky", "Dog", "Beagle", 1, household));

        List<Pet> beagles = petService.findPetsByBreedOrderByAge("Beagle");
        assertEquals(3, beagles.size());

        assertTrue(beagles.get(0).getAge() <= beagles.get(1).getAge());
        assertTrue(beagles.get(1).getAge() <= beagles.get(2).getAge());
    }

    @Test
    void testGetPetNameBreedAnimalType() {
        List<PetNameBreedAnimalType> petDetails = petService.getPetNameBreedAnimalType();
        assertEquals(9, petDetails.size());

        for (PetNameBreedAnimalType detail : petDetails) {
            assertNotNull(detail.name());
            assertNotNull(detail.animalType());
            assertNotNull(detail.breed());
        }
    }

    @Test
    void testGetPetStatistics() {
        PetStatistics stats = petService.getPetStatistics();
        assertNotNull(stats);
        assertEquals(2.67, stats.getAverageAge());
        assertEquals(9, stats.getTotalCount());
    }
}
