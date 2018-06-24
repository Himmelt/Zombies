package org.soraworld.zombies.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import rikka.RikkaAPI;

public class ScoreBoards {

    private Scoreboard killsBoard;
    private Objective killObjective;
    private int displaySlot = 3;

    public ScoreBoards() {
        if (RikkaAPI.BUKKIT) {
            killsBoard = Bukkit.getScoreboardManager().getNewScoreboard();
            killObjective = killsBoard.getObjective(DisplaySlot.SIDEBAR);
            if (killObjective == null) {
                killObjective = killsBoard.registerNewObjective("kills", "dummy");
            }
        }
    }

    public void update(String name, int kill) {
        Player player = Bukkit.getPlayer(name);
        if (player != null) {
            if (displaySlot == 0) {
                player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            } else {
                killObjective.getScore(name).setScore(kill);
                player.setScoreboard(killsBoard);
            }
        }
    }

    public void setDisplaySlot(int slot) {
        displaySlot = slot;
        switch (slot) {
            case 1:
                killObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
                break;
            case 2:
                killObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
                break;
            default:
                killObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }

    public void setDisplayName(String display) {
        killObjective.setDisplayName(display);
    }
}
