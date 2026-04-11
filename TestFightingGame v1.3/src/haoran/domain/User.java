package haoran.domain;

import java.util.Random;

public class User {
    private String id;
    private String username;
    private String password;
    private int stateNum;

    public User() {
        id = createId();
    }

    public User(String username, String password) {
        id = createId();
        this.username = username;
        this.password = password;
    }

    //自动生成id
    public String createId() {
        StringBuilder sb = new StringBuilder("haoran");

        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            int ran = r.nextInt(10); // 0 ~ 9
            sb.append(ran);
        }

        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStateNum() {
        return stateNum;
    }

    public void setStateNum(int stateNum) {
        this.stateNum = stateNum;
    }
}
