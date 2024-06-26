package com.capstone.closetconnect.services;

import com.capstone.closetconnect.dtos.request.UpdateUser;
import com.capstone.closetconnect.dtos.response.UserDetail;

public interface UserService {

    UserDetail updateUser(Long userId, UpdateUser user);

}
