package com.example.lab10.services;

import com.example.lab10.dtos.PetNameBreedAnimalType;
import com.example.lab10.dtos.PetStatistics;
import com.example.lab10.entities.Pet;
import com.example.lab10.exceptions.NotFoundException;

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
    PetStatistics getPetStatistics();
    List<Pet> getPetsByAnimalType(String animalType);
}
