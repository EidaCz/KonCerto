package cz.eida.minecraft.koncerto.commands.selectors;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerSelector {

    /**
     * command sender
     */
    private CommandSender commandSender;
    /**
     * query
     */
    private String selector;

    private List<Player> selection;

    public PlayerSelector(CommandSender sender, String selector) {
        this.commandSender = sender;
        this.selector = selector;
    }

    public List<Player> getSelection() {

        if (Selectors.all().containsKey(selector)) {

            switch (Selectors.all().get(selector)) {
                case ALL -> selection = getAllServerPlayers();
                case SELF -> selection = getSinglePlayer(((Player) commandSender).getName());
                case WORLD -> selection = getPlayersInWorld(((Player) commandSender).getWorld());
                case RAND -> selection = getRandomPlayer();
                default -> selection = null;
            }

        } else {
            selection = getSinglePlayer(selector);
        }

        return selection;
    }

    /**
     * List of all online players.
     *
     * @return list of players
     */
    public List<Player> getAllServerPlayers() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

    /**
     * Get random player from all online players.
     *
     * @return list with one random player
     */
    public List<Player> getRandomPlayer() {

        Random randomizer = new Random();
        int randomIndex = randomizer.nextInt(getAllServerPlayers().size());

        List<Player> randomPlayer = new ArrayList<>();
        randomPlayer.add(getAllServerPlayers().get(randomIndex));

        return randomPlayer;
    }

    /**
     * Get all players in specified world.
     *
     * @param world world
     * @return list of players in world
     */
    private List<Player> getPlayersInWorld(World world) {
        List<Player> playersInWorld = new ArrayList<>();

        for (final Player p : getAllServerPlayers()) {
            if (p.getWorld().equals(world)) {
                playersInWorld.add(p);
            }
        }

        return playersInWorld;
    }

    private List<Player> getSinglePlayer(String playerName) {
        List<Player> player = new ArrayList<>();

        for (final Player p : getAllServerPlayers()) {
            if (p.getName().toLowerCase().equals(playerName.toLowerCase())) {
                player.add(p);
                break;
            }
        }

        return (player.size() == 1) ? player : null;
    }

}
