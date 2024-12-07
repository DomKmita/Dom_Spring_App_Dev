package com.example.lab10.repositories;
import com.example.lab10.entities.Pet;
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
    @Query("SELECT new com.example.lab10.dtos.PetNameBreedAnimalType(p.name, p.animalType, p.breed) FROM Pet p")
    List<com.example.lab10.dtos.PetNameBreedAnimalType> findAllPetNameBreedAnimalType();
    @Query("SELECT AVG(p.age) FROM Pet p")
    Double findAverageAge();
}
