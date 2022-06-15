package com.example.collection.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.collection.models.entities.SecurityUser;
import com.example.collection.models.entities.User;
import com.example.collection.repositories.UserRepository;

@Service
public class SecurityUserService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(SecurityUserService.class);

    private final UserRepository userRepository;

    @Autowired
    public SecurityUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { //상속받은 UserDetailsService 메서드 구현
        Optional<User> oUser = userRepository.findWithUserRolesByEmailAndDel(email, false); //이메일로 로그인할 것이므로, email을 통해 db로부터 사용자를 조회함
        if(!oUser.isPresent()) {
            logger.info("존재하지 않는 아이디입니다: " + email);
            throw new UsernameNotFoundException(email);
        }
        return new SecurityUser(oUser.get()); //user가 존재할 경우, user를 이용하여 SecurityUser 객체를 생성하여 반환함.
    }
}