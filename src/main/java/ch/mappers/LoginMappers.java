package ch.mappers;

import ch.dto.RequestLoginDTO;
import ch.dto.ResponsetLoginDTO;
import ch.models.Signing;
import org.springframework.stereotype.Service;

@Service
public class LoginMappers {

    public RequestLoginDTO fromLogin(Signing signing){
        RequestLoginDTO requestLoginDTO = new RequestLoginDTO();
        CopyMappers.copy(signing, requestLoginDTO);
        return requestLoginDTO;
    }

    public ResponsetLoginDTO fromResponseLogin(Signing signing){
        ResponsetLoginDTO responsetLoginDTO = new ResponsetLoginDTO();
        CopyMappers.copy(signing, responsetLoginDTO);
        return responsetLoginDTO;
    }

    public Signing fromLoginDTO(RequestLoginDTO requestLoginDTO){
        Signing signing = new Signing();
        CopyMappers.copy(requestLoginDTO, signing);
        return signing;
    }
}
