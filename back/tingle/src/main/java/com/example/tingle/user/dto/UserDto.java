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
    private int id;
    private String username;
    private String password;
    private String picture;
    private Role role;
    private String provider;
    private String email;
    // 추가로 필요한 필드가 있다면 여기에 추가할 수 있습니다.

    public String getRoleKey() {
        return this.role.getKey();
    }
}
