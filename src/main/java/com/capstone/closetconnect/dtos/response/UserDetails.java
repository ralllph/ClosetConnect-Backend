package com.capstone.closetconnect.dtos.response;


import com.capstone.closetconnect.enums.ClothSize;
import com.capstone.closetconnect.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {

    private Long id;

    private String userName;

    private ClothSize topSize;

    private ClothSize bottomSize;

    private String email;

    private String name;

    private Role role;


}
