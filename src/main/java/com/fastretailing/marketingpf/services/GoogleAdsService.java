package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.configs.Config;
import com.fastretailing.marketingpf.domain.dtos.PlatformAdAccountDto;
import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster;
import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster.GoogleApiTokenDto;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.mappers.AdsPlatformMasterMapper;
import com.fastretailing.marketingpf.exceptions.AudienceCreateException;
import com.fastretailing.marketingpf.utils.AesUtils;
import com.fastretailing.marketingpf.utils.JsonUtils;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.lib.GoogleAdsClient.Builder;
import com.google.ads.googleads.v10.common.CrmBasedUserListInfo;
import com.google.ads.googleads.v10.enums.CustomerMatchUploadKeyTypeEnum.CustomerMatchUploadKeyType;
import com.google.ads.googleads.v10.errors.GoogleAdsException;
import com.google.ads.googleads.v10.resources.CustomerClient;
import com.google.ads.googleads.v10.resources.CustomerName;
import com.google.ads.googleads.v10.resources.UserList;
import com.google.ads.googleads.v10.resources.UserListName;
import com.google.ads.googleads.v10.services.CustomerServiceClient;
import com.google.ads.googleads.v10.services.GoogleAdsRow;
import com.google.ads.googleads.v10.services.GoogleAdsServiceClient;
import com.google.ads.googleads.v10.services.GoogleAdsServiceClient.SearchPagedResponse;
import com.google.ads.googleads.v10.services.ListAccessibleCustomersRequest;
import com.google.ads.googleads.v10.services.ListAccessibleCustomersResponse;
import com.google.ads.googleads.v10.services.MutateUserListsResponse;
import com.google.ads.googleads.v10.services.SearchGoogleAdsRequest;
import com.google.ads.googleads.v10.services.UserListOperation;
import com.google.ads.googleads.v10.services.UserListServiceClient;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.UserCredentials;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleAdsService {

    private static final Logger logger = LoggerFactory.getLogger(GoogleAdsService.class);

    @Autowired
    public AdsPlatformMasterMapper adsPlatformMasterMapper;

    @Autowired
    protected Config config;

    /**
     * https://www.googleapis.com/auth/userinfo.email is for getting email
     */
    private List<String> AUTHORIZED_SCOPES = Arrays.asList("https://www.googleapis.com/auth/userinfo.email", "https://www.googleapis.com/auth/adwords");

    private static final HttpTransport httpTransport = new NetHttpTransport();

    /**
     * Create GoogleAuthorizationCodeFlow from configuration
     *
     * @return GoogleAuthorizationCodeFlow
     */
    public GoogleAuthorizationCodeFlow createAuthorizationCodeFlow() {
        AdsPlatformMaster googleAdsPfMaster = adsPlatformMasterMapper.findById((long) ADS_PLATFORM.GOOGLE_ADS.getValue());
        if (googleAdsPfMaster == null) {
            throw new RuntimeException("Missing configuration for GoogleAds");
        }
        GoogleApiTokenDto apiTokenDto = googleAdsPfMaster.getGoogleApiTokenDto();
        return new GoogleAuthorizationCodeFlow.Builder(httpTransport,
                GsonFactory.getDefaultInstance(),
                apiTokenDto.getClientId(),
                apiTokenDto.getClientSecret(),
                AUTHORIZED_SCOPES).setAccessType("offline").setApprovalPrompt("force").build();
    }

    /**
     * Get authorization url
     *
     * @return String
     */
    public String getAuthorizationUrl() {
        return createAuthorizationCodeFlow().newAuthorizationUrl()
                .setRedirectUri(config.getMkpf().getPlatformAuthCallbackUrl()).setState(Long.toString(ADS_PLATFORM.GOOGLE_ADS.getValue()))
                .setScopes(AUTHORIZED_SCOPES).build();
    }

    /**
     * Exchange authorization token for refresh token
     *
     * @param code
     * @return String
     */
    public String exchangeCodeForRefreshToken(String code) {
        GoogleAuthorizationCodeTokenRequest tokenRequest = createAuthorizationCodeFlow().newTokenRequest(code);
        try {
            return tokenRequest.setRedirectUri(config.getMkpf().getPlatformAuthCallbackUrl()).execute().getRefreshToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create custom audience
     *
     * @param developerToken
     * @param clientId
     * @param clientSecret
     * @param encryptedRefreshToken
     * @param loginCustomerId
     * @param customerId
     * @param audienceName
     * @param description
     * @param brand
     * @param country
     * @return String e.g 7017203057
     */
    public String createAudience(String developerToken, String clientId, String clientSecret, String encryptedRefreshToken, Long loginCustomerId, Long customerId,
            String audienceName, String description, EXTRACTION_TARGET_ID extractionTargetId, BRAND brand, COUNTRY country) {
        GoogleAdsClient googleAdsClient = createClient(developerToken, clientId, clientSecret, AesUtils.decrypt(config.getAes().getSecretKey(), encryptedRefreshToken), loginCustomerId);
        com.google.ads.googleads.v10.common.CrmBasedUserListInfo.Builder crmBasedUserListInfoBuilder = CrmBasedUserListInfo.newBuilder();
        if (extractionTargetId == EXTRACTION_TARGET_ID.MAIL_ADDRESS) {
            crmBasedUserListInfoBuilder.setUploadKeyType(CustomerMatchUploadKeyType.CONTACT_INFO);
        } else if (extractionTargetId == EXTRACTION_TARGET_ID.ADID) {
            if (brand == BRAND.UQ && country == COUNTRY.JP) {
                crmBasedUserListInfoBuilder
                        .setUploadKeyType(CustomerMatchUploadKeyType.MOBILE_ADVERTISING_ID)
                        .setAppId(config.getGoogleAds().getUserListUQJPAndroidAppId());
            } else if (brand == BRAND.GU && country == COUNTRY.JP) {
                crmBasedUserListInfoBuilder
                        .setUploadKeyType(CustomerMatchUploadKeyType.MOBILE_ADVERTISING_ID)
                        .setAppId(config.getGoogleAds().getUserListGUJPAndroidAppId());
            } else {
                throw new RuntimeException("This service is only support brand UQ/GU and country JP");
            }
        } else if (extractionTargetId == EXTRACTION_TARGET_ID.IDFA) {
            if (brand == BRAND.UQ && country == COUNTRY.JP) {
                crmBasedUserListInfoBuilder
                        .setUploadKeyType(CustomerMatchUploadKeyType.MOBILE_ADVERTISING_ID)
                        .setAppId(config.getGoogleAds().getUserListUQJPIOsAppId());
            } else if (brand == BRAND.GU && country == COUNTRY.JP) {
                crmBasedUserListInfoBuilder
                        .setUploadKeyType(CustomerMatchUploadKeyType.MOBILE_ADVERTISING_ID)
                        .setAppId(config.getGoogleAds().getUserListGUJPIOsAppId());
            } else {
                throw new RuntimeException("This service is only support brand UQ/GU and country JP");
            }
        }
        UserList userList = UserList.newBuilder()
                .setName(audienceName)
                .setDescription(description)
                .setMembershipLifeSpan(10000) // Unlimited
                .setCrmBasedUserList(crmBasedUserListInfoBuilder)
                .build();
        UserListOperation operation = UserListOperation.newBuilder().setCreate(userList).build();
        try (UserListServiceClient userListServiceClient = googleAdsClient.getLatestVersion().createUserListServiceClient()) {
            MutateUserListsResponse response = userListServiceClient.mutateUserLists(Long.toString(customerId), ImmutableList.of(operation));
            return UserListName.parse(response.getResults(0).getResourceName()).getUserListId();
        } catch (GoogleAdsException e) {
            throw new AudienceCreateException(e.getGoogleAdsFailure().getErrorsList().stream().map(er -> er.getMessage()).filter(Objects::nonNull).collect(Collectors.toList()).toString(), e);
        }
    }

    /**
     * Fetch accessible account
     *
     * @param refreshToken
     * @return List<<code>PlatformAdAccountDto<code>>
     */
    public List<PlatformAdAccountDto> fetchAdAccounts(String refreshToken) {
        AdsPlatformMaster googleAdsMaster = adsPlatformMasterMapper.findById((long) ADS_PLATFORM.GOOGLE_ADS.getValue());
        GoogleApiTokenDto apiTokenDto = googleAdsMaster.getGoogleApiTokenDto();
        return fetchAdAccounts(apiTokenDto.getDeveloperToken(), apiTokenDto.getClientId(), apiTokenDto.getClientSecret(), AesUtils.decrypt(config.getAes().getSecretKey(), refreshToken));
    }

    /**
     * Fetch accessible account
     *
     * @param developerToken
     * @param clientId
     * @param clientSecret
     * @param refreshToken
     * @return List<<code>PlatformAdAccountDto</code>>
     */
    public List<PlatformAdAccountDto> fetchAdAccounts(String developerToken, String clientId, String clientSecret, String refreshToken) {
        GoogleAdsClient googleAdsClient = createClient(developerToken, clientId, clientSecret, refreshToken, null);
        List<Long> accessibleCustomerIds = getAccessibleCustomers(googleAdsClient);
        List<PlatformAdAccountDto> list = new ArrayList<>();
        Map<CustomerClient, Multimap<Long, CustomerClient>> hierachy = getAllClientHierarchy(googleAdsClient, accessibleCustomerIds);
        hierachy.forEach((rootCustomerClient, customerLinkMap) -> {
            String loginCustomerId = Long.toString(rootCustomerClient.getId());
            if (!rootCustomerClient.getManager()) {
                list.add(new PlatformAdAccountDto(Long.toString(rootCustomerClient.getId()), rootCustomerClient.getDescriptiveName(), loginCustomerId));
            }
            customerLinkMap.asMap().forEach((customerId, customerClientList) -> {
                customerClientList.forEach(customerClient -> {
                    if (!customerClient.getManager()) {
                        list.add(new PlatformAdAccountDto(Long.toString(customerClient.getId()), customerClient.getDescriptiveName(), loginCustomerId));
                    }
                });
            });
        });
        return list;
    }

    /**
     * get login customer id
     *
     * @param refreshToken
     * @param customerId
     * @return Long
     */
    public Long getLoginCustomerId(String refreshToken, Long customerId) {
        AdsPlatformMaster googleAdsMaster = adsPlatformMasterMapper.findById((long) ADS_PLATFORM.GOOGLE_ADS.getValue());
        GoogleApiTokenDto apiTokenDto = googleAdsMaster.getGoogleApiTokenDto();
        return getLoginCustomerId(apiTokenDto.getDeveloperToken(), apiTokenDto.getClientId(), apiTokenDto.getClientSecret(), AesUtils.decrypt(config.getAes().getSecretKey(), refreshToken),
                customerId);
    }

    /**
     * get login_customer_id
     *
     * @param developerToken
     * @param clientId
     * @param clientSecret
     * @param refreshToken
     * @param customerId
     * @return Long
     */
    public Long getLoginCustomerId(String developerToken, String clientId, String clientSecret, String refreshToken, Long customerId) {
        if (customerId == null) {
            return null;
        }
        GoogleAdsClient googleAdsClient = createClient(developerToken, clientId, clientSecret, refreshToken, null);
        List<Long> accessibleCustomerIds = getAccessibleCustomers(googleAdsClient);
        for (Long loginCustomerId : accessibleCustomerIds) {
            if (this.isLoginCustomerId(googleAdsClient, customerId, loginCustomerId)) {
                return loginCustomerId;
            }
        }
        return null;
    }

    /**
     * Test if loginCustomerId has access to customerId
     *
     * @param googleAdsClient
     * @param customerId
     * @param loginCustomerId
     * @return boolean
     */
    private boolean isLoginCustomerId(GoogleAdsClient googleAdsClient, Long customerId, Long loginCustomerId) {
        Map<CustomerClient, Multimap<Long, CustomerClient>> hierarchy = createCustomerClientToHierarchy(googleAdsClient, loginCustomerId);
        logger.debug("customerId = {}, hierarchy = {}", customerId, JsonUtils.toPrettyJson(hierarchy));
        if (hierarchy == null) {
            return false;
        }
        for (Entry<CustomerClient, Multimap<Long, CustomerClient>> entry : hierarchy.entrySet()) {
            CustomerClient c = entry.getKey();
            if (c.getId() == (long) customerId && !c.getManager()) {
                return true;
            }
            for (Entry<Long, CustomerClient> seed : entry.getValue().entries()) {
                if (seed.getValue().getId() == (long) customerId) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get accessible customer list
     *
     * @param googleAdsClient
     * @return List<<code>Long</code>>
     */
    public List<Long> getAccessibleCustomers(GoogleAdsClient googleAdsClient) {
        try (CustomerServiceClient customerService = googleAdsClient.getLatestVersion().createCustomerServiceClient()) {
            ListAccessibleCustomersResponse resposne = customerService.listAccessibleCustomers(ListAccessibleCustomersRequest.newBuilder().build());
            List<Long> accessibleCustomerList = resposne.getResourceNamesList().stream()
                    .map(e -> Long.parseLong(CustomerName.parse(e).getCustomerId()))
                    .collect(Collectors.toList());
            logger.debug("accessibleCustomerList = {}", accessibleCustomerList);
            return accessibleCustomerList;
        }
    }

    /**
     * Get map customer hierarchy from a list of accessible customer
     *
     * @param googleAdsClient
     * @param accessibleCustomerIds
     * @return Map<CustomerClient, Multimap<Long, CustomerClient>>
     */
    public Map<CustomerClient, Multimap<Long, CustomerClient>> getAllClientHierarchy(GoogleAdsClient googleAdsClient, List<Long> accessibleCustomerIds) {
        Map<CustomerClient, Multimap<Long, CustomerClient>> hierarchy = new HashMap<>();
        for (Long customerId : accessibleCustomerIds) {
            Map<CustomerClient, Multimap<Long, CustomerClient>> customerClientToHierarchy =
                    createCustomerClientToHierarchy(googleAdsClient, customerId);
            if (customerClientToHierarchy == null) {
                continue;
            }
            hierarchy.putAll(customerClientToHierarchy);
        }
        return hierarchy;
    }

    /**
     * Build customer hierarchy for a single accessible customer<br>
     * Null return if account is not setup completely
     *
     * @param googleAdsClient
     * @param customerId
     * @return Map<CustomerClient, Multimap<Long, CustomerClient>>
     */
    public Map<CustomerClient, Multimap<Long, CustomerClient>> createCustomerClientToHierarchy(GoogleAdsClient googleAdsClient, Long customerId) {
        Multimap<Long, CustomerClient> customerIdsToChildAccounts = ArrayListMultimap.create();
        Queue<Long> managerAccountsToSearch = new LinkedList<>();
        CustomerClient rootCustomerClient = null;
        try (GoogleAdsServiceClient googleAdsServiceClient = googleAdsClient.toBuilder().setLoginCustomerId(customerId).build().getLatestVersion().createGoogleAdsServiceClient()) {
            String query = "SELECT customer_client.client_customer, customer_client.id, customer_client.manager, customer_client.descriptive_name, customer_client.level "
                    + "FROM customer_client WHERE customer_client.level <= 1";
            managerAccountsToSearch.add(customerId);
            while (!managerAccountsToSearch.isEmpty()) {
                long customerIdToSearchFrom = managerAccountsToSearch.poll();
                SearchPagedResponse response = googleAdsServiceClient.search(SearchGoogleAdsRequest.newBuilder().setQuery(query).setCustomerId(Long.toString(customerIdToSearchFrom)).build());
                for (GoogleAdsRow googleAdsRow : response.iterateAll()) {
                    CustomerClient customerClient = googleAdsRow.getCustomerClient();
                    if (customerClient.getId() == customerId) {
                        rootCustomerClient = customerClient;
                    }
                    if (customerClient.getId() == customerIdToSearchFrom) {
                        continue;
                    }
                    customerIdsToChildAccounts.put(customerIdToSearchFrom, customerClient);
                    if (customerClient.getManager()) {
                        boolean alreadyVisited = customerIdsToChildAccounts.containsKey(customerClient.getId());
                        if (!alreadyVisited && customerClient.getLevel() == 1) {
                            managerAccountsToSearch.add(customerClient.getId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Unable to get customer hierachy, customerId = " + customerId, e);
            return null;
        }
        if (rootCustomerClient == null) {
            return null;
        }
        Map<CustomerClient, Multimap<Long, CustomerClient>> customerClientToHierarchy = new HashMap<>();
        customerClientToHierarchy.put(rootCustomerClient, customerIdsToChildAccounts);
        return customerClientToHierarchy;
    }

    /**
     * Get email of auth account
     *
     * @param refreshToken
     * @return String
     */
    public String getAuthUserId(String refreshToken) {
        AdsPlatformMaster adsPlatformMaster = adsPlatformMasterMapper.findById((long) ADS_PLATFORM.GOOGLE_ADS.getValue());
        GoogleApiTokenDto apiTokenDto = adsPlatformMaster.getGoogleApiTokenDto();
        return getAuthUserId(apiTokenDto.getClientId(), apiTokenDto.getClientSecret(), refreshToken);
    }

    /**
     * get auth UserId
     *
     * @param clientId
     * @param clientSecret
     * @param refreshToken
     * @return String
     */
    public String getAuthUserId(String clientId, String clientSecret, String refreshToken) {
        try {
            UserCredentials credentials = UserCredentials.newBuilder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setRefreshToken(refreshToken).build();
            credentials.refreshIfExpired();
            HttpRequestInitializer httpRequestInitializer = new HttpCredentialsAdapter(credentials);
            HttpResponse httpResponse = httpTransport.createRequestFactory(httpRequestInitializer).buildGetRequest(new GenericUrl("https://www.googleapis.com/oauth2/v1/userinfo")).execute();
            String userInfoJson = IOUtils.toString(httpResponse.getContent(), StandardCharsets.UTF_8);
            return JsonUtils.fromJson(userInfoJson, UserInfo.class).email;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class UserInfo {

        public String email;
    }

    /**
     * Create GoogleAdsClient
     *
     * @param developerToken
     * @param clientId
     * @param clientSecret
     * @param refreshToken
     * @param loginCustomerId
     * @return GoogleAdsClient
     */
    public GoogleAdsClient createClient(String developerToken, String clientId, String clientSecret, String refreshToken, Long loginCustomerId) {
        UserCredentials credentials = UserCredentials.newBuilder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(refreshToken).build();
        try {
            credentials.refreshIfExpired();
        } catch (IOException e) {
            logger.error("Cannot refresh token, error = {}", e);
            throw new RuntimeException(e);
        }
        Builder builder = GoogleAdsClient.newBuilder().setDeveloperToken(developerToken).setCredentials(credentials);
        if (loginCustomerId != null) {
            builder.setLoginCustomerId(loginCustomerId);
        }
        return builder.build();
    }
}
