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
import org.bukkit.event.enchantment.EnchantItemEvent;
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
            this.blocksProtected = false;
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
        this.game.setWorldBorderSize(50.0D, 6L * 60L);

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
        this.fallDamages = true;

        this.nextEvent = new TimedEvent(5, 30, "Seconde réduction", ChatColor.YELLOW, false, () ->
        {
            this.game.setWorldBorderSize(50.0D);

            this.game.getCoherenceMachine().getMessageManager().writeCustomMessage("La map va désormais effectuer une seconde réduction pendant 8 minutes !", true);
            this.createSecondReducingEvent();
        });
    }

    public void createSecondReducingEvent()
    {
        this.game.setWorldBorderSize(16.0D, 6L * 60L);

        this.nextEvent = new TimedEvent(8, 0, "Fin de la réduction", ChatColor.RED, false, () ->
        {
            this.game.setWorldBorderSize(16.0D);

            this.game.getCoherenceMachine().getMessageManager().writeCustomMessage("La map est désormais réduite. Fin de la partie forcée dans 2 minutes !", true);
            this.createEndEvent();
        });
    }

    @Override
    public void createEndEvent()
    {
        super.createEndEvent();
        this.nextEvent = this.nextEvent.copy(2, 0);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.getItem() != null && event.getItem().getType() == Material.POTION && Potion.fromItemStack(event.getItem()).getType() == PotionType.POISON && !this.game.isPvPActivated())
        {
            event.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas utiliser cet objet hors du PvP.");

            event.setCancelled(true);
            event.getPlayer().updateInventory();
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

            this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () ->
            {
                for (ItemStack stack : event.getWhoClicked().getInventory().getContents())
                    if (stack != null && stack.getType() == Material.DIAMOND_PICKAXE && !stack.containsEnchantment(Enchantment.DIG_SPEED))
                        stack.addEnchantment(Enchantment.DIG_SPEED, 3);
            }, 5L);
        }
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event)
    {
        if (event.getItem().getType() == Material.LEATHER_BOOTS || event.getItem().getType() == Material.IRON_BOOTS || event.getItem().getType() == Material.CHAINMAIL_BOOTS || event.getItem().getType() == Material.GOLD_BOOTS || event.getItem().getType() == Material.DIAMOND_BOOTS)
            event.getItem().addEnchantment(Enchantment.DEPTH_STRIDER, 2);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if (event.getClickedInventory() != null && event.getCurrentItem() != null)
        {
            if (event.getCurrentItem().getType() == Material.LEATHER_BOOTS || event.getCurrentItem().getType() == Material.IRON_BOOTS || event.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS || event.getCurrentItem().getType() == Material.GOLD_BOOTS || event.getCurrentItem().getType() == Material.DIAMOND_BOOTS)
            {
                if ((event.getClickedInventory().getType() == InventoryType.ENCHANTING || event.getClickedInventory().getType() == InventoryType.ANVIL) && event.getSlot() == 0)
                    event.getCurrentItem().removeEnchantment(Enchantment.DEPTH_STRIDER);
                else if (event.getClickedInventory().getType() == InventoryType.ANVIL && event.getSlot() == 2)
                    event.getCurrentItem().addEnchantment(Enchantment.DEPTH_STRIDER, 2);
            }
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event)
    {
        PotionEffect actual = null;

        for (PotionEffect potionEffect : event.getPotion().getEffects())
        {
            if (potionEffect.getType().getName().equals("POISON"))
            {
                actual = potionEffect;
                break;
            }
        }

        if (actual != null)
        {
            event.setCancelled(true);
            event.getAffectedEntities().forEach(entity -> entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 8 * 20, 0)));
        }
    }
}
