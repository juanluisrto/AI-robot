package robotAgent;
import environment.Block;
import environment.Map;

public class Main {
	public static void main(String[] args) {
		int width =8;
		int length = 6;
		Map map = new Map(width,length);
		
		int x= width/2;
		int y= length/2;
		
		boolean keepRuning = true;
		for(int i=0 ; i< length && keepRuning; i++){
			for(int j=0; j< width && keepRuning; j++){
				if(map.getBlock(j, i)==Block.TypeOfBlock.FREE){
					x=j;
					y=i;
					keepRuning=false;
				}
			}
		}
		
		AgentsInterfaceForMap agentsInterface = new AgentsInterfaceForMap(map,x,y);
		Agent agent = new Agent(agentsInterface);
		agent.solve();		
	}
}
