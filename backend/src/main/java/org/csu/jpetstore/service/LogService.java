package org.csu.jpetstore.service;

import org.csu.jpetstore.domain.UserLog;
import org.csu.jpetstore.persistence.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Autowired
    LogMapper logMapper;
    public void insertUserLog(UserLog userLog){
        logMapper.insertLog(userLog);
    }
}
