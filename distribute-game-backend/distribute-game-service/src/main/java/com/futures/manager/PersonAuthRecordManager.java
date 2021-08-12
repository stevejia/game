package com.futures.manager;

import com.futures.common.enums.AlRespStatusEnum;
import com.futures.common.enums.AuthStatusEnum;
import com.futures.mapper.PersonAuthRecordMapper;
import com.futures.model.entity.PersonAuthRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/7/8 11:27
 */
@Service
public class PersonAuthRecordManager {

    @Autowired
    private PersonAuthRecordMapper authRecordMapper;

    public PersonAuthRecord convert(String name,String idCard,Long userId){
        PersonAuthRecord record = new PersonAuthRecord();
        record.setName(name);
        record.setIdCard(idCard);
        record.setCertifStatus(AuthStatusEnum.NOT_AUTH.getCode());
        record.setReqStatus(AlRespStatusEnum.PUSH_ING.getCode());
        record.setCreateDate(new Date());
        record.setUpdateDate(new Date());
        record.setBuildUserId(userId);
        return record;
    }

    public void insert(PersonAuthRecord record){
        authRecordMapper.insert(record);
    }

    public void updateIgnoreNull(PersonAuthRecord record){
        record.setUpdateDate(new Date());
        authRecordMapper.updateIgnoreNull(record);
    }

    public List<PersonAuthRecord> findByPerson(String name,String idCard){
        return authRecordMapper.findByPerson(name,idCard);
    }

    public PersonAuthRecord findByUserId(Long userId){
        return authRecordMapper.findByUserId(userId);
    }
}
