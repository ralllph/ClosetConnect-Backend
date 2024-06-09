package com.capstone.closetconnect.dtos;

import com.capstone.closetconnect.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userName;
    private String username;
    private String email;
    private String name;
    private String clothingSize;
    private String role;

    public static User toUserEntity(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setClothingSize(userDto.getClothingSize());
        user.setRole(userDto.getRole());
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(userName, userDto.userName) && Objects.equals(username, userDto.username) && Objects.equals(email, userDto.email) && Objects.equals(name, userDto.name) && Objects.equals(clothingSize, userDto.clothingSize) && Objects.equals(role, userDto.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, username, email, name, clothingSize, role);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userName='" + userName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", clothingSize='" + clothingSize + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
