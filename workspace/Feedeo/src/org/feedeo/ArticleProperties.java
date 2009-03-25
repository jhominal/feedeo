package org.feedeo;

import org.feedeo.util.HibernateObject;

public class ArticleProperties extends HibernateObject {

	private boolean alreadyRead;
	
	private boolean important;
	
	public ArticleProperties() {
		
	}

	public boolean isAlreadyRead() {
		return alreadyRead;
	}

	public void setAlreadyRead(boolean alreadyRead) {
		this.alreadyRead = alreadyRead;
	}

	public boolean isImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}
	
	
}
