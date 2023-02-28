package ch.controller;

import ch.dto.RequestLoginDTO;
import ch.dto.SigninDTO;
import ch.service.AuthService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("auth")
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

   // @PostMapping("/signin")
    //@PreAuthorize("hasAuthority('SCOPE_USER')")
    public Map<String, String> signIn(@RequestBody RequestLoginDTO loginUserDTO){
        return  null;//authService.signIn(loginUserDTO);
    }

    @PostMapping("/signin")
    public SigninDTO signinAuth(@RequestBody RequestLoginDTO loginUserDTO){
        return authService.signinAuth(loginUserDTO);
    }

   /* @PostMapping("/signin")
    public ResponsetLoginDTO signIn(@RequestBody RequestLoginDTO requestLoginDTO){
        return authService.login(requestLoginDTO);
    }*/
}
