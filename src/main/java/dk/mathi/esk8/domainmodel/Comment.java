package dk.mathi.esk8.domainmodel;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

@Entity
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "COMMENTS.COMMENT_ID")
    @TableGenerator(name = "COMMENTS.COMMENT_ID", table = "DATABASE_SEQUENCE", pkColumnName = "SEQUENCE_NAME", pkColumnValue = "COMMENT_ID", valueColumnName = "NEXT_AVAILABLE_ID", allocationSize = 1)
    @Column(name = "comment_id")
    private long id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "route_id")
    private int routeId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    public Comment() {
    }

    public Comment(long id, int userId, int routeId, String comment, Timestamp timestamp) {
        this.id = id;
        this.userId = userId;
        this.routeId = routeId;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", routeId=" + routeId +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
