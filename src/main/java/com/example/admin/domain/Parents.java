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
public class Parents {
    @Id @GeneratedValue
    private long id;
    @Column(unique = true)
    private String userId;
    @Setter
    private String nickname;
    private String password;
    private String phoneNumber;
    private String email;
    private String longinKey;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Parents(String userid, String password, String nickname, String phoneNumber, String email) {
        this.userId = userid;
        this.setPassword(password);
        this.nickname = nickname;
        setPhoneNumber(phoneNumber);
        setEmail(email);
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

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }else{
            this.phoneNumber = "";
        }
    }

    public void setEmail(String email) {
        if(email != null) {
            this.email = email;
        }else{
            this.email = "";
        }
    }
}
