package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.domain.Comment;
import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.CommentDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.exception.CardNotFoundException;
import com.woowahan.woowahan2018.exception.CommentNotFoundException;
import com.woowahan.woowahan2018.security.SignedInUser;
import com.woowahan.woowahan2018.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("")
    public CommonResponse postComment(@SignedInUser User signedInUser,
                                      @RequestBody
                                      @Valid CommentDto commentDto) throws CardNotFoundException {

        long cardId = commentDto.getCardId();
        Comment comment = commentService.createComment(signedInUser, cardId, commentDto);
        return CommonResponse.success("COMMENT.CREATE", comment);
    }

    @DeleteMapping("/{commentId}")
    public CommonResponse deleteComment(@SignedInUser User signedInUser,
                                        @PathVariable long commentId) throws CommentNotFoundException {

        Comment comment = commentService.deleteComment(signedInUser, commentId);
        return CommonResponse.success("COMMENT.DELETE", comment);
    }
}
