package org.myproject.uam.repository;

import org.myproject.uam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<User,Long> {

     @Query("select ad from Admin ad where ad.pfNumber=:pfNumber")
     public Optional<User> findByPfNumber(@Param("pfNumber") String pfNumber);

}
