package net.samagames.doublerunner;

import net.samagames.survivalapi.game.SurvivalGame;
import net.samagames.survivalapi.game.types.run.RunBasedGameLoop;
import net.samagames.survivalapi.utils.TimedEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DoubleRunnerGameLoop extends RunBasedGameLoop implements Listener
{
    private boolean fallDamages;

    public DoubleRunnerGameLoop(JavaPlugin plugin, Server server, SurvivalGame game)
    {
        super(plugin, server, game);

        this.fallDamages = false;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void createTeleportationEvent()
    {
        super.createTeleportationEvent();
        this.nextEvent = this.nextEvent.copy(9, 0);
    }

    @Override
    public void createReducingEvent()
    {
        super.createReducingEvent();
        this.fallDamages = true;
    }

    @Override
    public void createDeathmatchEvent()
    {
        this.game.getWorldBorder().setSize(10.0D, 6L * 60L);

        this.nextEvent = new TimedEvent(0, 30, "PvP activé", ChatColor.RED, false, () ->
        {
            this.game.enableDamages();
            this.game.enablePVP();

            this.game.getCoherenceMachine().getMessageManager().writeCustomMessage("Les dégats et le PvP sont maintenant activés. Bonne chance !", true);
            this.game.getCoherenceMachine().getMessageManager().writeCustomMessage("La map est maintenant en réduction constante pendant les 6 prochaines minutes.", true);

            this.createReducingEvent();
        });
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL && !this.fallDamages)
            event.setCancelled(true);
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event)
    {
        if (event.getRecipe().getResult().getType() == Material.DIAMOND_PICKAXE)
        {
            ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
            pickaxe.addEnchantment(Enchantment.DIG_SPEED, 3);

            event.getInventory().setResult(pickaxe);
        }
    }
}
