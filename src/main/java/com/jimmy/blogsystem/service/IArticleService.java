package com.jimmy.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.jimmy.blogsystem.model.domain.Article;

import java.util.List;

public interface IArticleService {

    //分页查询文章列表
    public PageInfo<Article> selectArticleWithPage(Integer page, Integer count) throws Exception;

    //统计热度排名前十的文章信息
    public List<Article> getHeatArticles() throws Exception;

    //根据文章id查询单个文章详情
    public Article selectArticleWithId(Integer id) throws Exception;

    //发布文章
    public void publish(Article article) throws Exception;

    //根据主键更新文章
    public void updateArticleWithId(Article article) throws Exception;

    //根据主键删除文章
    public void deleteArticleWithId(int id) throws Exception;
}
