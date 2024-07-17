package com.capstone.closetconnect.models;

import com.capstone.closetconnect.dtos.request.CreateUser;
import com.capstone.closetconnect.dtos.response.UserDetail;
import com.capstone.closetconnect.enums.ClothSize;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users_table")
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "username" , nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    @Column(name = "top_size")
    @Enumerated(EnumType.STRING)
    private ClothSize topSize;

    @Column(name = "bottom_size")
    @Enumerated(EnumType.STRING)
    private ClothSize bottomSize;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<ClothingItems> clothingItems;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Donations> donations;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Message> receivedMessages;

   @OneToMany(mappedBy = "user", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
   @JsonIgnore
    private List<Bid> bids;

    @OneToMany(mappedBy = "ratedBy", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JsonIgnore
    private List<Ratings> ratingsGiven;

    @OneToMany(mappedBy = "ratedUser", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JsonIgnore
    private List<Ratings> ratingsReceived;

    @OneToMany(mappedBy = "reportedBy", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JsonIgnore
    private List<Report> reportsSent;

    @OneToMany(mappedBy = "reportedUser", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JsonIgnore
    private List<Report> reportsReceived;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Trades> sentTrades;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Trades> receivedTrades;

    public  UserDetail toUserDto(User user) {
        UserDetail userDto = new UserDetail();
        userDto.setUserName(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setTopSize(user.getTopSize());
        userDto.setBottomSize(user.getBottomSize());
        userDto.setGender(user.getGender());
        userDto.setRole(user.getRole());
        return userDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(name, user.name) && topSize == user.topSize && bottomSize == user.bottomSize && Objects.equals(clothingItems, user.clothingItems) && Objects.equals(donations, user.donations) && gender == user.gender && role == user.role && Objects.equals(sentMessages, user.sentMessages) && Objects.equals(receivedMessages, user.receivedMessages) && Objects.equals(bids, user.bids) && Objects.equals(ratingsGiven, user.ratingsGiven) && Objects.equals(ratingsReceived, user.ratingsReceived) && Objects.equals(reportsSent, user.reportsSent) && Objects.equals(reportsReceived, user.reportsReceived);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, email, name, topSize, bottomSize, clothingItems, donations, gender, role, sentMessages, receivedMessages, bids, ratingsGiven, ratingsReceived, reportsSent, reportsReceived);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", topSize=" + topSize +
                ", bottomSize=" + bottomSize +
                ", gender=" + gender +
                ", role=" + role +
                ", bids=" + bids +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //authorities are the roles added in enum.. role refers to role field
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
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
        return UserDetails.super.isEnabled();
    }
}
