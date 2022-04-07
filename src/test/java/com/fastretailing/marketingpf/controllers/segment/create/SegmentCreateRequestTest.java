package com.fastretailing.marketingpf.controllers.segment.create;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.controllers.segment.create.SegmentCreateRequest.SegmentConditionRequestDto;
import com.fastretailing.marketingpf.controllers.segment.create.SegmentCreateRequest.SegmentCreateRequestDto;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SegmentCreateRequestTest {

    @Nested
    public class SegmentCreateRequestDtoTest {

        @Test
        public void testGetBasicSegmentConditionList() {
            SegmentConditionRequestDto condition1 = new SegmentConditionRequestDto();
            condition1.setSegmentConditionBlockOrder(0);
            condition1.setSegmentItemForScreenSequence(1L);
            SegmentConditionRequestDto condition2 = new SegmentConditionRequestDto();
            condition2.setSegmentConditionBlockOrder(1);
            condition2.setSegmentItemForScreenSequence(2L);
            SegmentConditionRequestDto condition3 = new SegmentConditionRequestDto();
            condition3.setSegmentConditionBlockOrder(0);
            condition3.setSegmentItemForScreenSequence(3L);
            SegmentConditionRequestDto condition4 = new SegmentConditionRequestDto();
            condition4.setSegmentConditionBlockOrder(2);
            condition4.setSegmentItemForScreenSequence(4L);
            SegmentCreateRequestDto dto = new SegmentCreateRequestDto();
            dto.setSegmentConditionList(Arrays.asList(condition1, condition2, condition3, condition4));
            SegmentCreateRequest request = new SegmentCreateRequest();
            request.setTargetSegment(dto);

            List<SegmentConditionRequestDto> result = request.getTargetSegment().getBasicSegmentConditionList();
            assertThat(result.size()).isEqualTo(2);
            assertThat(result.get(0).getSegmentItemForScreenSequence()).isEqualTo(1L);
            assertThat(result.get(1).getSegmentItemForScreenSequence()).isEqualTo(3L);
        }

        @Test
        public void testGetExtraSegmentConditionList() {
            SegmentConditionRequestDto condition1 = new SegmentConditionRequestDto();
            condition1.setSegmentConditionBlockOrder(0);
            condition1.setSegmentItemForScreenSequence(1L);
            SegmentConditionRequestDto condition2 = new SegmentConditionRequestDto();
            condition2.setSegmentConditionBlockOrder(1);
            condition2.setSegmentItemForScreenSequence(2L);
            SegmentConditionRequestDto condition3 = new SegmentConditionRequestDto();
            condition3.setSegmentConditionBlockOrder(0);
            condition3.setSegmentItemForScreenSequence(3L);
            SegmentConditionRequestDto condition4 = new SegmentConditionRequestDto();
            condition4.setSegmentConditionBlockOrder(2);
            condition4.setSegmentItemForScreenSequence(4L);
            SegmentCreateRequestDto dto = new SegmentCreateRequestDto();
            dto.setSegmentConditionList(Arrays.asList(condition1, condition2, condition3, condition4));
            SegmentCreateRequest request = new SegmentCreateRequest();
            request.setTargetSegment(dto);

            List<SegmentConditionRequestDto> result = request.getTargetSegment().getExtraSegmentConditionList();
            assertThat(result.size()).isEqualTo(2);
            assertThat(result.get(0).getSegmentItemForScreenSequence()).isEqualTo(2L);
            assertThat(result.get(1).getSegmentItemForScreenSequence()).isEqualTo(4L);
        }
    }
}
