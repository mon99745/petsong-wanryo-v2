package com.study.demoProject.controller.api.user;

import com.study.demoProject.config.auth.PrincipalDetail;
import com.study.demoProject.domain.user.User;
import com.study.demoProject.model.dto.user.UserSaveRequestDto;
import com.study.demoProject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

//    v1 은 현재 사용 중인 디플로이 연동 API의 버전을 의미.
//    추후 기능 추가 및 변경으로 인하여 API가 바뀌면 v2 등을 사용하게 될 수도 있다.

    /**
     * 회원가입 API
     */
    @PostMapping("/auth/api/v1/user")
    public Long save(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return userService.save(userSaveRequestDto.toEntity());
    }

    /**
     * 회원조회 API
     */
    @GetMapping("/auth/api/v1/users")
    public String list(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users",users);
        return null;
    }

    /**
     * 회원수정 API
     */
    @PutMapping("/api/v1/user")
    // @AuthenticationPrincipal에 PrincipalDetail타입으로 파라미터를 받으면 유저 정보를 얻을 수 있다.
    public Long update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        userService.update(user, principalDetail);
        return user.getCode();
    }

    /**
     * 회원탈퇴 API
     */
    @DeleteMapping("/api/v1/user/{code}")
    // id값을 주소에 받기 위해 @PathVariable
    public Long deleteById(@PathVariable Long code) {
        userService.deleteById(code);
        return code;
    }
}
