/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.mmo.movement.jumpkill;

import nl.imine.mmo.movement.leap.Leap;
import nl.makertim.MMOmain.Skill;
import nl.makertim.MMOmain.lib.MMOOutlaws;
import nl.makertim.MMOmain.lib.SkillAction;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 *
 * @author Sander
 */
public class JumpKill extends SkillAction {

    private static Plugin plugin;

    public static void init() {
        MMOOutlaws.getInstance().addSkillHandler(Leap.class);
    }

    @Override
    public void registerEvents(PluginManager pm, Plugin pl) {
        super.registerEvents(pm, pl);
        plugin = pl;
    }

    @EventHandler
    public void onPlayerDammage(EntityDamageEvent evt) {
        if (evt.getEntity() instanceof Player) {
            if (evt.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                if (evt.getDamage() != 0) {
                    for (Entity E : evt.getEntity().getNearbyEntities(2, 2, 2)) {
                        if (E instanceof Player) {
                            double dmg = getWeaponDamage(((Player) evt.getEntity()).getItemInHand().getType());
                            dmg += evt.getDamage();
                            ((Player) E).damage(dmg, evt.getEntity());
                            evt.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    private static double getWeaponDamage(Material weapon) {
        switch (weapon) {
            case WOOD_SWORD:
            case GOLD_SWORD:
                return 5;
            case STONE_SWORD:
                return 6;
            case IRON_SWORD:
                return 7;
            case DIAMOND_SWORD:
                return 8;
            default:
                return 1;
        }
    }

    @Override
    public Skill theSkill() {
        return Skill.Movement.DEATH_FROM_ABOVE;
    }

    @Override
    public ItemStack getItemStack() {
        return null;
    }

    @Override
    public Integer slotNumber() {
        return null;
    }
}
