package ru.comments.commentservice.consumer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.comments.commentservice.dto.NewCommentDto;
import ru.comments.commentservice.dto.NewsDeletionEvent;
import ru.comments.commentservice.service.CommentService;

@Component
@RequiredArgsConstructor
@Validated
@EnableKafka
public class KafkaConsumer {

    private final CommentService commentService;

    @KafkaListener(topics = "create-comment-topic", groupId = "comment-create-group")
    public void consume(@Valid NewCommentDto request) {
        commentService.createComment(request);
    }

    @KafkaListener(topics = "news-deletion-topic", groupId = "news-delete-group")
    public void deleteNews(@Valid NewsDeletionEvent deletionEvent) {
        Integer id = deletionEvent.getNewsId();
        commentService.deleteCommentsByNewsId(id);
    }
}