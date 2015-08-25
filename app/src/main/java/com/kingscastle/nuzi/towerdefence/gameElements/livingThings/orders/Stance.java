package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.orders;

public enum Stance
{

	FREE  , HOLD_POSITION , GUARD_LOCATION , PLAYING_THE_OBJECTIVE;

	public Stance getNext()
	{
		return getNext( this );
	}

	private static Stance getNext(Stance current)
	{
		switch( current )
		{
		default:
		case FREE: return GUARD_LOCATION;
		//	case HOLD_POSITION: return GUARD_LOCATION;
		case GUARD_LOCATION: return FREE;
		}
	}
}
