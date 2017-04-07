package net.samagames.doublerunner.modules;

import net.minecraft.server.v1_8_R3.MathHelper;
import net.samagames.survivalapi.modules.block.RapidOresModule;
import net.samagames.tools.MojangShitUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CustomRapidOresModuleConfiguration extends RapidOresModule.ConfigurationBuilder
{
    public CustomRapidOresModuleConfiguration()
    {
        this.addDefaults();

        this.addDrop(new ItemStack(Material.COAL), new IRapidOresHook()
        {
            @Override
            public ItemStack getDrop(ItemStack base, Random random)
            {
                return new ItemStack(Material.TORCH, 6);
            }

            @Override
            public int getExperienceModifier(Random random)
            {
                return MathHelper.nextInt(random, 3, 6);
            }
        }, true);

        this.addDrop(new ItemStack(Material.IRON_ORE), new IRapidOresHook()
        {
            @Override
            public ItemStack getDrop(ItemStack base, Random random)
            {
                return new ItemStack(Material.IRON_INGOT, 6);
            }

            @Override
            public int getExperienceModifier(Random random)
            {
                return MathHelper.nextInt(random, 5, 7);
            }
        }, true);

        this.addDrop(new ItemStack(Material.DIAMOND), new IRapidOresHook()
        {
            @Override
            public ItemStack getDrop(ItemStack base, Random random)
            {
                return new ItemStack(Material.DIAMOND, 4);
            }

            @Override
            public int getExperienceModifier(Random random)
            {
                return MathHelper.nextInt(random, 7, 9);
            }
        }, true);

        this.addDrop(new ItemStack(Material.QUARTZ_BLOCK, 1), new IRapidOresHook()
        {
            @Override
            public ItemStack getDrop(ItemStack base, Random random)
            {
                int randomized = random.nextInt(100);

                if (randomized < 25)
                    return MojangShitUtils.getPotion("long_strength", false, false);
                else if (randomized < 40)
                    return MojangShitUtils.getPotion("long_swiftness", false, false);
                else if (randomized < 55)
                    return MojangShitUtils.getPotion("long_fire_resistance", false, false);
                else if (randomized < 70)
                    return MojangShitUtils.getPotion("long_leaping", false, false);
                else if (randomized < 85)
                    return MojangShitUtils.getPotion("long_night_vision", false, false);
                else
                    return MojangShitUtils.getPotion("long_poison", true, false);
            }

            @Override
            public int getExperienceModifier(Random random)
            {
                return MathHelper.nextInt(random, 5, 7);
            }
        }, true);
    }
}
