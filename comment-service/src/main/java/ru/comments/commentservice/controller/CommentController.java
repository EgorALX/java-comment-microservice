package ru.comments.commentservice.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.comments.commentservice.dto.CommentDto;
import ru.comments.commentservice.dto.NewCommentDto;
import ru.comments.commentservice.dto.UpdateCommentDto;
import ru.comments.commentservice.service.CommentService;

import java.util.List;

@Slf4j
@RequestMapping("/comments")
@RestController
@Validated
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getComments(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                        @RequestParam(required = false, name = "news_Id") Integer newsId) {
        log.info("Starting getComments method. Getting comments with params: page={}, size={}, newsId={}",
                page, size, newsId);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        List<CommentDto> comments = commentService.getUsers(newsId, pageRequest);
        log.info("Completed getComments method successfully. Results: {}", comments);
        return comments;
    }

    @GetMapping("/{commentId}")
    public CommentDto getById(@PathVariable @Positive Integer commentId) {
        log.info("Starting getById method. Getting user by commentId={}", commentId);
        CommentDto comment = commentService.getById(commentId);
        log.info("Completed getById method successfully. Result: {}", comment);
        return comment;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto add(@Valid @RequestBody NewCommentDto dto) {
        log.info("Starting add method. Creating comment: {}", dto.toString());
        CommentDto comment = commentService.add(dto);
        log.info("Completed add method successfully. Result: {}", comment);
        return comment;
    }

    @PatchMapping("/{commentId}")
    public UpdateCommentDto update(@PathVariable @Positive Integer commentId,
                                   @Valid @RequestBody UpdateCommentDto dto) {
        log.info("Starting update method. Updating userId={}", commentId);
        UpdateCommentDto comment = commentService.update(commentId, dto);
        log.info("Completed update method successfully. Result: {}", comment);
        return comment;
    }

    @DeleteMapping("/{commentId}")
    public void removeById(@PathVariable @Positive Integer commentId) {
        log.info("Starting removeById method. Removing commentId={}", commentId);
        commentService.removeById(commentId);
        log.info("Completed removeById method successfully");
    }
}
