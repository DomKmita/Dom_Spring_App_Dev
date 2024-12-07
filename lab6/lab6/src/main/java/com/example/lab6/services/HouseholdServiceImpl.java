package com.example.lab6.services;

import com.example.lab6.entities.Household;
import com.example.lab6.repositories.HouseholdRepository;
import com.example.lab6.services.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;

    @Override
    public Household findHouseholdByEircode(String eircode) throws NotFoundException {
        return householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household not found with eircode " + eircode));
    }

    @Override
    public Household findHouseholdWithPetsByEircode(String eircode) throws NotFoundException {
        return householdRepository.findWithPetsByEircode(eircode)
                .orElseThrow(() -> new NotFoundException("Household containing pets not found with eircode " + eircode));
    }

    @Override
    public List<Household> findHouseholdsWithNoPets() {
        return householdRepository.findHouseholdsWithNoPets();
    }

    @Override
    public Household saveHousehold(Household household) {
        return householdRepository.save(household);
    }

    @Override
    public void deleteHousehold(String eircode) {
        householdRepository.deleteById(eircode);
    }
}
