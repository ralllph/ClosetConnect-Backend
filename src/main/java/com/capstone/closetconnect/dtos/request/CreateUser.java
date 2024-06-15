package com.capstone.closetconnect.dtos.request;

import com.capstone.closetconnect.enums.ClothSize;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.enums.Role;
import com.capstone.closetconnect.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUser {

    @NotBlank(message = "user name cannot be blank")
    private String userName;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "Password must be at least 8 characters long and contain at least one letter, one number," +
                    "and a special character)"
    )
    private String password;

    @NotNull(message = "Top size cannot be blank")
    private ClothSize topSize;

    @NotNull(message = "Bottom size cannot be blank")
    private ClothSize bottomSize;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Role cannot be blank")
    private Role role;

    @NotNull(message = "Gender cannot be blank")
    private Gender gender;


    public static User toUserEntity(CreateUser userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setTopSize(userDto.getTopSize());
        user.setRole(userDto.getRole());
        user.setGender(userDto.getGender());
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateUser that = (CreateUser) o;
        return Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && topSize == that.topSize && bottomSize == that.bottomSize && Objects.equals(email, that.email) && Objects.equals(name, that.name) && role == that.role && gender == that.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, topSize, bottomSize, email, name, role, gender);
    }

    @Override
    public String toString() {
        return "CreateUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", topSize=" + topSize +
                ", bottomSize=" + bottomSize +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", gender=" + gender +
                '}';
    }

}
