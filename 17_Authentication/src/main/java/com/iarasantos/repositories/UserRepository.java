package com.iarasantos.repositories;

import com.iarasantos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    //querying object User not table user so userName not user_name
    @Query("SELECT u FROM User WHERE u.userName =: userName")
    User findByUsername(@Param("userName") String userName);
}
