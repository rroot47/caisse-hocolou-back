package ch.service;

import ch.dto.RoleDTO;
import ch.mappers.RoleMappers;
import ch.models.Role;
import ch.repository.RoleReopsitory;
import ch.repository.UtilisateurRepository;
import ch.dto.ResponseUserDTO;
import ch.dto.RequestUserDTO;
import ch.models.Utilisateur;
import ch.mappers.UtilisateurMappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UtilisateurServiceImp implements UtilisateurService{

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMappers  utilisateurMappers;

    private RoleReopsitory roleReopsitory;
    private RoleMappers roleMappers;

    private final PasswordEncoder passwordEncoder;

    public UtilisateurServiceImp(
            UtilisateurRepository utilisateurRepository,
            UtilisateurMappers utilisateurMappers,
            RoleReopsitory roleReopsitory,
            RoleMappers roleMappers,
            PasswordEncoder passwordEncoder)
    {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMappers = utilisateurMappers;
        this.roleReopsitory = roleReopsitory;
        this.roleMappers = roleMappers;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseUserDTO saveUser(RequestUserDTO requestUserDTO) {
      /*  Optional<Utilisateur> utilisateurCheck = utilisateurRepository.findUtilisateurByEmail(requestUserDTO.getEmail());
        if (utilisateurCheck.isEmpty()) {
            throw  new RuntimeException("User already exists");
        }*/
        Utilisateur utilisateur = utilisateurMappers.fromUtilisateurDTO(requestUserDTO);
        if(!utilisateur.getPassword().equals(utilisateur.getConfirmPassword())){
            throw new RuntimeException("Please confirm you password");
        }
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        Utilisateur utilisateursave = utilisateurRepository.save(utilisateur);
        addRoleToUser(utilisateursave.getEmail(), "USER");
        return utilisateurMappers.fromResponseUser(utilisateursave);
    }

    @Override
    public ResponseUserDTO updateUser(RequestUserDTO requestUserDTO) {
        return null;
    }

    @Override
    public List<ResponseUserDTO> getAllUser() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return  utilisateurs.stream()
                .map(utilisateurMappers::fromResponseUser)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseUserDTO getUser(Long user_id) {
        if(user_id!=0){
            Utilisateur utilisateur = utilisateurRepository.findById(user_id)
                    .orElseThrow(()->new NotFoundException("User Not Found"));
            return utilisateurMappers.fromResponseUser(utilisateur);
        }
       return  null;
    }

    @Override
    public void deleteUser(long user_id) {

    }

    public Optional<Utilisateur> findUtilisateurByEmail(String email){
        return utilisateurRepository.findUtilisateurByEmail(email);
    }

    @Override
    public RoleDTO saveRole(RoleDTO roleDTO) {
        Role role = roleMappers.fromRoleDTO(roleDTO);
        Role saveRole = roleReopsitory.save(role);
        return roleMappers.fromRole(saveRole);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
        Role role = roleReopsitory.findRoleByRoleName(roleName);
        utilisateur.get().getRoles().add(role);
    }

    @Override
    public Collection<Role> getRoleName(Long id) {
        Utilisateur roleDTO = utilisateurRepository.findById(id).get();
        return roleDTO.getRoles();
    }

}
