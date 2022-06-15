package com.example.collection.repositories;

import com.example.collection.models.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "userRoles")
    Optional<User> findWithUserRolesById(Long id);

    List<User> findAllByDel(boolean del);

    List<User> findAllByDelOrderByIdDesc(boolean del, Pageable pageable);

    @EntityGraph(attributePaths = "userRoles")
    Optional<User> findWithUserRolesByEmailAndDel(String email, boolean del);

    Optional<User> findByEmail(String email);

}
