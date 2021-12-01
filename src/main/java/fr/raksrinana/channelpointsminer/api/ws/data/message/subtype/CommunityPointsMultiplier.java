package fr.raksrinana.channelpointsminer.api.ws.data.message.subtype;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.raksrinana.channelpointsminer.api.gql.data.types.MultiplierReasonCode;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class CommunityPointsMultiplier{
	@JsonProperty("reason_code")
	@NotNull
	private MultiplierReasonCode reasonCode;
	@JsonProperty("factor")
	private float factor;
}