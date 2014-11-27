package nl.imine.mmo.movement.zipline;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sander
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import nl.makertim.MMOmain.Skill;
import nl.makertim.MMOmain.lib.MMOOutlaws;
import nl.makertim.MMOmain.lib.SkillAction;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 *
 * @author Sander
 */
public class Zipline extends SkillAction {

    private static Plugin plugin;

    private static List<ZiplineTask> ziplineList;

    public static void init() {
        MMOOutlaws.getInstance().addSkillHandler(Zipline.class);
    }

    @Override
    public void registerEvents(PluginManager pm, Plugin pl) {
        super.registerEvents(pm, pl);
        plugin = pl;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent E) {
        if (E.getAction().equals(Action.RIGHT_CLICK_BLOCK) && E.getClickedBlock().getType().equals(Material.RAILS) && (E.getItem().getType() != null)) {
            Minecart pulley = (Minecart) E.getClickedBlock().getWorld().spawnEntity(E.getClickedBlock().getLocation(), EntityType.MINECART);
            pulley.setSlowWhenEmpty(false);
            pulley.setVelocity(E.getPlayer().getLocation().getDirection());
            new ZiplineTask(E.getPlayer(), pulley).runTaskTimer(plugin, 1L, 1L);
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent E) {
        ZiplineTask zTask = Zipline.getPlayerZipline((Player) E.getPlayer());
        if (zTask != null) {
            zTask.detach();
        }

    }

    @EventHandler
    public void onPlayerDeath(EntityDamageEvent E) {
        if (E.getEntity() instanceof Player) {
            ZiplineTask zTask = Zipline.getPlayerZipline((Player) E.getEntity());
            if (zTask != null) {
                zTask.detach();
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent E) {
        ZiplineTask zTask = Zipline.getPlayerZipline(E.getEntity());
        if (zTask != null) {
            zTask.detach();
        }
    }

    public static ZiplineTask getPlayerZipline(Player player) {
        for (ZiplineTask z : ziplineList) {
            if (player.equals(z.getPlayer())) {
                return z;
            }
        }
        return null;
    }

    public static void addZipline(ZiplineTask zTask) {
        ziplineList.add(zTask);
    }

    public static void removeZipline(ZiplineTask zTask) {
        ziplineList.remove(zTask);
    }

    @Override
    public Skill theSkill() {
        return Skill.Movement.ZIP_LINE;
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
