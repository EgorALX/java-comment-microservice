package ru.comments.commentservice.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.PageRequest;
import ru.comments.commentservice.dto.CommentDto;
import ru.comments.commentservice.dto.NewCommentDto;
import ru.comments.commentservice.dto.UpdateCommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getUsers(Integer newsId, PageRequest pageRequest);

    CommentDto getById(@Positive Integer commentId);

    CommentDto add(@Valid NewCommentDto dto);

    UpdateCommentDto update(@Positive Integer commentId, @Valid UpdateCommentDto dto);

    void removeById(@Positive Integer commentId);
}
