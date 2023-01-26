package ch.dto;

import ch.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninDTO {

    private Long id;
    private String token;
    private String username;
    private List<String> roles;
}
