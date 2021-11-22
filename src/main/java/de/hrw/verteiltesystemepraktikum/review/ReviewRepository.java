package de.hrw.verteiltesystemepraktikum.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Long id);

}
