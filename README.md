# AndroidClientServer
This project tries to use the Android Service component as 1. Task Executer 2. Server

1. Task Executer- that receives the work in terms of Intent and performs the task as requested. These tasks run on the worker thread with the help of HandlerThread's looper.
2. Server- that allows clients to bind, send and receive data, to and from clients using android.os.Messenger.
