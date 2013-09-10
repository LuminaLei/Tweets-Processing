package models;

public class User {
    public String name;
    public String id_str;
    public int followers_count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public void setUser(User user) {
        this.setName(user.getName());
        this.setId_str(user.getId_str());
        this.setFollowers_count(user.getFollowers_count());

    }
}
