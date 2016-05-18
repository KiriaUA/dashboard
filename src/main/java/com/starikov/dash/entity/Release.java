package com.starikov.dash.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    @JsonProperty("number")
    private String releaseNumber;

    @JsonProperty("date")
    @JsonFormat(pattern = "MM/dd/yyyy")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate releaseDate;

    @JsonProperty("color")
    private String releaseColor;

    public Release() {
    }

    public String getReleaseColor() {
        return releaseColor;
    }

    public void setReleaseColor(String releaseColor) {
        this.releaseColor = releaseColor;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getReleaseNumber() {
        return releaseNumber;
    }

    public void setReleaseNumber(String releaseNumber) {
        this.releaseNumber = releaseNumber;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDaysTillRelease() {
        Period periodTillRelease = Period.between(LocalDate.now(), releaseDate);
        StringBuilder tillRelease = new StringBuilder();
        if (periodTillRelease.getMonths() > 0) {
            tillRelease.append(periodTillRelease.getMonths()).append(" month ");
        }
        tillRelease.append(periodTillRelease.getDays()).append(" days");
        return tillRelease.toString();
    }

}
