package com.fastretailing.marketingpf.domain.mappers;

import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiAuthenticationInformationMapper {

    List<ApiAuthenticationInformation> findAll();

    void upsert(ApiAuthenticationInformation apiAuthenticationInformation);

    ApiAuthenticationInformation findByAdsPlatformSequenceAndLoginUserId(Long adsPlatformSequence, String adsPfLoginUserId);

    ApiAuthenticationInformation findById(Long apiAuthenticationInformationSequence);

    List<ApiAuthenticationInformation> findListByAdsPlatformSequence(Long adsPlatformSequence);

    List<ApiAuthenticationInformation> findByAdsPfLoginUserIdTest(String adsPfLoginUserId);
}
