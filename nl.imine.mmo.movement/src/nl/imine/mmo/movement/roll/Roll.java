/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.mmo.movement.roll;

import nl.imine.mmo.movement.leap.Leap;
import nl.makertim.MMOmain.Refrence;
import nl.makertim.MMOmain.Skill;
import nl.makertim.MMOmain.lib.MMOOutlaws;
import nl.makertim.MMOmain.lib.SkillAction;
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
        MMOOutlaws.getInstance().addSkillHandler(Leap.class);
    }

    @Override
    public void registerEvents(PluginManager pm, Plugin pl) {
        super.registerEvents(pm, pl);
        plugin = pl;
    }

    @EventHandler
    public void onPlayerDammage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause().equals(DamageCause.FALL)) {
                Player pl = (Player) e.getEntity();
                if (pl.isSneaking()) {
                    pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Refrence.random.nextInt((int) (e.getDamage() * 40 + 1)), 0, false));
                    e.setDamage(e.getDamage() / 2);
                }
            }
        }
    }

    @Override
    public Skill theSkill() {
        return Skill.Movement.CROUCH_NO_DAMMAGE;
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
