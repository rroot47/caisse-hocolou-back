package ch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String roleName;
}
