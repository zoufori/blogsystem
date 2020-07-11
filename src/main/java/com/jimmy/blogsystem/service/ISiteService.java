package com.jimmy.blogsystem.service;

import com.jimmy.blogsystem.model.ResponseData.StaticticsBo;
import com.jimmy.blogsystem.model.domain.Article;
import com.jimmy.blogsystem.model.domain.Comment;

import java.util.List;

public interface ISiteService {

    //最新收到的评论
    public List<Comment> recentComments(int count) throws Exception;
    //最新发表的文章
    public List<Article> recentArrticles(int count) throws Exception;
    //获取后台统计数据
    public StaticticsBo getStatistics() throws Exception;
    //更新某个文章的统计数据
    public void updateStatistics(Article article) throws Exception;

}
