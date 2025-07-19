package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PisteServicesImplTest {
    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllPistes() {
        when(pisteRepository.findAll()).thenReturn(Collections.emptyList());
        List<Piste> result = pisteServices.retrieveAllPistes();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(pisteRepository).findAll();
    }

    @Test
    void testAddPiste() {
        Piste piste = new Piste();
        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);
        Piste result = pisteServices.addPiste(piste);
        assertNotNull(result);
        verify(pisteRepository).save(piste);
    }

    @Test
    void testRemovePiste() {
        pisteServices.removePiste(1L);
        verify(pisteRepository).deleteById(1L);
    }

    @Test
    void testRetrievePiste() {
        Piste piste = new Piste();
        when(pisteRepository.findById(1L)).thenReturn(Optional.of(piste));
        Piste result = pisteServices.retrievePiste(1L);
        assertNotNull(result);
        verify(pisteRepository).findById(1L);
    }
}
