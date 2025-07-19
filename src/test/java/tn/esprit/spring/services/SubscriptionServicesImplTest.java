package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISubscriptionRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SubscriptionServicesImplTest {
    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionServicesImpl subscriptionServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSubscription() {
        Subscription subscription = mock(Subscription.class);
        when(subscription.getTypeSub()).thenReturn(tn.esprit.spring.entities.TypeSubscription.ANNUAL);
        when(subscription.getStartDate()).thenReturn(java.time.LocalDate.of(2025, 1, 1));
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);
        Subscription result = subscriptionServices.addSubscription(subscription);
        assertNotNull(result);
        verify(subscriptionRepository).save(subscription);
    }

    @Test
    void testUpdateSubscription() {
        Subscription subscription = new Subscription();
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);
        Subscription result = subscriptionServices.updateSubscription(subscription);
        assertNotNull(result);
        verify(subscriptionRepository).save(subscription);
    }

    @Test
    void testRetrieveSubscriptionById() {
        Subscription subscription = new Subscription();
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));
        Subscription result = subscriptionServices.retrieveSubscriptionById(1L);
        assertNotNull(result);
        verify(subscriptionRepository).findById(1L);
    }

    @Test
    void testGetSubscriptionByType() {
        Set<Subscription> subs = new HashSet<>();
        when(subscriptionRepository.findByTypeSubOrderByStartDateAsc(TypeSubscription.ANNUAL)).thenReturn(subs);
        Set<Subscription> result = subscriptionServices.getSubscriptionByType(TypeSubscription.ANNUAL);
        assertNotNull(result);
        verify(subscriptionRepository).findByTypeSubOrderByStartDateAsc(TypeSubscription.ANNUAL);
    }

    @Test
    void testRetrieveSubscriptionsByDates() {
        List<Subscription> subs = Collections.emptyList();
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(1);
        when(subscriptionRepository.getSubscriptionsByStartDateBetween(start, end)).thenReturn(subs);
        List<Subscription> result = subscriptionServices.retrieveSubscriptionsByDates(start, end);
        assertNotNull(result);
        verify(subscriptionRepository).getSubscriptionsByStartDateBetween(start, end);
    }
}
