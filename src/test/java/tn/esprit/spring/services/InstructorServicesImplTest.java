package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.entities.Course;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class InstructorServicesImplTest {
    @Mock
    private IInstructorRepository instructorRepository;
    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);
        Instructor result = instructorServices.addInstructor(instructor);
        assertNotNull(result);
        verify(instructorRepository).save(instructor);
    }

    @Test
    void testRetrieveAllInstructors() {
        when(instructorRepository.findAll()).thenReturn(Collections.emptyList());
        List<Instructor> result = instructorServices.retrieveAllInstructors();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(instructorRepository).findAll();
    }

    @Test
    void testUpdateInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);
        Instructor result = instructorServices.updateInstructor(instructor);
        assertNotNull(result);
        verify(instructorRepository).save(instructor);
    }

    @Test
    void testRetrieveInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        Instructor result = instructorServices.retrieveInstructor(1L);
        assertNotNull(result);
        verify(instructorRepository).findById(1L);
    }

    @Test
    void testAddInstructorAndAssignToCourse() {
        Instructor instructor = new Instructor();
        Course course = new Course();
        when(courseRepository.findById(1L)).thenReturn(java.util.Optional.of(course));
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);
        Instructor result = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);
        assertNotNull(result);
        verify(courseRepository).findById(1L);
        verify(instructorRepository).save(instructor);
    }
}
