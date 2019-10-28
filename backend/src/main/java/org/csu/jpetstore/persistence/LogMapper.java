package org.csu.jpetstore.persistence;

import org.csu.jpetstore.domain.UserLog;
import org.springframework.stereotype.Repository;

@Repository
public interface LogMapper {
    void insertLog(UserLog userLog);
}
