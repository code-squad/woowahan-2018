package com.woowahan.woowahan2018.service;

import com.woowahan.woowahan2018.domain.*;
import com.woowahan.woowahan2018.dto.CommentDto;
import com.woowahan.woowahan2018.exception.CardNotFoundException;
import com.woowahan.woowahan2018.exception.CommentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CardRepository cardRepository;

    @Transactional
    public Comment createComment(User signedInUser, long cardId, CommentDto commentDto) throws CardNotFoundException {
        Card card = Optional.ofNullable(cardRepository.findOne(cardId))
                .orElseThrow(CardNotFoundException::new);

        card.checkMember(signedInUser);
        return commentRepository.save(commentDto.toComment(card, signedInUser));
    }

    @Transactional
    public Comment deleteComment(User signedInUser, long commentId) throws CommentNotFoundException {
        Comment comment = findOneComment(commentId);
        comment.checkMember(signedInUser);

        commentRepository.delete(commentId);
        return comment;
    }

    private Comment findOneComment(long commentId) throws CommentNotFoundException {
        return Optional.ofNullable(commentRepository.findOne(commentId))
                .orElseThrow(CommentNotFoundException::new);
    }
}
