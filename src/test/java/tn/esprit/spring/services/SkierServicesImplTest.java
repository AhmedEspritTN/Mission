package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.Piste;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SkierServicesImplTest {
    @Mock
    private ISkierRepository skierRepository;
    @Mock
    private ISubscriptionRepository subscriptionRepository;
    @Mock
    private IPisteRepository pisteRepository;
    @Mock
    private tn.esprit.spring.repositories.ICourseRepository courseRepository;
    @InjectMocks
    private SkierServicesImpl skierServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllSkiers() {
        List<Skier> skiers = Collections.singletonList(new Skier());
        when(skierRepository.findAll()).thenReturn(skiers);
        List<Skier> result = skierServices.retrieveAllSkiers();
        assertEquals(1, result.size());
        verify(skierRepository).findAll();
    }

    @Test
    void testAddSkier() {
        Skier skier = new Skier();
        // Mock Subscription and set to skier
        tn.esprit.spring.entities.Subscription subscription = mock(tn.esprit.spring.entities.Subscription.class);
        when(subscription.getTypeSub()).thenReturn(tn.esprit.spring.entities.TypeSubscription.ANNUAL);
        when(subscription.getStartDate()).thenReturn(java.time.LocalDate.now());
        skier.setSubscription(subscription);
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier result = skierServices.addSkier(skier);
        assertNotNull(result);
        verify(skierRepository).save(skier);
    }

    @Test
    void testAssignSkierToSubscription() {
        Skier skier = new Skier();
        Subscription subscription = new Subscription();
        when(skierRepository.findById(1L)).thenReturn(java.util.Optional.of(skier));
        when(subscriptionRepository.findById(2L)).thenReturn(java.util.Optional.of(subscription));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier result = skierServices.assignSkierToSubscription(1L, 2L);
        assertNotNull(result);
        verify(skierRepository).findById(1L);
        verify(subscriptionRepository).findById(2L);
        verify(skierRepository).save(skier);
    }

    @Test
    void testAddSkierAndAssignToCourse() {
        Skier skier = new Skier();
        // Set non-null registrations
        skier.setRegistrations(new java.util.HashSet<>());
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        // Mock courseRepository.getById
        when(courseRepository.getById(1L)).thenReturn(new tn.esprit.spring.entities.Course());
        Skier result = skierServices.addSkierAndAssignToCourse(skier, 1L);
        assertNotNull(result);
        verify(skierRepository).save(skier);
        verify(courseRepository).getById(1L);
    }

    @Test
    void testRemoveSkier() {
        doNothing().when(skierRepository).deleteById(1L);
        skierServices.removeSkier(1L);
        verify(skierRepository).deleteById(1L);
    }

    @Test
    void testRetrieveSkier() {
        Skier skier = new Skier();
        when(skierRepository.findById(1L)).thenReturn(java.util.Optional.of(skier));
        Skier result = skierServices.retrieveSkier(1L);
        assertNotNull(result);
        verify(skierRepository).findById(1L);
    }

    @Test
    void testAssignSkierToPiste() {
        Skier skier = new Skier();
        Piste piste = new Piste();
        when(skierRepository.findById(1L)).thenReturn(java.util.Optional.of(skier));
        when(pisteRepository.findById(2L)).thenReturn(java.util.Optional.of(piste));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier result = skierServices.assignSkierToPiste(1L, 2L);
        assertNotNull(result);
        verify(skierRepository).findById(1L);
        verify(pisteRepository).findById(2L);
        verify(skierRepository).save(skier);
    }

    @Test
    void testRetrieveSkiersBySubscriptionType() {
        List<Skier> skiers = Collections.singletonList(new Skier());
        when(skierRepository.findBySubscription_TypeSub(TypeSubscription.ANNUAL)).thenReturn(skiers);
        List<Skier> result = skierServices.retrieveSkiersBySubscriptionType(TypeSubscription.ANNUAL);
        assertEquals(1, result.size());
        verify(skierRepository).findBySubscription_TypeSub(TypeSubscription.ANNUAL);
    }
}
