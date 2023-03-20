package ch.controller;

import ch.dto.AllMemberDTO;
import ch.dto.MembreDTO;
import ch.dto.PaginationDTO;
import ch.service.MembreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hc")
@CrossOrigin("*")
@SecurityRequirement(name = "Bearer Authorization")
@Tag(name = "API MEMBRE")
public class MembreController {

    private final MembreService membreService;

    public MembreController(MembreService membreService) {
        this.membreService = membreService;
    }

    @GetMapping("/membres")
    public List<AllMemberDTO> getAllMembre(){
        return membreService.getAllMembres();
    }

    @GetMapping("/membres/pages")
    public PaginationDTO getAllMembrePages(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10")int size){
        return membreService.getAllMembresPage(page, size);
    }

    @GetMapping("/membre/{id}")
    public MembreDTO getMembre(@PathVariable("id")  Long member_id){
        if(member_id!=0){
            return membreService.getMembre(member_id);
        }
        return null;
    }

    @GetMapping("/membre/montantTotal")
    public double montantTotol(){
        return membreService.montantTotol();
    }

    @PostMapping("/membre")
    public MembreDTO addMembre(@RequestBody MembreDTO membreDTO){
        return membreService.saveMembre(membreDTO);
    }

    @PatchMapping("/membre/{id}")
    public MembreDTO updateMembre(@PathVariable("id") Long id, @RequestBody MembreDTO membreDTO){
        return membreService.updateMembre(id,membreDTO);
    }

    @DeleteMapping("/membre/{id}")
    public void deleteMembre(@PathVariable("id") Long member_id){
        if(member_id!=0){
            membreService.deleteMembre(member_id);
        }
    }
}

