package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.IRegistrationRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegistrationServicesImplTest {
    @Mock
    private IRegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRegistrationAndAssignToSkier() {
        Registration registration = new Registration();
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, 1L);
        assertNotNull(result);
        verify(registrationRepository).save(registration);
    }

    @Test
    void testAssignRegistrationToCourse() {
        Registration registration = new Registration();
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        Registration result = registrationServices.assignRegistrationToCourse(1L, 2L);
        assertNotNull(result);
        verify(registrationRepository).save(any(Registration.class));
    }

    @Test
    void testAddRegistrationAndAssignToSkierAndCourse() {
        Registration registration = new Registration();
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 2L);
        assertNotNull(result);
        verify(registrationRepository).save(registration);
    }

    @Test
    void testNumWeeksCourseOfInstructorBySupport() {
        List<Integer> weeks = Collections.singletonList(1);
        when(registrationRepository.numWeeksCourseOfInstructorBySupport(1L, Support.SKI)).thenReturn(weeks);
        List<Integer> result = registrationServices.numWeeksCourseOfInstructorBySupport(1L, Support.SKI);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(registrationRepository).numWeeksCourseOfInstructorBySupport(1L, Support.SKI);
    }
}
