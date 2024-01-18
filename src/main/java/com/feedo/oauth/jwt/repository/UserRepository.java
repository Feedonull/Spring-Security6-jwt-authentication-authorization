package com.feedo.oauth.jwt.repository;

import com.feedo.oauth.jwt.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    public UserInfo findByUserName(String username);
    public UserInfo findFirstById(Long id);

}
