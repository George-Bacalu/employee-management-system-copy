package com.project.ems.unit.feedback;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.project.ems.constants.Constants.FEEDBACK_FILTER_KEY;
import static com.project.ems.constants.Constants.FEEDBACK_FILTER_QUERY;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FeedbackRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @Test
    void findAllByKey_shouldReturnListOfFilteredFeedbacksPaginatedSorted() {
        String expectedParam = "%" + FEEDBACK_FILTER_KEY + "%";
        given(entityManager.createQuery(FEEDBACK_FILTER_QUERY)).willReturn(query);
        given(query.setParameter("key", expectedParam)).willReturn(query);
        given(query.getResultList()).willReturn(null);
        Query actualQuery = entityManager.createQuery(FEEDBACK_FILTER_QUERY);
        actualQuery.setParameter("key", expectedParam);
        actualQuery.getResultList();
        verify(entityManager).createQuery(FEEDBACK_FILTER_QUERY);
        verify(query).setParameter("key", expectedParam);
        verify(query).getResultList();
    }
}
