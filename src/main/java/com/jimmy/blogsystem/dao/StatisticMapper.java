package com.jimmy.blogsystem.dao;

import com.jimmy.blogsystem.model.domain.Article;
import com.jimmy.blogsystem.model.domain.Statistic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StatisticMapper {

    //新增文章对应的统计信息
    @Insert("insert into t_statistic(article_id, hits, comments_num) " +
            "values (#{id}, 0, 0)")
    public void addStatistic(Article article) throws Exception;

    //根据文章id查询点击量和评论量相关信息
    @Select("select * from t_statistic where article_id = #{articleId}")
    public Statistic selectStatisticWithArticleId(Integer articleId) throws Exception;

    //通过文章id更新点击量
    @Update("update t_statistic set hits=#{hits} " +
            "where article_id = #{articleId}")
    public void updateArticleHitsWithId(Statistic statistic) throws Exception;

    //通过文章id更新评论量
    @Update("update t_statistic set comments_num = #{commentsNum} " +
            "where article_id = #{articleId}")
    public void updateArticleCommentsWithId(Statistic statistic) throws Exception;

    //根据文章id删除统计数据
    @Delete("delete from t_statistic where article_id = #{aid}")
    public void deleteStatisticWithId(int aid) throws Exception;

    //统计文章热度信息
    @Select("select * from t_statistic where hits != '0' " +
            "order by hits desc, comments_num desc")
    public List<Statistic> getStatistic() throws Exception;

    //统计博客文章总访问量
    @Select("select sum(hits) from t_statistic")
    public long getTotalVisit() throws Exception;

    //统计博客文章总评论量
    @Select("select sum(comments_num) from t_statistic")
    public long getTotalComment() throws Exception;
}
