package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.dtos.BatchJob;
import com.fastretailing.marketingpf.domain.dtos.BatchJobList;
import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_STATUS;
import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_TYPE;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BatchJobService extends BaseUpstreamService {

    /**
     * Register batchJob to calculate number of people
     *
     * @param segmentSequence
     * @param sql
     * @param loginUserId
     * @return BatchJob
     */
    public BatchJob create(Long segmentSequence, String sql, String loginUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("sql", sql);
        map.put("segmentSequence", segmentSequence);
        map.put("createUserIdForAudit", loginUserId);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path(config.getMkdbApi().getUriPrefix() + "/segment/count/");
        return this.postForMono(builder.build().toUri(), map, BatchJob.class);
    }

    /**
     * Send request to search batchJob from MKDB
     *
     * @param batchJobType
     * @param segmentSequenceList
     * @param jobStatus
     * @param updateUserIdForAudit
     * @return BatchJobList
     */
    public BatchJobList search(
            BATCH_JOB_TYPE batchJobType,
            List<Long> segmentSequenceList,
            BATCH_JOB_STATUS jobStatus,
            String updateUserIdForAudit) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .path(config.getMkdbApi().getUriPrefix() + "/jobs/");
        if (segmentSequenceList != null) {
            builder.queryParam("segmentSequenceList", segmentSequenceList);
        }
        if (batchJobType != null) {
            builder.queryParam("batchJobType", batchJobType.getValueAsString());
        }
        if (jobStatus != null) {
            builder.queryParam("status", jobStatus.getValueAsString());
        }
        if (StringUtils.isNotEmpty(updateUserIdForAudit)) {
            builder.queryParam("updateUserIdForAudit", updateUserIdForAudit);
        }
        return this.getAsMono(builder.build().toUri(), BatchJobList.class);
    }

    /**
     * stop job
     *
     * @param batchJobSequence
     * @return BatchJob
     */
    public BatchJob stop(Long batchJobSequence) {
        URI uri = UriComponentsBuilder.newInstance()
                .path(config.getMkdbApi().getUriPrefix() + "/job/stop/" + batchJobSequence)
                .build().toUri();
        return this.putForMono(uri, BatchJob.class);
    }
}
