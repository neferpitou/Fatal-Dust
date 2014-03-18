package interfaces;

public interface Character 
{
	//positions (18 of them)
	final static int RIGHT_WALK = 0;
	final static int LEFT_WALK = 1;
	
	final static int RIGHT_JUMP = 2;
	final static int LEFT_JUMP = 3;
	
	final static int RIGHT_PUNCH = 4;
	final static int LEFT_PUNCH= 5;
	
	final static int RIGHT_JUMP_BACK = 6;
	final static int LEFT_JUMP_BACK = 7;
	
	final static int RIGHT_DUCK = 8;
	final static int LEFT_DUCK = 9;
	
	final static int RIGHT_KICK = 10;
	final static int LEFT_KICK = 11;
	
	final static int RIGHT_KICK_DUCKING = 12;
	final static int LEFT_KICK_DUCKING = 13;
	
	final static int RIGHT_PUNCH_DUCKING = 14;
	final static int LEFT_PUNCH_DUCKING = 15;
	
	final static int RIGHT_ALT_PUNCH = 16;
	final static int LEFT_ALT_PUNCH = 17;
	
	
	//correspond to above positions
	final static String[] POSITIONS = { "rtw" , "ltw" , "rtj" , "ltj", "rtjb" , "ltjb" , "rtd" , "ltd", "rtk" , "ltk", "rtkd", "ltkd",
										"rtpd" , "ltpd" , "rtap", "ltap" };
	
	
	public void punch();
	
	public void kick();
	
	public void jump();
	
	public void duck();
	
	public void decreaseHealthBy(int dHealth);
	
	public void lookLeft();
	
	public void lookRight();

}
