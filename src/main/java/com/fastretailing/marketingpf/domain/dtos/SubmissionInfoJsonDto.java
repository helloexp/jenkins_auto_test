package com.fastretailing.marketingpf.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"mail", "adid", "idfa"})
public class SubmissionInfoJsonDto {

    @JsonProperty("1")
    public SubmissionDetail mail;

    @JsonProperty("2")
    public SubmissionDetail adid;

    @JsonProperty("3")
    public SubmissionDetail idfa;

    @JsonPropertyOrder({"userListId", "userListName"})
    public static class SubmissionDetail {

        @JsonProperty("user_list_id")
        public String userListId;

        @JsonProperty("user_list_name")
        public String userListName;

        public SubmissionDetail() {
        }

        public SubmissionDetail(String userListId, String userListName) {
            this.userListId = userListId;
            this.userListName = userListName;
        }
    }

    public SubmissionInfoJsonDto() {
    }

    public SubmissionInfoJsonDto(SubmissionDetail mail, SubmissionDetail adid, SubmissionDetail idfa) {
        this.mail = mail;
        this.adid = adid;
        this.idfa = idfa;
    }
}
