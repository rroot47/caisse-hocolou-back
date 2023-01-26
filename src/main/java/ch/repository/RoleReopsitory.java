package ch.repository;

import ch.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoleReopsitory extends JpaRepository<Role, Long> {

    Role findRoleByRoleName(String roleName);

}
