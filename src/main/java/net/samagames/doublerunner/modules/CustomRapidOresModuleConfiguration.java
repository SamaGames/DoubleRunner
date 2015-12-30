package net.samagames.doublerunner.modules;

import net.minecraft.server.v1_8_R3.MathHelper;
import net.samagames.survivalapi.modules.block.RapidOresModule;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
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
                return MathHelper.nextInt(random, 3, 6);
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
                return MathHelper.nextInt(random, 5, 7);
            }
        }, true);

        this.addDrop(new ItemStack(Material.QUARTZ_BLOCK, 1), new IRapidOresHook()
        {
            @Override
            public ItemStack getDrop(ItemStack base, Random random)
            {
                int randomized = random.nextInt(100);

                if (randomized < 25)
                    return new Potion(PotionType.STRENGTH).extend().toItemStack(1);
                else if (randomized < 40)
                    return new Potion(PotionType.SPEED).extend().toItemStack(1);
                else if (randomized < 55)
                    return new Potion(PotionType.FIRE_RESISTANCE).extend().toItemStack(1);
                else if (randomized < 70)
                    return new Potion(PotionType.JUMP).extend().toItemStack(1);
                else if (randomized < 85)
                    return new Potion(PotionType.NIGHT_VISION).extend().toItemStack(1);
                else
                {
                    Potion potion = new Potion(PotionType.POISON, 1).extend().splash();
                    ItemStack stack = potion.toItemStack(1);

                    PotionMeta meta = (PotionMeta) stack.getItemMeta();
                    meta.clearCustomEffects();
                    meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 8 * 20, 0), true);

                    stack.setItemMeta(meta);

                    return stack;
                }
            }

            @Override
            public int getExperienceModifier(Random random)
            {
                return MathHelper.nextInt(random, 3, 6);
            }
        }, true);
    }
}
