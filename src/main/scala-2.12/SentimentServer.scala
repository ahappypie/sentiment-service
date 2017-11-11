import java.util.logging.Logger

import com.watershed.sentiment.protos.sentiment.SentimentGrpc
import io.grpc.{Server, ServerBuilder}

class SentimentServer(server: Server) {

  val logger: Logger = Logger.getLogger(classOf[SentimentServer].getName)

  def start(): Unit = {
    server.start()
    logger.info(s"Server started, listening on ${server.getPort}")
    sys.addShutdownHook {
      // Use stderr here since the logger may has been reset by its JVM shutdown hook.
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      stop()
      System.err.println("*** server shut down")
    }
    ()
  }

  def stop(): Unit = {
    server.shutdown()
  }

  /**
    * Await termination on the main thread since the grpc library uses daemon threads.
    */
  def blockUntilShutdown(): Unit = {
    server.awaitTermination()
  }
}

object SentimentServer extends App {

  val server = new SentimentServer(
    ServerBuilder
      .forPort(8980)
      .addService(
        SentimentGrpc.bindService(
          new SentimentService(),
          scala.concurrent.ExecutionContext.global
        )
      )
      .build()
  )
  server.start()
  server.blockUntilShutdown()
}