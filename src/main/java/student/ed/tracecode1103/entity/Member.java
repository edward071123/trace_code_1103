package student.ed.tracecode1103.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import student.ed.tracecode1103.constant.MemberStatus;

@Entity
@Table(name = "members")
public class Member {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    protected Member() {
    }

    public Long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public MemberStatus getStatus() {
        return status;
    }
}

