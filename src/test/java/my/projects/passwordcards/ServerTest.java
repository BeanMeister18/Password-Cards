package my.projects.passwordcards;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.projects.passwordcards.model.Credential;
import my.projects.passwordcards.service.CredentialService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MainProgram.class
)
//@WebMvcTest
@TestPropertySource(locations = "classpath:application.properties")
public class ServerTest {

    @MockBean
    CredentialService credentialService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenCallHealth_ThenReturns200_AndOK() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/health")
                        .accept(MediaType.TEXT_PLAIN))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody, is(notNullValue()));
        assertThat(responseBody, is("OK"));
    }

    @Test
    void whenCallNew_ThenReturns200_AndHTML() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/new")
                        .accept(MediaType.TEXT_HTML))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody, is(notNullValue()));
        assertThat(responseBody, containsStringIgnoringCase("Website Name:"));
        assertThat(responseBody, containsStringIgnoringCase("Username:"));
        assertThat(responseBody, containsStringIgnoringCase("Password:"));
    }

    //@Test
    void whenCallSave_ThenReturns302_AndRedirect() throws Exception {
        Credential cr = new Credential();
        cr.setWebsite("www.website.com");
        cr.setUsername("user");
        cr.setPassword("password");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString((cr)))
                        .accept(MediaType.TEXT_HTML))
                        // 302 - redirect means FOUND
                        .andExpect(status().isFound())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody, is(notNullValue()));
    }

    @Test
    void whenCallHome_ThenReturns200_AndHTML() throws Exception {
        // prepare credentials to be returned by mock service
        Credential cr = new Credential();
        cr.setId(1L);
        cr.setWebsite("www.website-1.com");
        cr.setUsername("user-1");
        cr.setPassword("password-1");
        Credential cr2 = new Credential();
        cr2.setId(2L);
        cr2.setWebsite("www.website-2.com");
        cr2.setUsername("user-2");
        cr2.setPassword("password-2");
        // add to the list
        List<Credential> all = new ArrayList<>();
        all.add(cr);
        all.add(cr2);

        // mock service response
        Mockito.when(credentialService.listAll()).thenReturn(all);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.TEXT_HTML))
                        .andExpect(status().isOk())
                        .andReturn();
        // check response
        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody, is(notNullValue()));
        assertThat(result.getModelAndView().getViewName(), is("index"));
        // response must contain mock credential
        assertThat(responseBody, containsStringIgnoringCase(cr.getWebsite()));
        assertThat(responseBody, containsStringIgnoringCase(cr.getUsername()));
        assertThat(responseBody, containsStringIgnoringCase(cr.getPassword()));
        // check returned list of credentials - must have 2 objects
        Object listCredential = result.getModelAndView().getModel().get("listCredential");
        assertThat(listCredential, is(instanceOf(List.class)));
        assertThat(((List<Credential>)listCredential).size(), is(2));
        // check the second object
        Credential returnedCredential = ((List<Credential>)listCredential).get(1);
        assertThat(returnedCredential.getId(), is(cr2.getId()));
        assertThat(returnedCredential.getWebsite(), is(cr2.getWebsite()));
        assertThat(returnedCredential.getUsername(), is(cr2.getUsername()));
        assertThat(returnedCredential.getPassword(), is(cr2.getPassword()));
    }

    @Test
    void whenCallEdit_ThenReturns200_AndEditPage() throws Exception {

        // prepare credentials to be returned by mock service
        Credential cr = new Credential();
        cr.setId(1L);
        cr.setWebsite("www.website-1.com");
        cr.setUsername("user-1");
        cr.setPassword("password-1");

        // mock service response
        Mockito.when(credentialService.get(1)).thenReturn(cr);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/edit/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.TEXT_HTML))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody, is(notNullValue()));
        assertThat(result.getModelAndView().getViewName(), is("editCredential"));
        // response must contain mock credential
        assertThat(responseBody, containsStringIgnoringCase("Edit Credential"));
        assertThat(responseBody, containsStringIgnoringCase(cr.getWebsite()));
        assertThat(responseBody, containsStringIgnoringCase(cr.getUsername()));
        assertThat(responseBody, containsStringIgnoringCase(cr.getPassword()));
        // check returned model
        Object model = result.getModelAndView().getModel().get("credential");
        assertThat(model, is(instanceOf(Credential.class)));
        // check the second object
        Credential returnedCredential = (Credential)model;
        assertThat(returnedCredential.getId(), is(cr.getId()));
        assertThat(returnedCredential.getWebsite(), is(cr.getWebsite()));
        assertThat(returnedCredential.getUsername(), is(cr.getUsername()));
        assertThat(returnedCredential.getPassword(), is(cr.getPassword()));
    }

    @Test
    void whenCallDelete_ThenReturns302_AndRedirect() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.TEXT_HTML))
                        // 302 - redirect means FOUND
                        .andExpect(status().isFound())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody, is(notNullValue()));
    }
}
