package com.example.librarysystem.Repository;

import com.example.librarysystem.dto.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
//@Transactional
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
