package com.example.collection.models.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1146360965411496820L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP) //date + time으로, 년-월-일-시-분-초
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, columnDefinition = "TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private Date updateTimestamp;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean del;

    @PrePersist //현재 시간의 정보를 주입해주는 함수(onCreate())를 생성 후, @PrePersist를 달아준다. 이 뜻은 "DB에 해당 테이블의 insert 연산을 실행할 때 같이 실행해라"라는 의미다. 그러면 insert시 이 함수가 실행되고 결과값이 createTimestamp에 담기는 것!
    protected void onCreate() {
        createTimestamp = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() {
        updateTimestamp = Timestamp.valueOf(LocalDateTime.now());
    }

}
