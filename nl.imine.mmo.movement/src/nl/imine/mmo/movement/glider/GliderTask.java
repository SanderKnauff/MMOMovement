package nl.imine.mmo.movement.glider;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GliderTask extends BukkitRunnable {

    private Entity glider;
    private Player player;
    private int fireTime;

    public GliderTask(Player player, Entity glider) {
        this.glider = glider;
        this.player = player;
    }

    public void run() {
        if ((this.glider.getPassenger() == null) || (this.glider.isDead())) {
            this.glider.remove();
            cancel();
        }
        Location gliderFeetLoc = this.glider.getLocation();
        if ((gliderFeetLoc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) || (gliderFeetLoc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.FIRE))) {
            double x = this.player.getLocation().getDirection().getX();
            double y = this.glider.getVelocity().getY();
            double z = this.player.getLocation().getDirection().getZ();
            if (hasFireBelow(gliderFeetLoc.getBlock())) {
                this.fireTime = 20;
            }
            if (this.fireTime > 0) {
                this.glider.setVelocity(new Vector(x * 0.5D, this.fireTime * 0.1D, z * 0.5D));
                this.fireTime -= 1;
            } else {
                this.glider.setVelocity(new Vector(x * 0.5D, y, z * 0.5D));
            }
        } else {
            this.glider.eject();
            this.glider.remove();
            cancel();
        }
    }

    private boolean hasFireBelow(Block block) {
        Location checkLoc = block.getLocation();
        for (int i = 0; i < 3; i++) {
            checkLoc.setY(checkLoc.getY() - i);
            if (checkLoc.getBlock().getType().equals(Material.FIRE)) {
                return true;
            }
        }
        return false;
    }
}
