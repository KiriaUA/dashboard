package com.starikov.dash.entity;

public class JiraEpic {

    private String id;
    private String summary;
    private String priorityIconUrl;

    public JiraEpic(String id, String summary, String priorityIconUrl) {
        this.id = id;
        this.summary = summary;
        this.priorityIconUrl = priorityIconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
