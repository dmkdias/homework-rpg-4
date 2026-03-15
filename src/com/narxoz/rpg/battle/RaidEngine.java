package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;
import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        RaidResult result = new RaidResult();
        int round = 0;
        int maxRounds = 50;

        result.addLine("--- Начало рейда: " + teamA.getName() + " VS " + teamB.getName() + " ---");

        while (teamA.isAlive() && teamB.isAlive() && round < maxRounds) {
            round++;
            result.addLine("Раунд " + round);

            if (teamA.isAlive()) {
                teamASkill.cast(teamB);
                result.addLine(teamA.getName() + " использует " + teamASkill.getSkillName() +
                        " [" + teamASkill.getEffectName() + "]. У " + teamB.getName() +
                        " осталось HP: " + teamB.getHealth());
            }

            if (teamB.isAlive()) {
                teamBSkill.cast(teamA);
                result.addLine(teamB.getName() + " использует " + teamBSkill.getSkillName() +
                        " [" + teamBSkill.getEffectName() + "]. У " + teamA.getName() +
                        " осталось HP: " + teamA.getHealth());
            }
        }

        result.setRounds(round);
        result.setWinner(teamA.isAlive() ? teamA.getName() : (teamB.isAlive() ? teamB.getName() : "Ничья"));
        result.addLine("--- Бой окончен! Победитель: " + result.getWinner() + " ---");

        return result;
    }
}