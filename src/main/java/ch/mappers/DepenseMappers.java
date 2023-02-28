package ch.mappers;

import ch.dto.RequestDepenseDTO;
import ch.dto.ResponseDepenseDTO;
import ch.models.Depense;
import org.springframework.stereotype.Service;

@Service
public class DepenseMappers {

    public ResponseDepenseDTO fromResponseDepense(Depense depense){
        ResponseDepenseDTO responseDepenseDTO = new ResponseDepenseDTO();
        CopyMappers.copy(depense, responseDepenseDTO);
        return responseDepenseDTO;
    }

    public Depense fromDepense(RequestDepenseDTO requestDepenseDTO){
        Depense depense = new Depense();
        CopyMappers.copy(requestDepenseDTO, depense);
        return depense;
    }
}
