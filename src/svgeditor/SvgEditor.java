package svgeditor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class to represent the svg editor
 * 
 * Icons: https://icons8.com/
 * @author Davide Tonin
 * @version 1.0 2017-10-05
 */
public class SvgEditor extends JFrame implements ActionListener {
    // constants
	static final int DEFAULT_HEIGHT = 600;
    static final int DEFAULT_WIDTH = 600;

    // Menu
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openOption;
    private JMenuItem saveOption;
    private JMenuItem exitOption;
    private JMenu helpMenu;
    private JMenuItem helpOption;
    
    // ToolBar
    private JToolBar toolBar;
    private JButton openButton;
    private JButton saveButton;
    private JButton pointButton;
    private JButton lineButton;
    private JButton circleButton;
    private JButton polylineButton;
    private JButton mixedLineButton;
    private JButton chooseColorButton;
    private JButton clearButton;

    private SvgEditorPanel svgEditorPanel;
    
    public SvgEditor() {
        setTitle("SVG Editor - Tonin Davide - version 1.0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // set full screen 
        
        Container contentPane = getContentPane();

        setMenu();
        toolBar = new JToolBar();
        setToolBar(contentPane);
        
        svgEditorPanel = new SvgEditorPanel(this);
        contentPane.add(svgEditorPanel);
        
        setVisible(true);
    }
   
    /**
     * Set the menu
     */
    public void setMenu() {
        menuBar = new JMenuBar();
        
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");
       
        // File menu options
        openOption = new JMenuItem("Open");
        openOption.addActionListener(this);
        saveOption = new JMenuItem("Save");
        saveOption.addActionListener(this);
        exitOption = new JMenuItem("Exit");
        exitOption.addActionListener(this);
        
        // Add options to file menu
        fileMenu.add(openOption);
        fileMenu.add(saveOption);
        fileMenu.add(exitOption);
        
        // Help menu option
        helpOption = new JMenuItem("Help");
        helpOption.addActionListener(this);
        helpMenu.add(helpOption);
        
        // Add menus to menubar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        this.setJMenuBar(menuBar);
    }
    
    /**
     * Set the toolbar
     */
    public void setToolBar(Container contentPane) {
        try {
            openButton = new JButton(new ImageIcon(this.getClass().getResource("/resources/icons/open.png").getPath()));
        } catch (NullPointerException e) {
            openButton = new JButton("Open");
        }
        
        try {
            saveButton = new JButton(new ImageIcon(this.getClass().getResource("/resources/icons/save.png").getPath()));
        } catch (NullPointerException e) {
            saveButton = new JButton("Save");
        }
        
        try {
            pointButton = new JButton(new ImageIcon(this.getClass().getResource("/resources/icons/point.png").getPath()));
        } catch (NullPointerException e) {
            pointButton = new JButton("Point");
        }
        
        try {
            lineButton = new JButton(new ImageIcon(this.getClass().getResource("/resources/icons/line.png").getPath()));
        } catch (NullPointerException e) {
            lineButton = new JButton("Line");
        }
        
        try {
            circleButton = new JButton(new ImageIcon(this.getClass().getResource("/resources/icons/circle.png").getPath()));
        } catch (NullPointerException e) {
            circleButton = new JButton("Circle");
        }
        
        try {
            polylineButton = new JButton(new ImageIcon(this.getClass().getResource("/resources/icons/polyline.png").getPath()));
        } catch (NullPointerException e) {
            polylineButton = new JButton("Polyline");
        }
        
        try {
            mixedLineButton = new JButton(new ImageIcon(this.getClass().getResource("/resources/icons/pencil.png").getPath()));
        } catch (NullPointerException e) {
        	mixedLineButton = new JButton("Mixed Line");
        }
        
        try {
            chooseColorButton = new JButton(new ImageIcon(this.getClass().getResource("/resources/icons/choosecolor.png").getPath()));
        } catch (NullPointerException e) {
        	chooseColorButton = new JButton("Choose Color");
        }

        try {
            clearButton = new JButton(new ImageIcon(this.getClass().getResource("/resources/icons/clear.png").getPath()));
        } catch (NullPointerException e) {
        	clearButton = new JButton("Clear");
        }
        
        openButton.addActionListener(this);
        saveButton.addActionListener(this);
        pointButton.addActionListener(this);
        lineButton.addActionListener(this);
        circleButton.addActionListener(this);
        polylineButton.addActionListener(this);
        mixedLineButton.addActionListener(this);
        chooseColorButton.addActionListener(this);
        clearButton.addActionListener(this);
        
        toolBar.add(openButton);
        toolBar.add(saveButton);
        toolBar.add(pointButton);
        toolBar.add(lineButton);
        toolBar.add(circleButton);
        toolBar.add(polylineButton);
        toolBar.add(mixedLineButton);
        toolBar.add(chooseColorButton);
        toolBar.add(clearButton);
        
        contentPane.add(toolBar, BorderLayout.NORTH);
    }
    
    /**
     * Open file
     */
    public void open() {
    	// Initial directory -> projectPath/src/resources
    	File workingDirectory = new File(System.getProperty("user.dir")+"/src/resources");
    	// Define a file chooser to allow the user to navigate in file system to choose the file to open
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.xml", "xml"));
		fileChooser.setCurrentDirectory(workingDirectory);
		// Show the file chooser in open mode and check the response
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			svgEditorPanel.read(fileChooser.getSelectedFile().getPath());				
		}
    }
    
