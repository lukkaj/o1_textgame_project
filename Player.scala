package o1.adventure

import scala.collection.mutable.Map
import Constants._
import o1.Pic
import o1.gui.Pic.show
import o1.gui.colors.White

import scala.util.Random


/** A `Player` object represents a player character controlled by the real-life user of the program.
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  * Player has inventory where to store items, player can wield weapon at one hand, playerhp is the health of player
  * player has aa damage that can be dealed in combat. In the begining player is not dea, but in comabt player
  * can die, if health reaches zero.
  * @param startingArea  the initial location of the player
  * @param name player's name */
class Player(startingArea: Area, name: String) {
  val playerName = name
  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  val playerInventory = Map[String, Item]() // players inventory
  val wieldedWeapon = Map[String, Item]() // Wielded weapon will not be usable from inventory, but can be put back to inventory.
  var playerHP = maxHpPlayer
  var playerDamage = fistDamage // This can change if player uses some weapon to deal more damage
  var dead = false // player is not dead. If this is true player is dead and game ends
  val randomGenerator = new Random(playerSeed)

  /** Calculates random number for player's combat against hostile non-player characters */
  def randomNumber = randomGenerator.nextDouble()
  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven


  /** Returns the current location of the player. */
  def location = this.currentLocation


  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player's current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if (destination.isDefined) "You go " + direction + "." else "You can't go " + direction + "."
  }

  /** Causes the player to rest for a short while and get healed to max health, can not be done while in combat */
  def rest() = {
    playerHP = maxHpPlayer
    "You rest for a while. You feel rested and better overall"
  }

  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }

  /** picks up item from ground
    * @param itemName name of the item player is about to pick up*/
  def get(itemName: String): String = {
    val pickedUpItem = this.location.removeItem(itemName)
    for (newItem <- pickedUpItem){
      this.playerInventory.put(newItem.name, newItem)
    }
    if (pickedUpItem.isDefined) s"You pick up the $itemName." else s"There is no $itemName here to pick up."
  }

  /** Drops the item to the ground
    * @param itemName name of the item player want's to drop*/
  def drop(itemName: String): String = {
    if(has(itemName)){
      this.location.addItem(playerInventory.remove(itemName).get)
      s"You drop the $itemName."
    } else {
      "You dont't have that."
    }
  }

  /** Inventory tells what items player is carrying or that inventory is empty */
  def inventory: String = {
    var itemList: String = ""
    if(playerInventory.isEmpty){
      "Your inventory is empty."
    } else {
      itemList = "You are carrying:\n"
      for (item <- playerInventory.keys){
        itemList += item + "\n"
      }
      itemList
    }
  }

  /** Method used to tell if player has given item
    * @param itemName name of the item player is looking for*/
  def has(itemName: String): Boolean = {
    playerInventory.contains(itemName)
  }
  /** Player examines given item if player has such a item. Prints the description of certain item
    * @param itemName name of the item */
  def examine(itemName: String): String = {
    if(has(itemName)){
      var itemDescription = this.playerInventory.get(itemName).head.description
      s"You look closely at the $itemName.\n$itemDescription"
    } else {
      "You do not have such a item in your inventory"
    }
  }

  /** PLayer can wield one weapon. When weapon is wielded it is not in players inventory.
    * If player is already wielding a weapon, wielded weapon is placed in inventory
    * and the new weapon is wielded instead. Unwielded weapon can be found from inventory
    * @param weaponName name of the weapon player is about to wield */
  def wield(weaponName: String) : Int = {
    val weaponToWield = playerInventory.find(_._1 == weaponName).map(_._2)
    if(wieldedWeapon.isEmpty){ // Player can wield only one weapon at a time
      wieldedWeapon.put(weaponName, weaponToWield.head)
      playerInventory.remove(weaponName)
    } else {
      val weaponInHand = wieldedWeapon.head
      playerInventory.put(weaponInHand._1, weaponInHand._2)
      wieldedWeapon.remove(weaponInHand._1)
      wieldedWeapon.put(weaponName, weaponToWield.head)
    }
    wieldedWeapon.get(weaponName).flatMap(_.damage).head
  }

  /** With this method player can utilize special effects of certain item types, such as weapons and potions
    * Weapons can increase player damage done to enemies. Health potions can be used to increase max health
    * @param itemName name of the item player wants to utilize */
  def use(itemName: String) = {
    val itemToUse = playerInventory.keys.find(_ == itemName).getOrElse(s"You do not have $itemName")
    if(has(itemToUse)){
      if(playerInventory(itemToUse).damage.isDefined){
        val weaponDamage = wield(itemToUse)
        playerDamage = weaponDamage
        playerInventory.remove(itemToUse)
        println(s"This $itemToUse looks handy. Your damage is $weaponDamage")
        s"You wield ${itemName}"
      } else if(playerInventory(itemToUse).effect.isDefined){
        val itemEffect = playerInventory(itemToUse).effect.get
        if(playerHP < maxHpPlayer) {
          playerHP += itemEffect
          playerInventory.remove(itemToUse)
          println(s"Your health is inreased by $itemEffect to $playerHP")
          s"You used $itemToUse."
        } else {
          playerInventory.remove(itemToUse)
          println(s"You do not feela any different from before. To think why would vitamins or some minerals affect instantly...")
          s"You used $itemToUse, your health was increased by 0 to $playerHP"
        }
      } else {
        s"$itemToUse could not be used"
      }
    } else {
      s"You do not own $itemName"
    }
  }

  /** Shows the picture of help picture */
  def help() = {
    val helpPic = Pic("dreamLandHelp.PNG")
    show(helpPic, White, 100)
    ""
  }
  /** Shows the ingame map as a picture */
  def showMap() = {
    val mapPic = Pic("map.PNG")
    show(mapPic, White, 100)
    ""
  }

  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name

}
