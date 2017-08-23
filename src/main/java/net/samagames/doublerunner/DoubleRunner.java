package net.samagames.doublerunner;

import com.google.gson.JsonPrimitive;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.GamesNames;
import net.samagames.doublerunner.modules.CustomRapidFoodModuleConfiguration;
import net.samagames.doublerunner.modules.CustomRapidOresModuleConfiguration;
import net.samagames.doublerunner.modules.CustomRapidUsefulModuleConfiguration;
import net.samagames.survivalapi.modules.combat.OneShootPassiveModule;
import net.samagames.survivalapi.SurvivalAPI;
import net.samagames.survivalapi.game.SurvivalGame;
import net.samagames.survivalapi.game.types.run.RunBasedSoloGame;
import net.samagames.survivalapi.game.types.run.RunBasedTeamGame;
import net.samagames.survivalapi.modules.block.RandomChestModule;
import net.samagames.survivalapi.modules.block.RapidOresModule;
import net.samagames.survivalapi.modules.block.TorchThanCoalModule;
import net.samagames.survivalapi.modules.block.WorldDropModule;
import net.samagames.survivalapi.modules.craft.RapidToolsModule;
import net.samagames.survivalapi.modules.gameplay.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

/*
 * This file is part of DoubleRunner.
 *
 * DoubleRunner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DoubleRunner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DoubleRunner.  If not, see <http://www.gnu.org/licenses/>.
 */
public class DoubleRunner extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        SurvivalGame game;

        SurvivalAPI.get().loadModule(RapidOresModule.class, new CustomRapidOresModuleConfiguration().build());
        SurvivalAPI.get().loadModule(RapidFoodModule.class, new CustomRapidFoodModuleConfiguration().build());
        SurvivalAPI.get().loadModule(RapidUsefulModule.class, new CustomRapidUsefulModuleConfiguration().build());

        RapidToolsModule.ConfigurationBuilder rapidToolsConfiguration = new RapidToolsModule.ConfigurationBuilder();
        rapidToolsConfiguration.setToolsMaterial(RapidToolsModule.ConfigurationBuilder.ToolMaterial.IRON);

        SurvivalAPI.get().loadModule(RapidToolsModule.class, rapidToolsConfiguration.build());

        TorchThanCoalModule.ConfigurationBuilder torchThanCoalConfiguration = new TorchThanCoalModule.ConfigurationBuilder();
        torchThanCoalConfiguration.setTorchAmount(8);

        SurvivalAPI.get().loadModule(TorchThanCoalModule.class, torchThanCoalConfiguration.build());

        WorldDropModule.ConfigurationBuilder worldDropConfiguration = new WorldDropModule.ConfigurationBuilder();
        worldDropConfiguration.addCustomDrop(Material.OBSIDIAN, new ItemStack(Material.OBSIDIAN, 4));
        worldDropConfiguration.addCustomDrop(Material.APPLE, new ItemStack(Material.GOLDEN_APPLE, 2));

        SurvivalAPI.get().loadModule(WorldDropModule.class, worldDropConfiguration.build());

        ConstantPotionModule.ConfigurationBuilder constantPotionConfiguration = new ConstantPotionModule.ConfigurationBuilder();
        constantPotionConfiguration.addPotionEffect(PotionEffectType.SPEED, 1);
        constantPotionConfiguration.addPotionEffect(PotionEffectType.FAST_DIGGING, 0);

        SurvivalAPI.get().loadModule(ConstantPotionModule.class, constantPotionConfiguration.build());
        SurvivalAPI.get().loadModule(OneShootPassiveModule.class, null);
        SurvivalAPI.get().loadModule(AutomaticLapisModule.class, null);

        int nb = SamaGamesAPI.get().getGameManager().getGameProperties().getGameOption("playersPerTeam", new JsonPrimitive(1)).getAsInt();

        if (nb > 1)
            game = new RunBasedTeamGame<>(this, "doublerunner", "DoubleRunner", "Un Repas, un Café, un DoubleRunner !", ChatColor.DARK_PURPLE + "☕", DoubleRunnerGameLoop.class, nb);
        else
            game = new RunBasedSoloGame<>(this, "doublerunner", "DoubleRunner", "Un Repas, un Café, un DoubleRunner !", ChatColor.DARK_PURPLE + "☕", DoubleRunnerGameLoop.class);

        SurvivalAPI.get().unloadModule(RandomChestModule.class);

        SamaGamesAPI.get().getGameManager().setMaxReconnectTime(10);
        SamaGamesAPI.get().getStatsManager().setStatsToLoad(GamesNames.DOUBLERUNNER, true);
        SamaGamesAPI.get().getShopsManager().setShopToLoad(GamesNames.DOUBLERUNNER, true);
        SamaGamesAPI.get().getGameManager().setGameStatisticsHelper(new DoubleRunnerStatisticsHelper());
        SamaGamesAPI.get().getGameManager().registerGame(game);
    }
}