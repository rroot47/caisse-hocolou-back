package ch.service;

import ch.dto.RequestLoginDTO;
import ch.dto.SigninDTO;
import ch.models.Utilisateur;
import ch.repository.UtilisateurRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    private final AuthenticationManager authenticationManager;
    //private UserDetailsService userDetailsService;
    private final UtilisateurRepository utilisateurRepository;

    public AuthService(JwtEncoder jwtEncoder, AuthenticationManager authenticationManager, UtilisateurRepository utilisateurRepository) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
        this.utilisateurRepository = utilisateurRepository;

    }

  /* public Map<String, String> signIn(RequestLoginDTO requestLoginDTO) {
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
    }*/

   public SigninDTO signinAuth(RequestLoginDTO requestLoginDTO){
       String subject = null;
       SigninDTO signinDTO = new SigninDTO();
       List<String> scope=null;
       List<SigninDTO> token=null;
       Instant instant=Instant.now();
       Optional<Utilisateur> user = utilisateurRepository.findUtilisateurByEmail(requestLoginDTO.getEmail());
        //TODO
     /*  if(!user.get().getPassword().equals(requestLoginDTO.getPassword())){
           throw new RuntimeException("password is not available");
       }*/
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
      signinDTO.setToken(jwtAccessToken);
      signinDTO.setId(user.get().getId());
      signinDTO.setUsername(user.get().getNom());
      signinDTO.setEmail(user.get().getEmail());
      signinDTO.setRoles(scope);

      // SigninDTO signinDTO = new SigninDTO(jwtAccessToken, user.get().getNom(), scope);
      // return  List.of(jwtAccessToken, scope, user.get().getId(), user.get().getNom());
       return signinDTO;
   }
}
