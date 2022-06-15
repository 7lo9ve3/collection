package com.example.collection.controllers.views;

import com.example.collection.models.values.UserValue;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.collection.models.entities.SecurityUser;
import com.example.collection.models.entities.UserRole.RoleType;
import com.example.collection.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserService userService;

    @GetMapping(value = "/")
    public String index(@AuthenticationPrincipal SecurityUser securityUser){
        if(securityUser != null){
            if(securityUser.getRoleTypes().contains(RoleType.ROLE_VIEW)) {
                return "redirect:/v";
            }
        }
        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String login(@AuthenticationPrincipal SecurityUser SecurityUser){
        if(SecurityUser != null){
            if(SecurityUser.getRoleTypes().contains(RoleType.ROLE_VIEW)){ //getAuthorities()로, 유저의 role을 가져올 수 있다.
                return "redirect:/v";
            }
        }
        return "login/login";
    }

    @GetMapping(value = "/err/denied-page")
    public String accessDenied(){
        return "err/deniedPage";
    }

    @GetMapping(value = "/join")
    public String joinForm(@AuthenticationPrincipal SecurityUser securityUser){
        if(securityUser != null && securityUser.getRoleTypes().contains(RoleType.ROLE_VIEW)){
            return "redirect:/v";
        }
        return "login/join";
    }

    @ResponseBody
    @PostMapping(value = "/join")
    public Map<String, Object> join(@RequestBody UserValue value){
        Map<String, Object> response = new HashMap<>();
        if(userService.findByEmail(value.getEmail()).isPresent()){
            response.put("duplicate", true);
            return response;
        }

        response.put("success", userService.join(value) != null ? true : false);
        return response;

    }
}
