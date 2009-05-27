package org.feedeo.hibernate;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * This class specifies a few things that are common to all of our Hibernate
 * entities. It also gives a few methods for easier saving/updating/deletion.
 * 
 * @author Feedeo Team
 */
// TODO classe a revoir integralement.
public abstract class HibernateObject {

	/**
	 * Default constructor.
	 */
	public HibernateObject() {
		super();
	}

	private Long id;

	/**
	 * Sets the object's id in the corresponding table in the database. Its
	 * visibility, protected, means that only Hibernate will access it.
	 * 
	 * @param id
	 */
	protected void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the object's id in the corresponding table in the database.
	 */
	protected Long getId() {
		return id;
	}

}
