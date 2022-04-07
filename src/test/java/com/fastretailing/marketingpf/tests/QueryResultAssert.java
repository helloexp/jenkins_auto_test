package com.fastretailing.marketingpf.tests;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.AbstractAssert;

/**
 * Using to assert query result<br>
 * The assertion use string comparison. Data must be same type and value to be matched
 *
 * <code>
 * assertQueryResult("SELECT ad_acnt_seq, del_flg_f_adt FROM t_ad_acnt ORDER BY ad_acnt_seq ASC").isSameAs("1,f");
 * </code>
 */
public class QueryResultAssert extends AbstractAssert<QueryResultAssert, List<String>> {

    public QueryResultAssert(List<String> list) {
        super(list, QueryResultAssert.class);
    }

    public QueryResultAssert isSameDataAs(String... exepected) {
        isNotNull();
        List<String> exepectedList = new ArrayList<>();
        for (String e : exepected) {
            exepectedList.add(e);
        }
        assertThat(actual).hasSameElementsAs(exepectedList);
        return this;
    }
}
