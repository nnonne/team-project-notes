package com.example.Note.feature.user;

import com.example.Note.feature.auth.CustomUserDetailsService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Query(nativeQuery = true, value = "SELECT * FROM \"user\" WHERE name = :name")
    User searchUserByName(@Param("name") String name);

}
