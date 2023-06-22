package com.example.librarysystem.Service;

import com.example.librarysystem.Repository.MemberRepository;
import com.example.librarysystem.dto.Book;
import com.example.librarysystem.dto.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService{


    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member addMember(Member memberDTO) {
        Member member = new Member();
        member.setAddress(memberDTO.getAddress());
        member.setName(memberDTO.getName());
        member.setPhone(memberDTO.getPhone());
        member.setFees(memberDTO.getFees());
        member.setCategory(memberDTO.getCategory());

        return memberRepository.save(member);
    }

    @Override
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    @Override
    public Member updateMember(Long memberId, Member memberDTO) {
        Optional<Member> entity = memberRepository.findById(memberId);

        if(entity.isPresent()){
            Member member = entity.get();
            member.setName(memberDTO.getName());
            member.setPhone(memberDTO.getPhone());
            member.setAddress(memberDTO.getAddress());
            member.setFees(memberDTO.getFees());
            member.setCategory(memberDTO.getCategory());

            return memberRepository.save(member);
        }

        return null;
    }

    @Override
    public boolean deleteMember(Long memberId) {
        Member existingMember = getMemberById(memberId);
        if (existingMember != null) {
            memberRepository.delete(existingMember);
            return true;
        }
        return false;
    }

    @Override
    public List<Member> searchMembers(String query) {
        return memberRepository.searchMembersByNameOrPhone(query);
    }
}
