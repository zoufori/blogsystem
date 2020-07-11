package com.jimmy.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.jimmy.blogsystem.dao.ArticleMapper;
import com.jimmy.blogsystem.dao.CommentMapper;
import com.jimmy.blogsystem.dao.StatisticMapper;
import com.jimmy.blogsystem.model.ResponseData.StaticticsBo;
import com.jimmy.blogsystem.model.domain.Article;
import com.jimmy.blogsystem.model.domain.Comment;
import com.jimmy.blogsystem.model.domain.Statistic;
import com.jimmy.blogsystem.service.ISiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SiteServiceImpl implements ISiteService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public List<Comment> recentComments(int count) throws Exception {
        PageHelper.startPage(1, count > 10 || count < 1 ? 10 : count);
        return commentMapper.selectNewComment();
    }

    @Override
    public List<Article> recentArrticles(int count) throws Exception {
        PageHelper.startPage(1, count > 10 || count < 1 ? 10 : count);
        List<Article> articleList = articleMapper.selectArticleWithPage();
        //封装文章统计数据
        for (Article article : articleList) {
            Statistic statistic = statisticMapper.selectStatisticWithArticleId(article.getId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        return articleList;
    }

    @Override
    public StaticticsBo getStatistics() throws Exception {
        StaticticsBo staticticsBo = new StaticticsBo();
        Integer articles = articleMapper.countArticle();
        Integer comments = commentMapper.countComment();
        staticticsBo.setArticles(articles);
        staticticsBo.setComments(comments);
        return staticticsBo;
    }

    @Override
    public void updateStatistics(Article article) throws Exception {
        Statistic statistic = statisticMapper.selectStatisticWithArticleId(article.getId());
        statistic.setHits(statistic.getHits() + 1);
        statisticMapper.updateArticleHitsWithId(statistic);
    }
}
