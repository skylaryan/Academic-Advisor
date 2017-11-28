# academicadvisor
Iteration 1 <11/1/2017> 

Instructions:
1.	Logon to GitHub and search for “tyleroconnell/adademicadvisor”; 
2.	Click on the green button “Clone or download” to download the zip file of this project to your local computer;
3.	Unzip the file to your working folder;
4.	Download and install the newest version of Android Studio;
5.	Launch Android Studio and open the project from "File -> Open..." by choosing the root folder of the unzipped file;
6.	Press Shift + F10 or click on the "Run" button on the tools bar;
7.  To run the MyCourseGuideScraper, first install selenium, lxml, beautifulsoup, requests and json for python
8.  Install Firefox and the selenium geckodriver for your OS.
9.  Change path in line 145 of CourseGuideScraper.py to the location of geckodriver on your computer and then run the python program.
10. The output of the CourseGuideScraper is in the data.txt file in the out folder.
10. For RmpScraper, add the jar's for the jsoup and simple json libraries. 
11. For PdfScraper, add the PDFBox jar. Also make sure the Course Grade Distribution documents are in the same directory as the program.

For the API,
1. Open IntelliJ
2. Click Open for an existing project
3. Navigate to the directory of "academicadvisor/backend/api" from the repo you cloned and click Open
4. Click File | New | Module from Existing Sources
5. Navigate to "academicadvisor/backend/api" and click Open
6. Make sure "Create module from existing sources" is selected and click Next
7. Make sure "academicadvisor/backend/api/src/main/java" and "academicadvisor/backend/api/src/test/java" are both selected and click Next
8. Keep clicking Next past every screen continuing to ensure those two are selected
9. Finally when prompted press Finish
10. Click File | Project Structure | Project and make sure you have specified the path to your Java SDK in "Project SDK"
11. Click Apply
12. Click Modules listed under the current Project menu
13. Select both java and test which were added before and right click and select Move Module to Group > New Top Level Group...
14. Click Apply
15. Click OK
16. Build | Rebuild Project
