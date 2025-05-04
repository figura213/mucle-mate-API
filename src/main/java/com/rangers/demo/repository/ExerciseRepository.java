// ExerciseRepository.java
package com.rangers.demo.repository;

        import com.rangers.demo.entity.Exercise;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;

        import java.util.UUID;

public interface ExerciseRepository  extends JpaRepository<Exercise, Long>,
        JpaSpecificationExecutor<Exercise> {
    // наследуете метод:
    // Page<Exercise> findAll(Specification<Exercise> spec, Pageable pageable);
}
