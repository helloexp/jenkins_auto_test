package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.entities.UserMaster;
import com.fastretailing.marketingpf.domain.mappers.UserMasterMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMasterService {

    @Autowired
    public UserMasterMapper userMasterMapper;

    /**
     * Find All user master
     *
     * @return List<UserMaster>
     */
    public List<UserMaster> findAll() {
        return userMasterMapper.findAll();
    }

    /**
     * Register user if not exists<br>
     * Otherwise, update user
     * If the user exists it will be error, so make sure there are no deleted users in the database
     *
     * @param userId
     * @param userFullName
     */
    public void upsert(String userId, String userFullName, String roleSequenceList, LocalDateTime updateDateTime) {
        UserMaster user = userMasterMapper.findById(userId);
        if (user == null) {
            user = new UserMaster();
            user.setUserId(userId);
            user.setUserFullName(userFullName);
            user.setRoleSequenceList(roleSequenceList);
            user.setAuditInfoForCreation(userId, updateDateTime);
            userMasterMapper.create(user);
            return;
        }
        if (StringUtils.equals(user.getUserFullName(), userFullName)
                && StringUtils.equals(user.getRoleSequenceList(), roleSequenceList)) {
            return; // No update
        }
        user.setUserFullName(userFullName);
        user.setRoleSequenceList(roleSequenceList);
        user.setAuditInfoForUpdate(userId, updateDateTime);
        userMasterMapper.update(user);
    }

    /**
     * get UserId to UserName map
     *
     * @return Map<<code>String, String</code>>
     */
    public Map<String, String> getUserIdToUserNameMap() {
        List<UserMaster> userMasterList = userMasterMapper.findAll();
        userMasterList.addAll(Arrays.asList(new UserMaster("PIIDB_BATCH", "システムユーザ"), new UserMaster("MKDB_BATCH", "システムユーザ"), new UserMaster("MKPF_SCHEDULER", "システムユーザ")));
        return userMasterList.stream().collect(Collectors.toMap(UserMaster::getUserId, UserMaster::getUserFullName));
    }
}
