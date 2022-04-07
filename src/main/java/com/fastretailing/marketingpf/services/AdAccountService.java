package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.dtos.AdAccount;
import com.fastretailing.marketingpf.domain.dtos.AdAccount.AdAccountDto;
import com.fastretailing.marketingpf.domain.dtos.AdAccountList;
import com.fastretailing.marketingpf.domain.dtos.AdAccountList.AdAccountListDto;
import com.fastretailing.marketingpf.domain.mappers.ApiAuthenticationInformationMapper;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AdAccountService extends BaseUpstreamService {

    @Autowired
    ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

    /**
     * Get adAccount from DB
     *
     * @param adAccountSequence
     * @return AdAccount
     */
    public AdAccount findById(Long adAccountSequence) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .path(config.getMkdbApi().getUriPrefix() + "/ad/account/" + adAccountSequence);
        return this.getAsMono(builder.build().toUri(), AdAccount.class);
    }

    /**
     * Create new AdAccount
     *
     * @param adsPlatformAccountId
     * @param adsPlatformSequence
     * @param apiAuthenticationInformationSequence
     * @param brandId
     * @param countryId
     * @param accountName
     * @param loginUserId
     * @return AdAccount
     */
    public AdAccount create(
            String adsPlatformAccountId,
            Long adsPlatformSequence,
            Long apiAuthenticationInformationSequence,
            String brandId,
            String countryId,
            String accountName,
            Long loginCustomerId,
            String loginUserId) {
        AdAccountDto adAccountDto = new AdAccountDto();
        adAccountDto.setAdsPlatformAccountId(adsPlatformAccountId);
        adAccountDto.setAdsPlatformSequence(adsPlatformSequence);
        adAccountDto.setApiAuthenticationInformationSequence(apiAuthenticationInformationSequence);
        adAccountDto.setBrandId(brandId);
        adAccountDto.setCountryId(countryId);
        adAccountDto.setAccountName(accountName);
        if (loginCustomerId != null) {
            adAccountDto.setLoginCustomerId(String.valueOf(loginCustomerId));
        }
        adAccountDto.setCreateUserIdForAudit(loginUserId);

        AdAccount adAccount = new AdAccount();
        adAccount.setAdAccount(adAccountDto);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path(config.getMkdbApi().getUriPrefix() + "/ad/account");
        return this.postForMono(builder.build().toUri(), adAccount, AdAccount.class);
    }

    /**
     * Update AdAccount
     *
     * @param adAccountSequence
     * @param brandId
     * @param countryId
     * @param loginUserId
     * @return AdAccount
     */
    public AdAccount update(
            Long adAccountSequence,
            String brandId,
            String countryId,
            String loginUserId) {
        AdAccount adAccount = this.findById(adAccountSequence);

        if (brandId != null) {
            adAccount.getAdAccount().setBrandId(brandId);
        }

        if (countryId != null) {
            adAccount.getAdAccount().setCountryId(countryId);
        }
        adAccount.getAdAccount().setUpdateUserIdForAudit(loginUserId);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .path(config.getMkdbApi().getUriPrefix() + "/ad/account/" + adAccountSequence);
        return this.putForMono(builder.build().toUri(), adAccount, AdAccount.class);
    }

    /**
     * Delete AdAccount by adAccountSequence
     *
     * @param adAccountSequence
     * @param loginUserId
     * @return AdAccount
     */
    public AdAccount delete(Long adAccountSequence, String loginUserId) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .path(config.getMkdbApi().getUriPrefix() + "/ad/account/" + adAccountSequence);
        builder.queryParam("deleteUserIdForAudit", loginUserId);
        return this.deleteForMono(builder.build().toUri(), AdAccount.class);
    }

    /**
     * Search AdAccount
     *
     * @param adsPlatformSequence
     * @param brandId
     * @param countryId
     * @param apiAuthenticationInformationSequence
     * @return AdAccountList
     */
    public AdAccountList search(
            Long adsPlatformSequence,
            String brandId,
            String countryId,
            Long apiAuthenticationInformationSequence) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        builder.path(config.getMkdbApi().getUriPrefix() + "/ad/accounts/");
        if (adsPlatformSequence != null) {
            builder.queryParam("adsPlatformSequence", adsPlatformSequence);
        }
        if (brandId != null) {
            builder.queryParam("brandId", brandId);
        }
        if (countryId != null) {
            builder.queryParam("countryId", countryId);
        }
        if (apiAuthenticationInformationSequence != null) {
            builder.queryParam("apiAuthenticationInformationSequence", apiAuthenticationInformationSequence);
        }
        return this.getAsMono(builder.build().toUri(), AdAccountList.class);
    }

    /**
     * Search with optional adsPfLoginUserId
     *
     * @param adsPlatformSequence
     * @param brandId
     * @param countryId
     * @param adsPfLoginUserId
     * @return List<AdAccountListDto>
     */
    public List<AdAccountListDto> searchWithAdsPfLoginUserId(Long adsPlatformSequence, String brandId, String countryId, String adsPfLoginUserId) {
        if (StringUtils.isAllBlank(adsPfLoginUserId)) {
            return this.search(adsPlatformSequence, brandId, countryId, null).getAdsAccountList();
        }
        return apiAuthenticationInformationMapper.findByAdsPfLoginUserIdTest(adsPfLoginUserId).stream()
                .map(e -> search(adsPlatformSequence, brandId, countryId, e.getApiAuthenticationInformationSequence()).getAdsAccountList())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
