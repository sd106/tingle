package com.example.tingle.user.dto;

import com.example.tingle.store.dto.ProductDto;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String picture;
    private Role role;
    private String provider;
    private String email;

    public String getRoleKey() {
        return this.role.getKey();
    }
}
