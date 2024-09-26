import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.comments.commentservice.controller.CommentController;
import ru.comments.commentservice.dto.CommentDto;
import ru.comments.commentservice.dto.NewCommentDto;
import ru.comments.commentservice.dto.UpdateCommentDto;
import ru.comments.commentservice.service.CommentService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentController commentController;

    private final CommentDto commentDto = new CommentDto(1, 2, "description");

    private final NewCommentDto newCommentDto = new NewCommentDto(1, 2, "description");

    private final UpdateCommentDto updateCommentDto = new UpdateCommentDto(1, 2, "description");

    @Test
    @SneakyThrows
    void addCommentTest() {
        when(commentService.add(any(NewCommentDto.class))).thenReturn(commentDto);

        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCommentDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId", is(commentDto.getUserId()), Integer.class))
                .andExpect(jsonPath("$.newsId", is(commentDto.getNewsId())))
                .andExpect(jsonPath("$.description", is(commentDto.getDescription())));
    }

    @Test
    @SneakyThrows
    void updateCommentTest() {
        when(commentService.update(any(Integer.class), any(UpdateCommentDto.class))).thenReturn(commentDto);

        mockMvc.perform(patch("/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommentDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId", is(updateCommentDto.getUserId())))
                .andExpect(jsonPath("$.description", is(updateCommentDto.getDescription())))
                .andExpect(jsonPath("$.newsId", is(updateCommentDto.getNewsId().toString())));
    }

    @Test
    @SneakyThrows
    void deleteCommentTest() {
        mockMvc.perform(delete("/comments/1")).andExpect(status().isNoContent());
    }

    @SneakyThrows
    @Test
    void getByIdTest() {
        when(commentService.getById(any(Integer.class))).thenReturn(commentDto);

        mockMvc.perform(get("/comments/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId", is(commentDto.getUserId()), Integer.class))
                .andExpect(jsonPath("$.newsId", is(commentDto.getNewsId())))
                .andExpect(jsonPath("$.description", is(commentDto.getDescription())));
    }
    void getUsersTest() throws Exception {
        int page = 1;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        when(commentService.getComments(any(Integer.class), any()))
                .thenReturn(List.of(commentDto));

        mockMvc.perform(get("/comments")
                        .param("news_Id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$.[0].userId", is(commentDto.getUserId()), Integer.class))
                .andExpect(jsonPath("$.[0].newsId", is(commentDto.getNewsId())))
                .andExpect(jsonPath("$.[0].description", is(commentDto.getDescription())));
    }

}