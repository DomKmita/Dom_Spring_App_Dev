package com.example.lab10;

import com.example.lab10.services.HouseholdService;
import com.example.lab10.services.PetService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(classes = Lab5Application.class)
@AutoConfigureMockMvc
public class SecurityTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PetService petService;

    @MockBean
    HouseholdService householdService;

    @Nested
    class DeletePetTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        public void whenDeletePetRequest_idExists_thenNoContent() throws Exception {
            doNothing().when(petService).deletePetById(1);
            mockMvc.perform(delete("/api/pets/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
        }

        @Test
        @WithMockUser(roles = "USER")
        public void whenDeletePetRequest_asUser_thenForbidden() throws Exception {
            mockMvc.perform(delete("/api/pets/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }

        @Test
        public void whenDeletePetRequest_unauthenticated_thenUnauthorized() throws Exception {
            mockMvc.perform(delete("/api/pets/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        }
    }

    @Nested
    class GetPetsTests {

        @Test
        @WithMockUser(roles = "USER")
        public void whenGetPets_asUser_thenOk() throws Exception {
            when(petService.getAllPets()).thenReturn(List.of());
            mockMvc.perform(get("/api/pets")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void whenGetPets_unauthenticated_thenOk() throws Exception {
            when(petService.getAllPets()).thenReturn(List.of());
            mockMvc.perform(get("/api/pets")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Nested
    class CreatePetTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        public void whenCreatePet_asAdmin_thenCreated() throws Exception {
            String petJson = "{\"name\":\"Buddy\",\"animalType\":\"Dog\",\"breed\":\"Golden Retriever\",\"age\":3,\"eircode\":\"D02XY45\"}";

            mockMvc.perform(post("/api/pets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(petJson))
                    .andExpect(MockMvcResultMatchers.status().isCreated());

            verify(petService, times(1)).createPet(any());
        }

        @Test
        @WithMockUser(roles = "USER")
        public void whenCreatePet_asUser_thenCreated() throws Exception {
            String petJson = "{\"name\":\"Buddy\",\"animalType\":\"Dog\",\"breed\":\"Golden Retriever\",\"age\":3,\"eircode\":\"D02XY45\"}";

            mockMvc.perform(post("/api/pets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(petJson))
                    .andExpect(MockMvcResultMatchers.status().isCreated());

            verify(petService, times(0)).createPet(any());
        }

        @Test
        public void whenCreatePet_unauthenticated_thenUnauthorized() throws Exception {
            String petJson = "{\"name\":\"Buddy\",\"animalType\":\"Dog\",\"breed\":\"Golden Retriever\",\"age\":3,\"eircode\":\"D02XY45\"}";

            mockMvc.perform(post("/api/pets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(petJson))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized());

            verify(petService, times(0)).createPet(any());
        }
    }

    @Nested
    class HouseholdsEndpointTests {
        @Test
        @WithMockUser(roles = "ADMIN")
        public void whenDeleteHousehold_asAdmin_thenNoContent() throws Exception {
            doNothing().when(householdService).deleteHousehold("D02XY45");
            mockMvc.perform(delete("/api/households/D02XY45")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
        }

        @Test
        @WithMockUser(roles = "USER")
        public void whenDeleteHousehold_asUser_thenForbidden() throws Exception {
            mockMvc.perform(delete("/api/households/D02XY45")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }

        @Test
        public void whenDeleteHousehold_unauthenticated_thenUnauthorized() throws Exception {
            mockMvc.perform(delete("/api/households/D02XY45")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        }

        @Test
        @WithMockUser(roles = "USER")
        public void whenGetHouseholds_asUser_thenOk() throws Exception {
            when(householdService.getAllHouseholds()).thenReturn(List.of());
            mockMvc.perform(get("/api/households")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        public void whenGetHouseholds_asAdmin_thenOk() throws Exception {
            when(householdService.getAllHouseholds()).thenReturn(List.of());
            mockMvc.perform(get("/api/households")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void whenGetHouseholds_unauthenticated_thenOk() throws Exception {
            when(householdService.getAllHouseholds()).thenReturn(List.of());
            mockMvc.perform(get("/api/households")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }
}
