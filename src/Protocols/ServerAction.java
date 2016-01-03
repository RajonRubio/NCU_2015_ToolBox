package Protocols;

public enum ServerAction {
	UP_PRESS, DOWN_PRESS, RIGHT_PRESS, LEFT_PRESS, STANDING,
	ATTACK,
	
	CH_NAME,
	CH_TEAM,
	CH_ROLE,
	READY
}

/* server side
 * (CH_NAME, String )
 * (CH_TEAM, ENUM TEAM)
 * (CH_ROLE, ENUM ROLE)
 * 
 */
