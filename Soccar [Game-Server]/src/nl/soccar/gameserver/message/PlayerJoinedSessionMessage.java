package nl.soccar.gameserver.message;

import nl.soccar.gameserver.message.handler.PlayerJoinedSessionMessageHandler;
import nl.soccar.library.Player;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageEvent;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = 3, handler = PlayerJoinedSessionMessageHandler.class)
public final class PlayerJoinedSessionMessage extends Message {

    private final Player player;
    private final TeamColour team;

    public PlayerJoinedSessionMessage(Player player, TeamColour team) {
        this.player = player;
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public TeamColour getTeam() {
        return team;
    }

    @Override
    public int getId() {
        return 3;
    }

}
