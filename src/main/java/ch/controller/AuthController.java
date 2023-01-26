package ch.controller;

import ch.dto.RequestLoginDTO;
import ch.dto.SigninDTO;
import ch.service.AuthService;
import ch.service.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("auth")
@CrossOrigin("*")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

   // @PostMapping("/signin")
    //@PreAuthorize("hasAuthority('SCOPE_USER')")
    public Map<String, String> signIn(@RequestBody RequestLoginDTO loginUserDTO){
        return authService.signIn(loginUserDTO);
    }

    @PostMapping("/signin")
    public SigninDTO signinAuth(@RequestBody RequestLoginDTO loginUserDTO){
        return authService.signinAuth(loginUserDTO);
    }

   /* @PostMapping("/signin")
    public ResponsetLoginDTO signIn(@RequestBody RequestLoginDTO requestLoginDTO){
        return authService.login(requestLoginDTO);
    }*/
   @RequestMapping(value = "/username", method = RequestMethod.GET)
   public String home(@AuthenticationPrincipal UserDetailsServiceImpl user) {
       System.out.println(user);
   return null;
   }
}
