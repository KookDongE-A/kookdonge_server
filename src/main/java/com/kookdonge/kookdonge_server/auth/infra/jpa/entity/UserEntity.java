package com.kookdonge.kookdonge_server.auth.infra.jpa.entity;

import com.kookdonge.kookdonge_server.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class UserEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(length = 64)
    private String externalUserId;

    @NotNull
    @Column(length = 64)
    private String email;

    @NotNull
    @Column(length = 16)
    private String phoneNumber;

    @NotNull
    @Column(length = 32)
    private String department;

    @NotNull
    @Column(length = 16)
    private String studentId;

    private String clubId;

    public static UserEntity ofDB(String email, String phoneNumber, String department, String studentId) {
        return UserEntity.builder()
                        .externalUserId(UUID.randomUUID().toString())
                        .email(email)
                        .phoneNumber(phoneNumber)
                        .department(department)
                        .studentId(studentId)
                .build();
    }

}
