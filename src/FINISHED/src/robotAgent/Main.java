package robotAgent;
import environment.Map;

public class Main {
	public static void main(String[] args) {

		Map map = new Map();
		
		int x= map.width/2;
		int y= map.length/2;
		
		boolean keepRuning = true;
		for(int i=0 ; i< map.length && keepRuning; i++){
			for(int j=0; j< map.width && keepRuning; j++){
				if(map.getBlock(j, i)==Map.TypeOfBlock.FREE){
					x=j;
					y=i;
					keepRuning=false;
				}
			}
		}
		
		Thread thread = new Thread(new MapPainter(map));
		thread.start();
		
		AgentsInterfaceForMap agentsInterface = new AgentsInterfaceForMap(map,x,y);
		Agent agent = new Agent(agentsInterface);
		agent.solve();		
	}
	
	public static class MapPainter implements Runnable {
		Map map;
		public MapPainter(Map map) {
			this.map = map;
		}
		
		@Override
		public void run() {
			while (true){	
				map.paintObst();

				map.paintVisited();
				
				map.paintAgent();
				map.v.actualizaFotograma();
				/*try {m.v.wait(l);}
				catch (Exception e){*/
			}
		}
	}
}
