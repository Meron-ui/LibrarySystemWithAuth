package com.example.librarysystem.Repository;

import com.example.librarysystem.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MemberRepository extends JpaRepository<Member,Long> {

    @Query("SELECT m FROM Member m WHERE m.name LIKE %:query% OR m.phone LIKE %:query%")
    List<Member> searchMembersByNameOrPhone(@Param("query") String query);

    @Query("SELECT m.fees FROM Member m WHERE m.memberId = :memberId")
    double findOverdueAmount(Long memberId);
}
