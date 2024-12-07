package com.example.lab10.services;

import com.example.lab10.dtos.PetNameBreedAnimalType;
import com.example.lab10.dtos.PetStatistics;
import com.example.lab10.entities.Pet;
import com.example.lab10.exceptions.NotFoundException;
import com.example.lab10.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet getPetById(Integer id) throws NotFoundException {
        return petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet not found with id: " + id));
    }

    @Override
    public Pet updatePet(Integer id, Pet petDetails) throws NotFoundException {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet not found with id: " + id));

        existingPet.setName(petDetails.getName());
        existingPet.setAnimalType(petDetails.getAnimalType());
        existingPet.setBreed(petDetails.getBreed());
        existingPet.setAge(petDetails.getAge());

        return petRepository.save(existingPet);
    }

    @Override
    public void deletePetById(Integer id) throws NotFoundException {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet not found with id: " + id);
        }
        petRepository.deleteById(id);
    }

    @Override
    public int deletePetsByName(String name) {
        return petRepository.deleteByNameIgnoreCase(name);
    }

    @Override
    public List<Pet> findPetsByAnimalType(String animalType) {
        return petRepository.findByAnimalType(animalType);
    }

    @Override
    public List<Pet> findPetsByBreedOrderByAge(String breed) {
        return petRepository.findByBreedOrderByAgeAsc(breed);
    }

    @Override
    public List<PetNameBreedAnimalType> getPetNameBreedAnimalType() {
        return petRepository.findAllPetNameBreedAnimalType();
    }
    @Override
    public PetStatistics getPetStatistics() {
        double averageAge = BigDecimal.valueOf(petRepository.findAverageAge()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        int totalCount = Math.toIntExact(petRepository.count());

        return new PetStatistics(
                averageAge,
                totalCount
        );
    }

    @Override
    public List<Pet> getPetsByAnimalType(String animalType) {
        return petRepository.findByAnimalType(animalType);
    }
}
