package com.jimmy.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jimmy.blogsystem.dao.ArticleMapper;
import com.jimmy.blogsystem.dao.CommentMapper;
import com.jimmy.blogsystem.dao.StatisticMapper;
import com.jimmy.blogsystem.model.domain.Article;
import com.jimmy.blogsystem.model.domain.Statistic;
import com.jimmy.blogsystem.service.IArticleService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public PageInfo<Article> selectArticleWithPage(Integer page, Integer count) throws Exception {
        PageHelper.startPage(page, count);
        List<Article> articleList = articleMapper.selectArticleWithPage();
        //封装文章统计数据
        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            Statistic statistic = statisticMapper.selectStatisticWithArticleId(article.getId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        return pageInfo;
    }

    @Override
    public List<Article> getHeatArticles() throws Exception {
        List<Statistic> list = statisticMapper.getStatistic();
        List<Article> articleList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Article article = articleMapper.selectArticleWithId(list.get(i).getArticleId());
            article.setHits(list.get(i).getHits());
            article.setCommentsNum(list.get(i).getCommentsNum());
            articleList.add(article);
            if(i >= 9)
                break;
        }
        return articleList;
    }

    @Override
    public Article selectArticleWithId(Integer id) throws Exception {
        Article article = null;
        Object o = redisTemplate.opsForValue().get("article_" + id);
        if(o!=null){
            article = (Article)o;
        }else{
            article = articleMapper.selectArticleWithId(id);
            if(article != null){
                redisTemplate.opsForValue().set("article_" + id, article);
            }
        }
        return article;
    }

    @Override
    public void publish(Article article) throws Exception {
        //去除表情
        article.setContent(EmojiParser.parseToAliases(article.getContent()));
        article.setCreated(new Date());
        article.setHits(0);
        article.setCommentsNum(0);
        //插入文章，同时插入文章统计数据
        articleMapper.publishArticle(article);
        statisticMapper.addStatistic(article);
    }

    @Override
    public void updateArticleWithId(Article article) throws Exception{
        article.setModified(new Date());
        articleMapper.updateArticleWithId(article);
        redisTemplate.delete("article_" + article.getId());
    }

    //删除文章
    @Override
    public void deleteArticleWithId(int id) throws Exception {
        //删除文章的同时，删除对应的缓存
        articleMapper.deleteArticleWithId(id);
        redisTemplate.delete("article_" + id);
        //同时删除对应文章的统计数据
        statisticMapper.deleteStatisticWithId(id);
        //同时删除对应文章的评论数据
        commentMapper.deleteCommentWithId(id);
    }
}
