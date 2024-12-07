package com.example.lab5;

import com.example.lab5.dtos.PetNameBreedAnimalType;
import com.example.lab5.dto.PetStatistics;
import com.example.lab5.entities.Pet;
import com.example.lab5.services.PetService;
import com.example.lab5.services.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@Rollback
public class PetServiceIntegrationTest {

    @Autowired
    private PetService petService;

    @Test
    void testCreatePet() {
        Pet newPet = new Pet("Bella", "Dog", "Labrador", 4);
        Pet createdPet = petService.createPet(newPet);

        assertEquals("Bella", createdPet.getName());
    }

    @Test
    void testGetAllPets() {
        List<Pet> pets = petService.getAllPets();
        assertEquals(10, pets.size());
    }

    @Test
    void testGetPetById_Found() throws NotFoundException {
        Pet foundPet = petService.getPetById(1);
        assertNotNull(foundPet);
        assertEquals("Maximus", foundPet.getName());
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
        Pet updatedDetails = new Pet( "Maximus", "Dog", "Beagle", 4);
        Pet updatedPet = petService.updatePet(1, updatedDetails);

        assertEquals("Maximus", updatedPet.getName());
        assertEquals(4, updatedPet.getAge());
    }

    @Test
    void testUpdatePet_NotFound() {
        Pet updatedDetails = new Pet( "Ghost", "Dog", "Bulldog", 5);
        Exception exception = assertThrows(NotFoundException.class, () -> {
            petService.updatePet(999, updatedDetails);
        });

        assertEquals("Pet not found with id: 999", exception.getMessage());
    }

    @Test
    void testDeletePetById_Found() throws NotFoundException {
        petService.deletePetById(1);

        Exception exception = assertThrows(NotFoundException.class, () -> {
            petService.getPetById(1);
        });

        assertEquals("Pet not found with id: " + 1, exception.getMessage());
    }

    @Test
    void testDeletePetById_NotFound() {
        Exception exception = assertThrows(NotFoundException.class, () -> {
            petService.deletePetById(999);
        });

        assertEquals("Pet not found with id: 999", exception.getMessage());
    }

    @Test
    void testDeletePetsByName() {
        int deletedCount = petService.deletePetsByName("Nibbles");
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
    void testFindPetsByBreedOrderByAge() {
        petService.createPet(new Pet( "Oscar", "Dog", "Beagle", 2));
        petService.createPet(new Pet( "Rocky", "Dog", "Beagle", 1));
        List<Pet> beagles = petService.findPetsByBreedOrderByAge("Beagle");
        assertEquals(4, beagles.size());

        assertTrue(beagles.get(0).getAge() <= beagles.get(1).getAge());
        assertTrue(beagles.get(1).getAge() <= beagles.get(2).getAge());
        assertTrue(beagles.get(2).getAge() <= beagles.get(3).getAge());
    }

    @Test
    void testGetPetNameBreedAnimalType() {
        List<PetNameBreedAnimalType> petDetails = petService.getPetNameBreedAnimalType();
        assertEquals(10, petDetails.size());

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
        assertEquals(2.8, stats.getAverageAge());
        assertEquals(10, stats.getTotalCount());
    }
}
