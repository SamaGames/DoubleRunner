package net.samagames.doublerunner.modules;

import net.samagames.survivalapi.modules.gameplay.RapidUsefulModule;
import net.samagames.survivalapi.utils.Meta;
import net.samagames.tools.MojangShitUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class CustomRapidUsefulModuleConfiguration extends RapidUsefulModule.ConfigurationBuilder
{
    public CustomRapidUsefulModuleConfiguration()
    {
        this.addDefaults();

        this.addDrop(new ItemStack(Material.SUGAR_CANE, 1), (base, random) ->
        {
            int randomized = random.nextInt(100);

            if (randomized < 5)
                return new ItemStack(Material.ENCHANTMENT_TABLE, 1);
            else if (randomized < 25)
                return Meta.addMeta(MojangShitUtils.getPotion("long_swiftness", false, false));
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
