package o1.adventure
import Constants._
import scala.io.StdIn.readLine
/** Trade class represents trading items between npc and player. Player can trade certain items from his/her inventory to npc.
  * Friendly npc has certain items that will be traded for certain items. If player does not own these items he can come back later to trade.
  * Player must make decision whether to trade or not with the npc.
  * @param player Player character
  * @param merchant friendly npc */
class Trade(player: Player, merchant: NPC) {
  println(s"Hello my name is ${merchant.name}, you aren't here to take away my last things right? I can give you better weapons if you find my gold or silver coins!")
  if (player.playerInventory.contains("silver coin")){
    val silverCoin = player.playerInventory("silver coin")
    println(s"I can trade a great weapon for my silver coin!")
    val knife = merchant.inventory(knifeIndex)
    var choice = readLine("Do you agree to trade? yes or no? ")
    if (choice == "yes"){
      println(s"happy to do business with you ${player.playerName}")
      player.playerInventory.put(knife.name, knife)
      println(s"You traded ${silverCoin.name} for ${knife.name}. ${knife.name} has damage of ${knife.damage.head}")
      merchant.inventory += silverCoin
      player.playerInventory.remove(silverCoin.name)
      merchant.inventory.drop(knifeIndex)
    } else {
      println("If you change your mind I would be more than happy to trade with you")
    }
  }

  if (player.playerInventory.contains("gold coin")){
    println("PLEASE trade the coin with me! I give you my most precious thing!")
    val goldCoin = player.playerInventory("gold coin")
    val machete = merchant.inventory(macheteIndex)
    var anotherChoice = readLine("Do you agree to trade? yes or no? ")
    if (anotherChoice == "yes"){
      println(s"happy to do business with you ${player.playerName}")
      player.playerInventory.put(machete.name, machete)
      println(s"You traded ${goldCoin.name} for ${machete.name}. ${machete.name} has damage of ${machete.damage.head}")
      merchant.inventory += goldCoin
      player.playerInventory.remove(goldCoin.name)
      merchant.inventory.drop(macheteIndex)
    } else {
      println("If you change your mind I would be more than happy to trade with you")
    }
  }

  if(merchant.inventory.exists(_.name == "gold coin") && merchant.inventory.exists(_.name == "silver coin")){
    println(s"What are you doing back here ${player.playerName}? I don't have anything for you, please leave!")
  } else {
    println("I will be here if you need something. I can not go outside because there are those shadow thingies")
  }

}
