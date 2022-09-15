package fr.raksrinana.channelpointsminer.miner.api.ws.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.raksrinana.channelpointsminer.miner.api.ws.data.request.topic.Topics;
import fr.raksrinana.channelpointsminer.miner.util.CommonUtils;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

@Getter
public class ListenTopicRequest extends ITwitchWebSocketRequest{
	private static final int NONCE_LENGTH = 30;
	
	@JsonProperty("nonce")
	private String nonce;
	@JsonProperty("data")
	private Topics data;
	
	public ListenTopicRequest(@NotNull Topics topics){
		super("LISTEN");
		data = topics;
		nonce = CommonUtils.randomAlphanumeric(NONCE_LENGTH);
	}
}
