package geometricobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

/**
 * Class to represent a line
 * 
 * @author Davide Tonin
 * @version 1.0 2017-09-20
 */
public class Circle extends GeometricObject implements Drawable {
	static final Color DEFAULT_COLOR = Color.RED;
	
	private Point center;
	private int radius;
	private Color color;
	private boolean complete;
	
	/**
	 * Basic Constructor
	 */
	public Circle() {
		super("Circle");
		this.complete = false;
	}

	/**
	 * Basic constructor
	 * @param center the center point
	 */
	public Circle(Point center) {
		this();
		this.center = center;
		this.color = DEFAULT_COLOR;
	}
	
	/**
	 * Basic constructor
	 * @param center the center point
	 * @param radius the radius of the circle
	 */
	public Circle(Point center, int radius) {
		this();
		this.center = center;
		setRadius(radius);
		this.color = DEFAULT_COLOR;
	}
	
	/**
	 * Complete constructor
	 * @param center the center point
	 * @param radius the radius of the circle
	 * @param color the color
	 */
	public Circle(Point center, int radius, Color color) {
		this();
		this.center = center;
		setRadius(radius);
		this.color = color;
	}
	
	/**
	 * Complete constructor
	 * @param center the center point
	 * @param radius the radius of the circle
	 * @param color the color
	 * @param complete true if the circle is complete
	 */
	public Circle(Point center, int radius, Color color, boolean complete) {
		this();
		this.center = center;
		setRadius(radius);
		this.color = color;
		this.complete = complete;
	}
	
	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * @param center the center to set
	 */
	public void setCenter(Point center) {
		this.center = center;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		if (radius >= 0) this.radius = radius;
		else {
			System.out.println("Raggio non valido");
			JOptionPane.showMessageDialog(null, "Raggio del cerchio non valido!");
		}
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the complete
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * @param complete the complete to set
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(color);
		if (center != null && radius > 0) g2d.drawOval(center.getX()-radius, center.getY()-radius, 2*radius, 2*radius);
	}

}
