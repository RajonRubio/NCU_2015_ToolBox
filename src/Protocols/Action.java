package Protocols;

public enum Action {
	UP_PRESS, DOWN_PRESS, RIGHT_PRESS, LEFT_PRESS,
	UP_RELEASE, DOWN_RELEASE, RIGHT_RELEASE, LEFT_RELEASE,
	ATTACK,
	CH_NAME,
	NAME_OK,
	NAME_FAIL,
	CH_TEAM,
	CH_ROLE,
	TEAM_STAT,
	GAME_START,GAME_OVER
}

/* server side
 * (CH_NAME, String )
 * (JOIN, ENUM TEAM)
 * (READY, ENUM ROLE)
 * 
 */

/* client side
 * (TEAM_STAT, TeamState)
 * 
 */
