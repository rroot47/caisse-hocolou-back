package ch.service;

import ch.dto.AllMemberDTO;
import ch.dto.PaginationDTO;
import ch.repository.MembreRepository;
import ch.dto.MembreDTO;
import ch.models.Membre;
import ch.mappers.MembreMappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MembreServiceImp implements  MembreService{
    private final MembreRepository membreRepository;
    private final MembreMappers membreMappers;

    public MembreServiceImp(MembreRepository membreRepository, MembreMappers membreMappers) {
        this.membreRepository = membreRepository;
        this.membreMappers = membreMappers;
    }

    @Override
    public MembreDTO saveMembre(MembreDTO membreDTO) {
        int somme=0;
        Membre membre = membreMappers.fromMembreDTO(membreDTO);
        somme+=membre.getMontantAdhesion();
        somme += membre.getAdherant().stream().mapToInt(adherant -> (int) adherant.getMontant()).sum();
        membre.setMontantTotals(somme);
        Membre saveMembre = membreRepository.save(membre);
        return membreMappers.fromMembre(saveMembre);
    }

    @Override
    public MembreDTO updateMembre(Long membre_id, MembreDTO membreDTO) {
        double somme =0;
        double saveMontant= 0;
        double saveMontantDTO = 0;
        Membre membre = membreRepository.findById(membre_id).get();
        membre.setMontantTotals(somme);
        saveMontant += membre.getMontantTotals();
        membre.setNom(membreDTO.getNom()==null? membre.getNom():membreDTO.getNom());
        membre.setPrenom(membreDTO.getPrenom()==null? membre.getPrenom():membreDTO.getPrenom());
        membre.setTelephone(membreDTO.getTelephone()==0? membre.getTelephone():membreDTO.getTelephone());
        membre.setDomicile(membreDTO.getDomicile()==null? membre.getDomicile():membreDTO.getDomicile());
        membre.setMontantAdhesion(membreDTO.getMontantAdhesion()==0?membre.getMontantAdhesion():membreDTO.getMontantAdhesion());
        saveMontantDTO+=membreDTO.getAdherant().stream().mapToInt(adherant -> (int) adherant.getMontant()).sum();
        membre.setAdherant(membreDTO.getAdherant()==null?membre.getAdherant(): membreDTO.getAdherant());
        somme = saveMontant+saveMontantDTO+membre.getMontantAdhesion();
        membre.setMontantTotals(somme);
        membreRepository.save(membre);
        return membreMappers.fromMembre(membre);
    }
    //List<Integer> listAnneMembre = Arrays.stream(membre.getAdherant()).map(Adherant::getAnnee).toList();
    // List<Integer> listAnneMembreDTO = Arrays.stream(membreDTO.getAdherant()).map(Adherant::getAnnee).toList();
    @Override
    public PaginationDTO getAllMembresPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Membre> members = membreRepository.findAll(pageable);
        List<Membre> membreList = members.getContent();
        List<AllMemberDTO> allMemberDTOS = membreList.stream().map(membreMappers::fromAllMembre).toList();
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setAllMemberDTOS(allMemberDTOS);
        paginationDTO.setCurrentPage(page);
        paginationDTO.setPageSize(size);
        paginationDTO.setTotalPages(members.getTotalPages());
        return paginationDTO;//members.stream().map(membreMappers::fromAllMembre).collect(Collectors.toList());
    }

    @Override
    public List<AllMemberDTO> getAllMembres() {
        List<Membre> membres = membreRepository.findAll();
        return membres.stream().map(membreMappers::fromAllMembre).collect(Collectors.toList());
    }

    @Override
    public MembreDTO getMembre(Long membre_id) {
        if(membre_id!=0){
            Membre membre = membreRepository.findById(membre_id)
                    .orElseThrow(()->new NotFoundException("Arctile Not Found"));
            return membreMappers.fromMembre(membre);
        }
        return null;
    }

    @Override
    public void deleteMembre(long membre_id) {
        if(membre_id!=0){
            membreRepository.deleteById(membre_id);
        }
    }
    @Override
    public double montantTotol() {
      return  membreRepository.montantTotol();
    }

}
