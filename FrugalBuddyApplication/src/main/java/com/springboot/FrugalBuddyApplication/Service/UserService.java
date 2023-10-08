package com.springboot.FrugalBuddyApplication.Service;

import com.springboot.FrugalBuddyApplication.Dto.LoginDTO;
import com.springboot.FrugalBuddyApplication.Dto.UserDTO;

import com.springboot.FrugalBuddyApplication.Response.LoginResponse;
import org.springframework.stereotype.Service;

public interface UserService {


    String addUser(UserDTO userDTO);


    LoginResponse loginUser(LoginDTO loginDTO);

    //LoginMesage loginEmployee(LoginDTO loginDTO);
}
