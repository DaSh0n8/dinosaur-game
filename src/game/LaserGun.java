package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.WeaponItem;

public class LaserGun extends WeaponItem {
    public LaserGun() {
        super("Laser Gun", 'L', 100, "Shoot");
        addCapability(ItemType.LASERGUN);
    }
}
