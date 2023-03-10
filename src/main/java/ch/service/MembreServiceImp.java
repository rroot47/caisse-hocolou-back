package ch.service;

import ch.dto.AllMemberDTO;
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

import java.util.ArrayList;
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
        int somme =0;
        int saveMontant= 0;
        int saveMontantDTO = 0;
        Membre membre = membreRepository.findById(membre_id).get();
        saveMontant += membre.getMontantTotals();
        membre.setNom(membreDTO.getNom()==null? membre.getNom():membreDTO.getNom());
        membre.setPrenom(membreDTO.getPrenom()==null? membre.getPrenom():membreDTO.getPrenom());
        membre.setTelephone(membreDTO.getTelephone()==0? membre.getTelephone():membreDTO.getTelephone());
        membre.setDomicile(membreDTO.getDomicile()==null? membre.getDomicile():membreDTO.getDomicile());
        membre.setMontantAdhesion(membreDTO.getMontantAdhesion()==0?membre.getMontantAdhesion():membreDTO.getMontantAdhesion());
        saveMontantDTO+=membreDTO.getAdherant().stream().mapToInt(adherant -> (int) adherant.getMontant()).sum();
        membre.setAdherant(membreDTO.getAdherant()==null?membre.getAdherant(): membreDTO.getAdherant());
        somme = saveMontant+saveMontantDTO;
        membre.setMontantTotals(somme);
        membreRepository.save(membre);
        return membreMappers.fromMembre(membre);
    }
    //List<Integer> listAnneMembre = Arrays.stream(membre.getAdherant()).map(Adherant::getAnnee).toList();
    // List<Integer> listAnneMembreDTO = Arrays.stream(membreDTO.getAdherant()).map(Adherant::getAnnee).toList();
    @Override
    public List<AllMemberDTO> getAllMembres() {
        List<Membre> members = membreRepository.findAll();

        return members.stream()
                    .map(membreMappers::fromAllMembre)
                    .collect(Collectors.toList());
    }
    public List<AllMemberDTO> getPageMembres(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Membre> pageMembre = membreRepository.findAll(pageable);
        if (pageMembre.hasContent()){
            return pageMembre.getContent().stream().map(membreMappers::fromAllMembre)
                    .collect(Collectors.toList());
        }else {
            return new ArrayList<AllMemberDTO>();
        }
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
