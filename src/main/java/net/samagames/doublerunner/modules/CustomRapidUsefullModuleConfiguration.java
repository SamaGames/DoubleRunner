package net.samagames.doublerunner.modules;

import net.samagames.survivalapi.modules.gameplay.RapidUsefullModule;
import net.samagames.survivalapi.utils.Meta;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Tree;
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
            else if (randomized < 25)
                return Meta.addMeta(new Potion(PotionType.SPEED).extend().toItemStack(1));
            else
                return new ItemStack(Material.BOOK, 1);
        }, true);

        this.addDrop(new ItemStack(Material.GRAVEL, 1), (base, random) ->
        {
            if (random.nextInt(100) < 20)
                return new ItemStack(Material.FLINT_AND_STEEL, 1);
            else
                return new ItemStack(Material.ARROW, 16);
        }, true);

        this.addDrop(new ItemStack(Material.FLINT, 1), (base, random) ->
        {
            if (random.nextInt(100) < 20)
                return new ItemStack(Material.FLINT_AND_STEEL, 1);
            else
                return new ItemStack(Material.ARROW, 16);
        }, true);

        this.addDrop(new ItemStack(Material.SAND, 1), (base, random) -> new ItemStack(Material.SAND, 1), true);
    }
}
