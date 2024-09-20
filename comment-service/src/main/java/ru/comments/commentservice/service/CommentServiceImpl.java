package ru.comments.commentservice.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.comments.commentservice.dto.CommentDto;
import ru.comments.commentservice.dto.NewCommentDto;
import ru.comments.commentservice.dto.UpdateCommentDto;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public List<CommentDto> getUsers(Integer newsId, PageRequest pageRequest) {
        return List.of();
    }

    @Override
    public CommentDto getById(Integer commentId) {
        return null;
    }

    @Override
    public CommentDto add(NewCommentDto dto) {
        return null;
    }

    @Override
    public UpdateCommentDto update(Integer commentId, UpdateCommentDto dto) {
        return null;
    }

    @Override
    public void removeById(Integer commentId) {

    }
}
