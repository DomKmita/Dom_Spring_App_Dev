package com.example.lab7.repositories;
import com.example.lab7.entities.Pet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    @Transactional
    @Modifying
    int deleteByNameIgnoreCase(String name);
    List<Pet> findByAnimalType(String animalType);
    List<Pet> findByBreedOrderByAgeAsc(String breed);
    @Query("SELECT new com.example.lab7.dtos.PetNameBreedAnimalType(p.name, p.animalType, p.breed) FROM Pet p")
    List<com.example.lab7.dtos.PetNameBreedAnimalType> findAllPetNameBreedAnimalType();
    @Query("SELECT AVG(p.age) FROM Pet p")
    Double findAverageAge();
}
