package com.example.admin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@Getter
@Entity
public class Member {
    @Id @GeneratedValue
    private long id;
    @Column(unique = true)
    private String userId;
    @Setter
    private String nickname;
    private String password;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Member(String userid, String password, String nickname) {
        this.userId = userid;
        this.setPassword(password);
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = passwordEncoding(password);
    }

    public String passwordEncoding (String password) {
        return passwordEncoder.encode(password);
    }

    public boolean checkPassword(String rawPassword) {
        return passwordEncoder.matches(rawPassword, this.password);
    }
}
