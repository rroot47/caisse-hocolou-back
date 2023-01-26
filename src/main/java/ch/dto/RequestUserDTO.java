package ch.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDTO {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String confirmPassword;
}
