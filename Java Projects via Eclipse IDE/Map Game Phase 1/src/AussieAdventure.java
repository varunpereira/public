import java.awt.Color;

public class AussieAdventure {

	public static void main(String[] args) {
		GTerm gt=new GTerm(670,600);
		
		gt.setXY(300, 50);
		gt.print("Darwin");
		gt.setXY(50, 375);
		gt.print("Perth");
		gt.setXY(50, 375);
		gt.setXY(500, 480);
		gt.print("Melbourne");
		gt.setXY(50, 375);
		gt.setXY(0, 0);
		gt.addImageIcon("Australia_relief_map.jpg");
		
		String destination = gt.getInputString("Enter name of city, to visit.");
		
		if (destination.equalsIgnoreCase("Darwin")) {
			gt.clear();
			
			gt.setFontColor(Color.RED);
			gt.setXY(300, 50);
			gt.print("Darwin");
			
			gt.setFontColor(Color.BLACK);
			gt.setXY(50, 375);
			gt.print("Perth");
			
			gt.setFontColor(Color.BLACK);
			gt.setXY(500, 480);
			gt.print("Melbourne");
			
			gt.setXY(0, 0);
			gt.addImageIcon("Australia_relief_map.jpg");
			
		}
			
		if (destination.equalsIgnoreCase("Perth")) {
			gt.clear();
				
			gt.setFontColor(Color.BLACK);
			gt.setXY(300, 50);
			gt.print("Darwin");
				
			gt.setFontColor(Color.RED);
			gt.setXY(50, 375);
			gt.print("Perth");
				
			gt.setFontColor(Color.BLACK);
			gt.setXY(500, 480);
			gt.print("Melbourne");
				
			gt.setXY(0, 0);
			gt.addImageIcon("Australia_relief_map.jpg");
			
		}
		
		if (destination.equalsIgnoreCase("Melbourne")) {
			gt.clear();
					
			gt.setFontColor(Color.BLACK);
			gt.setXY(300, 50);
			gt.print("Darwin");
					
			gt.setFontColor(Color.BLACK);
			gt.setXY(50, 375);
			gt.print("Perth");
					
			gt.setFontColor(Color.RED);
			gt.setXY(500, 480);
			gt.print("Melbourne");
					
			gt.setXY(0, 0);
			gt.addImageIcon("Australia_relief_map.jpg");				 			
			 
		}
	}
}
	