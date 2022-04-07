package com.fastretailing.marketingpf.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SegmentItemForScreenMasterTest {

    @Nested
    public class GetOperatorListAsEnumListTest {

        @Test
        public void expectingGetListSuccess() {
            SegmentItemForScreenMaster master = new SegmentItemForScreenMaster();
            master.setOperatorList("{\"operatorList\": [1,2,7,8,9,10]}");
            List<OPERATOR_TYPE> resultList = master.getOperatorListAsEnumList();
            assertThat(resultList.size()).isEqualTo(6);
            assertThat(resultList.get(0)).isEqualTo(OPERATOR_TYPE.EQUAL);
            assertThat(resultList.get(1)).isEqualTo(OPERATOR_TYPE.NOT_EQUAL);
            assertThat(resultList.get(2)).isEqualTo(OPERATOR_TYPE.GREATER_OR_EQUAL);
            assertThat(resultList.get(3)).isEqualTo(OPERATOR_TYPE.GREATER);
            assertThat(resultList.get(4)).isEqualTo(OPERATOR_TYPE.LESS_OR_EQUAL);
            assertThat(resultList.get(5)).isEqualTo(OPERATOR_TYPE.LESS);

            master.setOperatorList("{\"operatorList\": [3]}");
            resultList = master.getOperatorListAsEnumList();
            assertThat(resultList.size()).isEqualTo(1);
            assertThat(resultList.get(0)).isEqualTo(OPERATOR_TYPE.IN);

            master.setOperatorList("{\"operatorList\": []}");
            resultList = master.getOperatorListAsEnumList();
            assertThat(resultList.size()).isEqualTo(0);
        }
    }
}
