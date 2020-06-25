package com.example.demo.repositories;

import com.example.demo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Page<User> findByNameContaining(@Param("name") String name, Pageable pageable);

    List<User> findByEmail(@Param("email") String email);

    List<User> findByNameContainingOrEmailContaining(@Param("name") String name, @Param("email") String email);

}