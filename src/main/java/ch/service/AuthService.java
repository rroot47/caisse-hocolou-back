package ch.service;

import ch.dto.RequestLoginDTO;
import ch.dto.SigninDTO;
import ch.mappers.LoginMappers;
import ch.models.Role;
import ch.models.Utilisateur;
import ch.repository.RoleReopsitory;
import ch.repository.SigningRepo;
import ch.repository.UtilisateurRepository;
import com.google.gson.JsonObject;
import net.minidev.json.JSONArray;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class AuthService {
    private final JwtEncoder jwtEncoder;
    //private JwtDecoder jwtDecoder;
    private AuthenticationManager authenticationManager;
    //private UserDetailsService userDetailsService;
    private LoginMappers loginMappers;
    private SigningRepo signingRepo;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurRepository utilisateurRepository;
    private final RoleReopsitory roleReopsitory;

    public AuthService(JwtEncoder jwtEncoder, AuthenticationManager authenticationManager, LoginMappers loginMappers, SigningRepo signingRepo, PasswordEncoder passwordEncoder, UtilisateurRepository utilisateurRepository, RoleReopsitory roleReopsitory) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
        this.loginMappers = loginMappers;
        this.signingRepo = signingRepo;
        this.passwordEncoder = passwordEncoder;
        this.utilisateurRepository = utilisateurRepository;
        this.roleReopsitory = roleReopsitory;
    }

   public Map<String, String> signIn(RequestLoginDTO requestLoginDTO) {
        String subject;
        List<String> scope=null;
        Map<String, String> idToken=new HashMap<>();
        Instant instant=Instant.now();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestLoginDTO.getEmail(), requestLoginDTO.getPassword())
        );
        Optional<Utilisateur> utilisateur = utilisateurRepository.findUtilisateurByEmail(requestLoginDTO.getEmail());

       List<String> role= utilisateur.get().getRoles().stream().map(Role::getRoleName).toList();
        subject=authentication.getName();
        scope=authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(3, ChronoUnit.HOURS))
                .issuer("auth-service")
                .claim("scope",scope)
                .build();
        String jwtAccessToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
       Optional<Utilisateur> user = utilisateurRepository.findUtilisateurByEmail(requestLoginDTO.getEmail());
        idToken.put("accessToken",jwtAccessToken);
        idToken.put("user", user.get().getNom());
        idToken.put("roles", JSONArray.toJSONString(scope));
        return idToken;
    }

   public SigninDTO signinAuth(RequestLoginDTO requestLoginDTO){
       String subject = null;
       SigninDTO signinDTO = new SigninDTO();
       List<String> scope=null;
       List<SigninDTO> token=null;
       Instant instant=Instant.now();
       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(requestLoginDTO.getEmail(), requestLoginDTO.getPassword())
       );
       subject=authentication.getName();
       scope=authentication.getAuthorities()
               .stream().map(GrantedAuthority::getAuthority)
               .collect(Collectors.toList());
       JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
               .subject(subject)
               .issuedAt(instant)
               .expiresAt(instant.plus(3, ChronoUnit.HOURS))
               .issuer("auth-service")
               .claim("scope",scope)
               .build();
       String jwtAccessToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
       Optional<Utilisateur> user = utilisateurRepository.findUtilisateurByEmail(requestLoginDTO.getEmail());
      signinDTO.setToken(jwtAccessToken);
      signinDTO.setId(user.get().getId());
      signinDTO.setUsername(user.get().getNom());
      signinDTO.setRoles(scope);

      // SigninDTO signinDTO = new SigninDTO(jwtAccessToken, user.get().getNom(), scope);
      // return  List.of(jwtAccessToken, scope, user.get().getId(), user.get().getNom());
       return signinDTO;
   }
}
