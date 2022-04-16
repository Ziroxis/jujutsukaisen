package com.example.jujutsukaisen.quest.objectives;

public class SharedKillChecks
{
    public static final KillEntityObjective.ICheckKill EXISTS = ((player, target, source) ->
    {
       return player.isAlive();
    });
}
