package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.configs.Config;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.BUSINESS_TYPE;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.SEGMENT_STATUS;
import com.fastretailing.marketingpf.domain.enums.SQL_EDIT_FLAG;
import com.fastretailing.marketingpf.domain.mappers.SegmentConditionMapper;
import com.fastretailing.marketingpf.domain.mappers.SegmentMapper;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.services.builders.SegmentQueryBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SegmentService {

    private static final Logger logger = LoggerFactory.getLogger(SegmentService.class);

    @Autowired
    public SegmentMapper segmentMapper;

    @Autowired
    public SegmentConditionMapper segmentConditionMapper;

    @Autowired
    public Config config;

    /**
     * Find segment by segmentSequence
     *
     * @param segmentSequence
     * @return Segment
     */
    public Segment findById(Long segmentSequence) {
        return segmentMapper.findById(segmentSequence);
    }

    /**
     * Create new segment
     *
     * @param businessType
     * @param brandId
     * @param countryId
     * @param deliveryScheduledMonth
     * @param eventTargetPeriodType
     * @param eventTargetPeriodStartDate
     * @param eventTargetPeriodEndDate
     * @param eventTargetPeriodRelativeNumberValue
     * @param targetItemCategory
     * @param segmentName
     * @param description
     * @param sqlEditFlag
     * @param editedSql
     * @param status
     * @param eventTypeList
     * @return Segment
     */
    public Segment create(
            String businessType,
            String brandId,
            String countryId,
            String deliveryScheduledMonth,
            String eventTargetPeriodType,
            LocalDate eventTargetPeriodStartDate,
            LocalDate eventTargetPeriodEndDate,
            Long eventTargetPeriodRelativeNumberValue,
            String eventTargetPeriodRelativePeriodUnit,
            String targetItemCategory,
            String segmentName,
            String description,
            String sqlEditFlag,
            String editedSql,
            String status,
            String eventTypeList,
            LOGICAL_OPERATOR_TYPE additionalConditionLogicalOperatorType,
            LocalDateTime createDateTimeForAudit,
            String createUserIdForAudit) {
        Segment segment = new Segment();
        segment.setBusinessType(businessType);
        segment.setBrandId(brandId);
        segment.setCountryId(countryId);
        segment.setDeliveryScheduledMonth(deliveryScheduledMonth);
        segment.setEventTargetPeriodType(eventTargetPeriodType);
        segment.setEventTargetPeriodStartDate(eventTargetPeriodStartDate);
        segment.setEventTargetPeriodEndDate(eventTargetPeriodEndDate);
        segment.setEventTargetPeriodRelativeNumberValue(eventTargetPeriodRelativeNumberValue);
        segment.setEventTargetPeriodRelativePeriodUnit(eventTargetPeriodRelativePeriodUnit);
        segment.setTargetItemCategory(targetItemCategory);
        segment.setSegmentName(segmentName);
        segment.setDescription(description);
        segment.setSqlEditFlag(sqlEditFlag);
        segment.setEditedSql(editedSql);
        segment.setStatus(status);
        segment.setEventTypeList(eventTypeList);
        segment.setAdditionalConditionLogicalOperatorType(additionalConditionLogicalOperatorType.getValueAsString());
        segment.setAuditInfoForCreation(createUserIdForAudit, createDateTimeForAudit);
        segmentMapper.create(segment);
        return segment;
    }

    /**
     * Create new SqlSegment
     *
     * @param businessType
     * @param brandId
     * @param countryId
     * @param deliveryScheduledMonth
     * @param sqlEditFlag
     * @param editedSql
     * @param segmentName
     * @param status
     * @param createDateTimeForAudit
     * @param createUserIdForAudit
     * @return Segment
     */
    public Segment createSqlSegment(
            String businessType,
            String brandId,
            String countryId,
            String deliveryScheduledMonth,
            SQL_EDIT_FLAG sqlEditFlag,
            String editedSql,
            String segmentName,
            String status,
            LocalDateTime createDateTimeForAudit,
            String createUserIdForAudit) {
        Segment sqlSegment = new Segment();
        sqlSegment.setBusinessType(businessType);
        sqlSegment.setBrandId(brandId);
        sqlSegment.setCountryId(countryId);
        sqlSegment.setDeliveryScheduledMonth(deliveryScheduledMonth);
        sqlSegment.setSqlEditFlag(sqlEditFlag.getValueAsString());
        sqlSegment.setEditedSql(editedSql);
        sqlSegment.setSegmentName(segmentName);
        sqlSegment.setStatus(status);
        sqlSegment.setAuditInfoForCreation(createUserIdForAudit, createDateTimeForAudit);

        segmentMapper.createSqlSegment(sqlSegment);
        return sqlSegment;
    }

    /**
     * Update segment
     *
     * @param businessType
     * @param brandId
     * @param countryId
     * @param deliveryScheduledMonth
     * @param eventTargetPeriodType
     * @param eventTargetPeriodStartDate
     * @param eventTargetPeriodEndDate
     * @param eventTargetPeriodRelativeNumberValue
     * @param targetItemCategory
     * @param segmentName
     * @param description
     * @param sqlEditFlag
     * @param editedSql
     * @param status
     * @param eventTypeList
     * @return Segment
     */
    public Segment update(
            Long segmentSequence,
            String businessType,
            String brandId,
            String countryId,
            String deliveryScheduledMonth,
            String eventTargetPeriodType,
            LocalDate eventTargetPeriodStartDate,
            LocalDate eventTargetPeriodEndDate,
            Long eventTargetPeriodRelativeNumberValue,
            String eventTargetPeriodRelativePeriodUnit,
            String targetItemCategory,
            String segmentName,
            String description,
            String sqlEditFlag,
            String editedSql,
            String status,
            String eventTypeList,
            LOGICAL_OPERATOR_TYPE additionalConditionLogicalOperatorType,
            LocalDateTime updateDateTimeForAudit,
            String updateUserIdForAudit) {

        Segment segment = segmentMapper.findById(segmentSequence);
        if (segment == null) {
            throw new ResourceNotFoundException();
        }
        if (businessType != null) {
            segment.setBusinessType(businessType);
        }
        if (brandId != null) {
            segment.setBrandId(brandId);
        }
        if (countryId != null) {
            segment.setCountryId(countryId);
        }
        if (deliveryScheduledMonth != null) {
            segment.setDeliveryScheduledMonth(deliveryScheduledMonth.toString());
        }
        if (eventTargetPeriodType != null) {
            segment.setEventTargetPeriodType(eventTargetPeriodType);
        }
        if (eventTargetPeriodStartDate != null) {
            segment.setEventTargetPeriodStartDate(eventTargetPeriodStartDate);
        }
        if (eventTargetPeriodEndDate != null) {
            segment.setEventTargetPeriodEndDate(eventTargetPeriodEndDate);
        }
        if (eventTargetPeriodRelativeNumberValue != null) {
            segment.setEventTargetPeriodRelativeNumberValue(eventTargetPeriodRelativeNumberValue);
        }
        if (eventTargetPeriodRelativePeriodUnit != null) {
            segment.setEventTargetPeriodRelativePeriodUnit(eventTargetPeriodRelativePeriodUnit);
        }
        if (targetItemCategory != null) {
            segment.setTargetItemCategory(targetItemCategory);
        }
        if (segmentName != null) {
            segment.setSegmentName(segmentName);
        }
        if (description != null) {
            segment.setDescription(description);
        }
        if (sqlEditFlag != null) {
            segment.setSqlEditFlag(sqlEditFlag);
        }
        if (editedSql != null) {
            segment.setEditedSql(editedSql);
        }
        if (status != null) {
            segment.setStatus(status);
        }
        if (eventTypeList != null) {
            segment.setEventTypeList(eventTypeList);
        }
        if (additionalConditionLogicalOperatorType != null) {
            segment.setAdditionalConditionLogicalOperatorType(additionalConditionLogicalOperatorType.getValueAsString());
        }
        segment.setAuditInfoForUpdate(updateUserIdForAudit, updateDateTimeForAudit);
        segmentMapper.update(segment);
        return segment;
    }

    /**
     *
     * Update sql segment
     *
     * @param segmentSequence
     * @param businessType
     * @param brandId
     * @param countryId
     * @param deliveryScheduledMonth
     * @param segmentName
     * @param description
     * @param editedSql
     * @param status
     * @param updateDateTimeForAudit
     * @param updateUserIdForAudit
     * @return Segment
     */
    public Segment updateSqlSegment(
            Long segmentSequence,
            String businessType,
            String brandId,
            String countryId,
            String deliveryScheduledMonth,
            String segmentName,
            String description,
            String editedSql,
            String status,
            LocalDateTime updateDateTimeForAudit,
            String updateUserIdForAudit) {
        Segment segment = segmentMapper.findById(segmentSequence);
        if (segment == null) {
            throw new ResourceNotFoundException();
        }
        if (businessType != null) {
            segment.setBusinessType(businessType);
        }
        if (brandId != null) {
            segment.setBrandId(brandId);
        }
        if (countryId != null) {
            segment.setCountryId(countryId);
        }
        if (deliveryScheduledMonth != null) {
            segment.setDeliveryScheduledMonth(deliveryScheduledMonth.toString());
        }
        if (segmentName != null) {
            segment.setSegmentName(segmentName);
        }
        if (description != null) {
            segment.setDescription(description);
        }
        if (editedSql != null) {
            segment.setEditedSql(editedSql);
        }
        if (status != null) {
            segment.setStatus(status);
        }

        segment.setSqlEditFlag(SQL_EDIT_FLAG.SQL_SEGMENT.getValueAsString());
        segment.setEventTargetPeriodType(null);
        segment.setEventTargetPeriodStartDate(null);
        segment.setEventTargetPeriodEndDate(null);
        segment.setEventTargetPeriodRelativeNumberValue(null);
        segment.setEventTargetPeriodRelativePeriodUnit(null);
        segment.setTargetItemCategory(null);
        segment.setEventTypeList(null);
        segment.setAdditionalConditionLogicalOperatorType(null);

        segment.setAuditInfoForUpdate(updateUserIdForAudit, updateDateTimeForAudit);
        segmentMapper.update(segment);
        return segment;
    }

    /**
     * Delete segment by segmentSequence
     *
     * @param segmentSequence
     * @return Segment
     */
    public Segment delete(Long segmentSequence, LocalDateTime deletionDateTime, String deletionUserId) {
        Segment segment = segmentMapper.findById(segmentSequence);
        if (segment == null) {
            throw new ResourceNotFoundException();
        }
        segment.setAuditInfoForDeletion(deletionUserId, deletionDateTime);
        segmentMapper.delete(segment);
        return segment;
    }

    /**
     * Build query from segment and conditions<br>
     * Only allow basic segment
     *
     * @param segmentSequence
     * @return String
     */
    public String buildSqlBySegmentSequence(Long segmentSequence) {
        Segment segment = segmentMapper.findById(segmentSequence);
        if (segment == null) {
            throw new ResourceNotFoundException();
        }
        if (segment.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.SQL_SEGMENT) {
            throw new RuntimeException("Cannot build query for sql segment, segment sequence = " + segmentSequence);
        }
        List<SegmentCondition> segmentConditionList = new ArrayList<>();
        for (SegmentCondition c : segmentConditionMapper.findListBySegmentSequence(segmentSequence)) {
            if (c.getSegmentItemForSqlMaster() == null) {
                logger.warn("Data integrity problem, missing segmentItemForSqlMaster, segmentConditionSequence = " + c.getSegmentConditionSequence());
                continue;
            }
            if (c.getSegmentItemForScreenMaster() == null) {
                throw new RuntimeException("Data integrity error, missing segmentItemForScreenMaster, segmentConditionSequence = " + c.getSegmentConditionSequence());
            }
            segmentConditionList.add(c);
        }
        if (CollectionUtils.isEmpty(segmentConditionList)) {
            throw new RuntimeException("Data integrity error, no valid segment conditions, segmentSequence = " + segmentSequence);
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("ENV", config.getEnv());
        paramMap.put("DATASET_GA_UQJP", config.getDatasetGaUqjp());
        paramMap.put("DATASET_GA_GUJP", config.getDatasetGaGujp());
        paramMap.put("DATASET_GA_UQJP_APP", config.getDatasetGaUqjpApp());
        SegmentQueryBuilder segmentQueryBuilder = new SegmentQueryBuilder(
                segment, segmentConditionList, paramMap);
        return segmentQueryBuilder.build();
    }

    /**
     * Get segment query<br>
     * Return editedSql if segment is sql segment<br>
     * Build and return generated sql for basic segment
     *
     * @param segmentSequence
     * @return String
     */
    public String getSegmentQueryBySegmentSequence(Long segmentSequence) {
        Segment segment = segmentMapper.findById(segmentSequence);
        if (segment == null) {
            throw new ResourceNotFoundException();
        }
        if (segment.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.SQL_SEGMENT) {
            return segment.getEditedSql();
        }
        return this.buildSqlBySegmentSequence(segmentSequence);
    }

    /**
     * Find SegmentList By SegmentName
     *
     * @param segmentName
     * @return List<Segment>
     */
    public List<Segment> findListBySegmentName(String segmentName) {
        return segmentMapper.findListBySegmentName(segmentName);
    }

    /**
     * search segment
     *
     * @param segmentSequence
     * @param businessType
     * @param deliveryScheduledMonth
     * @param brandId
     * @param countryId
     * @param status
     * @param eventTypeList
     * @param eventTargetPeriodType
     * @param eventTargetPeriodStartDate
     * @param eventTargetPeriodEndDate
     * @param eventTargetPeriodRelativeNumberValue
     * @param eventTargetPeriodRelativePeriodUnit
     * @param segmentName
     * @return List<<code>Segment</code>>
     */
    public List<Segment> search(
            Long segmentSequence,
            BUSINESS_TYPE businessType,
            String deliveryScheduledMonth,
            BRAND brandId,
            COUNTRY countryId,
            SEGMENT_STATUS status,
            List<Long> eventTypeList,
            EVENT_TARGET_PERIOD_TYPE eventTargetPeriodType,
            LocalDate eventTargetPeriodStartDate,
            LocalDate eventTargetPeriodEndDate,
            Integer eventTargetPeriodRelativeNumberValue,
            EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT eventTargetPeriodRelativePeriodUnit,
            String segmentName) {
        return segmentMapper.search(
                segmentSequence,
                businessType == null ? null : businessType.getValueAsString(),
                deliveryScheduledMonth,
                brandId == null ? null : brandId.getCode(),
                countryId == null ? null : countryId.getCode(),
                status == null ? null : status.getValueAsString(),
                eventTypeList,
                eventTargetPeriodType == null ? null : eventTargetPeriodType.getValueAsString(),
                eventTargetPeriodStartDate,
                eventTargetPeriodEndDate,
                eventTargetPeriodRelativeNumberValue,
                eventTargetPeriodRelativePeriodUnit == null ? null : eventTargetPeriodRelativePeriodUnit.getValueAsString(),
                segmentName != null ? "%" + segmentName.replace("%", "\\%") + "%" : null);
    }

    /**
     * create multi segment query
     *
     * @param segmentSequenceList
     * @param brand
     * @param country
     * @param extractionTargetIdList
     * @return String
     */
    public String createMultiSegmentQuery(List<Long> segmentSequenceList, BRAND brand, COUNTRY country, List<EXTRACTION_TARGET_ID> extractionTargetIdList) {
        String query = segmentSequenceList.stream().map(e -> "(" + this.getSegmentQueryBySegmentSequence(e) + ")").collect(Collectors.joining(" UNION DISTINCT "));
        if (extractionTargetIdList.stream().anyMatch(Arrays.asList(EXTRACTION_TARGET_ID.ADID, EXTRACTION_TARGET_ID.IDFA)::contains)) {
            return "select segment_query.uid as uid, mobile_ids.advrtsng_id as adid, mobile_ids.idfa as idfa"
                    + " from (" + query + ") as segment_query"
                    + " left join " + "fr-" + config.getEnv() + "-mdb" + "." + this.getDatasetNameAndTargetUidMobileIdViewByBrandCountry(brand, country)
                    + " as mobile_ids on mobile_ids.uid = segment_query.uid";
        }
        return query;
    }

    /**
     * get dataset name, target uid mobileId view by brand and country
     *
     * @param brand
     * @param country
     * @return String
     */
    protected String getDatasetNameAndTargetUidMobileIdViewByBrandCountry(BRAND brand, COUNTRY country) {
        if (brand == BRAND.UQ && country == COUNTRY.JP) {
            return "pii_" + config.getEnv() + "_uqjp" + "." + "dmp_vw_m_apsflyr_uqjp";
        } else if (brand == BRAND.GU && country == COUNTRY.JP) {
            return "pii_" + config.getEnv() + "_gujp" + "." + "dmp_vw_m_apsflyr_gujp";
        } else {
            throw new RuntimeException("This service is only support brand GU/UQ and country JP");
        }
    }

    /**
     * Update status by segmentSequence
     *
     * @param segmentSequence
     * @param status
     * @return Segment
     */
    public Segment updateStatusBySegmentSequence(Long segmentSequence, SEGMENT_STATUS status, String loginUserId, LocalDateTime updatedDateTime) {
        Segment segment = segmentMapper.findById(segmentSequence);
        if (segment == null) {
            throw new ResourceNotFoundException();
        }
        segment.setStatus(status.getValueAsString());
        segment.setAuditInfoForUpdate(loginUserId, updatedDateTime);
        segmentMapper.update(segment);
        return segment;
    }
}
