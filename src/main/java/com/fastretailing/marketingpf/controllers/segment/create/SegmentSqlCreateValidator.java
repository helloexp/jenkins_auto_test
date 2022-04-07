package com.fastretailing.marketingpf.controllers.segment.create;

import com.fastretailing.marketingpf.controllers.error.ErrorResponse.ValidationErrors;
import com.fastretailing.marketingpf.controllers.segment.create.SegmentCreateRequest.SegmentCreateRequestDto;
import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import com.fastretailing.marketingpf.utils.AuthUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class SegmentSqlCreateValidator {

    /**
     * Validate for Segment Sql request
     *
     * @param targetSegment
     */
    public void validate(SegmentCreateRequestDto targetSegment) {
        AuthUtils.preAuthorizeForSqlSegmentRole();
        if (StringUtils.isAllBlank(targetSegment.getEditedSql())) {
            throw new ValidationFailException(new ValidationErrors("editedSql", "EditedSql is null or empty"));
        }
        if (targetSegment.getEditedSql().contains(";")) {
            throw new ValidationFailException(new ValidationErrors("editedSql", "EditedSql has invalid character"));
        }
    }
}
