======================================================
Oracle Forms Wrapper for Data Visualization Components
======================================================
Author: Bincsoft (version: 1.0.2 - December 12th 2010)
Based on the work by Frank Nimphius (version: 9.0.4 - January 2005)

Instructions apply for JDeveloper.

To build this project:
1. Go to Edit -> Properties -> Libraries and Classpath.
2. Click Add Jar/Directory.
3. Add C:\oracle\Middleware\oracle_common\modules\oracle.adf.view_11.1.1\dvt-jclient.jar
4. Add C:\oracle\Middleware\oracle_common\modules\oracle.adf.view_11.1.1\dvt-utils.jar
5. Add frmall.jar from your Oracle Forms installation.
6. Execute build.

To deploy this project:
1. Go to Build -> Deploy -> FormsGraph to JAR file.
2. Locate the FormsGraph.jar file in the bin\deploy directory and copy it to your forms\java directory.
3. Locate the dvt-jclient.jar file inside your JDeveloper installation and copy it to your forms\java directory.
4. Locate the dvt-utils.jar file inside your JDeveloper installation and copy it to your forms\java directory.
5. Locate the jewt4.jar file inside your JDeveloper installation and copy it to your forms\java directory.
6. Locate the xmlparserv2.jar file inside your JDeveloper installation and copy it to your forms\java directory.
7. Add all JAR files to your formsweb.cfg configuration file.
8. Sign all the JAR files.
9. Run your Forms application.

Deploy with Maven:
1. Find frmall.jar in your Oracle Forms installation and copy it to {forms-dvc-wrapper}/lib.
2. Find dvt-jclient.jar, dvt-utils.jar, jewt4.jar, xmlparserv2.jar and copy them to {forms-dvc-wrapper}/lib.
3. Create a sub folder in {forms-dvc-wrapper}/lib/com/oracle/forms/frmall matching the version of your Oracle Forms installation. Example: 11g.
4. Move frmall.jar from {forms-dvc-wrapper}/lib to {forms-dvc-wrapper}/lib/com/oracle/forms/frmall/{version}.
5. Rename frmall.jar to frmall-{version}.jar. Example: frmall-11g.jar.
6. Repeat step 3 through 5 for dvt-jclient.jar, dvt-utils.jar, jewt4.jar, xmlparserv2.jar.
7. Edit {forms-dvc-wrapper}/pom.xml and update forms.version and dvc.version according to your environment.
8. Build using 'mvn package'.

Documentation for oracle.dss.graph and oracle.dss.gauge are located in the file "dvt-faces-javadoc.jar" which you can find in your JDeveloper install directory.