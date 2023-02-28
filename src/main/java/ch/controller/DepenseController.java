package ch.controller;


import ch.dto.RequestDepenseDTO;
import ch.dto.ResponseDepenseDTO;
import ch.service.DepenseService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hc")
@CrossOrigin("*")
@SecurityRequirement(name = "Bearer Authorization")
@OpenAPIDefinition(tags = {})
@Tag(name = "API DEPENSE")
public class DepenseController {

    private final DepenseService depenseService;


    public DepenseController(DepenseService depenseService) {
        this.depenseService = depenseService;
    }

    @GetMapping("/depenses")
    public List<ResponseDepenseDTO> getAllDepense(){
        return depenseService.getAllDepense();
    }

    @GetMapping("/depense/{id}")
    public ResponseDepenseDTO getDepense(@PathVariable("id") Long id){
        return depenseService.getDepense(id);
    }

    @PostMapping("/depense")
    private ResponseDepenseDTO addDepense(@RequestBody RequestDepenseDTO requestDepenseDTO){
        return depenseService.addDepense(requestDepenseDTO);
    }
}
