
import javax.swing.WindowConstants;

public class PhotoApp {
	private static void FrameCreate() {
		DrawFrame frame = new DrawFrame();
		
		frame.setTitle("Photo App");
		
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		frame.pack();
		frame.setSize(1000, 600);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
					FrameCreate();
				}
			});
	}

}
