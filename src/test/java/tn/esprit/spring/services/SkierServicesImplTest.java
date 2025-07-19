package tn.esprit.spring.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.repositories.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SkierServicesImplTest {

    @Mock
    private ISkierRepository skierRepository;
    @Mock
    private IPisteRepository pisteRepository;
    @Mock
    private ICourseRepository courseRepository;
    @Mock
    private IRegistrationRepository registrationRepository;
    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SkierServicesImpl skierServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllSkiers() {
        when(skierRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(skierServices.retrieveAllSkiers().isEmpty());
        verify(skierRepository, times(1)).findAll();
    }

    @Test
    void testAddSkier_Annual() {
        Subscription sub = new Subscription();
        sub.setTypeSub(TypeSubscription.ANNUAL);
        sub.setStartDate(java.time.LocalDate.of(2025, 1, 1));
        Skier skier = new Skier();
        skier.setSubscription(sub);
        Skier savedSkier = new Skier();
        when(skierRepository.save(any(Skier.class))).thenReturn(savedSkier);
        Skier result = skierServices.addSkier(skier);
        assertNotNull(result);
        assertEquals(java.time.LocalDate.of(2026, 1, 1), skier.getSubscription().getEndDate());
        verify(skierRepository).save(skier);
    }

    @Test
    void testAddSkier_Semestriel() {
        Subscription sub = new Subscription();
        sub.setTypeSub(TypeSubscription.SEMESTRIEL);
        sub.setStartDate(java.time.LocalDate.of(2025, 1, 1));
        Skier skier = new Skier();
        skier.setSubscription(sub);
        Skier savedSkier = new Skier();
        when(skierRepository.save(any(Skier.class))).thenReturn(savedSkier);
        Skier result = skierServices.addSkier(skier);
        assertNotNull(result);
        assertEquals(java.time.LocalDate.of(2025, 7, 1), skier.getSubscription().getEndDate());
        verify(skierRepository).save(skier);
    }

    @Test
    void testAddSkier_Monthly() {
        Subscription sub = new Subscription();
        sub.setTypeSub(TypeSubscription.MONTHLY);
        sub.setStartDate(java.time.LocalDate.of(2025, 1, 1));
        Skier skier = new Skier();
        skier.setSubscription(sub);
        Skier savedSkier = new Skier();
        when(skierRepository.save(any(Skier.class))).thenReturn(savedSkier);
        Skier result = skierServices.addSkier(skier);
        assertNotNull(result);
        assertEquals(java.time.LocalDate.of(2025, 2, 1), skier.getSubscription().getEndDate());
        verify(skierRepository).save(skier);
    }

    @Test
    void testAssignSkierToSubscription() {
        Skier skier = new Skier();
        Subscription sub = new Subscription();
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(subscriptionRepository.findById(2L)).thenReturn(Optional.of(sub));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier result = skierServices.assignSkierToSubscription(1L, 2L);
        assertEquals(sub, skier.getSubscription());
        assertEquals(skier, result);
        verify(skierRepository).save(skier);
    }

    @Test
    void testAddSkierAndAssignToCourse() {
        Skier skier = new Skier();
        Registration reg = new Registration();
        Set<Registration> regs = new HashSet<>();
        regs.add(reg);
        skier.setRegistrations(regs);
        Course course = new Course();
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        when(courseRepository.getById(3L)).thenReturn(course);
        when(registrationRepository.save(any(Registration.class))).thenReturn(reg);
        Skier result = skierServices.addSkierAndAssignToCourse(skier, 3L);
        assertEquals(skier, result);
        assertEquals(skier, reg.getSkier());
        assertEquals(course, reg.getCourse());
        verify(skierRepository).save(skier);
        verify(registrationRepository).save(reg);
    }

    @Test
    void testRemoveSkier() {
        skierServices.removeSkier(4L);
        verify(skierRepository).deleteById(4L);
    }

    @Test
    void testRetrieveSkier() {
        Skier skier = new Skier();
        when(skierRepository.findById(5L)).thenReturn(Optional.of(skier));
        Skier result = skierServices.retrieveSkier(5L);
        assertEquals(skier, result);
    }

    @Test
    void testAssignSkierToPiste() {
        Skier skier = new Skier();
        Piste piste = new Piste();
        skier.setPistes(new HashSet<>());
        when(skierRepository.findById(6L)).thenReturn(Optional.of(skier));
        when(pisteRepository.findById(7L)).thenReturn(Optional.of(piste));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier result = skierServices.assignSkierToPiste(6L, 7L);
        assertTrue(skier.getPistes().contains(piste));
        assertEquals(skier, result);
        verify(skierRepository).save(skier);
    }

    @Test
    void testAssignSkierToPiste_NullPistes() {
        Skier skier = new Skier();
        skier.setPistes(null);
        Piste piste = new Piste();
        when(skierRepository.findById(8L)).thenReturn(Optional.of(skier));
        when(pisteRepository.findById(9L)).thenReturn(Optional.of(piste));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier result = skierServices.assignSkierToPiste(8L, 9L);
        assertNotNull(skier.getPistes());
        assertTrue(skier.getPistes().contains(piste));
        assertEquals(skier, result);
        verify(skierRepository).save(skier);
    }

    @Test
    void testRetrieveSkiersBySubscriptionType() {
        List<Skier> skiers = Arrays.asList(new Skier(), new Skier());
        when(skierRepository.findBySubscription_TypeSub(TypeSubscription.ANNUAL)).thenReturn(skiers);
        List<Skier> result = skierServices.retrieveSkiersBySubscriptionType(TypeSubscription.ANNUAL);
        assertEquals(2, result.size());
        verify(skierRepository).findBySubscription_TypeSub(TypeSubscription.ANNUAL);
    }
}
