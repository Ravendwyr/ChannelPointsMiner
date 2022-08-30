package fr.raksrinana.channelpointsminer.miner.database;

import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import static java.time.ZoneOffset.UTC;

public class SQLiteDatabase extends BaseDatabase{
	public SQLiteDatabase(HikariDataSource dataSource){
		super(dataSource);
	}
	
	@Override
	public void initDatabase() throws SQLException{
		applyFlyway("db/migrations/sqlite");
	}
	
	@Override
	public void createChannel(@NotNull String channelId, @NotNull String username) throws SQLException{
		try(var conn = getConnection();
				var statement = conn.prepareStatement("""
						INSERT OR IGNORE INTO `Channel`(`ID`, `Username`, `LastStatusChange`)
						VALUES(?, ?, ?);"""
				)){
			
			var timestamp = LocalDateTime.now(UTC);
			
			statement.setString(1, channelId);
			statement.setString(2, username);
			statement.setObject(3, timestamp);
			
			statement.executeUpdate();
		}
	}
	
	@NotNull
	@Override
	protected PreparedStatement getPredictionStmt(@NotNull Connection conn) throws SQLException{
		return conn.prepareStatement("""
				INSERT OR IGNORE INTO `UserPrediction`(`ChannelID`, `UserID`, `Badge`)
				SELECT c.`ID`, ?, ? FROM `Channel` AS c
				WHERE c.`Username`=?"""
		);
	}
	
	@NotNull
	@Override
	protected PreparedStatement getUpdatePredictionUserStmt(@NotNull Connection conn) throws SQLException{
		return conn.prepareStatement("""
				WITH wi AS (SELECT ? AS n)
				UPDATE `PredictionUser`
				SET
				`PredictionCnt`=`PredictionCnt`+1,
				`WinCnt`=`WinCnt`+wi.n,
				`WinRate`=CAST((`WinCnt`+wi.n) AS REAL)/(`PredictionCnt`+1),
				`ReturnOnInvestment`=`ReturnOnInvestment`+?
				FROM wi
				WHERE `ID`=? AND `ChannelID`=?""");
	}
}
