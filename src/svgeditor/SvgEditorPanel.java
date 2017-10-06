package svgeditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import filemanager.FileManager;
import geometricobjects.*;
import java.awt.Container;

/**
 * Class to represent the canvas where you can draw
 *
 * @author Davide Tonin
 * @version 1.0 2017-09-19
 */
public class SvgEditorPanel extends Container implements MouseListener, MouseMotionListener {

    static final String POINT = "point";
    static final String LINE = "line";
    static final String CIRCLE = "circle";
    static final String POLYLINE = "polyline";
    static final String MIXEDLINE = "mixedline";

    private String shape;
    private Color currentColor;
    private int currentDrawingStroke;

    private ArrayList<Point> points;
    private ArrayList<Line> lines;
    private ArrayList<Circle> circles;
    private ArrayList<Polyline> polylines;

    /**
     * Complete constructor
     *
     * @param parent The parent object (JFrame)
     */
    public SvgEditorPanel(SvgEditor parent) {
        shape = "Geometric Object"; // Default shape
        currentColor = Color.RED;
        currentDrawingStroke = 2;

        points = new ArrayList<Point>();
        lines = new ArrayList<Line>();
        circles = new ArrayList<Circle>();
        polylines = new ArrayList<Polyline>();

        addMouseListener(this);
        addMouseMotionListener(this);
        parent.getContentPane().setBackground(Color.BLACK);
        repaint();
    }

    /**
     * Return all drawed objects
     *
     * @return
     */
    public ArrayList<GeometricObject> getData() {
        ArrayList<GeometricObject> data = new ArrayList<>();
        for (Point p : points) {
            data.add(p);
        }
        for (Line l : lines) {
            data.add(l);
        }
        for (Circle c : circles) {
            data.add(c);
        }
        for (Polyline pol : polylines) {
            data.add(pol);
        }
        return data;
    }

    /**
     * Set the shape selected
     */
    public void setShape(String shape) {
        shape = shape.toLowerCase();
        // Check if the shape to set is valid
        if (shape.equals(POINT) || shape.equals(LINE) || shape.equals(CIRCLE) || shape.equals(POLYLINE)
                || shape.equals(MIXEDLINE)) {
        	// If the previous shape was polyline or mixedline or line or circle, ends it
            if (this.shape.equals(POLYLINE) || this.shape.equals(MIXEDLINE) || this.shape.equals(LINE) || this.shape.equals(CIRCLE)) {
                endLastFigure();
                repaint();
                // if the previous shape was line, ends it
            }
            this.shape = shape;
        } else {
            System.err.println("Forma selezionata non valida");
        }
    }

    /**
     * Clear the drawing surface
     */
    public void clear() {
        points.clear();
        lines.clear();
        circles.clear();
        polylines.clear();
        repaint();
    }

    /**
     * Choose color
     */
    public void chooseColor() {
        currentColor = JColorChooser.showDialog(null, "Choose Color", currentColor);
    }

    /**
     * Add new point
     *
     * @param xSource x coordinate
     * @param ySource y coordinate
     */
    public void addPoint(int xSource, int ySource) {
        Point p = new Point(xSource, ySource, currentColor);
        if (!points.contains(p)) {
            points.add(p);
        } else {
            System.out.println("Punto (" + p.getX() + ", " + p.getY() + ") già selezionato");
        }
    }

    /**
     * Add new line
     *
     * @param xSource x coordinate
     * @param ySource y coordinate
     * @param toEnd true if the line could be ended
     */
    public void manageLine(int xSource, int ySource, boolean toEnd) {
        if (!lines.isEmpty()) {
        	// Check if the last line is complete
            if ((lines.get(lines.size() - 1)).isComplete()) {
            	// If the last line is complete, it creates a new one with only the first point
                Line line = new Line(new Point(xSource, ySource));
                line.setColor(currentColor);
                lines.add(line);
            } else {
            	// If the last line isn't complete, it adds the second point
                Line line = lines.get(lines.size() - 1);
                line.setP2(new Point(xSource, ySource));
                if (toEnd) line.setComplete(true);
                lines.set(lines.size() - 1, line);
            }
        } else {
        	// If there aren't lines yet, it creates one
            Line line = new Line(new Point(xSource, ySource));
            line.setColor(currentColor);
            lines.add(line);
        }
    }

