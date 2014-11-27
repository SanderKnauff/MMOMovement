package nl.imine.mmo.movement.zipline;

import org.bukkit.Location;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ZiplineTask extends BukkitRunnable {

    private Minecart pulley;
    private Player player;
    private boolean attached = false;
    private Location hangLoc;

    public ZiplineTask(Player player, Minecart pulley) {
        this.pulley = pulley;
        this.player = player;
        this.attached = true;
        Zipline.addZipline(this);
    }

    @Override
    public void run() {
        if ((!this.attached) || (this.pulley.isDead())) {
            this.pulley.remove();
            Zipline.removeZipline(this);
            cancel();
        }
        
        hangLoc = this.pulley.getLocation();
        if (!(pulley.getVelocity().getX() == 0 && pulley.getVelocity().getZ() == 0) && attached == true) {
            this.hangLoc = pulley.getLocation();
            this.hangLoc.setY(this.hangLoc.getY() - 4.0);
            this.hangLoc.setPitch(0);
            this.hangLoc.setYaw(getRoundYaw(player.getLocation().getYaw()));
            this.player.teleport(hangLoc);
        } else {
            this.attached = false;
            this.pulley.remove();
            Zipline.removeZipline(this);
            cancel();
        }
    }

    public void detach(){
        attached = false;
    }
    
    public Player getPlayer(){
        return this.player;
    }
    
    public static float getRoundYaw(float currentYaw) {
        return (float) Math.round(currentYaw / 90) * 90;
    }

}
