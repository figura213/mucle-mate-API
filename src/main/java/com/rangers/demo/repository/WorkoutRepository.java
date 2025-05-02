
package com.rangers.demo.repository;

import com.rangers.demo.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID> {
    List<Workout> findByUserUserId(UUID userId);
    Page<Workout> findByUserUserId(UUID userId, Pageable pageable);
}
