package student.ed.tracecode1103.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import student.ed.tracecode1103.constant.MemberStatus;
import student.ed.tracecode1103.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAccountAndStatus(String account, MemberStatus status);
}

