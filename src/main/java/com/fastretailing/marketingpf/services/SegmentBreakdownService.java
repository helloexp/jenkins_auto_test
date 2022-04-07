package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.dtos.SegmentBreakdown;
import com.fastretailing.marketingpf.domain.dtos.SegmentBreakdownList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SegmentBreakdownService extends BaseUpstreamService {

    /**
     * Find SegmentBreakdown by segmentSequence
     *
     * @param segmentSequence
     * @return SegmentBreakdown
     */
    public SegmentBreakdown findBySegmentSequence(Long segmentSequence) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .path(config.getMkdbApi().getUriPrefix() + "/segment/breakdowns/")
                .queryParam("segmentSequenceList", Arrays.asList(segmentSequence));
        SegmentBreakdownList segmentBreakdownList = this.getAsMono(builder.build().toUri(), SegmentBreakdownList.class);
        if (segmentBreakdownList.getSegmentBreakdownList().isEmpty()) {
            return null;
        }
        return segmentBreakdownList.getSegmentBreakdownList().get(0);
    }

    /**
     * Find SegmentBreakdown list by segmentSequenceList, numberOfPeopleValue, numberOfPeopleConditions
     *
     * @param segmentSequenceList
     * @param numberOfPeopleValue
     * @param numberOfPeopleConditions
     * @return List<SegmentBreakdown>
     */
    public List<SegmentBreakdown> search(List<Long> segmentSequenceList, Long numberOfPeopleValue, Integer numberOfPeopleConditions) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .path(config.getMkdbApi().getUriPrefix() + "/segment/breakdowns/")
                .queryParam("segmentSequenceList", segmentSequenceList)
                .queryParam("numberOfPeopleValue", numberOfPeopleValue)
                .queryParam("numberOfPeopleConditions", numberOfPeopleConditions);

        SegmentBreakdownList segmentBreakdownList = this.getAsMono(builder.build().toUri(), SegmentBreakdownList.class);
        return segmentBreakdownList.getSegmentBreakdownList();
    }
}
