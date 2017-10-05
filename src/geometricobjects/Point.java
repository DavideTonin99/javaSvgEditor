package geometricobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Class to represent a point
 * 
 * @author Davide Tonin
 * @version 1.0 2017-09-19
 */
public class Point extends GeometricObject implements Drawable {
	static final Color DEFAULT_COLOR = Color.RED;

	private int x;
	private int y;
	private Color color;

	/**
	 * Complete constructor
	 * 
	 * @param x
	 *            the x coordinate on the drawing surface
	 * @param y
	 *            the y coordinate on the drawing surface
	 */
	public Point(int x, int y) {
		super("Point");
		this.x = x;
		this.y = y;
		this.color = DEFAULT_COLOR;
	}

	/**
	 * Complete constructor
	 * 
	 * @param x
	 *            the x coordinate on the drawing surface
	 * @param y
	 *            the y coordinate on the drawing surface
	 * @param color
	 *            the color of the point
	 */
	public Point(int x, int y, Color color) {
		super("Point");
		this.x = x;
		this.y = y;
		this.color = color;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set color
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Return the color of the line
	 * 
	 * @return color
	 */
	public Color getColor() {
		return this.color;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		g2d.fillOval(x, y, 6, 6);
	}

	@Override
	public boolean equals(Object obj) {
		Point p = (Point) obj;
		return p.getX() == this.x && p.getY() == this.y;
	}

	@Override
	public String toString() {
		return "Point (x=" + x + ", y=" + y + "), color=" + color;
	}
}
