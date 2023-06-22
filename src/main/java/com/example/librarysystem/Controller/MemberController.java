package com.example.librarysystem.Controller;

import com.example.librarysystem.Service.MemberService;
import com.example.librarysystem.dto.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Member> addMember(@Validated @RequestBody Member memberDTO) {
        Member member = memberService.addMember(memberDTO);
        return ResponseEntity.ok(member);
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long memberId) {
        Member member = memberService.getMemberById(memberId);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @PutMapping("/{memberId}")
    public ResponseEntity<Member> updateMember(@PathVariable Long memberId, @Validated @RequestBody Member memberDTO) {
        Member updatedMember = memberService.updateMember(memberId, memberDTO);
        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        boolean isDeleted = memberService.deleteMember(memberId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @GetMapping("/search")
    public List<Member> searchMembers(@RequestParam(value = "query") String query) {
        return memberService.searchMembers(query);
    }
}
