package com.capstone.closetconnect.dtos.request;


import com.capstone.closetconnect.enums.ClothSize;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.enums.Role;
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
public class UpdateUser {

    @NotBlank(message = "user name cannot be blank")
    private String userName;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateUser that = (UpdateUser) o;
        return Objects.equals(userName, that.userName) && topSize == that.topSize && bottomSize == that.bottomSize && Objects.equals(email, that.email) && Objects.equals(name, that.name) && role == that.role && gender == that.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, topSize, bottomSize, email, name, role, gender);
    }

    @Override
    public String toString() {
        return "UpdateUser{" +
                "userName='" + userName + '\'' +
                ", topSize=" + topSize +
                ", bottomSize=" + bottomSize +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", gender=" + gender +
                '}';
    }

}
