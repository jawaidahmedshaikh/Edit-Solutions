SETUP/RUN INFORMATION

1. 	There are two files that need to be placed together in the same 
	directory (any directory is fine):

 	a) BatchAgentProxy.bat 
	b) BatchAgentProxy.jar

2. 	Edit the BatchAgentProxy.bat file and set its JAVA_HOME environment variable 
	to the root of your JDK 1.6 installation. Save the file.
	A typical example would look like the following:
	
	set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_04

3. 	Test the basic setup of the program from the command line as follows (a 
	successful message should print):

	c:\BatchAgentProxy.bat test

4. 	If the successful message prints, then it is set to go. A HELP menu can be 
	displayed as follows:

	c:\BatchAgentProxy.bat help

5. 	Here is a real-world example (make sure your target Tomcat environment is up and running):

	Let's say that we want to launch the "Process Daily Batch" process. By examining the
	Scheduling Documentation for "Process Daily Batch", we can infer the following command
	assuming a machine name of 'foomachine':

	c:\BatchAgentProxy.bat foomachine processDailyBatch 2008/03/13 All fooOperator fooUsername fooPassword SYNCHRONOUS

6. 	Once the example from #5 finishes, you can expect 1 of 4 response codes:
	
	1) PROCESSED_WITHOUT_ERRORS 
	2) PROCESSED_WITH_ERRORS
	3) PROCESSED_BUT_NO_ELEMENTS_FOUND    
	4) UNEXPECTED_ERROR

	#1 through #3 represent normal processing. If you encounter #4, then contact us - something is not right.




Some examples:

A. Create Bank Extracts (Contract Number not specied e.g. NULL)

	BatchAgentProxy.bat localhost createBankExtracts All NULL XML fooUsername fooPassword SYNCHRONOUS

B. Create Accounting Extract XML (Suppress Extract = false)

	BatchAgentProxy.bat localhost createAccountingExtract_XML 2007/02/02 false XML fooUsername fooPassword SYNCHRONOUS

C. Run Data Warehouse (Case Number and Group Number not specified e.g. NULL NULL)

	BatchAgentProxy.bat localhost runDataWarehouse 2007/02/02 All NULL NULL fooUsername fooPassword SYNCHRONOUS

D. Setup Agent Commission Checks

	BatchAgentProxy.bat localhost setupAgentCommissionChecksCK Weekly Y fooOperator fooUsername fooPassword SYNCHRONOUS

E. Setup Agent Commission Checks   

	BatchAgentProxy.bat localhost setupAgentCommissionChecksEFT Weekly Y fooUsername fooUsername fooPassword SYNCHRONOUS
