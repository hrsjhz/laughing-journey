package haoran.ui;

import haoran.domain.*;
import haoran.domain.Character;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightGame {
    public void gameStart(User user) throws InterruptedException {
        Random r = new Random();
        System.out.println("欢迎" + user.getUsername() + "进入游戏");
        System.out.println("您的id为" + user.getId());

        ArrayList<Consumables> consumables = createConsumables();

        HeroCharacter player = createPlayerCharacter(user.getUsername(),consumables);
        player.show();
        Thread.sleep(1000);

        ArrayList<EnemyCharacter> enemyCharacters = createEnemyCharacter();
        int count = 1;//当前第几场战斗
        int wins = 0;//当前胜场

        while (player.isSurvive()) {
            EnemyCharacter enemy;

            if (count % 4 == 1 && count != 1) {
                for (int i = 0; i < enemyCharacters.size(); i++) {
                    int addHP = 150;
                    int addMP = 0;
                    int addAttack = 25;
                    int addDefence = 25;
                    if (i == 3) {
                        addHP = 200;
                        addAttack = 30;
                        addDefence = 30;
                    }

                    EnemyCharacter c = enemyCharacters.get(i);
                    c.setOldMaxHP(c.getOldMaxHP() + addHP);
                    c.setOldMaxMP(c.getOldMaxMP() + addMP);
                    c.setMaxMP(c.getOldMaxMP());
                    c.setMaxHP(c.getOldMaxHP());
                    c.setHP(c.getMaxHP());
                    c.setMP(c.getMaxMP());
                    c.setOldAttack(c.getOldAttack() + addAttack);
                    c.setAttack(c.getOldAttack());
                    c.setOldDefence(c.getOldDefence() + addDefence);
                    c.setDefence(c.getOldDefence());
                    c.setDefending(false);
                    c.setEscape(0);
                    c.setLastAddAttackRound(0);
                    if(c.getName().equals("你马哥")){
                        c.setAttribute("乐");
                    }
                }

            }//每一轮结束强化enemy

            if(wins % 4 == 3){
                if (player.getMaxMP() < 200) {
                    System.out.println();
                    System.out.println("升级！获得 50 点最大生命值, 10 点攻击力和防御力, 20 点最大蓝量");
                    player.setOldMaxHP(player.getOldMaxHP() + 50);
                    player.setOldMaxMP(player.getOldMaxMP() + 20);
                    player.setMaxHP(player.getOldMaxHP());
                    player.setMaxMP(player.getOldMaxMP());
                    player.setOldAttack(player.getOldAttack() + 10);
                    player.setAttack(player.getOldAttack());
                    player.setOldDefence(player.getOldDefence() + 10);
                    player.setDefence(player.getOldDefence());
                    player.heal(50);
                    player.healMP(20);
                    player.setAttribute("喜");
                }else{
                    System.out.println();
                    System.out.println("升级！获得 50 点最大生命值, 10 点攻击力和防御力，恢复20点蓝量");
                    player.setOldMaxHP(player.getOldMaxHP() + 50);
                    player.setMaxHP(player.getOldMaxHP());
                    player.setOldAttack(player.getOldAttack() + 10);
                    player.setAttack(player.getOldAttack());
                    player.setOldDefence(player.getOldDefence() + 10);
                    player.setDefence(player.getOldDefence());
                    player.heal(50);
                    player.healMP(20);
                    player.setAttribute("喜");
                }
                player.show();
                Thread.sleep(1000);
            }//升级

            if(wins % 4 == 0 && wins != 0){
                if (player.getMaxMP() < 200) {
                    System.out.println();
                    System.out.println("升级！获得 50 点最大生命值, 20 点攻击力和防御力, 20 点最大蓝量");
                    player.setOldMaxHP(player.getOldMaxHP() + 50);
                    player.setMaxHP(player.getOldMaxHP());
                    player.setOldMaxMP(player.getOldMaxMP() + 20);
                    player.setMaxMP(player.getOldMaxMP());
                    player.setOldAttack(player.getOldAttack() + 20);
                    player.setAttack(player.getOldAttack());
                    player.setOldDefence(player.getOldDefence() + 20);
                    player.setDefence(player.getOldDefence());
                    player.setAttribute("喜");
                }else{
                    System.out.println();
                    System.out.println("升级！获得 75 点最大生命值, 25 点攻击力和防御力");
                    player.setOldMaxHP(player.getOldMaxHP() + 75);
                    player.setMaxHP(player.getOldMaxHP());
                    player.setOldAttack(player.getOldAttack() + 25);
                    player.setAttack(player.getOldAttack());
                    player.setOldDefence(player.getOldDefence() + 25);
                    player.setDefence(player.getOldDefence());
                    player.setAttribute("喜");
                }
                System.out.println("状态已恢复");
                player.setHP(player.getMaxHP());
                player.setMP(player.getMaxMP());
                showBar(player);
                player.show();
                System.out.println("当前胜场：" + wins);
                Thread.sleep(1000);
            }//boss战后升级

            //选择敌人
            if (count % 4 == 0) {
                enemy = enemyCharacters.get(3);
            } else {
                enemy = enemyCharacters.get(count % 4 - 1);
            }

            playerReserve(player);
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
                if(player.getLastAddAttackRound() != 0 && round - player.getLastAddAttackRound() <= 3){
                    System.out.println("破军之誓 还剩 " + (4 - (round - player.getLastAddAttackRound())) + " 回合");
                }
                if(player.getLastAddAttackRound() != 0 && round - player.getLastAddAttackRound() == 4){
                    System.out.println("破军之誓 已结束");
                    player.setAttack(player.getOldAttack());
                    System.out.println(player.getName() + " 当前攻击力为: " + player.getAttack());
                }
                if(player.getLastRemoveDefenceRound() != 0 && round - player.getLastRemoveDefenceRound() <= 3){
                    System.out.println("看破 还剩 " + (4 - (round - player.getLastRemoveDefenceRound())) + " 回合");
                }
                if(player.getLastRemoveDefenceRound() != 0 && round - player.getLastRemoveDefenceRound() == 4){
                    System.out.println("看破 已结束");
                    enemy.setDefence(enemy.getOldDefence());
                    System.out.println(enemy.getName() +" 当前防御力为: " + enemy.getDefence());
                }

                System.out.println("第" + round + "回合");
                playerRound(player,enemy,round);
                if(!enemy.isSurvive()){
                    System.out.println("你战胜了 "+enemy.getName()+" !");
                    dropLoot(player, enemy);
                    count++;
                    wins++;
                    if(wins % 4 == 0 && wins != 0){
                        break;
                    }
                    System.out .println("当前胜场为： " + wins);
                    Thread.sleep(1000);
                    break;
                }
                if(!player.isSurvive()){
                    System.out.println(player.getName() + " 被 " + enemy.getName() + " 击败了...");
                    System.out.println("你的决心碎了一地....");
                    System.out.println("感谢你的游玩！");
                    Thread.sleep(1000);
                    break;
                }

                enemyTurn(enemy, player,round,plannedAction);
                if(!enemy.isSurvive()){
                    System.out.println("你战胜了 "+enemy.getName()+" !");
                    dropLoot(player, enemy);
                    count++;
                    wins++;
                    if(wins % 4 == 0 && wins != 0){
                        break;
                    }
                    System.out .println("当前胜场为： " + wins);
                    Thread.sleep(1000);
                    break;
                }
                if(!player.isSurvive()){
                    System.out.println(player.getName() + " 被 " + enemy.getName() + " 击败了...");
                    System.out.println("你的决心碎了一地....");
                    System.out.println("感谢你的游玩！");
                    Thread.sleep(1000);
                    break;
                }
                Thread.sleep(500);
                round++;
            }
        }
    }


    public ArrayList<Consumables> createConsumables() {
        ArrayList<Consumables> consumables = new ArrayList<>();
        consumables.add(new Consumables("红色之泉", 1,"普通"));
        consumables.add(new Consumables("仁王盾", 1,"普通"));
        consumables.add(new Consumables("十面埋伏", 0,"稀有"));
        return consumables;
    }
    public HeroCharacter createPlayerCharacter(String username,ArrayList<Consumables> consumables) {
        int points = 20;

        HeroCharacter player = new HeroCharacter();

        player.cSkills.add("强力一击");
        player.cSkills.add("生命汲取");
        player.cSkills.add("破军之誓");
        player.cSkills.add("看破");

        player.cPackage.add(consumables.get(0));//红色之泉
        player.cPackage.add(consumables.get(1));//仁王盾
        player.cPackage.add(consumables.get(2));//十面埋伏

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
        player.setMaxHP(100 + values[0] * 20);
        player.setOldMaxHP(player.getMaxHP());
        player.setHP(player.getMaxHP());
        player.setMaxMP(100);
        player.setMP(100);
        player.setOldMaxMP(player.getMaxMP());
        player.setOldAttack(10 + values[1] * 10);
        player.setAttack(player.getOldAttack());
        player.setOldDefence(10 + values[2] * 5);
        player.setDefence(player.getOldDefence());
        player.setAttribute("喜");

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
        e1.cSkills.add("燃血");
        e2.cSkills.add("快速攻击");
        e2.cSkills.add("瞬狱影杀");
        e3.cSkills.add("防御姿态");
        e3.cSkills.add("肉蛋葱鸡");
        e4.cSkills.add("天动万象");
        e4.cSkills.add("唯我独尊");
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

    public void playerRound(HeroCharacter player, EnemyCharacter enemy, int round) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        boolean flag = true; // false为准备好
        while (flag) {
            showBar(player);
            showBar(enemy);
            System.out.println();
            System.out.println("---你的回合---");
            if(player.getEscape() > 0){
                player.setEscape(player.getEscape() - 1);
                System.out.println(player.getName() + " 本回合被晕眩！");
                Thread.sleep(1000);
                break;
            }
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
                        System.out.println("2. 生命汲取 (20MP 造成伤害,恢复血量)");
                        System.out.println("3. 破军之势 (20MP 提升攻击力)");
                        System.out.println("4. 看破 (20MP 降低敌人防御力)");
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
                            case 3 -> {
                                if(player.getMP() < 20){
                                    System.out.println("当前法力值不足!");
                                    System.out.println();
                                    continue;
                                } else if(round - player.getLastAddAttackRound() >= 6 || player.getLastAddAttackRound() == 0) {
                                    player.setMP(player.getMP() - 20);
                                    player.setLastAddAttackRound(round);
                                    int addAttack = (int) (player.getAttack() * 0.3);
                                    System.out.println(player.getName() + " 使用了 破军之势 ," + player.getName() + " 增加了 "+ addAttack +" 点攻击力！");
                                    player.setAttack(player.getAttack() + addAttack);
                                    System.out.println(player.getName() + " 当前攻击力为: " + player.getAttack());
                                } else{
                                    System.out.println("还有 " + (6 - (round - player.getLastAddAttackRound())) + " 回合才能使用该技能");
                                    System.out.println();
                                    continue;
                                }
                                skillFlag = false;
                            }
                            case 4 -> {
                                if(player.getMP() < 20){
                                    System.out.println("当前法力值不足!");
                                    System.out.println();
                                    continue;
                                } else if(round - player.getLastRemoveDefenceRound() >= 6 || player.getLastRemoveDefenceRound() == 0) {
                                    player.setMP(player.getMP() - 20);
                                    player.setLastRemoveDefenceRound(round);
                                    int removeDefence = (int) (enemy.getDefence() * 0.3);
                                    System.out.println(player.getName() + " 对 " + enemy.getName() + " 使用了 看破 ," + enemy.getName() + " 减少了 " + removeDefence + " 点防御力！");
                                    enemy.setDefence(enemy.getDefence() - removeDefence);
                                    System.out.println(enemy.getName() + " 当前防御力为: " + enemy.getDefence());
                                } else{
                                    System.out.println("还有 " + (6 - (round - player.getLastAddAttackRound())) + " 回合才能使用该技能");
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
                                } else if (round - player.getLastMpHealRound() >= 4 || player.getLastMpHealRound() == 0) {
                                    player.setLastMpHealRound(round);
                                    int healMP = (int) (player.getMaxMP() * (0.5 - 0.1 * player.getPlayerHealMP()));
                                    System.out.println(player.getName() + " 使用了 蓝量回复 , 恢复了 " + healMP + " 点蓝量");
                                    player.healMP(healMP);
                                    player.setPlayerHealMP(player.getPlayerHealMP() + 1);
                                } else {
                                    System.out.println("还有 " + (4 - (round - player.getLastMpHealRound())) + " 回合才能使用该技能");
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
                    // 道具菜单
                    boolean itemFlag = true;
                    String itemChoice = "";
                    while (itemFlag) {
                        System.out.println("---道具---");
                        System.out.print("背包内物品: ");
                        player.showPackage();
                        System.out.println("0. 返回");
                        System.out.print("请输入要使用的道具: ");
                        itemChoice = sc.next();

                        switch (itemChoice){
                            case "红色之泉" -> {
                                Consumables cons = player.cPackage.get(0);
                                if(cons.getNumber() >= 1){
                                    cons.setNumber(cons.getNumber() - 1);
                                    int heal = (int) (player.getMaxHP() * (0.5));
                                    System.out.println(player.getName() + " 使用了 " + cons.getName() + " ,恢复了 " + heal + " 点生命值");
                                    player.heal(heal);
                                    System.out.println();
                                }else {
                                    System.out.println("没有这个道具!");
                                    continue;
                                }
                                itemFlag = false;
                            }
                            case "仁王盾" -> {
                                Consumables cons = player.cPackage.get(1);
                                if(cons.getNumber() >= 1){
                                    cons.setNumber(cons.getNumber() - 1);
                                    int addDefence = (int) (player.getDefence() * 0.5);
                                    player.setDefence(player.getDefence() + addDefence);
                                    System.out.println(player.getName() + " 使用了 " + cons.getName() + " ,增加了 " + addDefence + " 点防御力");
                                    System.out.println(player.getName() + "当前防御力为: " + player.getDefence());
                                    System.out.println();
                                }else {
                                    System.out.println("没有这个道具!");
                                    continue;
                                }
                                itemFlag = false;
                            }
                            case "十面埋伏" -> {
                                Consumables cons = player.cPackage.get(2);
                                if(cons.getNumber() >= 1){
                                    cons.setNumber(cons.getNumber() - 1);
                                    enemy.setEscape(2);
                                    System.out.println(player.getName() + " 使用了 " + cons.getName() + " , " + enemy.getName() + " 将被晕眩两回合");
                                    System.out.println();
                                    System.out.println(enemy.getName() + " 本回合将被晕眩");
                                }else {
                                    System.out.println("没有这个道具!");
                                    continue;
                                }
                                itemFlag = false;
                            }
                            case "0" -> {
                                itemFlag = false;
                            }
                            default -> {
                                System.out.println("没有这个道具!");
                                System.out.println();
                            }
                        }
                    }
                }
                default -> {
                    System.out.println("无效输入!");
                    System.out.println();
                }
            }
        }
    }

    public void enemyTurn(EnemyCharacter enemy, HeroCharacter player, int round,String plannedAction) throws InterruptedException {
        System.out.println("---敌人的回合---");

        if(enemy.getEscape() > 0){
            enemy.setEscape(enemy.getEscape() - 1);
            System.out.println(enemy.getName() + " 本回合被晕眩!");
            return;
        }

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
                    int enemyHurt = enemy.takeDamage(hurt,enemy.getAttribute());
                    System.out.println("反击成功！对 " + enemy.getName() + " 造成 " + enemyHurt + " 点伤害");
                }
            }
            case "猛击"->{
                enemy.setMP(enemy.getMP() - 20);
                int damage2 = calculateDamage((int) (enemy.getAttack() * 1.5), player.getDefence());
                int hurt = player.takeDamage(damage2,enemy.getAttribute());
                System.out.println(enemy.getName() + " 对 " + player.getName() + " 使用了 猛击 ,造成 " + hurt + " 点伤害");
                if(round == player.getLastDefendingRound()){
                    int enemyHurt = enemy.takeDamage(hurt,enemy.getAttribute());
                    System.out.println("反击成功！对 " + enemy.getName() + " 造成 " + enemyHurt + " 点伤害");
                }
            }
            case "燃血"->{
               // 三金（战士）：增加燃血技能，消耗自身10%的血量上限，换取20%攻击力加成，消耗20mp；
                int reduceHP = (int) (enemy.getOldMaxHP() * 0.1);
                if(enemy.getHP() <= reduceHP){
                    System.out.println(enemy.getName() + " 即使知道自己会死,依然选择使用 燃血 !");
                    System.out.println("他英勇战斗过了！");
                    System.out.println();
                    enemy.setHP(0);
                    break;
                }
                enemy.setMP(enemy.getMP() - 20);
                enemy.setLastAddAttackRound(round);
                enemy.setMaxHP(enemy.getMaxHP() - reduceHP);
                enemy.setHP(enemy.getHP() - reduceHP);
                int addAttack = (int) (enemy.getAttack() * 0.2);
                enemy.setAttack(enemy.getAttack() + addAttack);
                System.out.println(enemy.getName() + " 燃烧了自己的生命！  ");
                System.out.println("增加了 " + addAttack + " 点攻击力, " + enemy.getName() + " 当前攻击力为: " + enemy.getAttack());
            }
            case "快速攻击"->{
                enemy.setMP(enemy.getMP() - 20);
                int damage3 = calculateDamage((int) (enemy.getAttack() * 1.6), player.getDefence());
                int hurt = player.takeDamage(damage3,enemy.getAttribute());
                System.out.println(enemy.getName()+" 对 " + player.getName() + " 使用了 快速攻击 ,造成 2 次 " + hurt / 2 + " 点伤害");
                if(round == player.getLastDefendingRound()){
                    int enemyHurt = enemy.takeDamage(hurt,enemy.getAttribute());
                    System.out.println("反击成功！对 " + enemy.getName() + " 造成 " + enemyHurt + " 点伤害");
                }
            }
            case "瞬狱影杀"->{
                //本博（刺客）：增加瞬狱影杀技能，造成6次30%攻击力伤害，消耗20mp；
                enemy.setMP(enemy.getMP() - 20);
                int damage4 = calculateDamage((int) (enemy.getAttack() * 1.8), player.getDefence());
                int hurt = player.takeDamage(damage4,enemy.getAttribute());
                System.out.println(enemy.getName()+" 使用了 瞬狱影杀 !消失在暗影中吧...");
                Thread.sleep(500);
                for(int i = 1; i <= 6; i++){
                    Thread.sleep(500);
                    System.out.println("第"+ i +"击! " + enemy.getName() + " 对 " + player.getName() + "造成 " + hurt / 6 + " 点伤害");
                }
                if(round == player.getLastDefendingRound()){
                    int enemyHurt = enemy.takeDamage(hurt,enemy.getAttribute());
                    System.out.println("反击成功！对 " + enemy.getName() + " 造成 " + enemyHurt + " 点伤害");
                }
            }
            case "防御姿态"->{
                enemy.setMP(enemy.getMP() - 20);
                enemy.setDefending(true);
                enemy.setLastDefendingRound(round);
                System.out.println(enemy.getName()+" 使用了 防御姿态 ,下回合受到的伤害减半");
            }
            case "肉蛋葱鸡"->{
               // 昊泽（坦克）：增加肉蛋葱击技能，对我方单位造成10%血量上限伤害，晕眩我方单位一回合，消耗20mp
                enemy.setMP(enemy.getMP() - 20);
                enemy.setLastAddAttackRound(round);
                player.setEscape(1);
                int damage5 = (int) (player.getMaxHP() * 0.1);
                int hurt = player.takeDamage(damage5,player.getAttribute());
                System.out.println(enemy.getName() + " 使用了 肉蛋葱击 !要来一杯吗?");
                System.out.println("对 " + player.getName() + " 造成 " + hurt + " 点伤害");
                System.out.println(player.getName() + " 被晕眩了...");
                if(round == player.getLastDefendingRound()){
                    int enemyHurt = enemy.takeDamage(hurt,enemy.getAttribute());
                    System.out.println("反击成功！对 " + enemy.getName() + " 造成 " + enemyHurt + " 点伤害");
                }
            }
            case "天动万象"->{
                enemy.setMP(enemy.getMP() - 50);
                player.setEscape(1);
                int damage = calculateDamage((int) (enemy.getAttack() * 2.0), player.getDefence());
                int heal = (int) (enemy.getMaxHP() * 0.3);
                int hurt = player.takeDamage(damage,enemy.getAttribute());
                System.out.print(" 天 ");
                Thread.sleep(500);
                System.out.print(" 动 ");
                Thread.sleep(500);
                System.out.print(" 万 ");
                Thread.sleep(500);
                System.out.println(" 象 ");
                System.out.println(enemy.getName() + " 造成了 " + hurt + " 点伤害,恢复了 "+ heal +" 点生命值");
                System.out.println(player.getName() + " 被晕眩了...");
                enemy.heal(heal);
                if(round == player.getLastDefendingRound()){
                    int enemyHurt = enemy.takeDamage(hurt,enemy.getAttribute());
                    System.out.println("反击成功！对 " + enemy.getName() + " 造成 " + enemyHurt + " 点伤害");
                }
            }
            case "唯我独尊"->{
                //你马哥 ：唯我独尊：获得所有属性,永远的属性克制。消耗50mp
                enemy.setLastAddAttackRound(round);
                enemy.setMP(enemy.getMP() - 50);
                enemy.setAttribute("唯我独尊");
                System.out.print(" 天 ");
                Thread.sleep(300);
                System.out.print(" 上 ");
                Thread.sleep(300);
                System.out.print(" 天 ");
                Thread.sleep(300);
                System.out.println(" 下 ");
                Thread.sleep(300);
                System.out.print(" 唯 ");
                Thread.sleep(300);
                System.out.print(" 我 ");
                Thread.sleep(300);
                System.out.print(" 独 ");
                Thread.sleep(300);
                System.out.println(" 尊 ");
                System.out.println("获得所有属性,永远的属性克制。");
                Thread.sleep(500);
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

    public String enemyAction(EnemyCharacter enemy, HeroCharacter player, int round) throws InterruptedException {
        if(enemy.getEscape() > 0){
            System.out.println(enemy.getName() + " 本回合将被晕眩");
            return "";
        }

        String action = "普通攻击";
        Random r = new Random();

        int num;//敌人技能
        if(enemy.getName().equals("你马哥")){
            num = r.nextInt(7);
        }else{
            num = r.nextInt(10);
        }

        if(enemy.getName().equals("你马哥")){
            if(round == 1){
                System.out.println(enemy.getName()+" 本回合将使用 唯我独尊");
                return "唯我独尊";
            }
            if(num >= 2 && num <= 4){
                int num2 = r.nextInt(2);
                action = enemy.cSkills.get(num2);
            }

            if(num >= 5){
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
            if(num >= 4 && num <= 8){
                int num2 = r.nextInt(2);
                action = enemy.cSkills.get(num2);
            }

            if(num == 9){
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
            case "燃血"->{
                //消耗自身5%的血量上限，换取20%攻击力加成，持续两回合，消耗20mp；
                if(enemy.getMP() >= 20){
                    System.out.println(enemy.getName()+" 本回合将使用 燃血");
                    return "燃血";
                }else{
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
            case "瞬狱影杀"->{
                if (enemy.getMP() >= 20) {
                    System.out.println(enemy.getName()+" 本回合将使用 瞬狱影杀");
                    return "瞬狱影杀";
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
            case "肉蛋葱鸡" ->{
                if (enemy.getMP() < 20) {
                    System.out.println(enemy.getName()+" 本回合将使用 蓝量回复");
                    return "蓝量回复";
                } else if(round - enemy.getLastAddAttackRound() >= 4 || enemy.getLastAddAttackRound() == 0){
                    System.out.println(enemy.getName()+" 本回合将使用 肉蛋葱鸡");
                    return "肉蛋葱鸡";
                }else {
                    System.out.println(enemy.getName()+" 本回合将使用 防御姿态");
                    return "防御姿态";
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
            case "唯我独尊"->{
                if (enemy.getMP() < 50) {
                    System.out.println(enemy.getName()+" 本回合将使用 蓝量回复");
                    return "蓝量回复";
                }else if(enemy.getLastAddAttackRound() == 0){
                    System.out.println(enemy.getName()+" 本回合将使用 唯我独尊");
                    return "唯我独尊";
                }else {
                    System.out.println(enemy.getName()+" 本回合将使用 天动万象");
                    return "天动万象";
                }
            }
        }
        return action;
    }

    public void playerReserve(HeroCharacter player) {
        player.setEscape(0);
        player.setDefending(false);
        player.setPlayerHealMP(0);
        player.setAttribute("喜");
        player.setLastDefendingRound(0);
        player.setLastMpHealRound(0);
        player.setLastAddAttackRound(0);
        player.setLastAddDefenceRound(0);
        player.setLastRemoveDefenceRound(0);
        player.setAttack(player.getOldAttack());
        player.setDefence(player.getOldDefence());
        player.setMaxHP(player.getOldMaxHP());
        player.setMaxMP(player.getOldMaxMP());
    }

    public void dropLoot(HeroCharacter player, EnemyCharacter enemy)  {
        Random r = new Random();
        boolean isBoss = enemy.getName().equals("你马哥");
        boolean isGetLoot = false;
        int chance = 20;

        if (isBoss) {
            int bossDropChance = r.nextInt(100);
            if (bossDropChance < 10) {
                Consumables tenAmbush = player.cPackage.get(2);
                tenAmbush.setNumber(tenAmbush.getNumber() + 1);
                System.out.println("获得道具: " + tenAmbush.getName() + "(" + tenAmbush.getAttribute() + ")"+" x1");
                isGetLoot = true;
            }
            chance = 50;
        }
        
        int dropChance = r.nextInt(100);
        if (dropChance < chance) {
            int itemIndex = r.nextInt(2);
            Consumables item = player.cPackage.get(itemIndex);
            item.setNumber(item.getNumber() + 1);
            System.out.println("获得道具: " + item.getName() + "(" + item.getAttribute() + ")"+" x1");
            isGetLoot = true;
        }

        if (isGetLoot){
            System.out.print("背包: ");
            player.showPackage();
        }
    } //掉落物品
}
