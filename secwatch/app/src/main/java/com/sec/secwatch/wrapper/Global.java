package com.sec.secwatch.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rbitt on 2017-04-25.
 */

public class Global {
    /*
    eliminations_average: 17,
    damage_done_average: 6997,
    deaths_average: 9.21,
    final_blows_average: 5.93,
    healing_done_average: 5813,
    objective_kills_average: 6.72,
    objective_time_average: 74000,
    solo_kills_average: 0.9,
    melee_final_blows: 5,
    solo_kills: 30,
    objective_kills: 222,
    final_blows: 196,
    damage_done: 230890,
    eliminations: 561,
    environmental_kill: 1,
    multikills: 5,
    healing_done: 191816,
    recon_assist: 1,
    eliminations_most_in_game: 52,
    final_blows_most_in_game: 18,
    damage_done_most_in_game: 18206,
    healing_done_most_in_game: 17924,
    defensive_assists_most_in_game: 44,
    offensive_assists_most_in_game: 22,
    objective_kills_most_in_game: 22,
    objective_time_most_in_game: 191000,
    multikill_best: 4,
    solo_kills_most_in_game: 18,
    time_spent_on_fire_most_in_game: 288000,
    melee_final_blows_average: 0.15,
    time_spent_on_fire_average: 73000,
    deaths: 304,
    environmental_deaths: 8,
    cards: 6,
    medals: 73,
    medals_gold: 32,
    medals_silver: 20,
    medals_bronze: 21,
    games_played: 33,
    games_won: 20,
    time_spent_on_fire: 2422000,
    objective_time: 2442000,
    time_played: 21600000,
    melee_final_blows_most_in_game: 2,
    turrets_destroyed_most_in_game: 3,
    environmental_kill_most_in_game: 1,
    kill_streak_best: 52,
    turrets_destroyed: 10,
    games_tied: 1,
    games_lost: 12,
    recon_assist_most_in_game: 1,
    offensive_assists: 205,
    defensive_assists: 267
    */

    @JsonProperty("eliminations_average")
    private float eliminations_average;

    @JsonProperty("damage_done_average")
    private int damage_done_average;

    @JsonProperty("deaths_average")
    private float deaths_average;

    @JsonProperty("final_blows_average")
    private float final_blows_average;

    @JsonProperty("healing_done_average")
    private int healing_done_average;

    @JsonProperty("objective_kills_average")
    private float objective_kills_average;

    @JsonProperty("objective_time_average")
    private int objective_time_average;

    @JsonProperty("solo_kills_average")
    private float solo_kills_average;

    @JsonProperty("melee_final_blows")
    private float melee_final_blows;

    @JsonProperty("solo_kills")
    private int solo_kills;

    @JsonProperty("objective_kills")
    private int objective_kills;

    @JsonProperty("final_blows")
    private int final_blows;

    @JsonProperty("damage_done")
    private int damage_done;

    @JsonProperty("eliminations")
    private int eliminations;

    @JsonProperty("environmental_kill")
    private int environmental_kill;

    @JsonProperty("multikills")
    private int multikills;

    @JsonProperty("healing_done")
    private int healing_done;

    @JsonProperty("recon_assist")
    private int recon_assist;

    @JsonProperty("eliminations_most_in_game")
    private int eliminations_most_in_game;

    @JsonProperty("final_blows_most_in_game")
    private int final_blows_most_in_game;

    @JsonProperty("damage_done_most_in_game")
    private int damage_done_most_in_game;

    @JsonProperty("healing_done_most_in_game")
    private int healing_done_most_in_game;

    @JsonProperty("defensive_assists_most_in_game")
    private int defensive_assists_most_in_game;

    @JsonProperty("offensive_assists_most_in_game")
    private int offensive_assists_most_in_game;

    @JsonProperty("objective_kills_most_in_game")
    private int objective_kills_most_in_game;

    @JsonProperty("objective_time_most_in_game")
    private int objective_time_most_in_game;

    @JsonProperty("multikill_best")
    private int multikill_best;

    @JsonProperty("solo_kills_most_in_game")
    private int solo_kills_most_in_game;

    @JsonProperty("time_spent_on_fire_most_in_game")
    private int time_spent_on_fire_most_in_game;

    @JsonProperty("time_spent_on_fire_average")
    private int time_spent_on_fire_average;

    @JsonProperty("melee_final_blows_average")
    private float melee_final_blows_average;

    @JsonProperty("deaths")
    private int deaths;

    @JsonProperty("environmental_deaths")
    private int environmental_deaths;

    @JsonProperty("cards")
    private int cards;

    @JsonProperty("medals")
    private int medals;

    @JsonProperty("medals_gold")
    private int medals_gold;

    @JsonProperty("medals_silver")
    private int medals_silver;

    @JsonProperty("medals_bronze")
    private int medals_bronze;

    @JsonProperty("games_played")
    private int games_played;

