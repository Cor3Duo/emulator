package Orion.Protocol.Message.Composer.Room.Chat;

import Orion.Api.Server.Game.Habbo.IHabbo;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class HabboWhisperComposer extends MessageComposer {
    public HabboWhisperComposer(IHabbo habbo, String message) {
        super(ComposerHeaders.HabboWhisperComposer);

        appendInt(habbo.getEntity().getVirtualId());
        appendString(message);
        appendInt(0);
        appendInt(habbo.getSettings().getChatColor());
        appendInt(0);
        appendInt(message.getBytes(StandardCharsets.UTF_8).length);
    }

    public HabboWhisperComposer(IHabbo habbo, String message, int gesture, List<String> urls) {
        super(ComposerHeaders.HabboWhisperComposer);

        appendInt(habbo.getEntity().getVirtualId());
        appendString(message);
        appendInt(gesture);
        appendInt(habbo.getSettings().getChatColor());
        appendInt(urls.size());
        for (String url : urls) {
            appendString(url);
        }
        appendInt(message.getBytes(StandardCharsets.UTF_8).length);
    }

    public HabboWhisperComposer(int roomIndex, String message, int gesture, int chatBubbleStyle, List<String> urls) {
        super(ComposerHeaders.HabboWhisperComposer);

        appendInt(roomIndex);
        appendString(message);
        appendInt(gesture);
        appendInt(chatBubbleStyle);
        appendInt(urls.size());
        for (String url : urls) {
            appendString(url);
        }
        appendInt(message.getBytes(StandardCharsets.UTF_8).length);
    }

    public HabboWhisperComposer(int roomIndex, String message, int chatBubbleStyle) {
        super(ComposerHeaders.HabboWhisperComposer);

        appendInt(roomIndex);
        appendString(message);
        appendInt(0);
        appendInt(chatBubbleStyle);
        appendInt(0);
        appendInt(message.getBytes(StandardCharsets.UTF_8).length);
    }
}
