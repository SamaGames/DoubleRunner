package net.samagames.doublerunner.modules;

import net.samagames.survivalapi.modules.gameplay.RapidUsefulModule;
import net.samagames.survivalapi.utils.Meta;
import net.samagames.tools.MojangShitUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/*
 * This file is part of DoubleRunner.
 *
 * DoubleRunner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DoubleRunner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DoubleRunner.  If not, see <http://www.gnu.org/licenses/>.
 */
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
