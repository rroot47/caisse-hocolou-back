package ch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO {
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<AllMemberDTO> allMemberDTOS;
}
