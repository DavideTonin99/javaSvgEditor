package geometricobjects;

/**
 * Represent a generic geometric object
 * 
 * @author Davide Tonin
 * @version 1.0 2017-09-18
 */
public abstract class GeometricObject {
    
	private String type;
	
	public GeometricObject() {
		this.type = "Geometric Object";
	}
	
	/**
	 * Complete constructor
	 * @param type the type of the object
	 */
	public GeometricObject(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
}