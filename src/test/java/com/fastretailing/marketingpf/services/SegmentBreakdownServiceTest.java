package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import com.fastretailing.marketingpf.domain.dtos.SegmentBreakdown;
import com.fastretailing.marketingpf.exceptions.MkdbApiUpstreamFailureException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

class SegmentBreakdownServiceTest extends BaseSpringBootTest {

    @Nested
    public class FindByIdTest {

        @MockBean
        private SegmentBreakdownService segmentBreakdownService;

        @Test
        public void expectingFindSuccess() {
            SegmentBreakdown segmentBreakdown = new SegmentBreakdown();
            segmentBreakdown.setSegmentBreakdownSequence(1001L);
            segmentBreakdown.setSegmentSequence(2001L);
            segmentBreakdown.setTargetNumberOfPeopleBySegment(3001L);
            segmentBreakdown.setSegmentBreakdown("{}");
            segmentBreakdown.setDeletionFlagForAudit("false");
            segmentBreakdown.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
            segmentBreakdown.setCreateUserIdForAudit("user_01");
            segmentBreakdown.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
            segmentBreakdown.setDeletionUserIdForAudit("user_01");
            segmentBreakdown.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
            segmentBreakdown.setUpdateUserIdForAudit("user_01");
            Mockito.doReturn(segmentBreakdown).when(segmentBreakdownService).findBySegmentSequence(Mockito.anyLong());

            SegmentBreakdown result = segmentBreakdownService.findBySegmentSequence(1001L);
            assertThat(result.getSegmentBreakdownSequence()).isEqualTo(1001L);
            assertThat(result.getSegmentSequence()).isEqualTo(2001L);
            assertThat(result.getTargetNumberOfPeopleBySegment()).isEqualTo(3001L);
            assertThat(result.getSegmentBreakdown()).isEqualTo("{}");
            assertThat(result.getDeletionFlagForAudit()).isEqualTo("false");
            assertThat(result.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
            assertThat(result.getCreateUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getDeletionDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
            assertThat(result.getDeletionUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
            assertThat(result.getUpdateUserIdForAudit()).isEqualTo("user_01");
        }

        @Test
        public void expectingMkdbApiUpstreamFailureException() {
            Mockito.doThrow(MkdbApiUpstreamFailureException.class).when(segmentBreakdownService).findBySegmentSequence(Mockito.anyLong());

            try {
                segmentBreakdownService.findBySegmentSequence(1002L);
                fail("");
            } catch (MkdbApiUpstreamFailureException e) {
            }
        }
    }

    @Nested
    public class SearchTest {

        @MockBean
        private SegmentBreakdownService segmentBreakdownService;

        @Test
        public void expectingSuccess() {
            SegmentBreakdown segmentBreakdown = new SegmentBreakdown();
            segmentBreakdown.setSegmentBreakdownSequence(1101L);
            segmentBreakdown.setSegmentSequence(2001L);
            segmentBreakdown.setTargetNumberOfPeopleBySegment(3003L);
            segmentBreakdown.setSegmentBreakdown("{}");
            segmentBreakdown.setDeletionFlagForAudit("false");
            segmentBreakdown.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
            segmentBreakdown.setCreateUserIdForAudit("user_01");
            segmentBreakdown.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
            segmentBreakdown.setDeletionUserIdForAudit("user_01");
            segmentBreakdown.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
            segmentBreakdown.setUpdateUserIdForAudit("user_01");

            SegmentBreakdown segmentBreakdown2 = new SegmentBreakdown();
            segmentBreakdown2.setSegmentBreakdownSequence(1102L);
            segmentBreakdown2.setSegmentSequence(2002L);
            segmentBreakdown2.setTargetNumberOfPeopleBySegment(3002L);
            segmentBreakdown2.setSegmentBreakdown("{}");
            segmentBreakdown2.setDeletionFlagForAudit("false");
            segmentBreakdown2.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
            segmentBreakdown2.setCreateUserIdForAudit("user_01");
            segmentBreakdown2.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
            segmentBreakdown2.setDeletionUserIdForAudit("user_01");
            segmentBreakdown2.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
            segmentBreakdown2.setUpdateUserIdForAudit("user_01");
            Mockito.doReturn(Arrays.asList(segmentBreakdown, segmentBreakdown2)).when(segmentBreakdownService).search(Mockito.any(), Mockito.anyLong(), Mockito.any());

            List<SegmentBreakdown> result = segmentBreakdownService.search(Arrays.asList(2001L, 2002L), 10003L, 2);
            assertThat(result.size()).isEqualTo(2);
            assertThat(result.get(0).getSegmentBreakdownSequence()).isEqualTo(1101L);
            assertThat(result.get(0).getTargetNumberOfPeopleBySegment()).isEqualTo(3003L);
            assertThat(result.get(0).getSegmentBreakdown()).isEqualTo("{}");
            assertThat(result.get(0).getDeletionFlagForAudit()).isEqualTo("false");
            assertThat(result.get(0).getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
            assertThat(result.get(0).getCreateUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.get(0).getDeletionDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
            assertThat(result.get(0).getDeletionUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.get(0).getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
            assertThat(result.get(0).getUpdateUserIdForAudit()).isEqualTo("user_01");

            assertThat(result.get(0).getSegmentBreakdownSequence()).isEqualTo(1101L);
            assertThat(result.get(0).getTargetNumberOfPeopleBySegment()).isEqualTo(3003L);
            assertThat(result.get(0).getSegmentBreakdown()).isEqualTo("{}");
        }

        @Test
        public void expectingMkdbApiUpstreamFailureException() {
            Mockito.doThrow(MkdbApiUpstreamFailureException.class).when(segmentBreakdownService).search(Mockito.any(), Mockito.anyLong(), Mockito.any());

            try {
                segmentBreakdownService.search(Arrays.asList(2001L, 2002L), 10003L, 2);
                fail("");
            } catch (MkdbApiUpstreamFailureException e) {
            }
        }
    }
}