    /**
     * Save file
     */
    public void save() {
    	File workingDirectory = new File(System.getProperty("user.dir")+"/src/resources");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specificare un file dove salvare");
		fileChooser.setCurrentDirectory(workingDirectory);
		// Show the file chooser in save mode and check the response
		int returnVal = fileChooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			svgEditorPanel.write(fileChooser.getSelectedFile().getPath());
		}
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        // Check menu clicks
        if (source instanceof JMenuItem) {
            JMenuItem menuItem = (JMenuItem) source;
            if (menuItem.equals(openOption)) {
            	System.out.println("Open Option è stato cliccato");
            	open();
            } else if (menuItem.equals(saveOption)) {
                System.out.println("Save Option è stato cliccato");
                save();
            } else if (menuItem.equals(exitOption)) {
                System.exit(0);
            } else if (menuItem.equals(helpOption)) {
            	JOptionPane.showMessageDialog(this,"- Apri: legge e disegna le figure salvate su un file csv o xml;\n"
            			+ "- Salva: salva le figure disegnate su un file csv o xml;\n"
            			+ "- Punto: disegna un punto ad ogni click sull'area di disegno;\n"
            			+ "- Linea: fare un click con il mouse per cominciare la linea, successivamente con un altro click sinistro si termina la linea,\n"
            			+ "con un click destro la si elimina;\n"
            			+ "- Cerchio: fare un click conil mouse per cominciare il cerchio, successivamente premere il tasto sinistro per terminarlo, o il destro per eliminarlo;\n"
            			+ "- Polilinea: Disegna una linea ad ogni click sinistro del mouse, con il destro la si termina;\n"
            			+ "- Matita: Tenere premuto e disegnare a piacere;\n"
            			+ "- Seleziona colore;\n"
            			+ "- Cancella tutto");
            }
        } else if (source instanceof JButton) {
        	// ToolBar clicks
        	JButton actionSelected = (JButton)source;
        	
        	if (actionSelected.equals(openButton)) {
        		System.out.println("Open Button è stato cliccato");
        		//Create a file chooser
        		open();        		
        	} else if (actionSelected.equals(saveButton)) {
        		System.out.println("Save Button  è stato cliccato");
        		save();
        	} else if (actionSelected.equals(pointButton)) {
        		System.out.println("Point Button  è stato cliccato");
        		svgEditorPanel.setShape("point");
        	} else if (actionSelected.equals(lineButton)) {
        		System.out.println("Line Button  è stato cliccato");
        		svgEditorPanel.setShape("line");
        	} else if (actionSelected.equals(circleButton)) {
        		System.out.println("Circle Button  è stato cliccato");
        		svgEditorPanel.setShape("circle");
        	} else if (actionSelected.equals(polylineButton)) {
        		System.out.println("Polyline Button  è stato cliccato");
        		svgEditorPanel.setShape("polyline");
        	} else if (actionSelected.equals(mixedLineButton)) {
        		System.out.println("Mixed Line Button  è stato cliccato");
        		svgEditorPanel.setShape("mixedline");
        	} else if (actionSelected.equals(chooseColorButton)) {
        		System.out.println("Choose Color Button  è stato cliccato");
        		svgEditorPanel.chooseColor();
        	} else if (actionSelected.equals(clearButton)) {
        		System.out.println("Clear Button  è stato cliccato");
        		svgEditorPanel.clear();
        	}
		}
	}
}
