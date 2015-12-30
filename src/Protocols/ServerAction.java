package Protocols;

public enum ServerAction {
	UP_PRESS, DOWN_PRESS, RIGHT_PRESS, LEFT_PRESS,
	UP_RELEASE, DOWN_RELEASE, RIGHT_RELEASE, LEFT_RELEASE,
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
