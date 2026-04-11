package haoran.ui;

import haoran.domain.*;
import haoran.domain.Character;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightGame {
    public void gameStart(User user) {
        Random r = new Random();
        System.out.println("欢迎" + user.getUsername() + "进入游戏");
        System.out.println("您的id为" + user.getId());

        ArrayList<String> skills = new ArrayList<>();
        skills.add("强力一击");
        skills.add("生命汲取");

        HeroCharacter player = createPlayerCharacter(user.getUsername(),skills);
        player.show();

        ArrayList<EnemyCharacter> enemyCharacters = createEnemyCharacter();
        int count = 1;//当前第几场战斗
        int wins = 0;//当前胜场

        while (player.isSurvive()) {
            EnemyCharacter enemy;

            if (count % 4 == 1 && count != 1) {
                for (int i = 0; i < enemyCharacters.size(); i++) {
                    int addHP = 150;
                    int addAttack = 25;
                    int addDefence = 25;
                    if (i == 3) {
                        addHP = 200;
                        addAttack = 30;
                        addDefence = 30;
                    }

                    EnemyCharacter c = enemyCharacters.get(i);
                    c.setMaxHP(c.getMaxHP() + addHP);
                    c.setHP(c.getMaxHP());
                    c.setMP(c.getMaxMP());
                    c.setAttack(c.getAttack() + addAttack);
                    c.setDefence(c.getDefence() + addDefence);
                    c.setDefending(false);
                    if(c.getName().equals("你马哥")){
                        c.setAttribute("乐");
                    }
                }

            }//每一轮结束强化enemy

            if(wins % 4 == 3){
                if (player.getMaxMP() < 200) {
                    System.out.println();
                    System.out.println("升级！获得 50 点最大生命值, 10 点攻击力和防御力, 20 点最大蓝量");
                    player.setMaxHP(player.getMaxHP() + 50);
                    player.setMaxMP(player.getMaxMP() + 20);
                    player.setAttack(player.getAttack() + 10);
                    player.setDefence(player.getDefence() + 10);
                    player.heal(50);
                    player.healMP(20);
                    player.setAttribute("喜");
                }else{
                    System.out.println();
                    System.out.println("升级！获得 50 点最大生命值, 10 点攻击力和防御力，恢复20点蓝量");
                    player.setMaxHP(player.getMaxHP() + 50);
                    player.setAttack(player.getAttack() + 10);
                    player.setDefence(player.getDefence() + 10);
                    player.heal(50);
                    player.healMP(20);
                    player.setAttribute("喜");
                }
                player.show();
            }//升级

            if(wins % 4 == 0 && wins != 0){
                if (player.getMaxMP() < 200) {
                    System.out.println();
                    System.out.println("升级！获得 50 点最大生命值, 20 点攻击力和防御力, 20 点最大蓝量");
                    player.setMaxHP(player.getMaxHP() + 50);
                    player.setMaxMP(player.getMaxMP() + 20);
                    player.setAttack(player.getAttack() + 20);
                    player.setDefence(player.getDefence() + 20);
                    player.setAttribute("喜");
                }else{
                    System.out.println();
                    System.out.println("升级！获得 75 点最大生命值, 25 点攻击力和防御力");
                    player.setMaxHP(player.getMaxHP() + 75);
                    player.setAttack(player.getAttack() + 25);
                    player.setDefence(player.getDefence() + 25);
                    player.setAttribute("喜");
                }
                System.out.println("检测到你击败了你马哥,肯定是运气,但还是帮你恢复状态吧");
                player.setHP(player.getMaxHP());
                player.setMP(player.getMaxMP());
                showBar(player);
                player.show();
                System.out.println("当前胜场：" + wins);
            }//boss战后升级

            //选择敌人
            if (count % 4 == 0) {
                enemy = enemyCharacters.get(3);
            } else {
                enemy = enemyCharacters.get(count % 4 - 1);
            }

            player.setMPHealing(false);
            player.setDefending(false);
            player.setPlayerHealMP(0);
            player.setAttribute("喜");
            System.out.println();
            System.out.println("第" + count + "场战斗开始,敌人：「 " + enemy.getName() + " 」");
            enemy.show();
            System.out.println("--------------------------------------");

            int round = 1;
            while (true) {
                player.setDefending(false);
                if(round - enemy.getLastDefendingRound() == 2){
                    enemy.setDefending(false);
                }

                System.out.println();
                String plannedAction = enemyAction(enemy,player,round);

                playerRound(player,enemy,round);
                if(!enemy.isSurvive()){
                    System.out.println("你战胜了 "+enemy.getName()+" !");
                    count++;
                    wins++;
                    if(wins % 4 == 0 && wins != 0){
                        break;
                    }
                    System.out .println("当前胜场为： " + wins);
                    break;
                }
                if(!player.isSurvive()){
                    System.out.println("你的决心碎了一地....");
                    System.out.println("浩然真是杂鱼啊~");
                    break;
                }

                enemyTurn(enemy, player,round,plannedAction);
                if(!enemy.isSurvive()){
                    System.out.println("你战胜了 "+enemy.getName()+" !");
                    count++;
                    wins++;
                    if(wins % 4 == 0 && wins != 0){
                        break;
                    }
                    System.out .println("当前胜场为： " + wins);
                    break;
                }
                if(!player.isSurvive()){
                    System.out.println("你的决心碎了一地....");
                    System.out.println("浩然真是杂鱼啊~");
                    break;
                }
                round++;
            }
        }
    }


    public HeroCharacter createPlayerCharacter(String username,ArrayList<String> skills) {
        int points = 20;
        HeroCharacter player = new HeroCharacter();

        System.out.println("正在创建您的角色中");
        System.out.println("您的初始数值为：");
        System.out.println("生命值 100/100 (每点天赋 + 20 HP）");
        System.out.println("法力值 100/100 ");
        System.out.println("攻击力 10      (每点天赋 + 10 ATK）");
        System.out.println("防御力 10      (每点天赋 + 5 DEF）");
        System.out.println("初始属性: 喜");
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
        player.setAttribute("喜");
        player.cSkills.addAll(skills);


        System.out.println("创建成功！");
        System.out.println();
        return player;
    }

    public ArrayList<EnemyCharacter> createEnemyCharacter() {
        ArrayList<EnemyCharacter> enemyCharacters = new ArrayList<>();
        EnemyCharacter e1 = new EnemyCharacter("三金", 200, 100, 50, 50,"喜");
        EnemyCharacter e2 = new EnemyCharacter("本博", 150, 100, 70, 30, "怒");
        EnemyCharacter e4 = new EnemyCharacter("你马哥", 300, 100, 70, 70, "乐");
        EnemyCharacter e3 = new EnemyCharacter("昊泽", 250, 100, 40, 60, "哀");
        enemyCharacters.add(e1);
        enemyCharacters.add(e2);
        enemyCharacters.add(e3);
        enemyCharacters.add(e4);
        e1.cSkills.add("猛击");
        e2.cSkills.add("快速攻击");
        e3.cSkills.add("防御姿态");
        e4.cSkills.add("天动万象");
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
        System.out.print("] " + c.getHP() + "/" + c.getMaxHP() + " HP ");
        System.out.print(c.getMP() + "/" + c.getMaxMP() + " MP ");
        System.out.println("属性: " + c.getAttribute());
    }

    public int calculateDamage(int attack, int defence){
        int damage = attack - defence;
        if (damage < 1) {
            damage = 1;
        }
        return damage;
    }

    public void playerRound(HeroCharacter player, EnemyCharacter enemy, int round) {
        Scanner sc = new Scanner(System.in);

        boolean flag = true; // false为准备好
        while (flag) {
            System.out.println("第" + round + "回合");
            showBar(player);
            showBar(enemy);
            System.out.println();
            System.out.println("---你的回合---");
            System.out.println("请选择行动:");
            System.out.println("1. 普通攻击");
            System.out.println("2. 技能");
            System.out.println("3. 属性");
            System.out.println("4. 防御");
            System.out.println("5. 道具");

            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    // 普通攻击
                    int damage1 = calculateDamage((int) (player.getAttack() * 1.5), enemy.getDefence());
                    System.out.println(player.getName() + " 对 " + enemy.getName() + " 使用了 普通攻击 ,造成 " + enemy.takeDamage(damage1, player.getAttribute()) + " 点伤害");
                    flag = false;
                }
                case 2 -> {
                    // 技能菜单
                    boolean skillFlag = true;
                    int skillChoice = 0;
                    while (skillFlag) {
                        System.out.println("---技能---");
                        System.out.println("1. 强力一击 (20MP 造成大量伤害)");
                        System.out.println("2. 生命汲取 (20MP 恢复血量)");
                        System.out.println("0. 返回");
                        skillChoice = sc.nextInt();
                        
                        switch (skillChoice) {
                            case 1 -> {
                                if (player.getMP() >= 20) {
                                    player.setMP(player.getMP() - 20);
                                    int damage2 = calculateDamage((int) (player.getAttack() * 1.8), enemy.getDefence());
                                    System.out.println(player.getName() + " 对 " + enemy.getName() + " 使用了 强力一击 ,造成 " + enemy.takeDamage(damage2, player.getAttribute()) + " 点伤害");
                                } else {
                                    System.out.println("当前法力值不足!");
                                    System.out.println();
                                    continue;
                                }
                                skillFlag = false;
                            }
                            case 2 -> {
                                if (player.getMP() >= 20) {
                                    player.setMP(player.getMP() - 20);
                                    int damage3 = calculateDamage((int) (player.getAttack() * 1.6), enemy.getDefence());
                                    int heal = (int) (player.getMaxHP() * 0.3);
                                    System.out.println(player.getName() + " 对 " + enemy.getName() + " 使用了 生命汲取 ,造成了 " + enemy.takeDamage(damage3, player.getAttribute()) + " 点伤害,恢复了 " + heal + " 点生命值");
                                    player.heal(heal);
                                } else {
                                    System.out.println("当前法力值不足!");
                                    System.out.println();
                                    continue;
                                }
                                skillFlag = false;
                            }
                            case 0 -> {
                                skillFlag = false;
                            }
                            default -> {
                                System.out.println("无效选择!");
                                System.out.println();
                            }
                        }
                    }
                    if(skillChoice != 0){
                        flag = false;
                    }
                }
                case 3 -> {
                    // 属性菜单
                    boolean attrFlag = true;
                    int attrChoice = 0;
                    while (attrFlag) {
                        System.out.println("---属性---");
                        System.out.println("1. 变身 (改变自身属性)");
                        System.out.println("2. 蓝量回复");
                        System.out.println("0. 返回");
                        attrChoice = sc.nextInt();

                        switch (attrChoice) {
                            case 1 -> {
                                System.out.println("请选择变身属性:");
                                System.out.println("(喜 -> 怒 -> 哀 -> 乐 -> 喜)");
                                System.out.println("1. 喜");
                                System.out.println("2. 怒");
                                System.out.println("3. 哀");
                                System.out.println("4. 乐");
                                System.out.println("0. 返回");
                                int atrChoice = sc.nextInt();

                                if (atrChoice == 0) {
                                    continue;
                                }

                                String[] attrs = {"", "喜", "怒", "哀", "乐"};
                                if (atrChoice >= 1 && atrChoice <= 4) {
                                    String targetAttr = attrs[atrChoice];
                                    if (player.getAttribute().equals(targetAttr)) {
                                        System.out.println("无法切换相同属性!");
                                        System.out.println();
                                        continue;
                                    }
                                    player.setAttribute(targetAttr);
                                    System.out.println("当前属性已切换为: " + player.getAttribute());
                                    attrFlag = false;
                                } else {
                                    System.out.println("无效选择!");
                                    System.out.println();
                                }
                            }
                            case 2 -> {
                                if (player.getPlayerHealMP() == 3) {
                                    System.out.println("每场战斗最多使用三次！");
                                    System.out.println();
                                    continue;
                                } else if (player.getMP() == player.getMaxMP()) {
                                    System.out.println("当前蓝量已满!");
                                    System.out.println();
                                    continue;
                                } else if (!player.isMPHealing() || round - player.getLastMpHealRound() >= 3) {
                                    player.setMPHealing(true);
                                    player.setLastMpHealRound(round);
                                    int healMP = (int) (player.getMaxMP() * (0.5 - 0.1 * player.getPlayerHealMP()));
                                    System.out.println(player.getName() + " 使用了 蓝量回复 , 恢复了 " + healMP + " 点蓝量");
                                    player.healMP(healMP);
                                    player.setPlayerHealMP(player.getPlayerHealMP() + 1);
                                } else {
                                    System.out.println("还有 " + (3 - (round - player.getLastMpHealRound())) + " 回合才能使用该技能");
                                    System.out.println();
                                    continue;
                                }
                                attrFlag = false;
                            }
                            case 0 -> {
                                attrFlag = false;
                            }
                            default -> {
                                System.out.println("无效选择!");
                                System.out.println();
                            }
                        }
                    }
                    if (attrChoice != 0) {
                        flag = false;
                    }
                }
                case 4 -> {
                    if (round - player.getLastDefendingRound() >= 2 || round == 1) {
                        player.setDefending(true);
                        player.setLastDefendingRound(round);
                        System.out.println(player.getName() + " 使用了 防御 ,本回合的伤害减半,并反击敌人相同伤害");
                    }else {
                        System.out.println("不能连续使用防御！");
                        System.out.println();
                        continue;
                    }
                    flag = false;
                }
                case 5 -> {
                    // 道具菜单（暂未实现）
                    System.out.println("---道具---");
                    System.out.println("暂无可用道具");
                    System.out.println("0. 返回");
                    int itemChoice = sc.nextInt();
                    if (itemChoice == 0) {
                        continue;
                    }
                }
                default -> {
                    System.out.println("无效选择!");
                    System.out.println();
                }
            }
        }
    }


    public void enemyTurn(EnemyCharacter enemy, HeroCharacter player, int round,String plannedAction) {
        System.out.println("---敌人的回合---");

        // 直接执行预定的行动
        if(plannedAction.startsWith("变身:")){
            String newAttr = plannedAction.substring(3);
            enemy.setAttribute(newAttr);
            System.out.println(enemy.getName() + " 使用了变身！当前属性为: " + enemy.getAttribute());
            return;
        }

        switch (plannedAction) {
            case "普通攻击"->{
                int damage1 = calculateDamage((int) (enemy.getAttack()*1.3), player.getDefence());
                int hurt = player.takeDamage(damage1,enemy.getAttribute());
                System.out.println(enemy.getName()+" 对 " + player.getName() + " 使用了 普通攻击 ,造成 " + hurt + " 点伤害");
                if(round == player.getLastDefendingRound()){
                    enemy.takeDamage(hurt,enemy.getAttribute());
                    System.out.println("反击成功！对 " + enemy.getName() + " 造成 " + hurt + " 点伤害");
                }
            }
            case "猛击"->{
                enemy.setMP(enemy.getMP() - 20);
                int damage2 = calculateDamage((int) (enemy.getAttack() * 1.5), player.getDefence());
                int hurt = player.takeDamage(damage2,enemy.getAttribute());
                System.out.println(enemy.getName() + " 对 " + player.getName() + " 使用了 猛击 ,造成 " + hurt + " 点伤害");
                if(round == player.getLastDefendingRound()){
                    enemy.takeDamage(hurt,enemy.getAttribute());
                    System.out.println("反击成功！对 " + enemy.getName() + " 造成 " + hurt + " 点伤害");
                }
            }
            case "快速攻击"->{
                enemy.setMP(enemy.getMP() - 20);
                int damage3 = calculateDamage((int) (enemy.getAttack() * 1.6), player.getDefence());
                int hurt = player.takeDamage(damage3,enemy.getAttribute());
                System.out.println(enemy.getName()+" 对 " + player.getName() + " 使用了 快速攻击 ,造成 2 次 " + hurt / 2 + " 点伤害");
                if(round == player.getLastDefendingRound()){
                    enemy.takeDamage(hurt,enemy.getAttribute());
                    System.out.println("反击成功！对 " + enemy.getName() + " 造成 " + hurt + " 点伤害");
                }
            }
            case "防御姿态"->{
                enemy.setMP(enemy.getMP() - 20);
                enemy.setDefending(true);
                enemy.setLastDefendingRound(round);
                System.out.println(enemy.getName()+" 使用了 防御姿态 ,下回合受到的伤害减半");
            }
            case "天动万象"->{
                enemy.setMP(enemy.getMP() - 50);
                int damage = calculateDamage((int) (enemy.getAttack() * 2.0), player.getDefence());
                int heal = (int) (enemy.getMaxHP() * 0.3);
                int hurt = player.takeDamage(damage,enemy.getAttribute());
                System.out.println(enemy.getName()+" 对 " + player.getName() + " 使用了 天动万象 ,造成 " + hurt + " 点伤害,恢复了 "+ heal +"点生命值");
                enemy.heal(heal);
                if(round == player.getLastDefendingRound()){
                    enemy.takeDamage(hurt,enemy.getAttribute());
                    System.out.println("反击成功！对 " + enemy.getName() + " 造成 " + hurt + " 点伤害");
                }
            }
            case "蓝量回复"->{
                System.out.println(enemy.getName() + " 使用了 蓝量回复");
                if(enemy.getName().equals("你马哥")){
                    enemy.healMP(25);
                }else{
                    enemy.healMP(20);
                }
            }
        }
    }

    public String enemyAction(EnemyCharacter enemy, HeroCharacter player, int round) {
        String action = "普通攻击";
        Random r = new Random();

        int num;//敌人技能
        if(enemy.getName().equals("你马哥")){
            num = r.nextInt(5);
        }else{
            num = r.nextInt(9);
        }

        if(enemy.getName().equals("你马哥")){
            if(num >= 2 && num <= 3){
                action = enemy.cSkills.get(0);
            }

            if(num == 4){
                if(player.getAttribute().equals("喜") && enemy.getAttribute().equals("怒") ){
                    System.out.println(enemy.getName() + " 本回合将使用 变身:乐");
                    return "变身:乐";
                }else if(player.getAttribute().equals("怒")  && enemy.getAttribute().equals("哀")){
                    System.out.println(enemy.getName() + " 本回合将使用 变身:喜");
                    return "变身:喜";
                }else if(player.getAttribute().equals("哀")  && enemy.getAttribute().equals("乐")){
                    System.out.println(enemy.getName() + " 本回合将使用 变身:怒");
                    return "变身:怒";
                }else if(player.getAttribute().equals("乐")  && enemy.getAttribute().equals("喜")){
                    System.out.println(enemy.getName() + " 本回合将使用 变身:哀");
                    return "变身:哀";
                }

            } //变身逻辑
        }else{
            if(num >= 4 && num <= 7){
                action = enemy.cSkills.get(0);
            }

            if(num == 8){
                if(player.getAttribute().equals("喜") && enemy.getAttribute().equals("怒") ){
                    System.out.println(enemy.getName() + " 本回合将使用 变身:乐");
                    return "变身:乐";
                }else if(player.getAttribute().equals("怒")  && enemy.getAttribute().equals("哀")){
                    System.out.println(enemy.getName() + " 本回合将使用 变身:喜");
                    return "变身:喜";
                }else if(player.getAttribute().equals("哀")  && enemy.getAttribute().equals("乐")){
                    System.out.println(enemy.getName() + " 本回合将使用 变身:怒");
                    return "变身:怒";
                }else if(player.getAttribute().equals("乐")  && enemy.getAttribute().equals("喜")) {
                    System.out.println(enemy.getName() + " 本回合将使用 变身:哀");
                    return "变身:哀";
                }
            } //变身逻辑
        }

        switch (action) {
            case "普通攻击"->{
                System.out.println(enemy.getName()+" 本回合将使用 普通攻击");
                return "普通攻击";
            }
            case "猛击"->{
                if(enemy.getMP() >= 20){
                    System.out.println(enemy.getName()+" 本回合将使用 猛击");
                    return "猛击";
                }else {
                    System.out.println(enemy.getName()+" 本回合将使用 蓝量回复");
                    return "蓝量回复";
                }
            }
            case "快速攻击"->{
                if (enemy.getMP() >= 20) {
                    System.out.println(enemy.getName()+" 本回合将使用 快速攻击");
                    return "快速攻击";
                }else {
                    System.out.println(enemy.getName()+" 本回合将使用 蓝量回复");
                    return "蓝量回复";
                }
            }
            case "防御姿态"->{
                if(round - enemy.getLastDefendingRound() == 1){
                    System.out.println(enemy.getName()+" 本回合将使用 普通攻击");
                    return "普通攻击";
                }else if (enemy.getMP() >= 20) {
                    System.out.println(enemy.getName()+" 本回合将使用 防御姿态");
                    return "防御姿态";
                }else {
                    System.out.println(enemy.getName()+" 本回合将使用 蓝量回复");
                    return "蓝量回复";
                }
            }
            case "天动万象"->{
                if (enemy.getMP() >= 50) {
                    System.out.println(enemy.getName()+" 本回合将使用 天动万象");
                    return "天动万象";
                }else {
                    System.out.println(enemy.getName()+" 本回合将使用 蓝量回复");
                    return "蓝量回复";
                }
            }
        }
        return action;
    }

}
