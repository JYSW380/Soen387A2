package Helper;

import model.Post;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DBQuerryInterface {
    public Map<String, ArrayList<Post>> getAllPost(Set<String> userGroup);

    public InputStream getFileUpload(int id);

    /**
     * extract post form the result set
     *
     * @param rs
     * @return
     */
    default Post extractPostFromRS(ResultSet rs) {
        try {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setUser(rs.getString("user"));
            post.setMessage(rs.getString("message"));
            post.setTimeStamp(LocalTime.parse(String.valueOf(rs.getTime("timeStamp"))));
            post.setHashTag(rs.getString("hashTag"));
            post.setGroup(rs.getString("group_name"));
            if (rs.getTime("updateTime") == null) {
                post.setUpdateTime(null);
            } else {
                post.setUpdateTime(LocalTime.parse(String.valueOf(rs.getTime("updateTime"))));
            }

            return post;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean insertPost(Post post, InputStream inputStream);

    public List<Post> search(String search, Set<String> userGroup);

    public boolean deletePost(int id);

    public Post getPostById(int id);

    public boolean updatePost(int id, Post post, InputStream inputStream);
}
