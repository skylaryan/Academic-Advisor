from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.common.exceptions import TimeoutException
from lxml import html
from lxml.etree import tostring
from selenium.webdriver.support.ui import Select
import bs4 as bs
import requests
import simplejson as json
from pyvirtualdisplay import Display


display = Display(visible=0, size=(1024, 768))
display.start()


browser = webdriver.Firefox(executable_path=r'~/academicadvisor/backend/scrapers/src/main/python/geckodriver') #replace with .Firefox(), or with the browser of your choice
url = "https://public.my.wisc.edu/web/expanded"
browser.get(url)
delay = 10000 # seconds
myElem = WebDriverWait(browser, delay).until(EC.presence_of_element_located((By.ID, 'keywordSearch')))
innerHTML = browser.execute_script("return document.body.innerHTML")
htmlElem = html.document_fromstring(innerHTML)
tdElems = htmlElem.find_class('btn btn-default launch-app-button ng-scope md-uw-madison-theme') #list of all td elems
inner_html = tostring(tdElems[0])
soup = bs.BeautifulSoup(inner_html, 'lxml')
for a in soup.find_all('a', href=True):
    x = "https://public.my.wisc.edu/"+a['href']
browser.get(x)
innerHTML = browser.execute_script("return document.body.innerHTML")
soup = bs.BeautifulSoup(innerHTML, 'lxml')
norespg = Select(browser.find_element_by_id("resultsPerPageSelectPluto_29_u124l1n31_12_tw_"))
norespg.select_by_index(3)

d = []
for it in range(1, 2):
	mySelectElement = Select(browser.find_element_by_id("subjSelPluto_29_u124l1n31_12_tw_"))
	Spring = browser.find_element_by_id("termChoice3")
	Spring.click()
	mySelectElement.select_by_index(it)
	submitButton = browser.find_element_by_id("submitButtonPluto_29_u124l1n31_12_tw_") 
	submitButton.click()
	innerHTML = browser.execute_script("return document.body.innerHTML")
	myElem = WebDriverWait(browser, delay).until(EC.presence_of_element_located((By.ID, 'subjSelPluto_29_u124l1n31_12_tw_')))
	myElem = WebDriverWait(browser, delay).until(EC.presence_of_element_located((By.ID, 'termChoice3')))
	myElem = WebDriverWait(browser, delay).until(EC.presence_of_element_located((By.ID, 'submitButtonPluto_29_u124l1n31_12_tw_')))
	innerHTML = browser.execute_script("return document.body.innerHTML")
	soup = bs.BeautifulSoup(innerHTML, 'lxml')
	courlis = soup.find_all("tr", class_="courseResult")
	x=[]
	flag = False
	for i in courlis:
		courses = i.find_all('td')
		for info in courses:
			try:
				if str(info.get_text().strip()) != '' and str(info.get_text().strip()) != 'sections' and str(info.get_text().strip()) != 'Loading section information...':
					tempstr = str(info.get_text().strip())
					if tempstr.find("Course Description") != -1:
						flag=True
					x.append(str(info.get_text().strip()))
			except:
				pass		
		if flag==True:
			if x != []:
				d.append(x)
				x=[]
		flag = False		

display.stop()
            #with open('data.txt', 'w') as outfile:
print json.dumps(d)
		




