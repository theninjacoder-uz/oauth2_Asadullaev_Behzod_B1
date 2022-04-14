package com.example.oauth2_asadullaev_behzod_b1.repository;

import com.example.oauth2_asadullaev_behzod_b1.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}