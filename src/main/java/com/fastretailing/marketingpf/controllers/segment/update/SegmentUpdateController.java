package com.fastretailing.marketingpf.controllers.segment.update;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.controllers.error.ErrorResponse.ValidationErrors;
import com.fastretailing.marketingpf.controllers.segment.update.SegmentUpdateRequest.SegmentConditionDto;
import com.fastretailing.marketingpf.controllers.segment.update.SegmentUpdateRequest.SegmentDto;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForSqlMaster;
import com.fastretailing.marketingpf.domain.enums.SQL_EDIT_FLAG;
import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import com.fastretailing.marketingpf.services.SegmentConditionService;
import com.fastretailing.marketingpf.services.SegmentItemForSqlMasterService;
import com.fastretailing.marketingpf.services.SegmentService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SegmentUpdateController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SegmentUpdateController.class);

    @Autowired
    public SegmentService segmentService;

    @Autowired
    public SegmentConditionService segmentConditionService;

    @Autowired
    public SegmentItemForSqlMasterService segmentItemForSqlMasterService;

    @Autowired
    public SegmentUpdateValidator segmentUpdateValidator;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SegmentUpdateResponse.class))})
    })
    @PutMapping("/control_of_segment_related_tables/{segmentSequence}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> update(@PathVariable Long segmentSequence, @RequestBody @Valid SegmentUpdateRequest request, HttpServletRequest httpRequest) {
        SegmentDto s = request.getTargetSegment();

        segmentUpdateValidator.validate(segmentSequence, s);

        Segment existingSegment = segmentService.findById(segmentSequence);

        // Update sql or convert to sql
        if (existingSegment.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.SQL_SEGMENT || s.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.SQL_SEGMENT) {
            Segment updatedSegment = segmentService.updateSqlSegment(
                    segmentSequence,
                    s.getBusinessType(),
                    s.getBrandId(),
                    s.getCountryId(),
                    s.getDeliveryScheduledMonth(),
                    s.getSegmentName(),
                    s.getDescription(),
                    s.getEditedSql(),
                    s.getStatus(),
                    LocalDateTime.now(),
                    getLoginUserId());
            // Delete current SegmentCondition
            List<SegmentCondition> segmentConditionList = segmentConditionService.findListBySegmentSequence(segmentSequence);
            for (SegmentCondition segmentCondition : segmentConditionList) {
                segmentConditionService.delete(segmentCondition.getSegmentConditionSequence(), LocalDateTime.now(), getLoginUserId());
            }
            return ok(new SegmentUpdateResponse(updatedSegment, new ArrayList<>()));
        }

        // Update basic segment
        Segment segment = segmentService.update(
                segmentSequence,
                s.getBusinessType(),
                s.getBrandId(),
                s.getCountryId(),
                s.getDeliveryScheduledMonth(),
                s.getEventTargetPeriodType(),
                s.getEventTargetPeriodStartDate(),
                s.getEventTargetPeriodEndDate(),
                s.getEventTargetPeriodRelativeNumberValue(),
                s.getEventTargetPeriodRelativePeriodUnit(),
                s.getTargetItemCategory(),
                s.getSegmentName(),
                s.getDescription(),
                s.getSqlEditFlag(),
                s.getEditedSql(),
                s.getStatus(),
                s.getEventTypeListAsString(),
                s.getAdditionalConditionLogicalOperatorTypeAsEnum(),
                LocalDateTime.now(),
                getLoginUserId());

        // Delete current SegmentCondition
        List<SegmentCondition> segmentConditionList = segmentConditionService.findListBySegmentSequence(segmentSequence);
        for (SegmentCondition segmentCondition : segmentConditionList) {
            segmentConditionService.delete(segmentCondition.getSegmentConditionSequence(), LocalDateTime.now(), getLoginUserId());
        }

        // Insert new SegmentCondition
        List<SegmentCondition> updatedSegmentConditionList = new ArrayList<>();
        // Create basic segment condition
        for (SegmentConditionDto basicCondition : request.getTargetSegment().getBasicSegmentConditionList()) {
            if (basicCondition.getComparisonValue().isEmpty()) {
                // If comparisonValue is empty, we will ignore process create segmentCondition for this conditionRequestDto
                logger.info("ComparisonValue is empty. SegmentItemForScreenSequence = " + basicCondition.getSegmentItemForScreenSequence(),
                        StructuredArguments.keyValue("XID", httpRequest.getAttribute("requestId")));
                continue;
            }

            List<SegmentItemForSqlMaster> segmentItemForSqlMasterList = segmentItemForSqlMasterService.findBySegmentItemForScreenSequenceAndConditions(basicCondition.getSegmentItemForScreenSequence(),
                    request.getTargetSegment().getEventTypeList(), request.getTargetSegment().getBrandId(), request.getTargetSegment().getCountryId());

            if (CollectionUtils.isEmpty(segmentItemForSqlMasterList)) {
                // If segmentItemForSqlMaster = null, we will ignore process create segmentCondition for this conditionRequestDto
                logger.info("Can not found segmentItemForSqlMaster", StructuredArguments.keyValue("XID", httpRequest.getAttribute("requestId")));
                continue;
            }

            for (SegmentItemForSqlMaster segmentItemForSqlMaster : segmentItemForSqlMasterList) {
                SegmentCondition newSegmentCondition = segmentConditionService.create(
                        segment.getSegmentSequence(),
                        basicCondition.getSegmentItemForScreenSequence(),
                        segmentItemForSqlMaster.getSegmentItemForSqlSequence(),
                        basicCondition.getOperatorTypeAsEnum(),
                        basicCondition.getComparisonValueAsString(),
                        basicCondition.getSegmentConditionBlockOrder(),
                        basicCondition.getLogicalOperatorTypeAsEnum(),
                        LocalDateTime.now(),
                        getLoginUserId());
                updatedSegmentConditionList.add(newSegmentCondition);
            }
        }

        // Create extra segment condition
        for (SegmentConditionDto extraCondition : request.getTargetSegment().getExtraSegmentConditionList()) {
            if (extraCondition.getComparisonValue().isEmpty()) {
                // If comparisonValue is empty, we will ignore process create segmentCondition for this conditionRequestDto
                logger.info("ComparisonValue is empty. SegmentItemForScreenSequence = " + extraCondition.getSegmentItemForScreenSequence(),
                        StructuredArguments.keyValue("XID", httpRequest.getAttribute("requestId")));
                continue;
            }

            SegmentItemForSqlMaster segmentItemForSqlMaster = segmentItemForSqlMasterService.findByConditions(extraCondition.getSegmentItemForScreenSequence(),
                    request.getTargetSegment().getBrandId(), request.getTargetSegment().getCountryId());

            if (segmentItemForSqlMaster == null) {
                // If segmentItemForSqlMaster = null, we will ignore process create segmentCondition for this conditionRequestDto
                logger.info("Can not found segmentItemForSqlMaster", StructuredArguments.keyValue("XID", httpRequest.getAttribute("requestId")));
                continue;
            }

            SegmentCondition newSegmentCondition = segmentConditionService.create(
                    segment.getSegmentSequence(),
                    extraCondition.getSegmentItemForScreenSequence(),
                    segmentItemForSqlMaster.getSegmentItemForSqlSequence(),
                    extraCondition.getOperatorTypeAsEnum(),
                    extraCondition.getComparisonValueAsString(),
                    extraCondition.getSegmentConditionBlockOrder(),
                    extraCondition.getLogicalOperatorTypeAsEnum(),
                    LocalDateTime.now(),
                    getLoginUserId());
            updatedSegmentConditionList.add(newSegmentCondition);
        }
        if (updatedSegmentConditionList.isEmpty()) {
            throw new ValidationFailException(new ValidationErrors("segmentConditionList", "segmentConditionList has no valid item"));
        }

        return ok(new SegmentUpdateResponse(segment, updatedSegmentConditionList));
    }
}
