package com.example.lab10;
import com.example.lab10.dtos.PetNameBreedAnimalType;
import com.example.lab10.entities.Household;
import com.example.lab10.entities.Pet;
import com.example.lab10.repositories.PetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback
public class PetRepositoryTests {

    @Autowired
    private PetRepository petRepository;

    @Test
    void testDeleteByNameIgnoreCase() {
        int deletedCount = petRepository.deleteByNameIgnoreCase("buddy");
        assertEquals(1, deletedCount);

        List<Pet> pets = petRepository.findAll();
        System.out.println(pets);
        assertEquals(9, pets.size());
    }

    @Test
    void testFindByAnimalType() {
        List<Pet> dogs = petRepository.findByAnimalType("Dog");
        assertEquals(3, dogs.size());

        for (Pet dog : dogs) {
            assertEquals("Dog", dog.getAnimalType());
        }
    }

    @Test
    void testFindByBreedOrderByAgeAsc() {
        List<Pet> beagles = petRepository.findByBreedOrderByAgeAsc("Beagle");
        assertEquals(1, beagles.size());
        assertEquals("Charlie", beagles.get(0).getName());

        List<Pet> siamese = petRepository.findByBreedOrderByAgeAsc("Siamese");
        assertEquals(1, siamese.size());
        assertEquals("Mittens", siamese.get(0).getName());
    }

    @Test
    void testFindAllPetNameBreedAnimalType() {
        List<PetNameBreedAnimalType> petDetails = petRepository.findAllPetNameBreedAnimalType();
        System.out.println(petDetails);
        assertEquals(9, petDetails.size());

        for (PetNameBreedAnimalType detail : petDetails) {
            assertNotNull(detail.name());
            assertNotNull(detail.animalType());
            assertNotNull(detail.breed());
        }

        PetNameBreedAnimalType firstPet = petDetails.get(0);
        assertEquals("Mittens", firstPet.name());
        assertEquals("Cat", firstPet.animalType());
        assertEquals("Siamese", firstPet.breed());
    }

    @Test
    void testFindAverageAge() {
        Double averageAge = petRepository.findAverageAge();
        assertNotNull(averageAge);
        assertEquals(2.7, averageAge, 0.001);
    }

    @Test
    void testCreatePet() {
        Household household = new Household();
        household.setEircode("D02XY45");
        Pet newPet = new Pet(10, "Bella", "Dog", "Labrador", 4, household);
        Pet savedPet = petRepository.save(newPet);
        assertEquals("Bella", savedPet.getName());
    }

    @Test
    void testReadPetById() {
        Pet foundPet = petRepository.findById(1).orElse(null);
        assertNotNull(foundPet);
        assertEquals("Buddy", foundPet.getName());
    }

    @Test
    void testUpdatePet() {
        Pet pet = petRepository.findById(1).orElse(null);
        assert pet != null;
        pet.setAge(4);
        Pet updatedPet = petRepository.save(pet);
        assertEquals(4, updatedPet.getAge());
    }

    @Test
    void testDeletePetById() {
        petRepository.deleteById(1);
        assertFalse(petRepository.findById(1).isPresent());
    }
}
