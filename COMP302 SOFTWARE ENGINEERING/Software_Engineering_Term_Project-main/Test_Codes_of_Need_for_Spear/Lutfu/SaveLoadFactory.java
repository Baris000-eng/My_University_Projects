package domain.saveLoad;

public class SaveLoadFactory {
	private static SaveLoadFactory instance;
	
	private SaveLoadFactory() {}
		
	public static SaveLoadFactory getInstance() {
		//MODIFIES: creates an instance of SaveLoadFactory if it is called for the first time, in other words; if the instance in the this.class is null.
		//EFFECTS: returns an instance of SaveLoadFactory.
		
		if (instance == null) {
			instance = new SaveLoadFactory();
		}
		return instance;
	}
	
	public ISaveLoadAdapter getSaveLoadAdapter(String saveLoadUsed)  {
		// EFFECTS: Returns an instance of TxtSaveLoadAdapter if saveLoadUsed == "txt", else returns an instance of DataBaseSaveLoadAdapter.
		ISaveLoadAdapter saveLoad;
		if (saveLoadUsed == "txt")
			saveLoad=  new  TxtSaveLoadAdapter();
		else
			saveLoad=  new DatabaseSaveLoadAdapter();
	
		return saveLoad;
	}

}

