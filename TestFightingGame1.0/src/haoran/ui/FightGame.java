package haoran.ui;

import haoran.domain.*;
import haoran.domain.Character;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightGame {
    public void gameStart(User user) {
        System.out.println("欢迎" + user.getUsername() + "进入游戏");
        System.out.println("您的id为" + user.getId());

        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill("普通攻击", 0, 0, 1.0));
        skills.add(new Skill("强力一击", 10, 0, 1.8));
        skills.add(new Skill("生命汲取", 10, 50, 1.0));
        skills.add(new Skill("防御", 0,  0, 0));

        HeroCharacter player = createPlayerCharacter(user.getUsername(),skills);
        player.show();

        ArrayList<EnemyCharacter> enemyCharacters = createEnemyCharacter();
        int count = 1;//当前第几场战斗
        int wins = 0;//当前胜场

        while (player.isSurvive()) {
            EnemyCharacter enemy;

            if (count % 4 == 1 && count != 1) {
                for (int i = 0; i < enemyCharacters.size(); i++) {
                    int addHP = 50;
                    int addAttack = 10;
                    int addDefence = 10;
                    if (i == 3) {
                        addHP = 100;
                        addAttack = 50;
                        addDefence = 50;
                    }

                    EnemyCharacter c = enemyCharacters.get(i);
                    c.setMaxHP(c.getMaxHP() + addHP);
                    c.setHP(c.getMaxHP());
                    c.setMP(c.getMaxMP());
                    c.setAttack(c.getAttack() + addAttack);
                    c.setDefence(c.getDefence() + addDefence);
                    c.setDefending(false);
                }

            }//每一轮结束强化enemy

            if(wins % 4 == 3){
                System.out.println();
                System.out.println("升级！获得 50 点生命值, 20 点攻击力和防御力, 20 点蓝量");
                player.setMaxHP(player.getMaxHP() + 50);
                player.setMaxMP(player.getMaxMP() + 20);
                player.setAttack(player.getAttack() + 20);
                player.setDefence(player.getDefence() + 20);
                player.heal(50);
                player.healMP(20);
                player.show();
            }//升级

            if(wins % 4 == 0 && wins != 0){
                System.out.println("升级！获得 50 点最大生命值, 20 点最大蓝量, 20 点攻击力和防御力");
                player.setMaxHP(player.getMaxHP() + 50);
                player.setMaxMP(player.getMaxMP() + 20);
                player.setAttack(player.getAttack() + 20);
                player.setDefence(player.getDefence() + 20);
                System.out.println("检测到你击败了你马哥,肯定是运气,但还是帮你恢复状态吧");
                player.setHP(player.getMaxHP());
                player.setMP(player.getMaxMP());
                player.show();
            }

            //选择敌人
            if (count % 4 == 0) {
                enemy = enemyCharacters.get(3);
            } else {
                enemy = enemyCharacters.get(count % 4 - 1);
            }

            System.out.println();
            System.out.println("第" + count + "场战斗开始,敌人：「 " + enemy.getName() + " 」");
            enemy.show();
            System.out.println("--------------------------------------");

            int round = 1;
            while (true) {
                System.out.println();
                System.out.println("第" + round + "回合开始");
                showBar(player);
                showBar(enemy);

                playerRound(player, enemy);
                if(!enemy.isSurvive()){
                    System.out.println("你战胜了 "+enemy.getName()+" !");
                    count++;
                    wins++;
                    System.out.println("恢复 50 生命值和 20 蓝量");
                    player.heal(50);
                    player.healMP(20);
                    showBar(player);
                    System.out.println("当前胜场为： " + wins);
                    break;
                }
                enemyTurn(enemy, player);
                if(!player.isSurvive()){
                    System.out.println("你的决心碎了一地....");
                    System.out.println("浩然真是杂鱼啊~");
                    break;
                }
                round++;
            }
        }
    }


    public HeroCharacter createPlayerCharacter(String username,ArrayList<Skill> skills) {
        int points = 20;
        HeroCharacter player = new HeroCharacter();

        System.out.println("正在创建您的角色中");
        System.out.println("您的初始数值为：");
        System.out.println("生命值 100/100 (每点天赋 + 20 HP）");
        System.out.println("法力值 100/100 ");
        System.out.println("攻击力 10      (每点天赋 + 10 ATK）");
        System.out.println("防御力 10      (每点天赋 + 5 DEF）");
        System.out.println("(不推荐攻击力5点以下)");
        System.out.println("--------------------------------------");
        String[] attributes = {"生命值", "攻击力", "防御力"};
        int[] values = new int[attributes.length];

        for (int i = 0; i < attributes.length; i++) {
            System.out.print("分配点数到 " + attributes[i] + " (剩余天赋点：" + points + ")" + ": ");
            Scanner sc = new Scanner(System.in);
            values[i] = sc.nextInt();
            if (values[i] > points) {
                System.out.println("天赋点数不足,自动将剩下全部点数分配给该项");
                values[i] = points;
            }
            if (values[i] < 0) {
                System.out.println("输入点数错误！自动分配0点");
                values[i] = 0;
            }
            points -= values[i];
        }
        if (points > 0) {
            System.out.println("剩余的天赋点数已自动分配给生命值");
            values[0] += points;
        }

        player.setName(username);
        player.setHP(100 + values[0] * 20);
        player.setMaxHP(100 + values[0] * 20);
        player.setMP(100);
        player.setMaxMP(100);
        player.setAttack(10 + values[1] * 10);
        player.setDefence(10 + values[2] * 5);
        for (int i = 0; i < skills.size(); i++) {
            player.skills[i] = skills.get(i);
        }


        System.out.println("创建成功！");
        System.out.println();
        return player;
    }

    public ArrayList<EnemyCharacter> createEnemyCharacter() {
        ArrayList<EnemyCharacter> enemyCharacters = new ArrayList<>();
        enemyCharacters.add(new EnemyCharacter("三金", 200, 100, 50, 50, "猛击"));
        enemyCharacters.add(new EnemyCharacter("本博", 150, 100, 70, 30, "快速攻击"));
        enemyCharacters.add(new EnemyCharacter("昊泽", 250, 100, 40, 60, "防御姿态"));
        enemyCharacters.add(new EnemyCharacter("你马哥", 300, 100, 70, 70, "天动万象"));
        return enemyCharacters;
    }

    public void showBar(Character c){
        int count = 20;
        System.out.print(c.getName() + ": [");
        int barCount = (int)( (c.getHP() * 1.0 / c.getMaxHP()) * count );
        for (int i = 0; i < count; i++) {
            if (i < barCount) {
                System.out.print("█");
            } else {
                System.out.print(" ");
            }
        }
        System.out.print("] " + c.getHP() + "/" + c.getMaxHP() + " HP");
        System.out.println("\t"+c.getMP() + "/" + c.getMaxMP() + " MP");
    }

    public int calculateDamage(int attack, int defence){
        int damage = attack - defence;
        if (damage < 1) {
            damage = 1;
        }
        return damage;
    }

    public void playerRound(HeroCharacter player, EnemyCharacter enemy){
        System.out.println("---你的回合---");
        System.out.println("请选择行动：");
        for (int i = 0; i < player.skills.length; i++) {
            if(player.skills[i] == null){
                break;
            }
            System.out.println(i + 1 + ". " + player.skills[i].getName());
        }
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice){
            case 1 -> {
                int damage1 = calculateDamage(player.getAttack(), enemy.getDefence());
                System.out.println(player.getName()+" 对 " + enemy.getName() + " 使用了 普通攻击 ,造成 " + damage1 + " 点伤害");
                enemy.takeDamage(damage1);
            }
            case 2 -> {
                if(player.getMP() >= 10){
                    player.setMP(player.getMP() - 10);
                    int damage2 = calculateDamage((int) (player.getAttack() * 1.8), enemy.getDefence());
                    System.out.println(player.getName()+" 对 " + enemy.getName() + " 使用了 强力一击 ,造成 " + damage2 + " 点伤害");
                    enemy.takeDamage(damage2);
                }else {
                    System.out.println("当前法力值不足,自动释放普通攻击");
                    int damage1 = calculateDamage(player.getAttack(), enemy.getDefence());
                    System.out.println(player.getName()+" 对 " + enemy.getName() + " 使用了 普通攻击 ,造成 " + damage1 + " 点伤害");
                    enemy.takeDamage(damage1);
                }
            }
            case 3 -> {
                if(player.getMP() >= 10){
                    player.setMP(player.getMP() - 10);
                    int damage3 = calculateDamage(player.getAttack(), enemy.getDefence());
                    System.out.println(player.getName()+" 对 " + enemy.getName() + " 使用了 生命汲取 ,造成了 "+damage3+" 点伤害,恢复了 " + 50 + " 点生命值");
                    enemy.takeDamage(damage3);
                    player.heal(50);
                }else {
                    System.out.println("当前法力值不足,自动释放普通攻击");
                    int damage1 = calculateDamage(player.getAttack(), enemy.getDefence());
                    System.out.println(player.getName()+" 对 " + enemy.getName() + " 使用了 普通攻击 ,造成 " + damage1 + " 点伤害");
                    enemy.takeDamage(damage1);
                }
            }
            case 4 -> {
                player.setDefending(true);
                System.out.println(player.getName()+" 使用了 防御 ,下次受到的伤害减半");
            }
            default -> {
                System.out.println("无效选择,自动释放普通攻击");
                int damage1 = calculateDamage(player.getAttack(), enemy.getDefence());
                System.out.println(player.getName()+" 对 " + enemy.getName() + " 使用了 普通攻击 ,造成 " + damage1 + " 点伤害");
                enemy.takeDamage(damage1);
            }
        }
    }

    private void enemyTurn(EnemyCharacter enemy, HeroCharacter player) {
        System.out.println("---敌人的回合---");
        String action = "普通攻击";

        Random r = new Random();
        int num = r.nextInt(10);
        if(num > 5){
            action = enemy.getSkill();
        }

        switch (action) {
            case "普通攻击"->{
                int damage1 = calculateDamage(enemy.getAttack(), player.getDefence());
                System.out.println(enemy.getName()+" 对 " + player.getName() + " 使用了 普通攻击 ,造成 " + damage1 + " 点伤害");
                player.takeDamage(damage1);
            }
            case "猛击"->{
                if(enemy.getMP() >= 20){
                    enemy.setMP(enemy.getMP() - 20);
                    int damage2 = calculateDamage((int) (enemy.getAttack() * 1.5), player.getDefence());
                    System.out.println(enemy.getName()+" 对 " + player.getName() + " 使用了 猛击 ,造成 " + damage2 + " 点伤害");
                    player.takeDamage(damage2);
                }else {
                    int damage1 = calculateDamage(enemy.getAttack(), player.getDefence());
                    System.out.println(enemy.getName()+" 对 " + player.getName() + " 使用了 普通攻击 ,造成 " + damage1 + " 点伤害");
                    player.takeDamage(damage1);
                }
            }
            case "快速攻击"->{
                if (enemy.getMP() >= 20) {
                    enemy.setMP(enemy.getMP() - 20);
                    int damage3 = calculateDamage((int) (enemy.getAttack() * 1.6), player.getDefence());
                    System.out.println(enemy.getName()+" 对 " + player.getName() + " 使用了 快速攻击 ,造成 2 次" + damage3 / 2 + " 点伤害");
                    player.takeDamage(damage3);
                }else {
                    int damage1 = calculateDamage(enemy.getAttack(), player.getDefence());
                    System.out.println(enemy.getName()+" 对 " + player.getName() + " 使用了 普通攻击 ,造成 " + damage1 + " 点伤害");
                    player.takeDamage(damage1);
                }
            }
            case "防御姿态"->{
                if (enemy.getMP() >= 20) {
                    enemy.setMP(enemy.getMP() - 20);
                    System.out.println(enemy.getName()+" 使用了 防御姿态,下次受到的伤害减半");
                    enemy.setDefending(true);
                }else {
                    int damage1 = calculateDamage(enemy.getAttack(), player.getDefence());
                    System.out.println(enemy.getName()+" 对 " + player.getName() + " 使用了 普通攻击 ,造成 " + damage1 + " 点伤害");
                    player.takeDamage(damage1);
                }
            }
            case "天动万象"->{
                if (enemy.getMP() >= 50) {
                    enemy.setMP(enemy.getMP() - 50);
                    int damage = calculateDamage((int) (enemy.getAttack() * 1.7), player.getDefence());
                    System.out.println(enemy.getName()+" 对 " + player.getName() + " 使用了 天动万象 ,造成 " + damage + " 点伤害,恢复了 "+ 50 +"点生命值");
                    player.takeDamage(damage);
                    enemy.heal(50);
                }else {
                    int damage1 = calculateDamage(enemy.getAttack(), player.getDefence());
                    System.out.println(enemy.getName()+" 对 " + player.getName() + " 使用了 普通攻击 ,造成 " + damage1 + " 点伤害");
                    player.takeDamage(damage1);
                }
            }
        }
    }
}
