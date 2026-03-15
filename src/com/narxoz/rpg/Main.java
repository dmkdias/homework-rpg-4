package com.narxoz.rpg;

import com.narxoz.rpg.battle.RaidEngine;
import com.narxoz.rpg.battle.RaidResult;
import com.narxoz.rpg.bridge.*;
import com.narxoz.rpg.composite.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 4 Demo: Bridge + Composite ===\n");

        HeroUnit warrior = new HeroUnit("Arthas", 140, 30);
        HeroUnit mage = new HeroUnit("Jaina", 90, 40);
        HeroUnit priest = new HeroUnit("Anduin", 70, 10); // Добавили третьего героя

        EnemyUnit goblin = new EnemyUnit("Goblin", 70, 20);
        EnemyUnit orc = new EnemyUnit("Orc", 120, 25);
        EnemyUnit boss = new EnemyUnit("Lich King", 500, 50); // Добавили босса

        PartyComposite heroes = new PartyComposite("Alliance Heroes");
        heroes.add(warrior);
        heroes.add(mage);
        heroes.add(priest);

        PartyComposite frontline = new PartyComposite("Frontline Squad");
        frontline.add(goblin);
        frontline.add(orc);

        RaidGroup enemyRaid = new RaidGroup("Scourge Raid");
        enemyRaid.add(boss);
        enemyRaid.add(frontline); // Вложенность: группа внутри группы

        System.out.println("--- Team Structures ---");
        heroes.printTree("");
        System.out.println("Total Heroes HP: " + heroes.getHealth());

        enemyRaid.printTree("");
        System.out.println("Total Enemies HP: " + enemyRaid.getHealth());

        Skill fireStorm = new AreaSkill("Fire Storm", 25, new FireEffect());

        Skill shadowStrike = new SingleTargetSkill("Shadow Strike", 40, new ShadowEffect());
        Skill iceBlast = new SingleTargetSkill("Ice Blast", 30, new IceEffect());

        System.out.println("\n--- Bridge Combinations Preview ---");
        System.out.println(fireStorm.getSkillName() + " deals " + fireStorm.getEffectName() + " damage type.");
        System.out.println(shadowStrike.getSkillName() + " deals " + shadowStrike.getEffectName() + " damage type.");

        System.out.println("\n--- Battle Starts! ---");
        RaidEngine engine = new RaidEngine().setRandomSeed(42L);

        RaidResult result = engine.runRaid(heroes, enemyRaid, fireStorm, shadowStrike);

        System.out.println("\n--- Raid Result ---");
        System.out.println("Winner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());

        System.out.println("\n--- Full Battle Log ---");
        for (String line : result.getLog()) {
            System.out.println(line);
        }

        System.out.println("\n=== Demo Complete ===");
    }
}