package net.samagames.doublerunner.modules;

import net.minecraft.server.v1_8_R3.MathHelper;
import net.samagames.survivalapi.modules.block.RapidOresModule;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.Random;

public class CustomRapidOresModuleConfiguration extends RapidOresModule.ConfigurationBuilder
{
    public CustomRapidOresModuleConfiguration()
    {
        this.addDefaults();

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
                return MathHelper.nextInt(random, 0, 2);
            }
        }, true);

        this.addDrop(new ItemStack(Material.QUARTZ_BLOCK, 1), new IRapidOresHook()
        {
            @Override
            public ItemStack getDrop(ItemStack base, Random random)
            {
                int randomized = random.nextInt(100);

                if (randomized < 25)
                    return new Potion(PotionType.STRENGTH).toItemStack(1);
                else if (randomized < 40)
                    return new Potion(PotionType.SPEED).toItemStack(1);
                else if (randomized < 55)
                    return new Potion(PotionType.FIRE_RESISTANCE).toItemStack(1);
                else if (randomized < 70)
                    return new Potion(PotionType.JUMP).toItemStack(1);
                else if (randomized < 85)
                    return new Potion(PotionType.NIGHT_VISION).toItemStack(1);
                else
                    return new Potion(PotionType.POISON).toItemStack(1);
            }

            @Override
            public int getExperienceModifier(Random random)
            {
                return MathHelper.nextInt(random, 2, 5);
            }
        }, true);
    }
}
