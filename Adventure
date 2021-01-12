package o1.adventure

import o1.Buffer

import Constants._
import scala.io.StdIn._


/** The class Adventure represents text adventure game. An adventure consists of a player, non-player characters and
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * This adventure follows how player get's trough from the strange land he/she is in and recovering lost memory */
class Adventure {

  /** The title of the adventure game. */
  val title = "Dreamland"

  /** Every area in the game. To easen reading  */
  val strangeRoom = new Area("Strange room", "Strange voice: Go trough the door towards north and then search for three keys.\n" +
                             "First key is a knife, you can find it by going east and through the forest to north. At the radiated lake you will find the person who has the key.\n" +
                             "Second key is located at the prison, which you find by continuing northwest. At the prison you will find a guard who has the key. Key is in the guard's pocket\n" +
                             "You can find the third key by going north to the wastelands and then to west. There you will meet a person who should be familiar to you and has the key.\n" +
                             "Be warned that the people that you meet in your adventure might not be friendly.\n" +
                             "When you have gathered the three keys you can unlock the door north of wasteland.\n" +
                             "You do not have unlimited time to wander around, when the sky goes red you know that it is time to speed up before everything collapses!\n" +
                             "Now go, but do not try the south sided door of this room.\n")

  val strangeRoomEnd =  new Area("Strange room", "You decide to test other door, what harm could it do?.\n" +
                                 "Door is locked, you decide to go for the north sided door, but it has dissapeared." +
                                 "You notice that the ceiling is coming towards you little by little.\n" +
                                 "You try everything to get the door open, but it is no use.\n" +
                                 "Ceiling has lowered so much that you have to crouch, then you see the south sided door but in miniature size and you crawl towards it." +
                                 "You realize that this must be a dream\n")

  private val ruinedCityMiddle = new Area("Ruined city middle", "You are in the middle of some ruined city. " +
                                          "There are ruined buildings everywhere and it seems that there are no people around here. Wind is howling.")

  private val ruinedCityNorth = new Area("Ruined city north", "You come to the northern part of the city, there are some rats and a few stray dogs around, but no people.")

  private val ruinedCityEast = new Area("Ruined city east", "You see a little shop with open door, suddenly door was pulled inwards. There might be someone.")

  private val shop = new Area("Shop", "You enter the shop, there you see frightened old man.")

  private val ruinedCityWest = new Area("Ruined city west", "Western city seems to be in much better condition compared to previous part of the city. ")

  private val easternForest = new Area("Forest", "From the backyard of the shop begins a forest. On the ground you can see footprints leading into the forest. Forest is quite dense")

  private val burnedForest = new Area("Burned forest", "This part of the forest is burned down. Some animal remains can be noticed on the ground. You remember that once you did this kind of thing to a forest.")

  private val northernForest = new Area("Forest", "Forest is not at dense as before. You notice that some things are not as they should be, " +
                                        "plants are strange and a squirrel had three eyes. You can see some green glow coming from north.")

  private val radiatedLake = new Area("Lake", "You arrive to the strangest lake you have ever seen. Lake is glowing really bright green glow. You see a few barrels with biohazard marks on them.")

  private val ruinedMall = new Area("Ruined mall", "You arrive to a mall. It is in pretty good conditioon compared to everything else you have seen. No people around here.")

  private val prison = new Area("Prison", "You arrive to a prison, which looks familiar. You remeber that you have been in there.")

  private val wastelandSouth = new Area("Southern wasteland", "It seems that you arrived to a wasteland. Buildings are reduced to piles of rubble. You begin to think what the hell has happened here.")

  private val wastelandNorth = new Area("Northern wasteland", "This northern wasteland is really unhospitale place for life, just junk around and sand. Sky is almost black, scary")

  private val desert = new Area("Desert", "You come to desert. There are nothing here but sand. You feel almost like being at home, because of the emptiness")

  private val doorAtTheEnd = new Area("Door at the end", "On the top of a hill of rubble you find the door, which the strange man told you to find. It is locked, you should find the three keys to open this door")

  private val ending = doorAtTheEnd

  /** Setting up neighbors of every area, this is where player can move from a certain location */
  strangeRoom.setNeighbors(Vector("north" -> ruinedCityMiddle, "south" -> strangeRoomEnd))
  ruinedCityMiddle.setNeighbors(Vector("north"-> ruinedCityNorth, "east" -> ruinedCityEast, "west" -> ruinedCityWest))
  ruinedCityEast.setNeighbors(Vector("east" -> easternForest, "west" -> ruinedCityMiddle, "inside" -> shop))
  ruinedCityWest.setNeighbors(Vector("north" -> ruinedMall, "east" -> ruinedCityMiddle))
  ruinedMall.setNeighbors(Vector("north" -> prison, "east" -> ruinedCityNorth, "south" -> ruinedCityWest))
  prison.setNeighbors(Vector("south" -> ruinedMall))
  shop.setNeighbors(Vector("out" -> ruinedCityEast))
  easternForest.setNeighbors(Vector("north" -> northernForest, "east" -> burnedForest, "west" -> ruinedCityEast))
  burnedForest.setNeighbors(Vector("west" -> easternForest))
  northernForest.setNeighbors(Vector("north" -> radiatedLake, "south" -> easternForest))
  radiatedLake.setNeighbors(Vector("south" -> northernForest, "west" -> wastelandSouth))
  ruinedCityNorth.setNeighbors(Vector("north" -> wastelandSouth, "south" -> ruinedCityMiddle, "west" -> ruinedMall))
  wastelandSouth.setNeighbors(Vector("north" -> wastelandNorth, "east" -> radiatedLake, "south" -> ruinedCityNorth))
  wastelandNorth.setNeighbors(Vector("north" -> doorAtTheEnd, "south" -> wastelandSouth, "west" -> desert))
  desert.setNeighbors(Vector("east" -> wastelandNorth))
  doorAtTheEnd.setNeighbors(Vector("south" -> wastelandNorth))

