package dk.mathi.esk8.domainmodel;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "BOARDS")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARDS.BOARD_ID")
    @TableGenerator(name = "BOARDS.BOARD_ID", table = "DATABASE_SEQUENCE", pkColumnName = "SEQUENCE_NAME", pkColumnValue = "BOARD_ID", valueColumnName = "NEXT_AVAILABLE_ID", allocationSize = 1)
    @Column(name = "board_id")
    private long id;

    @Column(name = "board_type")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Column(name = "board_brand")
    @Enumerated(EnumType.STRING)
    private BoardBrand boardBrand;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "weight")
    private int weight;

    @Column(name = "length")
    private int length;

    @Column(name = "motor_type")
    private String motorType;

    @Column(name = "battery")
    private String Battery;

    @Column(name = "note")
    private String note;

    @Column(name = "picture")
    private Byte[] picture;

    public Board() {
    }

    public Board(long id, BoardType boardType, BoardBrand boardBrand, String nickname, int weight, int length, String motorType, String battery, String note, Byte[] picture) {
        this.id = id;
        this.boardType = boardType;
        this.boardBrand = boardBrand;
        this.nickname = nickname;
        this.weight = weight;
        this.length = length;
        this.motorType = motorType;
        Battery = battery;
        this.note = note;
        this.picture = picture;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }

    public BoardBrand getBoardBrand() {
        return boardBrand;
    }

    public void setBoardBrand(BoardBrand boardBrand) {
        this.boardBrand = boardBrand;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getMotorType() {
        return motorType;
    }

    public void setMotorType(String motorType) {
        this.motorType = motorType;
    }

    public String getBattery() {
        return Battery;
    }

    public void setBattery(String battery) {
        Battery = battery;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", boardType=" + boardType +
                ", boardBrand=" + boardBrand +
                ", nickname='" + nickname + '\'' +
                ", weight=" + weight +
                ", length=" + length +
                ", motorType='" + motorType + '\'' +
                ", Battery='" + Battery + '\'' +
                ", note='" + note + '\'' +
                ", picture=" + Arrays.toString(picture) +
                '}';
    }
}
