package com.fastretailing.marketingpf.controllers.outboundSettingSegmentIntermediate.create;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.CustomAudience;
import com.fastretailing.marketingpf.domain.dtos.AdAccount;
import com.fastretailing.marketingpf.domain.dtos.AdAccount.AdAccountDto;
import com.fastretailing.marketingpf.domain.dtos.BatchJob;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.USER_ROLE;
import com.fastretailing.marketingpf.services.AdAccountService;
import com.fastretailing.marketingpf.services.FacebookAdsService;
import com.fastretailing.marketingpf.services.GoogleAdsService;
import com.fastretailing.marketingpf.services.SegmentService;
import com.fastretailing.marketingpf.services.UserListUploadService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class OutboundSettingSegmentIntermediateCreateControllerTest extends BaseSpringBootTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserListUploadService userListUploadService;

    @MockBean
    private GoogleAdsService googleAdsService;

    @MockBean
    private FacebookAdsService facebookAdsService;

    @MockBean
    private AdAccountService adAccountService;

    @MockBean
    public SegmentService segmentService;

    @BeforeEach
    public void setup() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("oid", "user_01");
        claims.put("name", "Fuga");
        claims.put("sub", "sub");
        OidcIdToken token = new OidcIdToken("hoge", null, null, claims);
        DefaultOidcUser principal = new DefaultOidcUser(new ArrayList<>(), token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_SQL_SEGMENT_USER.getRole())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @Sql("/controllers/OutboundSettingSegmentIntermediateCreateControllerTest/t_sgmt.sql")
    @Sql("/controllers/OutboundSettingSegmentIntermediateCreateControllerTest/t_outbound_stng.sql")
    @Sql("/controllers/OutboundSettingSegmentIntermediateCreateControllerTest/m_otpt_pltfrm.sql")
    @Sql("/controllers/OutboundSettingSegmentIntermediateCreateControllerTest/t_otpt_pltfrm_outbound_stng_intrm.sql")
    @Sql("/controllers/OutboundSettingSegmentIntermediateCreateControllerTest/t_outbound_stng_sgmt_intrm.sql")
    @Sql("/controllers/OutboundSettingSegmentIntermediateCreateControllerTest/m_ads_pltfrm.sql")
    @Sql("/controllers/OutboundSettingSegmentIntermediateCreateControllerTest/t_api_auth_info.sql")
    public void expectingSuccess() throws Exception {
        AdAccountDto adAccountDto = new AdAccountDto();
        adAccountDto.setAdAccountSequence(1101L);
        adAccountDto.setAccountName("accountName");
        adAccountDto.setLoginCustomerId("98521");
        adAccountDto.setAdsPlatformSequence(1L);
        adAccountDto.setAdsPlatformAccountId("456");
        adAccountDto.setBrandId(BRAND.GU.getCode());
        adAccountDto.setBrandId(COUNTRY.JP.getCode());
        adAccountDto.setApiAuthenticationInformationSequence(1001L);

        AdAccount adAccount = new AdAccount();
        adAccount.setAdAccount(adAccountDto);
        CustomAudience customAudience = new CustomAudience("1234567", new APIContext("accessToken"));
        doReturn(adAccount).when(adAccountService).findById(Mockito.anyLong());
        doReturn("123456").when(googleAdsService).createAudience(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        doReturn(customAudience).when(facebookAdsService).createCustomUserProvidedAudience(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        mvc.perform(post("/api/marketingpf/v1/fr/jp/outbound_setting_segment_intermediate/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "    \"brandId\": \"010\"," +
                        "    \"countryId\": \"1\"," +
                        "    \"businessType\": 2," +
                        "    \"submissionTimingType\": 2," +
                        "    \"reserveSubmissionDateTime\": \"2020-12-12T00:00:01\"," +
                        "    \"regularlySubmissionFrequencyDateTimeBasis\": \"2020-12-12T00:00:01\"," +
                        "    \"regularlySubmissionFrequencyPeriodNumberValue\": 1," +
                        "    \"regularlySubmissionFrequencyPeriodUnit\": 1," +
                        "    \"submissionCompletionContactList\": [\"mail1\", \"mail2\"]," +
                        "    \"outboundSettingName\": \"outboundSettingName\"," +
                        "    \"segmentSequenceList\": [2001, 2002]," +
                        "    \"adsPlatformSequence\": 1," +
                        "    \"adsPfLoginUserId\": \"5555\"," +
                        "    \"adAccountSequence\": 1001," +
                        "    \"extractionTargetId\": [1, 2, 3]" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals("{" +
                        "    \"targetOutboundSetting\": {" +
                        "        \"outboundSettingSegmentIntermediateSequenceList\": [1003, 1004]," +
                        "        \"segmentSequenceList\": \"[2001,2002]\"," +
                        "        \"outboundSettingSequence\": 1003," +
                        "        \"brandId\": \"010\"," +
                        "        \"countryId\": \"1\"," +
                        "        \"businessType\": \"2\"," +
                        "        \"submissionTimingType\": \"2\"," +
                        "        \"reserveSubmissionDateTime\": \"2020-12-12 00:00:01\"," +
                        "        \"regularlySubmissionFrequencyDateTimeBasis\": \"2020-12-12 00:00:01\"," +
                        "        \"regularlySubmissionFrequencyPeriodNumberValue\": 1," +
                        "        \"regularlySubmissionFrequencyPeriodUnit\": 1," +
                        "        \"submissionCompletionContact\": \"[\\\"mail1\\\",\\\"mail2\\\"]\"," +
                        "        \"outboundSettingName\": \"outboundSettingName\"," +
                        "        \"outputPlatformOutboundSettingIntermediateInfoList\": [{" +
                        "                \"outputPlatformOutboundSettingIntermediateSequence\": 1003," +
                        "                \"outboundSettingSequence\": 1003," +
                        "                \"outputPlatformSequence\": 1001," +
                        "                \"outputPlatformType\": \"1\"," +
                        "                \"adAccountSequence\": 1001," +
                        "                \"adsPlatformSequence\": 1," +
                        "                \"crmPlatformSequence\": null," +
                        "                \"audienceId\": \"123456\"," +
                        "                \"userListName\": \"outboundSettingName_Mail Address\"," +
                        "                \"extractionTargetId\": \"1\"" +
                        "            }, {" +
                        "                \"outputPlatformOutboundSettingIntermediateSequence\": 1004," +
                        "                \"outboundSettingSequence\": 1003," +
                        "                \"outputPlatformSequence\": 1001," +
                        "                \"outputPlatformType\": \"1\"," +
                        "                \"adAccountSequence\": 1001," +
                        "                \"adsPlatformSequence\": 1," +
                        "                \"crmPlatformSequence\": null," +
                        "                \"audienceId\": \"123456\"," +
                        "                \"userListName\": \"outboundSettingName_ADID\"," +
                        "                \"extractionTargetId\": \"2\"" +
                        "            }, {" +
                        "                \"outputPlatformOutboundSettingIntermediateSequence\": 1005," +
                        "                \"outboundSettingSequence\": 1003," +
                        "                \"outputPlatformSequence\": 1001," +
                        "                \"outputPlatformType\": \"1\"," +
                        "                \"adAccountSequence\": 1001," +
                        "                \"adsPlatformSequence\": 1," +
                        "                \"crmPlatformSequence\": null," +
                        "                \"audienceId\": \"123456\"," +
                        "                \"userListName\": \"outboundSettingName_IDFA\"," +
                        "                \"extractionTargetId\": \"3\"" +
                        "            }" +
                        "        ]" +
                        "    }" +
                        "}"));

        doReturn(new BatchJob()).when(userListUploadService).uploadUserList(Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyString());
        doReturn("query").when(segmentService).createMultiSegmentQuery(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        mvc.perform(post("/api/marketingpf/v1/fr/jp/outbound_setting_segment_intermediate/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "    \"brandId\": \"090\"," +
                        "    \"countryId\": \"1\"," +
                        "    \"businessType\": 2," +
                        "    \"submissionTimingType\": 1," +
                        "    \"reserveSubmissionDateTime\": \"2020-12-15T00:00:01\"," +
                        "    \"regularlySubmissionFrequencyDateTimeBasis\": \"2020-12-15T00:00:01\"," +
                        "    \"regularlySubmissionFrequencyPeriodNumberValue\": 1," +
                        "    \"regularlySubmissionFrequencyPeriodUnit\": 2," +
                        "    \"submissionCompletionContactList\": [\"mail1\", \"mail2\"]," +
                        "    \"outboundSettingName\": \"outboundSettingName\"," +
                        "    \"segmentSequenceList\": [2001, 2002]," +
                        "    \"adsPlatformSequence\": 1," +
                        "    \"adsPfLoginUserId\": \"5555\"," +
                        "    \"adAccountSequence\": 1001," +
                        "    \"extractionTargetId\": [1, 2, 3]" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals("{" +
                        "    \"targetOutboundSetting\": {" +
                        "        \"outboundSettingSegmentIntermediateSequenceList\": [1005, 1006]," +
                        "        \"segmentSequenceList\": \"[2001,2002]\"," +
                        "        \"outboundSettingSequence\": 1004," +
                        "        \"brandId\": \"090\"," +
                        "        \"countryId\": \"1\"," +
                        "        \"businessType\": \"2\"," +
                        "        \"submissionTimingType\": \"1\"," +
                        "        \"reserveSubmissionDateTime\": \"2020-12-15 00:00:01\"," +
                        "        \"regularlySubmissionFrequencyDateTimeBasis\": \"2020-12-15 00:00:01\"," +
                        "        \"regularlySubmissionFrequencyPeriodNumberValue\": 1," +
                        "        \"regularlySubmissionFrequencyPeriodUnit\": 2," +
                        "        \"submissionCompletionContact\": \"[\\\"mail1\\\",\\\"mail2\\\"]\"," +
                        "        \"outboundSettingName\": \"outboundSettingName\"," +
                        "        \"outputPlatformOutboundSettingIntermediateInfoList\": [{" +
                        "                \"outputPlatformOutboundSettingIntermediateSequence\": 1006," +
                        "                \"outboundSettingSequence\": 1004," +
                        "                \"outputPlatformSequence\": 1001," +
                        "                \"outputPlatformType\": \"1\"," +
                        "                \"adAccountSequence\": 1001," +
                        "                \"adsPlatformSequence\": 1," +
                        "                \"crmPlatformSequence\": null," +
                        "                \"audienceId\": \"123456\"," +
                        "                \"userListName\": \"outboundSettingName_Mail Address\"," +
                        "                \"extractionTargetId\": \"1\"" +
                        "            }, {" +
                        "                \"outputPlatformOutboundSettingIntermediateSequence\": 1007," +
                        "                \"outboundSettingSequence\": 1004," +
                        "                \"outputPlatformSequence\": 1001," +
                        "                \"outputPlatformType\": \"1\"," +
                        "                \"adAccountSequence\": 1001," +
                        "                \"adsPlatformSequence\": 1," +
                        "                \"crmPlatformSequence\": null," +
                        "                \"audienceId\": \"123456\"," +
                        "                \"userListName\": \"outboundSettingName_ADID\"," +
                        "                \"extractionTargetId\": \"2\"" +
                        "            }, {" +
                        "                \"outputPlatformOutboundSettingIntermediateSequence\": 1008," +
                        "                \"outboundSettingSequence\": 1004," +
                        "                \"outputPlatformSequence\": 1001," +
                        "                \"outputPlatformType\": \"1\"," +
                        "                \"adAccountSequence\": 1001," +
                        "                \"adsPlatformSequence\": 1," +
                        "                \"crmPlatformSequence\": null," +
                        "                \"audienceId\": \"123456\"," +
                        "                \"userListName\": \"outboundSettingName_IDFA\"," +
                        "                \"extractionTargetId\": \"3\"" +
                        "            }" +
                        "        ]" +
                        "    }" +
                        "}"));
    }
}
