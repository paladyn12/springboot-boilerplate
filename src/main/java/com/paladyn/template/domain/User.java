package com.paladyn.template.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 기본적인 Login 구현을 위한 User Entity
 */
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(unique = true)
    private String nickname;

    //Kakao, Google, ...
    private String oAuthProvider;
    private String authority;


    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
