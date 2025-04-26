package edu.misosnovskaya.utils;

import edu.misosnovskaya.entity.CommentEntity;
import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.entity.TagEntity;
import org.springframework.jdbc.core.RowMapper;

public class SqlRequestUtils {

    /*
     *  Posts
     * */
    public static final String DELETE_ALL_POSTS_SQL = "delete from posts";

    public static final String SELECT_POST_TAGS_LINK_SQL = """
                select t.id, t.name
                  from tags t
                inner join post_tags pt on pt.tag_id = t.id
                  where pt.post_id = ?
            """;

    public static final String SEARCH_POSTS_SQL = """
            select distinct p.id, p.title, p.text, p.image_path, p.likes_count from posts p
              join post_tags pt on p.id = pt.post_id
               join tags t on pt.tag_id = t.id
                where t.name = :tagName
                order by p.id desc limit :limit offset :offset
            """;

    public static final String SELECT_POST_BY_ID_SQL = """
            select * from posts
              where id = ?
            """;

    public static final String UPDATE_POST_SQL = """
            update posts
              set title = :title, text = :text, image_path = :imagePath
                where id = :id
            """;

    public static final String DELETE_POST_SQL = """
            delete
              from posts
                where id = ?
            """;

    public static final String UPDATE_LIKES_SQL = """
            update posts
              set likes_count = likes_count + ?
                where id = ?
            """;

    /*
     *  Comments
     * */
    public static final String DELETE_ALL_COMMENTS_SQL = "delete from comments";
    public static final String UPDATE_COMMENT_SQL = "update comments set text = ? where id = ? and post_id = ?";
    public static final String DELETE_COMMENT_SQL = "delete from comments where id = ?";
    public static final String SELECT_POST_COMMENTS_SQL = "select id, post_id, text from comments where post_id = ?";

    /*
     *  Tags
     * */
    public static final String INSERT_POST_TO_TAG_SQL = "insert into post_tags (post_id, tag_id) values (?, ?)";

    public static final String SELECT_TAGS_SQL = "select id, name from tags";

    public static final String SELECT_TAG_BY_NAME_SQL = "select id from tags where name = ?";

    public static final String DELETE_ALL_TAGS_SQL = "delete from tags";

    public static final String DELETE_ALL_POST_TO_TAGS_SQL = "delete from post_tags";

    public static final String DELETE_POST_TO_TAGS_SQL = "delete from post_tags where post_id = ?";


    /*
     *  Mappers
     * */
    public static final RowMapper<TagEntity> tagRowMapper =
            (rs, rowNum) -> new TagEntity(
                    rs.getLong("id"),
                    rs.getString("name")
            );

    public static final RowMapper<PostEntity> postRowMapper =
            (rs, rowNum) -> PostEntity.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .text(rs.getString("text"))
                    .imagePath(rs.getString("image_path"))
                    .likesCount(rs.getInt("likes_count"))
                    .build();

    public static final RowMapper<CommentEntity> commentRowMapper =
            (rs, rowNum) -> CommentEntity.builder()
                    .id(rs.getLong("id"))
                    .postId(rs.getLong("post_id"))
                    .text(rs.getString("text"))
                    .build();

}
