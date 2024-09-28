package ru.comments.commentservice.dto;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDeletionEvent {

    @Positive
    private Integer newsId;

}