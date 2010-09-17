package org.feedeo.model.user

class User(login: String, var rootFolder: Folder = null) {
  if (rootFolder == null) {
    rootFolder = new Folder(this)
  }

}