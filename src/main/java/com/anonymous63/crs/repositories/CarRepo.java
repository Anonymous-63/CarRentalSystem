package com.anonymous63.crs.repositories;

import com.anonymous63.crs.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepo extends JpaRepository<Car, Long> {
}
