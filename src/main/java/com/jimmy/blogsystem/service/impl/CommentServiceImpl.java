package com.jimmy.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jimmy.blogsystem.dao.CommentMapper;
import com.jimmy.blogsystem.dao.StatisticMapper;
import com.jimmy.blogsystem.model.domain.Comment;
import com.jimmy.blogsystem.model.domain.Statistic;
import com.jimmy.blogsystem.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public PageInfo<Comment> getComments(Integer aid, int page, int count) throws Exception {
        PageHelper.startPage(page, count);
        List<Comment> commentList = commentMapper.selectCommentWithPage(aid);
        PageInfo<Comment> commentInfo = new PageInfo<>(commentList);
        return commentInfo;
    }

    @Override
    public void pushComment(Comment comment) throws Exception {
        commentMapper.pushComment(comment);
        //更新文章评论数据量
        Statistic statistic = statisticMapper.selectStatisticWithArticleId(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum() + 1);
        statisticMapper.updateArticleCommentsWithId(statistic);
    }
}
