package com.fastretailing.marketingpf.controllers.segment.create;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.controllers.error.ErrorResponse.ValidationErrors;
import com.fastretailing.marketingpf.controllers.segment.create.SegmentCreateRequest.SegmentConditionRequestDto;
import com.fastretailing.marketingpf.controllers.segment.create.SegmentCreateRequest.SegmentCreateRequestDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SegmentCreateController extends BaseController {

    @Autowired
    public SegmentService segmentService;

    @Autowired
    public SegmentConditionService segmentConditionService;

    @Autowired
    public SegmentItemForSqlMasterService segmentItemForSqlMasterService;

    @Autowired
    public SegmentBasicCreateValidator segmentBasicCreateValidator;

    @Autowired
    public SegmentSqlCreateValidator segmentSqlCreateValidator;

    private static final Logger logger = LoggerFactory.getLogger(SegmentCreateController.class);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SegmentCreateResponse.class))}),
    })
    @PostMapping("/control_of_segment_related_tables/")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> create(@Valid @RequestBody SegmentCreateRequest request, HttpServletRequest httpRequest) {
        SegmentCreateRequestDto targetSegment = request.getTargetSegment();

        if (targetSegment.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.SQL_SEGMENT) {
            segmentSqlCreateValidator.validate(targetSegment);
            // Create SqlSegment
            Segment segment = segmentService.createSqlSegment(
                    targetSegment.getBusinessType(),
                    targetSegment.getBrandId(),
                    targetSegment.getCountryId(),
                    targetSegment.getDeliveryScheduledMonth(),
                    targetSegment.getSqlEditFlagAsEnum(),
                    targetSegment.getEditedSql(),
                    targetSegment.getSegmentName(),
                    targetSegment.getStatus(),
                    LocalDateTime.now(),
                    getLoginUserId());
            return ok(new SegmentCreateResponse(segment, new ArrayList<>()));
        }

        segmentBasicCreateValidator.validate(targetSegment);
        // Create basic segment
        Segment segment = segmentService.create(
                targetSegment.getBusinessType(),
                targetSegment.getBrandId(),
                targetSegment.getCountryId(),
                targetSegment.getDeliveryScheduledMonth(),
                targetSegment.getEventTargetPeriodType(),
                targetSegment.getEventTargetPeriodStartDate(),
                targetSegment.getEventTargetPeriodEndDate(),
                targetSegment.getEventTargetPeriodRelativeNumberValue(),
                targetSegment.getEventTargetPeriodRelativePeriodUnit(),
                targetSegment.getTargetItemCategory(),
                targetSegment.getSegmentName(),
                targetSegment.getDescription(),
                targetSegment.getSqlEditFlag(),
                targetSegment.getEditedSql(),
                targetSegment.getStatus(),
                targetSegment.getEventTypeListAsString(),
                targetSegment.getAdditionalConditionLogicalOperatorTypeAsEnum(),
                LocalDateTime.now(),
                getLoginUserId());

        List<SegmentCondition> createdSegmentConditionList = new ArrayList<>();
        // Create basic segment condition
        for (SegmentConditionRequestDto basicCondition : request.getTargetSegment().getBasicSegmentConditionList()) {
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
                createdSegmentConditionList.add(newSegmentCondition);
            }
        }

        // Create extra segment condition
        for (SegmentConditionRequestDto extraCondition : request.getTargetSegment().getExtraSegmentConditionList()) {
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
            createdSegmentConditionList.add(newSegmentCondition);
        }
        if (createdSegmentConditionList.isEmpty()) {
            throw new ValidationFailException(new ValidationErrors("segmentConditionList", "segmentConditionList has no valid item"));
        }

        return ok(new SegmentCreateResponse(segment, createdSegmentConditionList));
    }
}
