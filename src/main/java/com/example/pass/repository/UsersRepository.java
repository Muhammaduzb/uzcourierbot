package com.example.pass.repository;

import com.example.pass.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer>{
    Optional<Users> findByPhoneNumber(String phoneNmber);

}
