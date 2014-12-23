/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.mmo.movement.jumpkill;

import nl.makertim.MMOmain.PlayerStats;
import nl.makertim.MMOmain.lib.InventoryCleanupEvent;
import nl.makertim.MMOmain.lib.MMOOutlaws;
import nl.makertim.MMOmain.mission.Mission;
import nl.makertim.MMOmain.skill.Skill;
import nl.makertim.MMOmain.skill.SkillAction;
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
        MMOOutlaws.getInstance().addSkillHandler(JumpKill.class);
    }

    @Override
    public void registerEvents(PluginManager pm, Plugin pl) {
        super.registerEvents(pm, pl);
        plugin = pl;
    }

    @EventHandler
    public void onPlayerDammage(EntityDamageEvent evt) {
        if (evt.getEntity() instanceof Player) {
            if (gotSkill((Player) evt.getEntity())) {
                if (evt.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    if (evt.getDamage() != 0) {
                        for (Entity E : evt.getEntity().getNearbyEntities(2, 2, 2)) {
                            if (E instanceof Player) {
                                if (Mission.getMissionFromPlayer((Player) evt.getEntity()) != null) {
                                    if (Mission.getMissionFromPlayer((Player) evt.getEntity()).equals(Mission.getMissionFromPlayer((Player) E))) {
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
    public void onInventoryClean(InventoryCleanupEvent ice) {

    }

}
