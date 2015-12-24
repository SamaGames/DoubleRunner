package net.samagames.doublerunner.modules;

import net.samagames.survivalapi.modules.gameplay.RapidFoodModule;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomRapidFoodModuleConfiguration extends RapidFoodModule.ConfigurationBuilder
{
    public CustomRapidFoodModuleConfiguration()
    {
        this.addDefaults();

        this.addDrop(EntityType.SHEEP, (drops, random) ->
        {
            List<ItemStack> newDrops = drops.stream().filter(stack -> stack.getType() == Material.MUTTON).map(stack -> new ItemStack(Material.COOKED_MUTTON, stack.getAmount() * 2)).collect(Collectors.toList());
            newDrops.add(new ItemStack(Material.BOW, 1));
            newDrops.add(new ItemStack(Material.BOOK, random.nextInt(2) + 1));

            return newDrops;
        }, true);

        this.addDrop(EntityType.COW, (drops, random) ->
        {
            List<ItemStack> newDrops = drops.stream().filter(stack -> stack.getType() == Material.RAW_BEEF).map(stack -> new ItemStack(Material.COOKED_BEEF, stack.getAmount() * 2)).collect(Collectors.toList());
            newDrops.add(new ItemStack(Material.BOOK, random.nextInt(2) + 1));

            if (random.nextInt(100) < 30)
                newDrops.add(new ItemStack(Material.MILK_BUCKET, 1));

            return newDrops;
        }, true);

        this.addDrop(EntityType.PIG, (drops, random) ->
        {
            List<ItemStack> newDrops = drops.stream().filter(stack -> stack.getType() == Material.PORK).map(stack -> new ItemStack(Material.GRILLED_PORK, stack.getAmount() * 2)).collect(Collectors.toList());
            newDrops.add(new ItemStack(Material.BOOK, random.nextInt(2) + 1));

            return newDrops;
        }, true);

        this.addDrop(EntityType.CHICKEN, (drops, random) ->
        {
            List<ItemStack> newDrops = drops.stream().filter(stack -> stack.getType() == Material.RAW_CHICKEN).map(stack -> new ItemStack(Material.COOKED_CHICKEN, stack.getAmount() * 2)).collect(Collectors.toList());
            newDrops.add(new ItemStack(Material.ARROW, 16));

            return newDrops;
        }, true);

        this.addDrop(EntityType.SQUID, (drops, random) ->
        {
            List<ItemStack> newDrops = new ArrayList<>();
            newDrops.add(new ItemStack(Material.COOKED_FISH, random.nextInt(2) + 1));

            if (random.nextInt(100) < 30)
                newDrops.add(new ItemStack(Material.FISHING_ROD, 1));

            return newDrops;
        }, true);

        this.addDrop(EntityType.RABBIT, (drops, random) ->
        {
            List<ItemStack> newDrops = drops.stream().filter(stack -> stack.getType() == Material.RABBIT).map(stack -> new ItemStack(Material.COOKED_RABBIT, stack.getAmount() * 2)).collect(Collectors.toList());

            if (random.nextInt(100) < 30)
                newDrops.add(new Potion(PotionType.JUMP).toItemStack(1));

            return newDrops;
        }, true);

        this.addDrop(EntityType.BAT, (drops, random) ->
        {
            List<ItemStack> newDrops = new ArrayList<>();
            newDrops.add(new ItemStack(Material.COOKED_MUTTON, random.nextInt(2) + 1));

            if (random.nextInt(100) < 30)
                newDrops.add(new Potion(PotionType.NIGHT_VISION).toItemStack(1));

            return newDrops;
        }, true);

        this.addDrop(EntityType.SKELETON, (drops, random) ->
        {
            List<ItemStack> newDrops = new ArrayList<>();

            for (ItemStack stack : drops)
            {
                if (stack.getType() == Material.ARROW)
                {
                    newDrops.add(new ItemStack(Material.ARROW, stack.getAmount() * 2));
                }
                if (stack.getType() == Material.BOW)
                {
                    stack.setDurability((short) 0);
                    stack.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
                    newDrops.add(stack);
                }
            }

            return newDrops;
        }, true);

        this.addDrop(EntityType.SPIDER, (drops, random) ->
        {
            if (random.nextInt(100) < 20)
            {
                Potion potion = new Potion(PotionType.POISON, 1);
                ItemStack stack = potion.toItemStack(1);

                PotionMeta meta = (PotionMeta) stack.getItemMeta();
                meta.clearCustomEffects();
                meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 5 * 20, 0), true);

                stack.setItemMeta(meta);

                drops.add(stack);
            }

            return drops;
        }, true);

        this.addDrop(EntityType.ZOMBIE, (drops, random) ->
        {
            if (random.nextInt(100) < 15)
            {
                drops.add(new Potion(PotionType.STRENGTH).toItemStack(1));
            }

            return drops;
        }, true);
    }
}
