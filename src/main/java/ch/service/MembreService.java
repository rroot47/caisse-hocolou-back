package ch.service;

import ch.dto.AllMemberDTO;
import ch.dto.MembreDTO;

import java.util.List;

public interface MembreService {

    MembreDTO saveMembre(MembreDTO membreDTO);
    MembreDTO updateMembre(Long id, MembreDTO membreDTO);
    List<AllMemberDTO> getAllMembres();
    public List<AllMemberDTO> getPageMembres(Integer pageNumber, Integer pageSize);
    MembreDTO getMembre(Long membre_id);
    void deleteMembre(long membre_id);
    double montantTotol();
}
