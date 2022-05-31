package com.ohrs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ohrs.models.House;
import com.ohrs.models.User;

@Repository
public interface HouseRepository extends JpaRepository<House, Long>  {

}
