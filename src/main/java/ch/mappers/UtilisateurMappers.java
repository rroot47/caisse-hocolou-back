package ch.mappers;

import ch.dto.ResponseUserDTO;
import ch.dto.RequestUserDTO;
import ch.dto.RoleDTO;
import ch.models.Utilisateur;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurMappers {

    public RequestUserDTO fromUtilisateur(Utilisateur utilisateur){
        RequestUserDTO requestUserDTO = new RequestUserDTO();
        CopyMappers.copy(utilisateur, requestUserDTO);
        return requestUserDTO;
    }

    public ResponseUserDTO fromResponseUser(Utilisateur utilisateur){
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        CopyMappers.copy(utilisateur, responseUserDTO);
        return responseUserDTO;
    }

    public Utilisateur fromUtilisateurDTO(RequestUserDTO requestUserDTO){
        Utilisateur utilisateur = new Utilisateur();
        CopyMappers.copy(requestUserDTO, utilisateur);
        return utilisateur;
    }
}
