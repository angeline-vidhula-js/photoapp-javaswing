
import java.lang.reflect.InvocationTargetException;

import javax.swing.WindowConstants;

public class PhotoApp {
	private static void FrameCreate(String[] args) {
		DrawFrame frame = new DrawFrame();
		if(args.length != 0) frame.setDrawFrame(args);
		
		frame.setTitle("Photo App");
		
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		frame.pack();
		frame.setSize(1000, 600);
		frame.setVisible(true);
	}
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
					FrameCreate(args);
				}
			});
	}

}
