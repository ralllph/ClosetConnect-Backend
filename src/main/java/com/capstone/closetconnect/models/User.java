package com.capstone.closetconnect.models;

import com.capstone.closetconnect.dtos.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users_table")
public class User implements Serializable {

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

    private String clothingSize;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<ClothingItems> clothingItems;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Donations> donations;

    @Column(nullable = false)
    private String role = "USER";

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Message> receivedMessages;

   @OneToMany(mappedBy = "user", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Bid> bids;

    @OneToMany(mappedBy = "ratedBy", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    private List<Ratings> ratingsGiven;

    @OneToMany(mappedBy = "ratedUser", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    private List<Ratings> ratingsReceived;

    @OneToMany(mappedBy = "reportedBy", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    private List<Report> reportsSent;

    @OneToMany(mappedBy = "reportedUser", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    private List<Report> reportsReceived;


    public static UserDto toUserEntity(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setClothingSize(user.getClothingSize());
        userDto.setRole(user.getRole());
        return userDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(name, user.name) && Objects.equals(clothingSize, user.clothingSize) && Objects.equals(clothingItems, user.clothingItems) && Objects.equals(donations, user.donations) && Objects.equals(role, user.role) && Objects.equals(sentMessages, user.sentMessages) && Objects.equals(receivedMessages, user.receivedMessages) && Objects.equals(bids, user.bids) && Objects.equals(ratingsGiven, user.ratingsGiven) && Objects.equals(ratingsReceived, user.ratingsReceived) && Objects.equals(reportsSent, user.reportsSent) && Objects.equals(reportsReceived, user.reportsReceived);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, email, name, clothingSize, clothingItems, donations, role, sentMessages, receivedMessages, bids, ratingsGiven, ratingsReceived, reportsSent, reportsReceived);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", clothingSize='" + clothingSize + '\'' +
                ", clothingItems=" + clothingItems +
                ", donations=" + donations +
                ", role='" + role + '\'' +
                ", sentMessages=" + sentMessages +
                ", receivedMessages=" + receivedMessages +
                ", bids=" + bids +
                ", ratingsGiven=" + ratingsGiven +
                ", ratingsReceived=" + ratingsReceived +
                ", reportsSent=" + reportsSent +
                ", reportsReceived=" + reportsReceived +
                '}';
    }
    
}
