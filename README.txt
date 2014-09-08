***************************************************************************************
		***** SECURE BANKING SOFTWARE *****
***************************************************************************************
1) Download and install latest jdk.
2) Download eclipse and extract it.
3) Download Spring Tools Suite (STS) : http://spring.io/tools/sts and extract it.
5) Download and install SourceTree.
6) Clone this repository in SourceTree : https://github.com/sanketra/SBSProject and store it to a folder.
7) Open STS with a workspace and Go to file, import project, Existing Maven Project and select the above folder.
8) Download and extract Apache tomcat 8. Create a tomcat server in STS and add the above project.
9) Download MySQL installer and it will guide you through the process to install My SQL on your pc(Server only).
   Once the install is complete the MySQL server should be configured with a username and password.
10) In STS(3) delete the existing server and add tomcat(Google it).
11) Download and extract MySQL workbench. Launch the MySQL workbench & run the below statement:
    CREATE TABLE test.`Person` (
		`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
		`name` varchar(20) NOT NULL DEFAULT '',
		`country` varchar(20) DEFAULT NULL,
		PRIMARY KEY (`id`)
	);
11) In STS find the file "servlet-context.xml" & update the MySQL username and password.
12) Build the project and run the server. Open a browser & hit http://localhost:8080/SpringMVCHibernate/persons 
