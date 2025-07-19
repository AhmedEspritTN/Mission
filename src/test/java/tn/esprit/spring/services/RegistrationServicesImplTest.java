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
        Skier skier = mock(Skier.class);
        when(skier.getNumSkier()).thenReturn(1L);
        when(skier.getDateOfBirth()).thenReturn(java.time.LocalDate.of(2000, 1, 1));
        Course course = mock(Course.class);
        when(course.getNumCourse()).thenReturn(2L);
        when(course.getTypeCourse()).thenReturn(tn.esprit.spring.entities.TypeCourse.INDIVIDUAL);
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
    void testAddRegistrationAndAssignToSkierAndCourse_nullSkier() {
        Registration registration = new Registration();
        when(skierRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        when(courseRepository.findById(2L)).thenReturn(java.util.Optional.of(mock(Course.class)));
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 2L);
        assertNull(result);
    }

    @Test
    void testAddRegistrationAndAssignToSkierAndCourse_nullCourse() {
        Registration registration = new Registration();
        when(skierRepository.findById(1L)).thenReturn(java.util.Optional.of(mock(Skier.class)));
        when(courseRepository.findById(2L)).thenReturn(java.util.Optional.empty());
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 2L);
        assertNull(result);
    }

    @Test
    void testAddRegistrationAndAssignToSkierAndCourse_duplicateRegistration() {
        Registration registration = new Registration();
        Skier skier = mock(Skier.class);
        when(skier.getNumSkier()).thenReturn(1L);
        when(skier.getDateOfBirth()).thenReturn(java.time.LocalDate.of(2000, 1, 1));
        Course course = mock(Course.class);
        when(course.getNumCourse()).thenReturn(2L);
        when(course.getTypeCourse()).thenReturn(tn.esprit.spring.entities.TypeCourse.INDIVIDUAL);
        registration.setNumWeek(1);
        when(skierRepository.findById(1L)).thenReturn(java.util.Optional.of(skier));
        when(courseRepository.findById(2L)).thenReturn(java.util.Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, 1L, 2L)).thenReturn(1L);
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 2L);
        assertNull(result);
    }

    @Test
    void testAddRegistrationAndAssignToSkierAndCourse_collectiveChildren() {
        Registration registration = new Registration();
        Skier skier = mock(Skier.class);
        when(skier.getNumSkier()).thenReturn(1L);
        when(skier.getDateOfBirth()).thenReturn(java.time.LocalDate.of(2015, 1, 1));
        Course course = mock(Course.class);
        when(course.getNumCourse()).thenReturn(2L);
        when(course.getTypeCourse()).thenReturn(tn.esprit.spring.entities.TypeCourse.COLLECTIVE_CHILDREN);
        registration.setNumWeek(1);
        when(skierRepository.findById(1L)).thenReturn(java.util.Optional.of(skier));
        when(courseRepository.findById(2L)).thenReturn(java.util.Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, 1L, 2L)).thenReturn(0L);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 2L);
        assertNotNull(result);
    }

    @Test
    void testAddRegistrationAndAssignToSkierAndCourse_collectiveAdult() {
        Registration registration = new Registration();
        Skier skier = mock(Skier.class);
        when(skier.getNumSkier()).thenReturn(1L);
        when(skier.getDateOfBirth()).thenReturn(java.time.LocalDate.of(1990, 1, 1));
        Course course = mock(Course.class);
        when(course.getNumCourse()).thenReturn(2L);
        when(course.getTypeCourse()).thenReturn(tn.esprit.spring.entities.TypeCourse.COLLECTIVE_ADULT);
        registration.setNumWeek(1);
        when(skierRepository.findById(1L)).thenReturn(java.util.Optional.of(skier));
        when(courseRepository.findById(2L)).thenReturn(java.util.Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, 1L, 2L)).thenReturn(0L);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 2L);
        assertNotNull(result);
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
