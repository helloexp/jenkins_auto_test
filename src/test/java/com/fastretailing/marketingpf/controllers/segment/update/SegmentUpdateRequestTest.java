package com.fastretailing.marketingpf.controllers.segment.update;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.controllers.segment.update.SegmentUpdateRequest.SegmentConditionDto;
import com.fastretailing.marketingpf.controllers.segment.update.SegmentUpdateRequest.SegmentDto;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SegmentUpdateRequestTest {

    @Nested
    public class SegmentDtoTest {

        @Test
        public void testGetBasicSegmentConditionList() {
            SegmentConditionDto condition1 = new SegmentConditionDto();
            condition1.setSegmentConditionBlockOrder(0);
            condition1.setSegmentItemForScreenSequence(1L);
            SegmentConditionDto condition2 = new SegmentConditionDto();
            condition2.setSegmentConditionBlockOrder(1);
            condition2.setSegmentItemForScreenSequence(2L);
            SegmentConditionDto condition3 = new SegmentConditionDto();
            condition3.setSegmentConditionBlockOrder(0);
            condition3.setSegmentItemForScreenSequence(3L);
            SegmentConditionDto condition4 = new SegmentConditionDto();
            condition4.setSegmentConditionBlockOrder(2);
            condition4.setSegmentItemForScreenSequence(4L);
            SegmentDto dto = new SegmentDto();
            dto.setSegmentConditionList(Arrays.asList(condition1, condition2, condition3, condition4));
            SegmentUpdateRequest request = new SegmentUpdateRequest();
            request.setTargetSegment(dto);

            List<SegmentConditionDto> result = request.getTargetSegment().getBasicSegmentConditionList();
            assertThat(result.size()).isEqualTo(2);
            assertThat(result.get(0).getSegmentItemForScreenSequence()).isEqualTo(1L);
            assertThat(result.get(1).getSegmentItemForScreenSequence()).isEqualTo(3L);
        }

        @Test
        public void testGetExtraSegmentConditionList() {
            SegmentConditionDto condition1 = new SegmentConditionDto();
            condition1.setSegmentConditionBlockOrder(0);
            condition1.setSegmentItemForScreenSequence(1L);
            SegmentConditionDto condition2 = new SegmentConditionDto();
            condition2.setSegmentConditionBlockOrder(1);
            condition2.setSegmentItemForScreenSequence(2L);
            SegmentConditionDto condition3 = new SegmentConditionDto();
            condition3.setSegmentConditionBlockOrder(0);
            condition3.setSegmentItemForScreenSequence(3L);
            SegmentConditionDto condition4 = new SegmentConditionDto();
            condition4.setSegmentConditionBlockOrder(2);
            condition4.setSegmentItemForScreenSequence(4L);
            SegmentDto dto = new SegmentDto();
            dto.setSegmentConditionList(Arrays.asList(condition1, condition2, condition3, condition4));
            SegmentUpdateRequest request = new SegmentUpdateRequest();
            request.setTargetSegment(dto);

            List<SegmentConditionDto> result = request.getTargetSegment().getExtraSegmentConditionList();
            assertThat(result.size()).isEqualTo(2);
            assertThat(result.get(0).getSegmentItemForScreenSequence()).isEqualTo(2L);
            assertThat(result.get(1).getSegmentItemForScreenSequence()).isEqualTo(4L);
        }
    }
}
