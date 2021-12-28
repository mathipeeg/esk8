package dk.mathi.esk8.domainmodel;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "ROUTE_NOTIFICATION")
public class RouteNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ROUTE_NOTIFICATIONS.ROUTE_NOTIFICATION_ID")
    @TableGenerator(name = "ROUTE_NOTIFICATIONS.ROUTE_NOTIFICATION_ID", table = "DATABASE_SEQUENCE", pkColumnName = "SEQUENCE_NAME", pkColumnValue = "ROUTE_NOTIFICATION_ID", valueColumnName = "NEXT_AVAILABLE_ID", allocationSize = 1)
    @Column(name = "route_notification_id")
    private long id;

    @Column(name = "is_on")
    private boolean isOn;

    @Column(name = "interval")
    private int interval;

    @Column(name = "avg_speed")
    private boolean avgSpeed;

    @Column(name = "distance")
    private boolean distance;

    @Column(name = "time")
    private boolean time;

    @Column(name = "voice_actor")
    @Enumerated(EnumType.STRING)
    private VoiceActors voiceActor;

    public RouteNotification() {
    }

    public RouteNotification(long id, boolean isOn, int interval, boolean avgSpeed, boolean distance, boolean time, VoiceActors voiceActor) {
        this.id = id;
        this.isOn = isOn;
        this.interval = interval;
        this.avgSpeed = avgSpeed;
        this.distance = distance;
        this.time = time;
        this.voiceActor = voiceActor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public boolean isAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(boolean avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public boolean isDistance() {
        return distance;
    }

    public void setDistance(boolean distance) {
        this.distance = distance;
    }

    public boolean isTime() {
        return time;
    }

    public void setTime(boolean time) {
        this.time = time;
    }

    public VoiceActors getVoiceActor() {
        return voiceActor;
    }

    public void setVoiceActor(VoiceActors voiceActor) {
        this.voiceActor = voiceActor;
    }

    @Override
    public String toString() {
        return "RouteNotification{" +
                "id=" + id +
                ", isOn=" + isOn +
                ", interval=" + interval +
                ", avgSpeed=" + avgSpeed +
                ", distance=" + distance +
                ", time=" + time +
                ", voiceActor=" + voiceActor +
                '}';
    }
}
