package ch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsetLoginDTO {
    private String token;
    private RoleDTO[] roleDTOS;
}
