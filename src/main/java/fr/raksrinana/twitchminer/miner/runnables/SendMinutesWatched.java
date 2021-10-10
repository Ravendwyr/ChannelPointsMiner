package fr.raksrinana.twitchminer.miner.runnables;

import fr.raksrinana.twitchminer.api.gql.data.types.Game;
import fr.raksrinana.twitchminer.api.twitch.MinuteWatchedProperties;
import fr.raksrinana.twitchminer.api.twitch.MinuteWatchedRequest;
import fr.raksrinana.twitchminer.miner.IMiner;
import fr.raksrinana.twitchminer.miner.data.Streamer;
import fr.raksrinana.twitchminer.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
public class SendMinutesWatched implements Runnable{
	private static final String SITE_PLAYER = "site";
	
	@NotNull
	private final IMiner miner;
	
	@Override
	public void run(){
		log.debug("Sending minutes watched");
		try{
			var toSendMinutesWatched = miner.getStreamers().stream()
					.filter(Streamer::isStreaming)
					.filter(streamer -> Objects.nonNull(streamer.getSpadeUrl()))
					.limit(2)
					.toList();
			
			for(var streamer : toSendMinutesWatched){
				send(streamer);
				CommonUtils.randomSleep(100, 50);
			}
			
			log.debug("Done sending minutes watched");
		}
		catch(Exception e){
			log.error("Failed to send minutes watched", e);
		}
	}
	
	private void send(Streamer streamer){
		log.debug("Sending minutes watched for {}", streamer);
		var request = new MinuteWatchedRequest(MinuteWatchedProperties.builder()
				.channelId(streamer.getId())
				.broadcastId(streamer.getStreamId().orElse(null))
				.player(SITE_PLAYER)
				.userId(miner.getTwitchLogin().getUserId())
				.game(streamer.getGame().map(Game::getName).orElse(null))
				.build());
		
		miner.getTwitchApi().sendMinutesWatched(streamer.getSpadeUrl(), request);
	}
}