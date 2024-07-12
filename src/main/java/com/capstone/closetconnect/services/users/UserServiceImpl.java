package com.capstone.closetconnect.services.users;

import com.capstone.closetconnect.dtos.request.UpdateUser;
import com.capstone.closetconnect.dtos.response.UserDetail;
import com.capstone.closetconnect.exceptions.NotFoundException;
import com.capstone.closetconnect.exceptions.UserAlreadyExistsException;
import com.capstone.closetconnect.models.User;
import com.capstone.closetconnect.repositories.UserRepository;
import com.capstone.closetconnect.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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
        updateUserEntity(userToBeUpdated, user);
        User updatedUser = userRepository.save(userToBeUpdated);
        return updatedUser.toUserDto(userToBeUpdated);
    }

    @Override
    public UserDetail getUser(Long userId) {
        User userFound = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("user",userId));
        return userFound.toUserDto(userFound);
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
