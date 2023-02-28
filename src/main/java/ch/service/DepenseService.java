package ch.service;

import ch.dto.RequestDepenseDTO;
import ch.dto.ResponseDepenseDTO;

import java.util.List;

public interface DepenseService {

    ResponseDepenseDTO addDepense(RequestDepenseDTO requestDepenseDTO);
    ResponseDepenseDTO updateDepense(Long id, RequestDepenseDTO requestDepenseDTO);
    List<ResponseDepenseDTO> getAllDepense();
    ResponseDepenseDTO getDepense(Long id);

}