    /**
     * Add a circle
     *
     * @param xSource the x coordinate of the center
     * @param ySource the y coordinate of the center
     * @param toEnd true if the circle could be ended
     */
    public void manageCircle(int xSource, int ySource, boolean toEnd) {
        if (circles.isEmpty() || circles.get(circles.size() - 1).isComplete()) {
        	// If there aren't circles yet or if the last circle is complete, it creates new one
            Circle circle = new Circle(new Point(xSource, ySource));
            int radius = calculateCircleRadius(circle, xSource, ySource);
            circle.setRadius(radius);
            circle.setColor(currentColor);
            circles.add(circle);            
        } else if (!circles.isEmpty() || !circles.get(circles.size() - 1).isComplete()) {
        	// If there are already circles or the last circle isn't complete, ends it
            Circle circle = circles.get(circles.size() - 1);
            int radius = calculateCircleRadius(circle, xSource, ySource);
            circle.setRadius(radius);
            circle.setColor(currentColor);
            if (toEnd) circle.setComplete(true);
            circles.set(circles.size() - 1, circle);
        }
    }

    /**
     * Calculate and return the radius of a circle
     *
     * @param circle the circle
     * @param xSource x coordinate of the mouse
     * @param ySource y coordinate of the mouse
     * @return radius
     */
    public int calculateCircleRadius(Circle circle, int xSource, int ySource) {
        int xCenter = circle.getCenter().getX();
        int yCenter = circle.getCenter().getY();
        int radius = new Double(Math.abs(Math.sqrt(Math.pow(xCenter - xSource, 2) + Math.pow(yCenter - ySource, 2))))
                .intValue();
        return radius;
    }

    /**
     * Add a polyline
     *
     * @param xSource x coordinate of the mouse
     * @param ySource y coordinate of the mouse
     */
    public void managePolyline(int xSource, int ySource) {
    	// If there are already some polylines and the last isn't complete, adds new point to it
        if (!polylines.isEmpty() && !polylines.get(polylines.size() - 1).isComplete()) {
            Polyline pol = polylines.get(polylines.size() - 1);
            pol.addPoint(new Point(xSource, ySource, currentColor));
            polylines.set(polylines.size() - 1, pol);
        } else {
            Point p = new Point(xSource, ySource, currentColor);
            Polyline pol = new Polyline();
            pol.addPoint(p);
            pol.setColor(currentColor);
            polylines.add(pol);
        }
    }

    /**
     * Ends current drawing form
     */
    public void endLastFigure() {
        if (this.shape.toLowerCase().equals(LINE)) {
            if (!lines.isEmpty() && !lines.get(lines.size() - 1).isComplete()) {
            	// If the last line isn't complete, deletes it
                lines.remove(lines.size() - 1);
            }
        } else if (this.shape.toLowerCase().equals(CIRCLE)) {
            if (!circles.isEmpty() && !circles.get(circles.size() - 1).isComplete()) {
            	// If the last circle isn't complete, deletes it
            	circles.remove(circles.size() - 1);
            }
        } else if (this.shape.toLowerCase().equals(POLYLINE) || this.shape.toLowerCase().equals(MIXEDLINE)) {        	
            if (!polylines.isEmpty() && !polylines.get(polylines.size() - 1).isComplete()) {
            	// If the last polyline or mixedline isn't complete, ends it
                Polyline pol = polylines.get(polylines.size() - 1);
                pol.setComplete(true);
                pol.removeLast(); // remove the last point because it was provisional
                polylines.set(polylines.size() - 1, pol);
            }
        }
    }

