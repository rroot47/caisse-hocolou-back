package ch.service;

import ch.repository.UtilisateurRepository;
import ch.models.Utilisateur;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    public UserDetailsServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
        if (utilisateur.isEmpty()) {
            throw new UsernameNotFoundException("Invalid user");
        }
      /*  UserDetails userDetails = User.withUsername(utilisateur.get().getEmail())
                .password(utilisateur.get().getPassword())
                .disabled(false)
                .authorities(new ArrayList<>())
                .build();*/
        return  new User(utilisateur.get().getEmail(), utilisateur.get().getPassword(), new ArrayList<>());
    }

}
