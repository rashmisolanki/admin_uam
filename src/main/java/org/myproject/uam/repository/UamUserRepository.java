package org.myproject.uam.repository;

import org.myproject.uam.entity.UamUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UamUserRepository extends JpaRepository<UamUser, Long> {

     @Query("select ad from UamUser ad where ad.pfNumber=:pfNumber")
     public Optional<UamUser> findByPfNumber(@Param("pfNumber") String pfNumber);

     @Query("select uu,ug from UamUser uu, UamUserGroupId ug where uu.isDeleted=0 and uu.pfNumber=ug.pfNumber")
     public List<Object[]> findAllUserByIsDeleted();
}
