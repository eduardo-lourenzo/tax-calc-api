package br.edu.zup.tax_calc_api.dtos;

import br.edu.zup.tax_calc_api.models.RoleEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterResponseDTOTest {

    @Test
    void testRegisterResponseDTO_AllArgsConstructor() {
        Long id = 1L;
        String username = "testUser";
        RoleEnum role = RoleEnum.USER;

        RegisterResponseDTO responseDTO = new RegisterResponseDTO(id, username, role);

        assertEquals(id, responseDTO.getId(), "O ID deve ser igual ao valor fornecido.");
        assertEquals(username, responseDTO.getUsername(), "O username deve ser igual ao valor fornecido.");
        assertEquals(role, responseDTO.getRole(), "O role deve ser igual ao valor fornecido.");
    }

    @Test
    void testRegisterResponseDTO_SettersAndGetters() {
        RegisterResponseDTO responseDTO = new RegisterResponseDTO(null, null, null);
        Long id = 2L;
        String username = "anotherUser";
        RoleEnum role = RoleEnum.ADMIN;

        responseDTO.setId(id);
        responseDTO.setUsername(username);
        responseDTO.setRole(role);

        assertEquals(id, responseDTO.getId(), "O ID deve ser igual ao valor definido.");
        assertEquals(username, responseDTO.getUsername(), "O username deve ser igual ao valor definido.");
        assertEquals(role, responseDTO.getRole(), "O role deve ser igual ao valor definido.");
    }
}