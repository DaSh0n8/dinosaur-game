package game.item;

import edu.monash.fit2099.engine.WeaponItem;
import game.enumeration.ItemType;

public class LaserGun extends WeaponItem {
    public LaserGun() {
        super("Laser Gun", 'l', 100, "Shoot");
        addCapability(ItemType.LASERGUN);
    }
}
