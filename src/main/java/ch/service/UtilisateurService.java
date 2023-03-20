package ch.service;

import ch.dto.ResponseUserDTO;
import ch.dto.RequestUserDTO;
import ch.dto.RoleDTO;
import ch.models.Role;
import ch.models.Utilisateur;;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    ResponseUserDTO saveUser(RequestUserDTO requestUserDTO) throws SQLException;
    ResponseUserDTO updateUser(RequestUserDTO requestUserDTO);
    List<ResponseUserDTO> getAllUser();
    ResponseUserDTO getUser(Long user_id);
    void deleteUser(long user_id);

    public Optional<Utilisateur> findUtilisateurByEmail(String email);

    RoleDTO saveRole(RoleDTO roleDTO);
    void addRoleToUser(String nom, String roleName);

    Collection<Role> getRoleName(Long id);

    public boolean verify(String verificationCode);
    public void sendVerificationEmail(Utilisateur requestUserDTO, String siteURL) throws MessagingException, UnsupportedEncodingException;

    public ResponseUserDTO addUser(RequestUserDTO requestUserDTO, String siteURL) throws MessagingException, UnsupportedEncodingException, SQLDataException;

    ResponseUserDTO getUserByCode(String code);

    Optional<Utilisateur>  getUserByEmail(String email);
}
