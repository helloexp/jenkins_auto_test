package com.fastretailing.marketingpf.domain.mappers;

import com.fastretailing.marketingpf.domain.entities.UserMaster;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMasterMapper {

    List<UserMaster> findAll();

    UserMaster findById(String userId);

    void create(UserMaster userMaster);

    void update(UserMaster userMaster);
}
