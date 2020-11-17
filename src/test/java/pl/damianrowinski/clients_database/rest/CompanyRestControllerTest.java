package pl.damianrowinski.clients_database.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.damianrowinski.clients_database.domain.dto.CompanyDTO;
import pl.damianrowinski.clients_database.services.CompanyService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CompanyRestController.class)
class CompanyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyService companyService;

    private long companyId1;
    private CompanyDTO companyData1;
    private CompanyDTO companyData2;
    private List<CompanyDTO> companyDataList;
    private String companyJSON;

    @BeforeEach
    void init() throws JsonProcessingException {
        companyId1 = 1;
        companyData1 = new CompanyDTO();
        companyData1.setId(companyId1);
        companyData1.setCompanyName("Coca-cola");
        companyData1.setModificationDate(LocalDateTime.now());
        companyData1.setNip(111_111);

        companyData2 = new CompanyDTO();
        companyData2.setId(2);
        companyData2.setCompanyName("Pepsi Co");
        companyData2.setModificationDate(LocalDateTime.now().minusDays(2));
        companyData2.setNip(222_222);

        companyDataList = List.of(companyData1, companyData2);

        companyJSON = objectMapper.writeValueAsString(companyData1);
    }


    @Test
    void givenMainApiPathShouldReturnListOfCompaniesData() throws Exception {
        String companyListJSON = objectMapper.writeValueAsString(companyDataList);
        when(companyService.findAll()).thenReturn(companyDataList);

        mockMvc.perform(get("/api/company"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(companyListJSON));
    }

    @Test
    void givenPathVariableShouldReturnCompanyData() throws Exception {
        when(companyService.findById(companyId1)).thenReturn(Optional.of(companyData1));

        mockMvc.perform(get("/api/company/{id}", companyId1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(companyJSON));
    }

    @Test
    void givenIdNotPresentInDatabaseShouldReturnNotFound() throws Exception {
        when(companyService.findById(companyId1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/company/{id}", companyId1))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenCompanyDataJsonShouldReturnCompanyDataJson() throws Exception {
        when(companyService.add(companyData1)).thenReturn(companyData1);

        mockMvc.perform(post("/api/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(companyJSON));
    }

//    @Test
//    void givenCompanyJsonShouldReturnUriWithId() throws Exception {
//        String companyURIPatern = "/api/company/" + companyData1.getId();
//        when(companyService.add(companyData1)).thenReturn(companyData1);
//
//        mockMvc.perform(post("/api/company")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(companyJSON))
//                .andExpect(redirectedUrlPattern(companyURIPatern));
//    }


}