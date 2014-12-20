package nl.imine.mmo.movement.zipline;
/**
 *
 * @author Sander
 */

import java.util.ArrayList;
import java.util.List;
import nl.makertim.MMOmain.lib.InventoryCleanupEvent;
import nl.makertim.MMOmain.lib.MMOOutlaws;
import nl.makertim.MMOmain.skill.Skill;
import nl.makertim.MMOmain.skill.SkillAction;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

/**
 *
 * @author Sander
 */
public class Zipline extends SkillAction {

    private static Plugin plugin;

    private static List<ZiplineTask> ziplineList;

    public static void init() {
        ziplineList = new ArrayList<>();
        MMOOutlaws.getInstance().addSkillHandler(Zipline.class);
    }

    @Override
    public void registerEvents(PluginManager pm, Plugin pl) {
        super.registerEvents(pm, pl);
        plugin = pl;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt) {
        if (gotSkill(evt.getPlayer())) {
            if (evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) && evt.getClickedBlock().getType().equals(Material.RAILS) && getPlayerZipline(evt.getPlayer()) == null) {
                Minecart pulley = (Minecart) evt.getClickedBlock().getWorld().spawnEntity(evt.getClickedBlock().getLocation(), EntityType.MINECART);
                pulley.setSlowWhenEmpty(false);
                pulley.setVelocity(evt.getPlayer().getLocation().getDirection());
                new ZiplineTask(evt.getPlayer(), pulley).runTaskTimer(plugin, 1L, 1L);
            }
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent E) {
        ZiplineTask zTask = Zipline.getPlayerZipline((Player) E.getPlayer());
        if (zTask != null) {
            Vector v = zTask.getPully().getVelocity();
            zTask.detach();
            E.getPlayer().setVelocity(v);
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
    public void onInventoryClean(InventoryCleanupEvent ice) {
        
    }
}
