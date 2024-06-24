package com.capstone.closetconnect.services;

import com.capstone.closetconnect.dtos.request.UpdateUser;
import com.capstone.closetconnect.dtos.response.UserDetail;
import com.capstone.closetconnect.exceptions.NotFoundException;
import com.capstone.closetconnect.models.User;
import com.capstone.closetconnect.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService{

    private final UserRepository userRepository;

    @Override
    public UserDetail updateUser(Long userId, UpdateUser user) {
        User userToBeUpdated = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("user",userId));
        updateUserEntity(userToBeUpdated, user);
        User updatedUser = userRepository.save(userToBeUpdated);
        return updatedUser.toUserDto(userToBeUpdated);
    }

    private static void updateUserEntity(User userToBeUpdated, UpdateUser updateDetails){
        userToBeUpdated.setUserName(updateDetails.getUserName());
        userToBeUpdated.setTopSize(updateDetails.getTopSize());
        userToBeUpdated.setBottomSize(updateDetails.getBottomSize());
        userToBeUpdated.setEmail(updateDetails.getEmail());
        userToBeUpdated.setName(updateDetails.getName());
        userToBeUpdated.setRole(updateDetails.getRole());
        userToBeUpdated.setGender(updateDetails.getGender());
    }

}
