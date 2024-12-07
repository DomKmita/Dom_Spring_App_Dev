package com.example.lab6.services;
import com.example.lab6.entities.Household;
import com.example.lab6.services.exceptions.NotFoundException;

import java.util.List;

public interface HouseholdService {
    Household findHouseholdByEircode(String eircode) throws NotFoundException;
    Household findHouseholdWithPetsByEircode(String eircode) throws NotFoundException;
    List<Household> findHouseholdsWithNoPets();
    Household saveHousehold(Household household);
    void deleteHousehold(String eircode);
}
