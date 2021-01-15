package o1.adventure



import scala.collection.mutable.Map

/** The class Area represents locations in a text adventure game world. A game world
  * consists of areas. What different areas have in common is that players can be
  * located in them and that they can have exits leading to other, neighboring areas,
  * contain items and area can hold non-player characters.
  * An area also has a name and a description.
  * @param name         the name of the area
  * @param description  a basic description of the area (typically not including information about items) */
class Area(var name: String, var description: String) {

  private val neighbors = Map[String, Area]()
  val items = Map[String, Item]() // Items in certain area
  val nonPlayerChar = Map[String, NPC]() // Areas non-player characters

  /** Returns the area that can be reached from this area by moving in the given direction. The result
    * is returned in an Option; None is returned if there is no exit in the given direction. */
  def neighbor(direction: String) = this.neighbors.get(direction)

  /** Adds exits from this area to the given areas.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    *@see [[setNeighbor]] */
  def setNeighbors(exits: Vector[(String, Area)]) = {
    this.neighbors ++= exits
  }

  /** Returns a multi-line description of the area as a player sees it. This includes a basic
    * description of the area as well as information about exits, non-player characters and items.
    * The return value has the form "DESCRIPTION\n\nExits available: DIRECTIONS SEPARATED BY SPACES".
    * The directions are listed in an arbitrary order. */
  def fullDescription = {
    val itemsInArea = if(this.items.isEmpty) "" else "\nYou see here: " + this.items.keys.mkString(" ")
    val npcInArea = if(this.nonPlayerChar.isEmpty) "" else s"\nYou see ${this.nonPlayerChar.keys.mkString(" ")}"
    val exitList = "\n\nExits available: " + this.neighbors.keys.mkString(" ")
    this.description + itemsInArea + exitList + npcInArea
  }

  /** Place an item in the area so that it can be for instance, picked up
    * @param item contains item, which is placed on the area*/
  def addItem(item: Item): Unit = {
    this.items.put(item.name, item)
  }

  /** Wheater the area contains given item
    * @param itemName name of the item  */
  def contains(itemName: String): Boolean = {
    this.items.contains(itemName)
  }

  /** Remove item from the area
    * @param itemName the name of the item, which is be removed */
  def removeItem(itemName: String): Option[Item] ={
    this.items.remove(itemName)
  }

  /** Add given npc to area
    * @param npc the npc that is added to certain area */
  def addNpc(npc: NPC) = {
    this.nonPlayerChar.put(npc.name, npc)
  }

  /** Check if an area contain given npc
    * @param npcName name of the npc */
  def containsNpc(npcName: String) = {
    this.nonPlayerChar.contains(npcName)
  }

  /** Remove npc of given name
    * @param npcName name of the to be removed npc */
  def removeNpc(npcName: String) = {
    this.nonPlayerChar.remove(npcName)
  }

  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)

}



