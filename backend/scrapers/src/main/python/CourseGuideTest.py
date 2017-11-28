

import subprocess
cmd = 'python CourseGuideScraper.py'

p = subprocess.Popen(cmd, stdout=subprocess.PIPE, shell=True)
out, err = p.communicate() 
result = out.split('\n')
#print out

x = "[\"ACCT I S\", \"100\", \"Introductory Financial Accounting\", \"3\", \"2017 Fall\", \"Course Description: Examines generally accepted accounting principles for measurement and reporting of financial information in a balance sheet, income statement, and statement of cash flows; introduction to analysis and interpretation of financial accounting data for decision-making purposes."
if (out.find(x) != -1):
	print "test 1 passed"

x = "[\"ACCT I S\", \"211\", \"Introductory Managerial Accounting\", \"3\", \"2017 Fall\", \"Course Description: Managerial accounting concepts relevant for decision-making; use of accounting information for planning, decision-making, and control of business operations in various management and business environments." 	
if (out.find(x) != -1):
	print "test 2 passed"

x = "[\"ACCT I S\", \"302\", \"Financial Reporting II\", \"3\", \"2017 Fall\", \"Course Description: Examines current and emerging financial accounting theory and techniques used to measure and report financial information to investors, creditors, and other external users, including dilutive securities, investments, revenue recognition, income tax allocation, pensions, leases and accounting changes." 	
if (out.find(x) != -1):
	print "test 3 passed"

x = "[\"ACCT I S\", \"600\", \"Accountancy Internship and Practice Research\", \"3-6\", \"2017 Sprng\", \"Course Description: The internship program represents an opportunity for students to experience a professional accounting practice first hand and to integrate this experience with their formal education." 	
if (out.find(x) != -1):
	print "test 4 passed"

x = "[\"ACCT I S\", \"621\", \"Corporate and Advanced Taxation\", \"3\", \"Graduate 50%: Y\", \"2017 Fall\", \"Course Description: Application of federal tax provisions and administrative rules pertaining to corporations and shareholders, including elective provisions for"
if (out.find(x) != -1):
	print "test 5 passed"
