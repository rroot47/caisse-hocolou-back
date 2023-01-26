package ch.mappers;

import ch.dto.AllMemberDTO;
import ch.dto.MembreDTO;
import ch.models.Membre;
import org.springframework.stereotype.Service;

@Service
public class MembreMappers {
    public MembreDTO fromMembre(Membre membre){
        MembreDTO membreDTO=new MembreDTO();
        CopyMappers.copy(membre, membreDTO);
        return membreDTO;
    }

    public AllMemberDTO fromAllMembre(Membre membre){
        AllMemberDTO membreDTO=new AllMemberDTO();
        CopyMappers.copy(membre, membreDTO);
        return membreDTO;
    }



    public Membre fromMembreDTO(MembreDTO membreDTO){
        Membre membre=new Membre();
        CopyMappers.copy(membreDTO, membre);
        return membre;
    }
}
