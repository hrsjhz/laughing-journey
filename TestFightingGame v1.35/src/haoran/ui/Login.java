package haoran.ui;

import haoran.domain.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {
    public void Start() throws InterruptedException {
        ArrayList<User> users = new ArrayList<>();
        System.out.println("欢迎来到文字格斗游戏");

        while (true) {

            System.out.println("请选择操作：1.登录 2.注册 3.退出");

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> login(users);
                case 2 -> register(users);
                case 3 -> {
                    System.out.println("退出游戏");
                    System.exit(0);
                }
                default -> System.out.println("输入错误,请重新输入");
            }
        }
    }

    public void login(ArrayList<User> users) throws InterruptedException {
//        1.键盘录入用户名
//        2.判断是否注册
//            未注册：  返回注册
//            已注册：   被封：联系haoran管理员
//        3.输入密码
//        4.输入验证码
        System.out.println("请输入用户名：");
        Scanner sc = new Scanner(System.in);
        String username = sc.next();

        if (!contains(users, username)) {
            System.out.println("用户名不存在,请返回注册");
            return;
        }

        int index = getIndex(users, username);//该用户的索引
        User user = users.get(index);//该用户

        if (user.getStateNum() == 3) {
            System.out.println("用户" + username + "被封 ，请联系管理员haoran");
            return;
        }


        for (int i = 1; i <= 3; i++) {
            while (true) {
                String answer = getCode();
                System.out.println(answer);
                System.out.println("请输入验证码:");
                String code = sc.next();
                if (!code.equalsIgnoreCase(answer)) {
                    System.out.println("验证码错误,请重新输入");
                    continue;
                }
                break;
            }//验证码

            System.out.println("请输入密码：");
            String password = sc.next();

            if (!user.getPassword().equals(password)) {
                user.setStateNum(user.getStateNum() + 1);
                if(user.getStateNum()==3){
                    System.out.println("用户" + username + "被封 ，请联系管理员haoran");
                    return;
                }
                System.out.println("密码错误,还剩下" + (3 - i) + "次机会");
            }else{
                break;
            }
        }
        System.out.println("登录成功!");
        FightGame fightGame = new FightGame();
        fightGame.gameStart(user);
    }//登录

    public void register(ArrayList<User> users) {
        /* 1.创建User对象（）

           2.键盘录入账号密码
                判断账号密码是否符合要求
           3.添加到users集合中

         */
        User u = new User();

        Scanner sc = new Scanner(System.in);

        //校验用户名
        while (true) {
            System.out.println("请输入用户名:");
            String username = sc.next();

            if (!checkLength(3, 10, username)) {
                System.out.println("用户名的长度必须在3~16位之间");
                continue;
            }

            if (!checkUsername(username)) {
                System.out.println("用户名只能由字母，数字构成，不能为纯数字");
                continue;
            }

            if (contains(users, username)) {
                System.out.println("用户名已存在");
                continue;
            }

            u.setUsername(username);
            break;
        }


        //校验密码
        while (true) {
            System.out.println("请输入密码:");
            String password1 = sc.next();
            if (!checkLength(3, 8, password1)) {
                System.out.println("密码的长度必须在3~8位之间");
                continue;
            }
            if (!checkPassword(password1)) {
                System.out.println("密码只能是字母加数字的组合，不能有其他字符");
                continue;
            }

            System.out.println("请再次输入密码:");
            String password2 = sc.next();

            if (!password1.equals(password2)) {
                System.out.println("两次输入的密码不一致");
                continue;
            }

            u.setPassword(password1);
            break;
        }

        users.add(u);
        System.out.println("用户" + u.getUsername() + "注册成功");
    }//注册

    public int getIndex(ArrayList<User> users, String username) {
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }//获取User的索引

    public boolean contains(ArrayList<User> users, String username) {
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }//检验集合内已存在该用户名

    public boolean checkLength(int min, int max, String str) {
//        if (str.length() >= min && str.length() <= max) {
//            return true;
//        } else {
//            return false;
//        }
        return str.length() >= min && str.length() <= max;
    }//检验字符串是否符合长度要求

    public int[] checkCount(String userInfo) {
        int charCount = 0;
        int numCount = 0;
        int otherCount = 0;

        for (int i = 0; i < userInfo.length(); i++) {
            char c = userInfo.charAt(i);

            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                charCount++;
            } else if (c >= '0' && c <= '9') {
                numCount++;
            } else {
                otherCount++;
            }
        }
        return new int[]{charCount, numCount, otherCount};
    }//检测字符串内字母、数字、其他字符的个数

    public boolean checkUsername(String username) {
        int[] arr = checkCount(username);
        return arr[0] > 0 && arr[1] >= 0 && arr[2] == 0;
    }//核验用户名

    public boolean checkPassword(String password) {
        int[] arr = checkCount(password);
        return arr[0] > 0 && arr[1] > 0 && arr[2] == 0;
    }//核验密码

    public String getCode() {
        ArrayList<Character> arr = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();

        for (int i = 0; i < 26; i++) {
            arr.add((char) ('a' + i));
            arr.add((char) ('A' + i));
        }

        for (int i = 0; i < 4; i++) {
            sb.append(arr.get(r.nextInt(arr.size())));
        }

        int num = r.nextInt(10);
        sb.insert(r.nextInt(sb.length() + 1), num);

        return sb.toString();
    }//获取验证码，4位字母 + 1位数字
}
