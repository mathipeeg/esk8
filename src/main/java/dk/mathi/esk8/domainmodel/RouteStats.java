package dk.mathi.esk8.domainmodel;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "ROUTE_STATS")
public class RouteStats {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ROUTE_STATS.ROUTE_STATS_ID")
    @TableGenerator(name = "ROUTE_STATS.ROUTE_STATS_ID", table = "DATABASE_SEQUENCE", pkColumnName = "SEQUENCE_NAME", pkColumnValue = "ROUTE_STATS_ID", valueColumnName = "NEXT_AVAILABLE_ID", allocationSize = 1)
    @Column(name = "route_stats_id")
    private long id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "route_id")
    private int routeId;

    @Column(name = "board_id")
    private int boardId;

    @Column(name = "distance")
    private int distance;

    @Column(name = "avg_speed")
    private int avgSpeed;

    @Column(name = "max_speed")
    private int maxSpeed;

    @Column(name = "power")
    private int power;

    public RouteStats() {
    }

    public RouteStats(long id, int userId, int routeId, int boardId, int distance, int avgSpeed, int maxSpeed, int power) {
        this.id = id;
        this.userId = userId;
        this.routeId = routeId;
        this.boardId = boardId;
        this.distance = distance;
        this.avgSpeed = avgSpeed;
        this.maxSpeed = maxSpeed;
        this.power = power;
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

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(int avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "RouteStats{" +
                "id=" + id +
                ", userId=" + userId +
                ", routeId=" + routeId +
                ", boardId=" + boardId +
                ", distance=" + distance +
                ", avgSpeed=" + avgSpeed +
                ", maxSpeed=" + maxSpeed +
                ", power=" + power +
                '}';
    }
}
