package telran.java57;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import telran.java57.forum.Application;

@SpringBootTest (classes = {Application.class})
@AutoConfigureMockMvc
//@ContextConfiguration
class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/forum/posts/author/uz")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Java is the best for backend")));
    }
}


//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.Test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
////import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import telran.java57.forum.posts.controller.PostController;
//import telran.java57.forum.posts.service.PostServiceImpl;
//
//@WebMvcTest(PostController.class)
//class ApplicationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
// //   @MockBean
//    private PostServiceImpl service;
//
//    @Test
//    void greetingShouldReturnMessageFromService() throws Exception {
//        //when(service.findPostsByAuthor()).thenReturn("Hello, Mock");
//        this.mockMvc.perform(get("/posts/author/uz")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("Java is the best for backend")));
//    }
//}
//
