package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServicesImplTest {
    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllCourses() {
        when(courseRepository.findAll()).thenReturn(Collections.emptyList());
        List<Course> result = courseServices.retrieveAllCourses();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(courseRepository).findAll();
    }

    @Test
    void testAddCourse() {
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        Course result = courseServices.addCourse(course);
        assertNotNull(result);
        verify(courseRepository).save(course);
    }

    @Test
    void testUpdateCourse() {
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        Course result = courseServices.updateCourse(course);
        assertNotNull(result);
        verify(courseRepository).save(course);
    }

    @Test
    void testRetrieveCourse() {
        Course course = new Course();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Course result = courseServices.retrieveCourse(1L);
        assertNotNull(result);
        verify(courseRepository).findById(1L);
    }
}
