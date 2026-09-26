package com.wcy.service;

import com.wcy.dto.LoginDTO;
import com.wcy.vo.LoginVO;

public interface LoginService {

    LoginVO login(LoginDTO loginDTO);
}
