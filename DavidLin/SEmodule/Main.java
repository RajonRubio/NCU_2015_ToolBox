package SEmodule;

public class Main {

	public static void main(String[] args) {
		SceneDataModule sdm = new SceneDataModule();
		sdm.loadMap("map.txt");
		SceneRenderEngine sre = new SceneRenderEngine(DOM);
		
		Frame frame = new Frame();
	}
	
}
