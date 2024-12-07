package com.example.lab9.services;
import com.example.lab9.entities.Household;
import com.example.lab9.exceptions.NotFoundException;

import java.util.List;

public interface HouseholdService {
    Household findHouseholdByEircode(String eircode) throws NotFoundException;
    Household findHouseholdWithPetsByEircode(String eircode) throws NotFoundException;
    List<Household> findHouseholdsWithNoPets();
    Household saveHousehold(Household household);
    void deleteHousehold(String eircode);
    List<Household> getAllHouseholds();
}
