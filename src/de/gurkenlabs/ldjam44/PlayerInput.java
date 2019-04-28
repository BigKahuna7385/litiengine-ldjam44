package de.gurkenlabs.ldjam44;

import java.awt.event.KeyEvent;

import de.gurkenlabs.ldjam44.entities.Enemy;
import de.gurkenlabs.ldjam44.entities.Gatekeeper;
import de.gurkenlabs.ldjam44.entities.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.ICombatEntity;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;

public final class PlayerInput {
  public static void init() {
    Input.keyboard().onKeyPressed(KeyEvent.VK_SPACE, e -> {
      Player.instance().getStrike().cast();
    });

    Input.keyboard().onKeyPressed(KeyEvent.VK_SHIFT, e -> {
      Player.instance().getDash().cast();
    });

    Input.keyboard().onKeyPressed(KeyEvent.VK_E, e -> {
      boolean triggered = false;
      for (ICombatEntity entity : Game.world().environment().findCombatEntities(GeometricUtilities.extrude(Player.instance().getBoundingBox(), 2))) {
        if (entity instanceof Enemy) {
          Enemy enemy = (Enemy) entity;
          enemy.sendMessage(Player.instance(), Enemy.SLAVE_TRIGGER);
          triggered = true;
        }
      }

      if (triggered) {
        return;
      }

      Gatekeeper keeper = GameManager.getGateKeeper();
      if (keeper == null) {
        return;
      }

      keeper.sendMessage(Player.instance(), Gatekeeper.MESSAGE_FINISH);
    });
  }
}
