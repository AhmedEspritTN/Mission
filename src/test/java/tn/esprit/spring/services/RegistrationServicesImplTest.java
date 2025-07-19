package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Course;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class RegistrationServicesImplTest {
    @Mock
    private IRegistrationRepository registrationRepository;
    @Mock
    private ISkierRepository skierRepository;
    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAddRegistrationAndAssignToSkier() {
        Registration registration = new Registration();
        Skier skier = new Skier();
        when(skierRepository.findById(1L)).thenReturn(java.util.Optional.of(skier));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, 1L);
        assertNotNull(result);
        verify(skierRepository).findById(1L);
        verify(registrationRepository).save(registration);
    }


    @Test
    void testAssignRegistrationToCourse() {
        Registration registration = new Registration();
        Course course = new Course();
        when(registrationRepository.findById(1L)).thenReturn(java.util.Optional.of(registration));
        when(courseRepository.findById(2L)).thenReturn(java.util.Optional.of(course));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        Registration result = registrationServices.assignRegistrationToCourse(1L, 2L);
        assertNotNull(result);
        verify(registrationRepository).findById(1L);
        verify(courseRepository).findById(2L);
        verify(registrationRepository).save(registration);
    }


    @Test
    void testAddRegistrationAndAssignToSkierAndCourse() {
        Registration registration = new Registration();
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        Course course = new Course();
        course.setNumCourse(2L);
        registration.setNumWeek(1);
        when(skierRepository.findById(1L)).thenReturn(java.util.Optional.of(skier));
        when(courseRepository.findById(2L)).thenReturn(java.util.Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, 1L, 2L)).thenReturn(0L);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 2L);
        assertNotNull(result);
        verify(skierRepository).findById(1L);
        verify(courseRepository).findById(2L);
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
