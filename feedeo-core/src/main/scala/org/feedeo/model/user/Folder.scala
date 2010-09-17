package org.feedeo.model.user

import org.feedeo.model.feed.Entry

class Folder(user: User, parent: Option[Folder] = None, var subFolders: Seq[Folder] = Nil) {
  var entries: Iterable[Entry] = Nil

  def isSubFolderOf(folder: Folder): Boolean = {
    parent match {
      case None => false
      case Some(thisParent) => if (thisParent == folder) true else thisParent.isSubFolderOf(folder)
    }
  }

  def isAncestorOf(folder: Folder) = folder.isSubFolderOf(this)

}
