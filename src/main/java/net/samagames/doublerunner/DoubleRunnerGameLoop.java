package net.samagames.doublerunner;

import net.samagames.survivalapi.game.SurvivalGame;
import net.samagames.survivalapi.game.types.run.RunBasedGameLoop;
import net.samagames.survivalapi.utils.TimedEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

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
    public void createDamageEvent()
    {
        this.nextEvent = new TimedEvent(1, 0, "Dégats actifs", ChatColor.GREEN, false, () ->
        {
            this.game.getCoherenceMachine().getMessageManager().writeCustomMessage("Les dégats sont désormais actifs.", true);
            this.game.getCoherenceMachine().getMessageManager().writeCustomMessage("Le map sera réduite dans 9 minutes. Le PvP sera activé à ce moment là.", true);
            this.game.enableDamages();
            this.createTeleportationEvent();
        });
    }

    @Override
    public void createTeleportationEvent()
    {
        super.createTeleportationEvent();
        this.nextEvent = this.nextEvent.copy(9, 0);
    }

    @Override
    public void createDeathmatchEvent()
    {
        this.game.getWorldBorder().setSize(50.0D, 6L * 60L);

        this.nextEvent = new TimedEvent(0, 30, "PvP activé", ChatColor.RED, false, () ->
        {
            this.game.enableDamages();
            this.game.enablePVP();

            this.game.getCoherenceMachine().getMessageManager().writeCustomMessage("Les dégats et le PvP sont maintenant activés. Bonne chance !", true);
            this.game.getCoherenceMachine().getMessageManager().writeCustomMessage("La map est maintenant en réduction constante pendant les 6 prochaines minutes.", true);

            this.createReducingEvent();
        });
    }

    @Override
    public void createReducingEvent()
    {
        super.createReducingEvent();
        this.nextEvent = this.nextEvent.copy(5, 30);

        this.fallDamages = true;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.getItem() != null && event.getItem().getType() == Material.POTION && Potion.fromItemStack(event.getItem()).getType() == PotionType.POISON)
        {
            if (!this.game.isPvPActivated())
            {
                event.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas utiliser cet objet hors du PvP.");

                event.setCancelled(true);
                event.getPlayer().updateInventory();
            }
        }
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

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event)
    {
        Bukkit.broadcastMessage("potion");

        PotionEffect actual = null;

        for (PotionEffect potionEffect : event.getPotion().getEffects())
        {
            Bukkit.broadcastMessage("effect: " + potionEffect.getType().getName());

            if (potionEffect.getType() == PotionEffectType.POISON)
            {
                Bukkit.broadcastMessage("poison detected");
                actual = potionEffect;
                break;
            }
        }

        if (actual != null)
        {
            Bukkit.broadcastMessage("changing effect");
            event.getPotion().getEffects().remove(actual);
            event.getPotion().getEffects().add(new PotionEffect(PotionEffectType.POISON, 5 * 20, 0));
        }
    }
}
