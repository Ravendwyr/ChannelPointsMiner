package fr.raksrinana.channelpointsminer.prediction.delay;

import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.raksrinana.channelpointsminer.api.ws.data.message.subtype.Event;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import java.time.ZonedDateTime;

@JsonTypeName("percentage")
@Getter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class PercentageDelay implements IDelayCalculator{
	private float percent;
	
	@Override
	@NotNull
	public ZonedDateTime calculate(@NotNull Event event){
		return event.getCreatedAt().plusSeconds((long) (percent * event.getPredictionWindowSeconds()));
	}
}