  /** All of the items, which are not in possession of npc's added to specific locations */
  ruinedCityMiddle.addItem(new Weapon("crowbar", "Rusty crowbar, might be handy", Option(15)))
  ruinedCityMiddle.addItem(new Potion("health potion", "Some kind of potion which should increase health, vitamins?", Option(10)))
  burnedForest.addItem(new NonUsableItem("gold coin", "Gold coin, nice!"))


  /** buffer containing all the npc's that will stay in one location */
  val nonMovingNpcBuffer = Buffer (
    new StagnantNonPlayerCharacter("fragile shadow", ruinedCityEast, 10, 5, true, Buffer(new NonUsableItem("silver coin", "Silver coin, nice!")), "It just vanished, this is really strange!", fragileShadowSeed),

    new StagnantNonPlayerCharacter("familiar man", radiatedLake, 50, 8, true, Buffer(new NonUsableItem("bloody knife", "Familiar knife, which is covered in blood")),
                                   "I know this man, I remember... I killed him at a lake with knife, because he would not leave me alone, idiot.", familiarManSeed),

    new StagnantNonPlayerCharacter("guard", prison, 65, 10, true, Buffer(new NonUsableItem("used bullet", "Used bullet, why somebody would onto an used bullet? It could not be used as ammo")),
                                   "My memories are getting back. When I was escaping from prison, I shot a guard who almost stopped me...", guardSeed),

    new StagnantNonPlayerCharacter("doppelganger", desert, 85, 15, true, Buffer(new NonUsableItem("bottle of medicine", "Bottle of medicine used for anger control")),
                                   "That thing looked just like me, but with red eyes! These are the pills I used to eat for my aggression problems, what the duck", doppelGangerSeed),

    new StagnantNonPlayerCharacter("shopkeeper willy", shop, 100000, 0, false, Buffer(new Weapon("machete", "This could slice meat like butter", Option(35)), new Weapon("knife", "Kitchen knife from IKEA", Option(17))), "", playerSeed)
  )

  /** adding every stagnant npc to right location */
  for (npc <- nonMovingNpcBuffer){
    npc.startingLocation.addNpc(npc)
  }

  val movingNpcVector = Buffer {
    new MovingNonPlayerCharacter("shadow", wastelandNorth, 50, 12, true, Buffer(new Weapon("sword", "Nice sword!", Option(40))), Vector("north", "south"), "Just vanished what the duck", shadowSeed)
  }

  for (movingNpc <- movingNpcVector){
    movingNpc.startingLocation.addNpc(movingNpc)
  }


  /** The character that the player controls in the game. */
  val player = {
    val playerName = readLine("Strage voice calls: What is your name? ")
    new Player(strangeRoom, playerName)
  }


  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 50

  /** When player is warned that time is running out */
  def redSky() = {
    if(turnCount == (timeLimit*0.6).round){
      println("\nYou see sky going red, this means that you must hurry before everything collapses!\n")
    }
  }

  /** Initialize combat when player has encountered hostile npc */
  def combat(enemy: NPC) = new Combat(this.player, enemy)

  /** Trade is initialized when player encounters friendly npc */
  def trade(merchant: NPC) = new Trade(this.player, merchant)

  /** Checks for npcs in certain location. If player and npc are at the same location, then either combat or trade will happen */
  def npcInArea() = {
    for (npc <- this.player.location.nonPlayerChar){
      if (npc._2.currentLocation == player.location && npc._2.hostile){
        combat(npc._2)
      } else if(npc._2.currentLocation == player.location && !(npc._2.hostile)){
        trade(npc._2)
      }
    }
  }

  /** Moving npc plays a turn and moves */
  def npcPlayTurn() = {
    for(npc <- movingNpcVector){
      if(!npc.dead){
        npc.movement()
      }
    }
  }

  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = this.player.location == this.ending && player.has("bloody knife") && player.has("used bullet") && player.has("bottle of medicine")

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit || this.player.dead || this.player.location == strangeRoomEnd

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "You wake up in the middle of some strange room. You feel that something is not right but cannot say why.\n" +
                       "Room’s walls are padded, strange. You stand up and look around, rooms is empty, but there is a door."


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
    if (this.isComplete || player.dead || this.player.location == strangeRoomEnd) {
      "You open the door\n"+
      "You wake up screaming, and realize that the things that just happened were just a dream.\n" +
      "Feeling relieved turns to panic when you realize that you are tied to some strange bed and the walls are padded.\n" +
      "You start screaming for help, and then the door opens. You see that two people, a man and a woman entering the room.\n" +
      "They talk something but you can not hear them.\n" +
      "Both of them walk towards you, when suddenly woman pulls a needle filled with some liquid and the man starts to hold tighly against the bed.\n" +
      s"Woman injects the liquid in to your vein and says -Good night ${player.playerName}.\n" +
      "You wake up in the middle of some strange room...\n"
    } else if (this.turnCount == this.timeLimit)
      "Suddenly, you fall to the ground and everything begins to go black, you are losing your consciousness. You realize that you were too slow.\nGame over!"
    else  // game over due to player quitting
      "Quitteeeerrr!"
  }


  /** Plays a turn by executing the given in-game command, such as "go west". Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String): String = {
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    if (outcomeReport.isDefined) {
      this.turnCount += 1
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }


}
