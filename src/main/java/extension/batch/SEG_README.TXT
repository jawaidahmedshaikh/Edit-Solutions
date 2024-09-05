The two classes of 1) HttpBatchRequest.java and 2) Main.java were written to be
executed from an executable jar.

We did not want to add the executable jar to our build since it is a small project
written specifically for Vision.

I created the executable jar from Netbeans 6.0 which automatically creates the
executable jar upon building.

Should this code need to be edited and re-distributed, then take note of what was
packaged:


I created a BatchAgentProxy.zip that contains:

1. The BatchAgentProxy.jar (the excecutable jar that contains the two compiled classes)
2. The BatchAgentProxy.bat (the client runs this to launch the BatchAgentProxy.jar)
3. README.TXT (setup/run information for the client)


The BatchAgentProxy.zip is what was given to the client.



Gregg Frosti
