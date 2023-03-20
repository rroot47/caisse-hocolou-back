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
import net.bytebuddy.utility.RandomString;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UtilisateurServiceImp implements UtilisateurService{

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMappers  utilisateurMappers;

    private final RoleReopsitory roleReopsitory;
    private final RoleMappers roleMappers;

    private final PasswordEncoder passwordEncoder;


    private final JavaMailSender mailSender;

    public UtilisateurServiceImp(
            UtilisateurRepository utilisateurRepository,
            UtilisateurMappers utilisateurMappers,
            RoleReopsitory roleReopsitory,
            RoleMappers roleMappers,
            PasswordEncoder passwordEncoder, JavaMailSender mailSender)
    {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMappers = utilisateurMappers;
        this.roleReopsitory = roleReopsitory;
        this.roleMappers = roleMappers;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Override
    public ResponseUserDTO saveUser(RequestUserDTO requestUserDTO) throws SQLException {
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
        addRoleToUser(requestUserDTO.getEmail(), "USER");
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

    /*@Override
    public void register(RequestUserDTO requestUserDTO, String siteURL) throws MessagingException, UnsupportedEncodingException{
        Utilisateur utilisateur = utilisateurMappers.fromUtilisateurDTO(requestUserDTO);
        if(!utilisateur.getPassword().equals(utilisateur.getConfirmPassword())){
            throw new RuntimeException("Please confirm you password");
        }
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        String randomCode = RandomString.make(64);
        utilisateur.setVerificationCode(randomCode);
        utilisateur.setEnabled(false);
        utilisateurRepository.save(utilisateur);
        addRoleToUser(utilisateur.getEmail(), "USER");

        sendVerificationEmail(utilisateur, siteURL);
    }*/

    @Override
    public ResponseUserDTO addUser(RequestUserDTO requestUserDTO, String siteURL) throws MessagingException, UnsupportedEncodingException, SQLDataException {
        Utilisateur utilisateur = utilisateurMappers.fromUtilisateurDTO(requestUserDTO);
        if(!utilisateur.getPassword().equals(utilisateur.getConfirmPassword())){
            throw new RuntimeException("Please confirm you password");
        }
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        String randomCode = RandomString.make(6);
        utilisateur.setVerificationCode(randomCode);
        utilisateur.setEnabled(false);
        Utilisateur utilisateursave = utilisateurRepository.save(utilisateur);
        addRoleToUser(requestUserDTO.getEmail(), "USER");
        sendVerificationEmail(utilisateur, siteURL);
        return utilisateurMappers.fromResponseUser(utilisateursave);
    }

    @Override
    public ResponseUserDTO getUserByCode(String code) {
        if(code==null){
            return null;
        }
        Utilisateur utilisateur = utilisateurRepository.findByVerificationCode(code);
        return utilisateurMappers.fromResponseUser(utilisateur);
    }

    @Override
    public Optional<Utilisateur> getUserByEmail(String email) {
        return utilisateurRepository.findUtilisateurByEmail(email);
    }

    @Override
    public void sendVerificationEmail(Utilisateur requestUserDTO, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = requestUserDTO.getEmail();
        String fromAddress = "boubousylla2@gmail.com";
        String senderName = "hocolou";
        String subject = "Veuillez vérifier votre inscription";
        String content = "Bonjour [[name]],<br>"
               /* + "Please click the link below to verify your registration:<br>"*/
                + "Veuillez copier le code ci-dessous pour vérifier votre inscription:<br>"
                /*+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"*/
                + "<h3>Votre code de verification : \"[[CODE]]\"</h3>"
                + "Merci,<br>"
                + "CAISSE HOCOLOU.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", requestUserDTO.getPrenom());
        //String verifyURL = siteURL + "/verify?code=" + requestUserDTO.getVerificationCode();

        String verifyCode = requestUserDTO.getVerificationCode();
        content = content.replace("[[CODE]]", verifyCode);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public boolean verify(String verificationCode) {
        Utilisateur user = utilisateurRepository.findByVerificationCode(verificationCode);
        if (user == null || user.isEnabled()) {
            return false;
        } else {
           // user.setVerificationCode(null);
            user.setEnabled(true);
            utilisateurRepository.save(user);
            return true;
        }

    }


}
