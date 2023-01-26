package ch.mappers;


import ch.dto.MembreDTO;
import ch.dto.RoleDTO;
import ch.models.Membre;
import ch.models.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleMappers {

    public RoleDTO fromRole(Role role){
        RoleDTO roleDTO=new RoleDTO();
        CopyMappers.copy(role, roleDTO);
        return roleDTO;
    }

    public Role fromRoleDTO(RoleDTO roleDTO){
        Role role=new Role();
        CopyMappers.copy(roleDTO, role);
        return role;
    }
}
