package net.samagames.doublerunner;

import net.samagames.api.SamaGamesAPI;
import net.samagames.survivalapi.game.SurvivalGameStatisticsHelper;

import java.util.UUID;

/**
 *               )\._.,--....,'``.
 * .b--.        /;   _.. \   _\  (`._ ,.
 * `=,-,-'~~~   `----(,_..'--(,_..'`-.;.'
 *
 * This file is issued of the project DoubleRunner
 * Created by Jérémy L. (BlueSlime) on 29/07/16
 */
public class DoubleRunnerStatisticsHelper implements SurvivalGameStatisticsHelper
{
    @Override
    public void increaseKills(UUID uuid)
    {
        SamaGamesAPI.get().getStatsManager().getPlayerStats(uuid).getDoubleRunnerStatistics().incrByKills(1);
    }

    @Override
    public void increaseDeaths(UUID uuid)
    {
        SamaGamesAPI.get().getStatsManager().getPlayerStats(uuid).getDoubleRunnerStatistics().incrByDeaths(1);
    }

    @Override
    public void increaseDamages(UUID uuid, double damages)
    {
        SamaGamesAPI.get().getStatsManager().getPlayerStats(uuid).getDoubleRunnerStatistics().incrByDamages((int) damages);
    }

    @Override
    public void increasePlayedTime(UUID uuid, long playedTime)
    {
        SamaGamesAPI.get().getStatsManager().getPlayerStats(uuid).getDoubleRunnerStatistics().incrByPlayedTime(playedTime);
    }

    @Override
    public void increasePlayedGames(UUID uuid)
    {
        SamaGamesAPI.get().getStatsManager().getPlayerStats(uuid).getDoubleRunnerStatistics().incrByPlayedGames(1);
    }

    @Override
    public void increaseWins(UUID uuid)
    {
        SamaGamesAPI.get().getStatsManager().getPlayerStats(uuid).getDoubleRunnerStatistics().incrByWins(1);
    }
}
