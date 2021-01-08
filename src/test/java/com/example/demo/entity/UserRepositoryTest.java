package com.example.demo.entity;

import com.example.demo.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserRepositoryTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;


    @Test
    void registrationWorksThroughAllLayers() throws Exception
    {
        String name = "Pesho1321a2aaaaa";
        UserDto user = new UserDto(name, "I1vanov");

        mockMvc.perform(post("/saveUser", user)
                .flashAttr("user", user)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)));


        User userEntity = userRepository.findByName(name).orElse(null);

        assertThat(Objects.requireNonNull(userEntity).getName()).isEqualTo(name);
    }
}