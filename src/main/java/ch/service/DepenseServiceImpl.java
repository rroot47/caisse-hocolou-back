package ch.service;

import ch.dto.AllMemberDTO;
import ch.dto.RequestDepenseDTO;
import ch.dto.ResponseDepenseDTO;
import ch.mappers.DepenseMappers;
import ch.models.Depense;
import ch.models.Membre;
import ch.repository.DepenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepenseServiceImpl implements DepenseService{

    private final DepenseRepository depenseRepository;
    private final DepenseMappers depenseMappers;

    public DepenseServiceImpl(DepenseRepository depenseRepository, DepenseMappers depenseMappers) {
        this.depenseRepository = depenseRepository;
        this.depenseMappers = depenseMappers;
    }


    @Override
    public ResponseDepenseDTO addDepense(RequestDepenseDTO requestDepenseDTO) {
        Depense  depense = depenseMappers.fromDepense(requestDepenseDTO);
        Depense saveDepense = depenseRepository.save(depense);
        return depenseMappers.fromResponseDepense(saveDepense);
    }

    @Override
    public ResponseDepenseDTO updateDepense(Long id, RequestDepenseDTO requestDepenseDTO) {
        Depense depense = depenseRepository.findById(id).get();
        depense.setTypeDepense(requestDepenseDTO.getTypeDepense()==null?depense.getTypeDepense():requestDepenseDTO.getTypeDepense());
        depense.setDescription(requestDepenseDTO.getDescription()==null?depense.getDescription():requestDepenseDTO.getDescription());
        depense.setNom(requestDepenseDTO.getNom()==null?depense.getNom(): requestDepenseDTO.getNom());
        depense.setPrenom(requestDepenseDTO.getPrenom()==null?depense.getPrenom(): requestDepenseDTO.getPrenom());
        depense.setDate(requestDepenseDTO.getDate()==null?depense.getDate(): requestDepenseDTO.getDate());
        depense.setSomme(requestDepenseDTO.getSomme()==0?depense.getSomme(): requestDepenseDTO.getSomme());
        depenseRepository.save(depense);
        return depenseMappers.fromResponseDepense(depense);
    }

    @Override
    public List<ResponseDepenseDTO> getAllDepense() {
        List<Depense> depenses = depenseRepository.findAll();
        return depenses.stream().map(depenseMappers::fromResponseDepense).collect(Collectors.toList());
    }

    @Override
    public ResponseDepenseDTO getDepense(Long id) {
        if(id!=0){
            Depense depense = depenseRepository.findById(id)
                    .orElseThrow(()->new NotFoundException("User Not Found"));
            return depenseMappers.fromResponseDepense(depense);
        }
        return  null;
    }

    @Override
    public double montantTotolDepense() {
        return depenseRepository.montantTotolDepense();
    }

}
