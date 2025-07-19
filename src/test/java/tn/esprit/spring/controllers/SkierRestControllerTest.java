
package tn.esprit.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.services.ISkierServices;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SkierRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ISkierServices skierServices;

    @InjectMocks
    private SkierRestController skierRestController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(skierRestController).build();
    }

    @Test
    void testAddSkier() throws Exception {
        Skier skier = new Skier();
        when(skierServices.addSkier(any(Skier.class))).thenReturn(skier);
        mockMvc.perform(post("/skier/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(skier)))
                .andExpect(status().isOk());
        verify(skierServices).addSkier(any(Skier.class));
    }

    @Test
    void testAddSkierAndAssignToCourse() throws Exception {
        Skier skier = new Skier();
        when(skierServices.addSkierAndAssignToCourse(any(Skier.class), eq(1L))).thenReturn(skier);
        mockMvc.perform(post("/skier/addAndAssign/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(skier)))
                .andExpect(status().isOk());
        verify(skierServices).addSkierAndAssignToCourse(any(Skier.class), eq(1L));
    }

    @Test
    void testAssignToSubscription() throws Exception {
        Skier skier = new Skier();
        when(skierServices.assignSkierToSubscription(1L, 2L)).thenReturn(skier);
        mockMvc.perform(put("/skier/assignToSub/1/2"))
                .andExpect(status().isOk());
        verify(skierServices).assignSkierToSubscription(1L, 2L);
    }

    @Test
    void testAssignToPiste() throws Exception {
        Skier skier = new Skier();
        when(skierServices.assignSkierToPiste(1L, 2L)).thenReturn(skier);
        mockMvc.perform(put("/skier/assignToPiste/1/2"))
                .andExpect(status().isOk());
        verify(skierServices).assignSkierToPiste(1L, 2L);
    }

    @Test
    void testRetrieveSkiersBySubscriptionType() throws Exception {
        List<Skier> skiers = Arrays.asList(new Skier(), new Skier());
        when(skierServices.retrieveSkiersBySubscriptionType(any(TypeSubscription.class))).thenReturn(skiers);
        mockMvc.perform(get("/skier/getSkiersBySubscription")
                .param("typeSubscription", "ANNUAL"))
                .andExpect(status().isOk());
        verify(skierServices).retrieveSkiersBySubscriptionType(any(TypeSubscription.class));
    }

    @Test
    void testGetById() throws Exception {
        Skier skier = new Skier();
        when(skierServices.retrieveSkier(1L)).thenReturn(skier);
        mockMvc.perform(get("/skier/get/1"))
                .andExpect(status().isOk());
        verify(skierServices).retrieveSkier(1L);
    }

    @Test
    void testDeleteById() throws Exception {
        doNothing().when(skierServices).removeSkier(1L);
        mockMvc.perform(delete("/skier/delete/1"))
                .andExpect(status().isOk());
        verify(skierServices).removeSkier(1L);
    }

    @Test
    void testGetAllSkiers() throws Exception {
        when(skierServices.retrieveAllSkiers()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/skier/all"))
                .andExpect(status().isOk());
        verify(skierServices).retrieveAllSkiers();
    }
}
