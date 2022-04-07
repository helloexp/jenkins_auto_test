package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum USER_ROLE {

    S_CDUMPF_ADMINISTRATOR("S-CDUMPF-ADMINISTRATOR", "Administrator", "Administrator",
            "システム管理者", true),
    S_CDUMPF_GENERAL_USER("S-CDUMPF-GENERAL_USER", "General User", "General users who can use functions except SQL Segment",
            "SQLセグメント関連以外が使用可能な一般ユーザ", false),
    S_CDUMPF_SQL_SEGMENT_USER("S-CDUMPF-SQL_SEGMENT_USER", "SQL Segment User", "Users who can use SQL Segment in addition to the permissions that General Users have",
            "一般ユーザの権限に加えて、SQLセグメントの使用が可能となるユーザ", true),

    ;

    @Getter
    private String role;

    @Getter
    private String roleDisplayName;

    @Getter
    private String roleDescriptionEn;

    @Getter
    private String roleDescriptionJa;

    @Getter
    private boolean hasSqlSegmentRole;

    private USER_ROLE(String role, String roleDisplayName, String roleDescriptionEn, String roleDescriptionJa, boolean hasSqlSegmentRole) {
        this.role = role;
        this.roleDisplayName = roleDisplayName;
        this.roleDescriptionEn = roleDescriptionEn;
        this.roleDescriptionJa = roleDescriptionJa;
        this.hasSqlSegmentRole = hasSqlSegmentRole;
    }

    public static USER_ROLE createFromAuthority(String authority) {
        if (authority == null) {
            return null;
        }
        for (USER_ROLE userRole : values()) {
            if (authority.equals("ROLE_" + userRole.getRole())) {
                return userRole;
            }
        }
        return null;
    }
}
