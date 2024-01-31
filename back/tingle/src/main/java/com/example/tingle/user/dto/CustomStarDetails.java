package com.example.tingle.user.dto;

import com.example.tingle.star.entity.StarEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomStarDetails implements UserDetails {

    private StarEntity starEntity;

    public CustomStarDetails(StarEntity starEntity){
        this.starEntity = starEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return starEntity.getRoleKey();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return starEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return starEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
