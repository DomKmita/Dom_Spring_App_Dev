package com.example.lab10.services;
import com.example.lab10.entities.Household;
import com.example.lab10.exceptions.NotFoundException;

import java.util.List;

public interface HouseholdService {
    Household findHouseholdByEircode(String eircode) throws NotFoundException;
    Household findHouseholdWithPetsByEircode(String eircode) throws NotFoundException;
    List<Household> findHouseholdsWithNoPets();
    Household saveHousehold(Household household);
    void deleteHousehold(String eircode);
    List<Household> getAllHouseholds();
}
