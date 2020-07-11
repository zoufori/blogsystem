package com.jimmy.blogsystem.dao;

import com.jimmy.blogsystem.model.domain.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    // 根据id查询文章信息
    @Select("select * from t_article where id = #{id}")
    public Article selectArticleWithId(Integer id) throws Exception;

    //发表文章，同时使用@Options注解获取自动生成的主键id
    @Insert("insert into t_article (title, created, modified, tags, categories," +
            "allow_comment, thumbnail, content)" +
            "values (#{title},#{created},#{modified},#{tags},#{categories},#{allow_comment},#{thumbnail},#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer publishArticle(Article article) throws Exception;

    //文章分页查询
    @Select("select * from t_article order by id desc")
    public List<Article> selectArticleWithPage() throws Exception;

    //通过id删除文章
    @Delete("delete from t_article where id = #{id}")
    public void deleteArticleWithId(int id) throws Exception;

    //站点服务统计，统计文章数量
    @Select("select count(1) from t_article")
    public Integer countArticle() throws Exception;

    //通过id更新文章
    public Integer updateArticleWithId(Article article) throws Exception;
}
