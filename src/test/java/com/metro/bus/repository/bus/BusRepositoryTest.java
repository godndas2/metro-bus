package com.metro.bus.repository.bus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metro.bus.controller.command.BusFormCommand;
import com.metro.bus.controller.ui.DashboardController;
import com.metro.bus.model.bus.Bus;
import com.sun.xml.internal.ws.api.pipe.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BusRepositoryTest {

    MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BusRepository busRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .addFilter(new CharacterEncodingFilter("UTF-8"))
                .build();
    }

    @After
    public void tearDown() {
        busRepository.deleteAll();
    }

    @Test
    public void getFindAll() {
        String code = "CODE-A";
        int capacity = 100;
        String make = "BENZ";

        Bus bus = new Bus();
        bus.setCode(code);
        bus.setCapacity(capacity);
        bus.setMake(make);

        busRepository.save(bus);

        List<Bus> busList = busRepository.findAll();

        Bus busA = busList.get(0);
        assertThat(busA.getCode()).isEqualTo(code);
        assertThat(busA.getCapacity()).isEqualTo(capacity);
        assertThat(busA.getMake()).isEqualTo(make);
    }

    // TODO Invalid mime type "{"code":"CODE-A","capacity":100,"make":"BENZ"}": does not contain '/'
    @Test
    @WithMockUser(roles = "ADMIN")
    public void addToBus() throws Exception {
        String code = "CODE-A";
        int capacity = 100;
        String make = "BENZ";

        BusFormCommand command = new BusFormCommand();
        command.setCode(code);
        command.setCapacity(capacity);
        command.setMake(make);

        String url = "http://localhost:" + port + "/bus";

        mockMvc.perform(post(url)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .contentType(new ObjectMapper().writeValueAsString(command)))
        .andDo(print())
        .andExpect(status().isOk());

        List<Bus> buses = busRepository.findAll();
        assertThat(buses.get(0).getCode()).isEqualTo(code);
        assertThat(buses.get(0).getCapacity()).isEqualTo(capacity);
        assertThat(buses.get(0).getMake()).isEqualTo(make);
    }
}
