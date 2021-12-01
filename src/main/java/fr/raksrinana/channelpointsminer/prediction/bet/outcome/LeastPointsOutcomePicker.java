package fr.raksrinana.channelpointsminer.prediction.bet.outcome;

import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.raksrinana.channelpointsminer.api.ws.data.message.subtype.Outcome;
import fr.raksrinana.channelpointsminer.handler.data.BettingPrediction;
import fr.raksrinana.channelpointsminer.prediction.bet.BetPlacementException;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import java.util.Comparator;

@JsonTypeName("leastPoints")
@Getter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@Log4j2
public class LeastPointsOutcomePicker implements IOutcomePicker{
	@Override
	@NotNull
	public Outcome chooseOutcome(@NotNull BettingPrediction bettingPrediction) throws BetPlacementException{
		return bettingPrediction.getEvent().getOutcomes().stream()
				.min(Comparator.comparingLong(Outcome::getTotalPoints))
				.orElseThrow(() -> new BetPlacementException("Couldn't get outcome with least points"));
	}
}