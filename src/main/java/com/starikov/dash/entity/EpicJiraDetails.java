package com.starikov.dash.entity;

import javax.persistence.Embeddable;

@Embeddable
public class EpicJiraDetails {

    private String epicKey;
    private String summary;
    private String priorityIconUrl;
    private int openIssues;

    public EpicJiraDetails(String epicKey, String summary, String priorityIconUrl, int openIssues) {
        this.epicKey = epicKey;
        this.summary = summary;
        this.priorityIconUrl = priorityIconUrl;
        this.openIssues = openIssues;
    }

    public EpicJiraDetails() {
    }

    public String getEpicKey() {
        return epicKey;
    }
    public void setEpicKey(String epicKey) {
        this.epicKey = epicKey;
    }

    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPriorityIconUrl() {
        return priorityIconUrl;
    }
    public void setPriorityIconUrl(String priorityIconUrl) {
        this.priorityIconUrl = priorityIconUrl;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }
}
