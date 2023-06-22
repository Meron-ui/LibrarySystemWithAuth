package com.example.librarysystem.Service;

import com.example.librarysystem.dto.Member;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MemberService {
    List<Member> getAllMembers();

    Member addMember(Member memberDTO);

    Member getMemberById(Long memberId);

    Member updateMember(Long memberId, Member memberDTO);

    boolean deleteMember(Long memberId);

    List<Member> searchMembers(String query);
}
