package org.feedeo;

import java.util.HashSet;
import java.util.Set;

import org.feedeo.hibernate.HibernateObject;

/**
 * This abstract class characterizes the fact that a given object class can
 * contain directories.
 * 
 * @author Feedeo Team
 * 
 */
public abstract class DirContainer extends HibernateObject {
	/**
	 * List of the object's directories.
	 */
	protected Set<Directory> directories;
	
	/**
	 * @return the children directories' owner
	 */
	abstract protected User getDirUser();

	/**
	 * Default constructor.
	 */
	public DirContainer() {
		super();
		directories = new HashSet<Directory>();
	}
	
	/**
	 * @return the object's directories.
	 */
	public Set<Directory> getDirectories() {
		return directories;
	}

	/**
	 * @param directories
	 */
		public void setDirectories(Set<Directory> directories) {
		this.directories = directories;
	}

	/**
	 * This function makes a given directory a child of
	 * this directory.
	 * 
	 * @param directory
	 */
	public void attachDirectory(Directory directory) {
		this.getDirectories().add(directory);
		directory.setOwner(this.getDirUser());
	}

}
