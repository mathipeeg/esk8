package dk.mathi.esk8.domainmodel;


import org.postgis.LineString;

import javax.persistence.*;

@Entity
@Table(name = "ROUTES")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ROUTES.ROUTE_ID")
    @TableGenerator(name = "ROUTES.ROUTE_ID", table = "DATABASE_SEQUENCE", pkColumnName = "SEQUENCE_NAME", pkColumnValue = "ROUTE_ID", valueColumnName = "NEXT_AVAILABLE_ID", allocationSize = 1)
    @Column(name = "route_id")
    private long id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "name")
    private String name;

    @Column(name = "road_type")
    @Enumerated(EnumType.STRING)
    private RoadType roadType;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "length")
    private int length;

    @Column(name = "rating")
    private int rating;

    @Column(name = "note")
    private String note;

    @Column(name = "geometry")
    private LineString geometry;

    public Route() {
    }

    public Route(long id, int userId, String name, RoadType roadType, Category category, int length, int rating, String note, LineString geometry) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.roadType = roadType;
        this.category = category;
        this.length = length;
        this.rating = rating;
        this.note = note;
        this.geometry = geometry;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoadType getRoadType() {
        return roadType;
    }

    public void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LineString getGeometry() {
        return geometry;
    }

    public void setGeometry(LineString geometry) {
        this.geometry = geometry;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", userId=" + userId +
                ", roadType=" + roadType +
                ", category=" + category +
                ", length=" + length +
                ", rating=" + rating +
                ", note='" + note + '\'' +
                ", geometry=" + geometry +
                '}';
    }
}
