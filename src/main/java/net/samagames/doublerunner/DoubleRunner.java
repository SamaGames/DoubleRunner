package net.samagames.doublerunner;

import com.google.gson.JsonPrimitive;
import net.samagames.api.SamaGamesAPI;
import net.samagames.doublerunner.modules.CustomRapidFoodModuleConfiguration;
import net.samagames.doublerunner.modules.CustomRapidOresModuleConfiguration;
import net.samagames.doublerunner.modules.CustomRapidUsefullModuleConfiguration;
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
import net.samagames.survivalapi.modules.gameplay.AutomaticLapisModule;
import net.samagames.survivalapi.modules.gameplay.ConstantPotionModule;
import net.samagames.survivalapi.modules.gameplay.RapidFoodModule;
import net.samagames.survivalapi.modules.gameplay.RapidUsefullModule;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class DoubleRunner extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        SurvivalGame game;

        SurvivalAPI.get().loadModule(RapidOresModule.class, new CustomRapidOresModuleConfiguration().build());
        SurvivalAPI.get().loadModule(RapidFoodModule.class, new CustomRapidFoodModuleConfiguration().build());
        SurvivalAPI.get().loadModule(RapidUsefullModule.class, new CustomRapidUsefullModuleConfiguration().build());

        RapidToolsModule.ConfigurationBuilder rapidToolsConfiguration = new RapidToolsModule.ConfigurationBuilder();
        rapidToolsConfiguration.setToolsMaterial(RapidToolsModule.ConfigurationBuilder.ToolMaterial.IRON);

        SurvivalAPI.get().loadModule(RapidToolsModule.class, rapidToolsConfiguration.build());

        TorchThanCoalModule.ConfigurationBuilder torchThanCoalConfiguration = new TorchThanCoalModule.ConfigurationBuilder();
        torchThanCoalConfiguration.setTorchAmount(8);

        SurvivalAPI.get().loadModule(TorchThanCoalModule.class, torchThanCoalConfiguration.build());

        WorldDropModule.ConfigurationBuilder worldDropConfiguration = new WorldDropModule.ConfigurationBuilder();
        worldDropConfiguration.addCustomDrop(Material.OBSIDIAN, new ItemStack(Material.OBSIDIAN, 4));
        worldDropConfiguration.addCustomDrop(Material.APPLE, new ItemStack(Material.GOLDEN_APPLE, 1));

        SurvivalAPI.get().loadModule(WorldDropModule.class, worldDropConfiguration.build());

        ConstantPotionModule.ConfigurationBuilder constantPotionConfiguration = new ConstantPotionModule.ConfigurationBuilder();
        constantPotionConfiguration.addPotionEffect(PotionEffectType.SPEED, 1);
        constantPotionConfiguration.addPotionEffect(PotionEffectType.FAST_DIGGING, 0);

        SurvivalAPI.get().loadModule(ConstantPotionModule.class, constantPotionConfiguration.build());
        SurvivalAPI.get().loadModule(OneShootPassiveModule.class, null);
        SurvivalAPI.get().loadModule(AutomaticLapisModule.class, null);

        int nb = SamaGamesAPI.get().getGameManager().getGameProperties().getOption("playersPerTeam", new JsonPrimitive(1)).getAsInt();

        if (nb > 1)
            game = new RunBasedTeamGame<>(this, "doublerunner", "DoubleRunner", "Un Repas, un Café, un DoubleRunner !", ChatColor.DARK_PURPLE + "☕", DoubleRunnerGameLoop.class, nb);
        else
            game = new RunBasedSoloGame<>(this, "doublerunner", "DoubleRunner", "Un Repas, un Café, un DoubleRunner !", ChatColor.DARK_PURPLE + "☕", DoubleRunnerGameLoop.class);

        SurvivalAPI.get().unloadModule(RandomChestModule.class);

        SamaGamesAPI.get().getGameManager().setMaxReconnectTime(10);
        SamaGamesAPI.get().getGameManager().registerGame(game);
    }
}