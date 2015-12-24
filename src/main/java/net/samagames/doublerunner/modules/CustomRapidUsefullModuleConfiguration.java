package net.samagames.doublerunner.modules;

import net.samagames.survivalapi.modules.gameplay.RapidUsefullModule;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;


public class CustomRapidUsefullModuleConfiguration extends RapidUsefullModule.ConfigurationBuilder
{
    public CustomRapidUsefullModuleConfiguration()
    {
        this.addDefaults();

        this.addDrop(new ItemStack(Material.SUGAR_CANE, 1), (base, random) ->
        {
            int randomized = random.nextInt(100);

            if (randomized < 5)
                return new ItemStack(Material.ENCHANTMENT_TABLE, 1);
            else
                return new Potion(PotionType.SPEED).toItemStack(1);
        }, true);

        this.addDrop(new ItemStack(Material.GRAVEL, 1), (base, random) ->
        {
            if (random.nextInt(100) < 20)
                return new ItemStack(Material.FLINT_AND_STEEL, 1);
            else
                return new ItemStack(Material.ARROW, 16);
        }, true);
    }
}
