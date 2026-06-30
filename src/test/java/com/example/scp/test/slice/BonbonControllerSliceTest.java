package com.example.scp.test.slice;

import com.example.scp.component.BonbonComponent;
import com.example.scp.controller.BonbonController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BonbonController.class)
class BonbonControllerSliceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private BonbonComponent bonbonComponent;

    @Test
    @WithMockUser(roles = {"USER"})
    void test() throws Exception {
        mockMvc.perform(get("/bonbon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value("health"));
    }

}
