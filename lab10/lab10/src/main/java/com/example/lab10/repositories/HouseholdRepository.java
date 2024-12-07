package com.example.lab10.repositories;

import com.example.lab10.entities.Household;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, String> {
    @EntityGraph(attributePaths = {"pets"})
    Optional<Household> findWithPetsByEircode(String eircode);

    @Query("SELECT h FROM Household h LEFT JOIN h.pets p WHERE p IS NULL")
    List<Household> findHouseholdsWithNoPets();
}
