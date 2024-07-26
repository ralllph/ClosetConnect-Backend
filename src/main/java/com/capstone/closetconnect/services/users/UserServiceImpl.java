package com.capstone.closetconnect.services.users;

import com.capstone.closetconnect.dtos.request.UpdateUser;
import com.capstone.closetconnect.dtos.response.UserDetail;
import com.capstone.closetconnect.exceptions.NotFoundException;
import com.capstone.closetconnect.exceptions.UserAlreadyExistsException;
import com.capstone.closetconnect.models.User;
import com.capstone.closetconnect.repositories.UserRepository;
import com.capstone.closetconnect.services.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetail updateUser(Long userId, UpdateUser user) {
        User userToBeUpdated = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("user",userId));
        userRepository.findByEmail(user.getEmail()).ifPresent(existingUser->
        {
            throw new UserAlreadyExistsException(user.getEmail());
        });
        User updatedUser = updateUserEntity(userToBeUpdated, user);
        userRepository.save(updatedUser);
        return updatedUser.toUserDto(updatedUser);
    }

    @Override
    public UserDetail getUser(Long userId) {
        User userFound = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("user",userId));
        return userFound.toUserDto(userFound);
    }

    private  User updateUserEntity(User userToBeUpdated, UpdateUser updateDetails){
        if (updateDetails.getUserName() != null) {
            userToBeUpdated.setUserName(updateDetails.getUserName());
        }
        if (updateDetails.getTopSize() != null) {
            userToBeUpdated.setTopSize(updateDetails.getTopSize());
        }
        if (updateDetails.getBottomSize() != null) {
            userToBeUpdated.setBottomSize(updateDetails.getBottomSize());
        }
        if (updateDetails.getEmail() != null) {
            userToBeUpdated.setEmail(updateDetails.getEmail());
        }
        if (updateDetails.getName() != null) {
            userToBeUpdated.setName(updateDetails.getName());
        }
        if (updateDetails.getRole() != null) {
            userToBeUpdated.setRole(updateDetails.getRole());
        }
        if (updateDetails.getGender() != null) {
            userToBeUpdated.setGender(updateDetails.getGender());
        }
        return  userRepository.save(userToBeUpdated);
    }

}
