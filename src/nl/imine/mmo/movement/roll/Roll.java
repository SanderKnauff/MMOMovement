/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.mmo.movement.roll;

import nl.makertim.MMOmain.PlayerStats;
import nl.makertim.MMOmain.Refrence;
import nl.makertim.MMOmain.lib.InventoryCleanupEvent;
import nl.makertim.MMOmain.lib.MMOOutlaws;
import nl.makertim.MMOmain.skill.Skill;
import nl.makertim.MMOmain.skill.SkillAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Sander
 */
public class Roll extends SkillAction {

    private static Plugin plugin;

    public static void init() {
        MMOOutlaws.getInstance().addSkillHandler(Roll.class);
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
                if (evt.getCause().equals(DamageCause.FALL)) {
                    Player pl = (Player) evt.getEntity();
                    if (pl.isSneaking()) {
                        pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Refrence.random.nextInt((int) (evt.getDamage() * 40 + 1)), 0, false));
                        evt.setDamage(evt.getDamage() / 2);
                    }
                }
            }
        }
    }

    @Override
    public Skill theSkill() {
        return Skill.Movement.CROUCH_NO_DAMMAGE;
    }
    
    @Override
    public void onInventoryClean(InventoryCleanupEvent ice) {
        
    }

}
