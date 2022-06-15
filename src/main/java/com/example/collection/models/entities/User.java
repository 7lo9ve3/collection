package com.example.collection.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@Entity
@Table(name ="user")
@DynamicUpdate
@DynamicInsert
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -563329217866858622L;

    @ColumnDefault(value = "0")
    @Column(nullable = false, length = 1)
    private String type;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    @ColumnDefault(value = "1")
    @Column(nullable = false, length = 1)
    private String sex;

    @Column(nullable = false, length = 6)
    private String birthDate;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @JsonIgnore
    @Column(nullable = false, length = 150)
    private String password;

    @Singular("userRoles")
    @JsonIgnoreProperties({"createTimestamp", "updateTimestamp", "del"}) //json 조회 시, 출력에서 제외시킬 칼럼
    @JsonManagedReference //UserRole과 User엔티티가 서로를 필드로 가지고 있어, json 조회시 무한 순환참조하는 현상이 발생. 두 엔티티 중, 매핑필드를 조회하고자 하는 엔티티에 @JsonManagedReference 애너테이션 삽입
    @OneToMany(mappedBy="user") //userRoles에 대해 @OneTomany 연관관계를 설정함. (1:n = 일대다!!!)
    @Where(clause = "del = false") //삭제되지 않은 UserRole만 가져옴
   private Set<UserRole> userRoles;

    @Builder
    public User(String type, String name, String email, String sex, String birthDate, String phoneNumber, String password){
        this.type = type;
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }


}
