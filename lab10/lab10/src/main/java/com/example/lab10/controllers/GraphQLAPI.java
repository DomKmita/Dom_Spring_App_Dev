package com.example.lab10.controllers;

import com.example.lab10.dtos.HouseholdData;
import com.example.lab10.dtos.PetStatistics;
import com.example.lab10.entities.Household;
import com.example.lab10.entities.Pet;
import com.example.lab10.services.HouseholdService;
import com.example.lab10.services.PetService;
import com.example.lab10.exceptions.NotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class GraphQLAPI {
    private final HouseholdService householdService;
    private final PetService petService;

    @QueryMapping
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping
    public List<Pet> getPetsByAnimalType(@Argument String animalType) {
        return petService.getPetsByAnimalType(animalType);
    }

    @QueryMapping
    public Household getHousehold(@Argument String eircode) throws NotFoundException {
        return householdService.findHouseholdByEircode(eircode);
    }

    @QueryMapping
    public Pet getPet(@Argument int id) throws NotFoundException {
        return petService.getPetById(id);
    }

    @QueryMapping
    public PetStatistics getStatistics() {
        return petService.getPetStatistics();
    }

    @MutationMapping
    @Secured(value = {"ROLE_ADMIN"})
    public Household createHousehold(@Valid @Argument HouseholdData householdData) {
        Household household = new Household(
                householdData.eircode(),
                householdData.numberOfOccupants(),
                householdData.maxNumberOfOccupants(),
                householdData.ownerOccupied(),
                null);
        return householdService.saveHousehold(household);
    }

    @MutationMapping
    @Secured(value = {"ROLE_ADMIN"})
    public int deleteHousehold(@Argument String eircode) {
        householdService.deleteHousehold(eircode);
        return 1;
    }

    @MutationMapping
    @Secured(value = {"ROLE_ADMIN"})
    public int deletePet(@Argument int id) throws NotFoundException {
        petService.deletePetById(id);
        return 1;
    }
}
