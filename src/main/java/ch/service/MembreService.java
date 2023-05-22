package ch.service;

import ch.dto.AllMemberDTO;
import ch.dto.MembreDTO;
import ch.dto.PaginationDTO;

import java.util.List;

public interface MembreService {
    MembreDTO saveMembre(MembreDTO membreDTO);
    MembreDTO updateMembre(Long id, MembreDTO membreDTO);
    PaginationDTO getAllMembresPage(int page, int size);
    List<AllMemberDTO> getAllMembres();
    MembreDTO getMembre(Long membre_id);
    void deleteMembre(long membre_id);
    double montantTotol();
}
