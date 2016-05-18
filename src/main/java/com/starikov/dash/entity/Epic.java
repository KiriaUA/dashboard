package com.starikov.dash.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Epic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3, max = 10)
    @NotNull
    @JsonProperty("game_code")
    private String gameCode;

    /**
     * Game title
     */
    @Size(min = 6, max = 100)
    @NotNull
    @JsonProperty("title")
    private String title;

    /**
     * Game Producer
     */
    @JsonProperty("GP")
    private String gp;

    /**
     * Game developer
     */
    @JsonProperty("developer")
    private String developer;

    @JsonIgnore
    @OneToOne
    private Release release;

    @JsonProperty("releaseName")
    private String releaseName;

    public Epic() {
    }

    public String getReleaseName() {

        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

//    public int getDaysToRelease() {
//        LocalDate today = LocalDate.now();
//        return (int) ChronoUnit.DAYS.between(today, releaseDate);
//    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getGp() {
        return gp;
    }

    public void setGp(String gp) {
        this.gp = gp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
