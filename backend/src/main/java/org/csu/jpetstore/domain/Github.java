package org.csu.jpetstore.domain;

public class Github {

    private int id;
    private String userId;
    private String githubId;
    private String githubName;
    private String avatar;
    private String email;
    private String status;

    public Github() {

    }

    public Github(String userId, String githubId, String githubName, String avatar, String email, String status) {
        this.userId = userId;
        this.githubId = githubId;
        this.githubName = githubName;
        this.avatar = avatar;
        this.email = email;
        this.status = status;
    }

    public Github(int id, String userId, String githubId, String githubName, String avatar, String email, String status) {
        this.id = id;
        this.userId = userId;
        this.githubId = githubId;
        this.githubName = githubName;
        this.avatar = avatar;
        this.email = email;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getGithubName() {
        return githubName;
    }

    public void setGithubName(String githubName) {
        this.githubName = githubName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Github{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", githubId='" + githubId + '\'' +
                ", githubName='" + githubName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
