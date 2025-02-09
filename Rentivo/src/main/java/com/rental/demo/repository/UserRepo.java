package com.rental.demo.repository;

import com.rental.demo.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u WHERE (u.username = :username OR u.email = :email) AND u.id != :id")
     boolean existsByUsernameOrEmailAndIdNot(@Param("username") String username, 
                                             @Param("email") String email, 
                                             @Param("id") Long id);
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	Optional<User> findByPhno(String phno);
	User findById(long id);
}
