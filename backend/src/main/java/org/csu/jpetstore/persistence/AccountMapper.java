package org.csu.jpetstore.persistence;

import org.csu.jpetstore.domain.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper {

  Account getAccountByUsername(String username);

  Account getAccountByUsernameAndPassword(Account account);

  void insertAccount(Account account);
  
  void insertProfile(Account account);
  
  void insertSignon(Account account);

  void updateAccount(Account account);

  void updateProfile(Account account);

  void updateSignon(Account account);

}
