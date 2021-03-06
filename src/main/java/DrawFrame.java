import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class DrawFrame extends JFrame {

	static final String[] EXTENSIONS = new String[]{
	        "gif", "png", "bmp" , "jpeg", "jpg"
	    };
	
	static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };

	
	ArrayList<File> allImgFiles = new ArrayList<>();
	JLabel label = new JLabel();
	JScrollPane scroller = new JScrollPane(label); 
	JMenuItem impor = new JMenuItem("Import");
	
	public DrawFrame() {
	
		this.setLayout(new BorderLayout()); 
		this.setSize(new Dimension(400, 300));

		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");    
		menuBar.add(file);
		file.add(impor);
		impor.putClientProperty( "page", false);
		JMenuItem dele = new JMenuItem("Delete");
		file.add(dele);
		JMenuItem exi = new JMenuItem("Exit");
		file.add(exi);
		JMenu view = new JMenu("View");    
		menuBar.add(view);
		JMenuItem photo = new JMenuItem("Photo");
		view.add(photo);
		JMenuItem grid = new JMenuItem("Grid");
		view.add(grid);
		this.setJMenuBar(menuBar);
		 
		
		
		
		JPanel tools = new JPanel();
		
		
		JButton prev = new JButton("Previous");
		JButton nex = new JButton("Next");
		JButton del = new JButton("Delete");
		
		JPanel bgroup = new JPanel();
        bgroup.add(prev);
        bgroup.add(nex);
        bgroup.add(del);
        
       
        
        bgroup.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JRadioButton pen = new JRadioButton("Pen");
        JRadioButton txt = new JRadioButton("Text");
		
		JPanel bgroup2 = new JPanel();
		
        bgroup2.add(pen);
        bgroup2.add(txt);
        
        ButtonGroup grpRadio = new ButtonGroup();
        grpRadio.add(pen);
        grpRadio.add(txt);
        pen.setPreferredSize(new Dimension(150, 200));
        
        bgroup2.setBorder(new CompoundBorder(new TitledBorder(""), new EmptyBorder(8, 0, 8, 0)));
        bgroup2.setLayout(new BoxLayout (bgroup2, BoxLayout.Y_AXIS));
        

        this.add(BorderLayout.PAGE_START, bgroup);
        tools.add(bgroup2);
        
        
        ButtonGroup chck = new ButtonGroup();
        JCheckBox vacay = new JCheckBox("Vacation");
        JCheckBox fam = new JCheckBox("Family");
        JCheckBox skl = new JCheckBox("School");
        JCheckBox work = new JCheckBox("Work");
        chck.add(vacay);
        chck.add(fam);
        chck.add(skl);
        chck.add(work);
        
        JPanel bgroup3 = new JPanel();
        
        bgroup3.setBorder(new CompoundBorder(new TitledBorder(""), new EmptyBorder(8, 0, 8, 0)));
        bgroup3.setLayout(new BoxLayout (bgroup3, BoxLayout.Y_AXIS));
        bgroup3.add(vacay);
        bgroup3.add(fam);
        bgroup3.add(skl);
        bgroup3.add(work);
        tools.add(bgroup3);
        
        
        tools.setLayout(new BoxLayout (tools, BoxLayout.Y_AXIS));
		
		
		this.add(BorderLayout.LINE_START, tools);
		
		

		
		label.setHorizontalAlignment(JLabel.CENTER);
		label.putClientProperty("filesExist", false);
		
		this.add(BorderLayout.CENTER, scroller);
	
		
		
		
		scroller.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		        
		        if((boolean)label.getClientProperty("filesExist")) {
		        
			        int i = (int) label.getClientProperty("index");
	    			ImageIcon img = new ImageIcon(allImgFiles.get(i).getAbsolutePath());
	    			Image scales = img.getImage().getScaledInstance(-1, scroller.getViewport().getSize().height, Image.SCALE_SMOOTH);
			        label.setIcon(new ImageIcon(scales));
		        }
		    }
		});
		
		
		impor.addActionListener(new ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		    	JFileChooser fileChooser = new JFileChooser();
		    	fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		    	int result = fileChooser.showOpenDialog(menuBar);
		    	if (result == JFileChooser.APPROVE_OPTION) {
		    		File selectedFile = fileChooser.getSelectedFile();
		    		allImgFiles.addAll(fileAdder(selectedFile));
		    	}

    	    	if(allImgFiles.size() != 0) {
    	    		boolean page = (boolean)((JMenuItem)evt.getSource()).getClientProperty( "page" );
    	    		if(!page) {
    	    			impor.putClientProperty( "page" , true);
    	    			ImageIcon img = new ImageIcon(allImgFiles.get(0).getAbsolutePath());
    	    			Image scales = img.getImage().getScaledInstance(-1, scroller.getViewport().getSize().height, Image.SCALE_SMOOTH);
    			        label.setIcon(new ImageIcon(scales));
    			        label.putClientProperty("index" , 0);
    	    		}
    	    		label.putClientProperty("filesExist", true);
    	    	}
		    }
		    
		});
		

		nex.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				int picidx = (int) label.getClientProperty("index" );
				if(picidx+1 < allImgFiles.size()) {
					picidx = picidx+1;
	    			ImageIcon img = new ImageIcon(allImgFiles.get(picidx).getAbsolutePath());
	    			Image scales = img.getImage().getScaledInstance(-1, scroller.getViewport().getSize().height, Image.SCALE_SMOOTH);
			        label.setIcon(new ImageIcon(scales));
					label.putClientProperty("index" , picidx);
				}
			}
		});
		
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				int picidx = (int) label.getClientProperty("index" );
				if(picidx-1 >= 0) {
					picidx = picidx-1;
	    			ImageIcon img = new ImageIcon(allImgFiles.get(picidx).getAbsolutePath());
	    			Image scales = img.getImage().getScaledInstance(-1, scroller.getViewport().getSize().height, Image.SCALE_SMOOTH);
			        label.setIcon(new ImageIcon(scales));
					label.putClientProperty("index" , picidx);
				}
			}
		});
		
	}
	
	public DrawFrame getDrawFrame()
    {
    	return this;
    }
	
	public void setDrawFrame(String[] args)
    {
    	Path path = Paths.get(args[0]);
    	if(Files.exists(path)) {
    		allImgFiles.addAll(fileAdder(new File(args[0])));
    	}
			impor.putClientProperty( "page" , true);
			ImageIcon img = new ImageIcon(allImgFiles.get(0).getAbsolutePath());
			int height = scroller.getViewport().getSize().height == 0 ? 300 : scroller.getViewport().getSize().height;
			Image scales = img.getImage().getScaledInstance(-1, height, Image.SCALE_SMOOTH);
	        label.setIcon(new ImageIcon(scales));
	        label.putClientProperty("index" , 0);
		
		label.putClientProperty("filesExist", true);
    	
    }
	
	static ArrayList fileAdder (File selectedFile) {
		ArrayList<File> imgFiles = new ArrayList<>();
		boolean exists =      selectedFile.exists();      
		boolean isDirectory = selectedFile.isDirectory(); 
		boolean isFile =      selectedFile.isFile();      
	    if(exists) {
	    	if(isDirectory) {
	    		File folder = new File(selectedFile.getAbsolutePath());
	    		
	    		for (final File f : folder.listFiles(IMAGE_FILTER)) {
	    			imgFiles.add(f);
	    		
	    		}
	    	} else if(isFile) {
	    		System.out.println("file: "  + selectedFile.getAbsolutePath());
	    		try {
	    		    Image image = ImageIO.read(new File(selectedFile.getAbsolutePath()));
	    		    if (image == null) {
	    		        System.out.println("The file could not be opened , it is not an image");
	    		    } else {
	    		    	imgFiles.add(new File(selectedFile.getAbsolutePath()));
	    		    }
	    		} catch(IOException ex) {
	    			System.out.println("The file could not be opened , it is not an image");
	    		}
	    	}
	    }
		return imgFiles;
	}
}