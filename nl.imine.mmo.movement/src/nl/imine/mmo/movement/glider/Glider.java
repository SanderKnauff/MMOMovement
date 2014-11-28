/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.mmo.movement.glider;

import nl.imine.mmo.movement.leap.Leap;
import nl.makertim.MMOmain.PlayerStats;
import nl.makertim.MMOmain.Skill;
import nl.makertim.MMOmain.lib.MMOOutlaws;
import nl.makertim.MMOmain.lib.SkillAction;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 *
 * @author Sander
 */
public class Glider extends SkillAction{
    
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
    public void onPlayerUseDye(PlayerInteractEvent E) {
        if ((E.getAction().equals(Action.RIGHT_CLICK_AIR)) && (E.getItem().getType() != null)
                && (E.getItem().getType().equals(Material.FEATHER))) {
            Entity chickenGlider = E.getPlayer().getWorld().spawnEntity(E.getPlayer().getLocation(), EntityType.CHICKEN);
            chickenGlider.setPassenger(E.getPlayer());
            new GliderTask(E.getPlayer(), chickenGlider).runTaskTimer(plugin, 1L, 1L);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent E) {
        if ((E.getEntity() instanceof Player)) {
            Player player = (Player) E.getEntity();
            player.leaveVehicle();
        }
        if ((E.getEntity().getType().equals(EntityType.CHICKEN)) && (E.getEntity().getPassenger() != null)) {
            E.getEntity().eject();
        }
    }

    @Override
    public Skill theSkill() {
        return Skill.Movement.HANGGLIDER;
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
