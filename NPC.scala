package o1.adventure

import scala.util.Random

import o1.Buffer

/** To easen the readability of the code, extends of stagnant and moving npc have been dropped with one row. */

/** Non-player characters are characters in game which player is capable of interacting with.
  * Stagnant npc's will stay on one location, and therefore easier to find. They are also more important regarding
  * game's story. Moving npc has an ability to move around the areas. Moving is done by generating pseudorandom number and npc
  * moves around randomly, but with given directions eg. north and south, but not east or west
  * @param name name of the npc
  * @param startingLocation location where npc is added
  * @param health max health of given npc
  * @param damage damage that npc is can deal in combat
  * @param hostile whether npc is hostile or friendly towards player. If in same area either trade or combat will begin
  * @param inventory items that npc have. These will drop from killed enemy or traded with friendly one
  * @param killMessage when npc dies certain message will be printed on text UI*/

abstract class NPC (val name: String, val startingLocation: Area, var health: Int, val damage: Int, val hostile: Boolean, var inventory: Buffer[Item], val killMessage: String) {
  var dead: Boolean
  var currentLocation: Area
  def randomNumberCombat: Double
  override def toString = this.name
}
/** Stagnant npc's will just stay on one location. Every npc will have different seed for randomgenerator in order to make combat different with each foe */
class StagnantNonPlayerCharacter(name: String, startingLocation: Area, health: Int, damage: Int, hostile: Boolean, inventory: Buffer[Item], killMessage: String, seed: Int)
                                extends NPC(name, startingLocation, health, damage, hostile, inventory, killMessage) {

  var currentLocation = startingLocation
  val randomGenerator = new Random(seed)
  def randomNumberCombat = randomGenerator.nextDouble()
  override var dead: Boolean = false
}
/** Moving npc will move to a random direction */
class MovingNonPlayerCharacter(name: String, startingLocation: Area, health: Int, damage: Int, hostile: Boolean, inventory: Buffer[Item], val directions: Vector[String], killMessage: String, seed: Int)
                               extends NPC(name, startingLocation, health, damage, hostile, inventory, killMessage)  {

  var currentLocation = startingLocation
  override var dead: Boolean = false
  val randomGeneratorCombat = new Random(seed)
  def randomNumberCombat = randomGeneratorCombat.nextDouble()

  def location = this.currentLocation
  val randomGeneratorMoving = new Random(100)
  def randomDir = {
    var direction = directions(randomGeneratorMoving.nextInt(directions.size))
    direction
  }
  /** npc will move to a location if possible. In while loop direction will be randomize and checked if it is possible to move that direction */
  def movement() = {
    var directionOfMovement = ""
    var directionToMove = ""
    var flag = 1
    while(flag != 0){
      directionOfMovement = randomDir
      if (this.location.neighbor(directionOfMovement).isDefined){
        directionToMove = directionOfMovement
        flag = 0
        directionToMove
      } else {
        flag = 1
      }
    }
    var newLocation = this.currentLocation.neighbor(directionToMove).getOrElse(currentLocation)
    var oldLocation = this.currentLocation
    oldLocation.removeNpc(this.name) // removes npc from old location
    this.currentLocation = newLocation
    this.currentLocation.addNpc(this) // add npc to new location
  }
}

