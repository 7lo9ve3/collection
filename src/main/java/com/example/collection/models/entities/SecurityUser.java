package com.example.collection.models.entities;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.collection.models.entities.UserRole.RoleType;

public class SecurityUser extends User implements UserDetails {

    private static final long serialVersionUID = 8666468119299100306L;

    private final boolean accountNonExpired; //인증 및 인가에 이용되는 필드값. 생성자 초기화 시, 함께 초기화됨. 4개중 하나라도 false일 경우 로그인 거부됨.
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public SecurityUser(User user) {
        super();
        setId(user.getId());
        setEmail(user.getEmail());
        setName(user.getName());
        setPassword(user.getPassword());
        setDel(user.isDel());
        setUserRoles(user.getUserRoles());
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public Set<RoleType> getRoleTypes() {
        return getUserRoles().stream().map(f -> f.getRoleName()).collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUserRoles();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}