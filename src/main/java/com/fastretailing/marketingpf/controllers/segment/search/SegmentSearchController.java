package com.fastretailing.marketingpf.controllers.segment.search;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.domain.dtos.SegmentBreakdown;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.services.SegmentBreakdownService;
import com.fastretailing.marketingpf.services.SegmentService;
import com.fastretailing.marketingpf.services.UserMasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SegmentSearchController extends BaseController {

    @Autowired
    public SegmentService segmentService;

    @Autowired
    public SegmentBreakdownService segmentBreakdownService;

    @Autowired
    public UserMasterService userMasterService;

    @Autowired
    public SegmentSearchValidator validator;

    @Operation(summary = "Get segment list", description = "Get segment list")
    @Parameters({
            @Parameter(name = "segmentSequence", in = ParameterIn.QUERY, schema = @Schema(description = "Segment Sequence", type = "Long")),
            @Parameter(name = "businessType", in = ParameterIn.QUERY, schema = @Schema(description = "Business Type", type = "String")),
            @Parameter(name = "deliveryScheduledMonth", in = ParameterIn.QUERY, schema = @Schema(description = "Delivery Scheduled Month", type = "String")),
            @Parameter(name = "brandId", in = ParameterIn.QUERY, schema = @Schema(description = "Brand Id", type = "String")),
            @Parameter(name = "countryId", in = ParameterIn.QUERY, schema = @Schema(description = "Country Id", type = "String")),
            @Parameter(name = "status", in = ParameterIn.QUERY, schema = @Schema(description = "Status", type = "String")),
            @Parameter(name = "eventTypeList", in = ParameterIn.QUERY, schema = @Schema(description = "Event Type List", type = "List")),
            @Parameter(name = "eventTargetPeriodType", in = ParameterIn.QUERY, schema = @Schema(description = "Event Target Period Type", type = "String")),
            @Parameter(name = "eventTargetPeriodStartDate", in = ParameterIn.QUERY, schema = @Schema(description = "Event Target Period Start Date", type = "String")),
            @Parameter(name = "eventTargetPeriodEndDate", in = ParameterIn.QUERY, schema = @Schema(description = "Event Target Period End Date", type = "String")),
            @Parameter(name = "eventTargetPeriodRelativeNumberValue", in = ParameterIn.QUERY, schema = @Schema(description = "Event Target Period Relative Number Value", type = "Integer")),
            @Parameter(name = "eventTargetPeriodRelativePeriodUnit", in = ParameterIn.QUERY, schema = @Schema(description = "Event Target Period Relative Period Unit", type = "String")),
            @Parameter(name = "segmentName", in = ParameterIn.QUERY, schema = @Schema(description = "Segment Name", type = "String")),
            @Parameter(name = "numberOfPeopleValue", in = ParameterIn.QUERY, schema = @Schema(description = "Number Of People Value", type = "Long")),
            @Parameter(name = "numberOfPeopleConditions", in = ParameterIn.QUERY, schema = @Schema(description = "Number Of People Conditions", type = "Integer")),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SegmentSearchResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "Authentication error")
    })
    @GetMapping("/segments/")
    public ResponseEntity<Object> search(@Parameter(hidden = true) @ModelAttribute @Valid SegmentSearchRequest request) {
        validator.validate(request);

        List<Segment> segmentList = segmentService.search(
                request.getSegmentSequence(),
                request.getBusinessTypeAsEnum(),
                request.getDeliveryScheduledMonth(),
                request.getBrandIdAsEnum(),
                request.getCountryIdAsEnum(),
                request.getStatusAsEnum(),
                request.getEventTypeList(),
                request.getEventTargetPeriodTypeAsEnum(),
                request.getEventTargetPeriodStartDate(),
                request.getEventTargetPeriodEndDate(),
                request.getEventTargetPeriodRelativeNumberValue(),
                request.getEventTargetPeriodRelativePeriodUnitAsEnum(),
                request.getSegmentName());
        List<Long> segmentSequenceList = segmentList.stream().map(Segment::getSegmentSequence).collect(Collectors.toList());
        List<SegmentBreakdown> segmentBreakdownList = segmentBreakdownService.search(
                segmentSequenceList,
                request.getNumberOfPeopleValue(),
                request.getNumberOfPeopleConditions());

        Map<Long, SegmentBreakdown> segmentBreakdownMap = segmentBreakdownList.stream()
                .collect(Collectors.toMap(SegmentBreakdown::getSegmentSequence, e -> e));

        if (request.getNumberOfPeopleValue() != null && request.getNumberOfPeopleConditions() != null) {
            List<Segment> segmentHasBreakdownList = segmentList.stream().filter(segment -> segmentBreakdownMap.get(segment.getSegmentSequence()) != null).collect(Collectors.toList());
            return ok(new SegmentSearchResponse(segmentHasBreakdownList, segmentBreakdownMap, userMasterService.getUserIdToUserNameMap()));
        }

        return ok(new SegmentSearchResponse(segmentList, segmentBreakdownMap, userMasterService.getUserIdToUserNameMap()));
    }
}
