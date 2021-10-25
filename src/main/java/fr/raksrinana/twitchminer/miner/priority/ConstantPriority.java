package fr.raksrinana.twitchminer.miner.priority;

import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.raksrinana.twitchminer.miner.streamer.Streamer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@JsonTypeName("constant")
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class ConstantPriority extends StreamerPriority{
	@Override
	public int getScore(@NotNull Streamer streamer){
		return getScore();
	}
}