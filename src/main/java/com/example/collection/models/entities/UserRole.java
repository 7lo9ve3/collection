package com.example.collection.models.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@Entity
@Table(name = "user_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_name"})})
@DynamicUpdate
public class UserRole extends BaseEntity implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 7943607393308984161L;

    @JsonBackReference //순환
    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ROLE_USER"))
    private User user;

    @Column(name="role_name", nullable=false, length = 20)
    @Enumerated(EnumType.STRING) //index가 아닌, 텍스트값 그대로 저장됨
    private RoleType roleName;

    @Builder
    public UserRole(User user, RoleType roleName) {
        this.user = user;
        this.roleName = roleName;
    }

    public enum RoleType {
        ROLE_ADMIN, ROLE_VIEW
    }

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.roleName.name();
    }

}