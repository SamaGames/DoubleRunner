package net.samagames.doublerunner.modules;

import net.minecraft.server.v1_8_R3.MathHelper;
import net.samagames.survivalapi.modules.block.RapidOresModule;
import net.samagames.tools.MojangShitUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

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
                    return MojangShitUtils.getPotionLegacy(PotionType.STRENGTH, 1, true, false);
                else if (randomized < 40)
                    return MojangShitUtils.getPotionLegacy(PotionType.SPEED, 1, true, false);
                else if (randomized < 55)
                    return MojangShitUtils.getPotionLegacy(PotionType.FIRE_RESISTANCE, 1, true, false);
                else if (randomized < 70)
                    return MojangShitUtils.getPotionLegacy(PotionType.INVISIBILITY, 1, true, false);
                else if (randomized < 85)
                    return MojangShitUtils.getPotionLegacy(PotionType.NIGHT_VISION, 1, true, false);
                else
                    return MojangShitUtils.getPotionLegacy(PotionType.POISON, 1, true, true);
            }

            @Override
            public int getExperienceModifier(Random random)
            {
                return MathHelper.nextInt(random, 5, 7);
            }
        }, true);
    }
}
