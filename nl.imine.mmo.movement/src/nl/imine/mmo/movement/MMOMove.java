/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.mmo.movement;

import org.bukkit.plugin.java.JavaPlugin;
import nl.imine.mmo.movement.glider.Glider;
import nl.imine.mmo.movement.jumpkill.JumpKill;
import nl.imine.mmo.movement.leap.Leap;
import nl.imine.mmo.movement.roll.Roll;
import nl.imine.mmo.movement.zipline.Zipline;

/**
 *
 * @author Sander
 */
public class MMOMove extends JavaPlugin {

    @Override
    public void onEnable(){
        Leap.init();
        Zipline.init();
        Roll.init();
        JumpKill.init();
        Glider.init();
    }
}
