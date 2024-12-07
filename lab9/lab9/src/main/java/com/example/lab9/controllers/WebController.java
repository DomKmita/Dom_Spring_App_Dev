package com.example.lab9.controllers;

import com.example.lab9.dtos.HouseholdData;
import com.example.lab9.dtos.PetData;
import com.example.lab9.entities.Household;
import com.example.lab9.entities.Pet;
import com.example.lab9.services.HouseholdService;
import com.example.lab9.services.PetService;
import com.example.lab9.exceptions.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class WebController {

    private final HouseholdService householdService;
    private final PetService petService;

    // Get all pets
    @GetMapping("/pets")
    public ResponseEntity<List<PetData>> getAllPets() {
        List<PetData> pets = petService.getAllPets().stream()
                .map(pet -> new PetData(
                        pet.getName(),
                        pet.getAnimalType(),
                        pet.getBreed(),
                        pet.getAge(),
                        pet.getHousehold().getEircode()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(pets);
    }

    // Get all households
    @GetMapping("/households")
    public ResponseEntity<List<HouseholdData>> getAllHouseholds() {
        List<HouseholdData> households = householdService.getAllHouseholds().stream()
                .map(household -> new HouseholdData(
                        household.getEircode(),
                        household.getNumberOfOccupants(),
                        household.getMaxNumberOfOccupants(),
                        household.isOwnerOccupied()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(households);
    }

    // Get households with no pets
    @GetMapping("/households/no-pets")
    public ResponseEntity<List<HouseholdData>> getHouseholdsWithNoPets() {
        List<HouseholdData> households = householdService.findHouseholdsWithNoPets().stream()
                .map(household -> new HouseholdData(
                        household.getEircode(),
                        household.getNumberOfOccupants(),
                        household.getMaxNumberOfOccupants(),
                        household.isOwnerOccupied()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(households);
    }

    // Get a specific pet
    @GetMapping("/pets/{id}")
    public ResponseEntity<PetData> getPetById(@PathVariable int id) throws NotFoundException {
        Pet pet = petService.getPetById(id);
        PetData petData = new PetData(
                pet.getName(),
                pet.getAnimalType(),
                pet.getBreed(),
                pet.getAge(),
                pet.getHousehold().getEircode());
        return ResponseEntity.ok(petData);
    }

    // Get a specific household
    @GetMapping("/households/{eircode}")
    public ResponseEntity<HouseholdData> getHouseholdByEircode(@PathVariable String eircode) throws NotFoundException {
        Household household = householdService.findHouseholdByEircode(eircode);
        HouseholdData householdData = new HouseholdData(
                household.getEircode(),
                household.getNumberOfOccupants(),
                household.getMaxNumberOfOccupants(),
                household.isOwnerOccupied());
        return ResponseEntity.ok(householdData);
    }

    // Delete a pet
    @DeleteMapping("/pets/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable int id) throws NotFoundException {
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }

    // Delete a household
    @DeleteMapping("/households/{eircode}")
    public ResponseEntity<Void> deleteHousehold(@PathVariable String eircode) throws NotFoundException {
        householdService.deleteHousehold(eircode);
        return ResponseEntity.noContent().build();
    }

    // Create a new household
    @PostMapping("/households")
    public ResponseEntity<HouseholdData> createHousehold(@Valid @RequestBody HouseholdData householdData) {
        Household newHousehold = new Household(
                householdData.eircode(),
                householdData.numberOfOccupants(),
                householdData.maxNumberOfOccupants(),
                householdData.ownerOccupied(),
                null);
        Household savedHousehold = householdService.saveHousehold(newHousehold);
        return ResponseEntity.status(HttpStatus.CREATED).body(householdData);
    }

    // Create a new pet
    @PostMapping("/pets")
    public ResponseEntity<PetData> createPet(@Valid @RequestBody PetData petData) throws NotFoundException {
        Household household = householdService.findHouseholdByEircode(petData.eircode());
        Pet newPet = new Pet(
                petData.name(),
                petData.animalType(),
                petData.breed(),
                petData.age(),
                household);
        Pet savedPet = petService.createPet(newPet);
        return ResponseEntity.status(HttpStatus.CREATED).body(petData);
    }

    // Change a pet's name
    @PatchMapping("/pets/{id}")
    public ResponseEntity<PetData> changePetName(@PathVariable int id, @RequestParam("name") String name) throws NotFoundException {
        Pet pet = petService.getPetById(id);
        pet.setName(name);
        Pet updatedPet = petService.updatePet(id, pet);
        PetData petData = new PetData(
                updatedPet.getName(),
                updatedPet.getAnimalType(),
                updatedPet.getBreed(),
                updatedPet.getAge(),
                updatedPet.getHousehold().getEircode());
        return ResponseEntity.ok(petData);
    }
}