    @JsonProperty("games_won")
    private int games_won;

    @JsonProperty("time_spent_on_fire")
    private int time_spent_on_fire;

    @JsonProperty("objective_time")
    private int objective_time;

    @JsonProperty("time_played")
    private int time_played;

    @JsonProperty("melee_final_blows_most_in_game")
    private int melee_final_blows_most_in_game;

    @JsonProperty("turrets_destroyed_most_in_game")
    private int turrets_destroyed_most_in_game;

    @JsonProperty("environmental_kill_most_in_game")
    private int environmental_kill_most_in_game;

    @JsonProperty("kill_streak_best")
    private int kill_streak_best;

    @JsonProperty("turrets_destroyed")
    private int turrets_destroyed;

    @JsonProperty("games_tied")
    private int games_tied;

    @JsonProperty("games_lost")
    private int games_lost;

    @JsonProperty("recon_assist_most_in_game")
    private int recon_assist_most_in_game;

    @JsonProperty("offensive_assists")
    private int offensive_assists;

    @JsonProperty("defensive_assists")
    private int defensive_assists;

    public float getEliminations_average() {
        return eliminations_average;
    }

    public int getDamage_done_average() {
        return damage_done_average;
    }

    public float getDeaths_average() {
        return deaths_average;
    }

    public float getFinal_blows_average() {
        return final_blows_average;
    }

    public int getHealing_done_average() {
        return healing_done_average;
    }

    public float getObjective_kills_average() {
        return objective_kills_average;
    }

    public int getObjective_time_average() {
        return objective_time_average;
    }

    public float getSolo_kills_average() {
        return solo_kills_average;
    }

    public float getMelee_final_blows() {
        return melee_final_blows;
    }

    public int getSolo_kills() {
        return solo_kills;
    }

    public int getObjective_kills() {
        return objective_kills;
    }

    public int getFinal_blows() {
        return final_blows;
    }

    public int getDamage_done() {
        return damage_done;
    }

    public int getEliminations() {
        return eliminations;
    }

    public int getEnvironmental_kill() {
        return environmental_kill;
    }

    public int getMultikills() {
        return multikills;
    }

    public int getHealing_done() {
        return healing_done;
    }

    public int getRecon_assist() {
        return recon_assist;
    }

    public int getEliminations_most_in_game() {
        return eliminations_most_in_game;
    }

    public int getFinal_blows_most_in_game() {
        return final_blows_most_in_game;
    }

    public int getDamage_done_most_in_game() {
        return damage_done_most_in_game;
    }

    public int getHealing_done_most_in_game() {
        return healing_done_most_in_game;
    }

    public int getDefensive_assists_most_in_game() {
        return defensive_assists_most_in_game;
    }

    public int getOffensive_assists_most_in_game() {
        return offensive_assists_most_in_game;
    }

    public int getObjective_kills_most_in_game() {
        return objective_kills_most_in_game;
    }

    public int getObjective_time_most_in_game() {
        return objective_time_most_in_game;
    }

    public int getMultikill_best() {
        return multikill_best;
    }

    public int getSolo_kills_most_in_game() {
        return solo_kills_most_in_game;
    }

    public int getTime_spent_on_fire_most_in_game() {
        return time_spent_on_fire_most_in_game;
    }

    public int getTime_spent_on_fire_average() {
        return time_spent_on_fire_average;
    }

    public float getMelee_final_blows_average() {
        return melee_final_blows_average;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getEnvironmental_deaths() {
        return environmental_deaths;
    }

    public int getCards() {
        return cards;
    }

    public int getMedals() {
        return medals;
    }

    public int getMedals_gold() {
        return medals_gold;
    }

    public int getMedals_silver() {
        return medals_silver;
    }

    public int getMedals_bronze() {
        return medals_bronze;
    }

    public int getGames_played() {
        return games_played;
    }

    public int getGames_won() {
        return games_won;
    }

    public int getTime_spent_on_fire() {
        return time_spent_on_fire;
    }

    public int getObjective_time() {
        return objective_time;
    }

    public int getTime_played() {
        return time_played;
    }

    public int getMelee_final_blows_most_in_game() {
        return melee_final_blows_most_in_game;
    }

    public int getTurrets_destroyed_most_in_game() {
        return turrets_destroyed_most_in_game;
    }

    public int getEnvironmental_kill_most_in_game() {
        return environmental_kill_most_in_game;
    }

    public int getKill_streak_best() {
        return kill_streak_best;
    }

    public int getTurrets_destroyed() {
        return turrets_destroyed;
    }

    public int getGames_tied() {
        return games_tied;
    }

    public int getGames_lost() {
        return games_lost;
    }

    public int getRecon_assist_most_in_game() {
        return recon_assist_most_in_game;
    }

    public int getOffensive_assists() {
        return offensive_assists;
    }

    public int getDefensive_assists() {
        return defensive_assists;
    }
}
