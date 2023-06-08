A small peer-to-peer chat application with command line interface (CLI) in Java and OpenJDK. The chat application has two optional input parameters:
sqlchat [--other_instance <IP>:<Port>]

If the IP address and port parameters are empty, the chat application outputs its IP and port to the terminal window at first and then waits for incoming connections. Another instance of the chat application can be started in another terminal window using this information as parameter.

Both chat applications are now connected and accept messages. Once a client receives a message, it outputs them directly and without modification to the terminal window. The chat application can be terminated, at any time, by entering the command "EXIT".


****************************************************************************************************************
The application can be run using terminal or IntelliJ IDE

Using Intellij IDE:
1) Run the SQLChat as Java file.
2) Copy the Ip and port from console.
3) Right click on SQLChat(java file) < Modify Run configuration < Modiy< click on 'allow multiple instance'<apply<ok
4) Again  Right click on SQLChat(java file) < Modify Run configuration < pass the copied ip and port(same order) as parameter for main method<change name to 'SQLChat1'<apply<ok
5) Run the SQLChat as java file.
6) Connection is established and application is started.
7) type exit to abort the chat.

Using terminal:
1) Traverse to cd:SQLTask\out\production\SQLTask
2) Enter command: java src.main.sqlchat.SQLChat
3) Copy the Ip and port from terminal.
4) Open another terminal and traverse to cd:SQLTask\out\production\SQLTask
5)  Enter command: java src.main.sqlchat.SQLChat {ip} {port}
6) Connection is established and application is started.
7) type exit to abort the chat.


