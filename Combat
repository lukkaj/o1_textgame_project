package o1.adventure

import scala.io.StdIn.readLine
import scala.util.Random

/** The Combat class represents the battle between npc and player. Player and npc try to hit each other inorder
  * to kill each other. Both will die if health is reducedd to zero. Hitting chance is generated with
  * pseudorandom generator. Player can either 'attack' or 'use item'. When attacking player has the chance of
  * inflicting damage to enemy, but player can also miss. Player can use items such as health potion or use
  * different weapon to change wielded weapon. If player dies game ends.
  * @param player player character
  * @param enemy hostile npc */
class Combat(player: Player, enemy: NPC) {
  println(s"\n$enemy charges at you! Be ready!")
  def enemyAction(chance: Double): Int = {
    if(chance > 0.59){
      if(player.playerHP <= enemy.damage){
        println("You died!")
        player.playerHP = 0
        player.dead = true
      } else {
        player.playerHP -= enemy.damage
        println(s"$enemy strikes you! You lost ${enemy.damage} health points. You have ${player.playerHP} health points left.")
      }
    } else {
      println(s"$enemy's attack missed!")
    }
    player.playerHP
  }
  /** Player and the enemy have different chances of hitting each other and pseudonumbers are generated differently for both */
  while (player.playerHP > 0 && enemy.health > 0){
    var chance = player.randomNumber// chance is between 0-1 floating number
    var enemyChance = enemy.randomNumberCombat // enemy chance of hitting
    var choice = readLine(s"Will you 'attack' or 'use item'? ")
    if (choice == "attack"){
      if(chance < 0.49){
        println(s"You missed, prepare for $enemy's attack!")
        enemyAction(enemyChance)
      } else {
        if(enemy.health <= player.playerDamage){
          enemy.dead = true
          enemy.health = 0
          println(s"You manage to kill $enemy. $enemy dropped\n")
          for(enemyInventory <- enemy.inventory){
            enemy.currentLocation.addItem(enemyInventory)
            println(s"${enemyInventory.name}\n")
          }
          enemy.currentLocation.removeNpc(this.enemy.name)
          println(enemy.killMessage)
          println("You should get dropped items and then rest for a bit to gather your strength.")
        } else {
          enemy.health -= player.playerDamage
          println(s"You manage to strike $enemy with ${player.playerDamage} points of damage. Enemy has ${enemy.health} health points left.")
          enemyAction(enemyChance)
        }
      }
    } else if(choice == "use item"){
      if (player.playerInventory.isEmpty){
        println(s"Your inventory is empty. As you search for item to use $enemy attacks you!")
      } else {
        println(s"In your inventory you have: ${player.playerInventory.keys.mkString(" ")}")
        var itemToUse = readLine("Which item do you wish to use? ")
        if (player.playerInventory.contains(itemToUse) && player.playerInventory(itemToUse).effect.isDefined){
          player.use(itemToUse)
        } else if (player.playerInventory.contains(itemToUse) && player.playerInventory(itemToUse).damage.isDefined){
          player.use(itemToUse)
        } else if (player.playerInventory.contains(itemToUse) && (player.playerInventory(itemToUse).effect.isEmpty || player.playerInventory(itemToUse).damage.isEmpty)){ // item is not potion or weapon
          println(s"$itemToUse could not be used in any meaningful way in combat.")
        } else {
          println(s"You do not have $itemToUse. While searching for the item $enemy begins to attack")
        }
      }
      enemyAction(enemyChance)
    }
  }
}
