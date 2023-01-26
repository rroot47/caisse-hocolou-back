package ch.service;

import ch.dto.ResponseUserDTO;
import ch.dto.RequestUserDTO;
import ch.dto.RoleDTO;
import ch.models.Role;
import ch.models.Utilisateur;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UtilisateurService {

    ResponseUserDTO saveUser(RequestUserDTO requestUserDTO);
    ResponseUserDTO updateUser(RequestUserDTO requestUserDTO);
    List<ResponseUserDTO> getAllUser();
    ResponseUserDTO getUser(Long user_id);
    void deleteUser(long user_id);

    public Optional<Utilisateur> findUtilisateurByEmail(String email);

    RoleDTO saveRole(RoleDTO roleDTO);
    void addRoleToUser(String nom, String roleName);

    Collection<Role> getRoleName(Long id);


}
