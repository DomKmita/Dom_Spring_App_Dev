package com.example.lab6.services;

import com.example.lab6.dtos.PetNameBreedAnimalType;
import com.example.lab6.entities.Pet;
import com.example.lab6.services.exceptions.NotFoundException;

import java.util.List;

public interface PetService {
    Pet createPet(Pet pet);
    List<Pet> getAllPets();
    Pet getPetById(Integer id) throws NotFoundException;
    Pet updatePet(Integer id, Pet petDetails) throws NotFoundException;
    void deletePetById(Integer id) throws NotFoundException;
    int deletePetsByName(String name);
    List<Pet> findPetsByAnimalType(String animalType);
    List<Pet> findPetsByBreedOrderByAge(String breed);
    List<PetNameBreedAnimalType> getPetNameBreedAnimalType();
    com.example.lab6.dto.PetStatistics getPetStatistics();
}
