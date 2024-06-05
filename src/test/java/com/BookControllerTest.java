package com;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@ContextConfiguration(classes = { BookController.class })
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
            .apply(documentationConfiguration(restDocumentation))
            .build();
    }

    @Test
    public void givenBooks_whenGetBooks_thenStatus200() throws Exception {
        this.mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andDo(document("", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), responseFields(
                fieldWithPath("[].id").description("The ID of the book").type(JsonFieldType.NUMBER),
                fieldWithPath("[].title").description("The title of the book").type(JsonFieldType.STRING),
                fieldWithPath("[].author").description("The author of the book").type(JsonFieldType.STRING),
                fieldWithPath("[].isbn").description("The ISBN of the book").type(JsonFieldType.STRING)
            )));
    }
}

