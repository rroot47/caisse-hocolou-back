package ch.controller;

import ch.dto.ResponseUserDTO;
import ch.dto.RequestUserDTO;
import ch.dto.RoleDTO;
import ch.dto.UserRoleDTO;
import ch.models.Role;
import ch.service.UtilisateurService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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

    @GetMapping("/user/{id}")
    public ResponseUserDTO getUser(@PathVariable("id")  Long user_id){
        if(user_id!=0){
            return utilisateurService.getUser(user_id);
        }
        return null;
    }

    @GetMapping("/role/{id}")
    public Collection<Role> getRoleName(@PathVariable("id") Long id){
        return utilisateurService.getRoleName(id);
    }

    @PostMapping("/user")
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseUserDTO addUser(@RequestBody RequestUserDTO requestUserDTO){
        return utilisateurService.saveUser(requestUserDTO);
    }

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
}
