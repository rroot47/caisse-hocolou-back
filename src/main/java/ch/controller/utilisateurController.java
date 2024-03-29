package ch.controller;

import ch.dto.ResponseUserDTO;
import ch.dto.RequestUserDTO;
import ch.dto.RoleDTO;
import ch.dto.UserRoleDTO;
import ch.models.Role;
import ch.models.Utilisateur;
import ch.service.UtilisateurService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("hc")
@CrossOrigin("*")
@SecurityRequirement(name = "Bearer Authorization")
@OpenAPIDefinition(tags = {})
@Tag(name = "API USER")
public class utilisateurController {

    private final UtilisateurService utilisateurService;

    public utilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
//839076191090
    @GetMapping("/users")
    public List<ResponseUserDTO> getAllUser(){
        return utilisateurService.getAllUser();
    }

    @GetMapping("/user")
    public ResponseUserDTO getUserByCode(@Param("code") String code){
        return utilisateurService.getUserByCode(code);
    }
    @GetMapping("/user/{id}")
    public ResponseUserDTO getUser(@PathVariable("id")  Long user_id){
        if(user_id!=0){
            return utilisateurService.getUser(user_id);
        }
        return null;
    }
    @GetMapping("/user/email")
    public Optional<Utilisateur> findUtilisateurEmail(@Param("email") String email){
        return utilisateurService.getUserByEmail(email);
    }
    @GetMapping("/role/{id}")
    public Collection<Role> getRoleName(@PathVariable("id") Long id){
        return utilisateurService.getRoleName(id);
    }

   /* @PostMapping("/user")
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseUserDTO addUser(@RequestBody RequestUserDTO requestUserDTO) throws SQLException {
        return utilisateurService.saveUser(requestUserDTO);
    }*/

    @PostMapping("/role")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public RoleDTO saveRole(@RequestBody RoleDTO roleDTO){
        return utilisateurService.saveRole(roleDTO);
    }

    @PostMapping("/addRoleToUser")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void addRoleToUser(@RequestBody UserRoleDTO userRoleDTO){
        utilisateurService.addRoleToUser(userRoleDTO.getEmail(), userRoleDTO.getRoleName());
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteUser(@PathVariable("id") Long user_id){
        if(user_id!=0){
            utilisateurService.deleteUser(user_id);
        }
    }

    @PostMapping("/user")
    public ResponseUserDTO register(@RequestBody  RequestUserDTO requestUserDTO, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException, SQLDataException {
        return utilisateurService.addUser(requestUserDTO, getSiteURL(request));
        //return "register_success";
    }

    @GetMapping("/verify")
    public boolean verifyUser(@Param("codeVerify") String codeVerify) {
        return utilisateurService.verify(codeVerify);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
