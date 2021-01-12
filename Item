package o1.adventure

/** The class `Item` represents items in the game. It is an abstract class,
  * and the concrete classes are certain items with distinct effects.
  * Each item has a name,a longer description and might have a certain effect or damage multiplier.
  * Every item has a unique name.
  * @param name         the item's name
  * @param description  the item's description */
abstract class Item(val name: String, val description: String) {
  /** Returns a short textual representation of the item (its name, that is). */
  override def toString = this.name
  def damage: Option[Int]
  def effect: Option[Int]
}

/** Nonusable items have no special effects
  * @param name name of the item
  * @param description longer description about the item */
class NonUsableItem(name: String, description: String) extends Item(name, description){
  override def damage: Option[Int] = None
  override def effect: Option[Int] = None
}
/** Weapons will enhance player's damage output
  * @param name name of the item
  * @param description longer description about the item */
class Weapon(name: String,description: String,val weaponDamage: Option[Int]) extends Item(name,description){
  override def damage: Option[Int] = weaponDamage
  override def effect: Option[Int] = None
}
/** Potion will increase player's health
  * @param name name of the item
  * @param description longer description about the item */
class Potion(name: String,description: String,val potionEffect: Option[Int]) extends Item(name,description){
  override def damage: Option[Int] = None
  override def effect: Option[Int] = potionEffect
}
