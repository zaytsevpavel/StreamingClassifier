package backend.connector

import akka.actor.{Actor, ActorRef, Props}
import akka.stream.scaladsl.Tcp.OutgoingConnection
import backend.connector.Connector.Endpoint
import backend.messages._
/**
  * Created by pzaytsev on 4/9/17.
  */

//Principles of operation:
//
//1) Disconnected State at the beginning
//2) Sender requests a connection
//3) It receives a connection, goes to Pending
//4) On transition, looks for endpoints and builds processing tcp based pipeline
//5) When successfully connected, sends itself a message that connected

// can be of several types depending on what it connects to

// should be able to use persistence module
// in case of shutdown it should restore a state it was in


object Connector {

  case class Endpoint(host: String, port: Int)
  def props_connector(endpoint: Endpoint): Props = Props(new Connector(endpoint))


  object Messages {

    trait ConnectionMessage
    case class SuccessfullyConnected(connection: OutgoingConnection) extends ConnectionMessage
    case object ConnectionFailed extends ConnectionMessage


  }
}

class Connector(endpoint: Endpoint) extends Actor {

  // sys it belongs to
  implicit val sys = context.system
  implicit val disp = context.dispatcher
  def receive = Actor.emptyBehavior
}
