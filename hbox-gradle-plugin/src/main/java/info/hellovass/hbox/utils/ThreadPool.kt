package info.hellovass.hbox.utils

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ThreadPool {

    val executor: ExecutorService = Executors.newWorkStealingPool(
        Runtime.getRuntime().availableProcessors()
    )
}