package domain.obstacles;




public class ObstacleFactory {

	private static ObstacleFactory instance;
	//private static int id=1;

	private ObstacleFactory() { }

	public static ObstacleFactory getInstance() {
		if (instance == null) {
			instance = new ObstacleFactory();
		}
		return instance;
	}


	public Obstacle getObstacle(String obstacleType, int x, int y)  {
		if(obstacleType.equalsIgnoreCase("SimpleObstacle")) {
			return new SimpleObstacle(obstacleType, x, y);
		} else if(obstacleType.equalsIgnoreCase("FirmObstacle")) {
			Obstacle firmObs=new FirmObstacle(obstacleType, x, y);
			return firmObs;
		} else if(obstacleType.equalsIgnoreCase("ExplosiveObstacle")) {
			return new ExplosiveObstacle(obstacleType, x, y);
		} else if(obstacleType.equalsIgnoreCase("GiftObstacle")) {
			return new GiftObstacle(obstacleType, x, y);
		}  else if(obstacleType.equalsIgnoreCase("HollowObstacle")) {
			return new HollowObstacle(obstacleType, x, y);
		} 

		return null;
	}

	public Obstacle getFirmObstacleWithNumHits( int x, int y, int numHits)  {

		return new FirmObstacle(x, y, numHits);

	}

	/*public Obstacle getRandomObstacle(int id, String obstacleType)  {

		Random random = new Random();
		int locX= random.nextInt((MainFrame.width-MainFrame.widthButton/2-255-MainFrame.width/50 - 8*MainFrame.width/100) + 1) + 8*MainFrame.width/100;
		int locY= random.nextInt((64*MainFrame.height/100 - 19*MainFrame.height/100) + 1) + MainFrame.heightButton;

		if(obstacleType.equalsIgnoreCase("SimpleObstacle")) {
			return new SimpleObstacle(id, obstacleType, locX, locY);
		} else if(obstacleType.equalsIgnoreCase("FirmObstacle")) {
			return new FirmObstacle(id,obstacleType, locX, locY);
		} else if(obstacleType.equalsIgnoreCase("ExplosiveObstacle")) {
			return new ExplosiveObstacle(id,obstacleType, locX, locY);
		} else if(obstacleType.equalsIgnoreCase("GiftObstacle")) {
			return new GiftObstacle(id, obstacleType, locX, locY);
		} 

		return null;
	}*/


	
	



}
