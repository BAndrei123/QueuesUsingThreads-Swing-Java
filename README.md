# QueuesUsingThreads-Swing-Java
The assignment’s main objective is to create an application that implements queues  procedures. The queues process the clients with an id, arrival time, and a processing time  representing the time in which the client will be served from when he arrived at the  queue. 
The sub-objectives are to see how the clients can be processed faster by using threads 
which will make the queues and the management of the clients in the waiting list work at 
the same. Because of the usage of threads, we will need to declare special data structures 
and variables.
Every queue will have a BlockingQueue data structure of clients which will 
provide the threads safety. The variables waitingPeriod and totalWaiting time variables 
will be used as AtomicInteger to avoid threads interference.
