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
@Setter
@Entity
public class Parents {
    @Id @GeneratedValue
    private long id;

    @Column(unique = true)
    private String user_id;

    private String nickname;
    private String password;
    private String phone_number;
    private String email;

    private String token;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Parents(String userid, String password, String nickname, String phoneNumber, String email) {
        this.user_id = userid;
        this.setPassword(password);
        this.nickname = nickname;
        this.phone_number = phoneNumber;
        this.email = email;
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
