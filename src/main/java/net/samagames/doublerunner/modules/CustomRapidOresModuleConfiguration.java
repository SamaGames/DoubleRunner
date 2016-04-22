package net.samagames.doublerunner.modules;

import net.minecraft.server.v1_9_R1.MathHelper;
import net.samagames.survivalapi.modules.block.RapidOresModule;
import net.samagames.survivalapi.utils.Meta;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

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
                    return createPotion(PotionType.STRENGTH);
                else if (randomized < 40)
                    return createPotion(PotionType.SPEED);
                else if (randomized < 55)
                    return createPotion(PotionType.FIRE_RESISTANCE);
                else if (randomized < 70)
                    return createPotion(PotionType.JUMP);
                else if (randomized < 85)
                    return createPotion(PotionType.NIGHT_VISION);
                else
                    return createPotion(PotionType.POISON);
            }

            @Override
            public int getExperienceModifier(Random random)
            {
                return MathHelper.nextInt(random, 5, 7);
            }
        }, true);
    }

    private ItemStack createPotion(PotionType potionType)
    {
        ItemStack potion = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(potionType, true, false));
        potion.setItemMeta(meta);

        return potion;
    }
}