    /**
     * Read figures to draw from file
     */
    public void read(String filename) {
        FileManager fm = new FileManager();
        String extension = filename.substring(filename.lastIndexOf("."), filename.length());
        if (extension.equals(".csv") || extension.equals(".xml")) {
            ArrayList<GeometricObject> geometricObjects;
            if (extension.equals(".csv")) {
                geometricObjects = fm.readCSV(filename);
            } else {
                geometricObjects = fm.readXML(filename);
            }
            for (GeometricObject go : geometricObjects) {
                String type = go.getType().toLowerCase();
                System.out.println(type);
                if (go instanceof Point) {
                    points.add((Point) go);
                } else if (go instanceof Polyline) {
                    polylines.add((Polyline) go);
                } else if (go instanceof Circle) {
                    circles.add((Circle) go);
                } else if (go instanceof Line) {
                    lines.add((Line) go);
                }
            }
        }
        repaint();
    }

    /**
     * Write drawed figures on file
     *
     * @param filename
     */
    public void write(String filename) {
        FileManager fm = new FileManager();
        if (filename.substring(filename.lastIndexOf("."), filename.length()).equals(".csv")) {
            fm.writeCSV(filename, this.getData());
        } else if (filename.substring(filename.lastIndexOf("."), filename.length()).equals(".xml")) {
            fm.writeXML(filename, this.getData());
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(currentDrawingStroke));

        for (int i = 0; i < points.size(); i++) {
            ((Point) points.get(i)).draw(g2d);
        }
        for (int i = 0; i < lines.size(); i++) {
            ((Line) lines.get(i)).draw(g2d);
        }
        for (int i = 0; i < circles.size(); i++) {
            ((Circle) circles.get(i)).draw(g2d);
        }
        for (int i = 0; i < polylines.size(); i++) {
            ((Polyline) polylines.get(i)).draw(g2d);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int xSource = e.getX();
        int ySource = e.getY();

        if (shape.equals("Geometric Object")) {
            JOptionPane.showMessageDialog(this, "Seleziona una forma da disegnare!");
        } else if (shape.equals(POINT)) {
            addPoint(xSource, ySource);
        } else if (shape.equals(LINE)) {
            // right click to delete the current line
            if (SwingUtilities.isRightMouseButton(e)) {
                endLastFigure();
            } else {
                manageLine(xSource, ySource, true);
            }
        } else if (shape.equals(CIRCLE)) {
        	// right click to delete the current circle
            if (SwingUtilities.isRightMouseButton(e)) {
                endLastFigure();
            } else {
                manageCircle(xSource, ySource, true);
            }
        } else if (shape.equals(POLYLINE) || shape.equals(MIXEDLINE)) {
        	// right click to delete the last point (the provisional point) and end the polyline
            if (SwingUtilities.isRightMouseButton(e)) {
                endLastFigure();
            } else {
                managePolyline(xSource, ySource);
            }
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.shape.equals(MIXEDLINE)) {
            endLastFigure();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.shape.equals(MIXEDLINE)) {
            mouseClicked(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (this.shape.equals(LINE)) {
            if (!lines.isEmpty() && !(lines.get(lines.size() - 1).isComplete())) {
            	manageLine(e.getX(), e.getY(), false);            
            	repaint();
            }
            
        } else if (this.shape.equals(CIRCLE)) {
        	if (!circles.isEmpty() && !(circles.get(circles.size() - 1).isComplete())) {
        		manageCircle(e.getX(), e.getY(), false);
                repaint();
        	}
        } else if (this.shape.equals(POLYLINE) || this.shape.equals(MIXEDLINE)) {
            if (!polylines.isEmpty() && !(polylines.get(polylines.size() - 1).isComplete())) {
            	// If the last polyline isn't complete, continues it
                Polyline pol = polylines.get(polylines.size() - 1);
                ArrayList<Point> points = pol.getPoints();
                // Change the last point with the current provisional
                points.set(points.size() - 1, new Point(e.getX(), e.getY()));
                pol.setPoints(points);
                polylines.set(polylines.size() - 1, pol);
            }
            repaint();
        }
    }

}
